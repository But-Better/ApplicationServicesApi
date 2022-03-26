package com.butbetter.applicationservices.csvExporter.storageManager;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageManager {

    URI getSaveLocation() throws StorageNotReadyException;

    boolean ready();

    void saveContentToFile(String name, String content) throws NameAlreadyBoundException, IOException, StorageNotReadyException;

    void saveContentToFile(String name, Stream<String> content) throws NameAlreadyBoundException, IOException, StorageNotReadyException;

    void copyFileToStorage(Path file) throws WriteAbortedException, StorageNotReadyException, FileNotFoundException, NotActiveException;

    File getFileHandleWithName(String filename) throws NameNotFoundException, IOException, StorageNotReadyException;

    Stream<Path> getAllSavedAsPaths() throws IOException;

    void moveFileToAnotherStorage(StorageManager nextStorage, String name) throws NameNotFoundException, IOException, StorageNotReadyException;

    void removeFileWithName(String name) throws NameNotFoundException, IOException, StorageNotReadyException;
}
