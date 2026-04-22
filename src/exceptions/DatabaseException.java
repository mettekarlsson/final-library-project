package exceptions;

import java.sql.SQLException;

public class DatabaseException extends LibraryException {
    public DatabaseException(SQLException e) {
        super("Could not connect to database: " + e.getMessage());
    }
}
