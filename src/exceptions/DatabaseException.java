package exceptions;

import java.sql.SQLException;

public class DatabaseException extends AuthorException {
    public DatabaseException(SQLException e) {
        super("Could not connect to database: " + e.getMessage());
    }
}
