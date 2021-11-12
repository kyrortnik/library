package com.epam;

import com.epam.entity.*;
import com.epam.exception.ServiceException;

import java.util.List;

public interface BookService {

    Book findById(Long id) throws ServiceException;

    boolean create(Book book) throws ServiceException;

    boolean update(Book book) throws ServiceException;

    boolean delete(Book book) throws ServiceException;

//    List<BookRow> getAll();

    Page<Book> getAll(Page<Book> pageRequest) throws ServiceException;

    List<Book> findBooksByIds(List<Reserve> reserves) throws ServiceException;

    List<Book> findBooksByOrder(Order order) throws ServiceException /*throws ServiceException*/;

    Page<Book> getPageByFilter(Page<Book> daoProductPageable) throws ServiceException;

//    Page<Book> get

}
