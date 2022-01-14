package com.butbetter.applicationservices.csvExporter.dirCleaner;

import com.butbetter.applicationservices.csvExporter.storageManager.LocalFileStorageManager;
import com.butbetter.applicationservices.csvExporter.properties.CSVExporterProperties;
import com.butbetter.applicationservices.csvExporter.storageManager.StorageNotReadyException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class Cleaner implements Runnable {

	private final Logger logger = LoggerFactory.getLogger(Cleaner.class);

	private final LocalFileStorageManager storageManager;
	private final LocalFileStorageManager errorStorageManager;

	private final Path directoryToClean;
	private final Path errorDirectory;

	@Autowired
	public Cleaner(CSVExporterProperties properties) {
		logger.info("Location: " + properties.getLocation() + " is getting used for the temporary save location");
		this.directoryToClean = Paths.get(properties.getLocation());

		logger.info("Location: " + properties.getManualDeletionLocation() + " is getting used, for manual " +
				"deletion requirement location");
		this.errorDirectory = Paths.get(properties.getManualDeletionLocation());

		storageManager = new LocalFileStorageManager(directoryToClean);
		errorStorageManager = new LocalFileStorageManager(errorDirectory);
	}

	private void init() throws FileNotFoundException, LocationCouldNotBeCreated {
		logger.info("initializing Cleaner");
		try {
			Files.createDirectories(directoryToClean);
		}
		catch (IOException e) {
			String message = "Could not initialize, directory at \"" + directoryToClean + "\" could not be created";
			logger.error(message);
			throw new LocationCouldNotBeCreated(message, e);
		}

		try {
			Files.createDirectories(errorDirectory);
		}
		catch (IOException e) {
			String message = "Could not initialize, directory at \"" + errorDirectory + "\" could not be created";
			logger.error(message);
			throw new LocationCouldNotBeCreated(message, e);
		}
		checkIfLocationsWhereSuccessfullyCreated();
		logger.info("initialisation complete");
	}

	private void checkIfLocationsWhereSuccessfullyCreated() throws FileNotFoundException {
		checkIfPathOfStorageManagerExists(storageManager);
		checkIfPathOfStorageManagerExists(errorStorageManager);
	}

	private void checkIfPathOfStorageManagerExists(LocalFileStorageManager storageManager) throws FileNotFoundException {
		if (!storageManager.pathUsedExistent()) {
			String message = "Storage was not correctly initialized and the accessed Path doesn't exist, " +
					"this may create a lot of trouble";
			logger.error(message);
			throw new FileNotFoundException(message);
		}
	}

	private void clean() {
		Stream<Path> existing_files;
		try {
			existing_files = storageManager.getAllSavedAsPaths();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return;
		}
		if (!pathsExistInStream(existing_files)) {
			existing_files.forEach(this::removeFile);
		}
	}

	@SneakyThrows({StorageNotReadyException.class})
	private void removeFile(Path path) {
		try {
			storageManager.removeFileWithName(String.valueOf(path.getFileName()));
		} catch (NameNotFoundException e) {
			String message = "the given name " + path.getFileName() + " couldn't be removed because it might have already been removed";
			logger.warn(message);
		} catch (IOException e) {
			String message = "the storage manager wasn't able to remove " + path + " for an unknown reason, it will now be moved to " + errorDirectory.toAbsolutePath();
			logger.error(message);
			moveFileToError(path);
		}
	}

	@SneakyThrows(StorageNotReadyException.class)
	private void moveFileToError(Path path) {
		try {
			storageManager.moveFileToAnotherStorage(errorStorageManager, path.getFileName().toString());
		} catch (NameNotFoundException e) {
			String message = "the given name " + path.getFileName() + " couldn't be removed because it might have already been removed";
			logger.warn(message);
		} catch (IOException e) {
			String message = "the given file at " + path + " can't be moved, needs to be removed manually (permission problems?)";
			logger.error(message);
			e.printStackTrace();
		}
	}

	private boolean pathsExistInStream(Stream<Path> existing_files) {
		Stream<Path> copy = existing_files.collect(Collectors.toList()).stream();
		return copy.findFirst().isPresent();
	}

	@Override
	public void run() {
		clean();
	}

	private static class LocationCouldNotBeCreated extends IOException {
		public LocationCouldNotBeCreated(String message, IOException e) {
			super(message, e);
		}
	}
}
