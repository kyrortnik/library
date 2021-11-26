package com.epam;

import com.epam.entity.*;
import com.epam.exception.ServiceException;

import java.util.List;

public interface BookService {

    List<Book> getBooksFromOrder(Long userId) throws ServiceException;

    Book findById(Long id) throws ServiceException;

    boolean create(Book book) throws ServiceException;

    boolean update(Book book) throws ServiceException;

    boolean delete(Long id) throws ServiceException;

    Page<Book> getAll(Page<Book> pageRequest) throws ServiceException;

    List<Book> findBooksByIds(List<Reserve> reserves) throws ServiceException;

    Page<Book> getReservesPage(Page<Book> pageRequest, Long userId) throws ServiceException;

    List<Book> findBooksByOrder(Order order) throws ServiceException;

//    Page<Book> getPageByFilter(Page<Book> daoProductPageable) throws ServiceException;


}
