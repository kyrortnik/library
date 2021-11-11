package com.epam.repository.impl;


import com.epam.entity.Order;
import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.OrderDAO;
import com.epam.repository.PropertyInitializer;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OrderDAOImpl implements OrderDAO {

    private static final String FIND_ORDER = "SELECT * FROM orders WHERE user_id = ?";
    private static final String FIND_ORDER_BY_USER_AND_PRODUCT = "SELECT * FROM orders WHERE user_id = ? AND product_id LIKE %?%";
    private static final String FIND_BY_USED_ID = "SELECT * FROM orders WHERE user_id = ?";
    private static final String SAVE_ORDER = "INSERT INTO orders VALUES (DEFAULT, ?, ?) ";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id = ?";
    private static final String UPDATE_ORDER = "UPDATE orders SET product_id = ? WHERE user_id = ? ";
    private static final String GET_ALL_ORDERS = "SELECT * FROM orders";


    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);
    private static final Logger log = Logger.getLogger(OrderDAOImpl.class.getName());


    public OrderDAOImpl() {
    }

    @Override
    public Order get(Order order) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_ORDER);
            statement.setLong(1,order.getUserId());
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                order.setId(resultSet.getLong(1));
                order.setProductIds(resultSet.getString(2));
                order.setUserId(resultSet.getLong(3));
            }if (!order.getProductIds().equals("") && order.getUserId() != 0){
                return order;
            }
            log.info("Couldn't find requested Order");
            return null;

        }catch (SQLException e){
            throw  new DAOException(e);
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



    /**Functionality not yet implemented*/
    @Override
    public Order getById(Long id) {
        throw new UnsupportedOperationException();
    }

    //TODO refactor order creation in the loop
    @Override
    public List<Order> getAll() throws DAOException {
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
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean update(Order order) throws DAOException {
        String orderToAdd = order.getProductIds();
        Connection connection = null;
        PreparedStatement statement = null;
        Order orderToUpdate = get(order);
        String newProducts = orderToUpdate.getProductIds() +" "+ orderToAdd;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(UPDATE_ORDER);
            statement.setString(1,newProducts);
            statement.setLong(2,order.getUserId());
            return (statement.executeUpdate() != 0) ;
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

 /*   @Override
    public Order getByUserId(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Order foundOrder = new Order();
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_BY_USED_ID);
            statement.setLong(1,order.getUserId());
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                foundOrder = new Order(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getLong(3)
                );
            }
            return foundOrder;
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }*/
/**
 * Realisation with return null ?
 * */
    @Override
    public Order getByUserId(Long userId) throws DAOException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Order foundOrder = new Order();

        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_BY_USED_ID);
            statement.setLong(1,userId);
            resultSet = statement.executeQuery();

            if(resultSet.next()){
                foundOrder.setId(resultSet.getLong(1));
                foundOrder.setProductIds(resultSet.getString(2));
                foundOrder.setUserId(resultSet.getLong(3));
                if(!foundOrder.getProductIds().equals("")){
                    return foundOrder;
                }
            }
            return null;
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }



    /**Functionality not yet implemented*/

    @Override
    public boolean deleteByUserId(Long userId) {
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
