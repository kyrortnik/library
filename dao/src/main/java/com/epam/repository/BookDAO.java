package com.epam.repository;

import com.epam.entity.BookRow;
import com.epam.entity.Pageable;
import com.epam.exception.DAOException;

public interface BookDAO extends BaseDAO<BookRow> {

     Pageable<BookRow> findPageWithParameters(Pageable<BookRow> daoProductPageable) throws DAOException;

}
