package net.modulaire.npilookup.connector;

import java.sql.Connection;

public class DBConnector {

  private static int instanceCount = 0;
  
  private Connection connection;
  
  @SuppressWarnings("unused")
  private DBConnector() { } //we do not want this being called without arguments
  
  public DBConnector(String server, String username, String password) throws ConnectorException {
    
    if(++instanceCount > 0) {
      throw new ConnectorException("More than one instance of DConnector was created.");
    }
    
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new ConnectorException("Exception thrown when calling the driver.", e);
    }
    
  }
  
  public void search(int searchType, SearchTerm... searchTerms) {
    //TODO build query string with this
  }
    
}
