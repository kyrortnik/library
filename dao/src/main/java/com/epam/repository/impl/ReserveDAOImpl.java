package com.epam.repository.impl;

import com.epam.entity.ReserveRow;
import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;
import com.epam.repository.ReserveDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReserveDAOImpl implements ReserveDAO {


    private static final String SAVE_RESERVE = "INSERT INTO reserves VALUES (DEFAULT,?,?)";
    private static final String FIND_RESERVES_FOR_USER = " SELECT * FROM reserves WHERE user_id = ?";
//    private static final String FIND_RESERVES_FOR_USER ="SELECT reserve_id, reserves.user_id, reserves.product_id from reserves FULL JOIN orders ON reserves.user_id = orders.user_id WHERE reserves.user_id = ? AND order_id IS NULL";
    private static final String FIND_RESERVE_IN_ORDER = " SELECT * FROM reserves WHERE user_id = ? AND product_id = ?";
    private static final String FIND_ORDER_FOR_RESERVE = "SELECT reserve_id, reserves.product_id,reserves.user_id, orders.product_id,orders.user_id FROM reserves FULL JOIN orders ON reserves.user_id = orders.user_id WHERE orders.user_id = ? and orders.product_id LIKE ?";
    private static final String DELETE_RESERVE_BY_USER_ID = " DELETE FROM reserves WHERE user_id = ?";
    private static final String COUNT_RESERVES_FOR_USER = "SELECT COUNT(reserve_id) FROM reserves WHERE user_id = ?";
    private static final String FIND_RESERVES_BY_USER_ID_LIMIT_QUERY = "SELECT * FROM reserves WHERE user_id = ? LIMIT ? OFFSET ?";
//    private static final String FIND_RESERVES_BY_USER = "SELECT * FROM reserves WHERE user_id = ?";

    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);

    private static final int MAX_ROWS = 5;


    /*TODO provide implementation*/
    /**Functionality not yet implemented*/

    @Override
    public ReserveRow get(ReserveRow reserve) throws DAOException {
     /*   Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ReserveRow foundRow = new ReserveRow();
        Long userId = reserve.getUserId();
        Long productId = reserve.getProductId();

        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_RESERVE_IN_ORDER);
            statement.setLong(1,userId);
            statement.setString(2,String.valueOf(productId));
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                foundOrder.setId(resultSet.getLong(1));
                foundOrder.setProductIds(resultSet.getString(2));
                foundOrder.setUserId(resultSet.getLong(3));
            }
            return foundOrder;
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }*/
        return null;
    }

    /**Functionality not yet implemented*/

    @Override
    public ReserveRow getById(Long id) {
        return null;
    }


    /**Functionality not yet implemented*/
    @Override
    public List<ReserveRow> getAll() {
        return null;
    }

    @Override
    public boolean orderForReserveExists(ReserveRow reserveRow) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        String parameterForLikeQuery = "%" + String.valueOf(reserveRow.getProductId()) + "%";
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_ORDER_FOR_RESERVE);
            statement.setLong(1,reserveRow.getUserId());
            statement.setString(2,parameterForLikeQuery);
            if (statement.executeUpdate() > 0){
                return  true;
            }else
                return false;
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }

    }

    @Override
    public boolean save(ReserveRow reserve) throws DAOException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SAVE_RESERVE);
            statement.setLong(1,reserve.getUserId());
            statement.setLong(2,reserve.getProductId());
            return (statement.executeUpdate() != 0);
        }catch (SQLException e){
            throw  new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    /**Functionality not yet implemented*/
    @Override
    public boolean delete(ReserveRow reserve) {
        return false;
    }

    /**Functionality not yet implemented*/
    @Override
    public boolean update(ReserveRow reserve) {
        return false;
    }

    @Override
    public ReserveRow getByUserAndProductId(ReserveRow reserve) throws DAOException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ReserveRow foundReserveRow = new ReserveRow();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_RESERVE_IN_ORDER);
            statement.setLong(1,reserve.getUserId());
            statement.setLong(2,reserve.getProductId());
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                foundReserveRow = new ReserveRow(
                        resultSet.getLong(2),
                        resultSet.getLong(3)
                );
            }
            return foundReserveRow;
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }

    }

    @Override
    public List<ReserveRow> getReservesForUser(Long userId) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<ReserveRow> reserves = new ArrayList<>();
        try{

            ReserveRow reserve;
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_RESERVES_FOR_USER);
            statement.setLong(1,userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                reserve = new ReserveRow(
                        resultSet.getLong(1),
                        resultSet.getLong(2),
                        resultSet.getLong(3)
                );
                reserves.add(reserve);
            }
            return reserves;
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public int countReservesForUser(long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int numberOfReserves = 0;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(COUNT_RESERVES_FOR_USER);
            statement.setLong(1,userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                numberOfReserves = resultSet.getInt(1);
            }
            return numberOfReserves;
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean deleteByUserId(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(DELETE_RESERVE_BY_USER_ID);
            statement.setLong(1,userId);
            int numberOfUpdates = statement.executeUpdate();
            if (numberOfUpdates > 0){
                return true;
            }else {
                return false;
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }



    @Override
    public List<ReserveRow> findReservationsByUserId(long userId, int row) throws DAOException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(FIND_RESERVES_BY_USER_ID_LIMIT_QUERY);
//            preparedStatement = connection.prepareStatement(FIND_RESERVES_BY_USER);
            preparedStatement.setLong(1, userId);
            preparedStatement.setInt(2, MAX_ROWS);
            preparedStatement.setInt(3, row);
            List<ReserveRow> reservations = new ArrayList<>();
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ReserveRow reserve = new ReserveRow(
                        resultSet.getLong(1),
                        userId,
                        resultSet.getLong(3));
                reservations.add(reserve);
            }
            return reservations;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            connectionPool.releaseConnection(connection);
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
