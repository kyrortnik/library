package com.epam.repository;


import com.epam.exception.DAOException;

public interface BaseDAO<T> {


    /**
     * Finds an entity by its id
     *
     * @param id, to find entity by
     * @return entity which was requested
     * @throws DAOException throws DAOException
     */
    T findById(Long id) throws DAOException;

    /**
     * Saves an entity in database
     *
     * @param t, entity to save
     * @return true if saved successfully, false vice versa
     * @throws DAOException throws DAOException
     */
    boolean save(T t) throws DAOException;

    /**
     * Deletes requested entity
     *
     * @param id to delete entity by
     * @return true if deleted successfully, false vice versa
     * @throws DAOException throws DAOException
     */
    boolean delete(Long id) throws DAOException;

    /**
     * Updated entity
     *
     * @param t, entity with new values in fields
     * @return true if updated successfully, false vice versa
     * @throws DAOException throws DAOException
     */
    boolean update(T t) throws DAOException;


}
