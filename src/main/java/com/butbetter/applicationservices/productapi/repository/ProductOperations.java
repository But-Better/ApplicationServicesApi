package com.butbetter.applicationservices.productapi.repository;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface ProductOperations<P> {

    void save(@NotNull final P product);

    P findById(@NotNull final UUID uuid);

    void deleteById(@NotNull final UUID id);

    Iterable<P> findAll();

    void deleteAll();

}
