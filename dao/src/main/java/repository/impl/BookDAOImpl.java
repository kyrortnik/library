package repository.impl;

import entity.Book;
import repository.BookDAO;
import repository.ConnectionPool;

import java.util.List;

public class BookDAOImpl implements BookDAO {


    public BookDAOImpl(ConnectionPool connectionPool) {
    }

    @Override
    public Book getEntity(Book element) {
        return null;
    }

    @Override
    public Book getEntityById(Long id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public boolean saveEntity(Book element) {
        return false;
    }

    @Override
    public boolean deleteEntity(Book element) {
        return false;
    }

    @Override
    public boolean updateEntity(Book element) {
        return false;
    }
}
