package com.epam.repository.impl;

import com.epam.entity.BookRow;
import com.epam.entity.Pageable;
import com.epam.exception.DAOException;
import com.epam.repository.BookDAO;
import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class BookDAOImpl implements BookDAO {


    private static final String FIND_BOOK_QUERY = "SELECT * FROM products WHERE title = ? AND author = ?";
    private static final String FIND_BOOK_BY_ID = "SELECT * FROM products WHERE product_id = ? ";
    private static final String SAVE_BOOK = "INSERT INTO products (product_id,title,author,publishyear,publisher,genre,number_of_pages,is_hard_cover,description) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BOOK = "DELETE FROM products WHERE product_id = ?";
    private static final String UPDATE_BOOK = "UPDATE products SET title = ?, author = ?,  publisher = ?, publishyear = ?, is_hard_cover = ? , number_of_pages = ?, genre = ?, description = ?  WHERE product_id = ?";
    private static final String GET_ALL_BOOKS = "SELECT * FROM products";

    private static final String COUNT_ALL = "SELECT count(product_id) FROM products";
    private static final String FIND_PAGE_FILTERED_SORTED = "SELECT * FROM products p ORDER BY p.%s %s LIMIT ? OFFSET ?";
//    private static final String FIND_PAGE_FILTERED_WHERE_PARAMETER = "SELECT * FROM products ORDER BY %s % %s WHERE %s = ? LIMIT ? OFFSET ?";
//    --------for user---------------
//    private static final String COUNT_ALL_FOR_USER
//    private static final String FIND_PAGE_FILTERED_FOR_USER


    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);

    private static final Logger log = Logger.getLogger(BookDAOImpl.class.getName());


    public BookDAOImpl() {
    }



    @Override
    public BookRow find(BookRow bookRow) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_BOOK_QUERY);
            statement.setString(1, bookRow.getTitle());
            statement.setString(2, bookRow.getAuthor());
            resultSet = statement.executeQuery();
            bookRow = new BookRow();
            while (resultSet.next()){
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
            }if (bookRow.getTitle() != null || bookRow.getId() != null){
                return bookRow;
            }
            log.info("Couldn't find requested book.");
            return null;
        }catch (Exception e){
            throw new DAOException(e);
        }finally {

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
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_BOOK_BY_ID);
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            BookRow bookRow = new BookRow();
            while (resultSet.next()){
                bookRow.setId(resultSet.getLong(1));
                bookRow.setTitle(resultSet.getString(2));
                bookRow.setAuthor(resultSet.getString(3));
                bookRow.setReserved(resultSet.getBoolean(4));
                bookRow.setPublishingYear(resultSet.getInt(5));
                bookRow.setPublisher(resultSet.getString(6));
                bookRow.setGenre(resultSet.getString(7));
                bookRow.setNumberOfPages(resultSet.getInt(8));
                bookRow.setHardCover(resultSet.getBoolean(9));
            }if (bookRow.getTitle() != null){
                return bookRow;
            }log.info("Couldn't find requested book by Id.");
            return null;
        }catch (Exception e){
            throw new DAOException(e);
        }finally {
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
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(GET_ALL_BOOKS);
            resultSet = statement.executeQuery();
           /* Book book;*/
            /*long id;
            String title;
            String author;
            boolean isReserved;
            int publishYear;
            String publisher;
            String genre;
            int numberOfPages;
            boolean isHardCover;
            while(resultSet.next()){
                id = resultSet.getLong(1);
                title =resultSet.getString(2);
                author = resultSet.getString(3);
                isReserved = resultSet.getBoolean(4);
                publishYear = resultSet.getInt(5);
                publisher = resultSet.getString(6);
                genre = resultSet.getString(7);
                numberOfPages = resultSet.getInt(8);
                isHardCover = resultSet.getBoolean(9);
                BookRow bookRow = new BookRow(
                        id,title,author,publishYear,publisher,isReserved,genre,numberOfPages,isHardCover
                );*/

            while (resultSet.next()){
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
        }catch (Exception e){
            throw new DAOException(e);
        }
        finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


    /*TODO need to add check on whether book already exists*/
    /**Functionality not yet implemented*/
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

        }catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);

        }

    }



    @Override
    public boolean delete(BookRow bookRow) throws DAOException {
       Connection connection = null;
       PreparedStatement statement = null;

       try {
           connection = connectionPool.getConnection();
           statement = connection.prepareStatement(DELETE_BOOK);
           statement.setLong(1, bookRow.getId());
           return (statement.executeUpdate() != 0);
       }catch (Exception e){
           throw new DAOException(e);
       }finally {
           closeStatement(statement);
           connectionPool.releaseConnection(connection);
       }
    }


    //                          <input type="hidden" name="command" value="editBook"/>
