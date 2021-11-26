package com.epam.repository.impl;


import com.epam.entity.Order;
import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.OrderDAO;


import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;


public class OrderDAOImpl extends AbstractDAO implements OrderDAO {

    private static final String FIND_ORDER = "SELECT * FROM orders WHERE user_id = ?";
    private static final String FIND_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id = ?";
    private static final String GET_ALL_ORDERS = "SELECT * FROM orders";
    private static final String SAVE_ORDER = "INSERT INTO orders VALUES (DEFAULT, ?, ?) ";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id = ?";
    private static final String UPDATE_ORDER = "UPDATE orders SET product_id = ? WHERE user_id = ? ";
    private static final String FIND_BY_USER_ID = "SELECT * FROM orders WHERE user_id = ?";
//    private static final String FIND_ORDER_BY_USER_AND_PRODUCT = "SELECT * FROM orders WHERE user_id = ? AND product_id LIKE %?%";

    private static final Logger LOG = Logger.getLogger(OrderDAOImpl.class.getName());

    public OrderDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }


    @Override
    public Order find(Order order) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_ORDER);
            statement.setLong(1, order.getUserId());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Order foundOrder = new Order();
                foundOrder.setId(resultSet.getLong(1));
                foundOrder.setProductIds((List<Long>) resultSet.getArray(2));
                foundOrder.setUserId(resultSet.getLong(3));
                return foundOrder;
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
    public Order findById(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Order order;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_ORDER_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = new Order();
                order.setId(resultSet.getLong(1));
                order.setProductIds((List<Long>) resultSet.getArray(2));
                order.setUserId(resultSet.getLong(3));
                return order;
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
    public List<Order> getAll() throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            ArrayList<Order> orders = new ArrayList<>();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(GET_ALL_ORDERS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getLong(1));
                order.setProductIds((List<Long>) resultSet.getArray(2));
                order.setUserId(resultSet.getLong(3));
                orders.add(order);
            }
            return orders;
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
    public boolean save(Order order) throws DAOException {
        List<Object> parameters;
        Connection connection = null;
        PreparedStatement findOrderStatement = null;
        PreparedStatement saveOrderStatement = null;
        ResultSet findOrderResultSet = null;
        boolean result;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            parameters = Collections.singletonList(order.getUserId());
            findOrderStatement = getPreparedStatement(FIND_BY_USER_ID, connection, parameters);
            findOrderResultSet = findOrderStatement.executeQuery();
            if (!findOrderResultSet.next()) {
                Array productIds = connection.createArrayOf("bigint", order.getProductIds().toArray());
                parameters = Arrays.asList(productIds, order.getUserId());
                saveOrderStatement = getPreparedStatement(SAVE_ORDER, connection, parameters);
                result = saveOrderStatement.executeUpdate() > 0;
            } else {
                result = false;
            }
            connection.commit();
            return result;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
            throw new DAOException(e);
        } finally {
            closeResultSet(findOrderResultSet);
            closeStatement(findOrderStatement, saveOrderStatement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(DELETE_ORDER);
            statement.setLong(1, id);
            return (statement.executeUpdate() != 0);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
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
        boolean result;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            Order orderToUpdate = getByUserId(order.getUserId());
            if (nonNull(orderToUpdate)) {
                List<Long> productsInExistingOrder = orderToUpdate.getProductIds();
                Array updatedProducts = connection.createArrayOf("bigint",
                        getOrderWithAddedProducts(productsInExistingOrder, productsToAdd).toArray());
                parameters = Arrays.asList(updatedProducts, order.getUserId());
                updateOrderStatement = getPreparedStatement(UPDATE_ORDER, connection, parameters);
                result = updateOrderStatement.executeUpdate() != 0;
                connection.commit();
            } else {
                result = false;
            }
            return result;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
            LOG.log(Level.SEVERE, "Exception: " + e);
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
        PreparedStatement checkEmptyProductsStatement = null;
        ResultSet findOrderResultSet = null;
        ResultSet checkProductsResultSet = null;
        try {
//   -------------1. Checks whether such order exists --------------------
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            parameters = Collections.singletonList(userId);
            findOrderStatement = getPreparedStatement(FIND_ORDER, connection, parameters);
            findOrderResultSet = findOrderStatement.executeQuery();
            if (findOrderResultSet.next()) {
//  -------------2. When order exists - updates it removing asked product --------------------
                Array updatedProducts = getUpdatedProductsArray(bookId, connection, findOrderResultSet);
                parameters = Arrays.asList(updatedProducts, userId);
                updateOrderStatement = getPreparedStatement(UPDATE_ORDER, connection, parameters);
//  -------------3. Check if products are empty after update - if so deletes the order --------------------
                if (updateOrderStatement.executeUpdate() != 0) {
                    parameters = Collections.singletonList(userId);
                    checkEmptyProductsStatement = getPreparedStatement(FIND_ORDER, connection, parameters);
                    checkProductsResultSet = checkEmptyProductsStatement.executeQuery();
                    if (checkProductsResultSet.next()) {
                        Long orderId = checkProductsResultSet.getLong(1);
                        result = !productsAreEmpty(checkProductsResultSet) || delete(orderId);
                    }
                }
            }
            connection.commit();
            return result;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new DAOException(e);
        } finally {
            closeResultSet(findOrderResultSet, checkProductsResultSet);
            closeStatement(findOrderStatement, updateOrderStatement, checkEmptyProductsStatement);
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
            statement = connection.prepareStatement(FIND_BY_USER_ID);
            statement.setLong(1, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Order foundOrder = new Order();
                foundOrder.setId(resultSet.getLong(1));
                Array pgArray = resultSet.getArray(2);
                Long[] longArray = (Long[]) pgArray.getArray();
                foundOrder.setProductIds(Arrays.asList(longArray));
                foundOrder.setUserId(resultSet.getLong(3));
                if (!foundOrder.getProductIds().isEmpty()) {
                    return foundOrder;
                }
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

    private boolean productsAreEmpty(ResultSet checkProductsResultSet) throws SQLException {
        Array pgArray = checkProductsResultSet.getArray(2);
        Long[] array = (Long[]) pgArray.getArray();
        return array.length == 0;
    }

    private List<Long> getOrderWithAddedProducts(List<Long> productsInExistingOrder, List<Long> productsToAdd) {
        return Stream.concat(productsInExistingOrder.stream(), productsToAdd.stream()).distinct().collect(Collectors.toList());
    }


    private Array getUpdatedProductsArray(Long bookId, Connection connection, ResultSet findOrderResultSet) throws SQLException {
        Array pgArray = findOrderResultSet.getArray(2);
        Long[] longArray = (Long[]) pgArray.getArray();
        List<Long> productsInExistingOrder = Arrays.asList(longArray);
        ArrayList<Long> list = new ArrayList<>(productsInExistingOrder);
        list.remove(bookId);
        return connection.createArrayOf("bigint", list.toArray());
    }


}
