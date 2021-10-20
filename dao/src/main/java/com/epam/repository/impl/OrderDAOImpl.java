package com.epam.repository.impl;


import com.epam.entity.Order;
import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.OrderDAO;
import com.epam.repository.PropertyInitializer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private static final String FIND_ORDER = "SELECT * FROM orders WHERE order_id = ?";
    private static final String SAVE_ORDER = "INSERT INTO orders VALUES (DEFAULT, ?, ?) ";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id = ?";
    private static final String UPDATE_ORDER = "UPDATE orders SET user_id = ?, product_id = ? WHERE order_id = ? ";
    private static final String GET_ALL_ORDERS = "SELECT * FROM orders";


    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);


    public OrderDAOImpl(ConnectionPool connectionPool) {
    }

    @Override
    public Order get(Order order) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_ORDER);
            statement.setLong(1,order.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                order.setId(resultSet.getLong(1));
                order.setProductId(resultSet.getLong(2));
                order.setUserId(resultSet.getLong(3));
            }if (order.getProductId() != 0 && order.getUserId() != 0){
                return order;
            }
            throw  new DAOException("unable to get the order");

        }catch (SQLException e){
            throw  new DAOException("dao exeption while getting order",e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }

    }

   /* @Override
    public Order getById(Long id) {
        return null;
    }*/

    /*@Override
    public List<Order> getAll() {
        return null;
    }*/

    @Override
    public Order getById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getAll() {
        ArrayList<Order> orders = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(GET_ALL_ORDERS);
            resultSet = statement.executeQuery();

            long orderId;
            long productId;
            long userId ;
            Order order;
            while(resultSet.next()){
                orderId = resultSet.getLong(1);
                productId = resultSet.getLong(2);
                userId = resultSet.getLong(3);
                order = new Order(orderId,productId,userId);
                orders.add(order);
            }
                return orders;


        }catch (SQLException e){
         throw new DAOException("unable to get All orders",e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean save(Order order) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SAVE_ORDER);
            statement.setLong(1,order.getProductId());
            statement.setLong(2,order.getUserId());
            return (statement.executeUpdate() != 0);
        }catch (SQLException e){
            throw  new DAOException("couldn't save order",e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(Order order) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(DELETE_ORDER);
            statement.setLong(1,order.getId());
            return (statement.executeUpdate() != 0);
        }catch (SQLException e){
            throw new DAOException("unable to delete order",e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean update(Order order) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(UPDATE_ORDER);
            statement.setLong(1,order.getProductId());
            statement.setLong(2,order.getUserId());
            return (statement.executeUpdate() != 0) ;
        }catch (SQLException e){
            throw new DAOException("unable to update order",e);
        }finally {
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
