package com.butbetter.applicationservices.productapi.controller;

import com.butbetter.applicationservices.productapi.model.Alcohol;
import com.butbetter.applicationservices.productapi.service.AlcoholService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "v1/product")
public class AlcoholController {

    private static final Logger log = LoggerFactory.getLogger(AlcoholController.class);

    private final AlcoholService alcoholService;

    @Autowired
    public AlcoholController(AlcoholService alcoholService) {
        this.alcoholService = alcoholService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody final Alcohol alcohol) {
        log.info("Controller save product at redis");
        this.alcoholService.save(alcohol);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        log.info("Controller findByID product from redis");
        return ResponseEntity.ok().body(this.alcoholService.findById(id));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info("Controller findAll products");
        return ResponseEntity.ok().body(this.alcoholService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        this.alcoholService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalInputs(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
