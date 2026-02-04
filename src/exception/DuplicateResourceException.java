package exception;

import java.sql.SQLException;

/**
 * Exception thrown when attempting to create a duplicate resource
 * Demonstrates exception hierarchy
 */
public class DuplicateResourceException extends InvalidInputException {
    public DuplicateResourceException(String message, SQLException e) {
        super(message);
    }
}