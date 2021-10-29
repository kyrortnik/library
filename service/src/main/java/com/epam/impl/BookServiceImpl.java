package com.epam.impl;

import com.epam.entity.*;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.BookDAO;
import com.epam.repository.DAOFactory;
import com.epam.BookService;
import com.epam.entity.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookServiceImpl implements BookService {

    private static final BookDAO bookDAO = DAOFactory.getInstance().createBookDAO();

    @Override
    public boolean create(BookRow bookRow) {
        return bookDAO.save(bookRow);
    }

    @Override
    public boolean update(BookRow bookRow) {
        return bookDAO.update(bookRow);
    }

    @Override
    public boolean delete(BookRow bookRow) {
        return bookDAO.delete(bookRow);
    }

   /* @Override
    public List<Book> getAll() {
        return bookDAO.getAll();
    }*/

    @Override
    public BookRow findById(Long id) {
        return bookDAO.getById(id);
    }

    @Override
    public List<BookRow> findBooksByIds(List<Reserve> reserves) throws ServiceException {
        try{
            List<BookRow> bookRows = new ArrayList<>();
            BookRow bookRow;
            for (Reserve reserve : reserves){
                bookRow = bookDAO.getById(reserve.getProductId());
                bookRows.add(bookRow);
            }
            return bookRows;
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public Page<Book> getAll(Page<Book> pageableRequest) {
        try {
            // validation
            // ...

            // prepare data
             Pageable<BookRow> daoProductPageable = convertToPageableBook(pageableRequest);
            // process data
             Pageable<BookRow> productRowsPageable = bookDAO.findPageByFilter(daoProductPageable);

            // return
            return convertToServicePage(productRowsPageable);
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }
    }


    private Page<Book> convertToServicePage(Pageable<BookRow> productRowsPageable) {
        Page<Book> page = new Page<>();
       page.setPageNumber(productRowsPageable.getPageNumber());
        page.setLimit(productRowsPageable.getLimit());
        page.setTotalElements(productRowsPageable.getTotalElements());
        page.setElements(convertToBooks(productRowsPageable.getElements()));
        page.setFilter(convertToBook(productRowsPageable.getFilter()));
        page.setSortBy(productRowsPageable.getSortBy());
        page.setDirection(productRowsPageable.getDirection());
        return page;
    }

    private List<Book> convertToBooks( List<BookRow> rows) {
        List<Book> list = new ArrayList<>();
        for (BookRow row : rows) {
            list.add(convertToBook(row));
        }
        return list;
    }

    private Book convertToBook(final BookRow row) {
        long id;
        String title;
        String author;
        int publishYear;
        Book book = null;
        if (Objects.nonNull(row)) {

            id = row.getId();
            title = row.getTitle();
           author = row.getAuthor();
           publishYear = row.getPublishingYear();
            book = new Book(id,title,author, publishYear);
        }
        return book;
    }


    private Pageable<BookRow> convertToPageableBook(Page<Book> pageableRequest) {
        final Pageable<BookRow> pageable = new Pageable<>();
        pageable.setPageNumber(pageableRequest.getPageNumber());
        pageable.setLimit(pageableRequest.getLimit());
        pageable.setTotalElements(pageableRequest.getTotalElements());
        pageable.setElements(convertToProductRows(pageableRequest.getElements()));
        pageable.setFilter(convertToProductRow(pageableRequest.getFilter()));
        pageable.setSortBy(pageableRequest.getSortBy());
        pageable.setDirection(pageableRequest.getDirection());
        return pageable;
    }


    private List<BookRow> convertToProductRows( List<Book> elements) {
        final List<BookRow> list = new ArrayList<>();
        for (Book book : elements) {
            list.add(convertToProductRow(book));
        }
        return list;
    }


    private BookRow convertToProductRow( Book bookRow) {
        BookRow productRow = null;
        if (Objects.nonNull(bookRow)) {
            productRow = new BookRow();
            productRow.setId(bookRow.getId());
            productRow.setTitle(bookRow.getTitle());
            productRow.setAuthor(bookRow.getAuthor());
            productRow.setPublisher(bookRow.getPublisher());
        }
        return productRow;
    }



}
