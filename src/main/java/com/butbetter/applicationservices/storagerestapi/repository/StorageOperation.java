package com.butbetter.applicationservices.storagerestapi.repository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface StorageOperation<P> {

    List<P> findAllProductInformation();

    P findProductInformationById(@NotNull String uuid);

    void saveProductInformation(@NotNull P productInformation);
}
