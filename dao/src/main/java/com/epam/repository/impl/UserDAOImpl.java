package com.epam.repository.impl;

import com.epam.entity.Pageable;
import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.UserDAO;
import com.epam.security.Salt;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.epam.repository.utils.DBConstants.*;


public class UserDAOImpl extends AbstractDAO implements UserDAO {

    private static final Logger LOG = Logger.getLogger(UserDAOImpl.class.getName());

    private static final String FIND_USER_QUERY = "SELECT * FROM users WHERE login = ? AND password = ?";
    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ? ";
    private static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String SAVE_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?) ";
    private static final String DELETE_USER = "DELETE FROM users WHERE user_id = ?";
    private static final String UPDATE_USER = "UPDATE users SET login = ?, password = ? WHERE id = ? ";
    private static final String CHANGE_USER_PASSWORD = "UPDATE users SET password = ? WHERE password = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private static final String COUNT_ALL = "SELECT count(user_id) FROM users";
    private static final String FIND_PAGE_FILTERED_SORTED = "SELECT * FROM users p ORDER BY p.%s %s LIMIT ? OFFSET ?";

    private final Salt salt = new Salt();


    public UserDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }


    @Override
    public User find(User user) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_USER_QUERY);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            resultSet = statement.executeQuery();
            user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRole(resultSet.getString(4));
            }
            if (user.getLogin() != null) {
                return user;
            }
            return null;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new DAOException(e);
        } finally {
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
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_USER_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRole(resultSet.getString(4));
            }
            if (user.getLogin() != null) {
                return user;
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
    public User findByLogin(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_USER_BY_LOGIN);
            statement.setString(1, user.getLogin());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                user.setPassword(resultSet.getString(3));
                user.setRole(resultSet.getString(4));
                user.setSalt(resultSet.getString(5));
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }

    }

    @Override
    public UserDTO register(User user) throws DAOException {
        List<Object> parameters;
        UserDTO registeredUser = null;
        Connection connection = null;
        PreparedStatement saveUserStatement = null;
        PreparedStatement getSavedUserStatement = null;
        ResultSet resultSet = null;
        try {
            String generatedSalt = salt.generateSalt();
            String hashedPassword = salt.generateEncryptedPassword(user.getPassword(), generatedSalt);

            parameters = Arrays.asList(
                    user.getLogin(),
                    hashedPassword,
                    user.getRole(),
                    generatedSalt
            );

            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            saveUserStatement = getPreparedStatement(SAVE_USER, connection, parameters);
            if (saveUserStatement.executeUpdate() != 0) {
                parameters = Collections.singletonList(user.getLogin());
                getSavedUserStatement = getPreparedStatement(FIND_USER_BY_LOGIN, connection, parameters);
                resultSet = getSavedUserStatement.executeQuery();
                if (resultSet.next()) {
                    registeredUser = new UserDTO();
                    registeredUser.setId(resultSet.getLong(1));
                    registeredUser.setLogin(resultSet.getString(2));
                    registeredUser.setRole(resultSet.getString(4));
                }
            }
            connection.commit();
            return registeredUser;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(saveUserStatement, getSavedUserStatement);
            connectionPool.releaseConnection(connection);
        }


    }

    @Override
    public UserDTO login(String login, String enteredPassword) throws DAOException {
        List<Object> parameters = Collections.singletonList(login);
        String saltFromDB;
        String passwordFromDB;

        Connection connection = null;
        PreparedStatement findUserPreparedStatement = null;
        ResultSet resultSet = null;
        UserDTO returnUser = null;
        try {
            connection = connectionPool.getConnection();
            findUserPreparedStatement = getPreparedStatement(FIND_USER_BY_LOGIN, connection, parameters);
            resultSet = findUserPreparedStatement.executeQuery();
            if (resultSet.next()) {
                UserDTO foundUser = new UserDTO();
                foundUser.setId(resultSet.getLong(1));
                foundUser.setLogin(resultSet.getString(2));
                foundUser.setRole(resultSet.getString(4));
                passwordFromDB = resultSet.getString(3);
                saltFromDB = resultSet.getString(5);
                if (salt.verifyPassword(enteredPassword, passwordFromDB, saltFromDB)) {
                    returnUser = foundUser;
                }
            }
            return returnUser;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(findUserPreparedStatement);
            connectionPool.releaseConnection(connection);
        }

    }

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
            statement.setString(4, user.getSalt());
            return statement.executeUpdate() != 0;
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
            statement = connection.prepareStatement(DELETE_USER);
            statement.setLong(1, id);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }

    }

    /**
     * Functionality not yet implemented
     * Logic in not complete
     */


    public boolean update(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int result = 0;
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(FIND_USER_BY_LOGIN);
            statement.setString(1, user.getLogin());
            resultSet = statement.executeQuery();
            User userCheck = new User();
            while (resultSet.next()) {
                userCheck.setId(resultSet.getLong(1));
                userCheck.setLogin(resultSet.getString(2));
                userCheck.setRole(resultSet.getString(4));
            }

            if (userCheck.getId() != null && user.getId().equals(userCheck.getId())) {
                statement = connection.prepareStatement(UPDATE_USER);
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setLong(3, user.getId());
                result = statement.executeUpdate();
            }
            connection.commit();
            return result > 0;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Exception:" + ex);
                throw new DAOException(ex);
            }
            LOG.log(Level.SEVERE, "Exception:" + e);
            throw new DAOException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


    /**
     * Functionality not yet implemented
     */

    @Override
    public boolean changePassword(User user, String newPassword) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(CHANGE_USER_PASSWORD);
            statement.setString(1, user.getPassword());
            return (statement.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DAOException("unable to change password", e);
        } finally {
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
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRole(resultSet.getString(4));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }

    }


    /**
     * Do I need this method?
     */



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
    @Override
    public Pageable<UserDTO> findPageByParameters(Pageable<UserDTO> daoProductPageable) throws DAOException {
        final int offset = (daoProductPageable.getPageNumber() - 1) * MAX_ROWS;

        List<Object> parameters = Arrays.asList(MAX_ROWS, offset);
        Connection connection = null;
        PreparedStatement countStatement = null;
        PreparedStatement queryStatement = null;
        ResultSet countResultSet = null;
        ResultSet queryResultSet = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            countStatement = getPreparedStatement(COUNT_ALL, connection, Collections.emptyList());
            final String findPageOrderedQuery =
                    String.format(FIND_PAGE_FILTERED_SORTED, daoProductPageable.getSortBy(), daoProductPageable.getDirection());
            queryStatement = getPreparedStatement(findPageOrderedQuery, connection, parameters);
            countResultSet = countStatement.executeQuery();
            queryResultSet = queryStatement.executeQuery();
            connection.commit();

            return getBookRowPageable(daoProductPageable, countResultSet, queryResultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            closeResultSet(countResultSet, queryResultSet);
            closeStatement(countStatement, queryStatement);
            connectionPool.releaseConnection(connection);
        }
    }


    private Pageable<UserDTO> getBookRowPageable(Pageable<UserDTO> daoProductPageable,
                                                 ResultSet countResultSet,
                                                 ResultSet queryResultSet) throws SQLException {
        final Pageable<UserDTO> pageable = new Pageable<>();
        long totalElements = 0L;
        while (countResultSet.next()) {
            totalElements = countResultSet.getLong(1);
        }
        final List<UserDTO> rows = new ArrayList<>();
        while (queryResultSet.next()) {
            long id = queryResultSet.getLong(1);
            String login = queryResultSet.getString(2);
            String role = queryResultSet.getString(4);

            rows.add(new UserDTO(id, login, role));
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


}
