package com.butbetter.applicationservices.csvExporter;

import com.butbetter.applicationservices.csvExporter.apiRequestHandler.RemoteFileService;
import com.butbetter.applicationservices.csvExporter.csvConverter.CSVConverter;
import com.butbetter.applicationservices.csvExporter.properties.CSVExporterProperties;
import com.butbetter.applicationservices.csvExporter.storageManager.LocalFileStorageManager;
import com.butbetter.applicationservices.csvExporter.storageManager.StorageNotReadyException;
import com.butbetter.applicationservices.storagerestapi.model.Address;
import com.butbetter.applicationservices.storagerestapi.model.ProductInformation;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.NameAlreadyBoundException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductInformationCSVFileExporterTest {

	private static final String BASE_PATH = "src/test/resources/testStorage/storageDirectory";

	private CSVExporterProperties properties;
	private CSVConverter mockedConverter;
	private RemoteFileService mockedRemoteManager;
	private LocalFileStorageManager tmpManager;

	private ProductInformationCSVFileExporter exporter;

	@SneakyThrows
	@BeforeEach
	void setUp() throws MalformedURLException {
		properties = mock(CSVExporterProperties.class);
		when(properties.getSaveLocation()).thenReturn(BASE_PATH);

		mockedRemoteManager = mock(RemoteFileService.class);
		when(mockedRemoteManager.isUp()).thenReturn(false);
		when(mockedRemoteManager.getStorageApiUrl()).thenReturn(new URL("https://localhost"));

		when(mockedRemoteManager.uploadFile(any())).thenReturn(new ResponseEntity<>(HttpStatus.ACCEPTED));

		tmpManager = mock(LocalFileStorageManager.class);
		when(tmpManager.ready()).thenReturn(true);

		mockedConverter = mock(CSVConverter.class);

		exporter = new ProductInformationCSVFileExporter(properties, tmpManager, mockedRemoteManager, mockedConverter);
	}

	@SneakyThrows
	@Test
	void export() {
		ProductInformation testInformation = new ProductInformation(0, new Address(), OffsetDateTime.now().toString());
		testInformation.setUuid(UUID.randomUUID().toString());

		exporter.export(testInformation);

		verify(mockedConverter, times(1)).convertSingle(testInformation);
		verify(mockedRemoteManager, times(1)).uploadFile(any());
	}
}