package exception;

import java.io.IOException;

public class FileOperationsException  extends ApplicationException{
    public final static String FILE_OPERATIONS_EXCEPTION = "exception.application.fileoperations";
    public FileOperationsException() {
        super(FILE_OPERATIONS_EXCEPTION);
    }

    public FileOperationsException(String message) {
        super(message);
    }

    public FileOperationsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileOperationsException(Throwable cause) {
        super(cause);
    }
}
