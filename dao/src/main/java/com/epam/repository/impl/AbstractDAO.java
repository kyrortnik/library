package com.epam.repository.impl;

import com.epam.repository.ConnectionPool;

import javax.sql.rowset.serial.SerialArray;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDAO {

    protected final ConnectionPool connectionPool;

    public AbstractDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private static final Logger LOG = Logger.getLogger(AbstractDAO.class.getName());


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
        } else{
            preparedStatement.setArray(queryParameterIndex,(Array) parameter);
        }
    }


    protected void closeResultSet(ResultSet ...resultSets) {
        if (resultSets != null){
            for (ResultSet rs : resultSets) {
                if (Objects.nonNull(rs)) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        LOG.log(Level.SEVERE, "Exception: " + e);
                    }
                }
            }
        }
    }

    protected void closeStatement(PreparedStatement ...statements){
        if (statements != null){
            for (PreparedStatement statement : statements){
                if (Objects.nonNull(statement)){
                    try{
                        statement.close();
                    }catch (SQLException e){
                        LOG.log(Level.SEVERE,"Exception: " + e);
                    }
                }
            }
        }
    }

}
