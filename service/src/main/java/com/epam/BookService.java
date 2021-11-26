package com.epam;

import com.epam.entity.Book;
import com.epam.entity.Page;
import com.epam.exception.ServiceException;

import java.util.List;

public interface BookService {

    //TODO replace with pageable request
    List<Book> getBooksFromOrder(Long userId) throws ServiceException;

    Book findById(Long id) throws ServiceException;

    boolean create(Book book) throws ServiceException;

    boolean update(Book book) throws ServiceException;

    boolean delete(Long id) throws ServiceException;

    Page<Book> getBooksPage(Page<Book> pageRequest) throws ServiceException;

    Page<Book> getReservedBooksPage(Page<Book> pageRequest, Long userId) throws ServiceException;





}
