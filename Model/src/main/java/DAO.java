import exception.ApplicationException;
import exception.NotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface DAO<T> {
 T read() throws ApplicationException, NotFoundException;
  void write(T t) throws ApplicationException, NotFoundException;
}
