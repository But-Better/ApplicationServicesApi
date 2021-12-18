package com.butbetter.applicationservices.db.repository;

import com.butbetter.applicationservices.db.model.Producible;
import com.butbetter.applicationservices.db.model.ProductRedis;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

public interface ProductRedisRepository<P extends Producible> {

    void save(@NotNull final P product);

    P findById(@NotNull final UUID uuid);

    void delete(@NotNull final UUID uuid);

    Map<UUID, ProductRedis> findAll();
}
