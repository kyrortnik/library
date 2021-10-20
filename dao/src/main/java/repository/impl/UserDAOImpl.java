package repository.impl;

import entity.User;
import exception.DAOException;
import repository.ConnectionPool;
import repository.PropertyInitializer;
import repository.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String FIND_USER_QUERY = "SELECT * FROM users WHERE login = ?";
    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ? ";
    private static final String SAVE_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?) ";
    private static final String DELETE_USER = "DELETE FROM users WHERE login = ?";
    private static final String UPDATE_USER = "UPDATE users SET login = ?, password = ? WHERE id = ? ";
    private static final String CHANGE_USER_PASSWORD = "UPDATE users SET password = ? WHERE password = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM users";

    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);

    public UserDAOImpl(ConnectionPool connectionPool) {
       // this.connectionPool = connectionPool;
    }

    @Override
    public User get(User user) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_USER_QUERY);
            statement.setString(1,user.getLogin());
           // statement.setString(2,user.getPassword());
            resultSet = statement.executeQuery();
            user = new User();
            while (resultSet.next()){
                user.setLogin(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setRole(resultSet.getString(3));
            }
            if (user.getLogin() != null){
                return user;
            }
            throw new DAOException("login is empty,no such user");
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
            throw new DAOException("login is empty,no such user");
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
            throw new DAOException("error while saving user");
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);

        }
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
* Should I add check on whether user which I want to update exists?
* */
    @Override
    public boolean update(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1,user.getLogin());
            statement.setString(2,user.getPassword());
            statement.setLong(3,user.getId());
            return (statement.executeUpdate() != 0);
        }catch (SQLException e){
            throw new DAOException("unable to update user",e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


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

    /** Do I need this when in find(User user) I search by login already?*/
   /* @Override
    public UserDTO findUserByLogin(String login) {
       Connection connection = null;
       PreparedStatement statement = null;
       ResultSet resultSet = null;
       try{
           connection = connectionPool.getConnection();
           statement = connection.prepareStatement()
       }
        return null;
    }*/





        @Override
    public List<User> getAll() {
        ArrayList<User> users = new ArrayList<User>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(GET_ALL_USERS);
            resultSet = statement.executeQuery();
            int id;
            String login;
            String password;
            String role;
            User user;
            while (resultSet.next()){
                id = resultSet.getInt(1);
                login = resultSet.getString(2);
                password = resultSet.getString(3);
                role = resultSet.getString(4);
                user = new User(id,login,password,role);
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
