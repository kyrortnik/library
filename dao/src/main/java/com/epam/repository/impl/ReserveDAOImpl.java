package com.epam.repository.impl;

import com.epam.entity.ReserveRow;
import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.ReserveDAO;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReserveDAOImpl extends AbstractDAO implements ReserveDAO {

    private static final Logger LOG = Logger.getLogger(ReserveDAOImpl.class.getName());

    private static final String FIND_RESERVE_BY_ID = "SELECT * FROM reserves WHERE reserve_id = ?";
    private static final String SAVE_RESERVE = "INSERT INTO reserves VALUES (DEFAULT,?,?)";
    private static final String DELETE_RESERVE = "DELETE FROM reserves WHERE reserve_id = ?";
    private static final String FIND_RESERVE_BY_USER_AND_PRODUCT = " SELECT * FROM reserves WHERE user_id = ? AND product_id = ?";
    private static final String DELETE_BY_USER_AND_PRODUCT = "DELETE FROM reserves WHERE user_id= ? AND product_id = ?";


    public ReserveDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }


    @Override
    public ReserveRow findById(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ReserveRow foundRow = new ReserveRow();
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_RESERVE_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foundRow.setId(resultSet.getLong(1));
                foundRow.setProductId(resultSet.getLong(2));
                foundRow.setUserId(resultSet.getLong(3));
                return foundRow;
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
    public boolean save(ReserveRow reserve) throws DAOException {
        Connection connection = null;
        PreparedStatement findReserveStatement = null;
        PreparedStatement saveReserveStatement = null;
        List<Object> parameters = Arrays.asList(reserve.getUserId(), reserve.getProductId());
        boolean result;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            findReserveStatement = getPreparedStatement(FIND_RESERVE_BY_USER_AND_PRODUCT, connection, parameters);
            if (!findReserveStatement.executeQuery().next()) {
                saveReserveStatement = getPreparedStatement(SAVE_RESERVE, connection, parameters);
                result = saveReserveStatement.executeUpdate() > 0;

            } else {
                result = false;
            }
            connection.commit();
            return result;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
            throw new DAOException(e);
        } finally {
            closeStatement(findReserveStatement, saveReserveStatement);
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public boolean delete(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(DELETE_RESERVE);
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public boolean deleteByUserAndProduct(Long userId, Long bookId) throws DAOException {
        List<Object> parameters = Arrays.asList(userId, bookId);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = getPreparedStatement(DELETE_BY_USER_AND_PRODUCT, connection, parameters);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }

    }

    @Override
    public boolean update(ReserveRow reserveRow) throws DAOException {
        throw new NotImplementedException();
    }

}
