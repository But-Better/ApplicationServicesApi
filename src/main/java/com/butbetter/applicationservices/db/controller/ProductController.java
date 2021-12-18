package com.butbetter.applicationservices.db.controller;

import com.butbetter.applicationservices.db.model.Product;
import com.butbetter.applicationservices.db.model.ProductRedis;
import com.butbetter.applicationservices.db.service.ProductRedisServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "v1/redis/product")
public class ProductController{
    private static final Logger log = LoggerFactory.getLogger(Product.class);

    private final ProductRedisServices productRedisServices;

    @Autowired
    public ProductController(ProductRedisServices productRedisServices) {
        this.productRedisServices = productRedisServices;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody final ProductRedis productRedis) {
        log.info("Controller save product at redis");
        this.productRedisServices.save(productRedis);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        log.info("Controller findByID product from redis");
        return ResponseEntity.ok().body(this.productRedisServices.findById(id));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info("Controller findAll products");
        return ResponseEntity.ok().body(this.productRedisServices.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        this.productRedisServices.delete(id);
        return ResponseEntity.ok().build();
    }
}
