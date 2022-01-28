package com.butbetter.applicationservices.csvExporter;

import com.butbetter.applicationservices.csvExporter.apiRequestHandler.RemoteFileService;
import com.butbetter.applicationservices.csvExporter.properties.CSVExporterProperties;
import com.butbetter.applicationservices.csvExporter.storageManager.ApiStorageManager;
import com.butbetter.applicationservices.csvExporter.storageManager.LocalFileStorageManager;
import com.butbetter.applicationservices.csvExporter.storageManager.StorageManager;
import com.butbetter.applicationservices.csvExporter.storageManager.StorageNotReadyException;
import com.butbetter.applicationservices.storagerestapi.model.ProductInformation;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.stereotype.Service;

import javax.naming.NameAlreadyBoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class ProductInformationCSVFileExporter implements CSVExporter<ProductInformation> {

	private final CSVExporterProperties properties;

	private final StorageManager uploader;

	@Autowired
	public ProductInformationCSVFileExporter(CSVExporterProperties properties) {
		this.properties = properties;

		LocalFileStorageManager tmpManager = new LocalFileStorageManager(Path.of(properties.getSaveLocation()));
		RemoteFileService remoteFileService = new RemoteFileService(properties.getStorageUrl());

		this.uploader = new ApiStorageManager(remoteFileService, tmpManager);
	}

	public ProductInformationCSVFileExporter(CSVExporterProperties properties, StorageManager uploader) {
		this.properties = properties;
		this.uploader = uploader;
	}

	public ProductInformationCSVFileExporter(CSVExporterProperties properties, LocalFileStorageManager tmpManager,
	                                         RemoteFileService remoteManager) {
		this.properties = properties;
		this.uploader = new ApiStorageManager(remoteManager, tmpManager);
	}

	@Override
	public void export(ProductInformation object) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, NameAlreadyBoundException, StorageNotReadyException {
		CSVCollector writer = new CSVCollector();
		StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

		beanToCsv.write(object);

		writer.close();

		List<String> data = writer.getCsvData();

		uploader.saveContentToFile(object.getUuid(), data.stream());
	}

	@Override
	public void export(Collection<ProductInformation> objects) throws IOException, CsvRequiredFieldEmptyException,
			CsvDataTypeMismatchException, NameAlreadyBoundException, StorageNotReadyException {

		CSVCollector writer = new CSVCollector();
		StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

		for(ProductInformation info: objects) {
			beanToCsv.write(info);
		}

		writer.close();

		List<String> data = writer.getCsvData();

		uploader.saveContentToFile(objects.stream().findFirst().get().getUuid(), data.stream());
	}

}
