package com.kraftics.krafticslib.database;

/**
 * Exception using when {@link Database} error occurs
 *
 * @see Database
 * @author Panda885
 */
public class DatabaseException extends Exception {

    public DatabaseException() {
        super();
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
