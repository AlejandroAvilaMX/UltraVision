package rent;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import customers.Customers;
import customers.MembershipCards;
import titles.Titles;
import utravision.ConectionDB;
import utravision.LoginController;
import validations.NoLetters;
import validations.ValidLength;

public class RentedDates extends JFrame implements ActionListener{
	private JLabel lID;
	private JTextField ID, loyaltyPoints, newloyaltyPoints, availableqtty;
	private String res;
	private JButton btnNewRent;

	public RentedDates() {
		this.setVisible(true);
        this.setSize(1100, 730);     //Size of the window
        this.setTitle("Rent");       //Title of the window
        
        JPanel p = new JPanel();
        p.setLayout(null);
        this.add(p);
        p.setBackground(java.awt.Color.orange);     //Color of the window
        Font fonttitle = new Font("Arial", Font.BOLD, 28);      //Font style (title)
        Font fontlabel = new Font("Calibri", Font.PLAIN, 16);       //Font style (labels)
        Font fontButton = new Font("Tahoma", Font.BOLD, 12);        //Font style (buttons)
        
        
        lID = new JLabel("ID");
        lID.setFont(fontlabel);
        lID.setBounds(70, 90, 80, 20);
        ID = new JTextField();
        ID.setBounds(70, 120, 80, 25);


        loyaltyPoints = new JTextField();
        loyaltyPoints.setBounds(70, 170, 100, 25);
        //stock.setVisible(false);
        newloyaltyPoints = new JTextField();
        newloyaltyPoints.setBounds(70, 200, 100, 25);
        //newstock.setVisible(false);
        
        availableqtty = new JTextField();
        availableqtty.setBounds(200, 200, 100, 25);
        //newstock.setVisible(false);
        
        //Button Customers
        btnNewRent = new JButton("New");
        btnNewRent.setFont(fontButton);
        btnNewRent.setBounds(200, 100, 110, 30);
        btnNewRent.addActionListener(this);
        btnNewRent.setActionCommand("btnCustomers"); 
        btnNewRent.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0){
        		
        		//checkAvailability();
        		
        		ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                
                try{
                	
                	String filter = ID.getText();
        	        String where = "";
        	        //Our filter must not be empty
        	        if(!"".equals(filter)){
        	            where = "WHERE custId = " + filter;
        	        }
        	    	PreparedStatement ps = null;
        	        ResultSet rs = null;
        	        
        	        //'search' will be the query that will be send to the database to find the stock and available
        	        String search = "SELECT loyaltyPoints FROM membershipCard " + where;
        	        System.out.println(search);
        	        ps = conection.prepareStatement(search);
        	        rs = ps.executeQuery();
        	        //We will take the result of the query and this will be written on the JTextField 'stock'
        	        while(rs.next()) {
        	        	loyaltyPoints.setText(rs.getString("loyaltyPoints"));
        	            res = rs.getString("loyaltyPoints");
        	            System.out.println("Loyalty Points: " + rs.getString("loyaltyPoints"));
        	        }
        	        
        	        String strloyaltyPoints = loyaltyPoints.getText();
        	        System.out.println(strloyaltyPoints);
        	        int intloyaltyPoints = Integer.parseInt(loyaltyPoints.getText());
        	        intloyaltyPoints = intloyaltyPoints +10;
        	        System.out.println(intloyaltyPoints);
        	        
        	        String qttyLoyaltyPoints = Integer.toString(intloyaltyPoints);
        	        
        	        newloyaltyPoints.setText(qttyLoyaltyPoints);
        	        System.out.println("New Loyalty Points: " + qttyLoyaltyPoints);
        	        try{
        	        	
        	        	//'newloyaltypoints' will be the query that we will send to the database to find the results
                    	String newloyaltypoints = "UPDATE membershipCard SET loyaltyPoints = ? " + where;
                        System.out.println("My update Loyalty Points: " + newloyaltypoints);
                        PreparedStatement newstatement = conection.prepareStatement(newloyaltypoints);
                        
                        newstatement.setString(1, newloyaltyPoints.getText());
                        
                        newstatement.execute();
        	        	
        	        }catch (Exception e){      //If something goes wrong
                    	JOptionPane.showMessageDialog(null, "Error inserting Loyalty Points!");
                    }
        	        
        	        conection.close();
        	        
        	        JOptionPane.showMessageDialog(null, "Loyalty Points updated successfully");
        	        loyaltyPoints.setText("");
        	        newloyaltyPoints.setText("");
        	        
        	        
                } catch (Exception e){      //If something goes wrong
                	JOptionPane.showMessageDialog(null, "Error finding Loyalty Points!");
                }
                
                
        		
        		
            	}
        	});
        
        
        p.add(lID);
        p.add(ID);
        p.add(loyaltyPoints);
        p.add(newloyaltyPoints);
        p.add(availableqtty);
        p.add(btnNewRent);
        
        this.validate();
        this.repaint();
       
	}
	
	/*
	public void checkAvailability() {
		
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        try{
        	
        	String filter = titleId.getText();
	        String where = "";
	        //Our filter must not be empty
	        if(!"".equals(filter)){
	            where = "WHERE titleID = " + filter;
	        }
	    	PreparedStatement ps = null;
	        ResultSet rs = null;
	        
	        //'search' will be the query that will be send to the database to find the stock and available
	        String search = "SELECT available FROM title " + where;
	        System.out.println(search);
	        ps = conection.prepareStatement(search);
	        rs = ps.executeQuery();
	        //We will take the result of the query and this will be written on the JTextField 'stock'
	        while(rs.next()) {
	        	available.setText(rs.getString("available"));
	            res = rs.getString("available");
	            System.out.println("Available: " + rs.getString("available"));
	        }
	        
	        String strAvailable = available.getText();
	        System.out.println(strAvailable);
	        int intAvailable = Integer.parseInt(available.getText());
	        intAvailable--;
	        System.out.println(intAvailable);
	        
	        String qttyAvailable = Integer.toString(intAvailable);
	        
	        newAvailable.setText(qttyAvailable);
	        System.out.println("New Available: " + qttyAvailable);
	        try{
	        	
	        	//'newavailable' will be the query that we will send to the database to find the results
            	String newavailable = "UPDATE title SET available = ? " + where;
                System.out.println("My update available: " + newavailable);
                PreparedStatement newstatement = conection.prepareStatement(newavailable);
                
                newstatement.setString(1, newAvailable.getText());
                
                newstatement.execute();
	        	
	        }catch (Exception e){      //If something goes wrong
            	JOptionPane.showMessageDialog(null, "Error inserting a new CD Title!");
            }
	        
	        conection.close();
	        
	        JOptionPane.showMessageDialog(null, "Available updated successfully");
            available.setText("");
            newAvailable.setText("");
	        
	        
        } catch (Exception e){      //If something goes wrong
        	JOptionPane.showMessageDialog(null, "Error inserting Availability!");
        }
            
            
		
	}*/

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	


}


	