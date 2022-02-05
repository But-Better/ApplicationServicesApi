package com.butbetter.applicationservices.productapi.service;

import com.butbetter.applicationservices.faker.Faker;
import com.butbetter.applicationservices.productapi.model.Alcohol;
import com.butbetter.applicationservices.productapi.repository.AlcoholRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableCaching
public class AlcoholServiceTest {

    @Autowired
    private AlcoholService alcoholService;

    @Autowired
    private AlcoholRepository alcoholRepository;

    private final Faker faker = new Faker();

    @Test
    void saveAProduct() {
        alcoholService.save(faker.getAlcohol().AbsolutVodka);
        assertThat(alcoholRepository.count()).isEqualTo(1);
    }

    @Test
    @Disabled
    void saveAProductAndCachingAfterARemovedProduct() {
        if (alcoholService.findAll().iterator().hasNext()) {
            alcoholService.deleteAll();
        }
        alcoholService.save(faker.getAlcohol().MaikäferFlugzeugBenzin);
        alcoholService.save(faker.getAlcohol().Frankfurter);
        alcoholService.save(faker.getAlcohol().Corona);

        Iterable<Alcohol> productIterable = alcoholService.findAll();
        Alcohol alcohol = productIterable.iterator().next();

        System.out.println(alcohol.getUuid());

        //productService.deleteById(product.getUuid());

        assertThat(alcoholRepository.count()).isEqualTo(3);
    }
}
