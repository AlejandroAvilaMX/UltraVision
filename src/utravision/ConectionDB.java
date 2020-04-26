/**
 * This is the class with the connection to the Database
 * 
 * author: Cesar Alejandro Avila Calderon		Student Number: 2018451
 */
package utravision;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class ConectionDB {
	
	Connection con;
    
    public Connection conect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
                    
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/UltraVision", "root", "pass");
            System.out.println("Conection successfully...!!");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Conection error...!!");
        }
        return con;
    }
}