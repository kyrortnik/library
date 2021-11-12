package com.epam.repository.impl;

import com.epam.entity.BookRow;
import com.epam.entity.Pageable;
import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;
import com.epam.repository.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private static final String COUNT_ALL = "SELECT count(user_id) FROM users";
    private static final String FIND_PAGE_FILTERED_SORTED = "SELECT * FROM users p ORDER BY p.%s %s LIMIT ? OFFSET ?";

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
            while (resultSet.next()){
                User user = new User();
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


    @Override
    public Pageable<UserDTO> findPageByFilter(Pageable<UserDTO> daoProductPageable) throws DAOException {
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


    private Pageable<UserDTO> getBookRowPageable(Pageable<UserDTO> daoProductPageable,
                                                 ResultSet resultSet1,
                                                 ResultSet resultSet2) throws SQLException {
        final Pageable<UserDTO> pageable = new Pageable<>();
        long totalElements = 0L;
        while (resultSet1.next()) {
            totalElements = resultSet1.getLong(1);
        }
        final List<UserDTO> rows = new ArrayList<>();
        while (resultSet2.next()) {
            long id = resultSet2.getLong(1);
            String login = resultSet2.getString(2);
            String role = resultSet2.getString(4);

            rows.add(new UserDTO(id,login,role));
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
