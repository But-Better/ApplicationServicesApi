package com.butbetter.applicationservices.db.service;

import com.butbetter.applicationservices.Faker.Faker;
import com.butbetter.applicationservices.productapi.model.Product;
import com.butbetter.applicationservices.productapi.repository.ProductRepository;
import com.butbetter.applicationservices.productapi.service.ProductService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final Faker faker = new Faker();

    @Test
    void saveAProduct() {
        productService.save(faker.getProductFaker().AbsolutVodka);
        assertThat(productRepository.count()).isEqualTo(1);
    }

    @Test
    @Disabled
    void saveAProductAndCachingAfterARemovedProduct() {
        if (productService.findAll().iterator().hasNext()) {
            productService.deleteAll();
        }
        productService.save(faker.getProductFaker().MaikäferFlugzeugBenzin);
        productService.save(faker.getProductFaker().Frankfurter);
        productService.save(faker.getProductFaker().Corona);

        Iterable<Product> productIterable = productService.findAll();
        Product product = productIterable.iterator().next();

        System.out.println(product.getUuid());

        //productService.deleteById(product.getUuid());

        assertThat(productRepository.count()).isEqualTo(3);
    }
}
