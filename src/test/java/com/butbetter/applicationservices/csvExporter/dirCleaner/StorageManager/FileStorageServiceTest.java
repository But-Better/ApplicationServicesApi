package com.butbetter.applicationservices.csvExporter.dirCleaner.StorageManager;

import com.butbetter.applicationservices.csvExporter.storageManager.LocalFileStorageManager;
import com.butbetter.applicationservices.csvExporter.storageManager.StorageNotReadyException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileStorageServiceTest {

	private static final String BASE_PATH = "src/test/resources/testStorage";

	private LocalFileStorageManager serviceToTest;

	@BeforeEach
	void setUp() {
		serviceToTest = new LocalFileStorageManager(Path.of(BASE_PATH + "/storageDirectory"));
	}

	@AfterEach
	void tearDown() throws IOException {
		Files.walk(Path.of(serviceToTest.getSaveLocation())).forEach(f -> {
			try {
				FileUtils.forceDelete(f.toFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		serviceToTest = null;
	}

	@Test
	void testSaveSingleContentInFile_GetsCreated() throws NameAlreadyBoundException, IOException, StorageNotReadyException {
		String name = "singleContentTestFile.file";
		String content = "some content";

		serviceToTest.saveContentToFile(name, content);
		assert Files.walk(Path.of(serviceToTest.getSaveLocation())).anyMatch(f -> f.getFileName().toString().equals(name));
	}

	@Test
	void testSaveMultipleStringsInFile_GetsCreated() throws NameAlreadyBoundException, IOException, StorageNotReadyException {
		String name = "multipleContentTestFile.file";
		String[] content = {"content", "with", "more", "content"};

		serviceToTest.saveContentToFile(name, Arrays.stream(content));
		assert Files.walk(Path.of(serviceToTest.getSaveLocation())).anyMatch(f -> f.getFileName().toString().equals(name));
	}

	@Test
	void testSave_CreatesNewFile() throws IOException, StorageNotReadyException {
		String filename = "newFileToSave";
		File fileToSave = new File(BASE_PATH + "/newFileToSave");

		assert fileToSave.createNewFile();

		serviceToTest.copyFileToStorage(fileToSave.toPath());

		assertTrue(Files.walk(Path.of(serviceToTest.getSaveLocation().getPath()))
				.anyMatch(f -> new File(serviceToTest.getSaveLocation().getPath() + "/" + filename)
						.compareTo(f.toFile()) == 0));
	}

	@Test
	void testGetFileHandle_ReturnsRightFile() throws IOException, NameNotFoundException, StorageNotReadyException {
		String filename = "newFileToSave";
		File fileToSave = new File(BASE_PATH + "/newFileToSave");

		assert fileToSave.createNewFile();

		serviceToTest.copyFileToStorage(fileToSave.toPath());
		assertEquals(new File(serviceToTest.getSaveLocation().getPath() + "/" + filename), serviceToTest.getFileHandleWithName(filename).getAbsoluteFile());
	}

	@Test
	void testGetAllSaved_GivesRightNumberOfFiles() throws IOException, StorageNotReadyException {
		File fileToSave = new File(BASE_PATH + "/newFileToSave");

		assert fileToSave.createNewFile();

		serviceToTest.copyFileToStorage(fileToSave.toPath());

		assertEquals(1, serviceToTest.getAllSavedAsPaths().count());
	}

	@Test
	void testGetAllSavedPaths_GivesTheRightFileBack() throws IOException, StorageNotReadyException {
		String filename = "newFileToSave";
		File fileToSave = new File(BASE_PATH + "/newFileToSave");

		assert fileToSave.createNewFile();

		serviceToTest.copyFileToStorage(fileToSave.toPath());

		Optional<Path> firstPath = serviceToTest.getAllSavedAsPaths().findFirst();

		assertTrue(firstPath.isPresent());
		assertEquals(new File(serviceToTest.getSaveLocation().getPath()  + "/" + filename).toPath().toAbsolutePath(), firstPath.get().toAbsolutePath());
	}

	@Test
	void testMoveAndDelete_SavesInNewStorage() throws IOException, NameNotFoundException, URISyntaxException, StorageNotReadyException {
		String filename = "newFileToSave";
		File fileToSave = new File(BASE_PATH + "/newFileToSave");

		assert fileToSave.createNewFile();

		serviceToTest.copyFileToStorage(fileToSave.toPath());

		LocalFileStorageManager mockService = mock(LocalFileStorageManager.class);
		when(mockService.getSaveLocation()).thenReturn(new URI("mockedFilePath"));
		serviceToTest.moveFileToAnotherStorage(mockService, filename);

		verify(mockService, times(1)).copyFileToStorage(any());
	}

	@Test
	void testMoveAndDelete_RemovesFileInOldStorage() throws IOException, NameNotFoundException, URISyntaxException, StorageNotReadyException {
		String filename = "newFileToSave";
		File fileToSave = new File(BASE_PATH + "/newFileToSave");

		assert fileToSave.createNewFile();

		LocalFileStorageManager mockService = mock(LocalFileStorageManager.class);
		when(mockService.getSaveLocation()).thenReturn(new URI("mockedFilePath"));

		serviceToTest.copyFileToStorage(fileToSave.toPath());
		serviceToTest.moveFileToAnotherStorage(mockService, filename);

		assertEquals(0, serviceToTest.getAllSavedAsPaths().count());
	}

	@Test
	void testRemove_RemovesFile() throws IOException, NameNotFoundException, StorageNotReadyException {
		String filename = "newFileToSave";
		File fileToSave = new File(BASE_PATH + "/newFileToSave");

		assert fileToSave.createNewFile();

		serviceToTest.copyFileToStorage(fileToSave.toPath());
		serviceToTest.removeFileWithName(filename);

		assertEquals(0, serviceToTest.getAllSavedAsPaths().count());
	}

	@Test
	void testRemoveFileUsingGetAllSaved() throws IOException, NameNotFoundException, StorageNotReadyException {
		File fileToSave = new File(BASE_PATH + "/newFileToSave");

		assert fileToSave.createNewFile();

		serviceToTest.copyFileToStorage(fileToSave.toPath());
		Stream<Path> savedFiles = serviceToTest.getAllSavedAsPaths();

		Optional<Path> savedFile = savedFiles.findFirst();
		assertTrue(savedFile.isPresent());

		String savedFileName = String.valueOf(savedFile.get().getFileName());

		serviceToTest.removeFileWithName(savedFileName);

		assertEquals(0, serviceToTest.getAllSavedAsPaths().count());
	}
}