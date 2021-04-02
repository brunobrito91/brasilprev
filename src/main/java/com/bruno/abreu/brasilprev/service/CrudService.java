package com.bruno.abreu.brasilprev.service;

import java.util.List;

public interface CrudService<T> {

    T create(T object);

    T read(Long id);

    T update(T object);

    void delete(Long id);

    List<T> findAll();
}
