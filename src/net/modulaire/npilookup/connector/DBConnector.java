package net.modulaire.npilookup.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    } catch(ClassNotFoundException e) {
      throw new ConnectorException("Exception thrown when initializing the PostgreSQL driver.", e);
    }
    
    try {
      connection = DriverManager.getConnection("jdbc:postgresql://" + server + "/npidata", username, password);
      connection.setAutoCommit(false);
    } catch(SQLException e) {
      throw new ConnectorException("Exception thrown when connecting to the database.", e);
    }
    
  }
  
  public ResultSet search(SearchTerm... searchTerms) throws ConnectorException {
    
    PreparedStatement statement;
    
    String query = "SELECT * FROM provider p"; //assume that we're always going to use the Provider table
    boolean onNameTable = false,
            onAddressDataTable = false;
    
    for(int i = 0; i < searchTerms.length; i++) {
      switch(searchTerms[i].getType().getParent()) {
      case SearchTerm.NAME_TABLE:
        onNameTable = true;
        break;
      case SearchTerm.ADDRESS_DATA_TABLE:
        onAddressDataTable = true;
      }
    }
    
    if(onNameTable) query += " INNER JOIN name n ON n.pid = p.pid";
    if(onAddressDataTable) query += " INNER JOIN address a ON a.provider = p.pid INNER JOIN address_data mad ON mad.adid = a.mailing_address INNER JOIN address_data pad ON pad.adid = a.practice_location";
    
    query += " WHERE";
    
    for(int i = 0; i < searchTerms.length; i++) {
      
      if(i > 0) query += " AND";
      
      if(searchTerms[i].getType() == SearchTerm.SEARCH_BY_NPI) query += " p.pid = '" + searchTerms[i].getKey() + "'";
      if(searchTerms[i].getType() == SearchTerm.SEARCH_BY_FIRST_NAME) query += " n.first_name LIKE '%" + searchTerms[i].getKey() + "%'";
      if(searchTerms[i].getType() == SearchTerm.SEARCH_BY_LAST_NAME) query += " n.last_name LIKE '%" + searchTerms[i].getKey() + "%'";
      if(searchTerms[i].getType() == SearchTerm.SEARCH_BY_ORGANIZATION) query += " n.org_business_name LIKE '%" + searchTerms[i].getKey() + "%'";
      if(searchTerms[i].getType() == SearchTerm.SEARCH_BY_CITY) query += " (mad.city LIKE '%" + searchTerms[i].getKey() + "%' OR pad.city LIKE '%" + searchTerms[i].getKey() + "%')";
      if(searchTerms[i].getType() == SearchTerm.SEARCH_BY_STATE) query += " (mad.state ='" + searchTerms[i].getKey() + "' OR pad.state = '" + searchTerms[i].getKey() + "')";
      if(searchTerms[i].getType() == SearchTerm.SEARCH_BY_COUNTRY) query += " (mad.country_code = '" + searchTerms[i].getKey() + "' OR pad.country_code ='" + searchTerms[i].getKey() + "')";
      if(searchTerms[i].getType() == SearchTerm.SEARCH_BY_POSTAL_CODE) query += " (mad.postal_code = '" + searchTerms[i].getKey() + "' OR pad.postal_code ='" + searchTerms[i].getKey() + "')";
    }
    
    query += " LIMIT 100";
    
    try {
      statement = connection.prepareStatement(query);
      statement.setFetchSize(100);
      System.out.println();
      System.out.println(query);
      System.out.println();
      return statement.executeQuery();
    } catch (SQLException e) {
      throw new ConnectorException("Exception thrown when building the query.", e);
    }
    
  }
  
  public void close() throws ConnectorException {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new ConnectorException("Exception thrown when trying to close the database.", e);
    }
  }
    
}
