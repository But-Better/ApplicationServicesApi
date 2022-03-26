package com.butbetter.applicationservices.productapi.repository;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface AlcoholOperations<P> {

    void saveAlcohol(@NotNull final P product);

    P findAlcoholById(@NotNull final UUID uuid);

    void deleteAlcoholById(@NotNull final UUID id);

    Iterable<P> findAllAlcohol();

    void deleteAllAlcohol();

}
