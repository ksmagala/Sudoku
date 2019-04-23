package exception;

public class DBException extends ApplicationException {
    public final static String DATABASE_EXCEPTION = "exception.application.database";
    public DBException() {
        super(DATABASE_EXCEPTION);
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException(Throwable cause) {
        super(cause);
    }
}
