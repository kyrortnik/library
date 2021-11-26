package com.epam.repository.impl;

import com.epam.entity.BookRow;
import com.epam.entity.Pageable;
import com.epam.exception.DAOException;
import com.epam.repository.BookDAO;
import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;

import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import static com.epam.repository.utils.DBConstants.*;

public class BookDAOImpl extends AbstractDAO implements BookDAO {


    private static final String FIND_BOOK_QUERY = "SELECT * FROM books WHERE title = ? AND author = ?";
    private static final String FIND_BOOK_BY_ID = "SELECT * FROM books WHERE book_id = ? ";
    private static final String GET_ALL_BOOKS = "SELECT * FROM books";
    private static final String SAVE_BOOK = "INSERT INTO books (book_id,title,author,publishyear,publisher,genre,number_of_pages,is_hard_cover,description) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE book_id = ?";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, author = ?,  publisher = ?, publishyear = ?, is_hard_cover = ? , number_of_pages = ?, genre = ?, description = ?  WHERE book_id = ?";
    private static final String COUNT_ALL = "SELECT count(book_id) FROM books";
    private static final String FIND_PAGE_FILTERED_SORTED = "SELECT * FROM books ORDER BY %s %s LIMIT ? OFFSET ?";
    private static final String FIND_ORDER = "SELECT product_id FROM orders WHERE user_id = ?";

    private static final Logger LOG = Logger.getLogger(BookDAOImpl.class.getName());

