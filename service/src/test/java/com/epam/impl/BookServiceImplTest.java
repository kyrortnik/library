package com.epam.impl;

import com.epam.entity.*;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.BookDAO;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookServiceImplTest {

    //mock
    private BookDAO bookDAO = Mockito.mock(BookDAO.class, withSettings().verboseLogging());

    // testing class
    private BookServiceImpl bookService = new BookServiceImpl(bookDAO);

    //captors
    private ArgumentCaptor<Pageable<BookRow>> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);


    // Page parameters
    private int pageNumber = 1;
    private long totalElements = 10;
    private int limit = 10;
    private String direction = "ASC";
    private String sortBy = "author";
    private List<Book> emptyElements = new ArrayList<>();
    private List<BookRow> emptyElementRows = new ArrayList<>();

    private List<Book> elements = Arrays.asList(
            new Book(1L, "Harry Potter and Chamber of Secrets", "J.K. Rowling",1998),
            new Book(2L, "Harry Potter and Prisoner of Azkaban", "J.K. Rowling", 1999),
            new Book(3L, "Harry Potter and Goblet of Fire", "J.K. Rowling", 2000)
    );

    private List<BookRow> rowElements =  Arrays.asList(
            new BookRow(1L, "Harry Potter and Chamber of Secrets", "J.K. Rowling",1998),
            new BookRow(2L, "Harry Potter and Prisoner of Azkaban", "J.K. Rowling", 1999),
            new BookRow(3L, "Harry Potter and Goblet of Fire", "J.K. Rowling", 2000)
    );
//    private Book filterBook = new Book(1);
//    private BookRow filterRow = new BookRow(1);

    // Book parameters
    private long id = 1L;
    private String title = "Title";
    private String author = "Author";
    private int publishingYear = 1999;
    private String publisher = "Publisher";
    private String genre = "Genre";
    private boolean isHardCover = false;
    private int pages = 300;
    private String description = "Description";

    Book book = new Book(id,title,author,publishingYear,publisher,genre,pages,isHardCover,description);
    BookRow bookRow = new BookRow(id,title,author,publishingYear,publisher,genre,pages,isHardCover,description);




    @Test
    public void testGetAll_positive() throws ServiceException, DAOException {
        Page<Book> productPageRequest = new Page<>(pageNumber, totalElements, limit, emptyElements, sortBy, direction);
        Pageable<BookRow> daoPageRequest = new Pageable<>(pageNumber,totalElements,limit,emptyElementRows,sortBy,direction);
        Pageable<BookRow> returnedDaoPage = new Pageable<>(pageNumber,totalElements,limit,rowElements,sortBy,direction);
        Page<Book> expectedProductPageRequest = new Page<>(pageNumber, totalElements, limit, elements, sortBy, direction);

        when(bookDAO.findPageWithParameters(daoPageRequest)).thenReturn(returnedDaoPage);
        Page<Book> actualBookPage = bookService.getAll(productPageRequest);
        assertEquals(expectedProductPageRequest, actualBookPage);
    }


    @Test
    public void testCreate_positive() throws ServiceException, DAOException {


        when(bookDAO.find(bookRow)).thenReturn(null);
        when(bookDAO.save(bookRow)).thenReturn(true);
        assertTrue(bookService.create(book));
    }

    @Test
    public void testUpdate_positive() throws ServiceException, DAOException {


        when(bookDAO.find(bookRow)).thenReturn(bookRow);
        when(bookDAO.update(bookRow)).thenReturn(true);
        assertTrue(bookService.update(book));

    }

    @Test
    public void testDelete_positive() throws DAOException, ServiceException {


        when(bookDAO.findById(bookRow.getId())).thenReturn(bookRow);
        when(bookDAO.delete(bookRow.getId())).thenReturn(true);
        assertTrue(bookService.delete(book.getId()));

    }

    @Test
    public void testFindById_positive() throws DAOException, ServiceException {

        when(bookDAO.findById(bookRow.getId())).thenReturn(bookRow);
        assertEquals(book,bookService.findById(book.getId()));
    }

    @Test
    public void testFindBooksByIds_positive() throws DAOException, ServiceException {
        List<Reserve> reserves = Arrays.asList(

                new Reserve(1L,2L,3L),
                new Reserve(2L, 3L,4L),
                new Reserve(3L,4L,5L)
        );
        List<BookRow> expectedBookRows =new ArrayList<>();
        expectedBookRows.add(new BookRow(3L));
        expectedBookRows.add(new BookRow(4L));
        expectedBookRows.add(new BookRow(5L));

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book(3L));
        expectedBooks.add(new Book(4L));
        expectedBooks.add(new Book(5L));

        for (int i = 0;i< reserves.size();i++){
            if (reserves.get(i).getProductId() == expectedBooks.get(i).getId()){
                when(bookDAO.findById(reserves.get(i).getProductId())).thenReturn(expectedBookRows.get(i));
            }

        }
        List<Book> actualBooks = bookService.findBooksByIds(reserves);
        assertEquals(expectedBooks,actualBooks);

    }

   /* @Test
    public void testFindBooksByOrder_positive() throws DAOException, ServiceException {
        Order order = new Order(1L,"2 3 4",2L);
        BookRow bookRow = new BookRow(2L);
        BookRow bookRow1 = new BookRow(3L);
        BookRow bookRow2 = new BookRow(4L);
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book(2L));
        expectedBooks.add(new Book(3L));
        expectedBooks.add(new Book(4L));
        when(bookDAO.findById(2L)).thenReturn(bookRow);
        when(bookDAO.findById(3L)).thenReturn(bookRow1);
        when(bookDAO.findById(4L)).thenReturn(bookRow2);
        assertEquals(expectedBooks,bookService.findBooksByOrder(order));

    }*/

}