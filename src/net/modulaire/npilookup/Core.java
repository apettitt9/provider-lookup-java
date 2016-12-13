package net.modulaire.npilookup;

import net.modulaire.npilookup.connector.ConnectorException;
import net.modulaire.npilookup.connector.DBConnector;
import net.modulaire.npilookup.userinterface.FrameworkDriver;

public class Core {

  private static final int CLEAN_EXIT = 0;
  private static final int DIRTY_EXIT = 1;
  
  //private static DBConnector dbc;
  private static FrameworkDriver fd;
  
  public static void main(String[] args) {
    
    System.out.println("[CORE] Hello, world!");
    fd = new FrameworkDriver();
    
    /* THIS CODE IS PART OF THE DEVELOPMENT FOR DBConnector.
    
    if(args.length < 3) { //make sure that we are getting something that resembles valid data
      System.out.println("Usage: java -jar <executable.jar> <server_location> <username> <password>");
      System.exit(CLEAN_EXIT);
    }
    
    try {
      
      dbc = new DBConnector(args[0], args[1], args[2]);
      
      DBCTestDriver testDriver = new DBCTestDriver();
      testDriver.runTests();
      
      dbc.close();
      
    } catch (ConnectorException e) {
      System.out.println("The application caught a ConnectorException and has to close.");
      if(e.getMessage() != null) System.out.println("Message: " + e.getMessage());
      if(e.getCause() != null) System.out.println("Note that the exception was caused by " + e.getCause().getClass().getSimpleName());
      System.exit(DIRTY_EXIT);
    }
    
    */
    
    
  }
  
}
