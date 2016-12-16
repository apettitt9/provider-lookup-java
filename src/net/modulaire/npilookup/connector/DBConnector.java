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
  
  public RetrievedProviders search(SearchTerm... searchTerms) throws ConnectorException {
    return search(5, searchTerms);
  }
  
  public RetrievedProviders search(int limit, SearchTerm... searchTerms) throws ConnectorException {
    
    //String query = "SELECT * FROM provider p INNER JOIN name n ON n.pid = p.pid INNER JOIN address a ON a.provider = p.pid INNER JOIN address_data mad ON mad.adid = a.mailing_address INNER JOIN address_data pad ON pad.adid = a.practice_location WHERE";
    
    if(limit <= 0) throw new ConnectorException("Bad term limit was specified.");

    String query = "SELECT p.pid AS " + RetrievedProviders.PROVIDER_IDENTIFIER.getIdentifier() + ", " +
      "n.first_name AS " + RetrievedProviders.FIRST_NAME.getIdentifier() + ", " +
      "n.middle_name AS " + RetrievedProviders.MIDDLE_NAME.getIdentifier() + ", " +
      "n.last_name AS " + RetrievedProviders.LAST_NAME.getIdentifier() + ", " +
      "n.name_prefix AS " + RetrievedProviders.NAME_PREFIX.getIdentifier() + ", " +
      "n.name_suffix AS " + RetrievedProviders.NAME_SUFFIX.getIdentifier() + ", " +
      "mad.first_line AS " + RetrievedProviders.MAILING_ADDRESS_FIRST_LINE.getIdentifier() + ", " +
      "mad.second_line AS " + RetrievedProviders.MAILING_ADDRESS_SECOND_LINE.getIdentifier() + ", " +
      "mad.city AS " + RetrievedProviders.MAILING_ADDRESS_CITY.getIdentifier() + ", " +
      "mad.state AS " + RetrievedProviders.MAILING_ADDRESS_STATE.getIdentifier() + ", " +
      "mad.postal_code AS " + RetrievedProviders.MAILING_ADDRESS_POSTAL_CODE.getIdentifier() + ", " +
      "mad.country_code AS " + RetrievedProviders.MAILING_ADDRESS_COUNTRY_CODE.getIdentifier() + ", " +
      "mad.phone_number AS " + RetrievedProviders.MAILING_ADDRESS_PHONE_NUMBER.getIdentifier() + ", " +
      "mad.fax_number AS " + RetrievedProviders.MAILING_ADDRESS_FAX_NUMBER.getIdentifier() + ", " +
      "pad.first_line AS " + RetrievedProviders.PRACTICE_LOCATION_FIRST_LINE.getIdentifier() + ", " +
      "pad.second_line AS " + RetrievedProviders.PRACTICE_LOCATION_SECOND_LINE.getIdentifier() + ", " +
      "pad.city AS " + RetrievedProviders.PRACTICE_LOCATION_CITY.getIdentifier() + ", " +
      "pad.state AS " + RetrievedProviders.PRACTICE_LOCATION_STATE.getIdentifier() + ", " +
      "pad.postal_code AS " + RetrievedProviders.PRACTICE_LOCATION_POSTAL_CODE.getIdentifier() + ", " +
      "pad.country_code AS " + RetrievedProviders.PRACTICE_LOCATION_COUNTRY_CODE.getIdentifier() + ", " +
      "pad.phone_number AS " + RetrievedProviders.PRACTICE_LOCATION_PHONE_NUMBER.getIdentifier() + ", " +
      "pad.fax_number AS " + RetrievedProviders.PRACTICE_LOCATION_FAX_NUMBER.getIdentifier() + " FROM provider p " +
      "INNER JOIN name n ON n.pid = p.pid " +
      "INNER JOIN address a ON a.provider = p.pid " +
      "INNER JOIN address_data mad ON mad.adid = a.mailing_address " +
      "INNER JOIN address_data pad ON pad.adid = a.practice_location WHERE";

    PreparedStatement statement;
    
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
    
    query += " LIMIT " + limit;
    
    try {
      statement = connection.prepareStatement(query);
      statement.setFetchSize(100);
      
      ResultSet resultSet = statement.executeQuery();
      
      return new RetrievedProviders(resultSet);
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
