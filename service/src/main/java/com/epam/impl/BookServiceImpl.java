package com.epam.impl;

import com.epam.entity.Book;
import com.epam.repository.BookDAO;
import com.epam.repository.DAOFactory;
import com.epam.BookService;

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
}
