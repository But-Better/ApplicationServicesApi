package com.butbetter.applicationservices.db.repository;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface ProductOperations<P> {

    void save(@NotNull final P product);

    P findById(@NotNull final UUID uuid);

    void delete(@NotNull final P product);

    Iterable<P> findAll();
}
