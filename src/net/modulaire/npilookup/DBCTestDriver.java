package net.modulaire.npilookup;

import java.util.Scanner;

import net.modulaire.npilookup.connector.*;

public class DBCTestDriver {
  
  DBConnector dbConnector;
  RetrievedProviders retrievedProviders;

  public DBCTestDriver() {
    println("Hello there. I'm a driver that tests the database connector.");
  }
  
  public DBCTestDriver(DBConnector dbConnector) {
    super();
    this.dbConnector = dbConnector;
    println("Ok, cool, I now know where the database is.");
  }
  
  public int runTests() {
    println("Alrighty, let me make sure everything is in order to start...");
    
    if(dbConnector == null) { //make sure that we have the connector
      println("I don't seem to know where the DBConnector is. Sorry. Going home.");
      return -1;
    }
    
    println("Sweet, I still remember where the connector is.");
    println("Testing the driver now (requires manual intervention, please).");

    Scanner keyboard = new Scanner(System.in);
    
    while(true) {
      
      int searchTypeId;
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
        if(searchTypeId < 0 || searchTypeId > 7) continue; 
      } catch(Exception e) {
        println("It appears that invalid text was entered. Please try again.");
        continue;
      }
      
      while(true) {
        print("Please enter a search word: ");
        searchKey = keyboard.nextLine();
        if(!searchKey.equalsIgnoreCase("")) break;
      }
      
      println("Attempting to search for '" + searchKey + "' by id " + searchTypeId + ".");
      
      try {
        retrievedProviders = dbConnector.search(new SearchTerm(SearchTerm.SearchType.getTypeFromId(searchTypeId), searchKey));
      } catch (ConnectorException e) {
        println("Something not so great happened when we tried to search.");
        e.printStackTrace();
        keyboard.close();
        return 1;
      }
      
      println("Finished searching with " + retrievedProviders.getSize() + " results.");
      System.out.format("%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s",
          "[PID]",
          "[FIRST_NAME]",
          "[MIDDLE_NAME]",
          "[LAST_NAME]",
          "[NAME_PREFIX]",
          "[NAME_SUFFIX]",
          "[MAIL FIRST LINE]",
          "[MAIL SECOND LINE]",
          "[MAIL CITY]",
          "[MAIL STATE]",
          "[MAIL POSTAL CODE]",
          "[MAIL COUNTRY]",
          "[MAIL PHONE]",
          "[MAIL FAX]",
          "[LOCATION FIRST LINE]",
          "[LOCATION SECOND LINE]",
          "[LOCATION CITY]",
          "[LOCATION STATE]",
          "[LOCATION POSTAL CODE]",
          "[LOCATION COUNTRY]",
          "[LOCATION PHONE]",
          "[LOCATION FAX]");
      
      System.out.println();
      
      while(retrievedProviders.next()) {
        for(int i = 0; i < RetrievedProviders.ResultType.getInstanceCount(); i++) {
          System.out.format("%20s", retrievedProviders.getValue(i));
        }
        System.out.println();
      }
      
      while(true) {
        String response;
        print("Would you like to continue? [y/n]: ");
        response = keyboard.nextLine();
        if(response.equalsIgnoreCase("y")) break;
        if(response.equalsIgnoreCase("n")) {
          keyboard.close();
          println("Goodbye, friend!");
          return 0;
        }
      }

    }
    
  }
  
  public void println(String message) {
    print(message + '\n');
  }
  
  public void print(String message) {
    System.out.print("[DBCTD] " + message);
  }
  
}
