package com.butbetter.applicationservices.csvExporter;

import com.butbetter.applicationservices.csvExporter.storageManager.StorageNotReadyException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import javax.naming.NameAlreadyBoundException;
import java.io.IOException;
import java.util.Collection;

interface CSVExporter<P> {
    String export(P object) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, NameAlreadyBoundException, StorageNotReadyException;

    String export(Collection<P> objects) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, NameAlreadyBoundException, StorageNotReadyException;
}
