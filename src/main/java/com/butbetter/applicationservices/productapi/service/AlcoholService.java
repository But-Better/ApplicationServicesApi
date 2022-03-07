package com.butbetter.applicationservices.productapi.service;

import com.butbetter.applicationservices.productapi.model.Alcohol;
import com.butbetter.applicationservices.productapi.repository.AlcoholOperations;
import com.butbetter.applicationservices.productapi.repository.AlcoholRepository;
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
@CacheConfig(cacheNames = {"alcohol"})
public class AlcoholService implements AlcoholOperations<Alcohol> {

    private final AlcoholRepository alcoholRepository;
    private final AlcoholValidationService alcoholValidationService;

    private final Logger log = LoggerFactory.getLogger(AlcoholService.class);

    @Autowired
    public AlcoholService(AlcoholRepository alcoholRepository, AlcoholValidationService alcoholValidationService) {
        this.alcoholRepository = alcoholRepository;
        this.alcoholValidationService = alcoholValidationService;
    }

    @Override
    @CacheEvict(value = "alcohol", allEntries = true)
    public void saveAlcohol(Alcohol alcohol) throws IllegalArgumentException, NullPointerException {
        this.alcoholValidationService.checkProduct(alcohol);
        log.info("save " + alcohol.toString() + " to db");
        this.alcoholRepository.save(alcohol);
    }

    @Override
    @Cacheable(value = "alcohol", key = "#id")
    public Alcohol findAlcoholById(UUID id) {
        log.info("Found a product over " + id);
        Optional<Alcohol> optionalProduct = this.alcoholRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    @Override
    @CacheEvict(value = "alcohol", allEntries = true, key = "#id")
    public void deleteAlcoholById(UUID id) {
        log.info("Delete a product with id: " + id);
        this.alcoholRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "alcohol")
    public Iterable<Alcohol> findAllAlcohol() {
        log.info("Get cacheable request from findAll()");
        return this.alcoholRepository.findAll();
    }

    @Override
    public void deleteAllAlcohol() {
        this.alcoholRepository.deleteAll();
    }

}
