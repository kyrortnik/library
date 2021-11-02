package com.epam.repository.impl;

import com.epam.entity.ReserveRow;
import com.epam.exception.DAOException;
import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;
import com.epam.repository.ReserveDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReserveDAOImpl implements ReserveDAO {


    private static final String SAVE_RESERVE = "INSERT INTO reserves VALUES (DEFAULT,?,?)";
    private static final String FIND_RESERVES_FOR_USER = " SELECT * FROM reserves WHERE user_id = ?";
    private static final String FIND_RESERVES_BY_USER_AND_PRODUCT = " SELECT * FROM reserves WHERE user_id = ? AND product_id = ?";
    private static final String DELETE_RESERVE_BY_USER_ID = " DELETE FROM reserves WHERE user_id = ?";

    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);

    public ReserveDAOImpl(){

    }

    /*TODO provide implementation*/

    @Override
    public ReserveRow get(ReserveRow reserve) throws DAOException {
        return null;
    }

    @Override
    public ReserveRow getById(Long id) {
        return null;
    }



    @Override
    public List<ReserveRow> getAll() {
        return null;
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
            throw  new DAOException("couldn't save reserve",e);
        }finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(ReserveRow reserve) {
        return false;
    }

    @Override
    public boolean update(ReserveRow reserve) {
        return false;
    }

    @Override
    public ReserveRow getByUserAndProductId(ReserveRow reserve) throws DAOException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ReserveRow temp = new ReserveRow();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_RESERVES_BY_USER_AND_PRODUCT);
            statement.setLong(1,reserve.getUserId());
            statement.setLong(2,reserve.getProductId());
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                temp = new ReserveRow(
                        resultSet.getLong(2),
                        resultSet.getLong(3)
                );
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
    public List<ReserveRow> getReservesForUser(Long userId) {

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
    public boolean deleteByUserId(Long userId) {
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
