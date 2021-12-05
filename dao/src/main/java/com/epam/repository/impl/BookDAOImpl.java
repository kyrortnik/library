package com.epam.repository.impl;

import com.epam.entity.BookRow;
import com.epam.entity.Pageable;
import com.epam.exception.DAOException;
import com.epam.repository.BookDAO;
import com.epam.repository.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.epam.repository.utils.DBConstants.MAX_ROWS;
import static java.util.Objects.nonNull;

public class BookDAOImpl extends AbstractDAO implements BookDAO {

    private static final String QUERY_ORDER = "ORDER BY %s %s LIMIT ? OFFSET ?";
    private static final String FIND_BOOK_BY_ID = "SELECT book_id, title, author, publishyear, publisher, genre, number_of_pages ,is_hard_cover, description FROM books WHERE book_id = ? ";
    private static final String SAVE_BOOK = "INSERT INTO books (book_id,title,author,publishyear,publisher,genre,number_of_pages,is_hard_cover,description) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE book_id = ?";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, author = ?,  publisher = ?, publishyear = ?, is_hard_cover = ? , number_of_pages = ?, genre = ?, description = ?  WHERE book_id = ?";
    private static final String COUNT_ALL = "SELECT count(book_id) FROM books";
    private static final String COUNT_RESERVES_FOR_USER = "SELECT count(book_id) FROM books b JOIN reserves r ON b.book_id = r.product_id WHERE user_id = ?";
    private static final String FIND_PAGE_FILTERED_SORTED = "SELECT book_id, title, author, publishyear, publisher, genre, number_of_pages ,is_hard_cover, description FROM books " + QUERY_ORDER;
    private static final String FIND_RESERVED_BOOKS = "SELECT book_id, title, author, publishyear, publisher, genre, number_of_pages, is_hard_cover, description FROM reserves r" +
            " JOIN books b on r.product_id = b.book_id WHERE user_id = ? " + QUERY_ORDER;
    private static final String FIND_BOOKS_ID_IN = "SELECT book_id, title, author, publishyear, publisher, genre, number_of_pages ,is_hard_cover, description" +
            " FROM books WHERE book_id IN (";
    private static final String FIND_BOOKS_FROM_ORDER = "SELECT product_ids FROM orders WHERE user_id = ?";


