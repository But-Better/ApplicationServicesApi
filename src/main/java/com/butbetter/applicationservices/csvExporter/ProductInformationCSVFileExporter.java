package com.butbetter.applicationservices.csvExporter;

import com.butbetter.applicationservices.csvExporter.apiRequestHandler.RemoteFileService;
import com.butbetter.applicationservices.csvExporter.csvConverter.CSVConverter;
import com.butbetter.applicationservices.csvExporter.properties.CSVExporterProperties;
import com.butbetter.applicationservices.csvExporter.storageManager.ApiStorageManager;
import com.butbetter.applicationservices.csvExporter.storageManager.LocalFileStorageManager;
import com.butbetter.applicationservices.csvExporter.storageManager.StorageManager;
import com.butbetter.applicationservices.csvExporter.storageManager.StorageNotReadyException;
import com.butbetter.applicationservices.storagerestapi.model.ProductInformation;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NameAlreadyBoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class ProductInformationCSVFileExporter implements CSVExporter<ProductInformation> {

	private final CSVExporterProperties properties;

	private final CSVConverter converter;

	private final StorageManager uploader;

	@Autowired
	public ProductInformationCSVFileExporter(CSVExporterProperties properties, CSVConverter converter) {
		this.properties = properties;

		LocalFileStorageManager tmpManager = new LocalFileStorageManager(Path.of(properties.getSaveLocation()));
		RemoteFileService remoteFileService = new RemoteFileService(properties.getStorageUrl());

		this.uploader = new ApiStorageManager(remoteFileService, tmpManager);

		this.converter = converter;
	}

	public ProductInformationCSVFileExporter(CSVExporterProperties properties, StorageManager uploader) {
		this.converter = new CSVConverter();
		this.properties = properties;
		this.uploader = uploader;
	}

	public ProductInformationCSVFileExporter(CSVExporterProperties properties, LocalFileStorageManager tmpManager,
	                                         RemoteFileService remoteManager, CSVConverter converter) {
		this.converter = converter;
		this.properties = properties;
		this.uploader = new ApiStorageManager(remoteManager, tmpManager);
	}

	@Override
	public void export(ProductInformation object) throws IOException, StorageNotReadyException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
		String csv = converter.convertSingle(object);
		uploadCsvData(csv);
	}

	@Override
	public void export(Collection<ProductInformation> objects) throws IOException, StorageNotReadyException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
		String csv = converter.convertList(new ArrayList<>(objects));
		uploadCsvData(csv);
	}

	private void uploadCsvData(String csv) throws IOException, StorageNotReadyException {
		try {
			uploader.saveContentToFile(UUID.randomUUID().toString(), csv);
		} catch (NameAlreadyBoundException e) {
			uploadCsvData(csv);
		}
	}

}
