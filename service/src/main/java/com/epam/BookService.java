package com.epam;

import com.epam.entity.Book;
import com.epam.entity.Page;
import com.epam.exception.ServiceException;

public interface BookService {

    /**
     * Validates params from client and passes them to DAO layer in order to find Book
     *
     * @param id, to search book by
     * @return found Book, null Book if not found
     * @throws ServiceException throws ServiceException
     */
    Book findById(Long id) throws ServiceException;

    /**
     * Validates params from client and passes them to DAO layer in order to create Book
     *
     * @param book, to be created
     * @return true is Book created, false if not
     * @throws ServiceException throws ServiceException
     */
    boolean create(Book book) throws ServiceException;

    /**
     * Validates params from client and passes them to DAO layer in order to update Book
     *
     * @param book, to be updated
     * @return true is Book updated, false if not
     * throws ServiceException throws ServiceException
     */
    boolean update(Book book) throws ServiceException;

    /**
     * Validates params from client and passes them to DAO layer in order to delete Book
     *
     * @param id, bookId to delete Book by
     * @return true is Book deleted, false if not
     * @throws ServiceException throws ServiceException
     */
    boolean delete(Long id) throws ServiceException;

    /**
     * Validates params from client and passes them to DAO layer in order to get Page<Book>
     *
     * @param pageRequest, page with params and empty List<Book> elements
     * @return page with updated List<Book> elements
     * @throws ServiceException throws ServiceException
     */
    Page<Book> getBooksPage(Page<Book> pageRequest) throws ServiceException;


    /**
     * Validates params from client and passes them to DAO layer in order to get Page<Book> with reserved books for current user
     *
     * @param pageReserve, page with params and empty List<Book> elements
     * @param userId       user to find ordered Books for
     * @return page with updated List<Book> elements
     * @throws ServiceException throws ServiceException
     */
    Page<Book> getReservedBooksPage(Page<Book> pageReserve, Long userId) throws ServiceException;

    /**
     * Validates params from client and passes them to DAO layer in order to get Page<Book> with ordered books for current user
     *
     * @param pageOrder, page with params and empty List<Book> elements
     * @param userId,    user to find ordered Books for
     * @return page with updated List<Book> elements
     * @throws ServiceException throws ServiceException
     */
    Page<Book> getOrderedBooksPage(Page<Book> pageOrder, Long userId) throws ServiceException;


}
