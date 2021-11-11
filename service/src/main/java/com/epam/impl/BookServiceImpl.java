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
    public boolean create(BookRow bookRow) throws ServiceException {
        try{
            return bookDAO.save(bookRow);
        }catch(DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean update(BookRow bookRow) throws ServiceException {
        try{
            return bookDAO.update(bookRow);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean delete(BookRow bookRow) throws ServiceException {
        try{
            return bookDAO.delete(bookRow);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

   /* @Override
    public List<Book> getAll() {
        return bookDAO.getAll();
    }*/

    @Override
    public Book findById(Long id) throws ServiceException {
        try{
            return convertToBook(bookDAO.getById(id));
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public List<Book> findBooksByIds(List<Reserve> reserves) throws ServiceException {
        try{
            List<BookRow> bookRows = new ArrayList<>();
            BookRow bookRow;
            for (Reserve reserve : reserves){
                bookRow = bookDAO.getById(reserve.getProductId());
                bookRows.add(bookRow);
            }

            return convertToBooks(bookRows);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }


    @Override
    public Page<Book> getAll(Page<Book> pageableRequest) throws ServiceException {
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



    /*@Override
    public Page<Book> getAll(Page<Book> pageableRequest) {
        try {
            // validation
            // ...

            // prepare data
            // process data

            // return
            return getPageByFilter(pageableRequest);
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }
    }*/

    public Page<Book> getPageByFilter(Page<Book> daoProductPageable) throws ServiceException {
        try{
            Pageable<BookRow> rowPageable = convertToPageableBook(daoProductPageable);
            return convertToServicePage(bookDAO.findPageByFilter(rowPageable));
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
        page.setFilter(convertToBook(bookRowPageable.getFilter()));
        page.setSortBy(bookRowPageable.getSortBy());
        page.setDirection(bookRowPageable.getDirection());
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
        pageable.setFilter(convertToBookRow(pageableRequest.getFilter()));
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
            row = new BookRow();
            row.setId(book.getId());
            row.setTitle(book.getTitle());
            row.setAuthor(book.getAuthor());
            row.setPublisher(book.getPublisher());
        }
        return row;
    }

    @Override
    public List<Book> findBooksByOrder(Order order) throws ServiceException {
        try{
            List<Book> booksFromOrder = new ArrayList<>();
            if (order.getId() != 0 && order.getId() != 0){
                String[] idsString = order.getProductIds().split(" ");
                Long[] idsLong = new Long[idsString.length];
                for (int i = 0;i<idsString.length;i++){
                    idsLong[i] = Long.valueOf(idsString[i]);
                }

                for (Long id : idsLong){
                    booksFromOrder.add(findById(id));
                }
            }
            return booksFromOrder;
        }catch (ServiceException e){
            throw new ServiceException(e);
        }



    }
}
