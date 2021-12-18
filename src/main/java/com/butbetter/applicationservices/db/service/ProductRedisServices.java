package com.butbetter.applicationservices.db.service;

import com.butbetter.applicationservices.db.model.ProductRedis;
import com.butbetter.applicationservices.db.repository.ProductRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductRedisServices implements ProductRedisRepository {

    private static final String PRODUCT_CACHE = "ProductRedis";
    private static final Logger log = LoggerFactory.getLogger(ProductRedisServices.class);

    @Autowired
    private RedisTemplate<String, ProductRedis> redisTemplate;
    private HashOperations<String, UUID, ProductRedis> hashOperations;

    @PostConstruct
    private void intializeHashOperations() {
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    @Override
    public void save(ProductRedis productRedis) {
        log.info("Save add " + PRODUCT_CACHE + " id " + productRedis.getUuid() + " obj " + productRedis);
        this.hashOperations.put(PRODUCT_CACHE, productRedis.getUuid(), productRedis);
    }

    @Override
    public ProductRedis findById(UUID uuid) {
        log.info("findById " + PRODUCT_CACHE + " id " + uuid);
        return this.hashOperations.get(PRODUCT_CACHE, uuid);
    }

    @Override
    public Map<UUID, ProductRedis> findAll() {
        log.info("findAll " + this.hashOperations.entries(PRODUCT_CACHE));
        return this.hashOperations.entries(PRODUCT_CACHE);
    }

    @Override
    public void delete(UUID uuid) {
        log.info("Delete id" + uuid);
        this.hashOperations.delete(PRODUCT_CACHE, uuid);
    }
}
