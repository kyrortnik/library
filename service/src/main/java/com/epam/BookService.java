package com.epam;

import com.epam.entity.*;
import com.epam.exception.ServiceException;

import java.util.List;

public interface BookService {

    Book findById(Long id) throws ServiceException;

    boolean create(BookRow bookRow) throws ServiceException;

    boolean update(BookRow bookRow) throws ServiceException;

    boolean delete(BookRow bookRow) throws ServiceException;

//    List<BookRow> getAll();

    Page<Book> getAll(Page<Book> pageRequest) throws ServiceException;

    List<Book> findBooksByIds(List<Reserve> reserves) throws ServiceException;

    List<Book> findBooksByOrder(Order order) throws ServiceException /*throws ServiceException*/;

    Page<Book> getPageByFilter(Page<Book> daoProductPageable) throws ServiceException;

//    Page<Book> get

}
