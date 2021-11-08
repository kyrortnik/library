package com.epam.repository.impl;

import com.epam.entity.User;
import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;
import com.epam.repository.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String FIND_USER_QUERY = "SELECT * FROM users WHERE login = ? AND password = ?";
    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ? ";
    private static final String FIND_USER_BY_LOGIN_QUERY = "SELECT * FROM shop.users WHERE login = ?";
    private static final String SAVE_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?) ";
    private static final String DELETE_USER = "DELETE FROM users WHERE login = ?";
    private static final String UPDATE_USER = "UPDATE users SET login = ?, password = ? WHERE id = ? ";
    private static final String CHANGE_USER_PASSWORD = "UPDATE users SET password = ? WHERE password = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM users";

    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);


    @Override
    public User get(User user) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_USER_QUERY);
            statement.setString(1,user.getLogin());
            statement.setString(2,user.getPassword());
            resultSet = statement.executeQuery();
            user = new User();
            while (resultSet.next()){
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRole(resultSet.getString(4));
            }
            if (user.getLogin() != null){
                return user;
            }else{
                return null;
            }
//            throw new DAOException("login is empty,no such user");
        }catch (SQLException e ){
            throw new DAOException("No user with such login",e);
        }
        finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);

        }
    }



    @Override
    public User getById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_USER_BY_ID);
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            User user = new User();
            while(resultSet.next()){
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRole(resultSet.getString(4));
            }
            if (user.getLogin() != null){
                return user;
            }
            return null;
//            throw new DAOException("login is empty,no such user");
        }catch (SQLException e){
            throw new DAOException("no user with such login",e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public boolean save(User user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SAVE_USER);
//            statement.setLong(1,element.getId());
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());

            return (statement.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DAOException("error while saving user",e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);

        }
    }

    @Override
    public boolean registration(User user, String password2) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(DELETE_USER);
            statement.setString(1,user.getLogin());
            return (statement.executeUpdate() != 0);
        }catch (SQLException e){
            throw new DAOException("unable to delete user",e);
        }
        finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }

    }

/**
* Should I add check on whether user which I want to update exists? - added
* */
  /*  @Override
    public boolean update(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1,user.getLogin());
            statement.setString(2,user.getPassword());
            statement.setLong(3,user.getId());
            return (statement.executeUpdate() > 0);
        }catch (SQLException e){
            throw new DAOException("unable to update user",e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }*/

    public boolean update(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int result = 0;
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(FIND_USER_BY_LOGIN_QUERY);
            statement.setString(1, user.getLogin());
            resultSet = statement.executeQuery();
            User userCheck = new User();
            while (resultSet.next()) {
                userCheck.setId(resultSet.getLong(1));
                userCheck.setLogin(resultSet.getString(2));
                userCheck.setRole(resultSet.getString(4));
            }

            if(userCheck.getId() == 0 || user.getId() == userCheck.getId()) {
                statement = connection.prepareStatement(UPDATE_USER);
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setLong(3, user.getId());
                result = statement.executeUpdate();
            }
            connection.commit();
            return (result > 0);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DAOException("Error during rollback", ex);
            }
            throw new DAOException("Error in DAO method", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


/**
* No yet implemented
* */


    @Override
    public boolean changePassword(User user, String newPassword) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(CHANGE_USER_PASSWORD);
            statement.setString(1, user.getPassword());
            return (statement.executeUpdate() != 0);
        }catch (SQLException e){
            throw new DAOException("unable to change password",e);
        }
        finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(GET_ALL_USERS);
            resultSet = statement.executeQuery();
            User user = new User();
            while (resultSet.next()){
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRole(resultSet.getString(4));
                users.add(user);
            }
            return users;
        }catch (SQLException e){
            throw new DAOException("unable to get all users",e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }

    }


    /**
    * Do I need this method?
    *
    * */



    @Override
    public boolean findUserByLogin(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        String pass;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_USER_QUERY);
            statement.setString(1,user.getLogin());
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                pass = resultSet.getString(3);
                if (user.getPassword().equals(pass)){
                    flag = true;
                }
            }
            return flag;
        }catch (SQLException e){
            throw new DAOException("unable to find user in database",e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    private void closeResultSet(ResultSet resultSet) {
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
