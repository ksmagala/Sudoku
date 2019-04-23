package exception;


import java.util.function.Supplier;

public class ApplicationException extends Exception {
    public final static String UNEXPECTED_APPLICATION_EXCEPTION = "exception.application.unknown";

    public ApplicationException() {
        super(UNEXPECTED_APPLICATION_EXCEPTION);
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }


}
