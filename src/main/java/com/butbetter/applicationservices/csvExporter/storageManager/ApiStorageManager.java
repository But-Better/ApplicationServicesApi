package com.butbetter.applicationservices.csvExporter.storageManager;

import com.butbetter.applicationservices.csvExporter.apiRequestHandler.RemoteFileService;
import javassist.tools.web.BadHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotActiveException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ApiStorageManager implements StorageManager {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final RemoteFileService remoteFileService;

	private final StorageManager tmpFileManager;

	public ApiStorageManager(URL location, StorageManager tmpFileManager) {
		logger.info("Remote: \"" + location + "\" is getting used for the save location");

		this.remoteFileService = new RemoteFileService(location);

		this.tmpFileManager = tmpFileManager;
	}

	public ApiStorageManager(URL location, StorageManager tmpFileManager, RestTemplate specificTemplate) {
		logger.info("Remote: \"" + location + "\" is getting used for the save location");

		this.remoteFileService = new RemoteFileService(location, specificTemplate);

		this.tmpFileManager = tmpFileManager;
	}

	public ApiStorageManager(RemoteFileService remoteFileService, StorageManager tmpFileManager) {
		logger.info("Remote: \"" + remoteFileService.getStorageApiUrl() + "\" is getting used for the save location");

		this.remoteFileService = remoteFileService;

		this.tmpFileManager = tmpFileManager;
	}

	@Override
	public URI getSaveLocation() throws StorageNotReadyException {
		try {
			return remoteFileService.getStorageApiUrl().toURI();
		} catch (URISyntaxException e) {
			String message = "given location for storage " + remoteFileService.getStorageApiUrl().toString() + " can't be converted into URI " +
					"(not formatted strictly according to to RFC2396). This can lead to failure in storing Files";
			logger.error(message);
			throw new StorageNotReadyException(message);
		}
	}

	@Override
	public boolean ready() {
		return remoteFileService.isUp() &&
				tmpFileManager.ready();
	}

	@Override
	public void saveContentToFile(String name, String content) throws IOException, StorageNotReadyException {
		List<String> contentInStream = putStringInCollection(content);
		File fileToUpload = createFileWithContentLocally(name, contentInStream.stream());
		uploadFileToRemote(fileToUpload);
		removeTemporaryFile(name);
	}

	private List<String> putStringInCollection(String content) {
		List<String> contentList = new ArrayList<>();
		contentList.add(content);
		return contentList;
	}

	@Override
	public void saveContentToFile(String name, Stream<String> content) throws IOException, StorageNotReadyException {
		File fileToUpload = createFileWithContentLocally(name, content);
		uploadFileToRemote(fileToUpload);
		removeTemporaryFile(name);
	}

	@Override
	public void copyFileToStorage(Path file) throws StorageNotReadyException, FileNotFoundException, NotActiveException {
		File fileToUpload = file.toFile();
		assert fileExists(fileToUpload);
		uploadFileToRemote(fileToUpload);
	}

	@Override
	public File getFileHandleWithName(String filename) {
		throw new UnsupportedOperationException("file request is not supported by the storage api due to api restrictions");
	}

	@Override
	public Stream<Path> getAllSavedAsPaths() {
		throw new UnsupportedOperationException("file listing is not supported by the storage api due to api restrictions");
	}

	@Override
	public void moveFileToAnotherStorage(StorageManager nextStorage, String name) {
		throw new UnsupportedOperationException("file movement is not supported by the storage api due to api restrictions");

	}

	@Override
	public void removeFileWithName(String name) {
		throw new UnsupportedOperationException("file removal is not supported by the storage api due to api restrictions");

	}

	private void removeTemporaryFile(String name) throws IOException, StorageNotReadyException {
		try {
			tmpFileManager.removeFileWithName(name);
		} catch (NameNotFoundException e) {
			String message = "temporary file " + name + " couldn't be removed, Storage might not be ready";
			logger.error(message);
			throw new StorageNotReadyException(message);
		}
	}
	private boolean fileExists(File fileToUpload) {
		return fileToUpload.exists();
	}
	private void uploadFileToRemote(File fileToUpload) throws NotActiveException, FileNotFoundException, StorageNotReadyException {
		try {
			remoteFileService.uploadFile(fileToUpload);
		} catch (BadHttpRequest e) {
			String message = "given remote api (" + remoteFileService.getStorageApiUrl() + ") is not available";
			logger.error(message);
			throw new StorageNotReadyException(message);
		}
	}
	private File createFileWithContentLocally(String name, Stream<String> content) throws IOException, StorageNotReadyException {
		if(fileExists(new File(name))) {
			removeTemporaryFile(name);
		}

		List<String> contentList = content.collect(Collectors.toList());

		try {
			if (onlyContainsOne(contentList.stream())) {
				tmpFileManager.saveContentToFile(name, contentList.stream().findFirst().get());
			} else {
				tmpFileManager.saveContentToFile(name, content);
			}
		} catch (NameAlreadyBoundException e) {
			String message = "The Storage might be broken due to complications in deleting and saving temporary files, therefore the Storage might not be fully ready";
			logger.error(message);
			throw new StorageNotReadyException(message);
		}

		return getTemporaryFileHandle(name);
	}

	private boolean onlyContainsOne(Stream<?> stringStream) {
		List<?> copy = stringStream.collect(Collectors.toList());
		return copy.size() == 1;
	}

	private File getTemporaryFileHandle(String name) throws IOException, StorageNotReadyException {
		try {
			return tmpFileManager.getFileHandleWithName(name);
		} catch (NameNotFoundException e) {
			String message = "The Storage might be broken due to complications in deleting and saving temporary files, " +
					"therefore the Storage might not be fully ready yet";
			logger.error(message);
			throw new StorageNotReadyException(message);
		}
	}

}
