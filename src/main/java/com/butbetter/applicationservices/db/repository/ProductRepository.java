package com.butbetter.applicationservices.db.repository;

import com.butbetter.applicationservices.db.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
}
