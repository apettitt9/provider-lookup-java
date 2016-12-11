package net.modulaire.npilookup;

import net.modulaire.npilookup.connector.DBConnector;

public class DBCTestDriver {
  
  DBConnector dbc;

  public DBCTestDriver() {
    print("Hello there. I'm a driver that tests the database connector.");
  }
  
  public DBCTestDriver(DBConnector dbc) {
    super();
    this.dbc = dbc;
    print("Ok, cool, I now know where the database is.");
  }
  
  public int runTests() {
    print("Alrighty, let me make sure everything is in order to start...");
    
    if(dbc == null) { //make sure that we have the connector
      print("I don't seem to know where the DBConnector is. Sorry.");
      return -1;
    }
    
    print("Sweet, I still remember where the connector is.");
    print("That's all the tests I have for now.");
    
    return 0;
    
  }
  
  public void print(String message) {
    System.out.println("[DBCTD] " + message);
  }
  
}
