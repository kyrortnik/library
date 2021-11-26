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
import java.util.logging.Level;
import java.util.logging.Logger;


public class BookServiceImpl implements BookService {


    private static final Logger LOG = Logger.getLogger(BookServiceImpl.class.getName());

    private final BookDAO bookDAO;
    private final ServiceValidator serviceValidator = new ServiceValidator();

    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean create(Book book) throws ServiceException {
        serviceValidator.validation(book);
        try {
            BookRow bookRow = convertToBookRow(book);
            BookRow foundRow = bookDAO.find(bookRow);
            if (foundRow == null) {
                return bookDAO.save(bookRow);
            } else {
                return false;
            }
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
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
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return bookDAO.delete(id);

        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }

    }


    @Override
    public Book findById(Long id) throws ServiceException {
        serviceValidator.validation(id);
        try {
            return convertToBook(bookDAO.findById(id));
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }

    }

    @Override
    public List<Book> findBooksByIds(List<Reserve> reserves) throws ServiceException {
        try {
            List<BookRow> bookRows = new ArrayList<>();
            BookRow bookRow;
            for (Reserve reserve : reserves) {
                bookRow = bookDAO.findById(reserve.getProductId());
                bookRows.add(bookRow);
            }

            return convertToBooks(bookRows);
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }

    }

    @Override
    public List<Book> getBooksFromOrder(Long userId) throws ServiceException {
        serviceValidator.validation(userId);
        try {
            return convertToBooks(bookDAO.getBookFromOrder(userId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<Book> getAll(Page<Book> page) throws ServiceException {
        serviceValidator.validation(page);
        try {
            Pageable<BookRow> bookRowPageable = convertToPageableBook(page);
            Pageable<BookRow> bookRowsPageable = bookDAO.findPageWithParameters(bookRowPageable);
            return convertToServicePage(bookRowsPageable);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Book> findBooksByOrder(Order order) throws ServiceException {
        serviceValidator.validation(order);
        try {
            List<Book> booksFromOrder = new ArrayList<>();
            for (Long id : getProductIdsFromOrder(order)) {
                booksFromOrder.add(findById(id));
            }
            return booksFromOrder;
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }
    }


    private Long[] getProductIdsFromOrder(Order order) {

       /* String[] idsString = order.getProductIds();
        Long[] idsLong = new Long[idsString.length];
        for (int i = 0;i<idsString.length;i++){
            idsLong[i] = Long.valueOf(idsString[i]);
        }*/
        return null;
    }

    @Override
    public Page<Book> getReservesPage(Page<Book> pageRequest, Long userId) throws ServiceException {
        serviceValidator.validation(userId);
       // serviceValidator.validation(pageRequest);
        try {
            Pageable<BookRow> pageableBookRowsRequest = convertToPageableBook(pageRequest);
            Pageable<BookRow> foundPageable = bookDAO.getReservesPage(pageableBookRowsRequest,userId);
            return convertToServicePage(foundPageable);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    private Page<Book> convertToServicePage(Pageable<BookRow> bookRowPageable) {
        Page<Book> page = new Page<>();
        page.setPageNumber(bookRowPageable.getPageNumber());
        page.setLimit(bookRowPageable.getLimit());
        page.setTotalElements(bookRowPageable.getTotalElements());
        page.setElements(convertToBooks(bookRowPageable.getElements()));
//        if (bookRowPageable.getFilter() == null){
//            page.setFilter(null);
//        }
//        page.setFilter(convertToBook(bookRowPageable.getFilter()));
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
            book.setReserved(row.isReserved());
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
//        pageable.setFilter(convertToBookRow(pageableRequest.getFilter()));
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
                    book.isReserved(),
                    book.getGenre(),
                    book.getNumberOfPages(),
                    book.isHardCover(),
                    book.getDescription()
            );
        }
        return row;
    }
}
