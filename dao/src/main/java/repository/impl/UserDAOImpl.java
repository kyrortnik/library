package repository.impl;

import entity.User;
import exception.DAOException;
import repository.ConnectionPool;
import repository.PropertyInitializer;
import repository.UserDAO;

import java.sql.*;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String FIND_USER_QUERY = "SELECT * FROM users WHERE login = ? AND password = ?";
    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ? ";
    private static final String SAVE_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?) ";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE users SET  WHERE id = ? ";
    private static final String GET_ALL_USERS = "SELECT * FROM users";

    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);

    public UserDAOImpl(ConnectionPool connectionPool) {
       // this.connectionPool = connectionPool;
    }

    @Override
    public User getEntity(User element) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_USER_QUERY);
            statement.setString(1,element.getLogin());
            statement.setString(2,element.getPassword());
            resultSet = statement.executeQuery();
            element = new User();
            while (resultSet.next()){
                element.setLogin(resultSet.getString(1));
                element.setPassword(resultSet.getString(2));
                element.setRole(resultSet.getString(3));
            }
            if (element.getLogin() != null){
                return element;
            }

        }catch (SQLException e ){
            e.printStackTrace();
        }
        finally {
            try{
                if (statement != null){
                    statement.close();
                }
                if (resultSet != null){
                    resultSet.close();
                }
                connectionPool.releaseConnection(connection);
            }catch (SQLException e){
                e.printStackTrace();
            }



        }

        return null;
    }

    @Override
    public User getEntityById(Long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public boolean saveEntity(User user) {
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
            if (statement != null) {
                try {
                    statement.close();
                    connectionPool.releaseConnection(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public boolean deleteEntity(User element) {
        return false;
    }

    @Override
    public boolean updateEntity(User element) {
        return false;
    }



}
