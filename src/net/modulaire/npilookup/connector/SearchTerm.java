package net.modulaire.npilookup.connector;

public class SearchTerm {
  
  public static final int PROVIDER_TABLE = 0;
  public static final int NAME_TABLE = 1;
  public static final int ADDRESS_DATA_TABLE = 2;
  //public static final int TAXONOMY_DATA_TABLE = 3; TODO implement this
  
  public static SearchType SEARCH_BY_NPI = new SearchType(PROVIDER_TABLE);
  public static SearchType SEARCH_BY_FIRST_NAME = new SearchType(NAME_TABLE);
  public static SearchType SEARCH_BY_LAST_NAME = new SearchType(NAME_TABLE);
  //public static SearchType SEARCH_BY_TAXONOMY_DESCRIPTION = new SearchType(TAXONOMY_DATA_TABLE); TODO implement this
  public static SearchType SEARCH_BY_ORGANIZATION = new SearchType(NAME_TABLE);
  public static SearchType SEARCH_BY_CITY = new SearchType(ADDRESS_DATA_TABLE);
  public static SearchType SEARCH_BY_STATE = new SearchType(ADDRESS_DATA_TABLE);
  public static SearchType SEARCH_BY_COUNTRY = new SearchType(ADDRESS_DATA_TABLE);
  public static SearchType SEARCH_BY_POSTAL_CODE = new SearchType(ADDRESS_DATA_TABLE);
  
  private SearchType searchType;
  private String searchKey;
  
  @SuppressWarnings("unused")
  private SearchTerm() { } //let's not call this without arguments
  
  public SearchTerm(SearchType searchType, String searchKey) {
    this.searchType = searchType;
    this.searchKey = searchKey;
  }
  
  public SearchType getType() {
    return searchType;
  }
  
  public String getKey() {
    return searchKey;
  }
  
  public static class SearchType {
    
    private static int instanceCount = 0;
    
    private static SearchType[] searchTypes = new SearchType[8];
    
    private int id;
    private int parentTable;
    
    public SearchType(int parentTable) {
      this.id = instanceCount++;
      this.parentTable = parentTable;
      searchTypes[id] = this;
    }
    
    public int getParent() {
      return parentTable;
    }
    
    public int getId() {
      return id;
    }
    
    public static SearchType getTypeFromId(int id) {
      if(id < 0 || id >= searchTypes.length) return null;
      return searchTypes[id];
    }
    
    public SearchType getTypeById(int i) {
      
      if(i < 0 || i > searchTypes.length) return null;
      return searchTypes[i];
      
    }
    
  }
  
}
