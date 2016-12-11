package net.modulaire.npilookup.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

  private static int instanceCount = 0;
  
  private Connection connection;
  
  @SuppressWarnings("unused")
  private DBConnector() { } //we do not want this being called without arguments
  
  public DBConnector(String server, String username, String password) throws ConnectorException {
    
    if(instanceCount++ > 0) {
      throw new ConnectorException("More than one instance of DConnector was created.");
    }
    
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new ConnectorException("Exception thrown when calling the driver.", e);
    }
    
    try {
      connection = DriverManager.getConnection("jdbc:postgresql://" + server + "/npidata", username, password);
      connection.setAutoCommit(false);
    } catch (SQLException e) {
      throw new ConnectorException("Exception thrown when connecting to the database.", e);
    }
    
  }
  
  public void search(int searchType, SearchTerm... searchTerms) {
    
  }
  
  public void close() throws ConnectorException {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new ConnectorException("Exception thrown when trying to close the database.", e);
    }
  }
    
}
