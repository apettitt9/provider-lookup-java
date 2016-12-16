package net.modulaire.npilookup.connector;

public class SearchTerm {
  
  public static SearchType SEARCH_BY_NPI = new SearchType();
  public static SearchType SEARCH_BY_FIRST_NAME = new SearchType();
  public static SearchType SEARCH_BY_LAST_NAME = new SearchType();
  public static SearchType SEARCH_BY_ORGANIZATION = new SearchType();
  public static SearchType SEARCH_BY_CITY = new SearchType();
  public static SearchType SEARCH_BY_STATE = new SearchType();
  public static SearchType SEARCH_BY_COUNTRY = new SearchType();
  public static SearchType SEARCH_BY_POSTAL_CODE = new SearchType();
  
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
    
    public SearchType() {
      this.id = instanceCount++;
      searchTypes[id] = this;
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
