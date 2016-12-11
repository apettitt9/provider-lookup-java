package net.modulaire.npilookup;

import java.util.Scanner;

import net.modulaire.npilookup.connector.DBConnector;
import net.modulaire.npilookup.connector.SearchTerm;
import net.modulaire.npilookup.connector.SearchTerm.SearchType;

public class DBCTestDriver {
  
  DBConnector dbc;

  public DBCTestDriver() {
    println("Hello there. I'm a driver that tests the database connector.");
  }
  
  public DBCTestDriver(DBConnector dbc) {
    super();
    this.dbc = dbc;
    println("Ok, cool, I now know where the database is.");
  }
  
  public int runTests() {
    println("Alrighty, let me make sure everything is in order to start...");
    
    if(dbc == null) { //make sure that we have the connector
      println("I don't seem to know where the DBConnector is. Sorry. Going home.");
      return -1;
    }
    
    println("Sweet, I still remember where the connector is.");
    println("Testing the driver now (requires manual intervention, please).");

    Scanner keyboard = new Scanner(System.in);
    
    while(true) {
      
      int searchTypeId;
      SearchType searchType;
      String searchKey;
      
      println("\nHere are your choices:");
      println("SEARCH_BY_NPI (" + SearchTerm.SEARCH_BY_NPI.getId() + ")");
      println("SEARCH_BY_FIRST_NAME (" + SearchTerm.SEARCH_BY_FIRST_NAME.getId() + ")");
      println("SEARCH_BY_LAST_NAME (" + SearchTerm.SEARCH_BY_LAST_NAME.getId() + ")");
      println("SEARCH_BY_ORGANIZATION (" + SearchTerm.SEARCH_BY_ORGANIZATION.getId() + ")");
      println("SEARCH_BY_CITY (" + SearchTerm.SEARCH_BY_CITY.getId() + ")");
      println("SEARCH_BY_STATE (" + SearchTerm.SEARCH_BY_STATE.getId() + ")");
      println("SEARCH_BY_COUNTRY (" + SearchTerm.SEARCH_BY_COUNTRY.getId() + ")");
      println("SEARCH_BY_POSTAL_CODE (" + SearchTerm.SEARCH_BY_POSTAL_CODE.getId() + ")");
      print("Please enter one of those numbers: ");
      
      try {
        searchTypeId = Integer.parseInt(keyboard.nextLine());
      } catch(Exception e) {
        println("It appears that invalid text was entered. Please try again.");
        continue;
      }
      
      searchType = SearchTerm.getTypeFromId(searchTypeId);
      
      print("Please enter a search word: ");
      searchKey = keyboard.nextLine();
      
      
    }
    
    //return 0;
    
  }
  
  public void println(String message) {
    print(message + '\n');
  }
  
  public void print(String message) {
    System.out.print("[DBCTD] " + message);
  }
  
}
