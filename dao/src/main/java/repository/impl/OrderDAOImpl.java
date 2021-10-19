package repository.impl;

import entity.Order;
import exception.DAOException;
import repository.ConnectionPool;
import repository.OrderDAO;
import repository.PropertyInitializer;

import java.sql.*;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private static final String FIND_ORDER = "SELECT * FROM orders WHERE order_id = ?";
    private static final String SAVE_ORDER = "INSERT INTO orders VALUES (?, ?) ";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id = ?";
    private static final String UPDATE_ORDER = "UPDATE orders SET user_id = ?, product_id = ? WHERE order_id = ? ";


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
    public boolean save(Order order) {
        return false;
    }

    @Override
    public boolean delete(Order order) {
        return false;
    }

    @Override
    public boolean update(Order order) {
        return false;
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
