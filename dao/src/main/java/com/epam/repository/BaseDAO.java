package com.epam.repository;


import com.epam.exception.DAOException;

public interface BaseDAO<T> {


    T findById(Long id) throws DAOException;

    boolean save(T t) throws DAOException;

    boolean delete(Long id) throws DAOException;

    boolean update(T t) throws DAOException;


}
