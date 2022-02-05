package com.butbetter.applicationservices.productapi.service;

import com.butbetter.applicationservices.productapi.model.Alcohol;
import com.butbetter.applicationservices.productapi.repository.ProductOperations;
import com.butbetter.applicationservices.productapi.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@CacheConfig(cacheNames = {"products"})
public class ProductService implements ProductOperations<Alcohol> {

    private final ProductRepository productRepository;
    private final ProductValidationService productValidationService;

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductRepository productRepository, ProductValidationService productValidationService) {
        this.productRepository = productRepository;
        this.productValidationService = productValidationService;
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public void save(Alcohol alcohol) throws IllegalArgumentException {
        this.productValidationService.checkProduct(alcohol);
        log.info("save " + alcohol.toString() + " to db");
        this.productRepository.save(alcohol);
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public Alcohol findById(UUID id) {
        log.info("Found a product over " + id);
        Optional<Alcohol> optionalProduct = this.productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    @Override
    @CacheEvict(value = "products", allEntries = true, key = "#id")
    public void deleteById(UUID id) {
        log.info("Delete a product with id: " + id);
        this.productRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "products")
    public Iterable<Alcohol> findAll() {
        log.info("Get cacheable request from findAll()");
        return this.productRepository.findAll();
    }

    @Override
    public void deleteAll() {
        this.productRepository.deleteAll();
    }

}
