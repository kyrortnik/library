package com.epam.impl;

import com.epam.entity.Book;
import com.epam.entity.BookRow;
import com.epam.entity.Page;
import com.epam.entity.Pageable;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.BookDAO;
import com.epam.validator.ServiceValidator;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    //mock
    private final BookDAO bookDAO = Mockito.mock(BookDAO.class, withSettings().verboseLogging());
    private final ServiceValidator serviceValidator = ServiceValidator.getInstance();

    // testing class
    private final BookServiceImpl bookService = new BookServiceImpl(bookDAO, serviceValidator);

    // Page parameters
    private final long pageNumber = 1;
    private final long totalElements = 10;
    private final int limit = 10;
    private final String direction = "ASC";
    private final String sortBy = "author";
    private final List<Book> emptyElements = new ArrayList<>();
    private final List<BookRow> emptyElementRows = new ArrayList<>();

    private List<Book> elements = Arrays.asList(
            new Book(1L, "Harry Potter and Chamber of Secrets", "J.K. Rowling", 1998),
            new Book(2L, "Harry Potter and Prisoner of Azkaban", "J.K. Rowling", 1999),
            new Book(3L, "Harry Potter and Goblet of Fire", "J.K. Rowling", 2000)
    );

    private List<BookRow> rowElements = Arrays.asList(
            new BookRow(1L, "Harry Potter and Chamber of Secrets", "J.K. Rowling", 1998),
            new BookRow(2L, "Harry Potter and Prisoner of Azkaban", "J.K. Rowling", 1999),
            new BookRow(3L, "Harry Potter and Goblet of Fire", "J.K. Rowling", 2000)
    );


    // Book parameters
    private final long id = 1L;
    private final String title = "Title";
    private final String author = "Author";
    private final int publishingYear = 1999;
    private final String publisher = "Publisher";
    private final String genre = "Genre";
    private final boolean isHardCover = false;
    private final int pages = 300;
    private final String description = "Description";
    

    @Test
    public void testCreate_positive() throws DAOException, ServiceException {

        Book book = new Book(id, title, author, publishingYear, publisher, genre, pages, isHardCover, description);
        BookRow bookRow = new BookRow(id, title, author, publishingYear, publisher, genre, pages, isHardCover, description);

        when(bookDAO.save(bookRow)).thenReturn(true);

        boolean result = bookService.create(book);

        verify(bookDAO).save(bookRow);
        assertTrue(result);
    }


    @Test
    public void testCreate_ServiceException_ServiceException() throws DAOException {

        Book book = new Book(id, title, author, publishingYear, publisher, genre, pages, isHardCover, description);
        BookRow bookRow = new BookRow(id, title, author, publishingYear, publisher, genre, pages, isHardCover, description);
        DAOException daoException = new DAOException("testing message");

        when(bookDAO.save(bookRow)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            bookService.create(book);
        } catch (ServiceException e) {
            actualException = e;

        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());

    }

    @Test
    public void testUpdate_positive() throws ServiceException, DAOException {
        Book book = new Book(id, title, author, publishingYear, publisher, genre, pages, isHardCover, description);
        BookRow bookRow = new BookRow(id, title, author, publishingYear, publisher, genre, pages, isHardCover, description);

        when(bookDAO.update(bookRow)).thenReturn(true);

        boolean result = bookService.update(book);

        verify(bookDAO).update(bookRow);
        assertTrue(result);
    }

    @Test
    public void testUpdate_ServiceException() throws DAOException {

        Book book = new Book(id, title, author, publishingYear, publisher, genre, pages, isHardCover, description);
        BookRow bookRow = new BookRow(id, title, author, publishingYear, publisher, genre, pages, isHardCover, description);
        DAOException daoException = new DAOException("testing message");

        when(bookDAO.update(bookRow)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            bookService.update(book);
        } catch (ServiceException e) {
            actualException = e;

        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());
    }

    @Test
    public void testDelete_positive() throws DAOException, ServiceException {

        when(bookDAO.delete(id)).thenReturn(true);

        boolean result = bookService.delete(id);

        verify(bookDAO).delete(id);
        assertTrue(result);

    }

    @Test
    public void testDelete_ServiceException() throws DAOException {

        DAOException daoException = new DAOException("testing message");

        when(bookDAO.delete(id)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            bookService.delete(id);
        } catch (ServiceException e) {
            actualException = e;

        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());

    }

    @Test
    public void testFindById_positive() throws DAOException, ServiceException {

        Book book = new Book(id, title, author, publishingYear, publisher, genre, pages, isHardCover, description);
        BookRow bookRow = new BookRow(id, title, author, publishingYear, publisher, genre, pages, isHardCover, description);

        when(bookDAO.findById(id)).thenReturn(bookRow);

        Book returnBook = bookService.findById(id);

        verify(bookDAO).findById(id);
        assertEquals(book, returnBook);
    }

    @Test
    public void testFindById_ServiceException() throws DAOException {

        DAOException daoException = new DAOException("testing message");

        when(bookDAO.findById(id)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            bookService.findById(id);
        } catch (ServiceException e) {
            actualException = e;

        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());
    }

    @Test
    public void testGetBooksPage_positive() throws DAOException, ServiceException {

        Pageable<BookRow> pageableEmptyElements = new Pageable<>(pageNumber, 0, limit, emptyElementRows, sortBy, direction);
        Page<Book> pageEmptyElements = new Page<>(pageNumber, 0, limit, emptyElements, sortBy, direction);
        Pageable<BookRow> pageableWithElements = new Pageable<>(pageNumber, totalElements, limit, rowElements, sortBy, direction);
        Page<Book> pageWithElements = new Page<>(pageNumber, totalElements, limit, elements, sortBy, direction);

        when(bookDAO.findPageWithParameters(pageableEmptyElements)).thenReturn(pageableWithElements);

        Page<Book> returnPage = bookService.getBooksPage(pageEmptyElements);

        verify(bookDAO).findPageWithParameters(pageableEmptyElements);
        assertEquals(pageWithElements, returnPage);

    }


    @Test
    public void testGetBooksPage_ServiceException() throws DAOException {

        DAOException daoException = new DAOException("testing message");
        Pageable<BookRow> pageableEmptyElements = new Pageable<>(pageNumber, 0, limit, emptyElementRows, sortBy, direction);
        Page<Book> pageEmptyElements = new Page<>(pageNumber, 0, limit, emptyElements, sortBy, direction);

        when(bookDAO.findPageWithParameters(pageableEmptyElements)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            bookService.getBooksPage(pageEmptyElements);
        } catch (ServiceException e) {
            actualException = e;

        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());

    }

    @Test
    public void testGetReservedBooksPage_positive() throws DAOException, ServiceException {

        Pageable<BookRow> pageableEmptyElements = new Pageable<>(pageNumber, 0, limit, emptyElementRows, sortBy, direction);
        Page<Book> pageEmptyElements = new Page<>(pageNumber, 0, limit, emptyElements, sortBy, direction);
        Pageable<BookRow> pageableWithElements = new Pageable<>(pageNumber, totalElements, limit, rowElements, sortBy, direction);
        Page<Book> pageWithElements = new Page<>(pageNumber, totalElements, limit, elements, sortBy, direction);


        when(bookDAO.getReservedBookRowsPage(pageableEmptyElements, id)).thenReturn(pageableWithElements);

        Page<Book> returnPage = bookService.getReservedBooksPage(pageEmptyElements, id);

        verify(bookDAO).getReservedBookRowsPage(pageableEmptyElements, id);
        assertEquals(pageWithElements, returnPage);
    }

    @Test
    public void testGetReservedBooksPage_ServiceException() throws DAOException {

        DAOException daoException = new DAOException("testing message");
        Pageable<BookRow> pageableEmptyElements = new Pageable<>(pageNumber, 0, limit, emptyElementRows, sortBy, direction);
        Page<Book> pageEmptyElements = new Page<>(pageNumber, 0, limit, emptyElements, sortBy, direction);

        when(bookDAO.getReservedBookRowsPage(pageableEmptyElements, id)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            bookService.getReservedBooksPage(pageEmptyElements, id);
        } catch (ServiceException e) {
            actualException = e;

        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());

    }


    @Test
    public void testGetOrderedBooksPage_positive() throws DAOException, ServiceException {

        Pageable<BookRow> pageableEmptyElements = new Pageable<>(pageNumber, 0, limit, emptyElementRows, sortBy, direction);
        Page<Book> pageEmptyElements = new Page<>(pageNumber, 0, limit, emptyElements, sortBy, direction);
        Pageable<BookRow> pageableWithElements = new Pageable<>(pageNumber, totalElements, limit, rowElements, sortBy, direction);
        Page<Book> pageWithElements = new Page<>(pageNumber, totalElements, limit, elements, sortBy, direction);


        when(bookDAO.getOrderedBookRowsPage(pageableEmptyElements, id)).thenReturn(pageableWithElements);

        Page<Book> returnPage = bookService.getOrderedBooksPage(pageEmptyElements, id);

        verify(bookDAO).getOrderedBookRowsPage(pageableEmptyElements, id);
        assertEquals(pageWithElements, returnPage);
    }

    @Test
    public void testGetOrderedBooksPage_ServiceException() throws DAOException, ServiceException {

        DAOException daoException = new DAOException("testing message");
        Pageable<BookRow> pageableEmptyElements = new Pageable<>(pageNumber, 0, limit, emptyElementRows, sortBy, direction);
        Page<Book> pageEmptyElements = new Page<>(pageNumber, 0, limit, emptyElements, sortBy, direction);

        when(bookDAO.getOrderedBookRowsPage(pageableEmptyElements, id)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            bookService.getOrderedBooksPage(pageEmptyElements, id);
        } catch (ServiceException e) {
            actualException = e;

        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());

    }
}
