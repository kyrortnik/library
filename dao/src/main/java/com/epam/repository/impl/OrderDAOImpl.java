package com.epam.repository.impl;


import com.epam.entity.Order;
import com.epam.exception.DAOException;
import com.epam.repository.AbstractDAO;
import com.epam.repository.ConnectionPool;
import com.epam.repository.OrderDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;


public class OrderDAOImpl extends AbstractDAO implements OrderDAO {


    private static final String FIND_ORDER_BY_USER_ID = "SELECT order_id, product_ids, user_id FROM orders WHERE user_id = ?";
    private static final String FIND_ORDER = "SELECT order_id, product_ids, user_id product FROM orders WHERE order_id = ?";
    private static final String SAVE_ORDER = "INSERT INTO orders VALUES (DEFAULT, ?, ?) ";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id = ?";
    private static final String UPDATE_ORDER = "UPDATE orders SET product_ids = ? WHERE user_id = ? ";
    private static final String DELETE_RESERVES_AFTER_ORDER = "DELETE FROM reserves WHERE user_id = ?";

    public OrderDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public Order findById(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Order order;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_ORDER);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = new Order();
                order.setId(resultSet.getLong(1));
                order.setProductIds(getListFromArray(resultSet, 2));
                order.setUserId(resultSet.getLong(3));
                return order;
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
    public boolean save(Order order) throws DAOException {
        List<Object> parameters;
        Connection connection = null;
        PreparedStatement findOrderStatement = null;
        PreparedStatement saveOrderStatement = null;
        PreparedStatement deleteReservesStatement = null;
        ResultSet findOrderResultSet = null;
        boolean result = false;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            parameters = Collections.singletonList(order.getUserId());
            findOrderStatement = getPreparedStatement(FIND_ORDER_BY_USER_ID, connection, parameters);
            findOrderResultSet = findOrderStatement.executeQuery();
            if (!findOrderResultSet.next()) {
                Array productIds = connection.createArrayOf("bigint", order.getProductIds().toArray());
                parameters = Arrays.asList(productIds, order.getUserId());
                saveOrderStatement = getPreparedStatement(SAVE_ORDER, connection, parameters);
                if (saveOrderStatement.executeUpdate() > 0) {
                    deleteReservesStatement = getPreparedStatement(DELETE_RESERVES_AFTER_ORDER, connection, Collections.singletonList(order.getUserId()));
                    result = deleteReservesStatement.executeUpdate() > 0;
                }

            }
            connection.commit();
            return result;
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
            closeResultSet(findOrderResultSet);
            closeStatement(findOrderStatement, saveOrderStatement, deleteReservesStatement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(DELETE_ORDER);
            statement.setLong(1, id);
            result = statement.executeUpdate() != 0;
            connection.commit();
            return result;
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
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public boolean update(Order order) throws DAOException {

        List<Long> productsToAdd = order.getProductIds();
        Connection connection = null;
        PreparedStatement updateOrderStatement = null;
        List<Object> parameters;
        boolean result = false;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            Order orderToUpdate = getByUserId(order.getUserId());

            if (nonNull(orderToUpdate)) {
                List<Long> productsInExistingOrder = orderToUpdate.getProductIds();
                boolean notOrdered = Collections.disjoint(productsInExistingOrder, productsToAdd);
                if (notOrdered) {
                    Array updatedProducts = connection.createArrayOf("bigint",
                            getOrderWithAddedProducts(productsInExistingOrder, productsToAdd).toArray());
                    parameters = Arrays.asList(updatedProducts, order.getUserId());

                    updateOrderStatement = getPreparedStatement(UPDATE_ORDER, connection, parameters);
                    result = updateOrderStatement.executeUpdate() != 0;
                }
            }
            connection.commit();
            return result;

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
            closeStatement(updateOrderStatement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean deleteFromOrder(Long userId, Long bookId) throws DAOException {
        List<Object> parameters;
        boolean result = false;
        Connection connection = null;
        PreparedStatement findOrderStatement = null;
        PreparedStatement updateOrderStatement = null;
        ResultSet findOrderResultSet = null;
        try {
//   Checks whether such order exists 
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            parameters = Collections.singletonList(userId);
            findOrderStatement = getPreparedStatement(FIND_ORDER_BY_USER_ID, connection, parameters);
            findOrderResultSet = findOrderStatement.executeQuery();
            if (findOrderResultSet.next()) {
//   When order exists- updates it removing asked product 
                Array updatedProducts = getUpdatedProductsArray(bookId, connection, findOrderResultSet);
                parameters = Arrays.asList(updatedProducts, userId);
                if (!productsAreEmpty(updatedProducts)) {
                    updateOrderStatement = getPreparedStatement(UPDATE_ORDER, connection, parameters);
                    result = updateOrderStatement.executeUpdate() != 0;
//  If no products to update - deletes order 
                } else {
                    long orderId = findOrderResultSet.getLong(1);
                    result = delete(orderId);
                }
            }
            connection.commit();
            return result;
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
            closeResultSet(findOrderResultSet);
            closeStatement(findOrderStatement, updateOrderStatement);
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public Order getByUserId(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_ORDER_BY_USER_ID);
            statement.setLong(1, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Order foundOrder = new Order();
                foundOrder.setId(resultSet.getLong(1));
                foundOrder.setProductIds(getListFromArray(resultSet, 2));
                foundOrder.setUserId(resultSet.getLong(3));
                if (!foundOrder.getProductIds().isEmpty()) {
                    return foundOrder;
                }
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

    private boolean productsAreEmpty(Array pgArray) throws SQLException {
        Long[] array = (Long[]) pgArray.getArray();
        return array.length == 0;
    }

    private List<Long> getOrderWithAddedProducts(List<Long> productsInExistingOrder, List<Long> productsToAdd) {
        return Stream.concat(productsInExistingOrder.stream(), productsToAdd.stream()).distinct().collect(Collectors.toList());
    }


    private Array getUpdatedProductsArray(Long bookId, Connection connection, ResultSet findOrderResultSet) throws SQLException {
        List<Long> productsInExistingOrder = getListFromArray(findOrderResultSet, 2);
        ArrayList<Long> list = new ArrayList<>(productsInExistingOrder);
        list.remove(bookId);
        return connection.createArrayOf("bigint", list.toArray());
    }


    private List<Long> getListFromArray(ResultSet resultSet, int columnIndex) throws SQLException {
        Array pgArray = resultSet.getArray(columnIndex);
        Long[] longArray = (Long[]) pgArray.getArray();
        return Arrays.asList(longArray);
    }

}