//                        <input class="fname" type="text" name="title" placeholder="Book title"/>
//                        <input type="text" name="author" placeholder="Author"/>
//                        <input type="text" name="publisher" placeholder="publisher"/>
//                        <input type="text" name="publishingYear" placeholder="publishing Year"/>
//                        <input type="text" name="isHardCover" placeholder="is hard cover"/>
//                        <input type="text" name="numberOfPages" placeholder="number of pages"/>
//                        <input type="text" name="genre" placeholder="Genre"/>
//                        <input type="text" name="description" placeholder="Description"/>

    @Override
    public boolean update(BookRow bookRow) throws DAOException {
       Connection connection = null;
       PreparedStatement statement = null;
       try{
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

       }catch (Exception e){
           throw new DAOException(e);
       }finally {
           closeStatement(statement);
           connectionPool.releaseConnection(connection);
       }
    }

    //TODO разобраться в логике метода

    @Override
    public Pageable<BookRow> findPageByFilter(Pageable<BookRow> daoProductPageable) throws DAOException {
        final int offset = (daoProductPageable.getPageNumber() - 1) * daoProductPageable.getLimit();
        List<Object> parameters1 = Collections.emptyList(); // todo implement filtering
        List<Object> parameters2 = Arrays.asList( // todo implement filtering
                daoProductPageable.getLimit(),
                offset
        );
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement1 = getPreparedStatement(COUNT_ALL, connection, parameters1);
            final String findPageOrderedQuery =
                    String.format(FIND_PAGE_FILTERED_SORTED, daoProductPageable.getSortBy(), daoProductPageable.getDirection());
            preparedStatement2 = getPreparedStatement(findPageOrderedQuery, connection, parameters2);
            resultSet1 = preparedStatement1.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();
           // connection.commit();

            return getBookRowPageable(daoProductPageable, resultSet1, resultSet2);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            closeResultSet(resultSet1);
            closeResultSet(resultSet2);
            closeStatement(preparedStatement1);
            closeStatement(preparedStatement2);
            connectionPool.releaseConnection(connection);
        }
    }


    private Pageable<BookRow> getBookRowPageable(Pageable<BookRow> daoProductPageable,
                                                    ResultSet resultSet1,
                                                    ResultSet resultSet2) throws SQLException {
        final Pageable<BookRow> pageable = new Pageable<>();
        long totalElements = 0L;
        while (resultSet1.next()) {
            totalElements = resultSet1.getLong(1);
        }
        final List<BookRow> rows = new ArrayList<>();
        while (resultSet2.next()) {
            long id = resultSet2.getLong(1);
            String title = resultSet2.getString(2);
            String author = resultSet2.getString(3);
            boolean isReserved = resultSet2.getBoolean(4);
            int publishingYear = resultSet2.getInt(5);
            String publisher = resultSet2.getString(6);
            String genre = resultSet2.getString(7);
            int numberOfPages = resultSet2.getInt(8);
            boolean isHardCover = resultSet2.getBoolean(9);

            rows.add(new BookRow(id, title,author,publishingYear,publisher,isReserved,genre,numberOfPages,isHardCover));
        }
        pageable.setPageNumber(daoProductPageable.getPageNumber());
        pageable.setLimit(daoProductPageable.getLimit());
        pageable.setTotalElements(totalElements);
        pageable.setElements(rows);
        pageable.setFilter(daoProductPageable.getFilter());
        pageable.setSortBy(daoProductPageable.getSortBy());
        pageable.setDirection(daoProductPageable.getDirection());
        return pageable;
    }



    protected PreparedStatement getPreparedStatement(String query, Connection connection,
                                                      List<Object> parameters) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(query);
        setPreparedStatementParameters(preparedStatement, parameters);
        return preparedStatement;
    }

    protected void setPreparedStatementParameters( PreparedStatement preparedStatement,
                                                   List<Object> parameters) throws SQLException {
        for (int i = 0, queryParameterIndex = 1; i < parameters.size(); i++, queryParameterIndex++) {
            final Object parameter = parameters.get(i);
            setPreparedStatementParameter(preparedStatement, queryParameterIndex, parameter);
        }
    }

    protected void setPreparedStatementParameter( PreparedStatement preparedStatement,
                                                  int queryParameterIndex,  Object parameter) throws SQLException {
        if (Long.class == parameter.getClass()) {
            preparedStatement.setLong(queryParameterIndex, (Long) parameter);
        } else if (Integer.class == parameter.getClass()){
            preparedStatement.setInt(queryParameterIndex, (Integer) parameter);
        } else if (String.class == parameter.getClass()){
            preparedStatement.setString(queryParameterIndex, (String) parameter);
        }
    }



    private void closeResultSet( ResultSet resultSet) {
        try{

            if (resultSet != null){
                resultSet.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void closeStatement(PreparedStatement statement){
        try{
            if (statement != null){
                statement.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }





}