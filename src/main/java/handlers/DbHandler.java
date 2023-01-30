package handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DbHandler {
    public String handle(ResultSet resultSet) throws SQLException;
}
