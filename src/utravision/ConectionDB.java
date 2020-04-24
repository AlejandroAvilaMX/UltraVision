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