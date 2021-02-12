package cellsociety.controller;

import java.io.Serial;

public class XMLException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public XMLException(String message, Object... values) {
    super(String.format(message, values));
  }

  public XMLException(Throwable cause, String message, Object... values) {
    super(String.format(message, values), cause);
  }

  public XMLException(Throwable cause) {
    super(cause);
  }


}
