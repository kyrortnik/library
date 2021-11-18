package com.epam.repository.impl;

import com.epam.repository.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDAO {

    protected final ConnectionPool connectionPool;

    public AbstractDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private static final Logger log = Logger.getLogger(AbstractDAO.class.getName());


    protected PreparedStatement getPreparedStatement(String query, Connection connection,
                                                     List<Object> parameters) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        setPreparedStatementParameters(preparedStatement, parameters);
        return preparedStatement;
    }

    protected void setPreparedStatementParameters( PreparedStatement preparedStatement,
                                                   List<Object> parameters) throws SQLException {
        for (int i = 0, queryParameterIndex = 1; i < parameters.size(); i++, queryParameterIndex++) {
            final Object parameter = parameters.get(i);
            setPreparedStatementParameter(preparedStatement, queryParameterIndex, parameter);
        }
    }

    protected void setPreparedStatementParameter( PreparedStatement preparedStatement,
                                                  int queryParameterIndex,  Object parameter) throws SQLException {
        if (Long.class == parameter.getClass()) {
            preparedStatement.setLong(queryParameterIndex, (Long) parameter);
        } else if (Integer.class == parameter.getClass()){
            preparedStatement.setInt(queryParameterIndex, (Integer) parameter);
        } else if (String.class == parameter.getClass()){
            preparedStatement.setString(queryParameterIndex, (String) parameter);
        } else if (Boolean.class == parameter.getClass()){
            preparedStatement.setBoolean(queryParameterIndex, (Boolean) parameter);
        }
    }


    protected void closeResultSet(ResultSet ...resultSets) {
        try{
            if (resultSets != null){
                for (ResultSet rs : resultSets){
                    rs.close();
                }
            }
        }catch (SQLException e){
            log.log(Level.SEVERE,"Exception: " + e);
        }
    }
    protected void closeStatement(PreparedStatement ...statements){
        try{
            if (statements != null){
                for (PreparedStatement statement : statements){
                    statement.close();
                }
            }
        }catch (SQLException e){
            log.log(Level.SEVERE,"Exception: " + e);
        }
    }

}
