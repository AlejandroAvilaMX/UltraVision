package utravision;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginModel {

	public boolean connection(User userLogged){
        // Creating an array that we can return later
        boolean login = false;
	try{
            // Load the database driver
            Class.forName("com.mysql.jdbc.Driver").newInstance() ;
		
            String dbServer = "jdbc:mysql://localhost:3306/UltraVision";
            String user = "root";
            String password = "pass";
            String query = "SELECT * FROM access WHERE username = '" + userLogged.getUn() + "' AND password = '" + userLogged.getPw() + "';";

            // Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, user, password) ;

            // Get a statement from the connection
            Statement stmt = conn.createStatement() ;

            // Execute the query
            ResultSet rs = stmt.executeQuery(query) ;
		
            login = rs.next();
               
            // Close the result set, statement and the connection
            rs.close() ;
            stmt.close() ;
            conn.close() ;
	}
	catch( SQLException se ){
            System.out.println( "SQL Exception:" );

            // Loop through the SQL Exceptions
            while( se != null ){
                System.out.println( "State  : " + se.getSQLState()  ) ;
                System.out.println( "Message: " + se.getMessage()   ) ;
                System.out.println( "Error  : " + se.getErrorCode() ) ;

		se = se.getNextException() ;
            }
	}
	catch( Exception e ){
            System.out.println( e ) ;
	}
        // Returning the array of data
        return login;
    }

}
