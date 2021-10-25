package com.epam;

import com.epam.entity.Book;

import java.util.List;

public interface BookService {

    Book findById(Long id);

    boolean create(Book book);

    boolean update(Book book);

    boolean delete(Book book);

    List<Book> getAll();

}
