package com.epam;

import com.epam.entity.Book;
import com.epam.entity.BookRow;
import com.epam.entity.Page;
import com.epam.entity.Reserve;

import java.util.List;

public interface BookService {

    BookRow findById(Long id);

    boolean create(BookRow bookRow);

    boolean update(BookRow bookRow);

    boolean delete(BookRow bookRow);

//    List<BookRow> getAll();

    Page<Book> getAll(Page<Book> pageRequest);

    Page<Book> findBooksByIds(List<Reserve> reserves);

}
