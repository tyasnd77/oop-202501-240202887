package com.upb.agripos.exception;

/**
 * Custom exception untuk error database/DAO.
 */
public class DatabaseException extends Exception {
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
