package com.butbetter.applicationservices.storagerestapi.repository;

import com.butbetter.applicationservices.storagerestapi.model.ProductInformation;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface StorageOperation {

    List<ProductInformation> all();

    ProductInformation one(@NotNull String uuid);

    void newProductInformation(@NotNull ProductInformation productInformation);
}
