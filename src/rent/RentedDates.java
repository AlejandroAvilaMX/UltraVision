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
	private JTextField ID, loyaltyPoints, newloyaltyPoints, availableqtty, freeRent, newfreeRent, qttyRent, newqttyRent;
	private int intloyaltyPoints, intfreeRent, intqttyRent;
	private String res, strfreeRent, qttyfreeRent, strqttyRent;
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


        qttyRent = new JTextField();
        qttyRent.setBounds(70, 170, 100, 25);
        //stock.setVisible(false);
        newqttyRent = new JTextField();
        newqttyRent.setBounds(70, 200, 100, 25);
        //newstock.setVisible(false);
        
        availableqtty = new JTextField();
        availableqtty.setBounds(200, 200, 100, 25);
        //newstock.setVisible(false);
        
        freeRent = new JTextField();
        freeRent.setBounds(70, 170, 100, 25);
        
        newfreeRent = new JTextField();
        newfreeRent.setBounds(70, 170, 100, 25);
        
        //Button Customers
        btnNewRent = new JButton("New");
        btnNewRent.setFont(fontButton);
        btnNewRent.setBounds(200, 100, 110, 30);
        btnNewRent.addActionListener(this);
        btnNewRent.setActionCommand("btnCustomers"); 
        btnNewRent.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0){
        		if(ID.getText().equals("")) {
        			JOptionPane.showMessageDialog(null, "ID cannot be empty");
        		} else {
        			qttyRent();
        		}
        		
        		
        		/*
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
        	        intloyaltyPoints = Integer.parseInt(loyaltyPoints.getText());
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
        	        
        	        if(intloyaltyPoints % 100 == 0) {
        	        	numberOffreeRent();
        	        	JOptionPane.showMessageDialog(null, "Congratulations! \n"
        	        			+ "You have " + intloyaltyPoints + " Loyalty Points\n"
        	        					+ "You have " + qttyfreeRent + " available Free Rent(s)");
        	        	
        	        }
        	        
        	        
        	        conection.close();
        	        
        	        JOptionPane.showMessageDialog(null, "Loyalty Points updated successfully");
        	        loyaltyPoints.setText("");
        	        newloyaltyPoints.setText("");
        	        
        	        
                } catch (Exception e){      //If something goes wrong
                	JOptionPane.showMessageDialog(null, "Error finding Loyalty Points!");
                }
                
                
        		
        		*/
            	}
        	});
        
        
        p.add(lID);
        p.add(ID);
        p.add(qttyRent);
        p.add(newqttyRent);
        p.add(availableqtty);
        p.add(btnNewRent);
        p.add(freeRent);
        p.add(newfreeRent);
        
        this.validate();
        this.repaint();
       
	}
	/*
	public void numberOffreeRent() {
		
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
	        String search = "SELECT freeRent FROM membershipCard " + where;
	        System.out.println(search);
	        ps = conection.prepareStatement(search);
	        rs = ps.executeQuery();
	        //We will take the result of the query and this will be written on the JTextField 'stock'
	        while(rs.next()) {
	        	freeRent.setText(rs.getString("freeRent"));
	            res = rs.getString("freeRent");
	            System.out.println("Free Rent: " + rs.getString("freeRent"));
	        }
	        
	        strfreeRent = freeRent.getText();
	        System.out.println(strfreeRent);
	        intfreeRent = Integer.parseInt(freeRent.getText());
	        intfreeRent = intloyaltyPoints /100;
	        System.out.println(intfreeRent);
	        
	        qttyfreeRent = Integer.toString(intfreeRent);
	        
	        newfreeRent.setText(qttyfreeRent);
	        System.out.println("Quantity of Free rent: " + qttyfreeRent);
	        
	        try{
	        	
	        	//'freeRentLeft' will be the query that we will send to the database to find the results
            	String freeRentLeft = "UPDATE membershipCard SET freeRent = ? " + where;
                System.out.println("My update Free Rent: " + newfreeRent);
                PreparedStatement newstatement = conection.prepareStatement(freeRentLeft);
                
                newstatement.setString(1, newfreeRent.getText());
                
                newstatement.execute();
	        	
	        }catch (Exception e){      //If something goes wrong
            	JOptionPane.showMessageDialog(null, "Error calculating Free Rent left!");
            }
	        conection.close();
	        
	        JOptionPane.showMessageDialog(null, "Free Rent used successfully");
	        freeRent.setText("");
	        newfreeRent.setText("");
	        
        } catch (Exception e){      //If something goes wrong
        	JOptionPane.showMessageDialog(null, "Error finding Free Rent!");
        }
	}
	
	/*
	public freeRent() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        
        String filter = titleId.getText();
        String where = "";
        //Our filter must not be empty
        if(!"".equals(filter)){
            where = "WHERE titleID = " + filter;
            System.out.println("useFreeRent where: " + where);
        }	        
    	try{
    		//Check if the Title has availability
    		PreparedStatement pst = null;
	        ResultSet rst = null;
	        
	        //'newsearch' will be the query that will be send to the database to find the stock and available
	        String newsearch = "SELECT available FROM title " + where;
	        System.out.println("newsearch: " + newsearch);
	        pst = conection.prepareStatement(newsearch);
	        rst = pst.executeQuery();
	        //We will take the result of the query and this will be written on the JTextField 'available'
	        while(rst.next()) {
	        	available.setText(rst.getString("available"));
	            res = rst.getString("available");
	            System.out.println("Available: " + rst.getString("available"));
	        }
	        //The quantity available must not be 0
	        qttyAvailable = Integer.parseInt(available.getText());
	        if(qttyAvailable == 0) {
	        	JOptionPane.showMessageDialog(null, "The rent is not possible! \n"
	        			+ "There is not enough titles availables to rent");
	        }else {
	        	try {
	        		//Calculating to new Available quantity
	        		
	        		String newfilter = titleId.getText();
	    	        String newwhere = "";
	    	        //Our filter must not be empty
	    	        if(!"".equals(newfilter)){
	    	            newwhere = "WHERE titleID = " + newfilter;
	    	            System.out.println("where: " + newwhere);
	    	        }	
	        		
	        		qttyAvailable --;
    	        	stravailable = Integer.toString(qttyAvailable);
    	        		
    	        	newAvailable.setText(stravailable);
    	        	System.out.println("New Qtty Availalble: " + stravailable);
	        		try{ 
	        			//Updating quantity available
	        			//'updateavailable' will be the query that we will send to the database to find the results
                    	String updateavailable = "UPDATE membershipCard SET available = ? " + newwhere;
                        System.out.println("My update Available: " + updateavailable);
                        PreparedStatement newstatement = conection.prepareStatement(updateavailable);
                        
                        newstatement.setString(1, newAvailable.getText());
                        
                        newstatement.execute();
                        
    	        		try {
    	        			//Saving information of the rent of the Title
                            //'addrent' will be query that will be send to the database to add a new register on the table rent                    
	                        String addrent = "INSERT INTO rent (rentDay, returnDay, titleId, custId) VALUES(?, ?, ?, ?)"; 
	                        System.out.println("Query new Rent " + addrent);
	                        PreparedStatement statement = conection.prepareStatement(addrent);
	                        statement.setString(1, rentedDay.getText());
	                        statement.setString(2, returnDay.getText());
	                        statement.setString(3, titleId.getText());
	                        statement.setString(4, ID.getText());
	                        
	                        statement.executeUpdate();

	                        try {
	                        	//Check and calculate Loyalty Points
	                	    	PreparedStatement ps = null;
	                	        ResultSet rs = null;
	                	        
	                	        //'search' will be the query that will be send to the database to find the stock and available
	                	        String search = "SELECT loyaltyPoints FROM membershipCard " + newwhere;
	                	        System.out.println(search);
	                	        ps = conection.prepareStatement(search);
	                	        rs = ps.executeQuery();
	                	        //We will take the result of the query and this will be written on the JTextField 'loyaltyPoints'
	                	        while(rs.next()) {
	                	        	loyaltyPoints.setText(rs.getString("loyaltyPoints"));
	                	            res = rs.getString("loyaltyPoints");
	                	            System.out.println("Loyalty Points: " + rs.getString("loyaltyPoints"));
	                	        }
	                	        
	                	        strloyaltyPoints = loyaltyPoints.getText();		//Storing the content of the JTextField on a String
	                	        System.out.println(strloyaltyPoints);
	                	        intloyaltyPoints = Integer.parseInt(loyaltyPoints.getText());		//Converting the value of that String to an Integer
	                	        intloyaltyPoints = intloyaltyPoints -100;		//Adding +10 to the Loyalty Points of the Customer
	                	        System.out.println(intloyaltyPoints);
	                	        
	                	        qttyLoyaltyPoints = Integer.toString(intloyaltyPoints);
                	        
	                	        newloyaltyPoints.setText(qttyLoyaltyPoints);		//The value of that String will be written on a JTextField
	                	        System.out.println("New Loyalty Points: " + qttyLoyaltyPoints);

	                	        try{
	                	        	//Updating Loyalty Points
	                	        	//'newloyaltypoints' will be the query that we will send to the database to find the results
	                            	String newloyaltypoints = "UPDATE membershipCard SET loyaltyPoints = ? " + newwhere;
	                                System.out.println("My update Loyalty Points: " + newloyaltypoints);
	                                PreparedStatement newstatement1 = conection.prepareStatement(newloyaltypoints);
	                                
	                                newstatement1.setString(1, newloyaltyPoints.getText());
	                                
	                                newstatement1.execute();
	                            
	                                try {
	                                	//Check and calculate new quantity of Free Rent
	                                	//'search' will be the query that will be send to the database to find the stock and available
	    	                	        PreparedStatement nps = null;
	    	                	        ResultSet nrs = null;
	    	                	        
	    	                	        String searchnew = "SELECT freeRent FROM membershipCard " + newwhere;
	    	            		        System.out.println(searchnew);
	    	            		        nps = conection.prepareStatement(searchnew);
	    	            		        nrs = nps.executeQuery();
	    	            		        //We will take the result of the query and this will be written on the JTextField 'stock'
	    	            		        while(nrs.next()) {
	    	            		        	freeRent.setText(nrs.getString("freeRent"));
	    	            		            res = nrs.getString("freeRent");
	    	            		            System.out.println("Free Rent: " + nrs.getString("freeRent"));
	    	            		        }
	    	            		        
	    	            		        strfreeRent = freeRent.getText();		//Storing the content of the JTextField on a String
	    	            		        System.out.println(strfreeRent);
	    	            		        intfreeRent = Integer.parseInt(freeRent.getText());		//Converting the value of that String to an Integer
	    	            		        intfreeRent = intfreeRent -1;		//Subtracting -1 to the quantity of Free Rent of the Customer
	    	            		        System.out.println(intfreeRent);
	    	            		        
	    	            		        qttyfreeRent = Integer.toString(intfreeRent);
	    	            		        
	    	            		        newfreeRent.setText(qttyfreeRent);		//The value of that String will be written on a JTextField
	    	            		        System.out.println("Quantity of Free rent: " + qttyfreeRent);
	    	            		        
	    	            		        try{
	    	            		        	//Updating Free Renty quantity
	    	            		        	//'freeRentLeft' will be the query that we will send to the database to find the results
	    	            	            	String freeRentLeft = "UPDATE membershipCard SET freeRent = ? " + newwhere;
	    	            	                System.out.println("My update Free Rent: " + freeRentLeft);
	    	            	                PreparedStatement statementnew = conection.prepareStatement(freeRentLeft);
	    	            	                
	    	            	                statementnew.setString(1, newfreeRent.getText());
	    	            	                
	    	            	                statementnew.execute();
	    	            		        	
	    	            		        }catch (Exception e){      //If something goes wrong
	    	            	            	JOptionPane.showMessageDialog(null, "Error discounting Free Rent!");
	    	            	            }
	                                }catch (Exception e){      //If something goes wrong
	                                	JOptionPane.showMessageDialog(null, "Error finding Free Rent!");
	                                }
	                	        }catch (Exception e){      //If something goes wrong
	                	        	JOptionPane.showMessageDialog(null, "Error updating Loyalty Points!");
	                	        }
	                        }catch (Exception e){      //If something goes wrong
                	        	JOptionPane.showMessageDialog(null, "Error finding Loyalty Points!");
                	        }
    	        		}catch (Exception e){      //If something goes wrong
            	        	JOptionPane.showMessageDialog(null, "Error inserting data!");
            	        }
    	        		
            	        loyaltyPoints.setText("");
            	        newloyaltyPoints.setText("");
            	        freeRent.setText("");
            	        newfreeRent.setText("");
                    	
                    }catch (Exception e){      //If something goes wrong
                        JOptionPane.showMessageDialog(null, "Error discounting Free Rent quantity");
                    }
                    
                    conection.close();
                    
                    JOptionPane.showMessageDialog(null, "New Title rented successfully");
                    ID.setText("");
                    titleId.setText("");
                    available.setText("");
                    newAvailable.setText("");
                    normalScreen();
                    
	        	}catch (Exception e){      //If something goes wrong
                    JOptionPane.showMessageDialog(null, "Error renting a Title!\n"
                    		+ "Please check the Customer ID");
                }
	        }
	    } catch (Exception e){      //If something goes wrong
            JOptionPane.showMessageDialog(null, "Error finding Availability of Title");
        }

	}*/
	
	public void qttyRent() {
		
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        
        try {
        
        	String filter = ID.getText();
            String where = "";
            //Our filter must not be empty
            if(!"".equals(filter)){
                where = "WHERE custId = " + filter;
                System.out.println("where: " + where);
            }
        	
            PreparedStatement ps = null;
	        ResultSet rs = null;
	        
	        //'search' will be the query that will be send to the database to find the stock and available
	        String search = "SELECT qttyRent FROM membershipCard " + where;
	        System.out.println(search);
	        ps = conection.prepareStatement(search);
	        rs = ps.executeQuery();
	        //We will take the result of the query and this will be written on the JTextField 'loyaltyPoints'
	        while(rs.next()) {
	        	qttyRent.setText(rs.getString("qttyRent"));
	            res = rs.getString("qttyRent");
	            System.out.println("Quantity of titles rented: " + rs.getString("qttyRent"));
	        }
	        
	        strqttyRent = qttyRent.getText();		//Storing the content of the JTextField on a String
	        System.out.println(qttyRent);
	        intqttyRent = Integer.parseInt(qttyRent.getText());		//Converting the value of that String to an Integer
	        
            if(intqttyRent == 4) {
            	JOptionPane.showMessageDialog(null, "The Rent of the Title is not possible!\n"
            			+ "The Customer is allowed to rent only 4 Titles");
            } else {
            	JOptionPane.showMessageDialog(null, "Process of Rent Title");
            	conection.close();
            }
        	
        } catch (Exception e){      //If something goes wrong
            JOptionPane.showMessageDialog(null, "Error finding the quantity available of Rent");
        }
		
		
	}

	
	
	/*
	public void checkAvailability() {
		
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        try{
        	
        	PreparedStatement pst = null;
    	        ResultSet rst = null;
    	        
    	        //'newsearch' will be the query that will be send to the database to find the stock and available
    	        String newsearch = "SELECT available FROM title " + where;
    	        System.out.println("useFreeRent newsearch: " + newsearch);
    	        pst = conection.prepareStatement(newsearch);
    	        rst = pst.executeQuery();
    	        //We will take the result of the query and this will be written on the JTextField 'available'
    	        while(rst.next()) {
    	        	available.setText(rst.getString("available"));
    	            res = rst.getString("available");
    	            System.out.println("useFreeRent Available: " + rst.getString("available"));
    	        }
    	        //The quantity available must not be less than quantity in Stock
    	        qttyAvailable = Integer.parseInt(available.getText());
    	        if(qttyAvailable == 0) {
    	        	JOptionPane.showMessageDialog(null, "useFreeRent The rent is not possible! \n"
    	        			+ "There is not enough titles availables to rent");
    	        }else {
    	        	try {	
    	        		
    	        		String newfilter = titleId.getText();
    	    	        String newwhere = "";
    	    	        //Our filter must not be empty
    	    	        if(!"".equals(newfilter)){
    	    	            newwhere = "WHERE titleID = " + newfilter;
    	    	            System.out.println("useFreeRent where: " + newwhere);
    	    	        }	
    	        		
    	        		qttyAvailable --;
        	        	stravailable = Integer.toString(qttyAvailable);
        	        		
        	        	newAvailable.setText(stravailable);
        	        	System.out.println("New Qtty Availalble" + newAvailable);
    	        		try{ 
    	        			//'newloyaltypoints' will be the query that we will send to the database to find the results
                        	String updateavailable = "UPDATE membershipCard SET available = ? " + newwhere;
                            System.out.println("My update Available: " + updateavailable);
                            PreparedStatement newstatement = conection.prepareStatement(updateavailable);
                            
                            newstatement.setString(1, newAvailable.getText());
                            
                            newstatement.execute();
    	        			
    	        		
	    	        		try {
	                            //'addrent' will be query that will be send to the database to add a new register on the table rent                    
		                        String addrent = "INSERT INTO rent (rentDay, returnDay, titleId, custId) VALUES(?, ?, ?, ?)"; 
		                        System.out.println("Query new Rent " + addrent);
		                        PreparedStatement statement = conection.prepareStatement(addrent);
		                        statement.setString(1, rentedDay.getText());
		                        statement.setString(2, returnDay.getText());
		                        statement.setString(3, titleId.getText());
		                        statement.setString(4, ID.getText());
		                        
		                        statement.executeUpdate();

		                        try {
		                	    	PreparedStatement ps = null;
		                	        ResultSet rs = null;
		                	        
		                	        //'search' will be the query that will be send to the database to find the stock and available
		                	        String search = "SELECT loyaltyPoints FROM membershipCard " + newwhere;
		                	        System.out.println(search);
		                	        ps = conection.prepareStatement(search);
		                	        rs = ps.executeQuery();
		                	        //We will take the result of the query and this will be written on the JTextField 'loyaltyPoints'
		                	        while(rs.next()) {
		                	        	loyaltyPoints.setText(rs.getString("loyaltyPoints"));
		                	            res = rs.getString("loyaltyPoints");
		                	            System.out.println("Loyalty Points: " + rs.getString("loyaltyPoints"));
		                	        }
		                	        
		                	        strloyaltyPoints = loyaltyPoints.getText();		//Storing the content of the JTextField on a String
		                	        System.out.println(strloyaltyPoints);
		                	        intloyaltyPoints = Integer.parseInt(loyaltyPoints.getText());		//Converting the value of that String to an Integer
		                	        intloyaltyPoints = intloyaltyPoints -100;		//Adding +10 to the Loyalty Points of the Customer
		                	        System.out.println(intloyaltyPoints);
		                	        
		                	        qttyLoyaltyPoints = Integer.toString(intloyaltyPoints);
	                	        
		                	        newloyaltyPoints.setText(qttyLoyaltyPoints);		//The value of that String will be written on a JTextField
		                	        System.out.println("New Loyalty Points: " + qttyLoyaltyPoints);
	
		                	        try{
		                	        	//'newloyaltypoints' will be the query that we will send to the database to find the results
		                            	String newloyaltypoints = "UPDATE membershipCard SET loyaltyPoints = ? " + newwhere;
		                                System.out.println("My update Loyalty Points: " + newloyaltypoints);
		                                PreparedStatement newstatement = conection.prepareStatement(newloyaltypoints);
		                                
		                                newstatement.setString(1, newloyaltyPoints.getText());
		                                
		                                newstatement.execute();
		                            
		                                try {
		                                	//'search' will be the query that will be send to the database to find the stock and available
		    	                	        PreparedStatement nps = null;
		    	                	        ResultSet nrs = null;
		    	                	        
		    	                	        String searchnew = "SELECT freeRent FROM membershipCard " + newwhere;
		    	            		        System.out.println(searchnew);
		    	            		        nps = conection.prepareStatement(searchnew);
		    	            		        nrs = nps.executeQuery();
		    	            		        //We will take the result of the query and this will be written on the JTextField 'stock'
		    	            		        while(nrs.next()) {
		    	            		        	freeRent.setText(nrs.getString("freeRent"));
		    	            		            res = nrs.getString("freeRent");
		    	            		            System.out.println("Free Rent: " + nrs.getString("freeRent"));
		    	            		        }
		    	            		        
		    	            		        strfreeRent = freeRent.getText();
		    	            		        System.out.println(strfreeRent);
		    	            		        intfreeRent = Integer.parseInt(freeRent.getText());
		    	            		        intfreeRent = intfreeRent -1;
		    	            		        System.out.println(intfreeRent);
		    	            		        
		    	            		        qttyfreeRent = Integer.toString(intfreeRent);
		    	            		        
		    	            		        newfreeRent.setText(qttyfreeRent);
		    	            		        System.out.println("Quantity of Free rent: " + qttyfreeRent);
		    	            		        
		    	            		        try{
		    	            		        	//'freeRentLeft' will be the query that we will send to the database to find the results
		    	            	            	String freeRentLeft = "UPDATE membershipCard SET freeRent = ? " + newwhere;
		    	            	                System.out.println("My update Free Rent: " + freeRentLeft);
		    	            	                PreparedStatement statementnew = conection.prepareStatement(freeRentLeft);
		    	            	                
		    	            	                statementnew.setString(1, newfreeRent.getText());
		    	            	                
		    	            	                statementnew.execute();
		    	            		        	
		    	            		        }catch (Exception e){      //If something goes wrong
		    	            	            	JOptionPane.showMessageDialog(null, "Error discounting Free Rent!");
		    	            	            }
		                                }catch (Exception e){      //If something goes wrong
		                                	JOptionPane.showMessageDialog(null, "Error finding Free Rent!");
		                                }
		                                
		                	        }catch (Exception e){      //If something goes wrong
		                	        	JOptionPane.showMessageDialog(null, "Error updating Loyalty Points!");
		                	        }
		                	        
		                        }catch (Exception e){      //If something goes wrong
	                	        	JOptionPane.showMessageDialog(null, "Error finding Loyalty Points!");
	                	        }
	    	        		}catch (Exception e){      //If something goes wrong
                	        	JOptionPane.showMessageDialog(null, "Error inserting data!");
                	        }
                	        
                	        conection.close();
                	        
                	        //JOptionPane.showMessageDialog(null, "Loyalty Points updated successfully");
                	        loyaltyPoints.setText("");
                	        newloyaltyPoints.setText("");
                	        freeRent.setText("");
                	        newfreeRent.setText("");
                        	
                        }catch (Exception e){      //If something goes wrong
                            JOptionPane.showMessageDialog(null, "Error discounting Free Rent quantity");
                        }
                        
                        conection.close();
                        
                        JOptionPane.showMessageDialog(null, "New Title rented successfully");
                        ID.setText("");
                        titleId.setText("");
                        available.setText("");
                        newAvailable.setText("");
                        normalScreen();
                        
    	        	}catch (Exception e){      //If something goes wrong
                        JOptionPane.showMessageDialog(null, "Error renting a Title!\n"
                        		+ "Please check the Customer ID");
                    }
    	        }

	        
	        
        } catch (Exception e){      //If something goes wrong
        	JOptionPane.showMessageDialog(null, "Error inserting Availability!");
        }
            
            
		
	}*/
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	


}


	