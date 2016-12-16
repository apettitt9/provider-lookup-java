package net.modulaire.npilookup.userinterface;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class FrameworkDriver {

  public FrameworkDriver() {
    System.out.println("[FD] The Framework Driver has been created.");
        /*
            Request something from the database using the Get method
            this will trigger the get Route
            the user's query will go into the Request variable and the result of the query into the Response variable
         */

    Spark.before((request, response) -> {
      boolean validRequest = false;
      // check if the user submitted a valid query
      if (!validRequest) {
        halt(401, "Please provide a valid query.");
        return 0;
      }
    });
        /*
            The above is a before request filter
            Use it to check the validity of the request
            If the request isn't valid, it will stop the request from going through
         */

    get("/", (request, response) -> {
            /*
                call the database here
                the result should go into the response variable
                most of the program logic can go here
             */
      return response.body();
      //don't actually do this, it's just a filler. Probably refine it to display in some nice way
    });


  }

}