    public BookDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }


    @Override
    public BookRow findById(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        BookRow bookRow = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(FIND_BOOK_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                bookRow = getBookRowFormResultSet(resultSet);
            }
            connection.commit();
            return bookRow;
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean save(BookRow bookRow) throws DAOException {
        List<Object> parameters = Arrays.asList(
                bookRow.getTitle(),
                bookRow.getAuthor(),
                bookRow.getPublishingYear(),
                bookRow.getPublisher(),
                bookRow.getGenre(),
                bookRow.getNumberOfPages(),
                bookRow.isHardCover(),
                bookRow.getDescription()

        );
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = getPreparedStatement(SAVE_BOOK, connection, parameters);
            return (statement.executeUpdate() != 0);

        } catch (SQLException e) {
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
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean update(BookRow bookRow) throws DAOException {
        boolean result = false;
        List<Object> parameters = Arrays.asList(
                bookRow.getTitle(),
                bookRow.getAuthor(),
                bookRow.getPublisher(),
                bookRow.getPublishingYear(),
                bookRow.isHardCover(),
                bookRow.getNumberOfPages(),
                bookRow.getGenre(),
                bookRow.getDescription(),
                bookRow.getId()
        );
        Connection connection = null;
        PreparedStatement updateStatement = null;
        BookRow existingBookRow;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            existingBookRow = findById(bookRow.getId());
            if (nonNull(existingBookRow)) {
                updateStatement = getPreparedStatement(UPDATE_BOOK, connection, parameters);
                result = updateStatement.executeUpdate() != 0;
            }
            connection.commit();
            return result;

        } catch (Exception e) {
            try {
                if (nonNull(connection)) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
            throw new DAOException(e);
        } finally {
            closeStatement(updateStatement);
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public Pageable<BookRow> getBookRowsPage(Pageable<BookRow> daoProductPageable) throws DAOException {
        Long offset = (daoProductPageable.getPageNumber() - 1) * MAX_ROWS;
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
            String findPageOrderedQuery =
                    String.format(FIND_PAGE_FILTERED_SORTED, daoProductPageable.getSortBy(), daoProductPageable.getDirection());
            queryPreparedStatement = getPreparedStatement(findPageOrderedQuery, connection, parameters);
            countResultSet = countPreparedStatement.executeQuery();
            queryResultSet = queryPreparedStatement.executeQuery();
            long totalElements = 0L;
            if (countResultSet.next()) {
                totalElements = countResultSet.getLong(1);
            }
            connection.commit();

            return getBookRowPageable(daoProductPageable, totalElements, queryResultSet);
        } catch (SQLException e) {
            try {
                if (nonNull(connection)) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
            throw new DAOException(e);
        } finally {
            closeResultSet(countResultSet, queryResultSet);
            closeStatement(countPreparedStatement, queryPreparedStatement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Pageable<BookRow> getReservedBookRowsPage(Pageable<BookRow> daoBookPageable, Long userId) throws DAOException {
        Long offset = (daoBookPageable.getPageNumber() - 1) * MAX_ROWS;
        List<Object> parameters = Arrays.asList(userId, MAX_ROWS, offset);
        Connection connection = null;
        PreparedStatement countReservesStatement = null;
        PreparedStatement booksReservedStatement = null;
        ResultSet reservesNumberSet = null;
        ResultSet reservedBooksSet = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            countReservesStatement = getPreparedStatement(COUNT_RESERVES_FOR_USER, connection, Collections.singletonList(userId));
            String findPageOrderedQuery =
                    String.format(FIND_RESERVED_BOOKS, daoBookPageable.getSortBy(), daoBookPageable.getDirection());
            booksReservedStatement = getPreparedStatement(findPageOrderedQuery, connection, parameters);
            reservesNumberSet = countReservesStatement.executeQuery();
            reservedBooksSet = booksReservedStatement.executeQuery();
            long totalElements = 0L;
            if (reservesNumberSet.next()) {
                totalElements = reservesNumberSet.getLong(1);
            }
            connection.commit();
            return getBookRowPageable(daoBookPageable, totalElements, reservedBooksSet);
        } catch (SQLException e) {
            try {
                if (nonNull(connection)) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
            throw new DAOException(e);
        } finally {
            closeResultSet(reservesNumberSet, reservedBooksSet);
            closeStatement(countReservesStatement, booksReservedStatement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Pageable<BookRow> getOrderedBookRowsPage(Pageable<BookRow> pageableBookRowsRequest, Long userId) throws DAOException {
        Long offset = (pageableBookRowsRequest.getPageNumber() - 1) * MAX_ROWS;
        List<Object> parameters = Arrays.asList(pageableBookRowsRequest.getLimit(), offset);
        Pageable<BookRow> pageable = pageableBookRowsRequest;
        Connection connection = null;
        PreparedStatement booksFromOrderStatement = null;
        PreparedStatement foundBooksStatement = null;
        ResultSet booksFromOrderSet = null;
        ResultSet foundBooksSet = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            booksFromOrderStatement = getPreparedStatement(FIND_BOOKS_FROM_ORDER, connection, Collections.singletonList(userId));
            booksFromOrderSet = booksFromOrderStatement.executeQuery();
            if (booksFromOrderSet.next()) {
                Array books = booksFromOrderSet.getArray(1);
                Long[] booksIds = (Long[]) books.getArray();
                long totalElements = booksIds.length;
                String query = String.format(
                        getQueryForBooksSelect(booksIds), pageableBookRowsRequest.getSortBy(), pageableBookRowsRequest.getDirection());
                foundBooksStatement = getPreparedStatement(query, connection, parameters);
                foundBooksSet = foundBooksStatement.executeQuery();
                pageable = getBookRowPageable(pageableBookRowsRequest, totalElements, foundBooksSet);
            }
            connection.commit();
            return pageable;
        } catch (SQLException e) {
            try {
                if (nonNull(connection)) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
            throw new DAOException(e);

        } finally {
            closeResultSet(booksFromOrderSet, foundBooksSet);
            closeStatement(booksFromOrderStatement, foundBooksStatement);
            connectionPool.releaseConnection(connection);
        }
    }

    private Pageable<BookRow> getBookRowPageable(Pageable<BookRow> daoBookPageable,
                                                 long totalElements,
                                                 ResultSet queryResultSet) throws SQLException {
        Pageable<BookRow> pageable = new Pageable<>();
        List<BookRow> rows = new ArrayList<>();
        while (queryResultSet.next()) {
            BookRow bookRow = getBookRowFormResultSet(queryResultSet);
            rows.add(bookRow);
        }
        pageable.setPageNumber(daoBookPageable.getPageNumber());
        pageable.setLimit(MAX_ROWS);
        pageable.setTotalElements(totalElements);
        pageable.setElements(rows);
        pageable.setSortBy(daoBookPageable.getSortBy());
        pageable.setDirection(daoBookPageable.getDirection());
        return pageable;
    }

    private BookRow getBookRowFormResultSet(ResultSet resultSet) throws SQLException {

        long id = resultSet.getLong(1);
        String title = resultSet.getString(2);
        String author = resultSet.getString(3);
        int publishingYear = resultSet.getInt(4);
        String publisher = resultSet.getString(5);
        String genre = resultSet.getString(6);
        int numberOfPages = resultSet.getInt(7);
        boolean isHardCover = resultSet.getBoolean(8);
        String description = resultSet.getString(9);

        return new BookRow(id, title, author, publishingYear, publisher, genre, numberOfPages, isHardCover, description);
    }

    private String getQueryForBooksSelect(Long[] booksIds) {

        StringBuilder stringBuilder = new StringBuilder(FIND_BOOKS_ID_IN);
        for (int i = 0; i < booksIds.length - 1; i++) {
            stringBuilder.append(booksIds[i]).append(", ");
        }
        stringBuilder.append(booksIds[booksIds.length - 1]).append(" ) ").append(QUERY_ORDER);
        return stringBuilder.toString();
    }

}