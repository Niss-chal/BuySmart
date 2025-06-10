
package buysmart.database;
import java.sql.*;
/**
 *
 */
public interface DbConnection {
    Connection openConnect();
    void closeConnection(Connection conn);
    ResultSet runQuery(Connection conn, String query);
    int executeUpdate(Connection conn, String query);
}
