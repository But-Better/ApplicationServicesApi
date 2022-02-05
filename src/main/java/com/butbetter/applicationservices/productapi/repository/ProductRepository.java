package com.butbetter.applicationservices.productapi.repository;

import com.butbetter.applicationservices.productapi.model.Alcohol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Alcohol, UUID> {
}
