package com.butbetter.applicationservices.storagerestapi.service;

import com.butbetter.applicationservices.storagerestapi.model.ProductInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Create Connection to Storage API
 */
@Service
public class StorageApiService {

    private static final Logger log = LoggerFactory.getLogger(StorageApiService.class);

    public static final String HEROKU_URL = "https://storageapi2.herokuapp.com/";
    public static final String LOCALHOST = "http://localhost:";
    public static final String PORT = "8080";
    public static final String STORAGE_URL = "/storage/v1/";
    public static final String PRODUCT_INFORMATION = "productInformation/";

    private final RestTemplate restTemplate;
    private final String default_url;

    public StorageApiService(String host, String port) {
        this.restTemplate = new RestTemplate();
        this.default_url = host + port + STORAGE_URL + PRODUCT_INFORMATION;
    }

    public StorageApiService(String port) {
        this.restTemplate = new RestTemplate();
        this.default_url = LOCALHOST + port + STORAGE_URL + PRODUCT_INFORMATION;
    }

    public StorageApiService() {
        this.restTemplate = new RestTemplate();
        this.default_url = LOCALHOST + PORT + STORAGE_URL + PRODUCT_INFORMATION;
    }

    /**
     * Get all ProductInformation from StorageAPI
     *
     * @return a list of {@link ProductInformation} or null
     */
    public List<ProductInformation> all() {
        ResponseEntity<ProductInformation[]> response = null;

        try {
            response = this.restTemplate.getForEntity(
                    this.default_url,
                    ProductInformation[].class
            );
        } catch (HttpClientErrorException | ResourceAccessException e) {
            log.error(e.getMessage());
            return null;
        }

        List<ProductInformation> informationList = Arrays.asList(Objects.requireNonNull(response.getBody()));
        log.info(Arrays.toString(informationList.toArray()));
        return informationList;
    }

    /**
     * Get one ProductInformation from StorageAPI
     *
     * @param id = type as UUID
     * @return {@link ProductInformation} or null
     */
    public ProductInformation one(@NotNull String id) {
        ResponseEntity<ProductInformation> response = null;
        try {
            response = this.restTemplate.getForEntity(
                    this.default_url + id,
                    ProductInformation.class
            );
        } catch (HttpClientErrorException | ResourceAccessException e) {
            log.error(e.getMessage());
            return null;
        }

        log.info(Objects.requireNonNull(response.getBody()).toString());
        return response.getBody();
    }

    /**
     * Add a new ProductionInformation at StorageAPI
     *
     * @param productInformation = {@link ProductInformation}
     */
    public void newProductInformation(@NotNull ProductInformation productInformation) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<ProductInformation> request = new HttpEntity<>(productInformation, headers);
        this.restTemplate.postForObject(this.default_url, request.getBody(), ProductInformation.class);
    }
}
