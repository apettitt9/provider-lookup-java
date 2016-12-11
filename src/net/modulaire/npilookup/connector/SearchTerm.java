package net.modulaire.npilookup.connector;

public class SearchTerm {
  
  public static final int SEARCH_BY_NPI = 0;
  public static final int SEARCH_BY_FIRST_NAME = 1;
  public static final int SEARCH_BY_LAST_NAME = 2;
  public static final int SEARCH_BY_TAXONOMY_DESCRIPTION = 3;
  public static final int SEARCH_BY_ORGANIZATION = 4;
  public static final int SEARCH_BY_CITY = 5;
  public static final int SEARCH_BY_STATE = 6;
  public static final int SEARCH_BY_COUNTRY = 7;
  public static final int SEARCH_BY_POSTAL_CODE = 8;
  
  private int searchType;
  private String searchKey;
  
  @SuppressWarnings("unused")
  private SearchTerm() { } //let's not call this without arguments
  
  public SearchTerm(int searchType, String searchKey) {
    this.searchType = searchType;
    this.searchKey = searchKey;
  }
  
  public int getType() {
    return searchType;
  }
  
  public String getKey() {
    return searchKey;
  }
  
}
