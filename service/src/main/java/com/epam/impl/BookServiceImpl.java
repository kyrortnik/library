package com.epam.impl;

import com.epam.entity.Book;
import com.epam.entity.Reserve;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.BookDAO;
import com.epam.repository.DAOFactory;
import com.epam.BookService;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {

    private static final BookDAO bookDAO = DAOFactory.getInstance().createBookDAO();

    @Override
    public boolean create(Book book) {
        return bookDAO.save(book);
    }

    @Override
    public boolean update(Book book) {
        return bookDAO.update(book);
    }

    @Override
    public boolean delete(Book book) {
        return bookDAO.delete(book);
    }

    @Override
    public List<Book> getAll() {
        return bookDAO.getAll();
    }

    @Override
    public Book findById(Long id) {
        return bookDAO.getById(id);
    }

    @Override
    public List<Book> findBooksByIds(List<Reserve> reserves) throws ServiceException {
        try{
            List<Book> books = new ArrayList<>();
            Book book;
            for (Reserve reserve : reserves){
                book = bookDAO.getById(reserve.getProductId());
                books.add(book);
            }
            return books;
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }
}
