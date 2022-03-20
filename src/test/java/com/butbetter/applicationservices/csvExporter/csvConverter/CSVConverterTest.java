package com.butbetter.applicationservices.csvExporter.csvConverter;

import com.butbetter.applicationservices.storagerestapi.model.Address;
import com.butbetter.applicationservices.storagerestapi.model.ProductInformation;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVConverterTest {

    private CSVConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CSVConverter();
    }

    @SneakyThrows
    @Test
    void testConvertSingleGeneral() {
        ProductInformation testInformation = generateValidProductInformation();

        String csv = converter.convertSingle(testInformation);

        String expectedCsv = "\"ADDRESS\"\n"+
                "\"AMOUNT\"\n"+
                "\"DELIVERYTIME\"\n"+
                "\"UUID\"\n"+
                generateCSVStructure(testInformation);

        generateCSVStructure(testInformation);

        assertEquals(csv, expectedCsv);
    }

    @SneakyThrows
    @Test
    void testConvertMultipleGeneral() {

        ArrayList<ProductInformation> testInformationCollection = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            testInformationCollection.add(generateValidProductInformation());
        }

        String csv = converter.convertList(testInformationCollection);

        String expectedCsv = "\"ADDRESS\"\n"+
                "\"AMOUNT\"\n"+
                "\"DELIVERYTIME\"\n"+
                "\"UUID\"\n"+
                testInformationCollection.stream()
                        .map(this::generateCSVStructure)
                        .collect(Collectors.joining());

        assertEquals(csv, expectedCsv);
    }

    private String generateCSVStructure(ProductInformation testInformation) {
        return  "\""+ testInformation.getAddress()      +"\"\n"+
                "\""+ testInformation.getAmount()       +"\"\n"+
                "\""+ testInformation.getDeliveryTime() +"\"\n"+
                "\""+ testInformation.getUuid()         +"\"\n";
    }

    private ProductInformation generateValidProductInformation() {
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
        return testInformation;
    }
}