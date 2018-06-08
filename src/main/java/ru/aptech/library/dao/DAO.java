package ru.aptech.library.dao;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DAO<T, PKType> {
    @Transactional
    List<T> find();

    @Transactional
    T find(PKType id);

    @Transactional
    PKType save(T o);

    @Transactional
    void update(T o);

    @Transactional
    void delete(T o);

}
