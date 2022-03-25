package com.butbetter.applicationservices.csvExporter.storageManager;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class LocalFileStorageManager implements StorageManager {

    private final Logger logger = LoggerFactory.getLogger(LocalFileStorageManager.class);

    private final Path saveLocation;

    public LocalFileStorageManager(Path location) {
        logger.info("Location: \"" + location + "\" is getting used for the save location");
        this.saveLocation = location;
        try {
            createPathIfNotExistent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean pathUsedIsExistent() {
        return saveLocation.toFile().exists();
    }

    private void createPathIfNotExistent() throws IOException {
        logger.info("initializing Storage");
        try {
            Files.createDirectories(saveLocation);
        } catch (IOException e) {
            String message = "Could not initialize storage, directory at \"" + saveLocation + "\" could not be created";
            logger.error(message);
            throw new IOException(message, e);
        }
        logger.info("initialisation complete");
    }

    @Override
    public URI getSaveLocation() {
        return saveLocation.toUri();
    }

    @Override
    public boolean ready() {
        return saveLocationExists()
                && saveLocationWritable()
                && saveLocationReadable();
    }

    private boolean saveLocationReadable() {
        return saveLocation.toFile().canRead();
    }

    private boolean saveLocationWritable() {
        return saveLocation.toFile().canWrite();
    }

    private boolean saveLocationExists() {
        return saveLocation.toFile().exists();
    }

    @Override
    public void saveContentToFile(String name, String content) throws NameAlreadyBoundException, IOException, StorageNotReadyException {
        checkIfStorageIsReady();

        name = StringUtils.cleanPath(name);
        logger.info("saving new content in " + name);

        if (filenameAlreadyExists(name)) {
            String message = "Filename: " + name + " already exists and therefore cannot be saved under that name";
            logger.error(message);
            throw new NameAlreadyBoundException(message);
        }


        String newFilePath = saveLocation.toAbsolutePath() + "/" + name;

        FileWriter writer = openNewFileWriter(newFilePath);

        try {
            writer.write(content);
        } catch (IOException e) {
            String message = "Writing content into " + newFilePath + " failed";
            logger.error(message, e);
            throw new WriteAbortedException(message, e);
        }

        closeWriter(writer);

        logger.info("file writing to " + newFilePath + " successful");
    }

    private void checkIfStorageIsReady() throws StorageNotReadyException {
        if (!ready()) {
            String message = "Storage at " + getSaveLocation().getPath() + " is not ready yet, " +
                    "please make sure the Permissions are correctly set";
            logger.error(message);
            throw new StorageNotReadyException(message);
        }
    }

    @Override
    public void saveContentToFile(String name, Stream<String> content) throws NameAlreadyBoundException, IOException, StorageNotReadyException {
        checkIfStorageIsReady();
        name = StringUtils.cleanPath(name);
        logger.info("saving new content in " + name);

        if (filenameAlreadyExists(name)) {
            String message = "Filename: " + name + " already exists and therefore cannot be saved under that name";
            logger.error(message);
            throw new NameAlreadyBoundException(message);
        }


        String newFilePath = saveLocation.toAbsolutePath() + "/" + name;

        FileWriter writer = openNewFileWriter(newFilePath);

        content.forEach(f -> {
            try {
                writer.write(f);
                writer.write(System.lineSeparator());
            } catch (IOException e) {
                String message = "Writing content into " + newFilePath + " failed";
                logger.error(message, e);
                e.printStackTrace();
            }
        });

        closeWriter(writer);

        logger.info("file writing successful");

    }

    // original filename will be kept
    @Override
    public void copyFileToStorage(Path file) throws WriteAbortedException, StorageNotReadyException {
        checkIfStorageIsReady();
        String fileName = StringUtils.cleanPath(String.valueOf(file.getFileName()));
        logger.info("saving File with name: " + fileName);

        try (InputStream inputStream = new FileInputStream(file.toAbsolutePath().toString())) {
            Files.copy(inputStream, Paths.get(saveLocation + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            String message = "Failed to copy file " + fileName;
            logger.error(message, e);
            throw new WriteAbortedException(message, e);
        }
        logger.info("successfully saved new file " + fileName);
    }

    @Override
    public File getFileHandleWithName(String filename) throws NameNotFoundException, IOException, StorageNotReadyException {
        checkIfStorageIsReady();
        logger.info(filename + "'s file-handle was requested");
        if (!filenameAlreadyExists(filename)) {
            String message = "file name doesn't exist";
            logger.error(message);
            throw new NameNotFoundException(message);
        }

        //noinspection OptionalGetWithoutIsPresent (already checked with checkIfNameAlreadyExists)
        return Files.walk(saveLocation)
                .filter(Files::isRegularFile)
                .findFirst()
                .get()
                .toFile();
    }

    @Override
    public Stream<Path> getAllSavedAsPaths() throws IOException {
        logger.info("requesting all existing files");
        return Files.walk(saveLocation).filter(Files::isRegularFile);
    }

    @Override
    public void moveFileToAnotherStorage(StorageManager nextStorage, String name) throws NameNotFoundException, IOException, StorageNotReadyException {
        checkIfStorageIsReady();
        if (nextStorage.ready()) {
            String message = "given Storage at " + nextStorage.getSaveLocation().getPath() + " is not ready yet";
            logger.error(message);
            throw new StorageNotReadyException(message);
        }
        logger.info("moving " + name + " to " + nextStorage.getSaveLocation().getPath());
        File handle = getFileHandleWithName(name);
        nextStorage.copyFileToStorage(handle.toPath());
        removeFileWithName(name);
        logger.info("successfully moved file to " + nextStorage.getSaveLocation().getPath());
    }

    @Override
    public void removeFileWithName(String name) throws NameNotFoundException, IOException, StorageNotReadyException {
        checkIfStorageIsReady();
        logger.info("removing " + name);
        File handle = getFileHandleWithName(name);
        if (!FileUtils.deleteQuietly(handle)) {
            String message = "wasn't able to remove " + name + " ,please check permissions";
            logger.error(message);
            throw new IOException(message);
        }
    }

    private void closeWriter(FileWriter writer) throws WriteAbortedException {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            String message = "closing file handle to writer failed hard";
            logger.error(message, e);
            throw new WriteAbortedException(message, e);
        }
    }

    private FileWriter openNewFileWriter(String newFilePath) throws WriteAbortedException {
        try {
            return new FileWriter(newFilePath);
        } catch (IOException e) {
            String message = "could not open file to write to (Location: " + newFilePath + ")";
            logger.error(message, e);
            throw new WriteAbortedException(message, e);
        }
    }

    private boolean filenameAlreadyExists(String name) throws IOException {
        return getAllSavedAsPaths()
                .map(f -> f.getFileName().toString())
                .anyMatch(f -> f.equals(name));
    }
}
