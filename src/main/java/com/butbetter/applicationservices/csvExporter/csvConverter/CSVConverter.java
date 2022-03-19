package com.butbetter.applicationservices.csvExporter.csvConverter;

import com.butbetter.applicationservices.storagerestapi.model.ProductInformation;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVConverter {

	private final CSVCollector collector;

	public CSVConverter(CSVCollector collector) {
		this.collector = collector;
	}

	@Autowired
	public CSVConverter() {
		this.collector = new CSVCollector();
	}

	public String convertList(List<ProductInformation> listOfObjects) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
		listToCsv(listOfObjects, this.collector);

		return extractCsvDataFromCollection();
	}

	private void listToCsv(List<ProductInformation> listOfObjects, CSVCollector collector) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(collector).build();

		for(ProductInformation info: listOfObjects) {
			beanToCsv.write(info);
		}

		collector.close();
	}

	public String convertSingle(ProductInformation object) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
		objectToCsv(object, this.collector);

		return extractCsvDataFromCollection();
	}

	private void objectToCsv(ProductInformation object, CSVCollector collector) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
		StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(collector).build();
		beanToCsv.write(object);
		collector.close();
	}

	private String extractCsvDataFromCollection() throws IOException {
		checkErrorInCollector(this.collector);

		List<String> data = new ArrayList<>(this.collector.getCsvData());

		this.collector.reset();

		return putCollectionOfStringsIntoSingleString(data);
	}

	private void checkErrorInCollector(CSVCollector collector) throws IOException {
		if (collector.checkError()) {
			throw collector.getException();
		}
	}

	private String putCollectionOfStringsIntoSingleString(List<String> data) {
		StringBuilder builder = new StringBuilder();

		data.forEach(l -> builder
				.append(l)
				.append(System.lineSeparator())
		);

		return builder.toString();
	}
}
