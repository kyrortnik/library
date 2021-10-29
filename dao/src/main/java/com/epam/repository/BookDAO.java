package com.epam.repository;

import com.epam.entity.BookRow;
import com.epam.entity.Pageable;
import com.epam.entity.ProductRow;
import com.epam.exception.DAOException;

public interface BookDAO extends AbstractDAO<BookRow> {

// List<Long> getReservedBooksIds(Long id);


     Pageable<BookRow> findPageByFilter(Pageable<BookRow> daoProductPageable) throws DAOException;

}
