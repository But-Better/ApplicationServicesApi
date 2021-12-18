package com.butbetter.applicationservices.db.repository;

import com.butbetter.applicationservices.db.model.ProductRedis;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

public interface ProductRedisRepository {

    /**
     * Save a {@link ProductRedis} obj
     *
     * @param productRedis = type of {@link ProductRedis}
     */
    void save(@NotNull final ProductRedis productRedis);

    /**
     * Same as {@link org.springframework.data.repository.CrudRepository}.findById(UUID id)
     *
     * @param uuid = type of  UUID
     * @return {@link ProductRedis}
     */
    ProductRedis findById(@NotNull final UUID uuid);

    /**
     * Same as {@link org.springframework.data.repository.CrudRepository}.findAll()
     *
     * @return all {@link ProductRedis}
     */
    Map<UUID, ProductRedis> findAll();

    /**
     * Same as {@link org.springframework.data.repository.CrudRepository}.delete()
     *
     * @param uuid = type of UUID
     */
    void delete(@NotNull final UUID uuid);
}
