package net.modulaire.npilookup.connector;

public class ConnectorException extends Exception {

  private static final long serialVersionUID = 8035517939618729161L;

  ConnectorException() {
    super("A ConnectorException was thrown.");
  }
  
  ConnectorException(String message) {
    super(message);
  }
  
  ConnectorException(Throwable cause) {
    super(cause);
  }
  
  ConnectorException(String message, Throwable cause) {
    super(message, cause);
  }
  
}
