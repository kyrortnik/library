package com.epam.repository.impl;

import com.epam.entity.Reserve;
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
    private static final String FIND_RESERVES_BY_USER_AND_PRODUCT = " SELECT * FROM reserves WHERE user_id = ? AND product_id = ?";

    PropertyInitializer propertyInitializer = new PropertyInitializer();
    protected ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);

    public ReserveDAOImpl(){

    }

    /*TODO provide implementation*/

    @Override
    public Reserve get(Reserve reserve) throws DAOException {
        return null;
    }

    @Override
    public Reserve getById(Long id) {
        return null;
    }



    @Override
    public List<Reserve> getAll() {
        return null;
    }

    @Override
    public boolean save(Reserve reserve) throws DAOException{
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
    public boolean delete(Reserve reserve) {
        return false;
    }

    @Override
    public boolean update(Reserve reserve) {
        return false;
    }

    @Override
    public Reserve getByUserAndProductId(Reserve reserve) throws DAOException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Reserve temp = new Reserve();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_RESERVES_BY_USER_AND_PRODUCT);
            statement.setLong(1,reserve.getUserId());
            statement.setLong(2,reserve.getProductId());
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                temp = new Reserve(
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
    public List<Reserve> getReservesForUser(Long userId) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Reserve> reserves = new ArrayList<>();
        try{

            Reserve reserve;
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(FIND_RESERVES_FOR_USER);
            statement.setLong(1,userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                reserve = new Reserve(
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
