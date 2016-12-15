package net.modulaire.npilookup.connector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RetrievedProviders {
  
  public static final ResultType PROVIDER_IDENTIFIER = new ResultType("pid");
  public static final ResultType FIRST_NAME = new ResultType("first_name");
  public static final ResultType MIDDLE_NAME = new ResultType("middle_name");
  public static final ResultType LAST_NAME = new ResultType("last");
  public static final ResultType NAME_PREFIX = new ResultType("prefix");
  public static final ResultType NAME_SUFFIX = new ResultType("suffix");
  public static final ResultType MAILING_ADDRESS_FIRST_LINE = new ResultType("mafirst");
  public static final ResultType MAILING_ADDRESS_SECOND_LINE = new ResultType("masecond");
  public static final ResultType MAILING_ADDRESS_CITY = new ResultType("macity");
  public static final ResultType MAILING_ADDRESS_STATE = new ResultType("mastate");
  public static final ResultType MAILING_ADDRESS_POSTAL_CODE = new ResultType("mapostal");
  public static final ResultType MAILING_ADDRESS_COUNTRY_CODE = new ResultType("macountry");
  public static final ResultType MAILING_ADDRESS_PHONE_NUMBER = new ResultType("maphone");
  public static final ResultType MAILING_ADDRESS_FAX_NUMBER = new ResultType("mafax");
  public static final ResultType PRACTICE_LOCATION_FIRST_LINE = new ResultType("plfirst");
  public static final ResultType PRACTICE_LOCATION_SECOND_LINE = new ResultType("plsecond");
  public static final ResultType PRACTICE_LOCATION_CITY = new ResultType("plcity");
  public static final ResultType PRACTICE_LOCATION_STATE = new ResultType("plstate");
  public static final ResultType PRACTICE_LOCATION_COUNTRY_CODE = new ResultType("plcountry");
  public static final ResultType PRACTICE_LOCATION_PHONE_NUMBER = new ResultType("plphone");
  public static final ResultType PRACTICE_LOCATION_FAX_NUMBER = new ResultType("plfax");
  public static final ResultType MAIN_TAXONOMY_TYPE = new ResultType("taxonomy");
  
  private int cursor;
  private int size;
  private String[][] results;

  public RetrievedProviders(ResultSet resultSet) throws ConnectorException {
    
    cursor = -1;
    
    try {
      resultSet.last();
      size = resultSet.getRow();
      resultSet.beforeFirst();
    } catch (SQLException e) {
      size = 0;
    }
    
    try {
      if(size > 0) {
        results = new String[size][22];
        for(int i = 0; i < size; i++) {
          for(int j = 0; j < 22; j++) {
            results[i][j] = resultSet.getString(ResultType.getIdentifier(j));
          }
        }
      }
    } catch(SQLException e) {
      throw new ConnectorException("Issue occurred when building the RetrievedProviders double array.", e);
    }
    
  }
  
  public void resetCursor() {
    cursor = -1;
  }
  
  public boolean next() {
    if(cursor >= size) return false;
    cursor++;
    return true;
  }
  
  public String getValue(ResultType resultType) {
    return getValue(resultType.getId());
  }
  
  public String getValue(int index) {
    if(cursor >= size || index < 0 || index > 22) return null;
    return results[cursor][index];
  }
  
  public static class ResultType {
    
    private static int instanceCount = 0;
    
    private static ResultType[] resultTypes = new ResultType[22];
    
    private int id;
    private String identifier;
    
    public ResultType(String identifier) {
      this.id = instanceCount++;
      this.identifier = identifier;
      resultTypes[id] = this;
    }
    
    public int getId() {
      return id;
    }
    
    public String getIdentifier() {
      return identifier;
    }
    
    public static String getIdentifier(int i) {
      if(i < 0 || i >= instanceCount) return null;
      return resultTypes[i].getIdentifier();
    }
    
  }
  
}
