package com.epam.impl;

import com.epam.entity.*;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.BookDAO;
import com.epam.BookService;
import com.epam.entity.Page;
import com.epam.validator.ServiceValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BookServiceImpl implements BookService {


    private final BookDAO bookDAO;
    private final ServiceValidator serviceValidator;

    public BookServiceImpl(BookDAO bookDAO, ServiceValidator serviceValidator) {
        this.serviceValidator = serviceValidator;
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean create(Book book) throws ServiceException {
        serviceValidator.validation(book);
        try {
            BookRow bookRow = convertToBookRow(book);
            return bookDAO.save(bookRow);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean update(Book book) throws ServiceException {
        serviceValidator.validation(book);
        try {

            BookRow bookRow = convertToBookRow(book);
            return bookDAO.update(bookRow);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return bookDAO.delete(id);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }


    @Override
    public Book findById(Long id) throws ServiceException {
        serviceValidator.validation(id);
        try {
            return convertToBook(bookDAO.findById(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }


    @Override
    public Page<Book> getBooksPage(Page<Book> page) throws ServiceException {
        serviceValidator.validation(page);
        try {
            Pageable<BookRow> bookRowPageable = convertToPageableBook(page);
            Pageable<BookRow> bookRowsPageable = bookDAO.getBookRowsPage(bookRowPageable);
            return convertToServicePage(bookRowsPageable);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public Page<Book> getReservedBooksPage(Page<Book> pageReserve, Long userId) throws ServiceException {
        serviceValidator.validation(userId);
        serviceValidator.validation(pageReserve);
        try {
            Pageable<BookRow> pageableBookRowsRequest = convertToPageableBook(pageReserve);
            Pageable<BookRow> foundPageable = bookDAO.getReservedBookRowsPage(pageableBookRowsRequest, userId);
            return convertToServicePage(foundPageable);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<Book> getOrderedBooksPage(Page<Book> pageOrder, Long userId) throws ServiceException {
        serviceValidator.validation(userId);
        serviceValidator.validation(pageOrder);
        try {
            Pageable<BookRow> pageableBookRowsRequest = convertToPageableBook(pageOrder);
            Pageable<BookRow> foundPageable = bookDAO.getOrderedBookRowsPage(pageableBookRowsRequest, userId);
            return convertToServicePage(foundPageable);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private Page<Book> convertToServicePage(Pageable<BookRow> bookRowPageable) {
        Page<Book> page = new Page<>();
        page.setPageNumber(bookRowPageable.getPageNumber());
        page.setLimit(bookRowPageable.getLimit());
        page.setTotalElements(bookRowPageable.getTotalElements());
        page.setElements(convertToBooks(bookRowPageable.getElements()));
        page.setSortBy(bookRowPageable.getSortBy());
        page.setDirection(bookRowPageable.getDirection());
        return page;
    }

    private List<Book> convertToBooks(List<BookRow> rows) {
        List<Book> list = new ArrayList<>();
        for (BookRow row : rows) {
            list.add(convertToBook(row));
        }
        return list;
    }

    private Book convertToBook(final BookRow row) {
        Book book = new Book();
        if (Objects.nonNull(row)) {
            book.setId(row.getId());
            book.setAuthor(row.getAuthor());
            book.setTitle(row.getTitle());
            book.setPublishingYear(row.getPublishingYear());
            book.setPublisher(row.getPublisher());
            book.setGenre(row.getGenre());
            book.setHardCover(row.isHardCover());
            book.setNumberOfPages(row.getNumberOfPages());

        }
        return book;
    }


    private Pageable<BookRow> convertToPageableBook(Page<Book> pageableRequest) {
        final Pageable<BookRow> pageable = new Pageable<>();
        pageable.setPageNumber(pageableRequest.getPageNumber());
        pageable.setLimit(pageableRequest.getLimit());
        pageable.setTotalElements(pageableRequest.getTotalElements());
        pageable.setElements(convertToBookRows(pageableRequest.getElements()));
        pageable.setSortBy(pageableRequest.getSortBy());
        pageable.setDirection(pageableRequest.getDirection());
        return pageable;
    }


    private List<BookRow> convertToBookRows(List<Book> elements) {
        final List<BookRow> list = new ArrayList<>();
        for (Book book : elements) {
            list.add(convertToBookRow(book));
        }
        return list;
    }


    private BookRow convertToBookRow(Book book) {
        BookRow row = null;
        if (Objects.nonNull(book)) {
            row = new BookRow(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublishingYear(),
                    book.getPublisher(),
                    book.getGenre(),
                    book.getNumberOfPages(),
                    book.isHardCover(),
                    book.getDescription()
            );
        }
        return row;
    }
}
