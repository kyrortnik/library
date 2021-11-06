package com.epam;

import com.epam.entity.*;

import java.util.List;

public interface BookService {

    Book findById(Long id);

    boolean create(BookRow bookRow);

    boolean update(BookRow bookRow);

    boolean delete(BookRow bookRow);

//    List<BookRow> getAll();

    Page<Book> getAll(Page<Book> pageRequest);

    List<Book> findBooksByIds(List<Reserve> reserves);

    List<Book> findBooksByOrder(Order order);

    Page<Book> getPageByFilter(Page<Book> daoProductPageable);

//    Page<Book> get

}
