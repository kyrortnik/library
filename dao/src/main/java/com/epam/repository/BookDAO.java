package com.epam.repository;

import com.epam.entity.BookRow;
import com.epam.entity.Pageable;
import com.epam.exception.DAOException;

public interface BookDAO extends AbstractDAO<BookRow> {

     Pageable<BookRow> findPageByFilter(Pageable<BookRow> daoProductPageable) throws DAOException;

//     Pageable<BookRow> findPageByParameter(Pageable<BookRow> daoProductPageable,Object whereParameter) throws DAOException;

}
