package com.epam.repository;


import com.epam.exception.DAOException;

import java.util.List;

/**
 * Base interface for all Data Access Objects
 * */
/**
 * TODO add DAOException to methods
* */
public interface AbstractDAO<T> {

    T get(T t) throws DAOException;

    T getById(Long id);

    List<T> getAll();

    boolean save(T t);

    boolean delete(T t);

    boolean update(T t);


}
