package com.butbetter.applicationservices.db.service;

import com.butbetter.applicationservices.ProductFaker;
import com.butbetter.applicationservices.db.model.Product;
import com.butbetter.applicationservices.db.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableCaching
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveAProduct() {
        productService.save(ProductFaker.AbsolutVodka);
        assertThat(productRepository.count()).isEqualTo(1);
    }

    @Test
    @Disabled
    void saveAProductAndCachingAfterARemovedProduct() {
        if (productService.findAll().iterator().hasNext()) {
            productService.deleteAll();
        }
        productService.save(ProductFaker.MaikäferFlugzeugBenzin);
        productService.save(ProductFaker.Frankfurter);
        productService.save(ProductFaker.Corona);

        Iterable<Product> productIterable = productService.findAll();
        Product product = productIterable.iterator().next();

        System.out.println(product.getUuid());

        //productService.deleteById(product.getUuid());

        assertThat(productRepository.count()).isEqualTo(3);
    }
}
