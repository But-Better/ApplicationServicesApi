package com.butbetter.applicationservices.csvExporter;

import com.butbetter.applicationservices.csvExporter.apiRequestHandler.RemoteFileService;
import com.butbetter.applicationservices.csvExporter.properties.CSVExporterProperties;
import com.butbetter.applicationservices.csvExporter.storageManager.LocalFileStorageManager;
import com.butbetter.applicationservices.csvExporter.storageManager.StorageNotReadyException;
import com.butbetter.applicationservices.storagerestapi.model.Address;
import com.butbetter.applicationservices.storagerestapi.model.ProductInformation;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NameAlreadyBoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductInformationCSVFileExporterTest {

	private static final String BASE_PATH = "src/test/resources/testStorage/storageDirectory";

	private CSVExporterProperties properties;
	private RemoteFileService mockedRemoteManager;
	private LocalFileStorageManager tmpManager;

	private ProductInformationCSVFileExporter exporter;

	@BeforeEach
	void setUp() throws MalformedURLException {
		properties = mock(CSVExporterProperties.class);
		when(properties.getSaveLocation()).thenReturn(BASE_PATH);

		mockedRemoteManager = mock(RemoteFileService.class);
		when(mockedRemoteManager.isUp()).thenReturn(true);
		when(mockedRemoteManager.getStorageApiUrl()).thenReturn(new URL("https://localhost"));

		tmpManager = mock(LocalFileStorageManager.class);
		when(tmpManager.ready()).thenReturn(true);

		exporter = new ProductInformationCSVFileExporter(properties, tmpManager, mockedRemoteManager);
	}

	@Test
	void export() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, NameAlreadyBoundException, StorageNotReadyException {
		ProductInformation testInformation = new ProductInformation(0, new Address(), OffsetDateTime.now().toString());
		testInformation.setUuid(UUID.randomUUID().toString());

		exporter.export(testInformation);
	}
}