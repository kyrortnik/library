package repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface ConnectionPool {

   /* Connection provide();

    Connection release();*/

    Connection getConnection() throws SQLException;


}
