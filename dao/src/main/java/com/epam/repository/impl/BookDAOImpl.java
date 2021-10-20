package com.epam.repository.impl;

import com.epam.entity.Book;
import com.epam.exception.DAOException;
import com.epam.repository.BookDAO;
import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {


    private static final String FIND_BOOK_QUERY = "SELECT * FROM books WHERE title = ? AND author = ? AND publish_year = ?";
    private static final String FIND_BOOK_BY_ID = "SELECT * FROM books WHERE id = ? ";
    private static final String SAVE_BOOK = "INSERT INTO books VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?) ";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE title = ? AND author = ? AND publisher = ?";
    private static final String UPDATE_BOOK = "UPDATE books SET author = ?, is_reserved = ?, publishyear = ?, publisher = ?, genre = ?, pages = ?, is_hard = ? WHERE id = ? ";
    private static final String GET_ALL_BOOKS = "SELECT * FROM products";


    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);


    public BookDAOImpl(ConnectionPool connectionPool) {
    }



    @Override
    public Book get(Book book) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_BOOK_QUERY);
            statement.setString(1,book.getTitle());
            statement.setString(2,book.getAuthor());
            statement.setInt(3,book.getPublishingYear());
            resultSet = statement.executeQuery();
            book = new Book();
            while (resultSet.next()){
                book.setId(resultSet.getLong(1));
                book.setTitle(resultSet.getString(2));
                book.setReserved(resultSet.getBoolean(3));
                book.setPublishingYear(resultSet.getInt(4));
                book.setPublisher(resultSet.getString(5));
                book.setGenre(resultSet.getString(6));
                book.setNumberOfPages(resultSet.getInt(7));
                book.setHardCover(resultSet.getBoolean(8));
            }if (book.getTitle() != null){
                return book;
            }throw  new DAOException("unable to find book");
        }catch (SQLException e){
            throw new DAOException("dao error",e);
        }finally {

            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }

    }

    @Override
    public Book getById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_BOOK_BY_ID);
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            Book book = new Book();
            while (resultSet.next()){
                book.setId(resultSet.getLong(1));
                book.setTitle(resultSet.getString(2));
                book.setReserved(resultSet.getBoolean(3));
                book.setPublishingYear(resultSet.getInt(4));
                book.setPublisher(resultSet.getString(5));
                book.setGenre(resultSet.getString(6));
                book.setNumberOfPages(resultSet.getInt(7));
                book.setHardCover(resultSet.getBoolean(8));
            }if (book.getTitle() != null){
                return book;
            }throw  new DAOException("unable to find book");
        }catch (SQLException e){
            throw new DAOException("dao error",e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Book> getAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Book> books = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(GET_ALL_BOOKS);
            resultSet = statement.executeQuery();
           /* Book book;*/
            long id;
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
                Book book = new Book(
                        id,title,author,publishYear,publisher,isReserved,genre,numberOfPages,isHardCover
                );
                books.add(book);
            }
            return books;
        }catch (SQLException e){
            throw new DAOException("unable to get all books",e);
        }
        finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    /*@Override
    public List<Book> getAll() {
        return null;
    }
*/

    /** need to add check on whether book already exists*/
    @Override
    public boolean save(Book book) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {

                connection = connectionPool.getConnection();
                statement = connection.prepareStatement(SAVE_BOOK);
                statement.setString(1, book.getAuthor());
                statement.setBoolean(2, book.isReserved());
                statement.setInt(3, book.getPublishingYear());
                statement.setString(4, book.getPublisher());
                statement.setString(5, book.getGenre());
                statement.setInt(6, book.getNumberOfPages());
                statement.setBoolean(7, book.isHardCover());
                return (statement.executeUpdate() != 0);

        }catch (SQLException e) {
            throw new DAOException("error while saving user");
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);

        }

    }

    @Override
    public boolean delete(Book book) {
       Connection connection = null;
       PreparedStatement statement = null;

       try {
           connection = connectionPool.getConnection();
           statement = connection.prepareStatement(DELETE_BOOK);
           statement.setString(1,book.getTitle());
           statement.setString(2,book.getAuthor());
           statement.setInt(3,book.getPublishingYear());
           return (statement.executeUpdate() != 0);
       }catch (SQLException e){
           throw new DAOException("unable to delete book",e);
       }finally {
           closeStatement(statement);
           connectionPool.releaseConnection(connection);
       }
    }


    /**
     * check whether user exists
     * */

    @Override
    public boolean update(Book book) {
       Connection connection = null;
       PreparedStatement statement = null;
       try{
           connection = connectionPool.getConnection();
           statement = connection.prepareStatement(UPDATE_BOOK);
           statement.setString(1, book.getAuthor());
           statement.setBoolean(2, book.isReserved());
           statement.setInt(3, book.getPublishingYear());
           statement.setString(4, book.getPublisher());
           statement.setString(5, book.getGenre());
           statement.setInt(6, book.getNumberOfPages());
           statement.setBoolean(7, book.isHardCover());
           statement.setLong(8,book.getId());
           return (statement.executeUpdate() != 0);

       }catch (SQLException e){
           throw new DAOException("unable to update book",e);
       }finally {
           closeStatement(statement);
           connectionPool.releaseConnection(connection);
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