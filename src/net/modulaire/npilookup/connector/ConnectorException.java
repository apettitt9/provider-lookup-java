package net.modulaire.npilookup.connector;

public class ConnectorException extends Exception {

  private static final long serialVersionUID = 8035517939618729161L;

  public ConnectorException() {
    super("A ConnectorException was thrown.");
  }
  
  public ConnectorException(String message) {
    super(message);
  }
  
  public ConnectorException(Throwable cause) {
    super(cause);
  }
  
  public ConnectorException(String message, Throwable cause) {
    super(message, cause);
  }
  
}
