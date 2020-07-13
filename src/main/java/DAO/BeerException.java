package DAO;

import java.sql.SQLException;

public class BeerException extends Exception {

    public BeerException(SQLException e) {

    }

    public BeerException(String message) {
        super(message);
    }

    public BeerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeerException(Throwable cause) {
        super(cause);
    }
}