    public BookDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public BookRow find(BookRow bookRow) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_BOOK_QUERY);
            statement.setString(1, bookRow.getTitle());
            statement.setString(2, bookRow.getAuthor());
            resultSet = statement.executeQuery();
            bookRow = new BookRow();
            while (resultSet.next()) {
                bookRow.setId(resultSet.getLong(1));
                bookRow.setTitle(resultSet.getString(2));
                bookRow.setAuthor(resultSet.getString(3));
                bookRow.setReserved(resultSet.getBoolean(4));
                bookRow.setPublishingYear(resultSet.getInt(5));
                bookRow.setPublisher(resultSet.getString(6));
                bookRow.setGenre(resultSet.getString(7));
                bookRow.setNumberOfPages(resultSet.getInt(8));
                bookRow.setHardCover(resultSet.getBoolean(9));
                bookRow.setDescription(resultSet.getString(10));
            }
            if (bookRow.getTitle() != null || bookRow.getId() != null) {
                return bookRow;
            }
            return null;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new DAOException(e);

        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }

    }

    @Override
    public BookRow findById(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_BOOK_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            BookRow bookRow = new BookRow();
            while (resultSet.next()) {
                bookRow.setId(resultSet.getLong(1));
                bookRow.setTitle(resultSet.getString(2));
                bookRow.setAuthor(resultSet.getString(3));
                bookRow.setReserved(resultSet.getBoolean(4));
                bookRow.setPublishingYear(resultSet.getInt(5));
                bookRow.setPublisher(resultSet.getString(6));
                bookRow.setGenre(resultSet.getString(7));
                bookRow.setNumberOfPages(resultSet.getInt(8));
                bookRow.setHardCover(resultSet.getBoolean(9));
                bookRow.setDescription(resultSet.getString(10));
            }
            if (bookRow.getTitle() != null) {
                return bookRow;
            }
            return null;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new DAOException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<BookRow> getAll() throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<BookRow> bookRows = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(GET_ALL_BOOKS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                BookRow bookRow = new BookRow();
                bookRow.setId(resultSet.getLong(1));
                bookRow.setTitle(resultSet.getString(2));
                bookRow.setAuthor(resultSet.getString(3));
                bookRow.setReserved(resultSet.getBoolean(4));
                bookRow.setPublishingYear(resultSet.getInt(5));
                bookRow.setPublisher(resultSet.getString(6));
                bookRow.setGenre(resultSet.getString(7));
                bookRow.setNumberOfPages(resultSet.getInt(8));
                bookRow.setHardCover(resultSet.getBoolean(9));
                bookRows.add(bookRow);
            }
            return bookRows;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new DAOException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public boolean save(BookRow bookRow) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {

            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SAVE_BOOK);
            statement.setString(1, bookRow.getTitle());
            statement.setString(2, bookRow.getAuthor());
            statement.setInt(3, bookRow.getPublishingYear());
            statement.setString(4, bookRow.getPublisher());
            statement.setString(5, bookRow.getGenre());
            statement.setInt(6, bookRow.getNumberOfPages());
            statement.setBoolean(7, bookRow.isHardCover());
            statement.setString(8, bookRow.getDescription());

            return (statement.executeUpdate() != 0);

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public boolean delete(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(DELETE_BOOK);
            statement.setLong(1, id);
            return (statement.executeUpdate() != 0);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean update(BookRow bookRow) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(UPDATE_BOOK);
            statement.setString(1, bookRow.getTitle());
            statement.setString(2, bookRow.getAuthor());
            statement.setString(3, bookRow.getPublisher());
            statement.setInt(4, bookRow.getPublishingYear());
            statement.setBoolean(5, bookRow.isHardCover());
            statement.setInt(6, bookRow.getNumberOfPages());
            statement.setString(7, bookRow.getGenre());
            statement.setString(8, bookRow.getDescription());
            statement.setLong(9, bookRow.getId());
            return (statement.executeUpdate() > 0);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public List<BookRow> getBookFromOrder(Long userId) throws DAOException {
        List<BookRow> booksFromOrder = new ArrayList<>();
        List<Object> parameters;
        Connection connection = null;
        PreparedStatement foundOrderStatement = null;
        PreparedStatement booksFromOrderStatement = null;
        ResultSet foundOrderSet = null;
        ResultSet booksFromOrderSet = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            parameters = Collections.singletonList(userId);
            foundOrderStatement = getPreparedStatement(FIND_ORDER, connection, parameters);
            foundOrderSet = foundOrderStatement.executeQuery();
            if (foundOrderSet.next()) {
                Array books = foundOrderSet.getArray(1);
                Long[] booksIds = (Long[]) books.getArray();
                String query = getQueryForBooksSelect(booksIds);
                booksFromOrderStatement = connection.prepareStatement(query);
                booksFromOrderSet = booksFromOrderStatement.executeQuery();
                while (booksFromOrderSet.next()) {
                    BookRow bookRow = new BookRow();
                    bookRow.setId(booksFromOrderSet.getLong(1));
                    bookRow.setTitle(booksFromOrderSet.getString(2));
                    bookRow.setAuthor(booksFromOrderSet.getString(3));
                    bookRow.setReserved(booksFromOrderSet.getBoolean(4));
                    bookRow.setPublishingYear(booksFromOrderSet.getInt(5));
                    bookRow.setPublisher(booksFromOrderSet.getString(6));
                    bookRow.setGenre(booksFromOrderSet.getString(7));
                    bookRow.setNumberOfPages(booksFromOrderSet.getInt(8));
                    bookRow.setHardCover(booksFromOrderSet.getBoolean(9));
                    booksFromOrder.add(bookRow);
                }
            }
            connection.commit();
            return booksFromOrder;
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            closeResultSet(foundOrderSet,booksFromOrderSet);
            closeStatement(foundOrderStatement,booksFromOrderStatement);
            connectionPool.releaseConnection(connection);
        }
    }

    private String getQueryForBooksSelect(Long[] booksIds) {

        String query = "SELECT * FROM books WHERE book_id IN (";
        StringBuilder stringBuilder = new StringBuilder(query);
        for (int i = 0; i < booksIds.length - 1; i++) {
            stringBuilder.append(booksIds[i]).append(", ");
        }
        stringBuilder.append(booksIds[booksIds.length - 1]).append(" )");
        return stringBuilder.toString();
    }


    @Override
    public Pageable<BookRow> findPageWithParameters(Pageable<BookRow> daoProductPageable) throws DAOException {
        int offset = (daoProductPageable.getPageNumber() - 1) * MAX_ROWS;
        List<Object> parameters = Arrays.asList(MAX_ROWS, offset);
        Connection connection = null;
        PreparedStatement countPreparedStatement = null;
        PreparedStatement queryPreparedStatement = null;
        ResultSet countResultSet = null;
        ResultSet queryResultSet = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            countPreparedStatement = getPreparedStatement(COUNT_ALL, connection, Collections.emptyList());
            final String findPageOrderedQuery =
                    String.format(FIND_PAGE_FILTERED_SORTED, daoProductPageable.getSortBy(), daoProductPageable.getDirection());
            queryPreparedStatement = getPreparedStatement(findPageOrderedQuery, connection, parameters);
            countResultSet = countPreparedStatement.executeQuery();
            queryResultSet = queryPreparedStatement.executeQuery();
            connection.commit();

            return getBookRowPageable(daoProductPageable, countResultSet, queryResultSet);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new DAOException(e);
        } finally {
            closeResultSet(countResultSet, queryResultSet);
            closeStatement(countPreparedStatement, queryPreparedStatement);
            connectionPool.releaseConnection(connection);
        }
    }


    private Pageable<BookRow> getBookRowPageable(Pageable<BookRow> daoProductPageable,
                                                 ResultSet countResultSet,
                                                 ResultSet queryResultSet) throws SQLException {
        Pageable<BookRow> pageable = new Pageable<>();
        long totalElements = 0L;
        while (countResultSet.next()) {
            totalElements = countResultSet.getLong(1);
        }
        List<BookRow> rows = new ArrayList<>();
        while (queryResultSet.next()) {
            long id = queryResultSet.getLong(1);
            String title = queryResultSet.getString(2);
            String author = queryResultSet.getString(3);
            boolean isReserved = queryResultSet.getBoolean(4);
            int publishingYear = queryResultSet.getInt(5);
            String publisher = queryResultSet.getString(6);
            String genre = queryResultSet.getString(7);
            int numberOfPages = queryResultSet.getInt(8);
            boolean isHardCover = queryResultSet.getBoolean(9);
            String description = queryResultSet.getString(10);

            rows.add(new BookRow(id, title, author, publishingYear, publisher, isReserved, genre, numberOfPages, isHardCover, description));
        }
        pageable.setPageNumber(daoProductPageable.getPageNumber());
        pageable.setLimit(MAX_ROWS);
        pageable.setTotalElements(totalElements);
        pageable.setElements(rows);
        pageable.setFilter(daoProductPageable.getFilter());
        pageable.setSortBy(daoProductPageable.getSortBy());
        pageable.setDirection(daoProductPageable.getDirection());
        return pageable;
    }

}