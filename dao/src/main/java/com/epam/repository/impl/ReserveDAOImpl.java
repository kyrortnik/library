package com.epam.repository.impl;

import com.epam.entity.ReserveRow;
import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;
import com.epam.repository.ReserveDAO;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.epam.repository.utils.DBConstants.*;

public class ReserveDAOImpl extends AbstractDAO implements ReserveDAO {


    private static final String FIND_RESERVE_BY_ID = "SELECT * FROM reserves WHERE reserve_id = ?";
    private static final String SAVE_RESERVE = "INSERT INTO reserves VALUES (DEFAULT,?,?)";
    private static final String DELETE_RESERVE = "DELETE FROM reserves WHERE reserve_id = ?";
    private static final String FIND_RESERVE_BY_USER_AND_PRODUCT = " SELECT * FROM reserves WHERE user_id = ? AND product_id = ?";
    private static final String FIND_RESERVES_FOR_USER = "SELECT * FROM reserves WHERE user_id = ?";
    private static final String COUNT_RESERVES_FOR_USER = "SELECT COUNT(reserve_id) FROM reserves WHERE user_id = ?";
    private static final String DELETE_RESERVE_BY_USER_ID = " DELETE FROM reserves WHERE user_id = ?";
    private static final String FIND_RESERVES_BY_USER_ID_LIMIT_QUERY = "SELECT * FROM reserves WHERE user_id = ? LIMIT ? OFFSET ?";
//    private static final String FIND_RESERVES_FOR_USER ="SELECT reserve_id, reserves.user_id, reserves.product_id from reserves FULL JOIN orders ON reserves.user_id = orders.user_id WHERE reserves.user_id = ? AND order_id IS NULL";
//    private static final String FIND_ORDER_FOR_RESERVE = "SELECT reserve_id, reserves.product_id,reserves.user_id, orders.product_id,orders.user_id FROM reserves FULL JOIN orders ON reserves.user_id = orders.user_id WHERE orders.user_id = ? and orders.product_id LIKE ?";






    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);

    private static final Logger log = Logger.getLogger(ReserveDAOImpl.class.getName());



    @Override
    public ReserveRow find(ReserveRow reserveRow) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ReserveRow foundRow = new ReserveRow();
        Long reserveId = reserveRow.getId();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_RESERVE_BY_ID);
            statement.setLong(1,reserveId);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                foundRow.setId(resultSet.getLong(1));
                foundRow.setProductId(resultSet.getLong(2));
                foundRow.setUserId(resultSet.getLong(3));
                return foundRow;
            }
            return null;
        }catch (SQLException e){
            log.log(Level.SEVERE,"Exception: " + e );
            throw new DAOException(e);
        }finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public ReserveRow findById(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ReserveRow foundRow = new ReserveRow();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_RESERVE_BY_ID);
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                foundRow.setId(resultSet.getLong(1));
                foundRow.setProductId(resultSet.getLong(2));
                foundRow.setUserId(resultSet.getLong(3));
                return foundRow;
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
    public List<ReserveRow> getAll() {
        throw new NotImplementedException();
    }

//    @Override
//    public boolean orderForReserveExists(ReserveRow reserveRow) throws DAOException {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        String parameterForLikeQuery = "%" + String.valueOf(reserveRow.getProductId()) + "%";
//        try{
//            connection = connectionPool.getConnection();
//            statement = connection.prepareStatement(FIND_ORDER_FOR_RESERVE);
//            statement.setLong(1,reserveRow.getUserId());
//            statement.setString(2,parameterForLikeQuery);
//            if (statement.executeUpdate() > 0){
//                return  true;
//            }else
//                return false;
//        }catch (SQLException e){
//            throw new DAOException(e);
//        }finally {
//            closeStatement(statement);
//            connectionPool.releaseConnection(connection);
//        }
//
//    }

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
            log.log(Level.SEVERE,"Exception: " + e);
            throw  new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }


    @Override
    public boolean delete(ReserveRow reserve) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection  = connectionPool.getConnection();
            statement = connection.prepareStatement(DELETE_RESERVE);
            statement.setLong(1,reserve.getId());
            return statement.executeUpdate() > 0;
        }catch (Exception e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new DAOException(e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean update(ReserveRow reserve) {
        throw new NotImplementedException();
    }

    @Override
    public ReserveRow getByUserAndProductId(ReserveRow reserve) throws DAOException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            ReserveRow foundReserveRow;
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_RESERVE_BY_USER_AND_PRODUCT);
            statement.setLong(1,reserve.getUserId());
            statement.setLong(2,reserve.getProductId());
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                foundReserveRow = new ReserveRow(
                        resultSet.getLong(1),
                        resultSet.getLong(2),
                        resultSet.getLong(3)
                );
                return foundReserveRow;
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
    public List<ReserveRow> getReservesForUser(Long userId) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            ArrayList<ReserveRow> reserves = new ArrayList<>();
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
            log.log(Level.SEVERE,"Exception: " + e);
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
            if (resultSet.next()){
                numberOfReserves = resultSet.getInt(1);
            }
            return numberOfReserves;
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
    public boolean deleteByUserId(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(DELETE_RESERVE_BY_USER_ID);
            statement.setLong(1,userId);
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
    public List<ReserveRow> findReserveByUserId(long userId, int offset) throws DAOException {

        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            List<ReserveRow> reservations = new ArrayList<>();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(FIND_RESERVES_BY_USER_ID_LIMIT_QUERY);
            preparedStatement.setLong(1, userId);
            preparedStatement.setInt(2, MAX_ROWS);
            preparedStatement.setInt(3, offset);
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
            log.log(Level.SEVERE,"Exception: " + e);
            throw new DAOException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            connectionPool.releaseConnection(connection);
        }
    }


}
