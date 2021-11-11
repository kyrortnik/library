package com.epam.repository.impl;

import com.epam.entity.User;
import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;
import com.epam.repository.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDAOImpl implements UserDAO {

    private static final String FIND_USER_QUERY = "SELECT * FROM users WHERE login = ? AND password = ?";
    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ? ";
    private static final String FIND_USER_BY_LOGIN_QUERY = "SELECT * FROM users WHERE login = ?";
    private static final String SAVE_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?) ";
    private static final String DELETE_USER = "DELETE FROM users WHERE login = ?";
    private static final String UPDATE_USER = "UPDATE users SET login = ?, password = ? WHERE id = ? ";
    private static final String CHANGE_USER_PASSWORD = "UPDATE users SET password = ? WHERE password = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM users";

    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);
    private static final Logger log = Logger.getLogger(UserDAOImpl.class.getName());


    @Override
    public User find(User user) throws DAOException {

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
            }
            log.info("Unable to get requested user.");
            return null;
        }catch (SQLException e ){
            throw new DAOException(e);
        }
        finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);

        }
    }



    @Override
    public User findById(Long id) throws DAOException {
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
            log.info("Unable to get requested by Id user.");
            return null;
        }catch (Exception e){
            throw new DAOException(e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    /*TODO need to add check on whether book already exists*/
    /**Functionality not yet implemented*/

    @Override
    public boolean save(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SAVE_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            return (statement.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);

        }
    }

//    @Override
//    public boolean registration(User user, String password2) {
//        return false;
//    }

    /*TODO need to add check on whether book already exists*/
    /**Functionality not yet implemented*/

    @Override
    public boolean delete(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(DELETE_USER);
            statement.setString(1,user.getLogin());
            return (statement.executeUpdate() != 0);
        }catch (SQLException e){
            throw new DAOException(e);
        }
        finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }

    }

    /**Functionality not yet implemented
     *Logic in not complete
     */


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

            if(userCheck.getId() == null || user.getId().equals(userCheck.getId())) {
                statement = connection.prepareStatement(UPDATE_USER);
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setLong(3, user.getId());
                result = statement.executeUpdate();
            }
            connection.commit();
            return (result > 0);
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception ex) {
                throw new DAOException(ex);
            }
            throw new DAOException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


    /**Functionality not yet implemented*/

    @Override
    public boolean changePassword(User user, String newPassword) throws DAOException {
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
    public List<User> getAll() throws DAOException {
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
            throw new DAOException(e);
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



   /* @Override
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
    }*/

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
