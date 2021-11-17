package com.epam.repository.impl;


import com.epam.entity.Order;
import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.OrderDAO;
import com.epam.repository.PropertyInitializer;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAOImpl extends AbstractDAO implements OrderDAO {

    private static final String FIND_ORDER = "SELECT * FROM orders WHERE user_id = ?";
    private static final String FIND_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id = ?";
    private static final String GET_ALL_ORDERS = "SELECT * FROM orders";
    private static final String SAVE_ORDER = "INSERT INTO orders VALUES (DEFAULT, ?, ?) ";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id = ?";
    private static final String UPDATE_ORDER = "UPDATE orders SET product_id = ? WHERE user_id = ? ";
    private static final String FIND_BY_USED_ID = "SELECT * FROM orders WHERE user_id = ?";
//    private static final String FIND_ORDER_BY_USER_AND_PRODUCT = "SELECT * FROM orders WHERE user_id = ? AND product_id LIKE %?%";


    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);

    private static final Logger log = Logger.getLogger(OrderDAOImpl.class.getName());


    @Override
    public Order find(Order order) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_ORDER);
            statement.setLong(1,order.getUserId());
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                order.setId(resultSet.getLong(1));
                order.setProductIds(resultSet.getString(2));
                order.setUserId(resultSet.getLong(3));
                return order;
            }
            return null;

        }catch (SQLException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw  new DAOException(e);
        }finally {
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
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_ORDER_BY_ID);
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                order = new Order();
                order.setId(resultSet.getLong(1));
                order.setProductIds(resultSet.getString(2));
                order.setUserId(resultSet.getLong(3));
                return order;
            }
            return null;
        }catch (SQLException e){
        log.log(Level.SEVERE,"Exception: " + e);
        throw new DAOException(e);
        }finally {
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

        try{
            ArrayList<Order> orders = new ArrayList<>();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(GET_ALL_ORDERS);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                Order order = new Order();
                order.setId(resultSet.getLong(1));
                order.setProductIds(resultSet.getString(2));
                order.setUserId(resultSet.getLong(3));
                orders.add(order);
            }
                return orders;
        }catch (SQLException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new DAOException(e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean save(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SAVE_ORDER);
            statement.setString(1,order.getProductIds());
            statement.setLong(2,order.getUserId());
            return (statement.executeUpdate() != 0);
        }catch (SQLException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw  new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(DELETE_ORDER);
            statement.setLong(1,order.getId());
            return (statement.executeUpdate() != 0);
        }catch (SQLException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Updates products in order
     */
    @Override
    public boolean update(Order order) throws DAOException {
        String orderToAdd = order.getProductIds();
        Connection connection = null;
        PreparedStatement statement = null;
        Order orderToUpdate = find(order);
        if (orderToUpdate == null){
            return false;
        }
        String newProducts = orderToUpdate.getProductIds() +" "+ orderToAdd;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(UPDATE_ORDER);
            statement.setString(1,newProducts);
            statement.setLong(2,order.getUserId());
            return (statement.executeUpdate() != 0) ;
        }catch (SQLException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean deleteFromOrder(Order orderToUpdate,String newProducts) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(UPDATE_ORDER);
            statement.setString(1,newProducts);
            statement.setLong(2,orderToUpdate.getUserId());
            return statement.executeUpdate() > 0;
        }catch (SQLException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Order getByUserId(Long userId) throws DAOException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_BY_USED_ID);
            statement.setLong(1,userId);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                Order foundOrder = new Order();
                foundOrder.setId(resultSet.getLong(1));
                foundOrder.setProductIds(resultSet.getString(2));
                foundOrder.setUserId(resultSet.getLong(3));
                if(!foundOrder.getProductIds().equals("")){
                    return foundOrder;
                }
            }
            return null;
        }catch (SQLException e){
            log.log(Level.SEVERE,"Exception: "+ e);
            throw new DAOException(e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }
}
