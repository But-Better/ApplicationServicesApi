package com.butbetter.applicationservices.db.controller;

import com.butbetter.applicationservices.db.model.Product;
import com.butbetter.applicationservices.db.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "v1/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody final Product product) {
        log.info("Controller save product at redis");
        this.productService.save(product);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        log.info("Controller findByID product from redis");
        return ResponseEntity.ok().body(this.productService.findById(id));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info("Controller findAll products");
        return ResponseEntity.ok().body(this.productService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        this.productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalInputs() {
        return ResponseEntity.badRequest().build();
    }
}
