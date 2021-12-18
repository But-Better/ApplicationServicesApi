package com.butbetter.applicationservices.repository;

import com.butbetter.applicationservices.model.ProductRedis;

import java.util.Map;
import java.util.UUID;

public interface ProductRedisRepository {

    void save(final ProductRedis productRedis);

    ProductRedis findById(final UUID uuid);

    Map<UUID, ProductRedis> findAll();

    void delete(final UUID uuid);
}
