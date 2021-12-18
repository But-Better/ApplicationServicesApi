package com.butbetter.applicationservices.csvExporter;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.Collection;

interface CSVExporter<P> {
	void export(P object) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
	void export(Collection<P> objects) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
}
