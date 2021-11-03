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
    private static final String FIND_BY_USED_ID = "SELECT * FROM orders WHERE user_id = ?";
    private static final String SAVE_ORDER = "INSERT INTO orders VALUES (DEFAULT, ?, ?) ";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id = ?";
    private static final String UPDATE_ORDER = "UPDATE orders SET user_id = ?, product_id = ? WHERE order_id = ? ";
    private static final String GET_ALL_ORDERS = "SELECT * FROM orders";


    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);


    public OrderDAOImpl() {
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
                order.setProductIds(resultSet.getString(2));
                order.setUserId(resultSet.getLong(3));
            }if (!order.getProductIds().equals("") && order.getUserId() != 0){
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
            String productIds;
            long userId ;
            Order order;
            while(resultSet.next()){
                orderId = resultSet.getLong(1);
                productIds = resultSet.getString(2);
                userId = resultSet.getLong(3);
                order = new Order(orderId,productIds,userId);
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
            statement.setString(1,order.getProductIds());
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
            statement.setString(1,order.getProductIds());
            statement.setLong(2,order.getUserId());
            return (statement.executeUpdate() != 0) ;
        }catch (SQLException e){
            throw new DAOException("unable to update order",e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Order getByUserId(Order order) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Order temp = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_BY_USED_ID);
            statement.setLong(1,order.getUserId());
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                temp = new Order(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getLong(3)
                );
            }else{
                temp =  new Order();

            }
            return temp;
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeResultSet(resultSet);
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
            Order foundOrder = new Order();
            if(resultSet.next()){
                foundOrder.setId(resultSet.getLong(1));
                foundOrder.setProductIds(resultSet.getString(2));
                foundOrder.setUserId(resultSet.getLong(3));

            }
            else{
                return foundOrder;

            }
            return foundOrder;
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean deleteBbyUserId(Long userId) {
        Connection connection = null;
        PreparedStatement statement = null;

        return true;
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
