package com.epam.repository;


import com.epam.exception.DAOException;

import java.util.List;

public interface BaseDAO<T> {

    T find(T t) throws DAOException;

    T findById(Long id) throws DAOException;

    List<T> getAll() throws DAOException;

    boolean save(T t) throws DAOException;

    boolean delete(Long id) throws DAOException;

    boolean update(T t) throws DAOException;



}
