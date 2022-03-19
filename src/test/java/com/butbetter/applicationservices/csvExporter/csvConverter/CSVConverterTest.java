package com.butbetter.applicationservices.csvExporter.csvConverter;

import com.butbetter.applicationservices.storagerestapi.model.Address;
import com.butbetter.applicationservices.storagerestapi.model.ProductInformation;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVConverterTest {

    private CSVConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CSVConverter();
    }

    @SneakyThrows
    @Test
    void convertSingle() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        Address address = new Address("name", "companyName", "street", "city", "12345", "country");
        address.setId(UUID.randomUUID().toString());
        String dateTime = OffsetDateTime.now().toString();
        ProductInformation testInformation = new ProductInformation(
                0,
                address,
                dateTime
        );

        String uuid = UUID.randomUUID().toString();
        testInformation.setUuid(uuid);

        String csv = converter.convertSingle(testInformation);

        System.out.println(csv);

        String expectedCsv = "\"ADDRESS\"\n"+
                "\"AMOUNT\"\n"+
                "\"DELIVERYTIME\"\n"+
                "\"UUID\"\n\""+
                address +"\"\n"+
                "\"0\"\n\""+
                dateTime+"\"\n"+
                "\"" + uuid + "\"\n";

        assertEquals(csv, expectedCsv);
    }
}