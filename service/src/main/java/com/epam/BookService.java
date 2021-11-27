package com.epam;

import com.epam.entity.Book;
import com.epam.entity.Page;
import com.epam.exception.ServiceException;

public interface BookService {


    Book findById(Long id) throws ServiceException;

    boolean create(Book book) throws ServiceException;

    boolean update(Book book) throws ServiceException;

    boolean delete(Long id) throws ServiceException;

    Page<Book> getBooksPage(Page<Book> pageRequest) throws ServiceException;

    Page<Book> getOrderedBooksPage(Page<Book> pageOrder,Long userId) throws ServiceException;

    Page<Book> getReservedBooksPage(Page<Book> pageRequest, Long userId) throws ServiceException;





}
