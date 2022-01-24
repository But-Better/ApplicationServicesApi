package com.butbetter.applicationservices.csvExporter.storageManager;

import com.butbetter.applicationservices.csvExporter.apiRequestHandler.RemoteFileService;
import javassist.tools.web.BadHttpRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApiStorageManagerTest {

	private static final String BASE_PATH = "src/test/resources/testStorage/apiUploadTest";

	private RemoteFileService mockedApiService;
	private StorageManager mockedLocalFileManager;

	private StorageManager manager;

	@BeforeEach
	void setUp() {
		mockedApiService = mock(RemoteFileService.class);
		mockedLocalFileManager = mock(LocalFileStorageManager.class);

		assert new File(BASE_PATH).exists() || new File(BASE_PATH).mkdirs();

		manager = new ApiStorageManager(mockedApiService, mockedLocalFileManager);
	}

	@AfterEach
	void tearDown() {
		manager = null;
	}

	@Test
	void getSaveLocation_returnsRemoteUrlOfRemoteFileService() throws StorageNotReadyException, MalformedURLException, URISyntaxException {
		URL expectedUrl = new URL("http://localhost");
		when(mockedApiService.getStorageApiUrl()).thenReturn(expectedUrl);

		URI gottenUrl = manager.getSaveLocation();

		assertEquals(expectedUrl.toURI(), gottenUrl);
	}

	@Test
	void ready_trueOnApiUpAndLocalFileManagerReady() {
		when(mockedApiService.isUp()).thenReturn(true);
		when(mockedLocalFileManager.ready()).thenReturn(true);

		assertTrue(manager.ready());
	}

	@Test
	void ready_falseOnApiNotUp() {
		when(mockedApiService.isUp()).thenReturn(false);
		when(mockedLocalFileManager.ready()).thenReturn(true);

		assertFalse(manager.ready());
	}

	@Test
	void saveContentToFile_createsFile() throws NameAlreadyBoundException, IOException, StorageNotReadyException {
		String content = "this is some content";
		String name = "name";

		manager.saveContentToFile(name, content);

		verify(mockedLocalFileManager, times(1)).saveContentToFile(name, content);
	}

	@Test
	void saveContentToFile_uploadsFile() throws NameAlreadyBoundException, IOException, StorageNotReadyException, BadHttpRequest {
		String content = "this is some content";
		String name = "name";

		manager.saveContentToFile(name, content);

		verify(mockedApiService, times(1)).uploadFile(any());
	}

	@Test
	void saveContentToFile_removesLocalFile() throws NameAlreadyBoundException, IOException, StorageNotReadyException, NameNotFoundException {
		String content = "this is some content";
		String name = "name";

		manager.saveContentToFile(name, content);

		verify(mockedLocalFileManager, times(1)).removeFileWithName(name);
	}

	@Test
	void saveContentToFile_createsFileOfStream() throws NameAlreadyBoundException, IOException, StorageNotReadyException {
		List<String> content = List.of("content", "content");
		String name = "name";
		Stream<String> contentStream = content.stream();

		manager.saveContentToFile(name, contentStream);

		verify(mockedLocalFileManager, times(1)).saveContentToFile(name, contentStream);
	}

	@Test
	void copyFileToStorage_uploadsFileAtGivenPath() throws IOException, BadHttpRequest, StorageNotReadyException {
		String name = "testFile";
		File testFile = new File(BASE_PATH + "/" + name);

		testFile.createNewFile();

		Path path = testFile.toPath();

		manager.copyFileToStorage(path);

		verify(mockedApiService, times(1)).uploadFile(testFile);
	}

	@Test
	void copyFileToStorage_doesntRemoveLocalFile() throws NameNotFoundException, IOException, StorageNotReadyException {
		String name = "testFile";
		File testFile = new File(BASE_PATH + "/" + name);

		testFile.createNewFile();

		Path path = testFile.toPath();

		manager.copyFileToStorage(path);

		verify(mockedLocalFileManager, times(0)).removeFileWithName(name);
	}

	@Test
	void getFileHandleWithName_throwsNotSupportedException() {
		assertThrows(UnsupportedOperationException.class, () -> manager.getFileHandleWithName(null));
	}

	@Test
	void getAllSavedAsPaths_throwsNotSupportedException() {
		assertThrows(UnsupportedOperationException.class, () -> manager.getAllSavedAsPaths());
	}

	@Test
	void moveFileToAnotherStorage_throwsNotSupportedException() {
		assertThrows(UnsupportedOperationException.class, () -> manager.moveFileToAnotherStorage(null, null));
	}

	@Test
	void removeFileWithName_throwsNotSupportedException() {
		assertThrows(UnsupportedOperationException.class, () -> manager.removeFileWithName(null));
	}
}