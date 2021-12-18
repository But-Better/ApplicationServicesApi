package com.butbetter.applicationservices.db.service;

import com.butbetter.applicationservices.db.model.Product;
import com.butbetter.applicationservices.db.repository.ProductOperations;
import com.butbetter.applicationservices.db.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService implements ProductOperations<Product> {

    private ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(ProductRedisServices.class);

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void save(Product product) {
        log.info(ProductService.class.getName() + " save");
        this.productRepository.save(product);
    }

    @Override
    public Product findById(UUID uuid) {
        Optional<Product> optionalProduct = this.productRepository.findById(uuid);
        log.info(ProductService.class.getName() + " findById");
        return optionalProduct.orElse(null);
    }

    @Override
    public void delete(Product product) {
        log.info(ProductService.class.getName() + " delete");
        this.productRepository.delete(product);
    }

    @Override
    public Iterable<Product> findAll() {
        log.info(ProductService.class.getName() + " findAll");
        return this.productRepository.findAll();
    }

}
