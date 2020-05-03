package rent;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import customers.Customers;
import customers.MembershipCards;
import titles.Titles;
import utravision.ConectionDB;
import utravision.LoginController;
import validations.NoLetters;
import validations.NoNumbers;
import validations.ValidLength;

public class returnTitle extends JFrame implements ActionListener{
	private JLabel ltitle, lrentId, lname, ldamageDeduction, llatenessDeduction;
	private JTextField rentId, name, rented, rentedReturn, available, newAvailable, titleId, custId, qttyRent, newqttyRent, latenessDeduction, damageDeduction;	
	private JButton btnRefresh, btnRentSearch, btnSearchName, btnReturn, btnAccept, btnCancel;
	private int qttyAvailable, intqttyRent;
	private String res, stravailable, strqttyRent, QttyRent;
	private DefaultTableModel model; 
	
	public returnTitle() {
		this.setVisible(true);
        this.setSize(1100, 730);     //Size of the window
        this.setTitle("Return Title");       //Title of the window
        
        JPanel p = new JPanel();
        p.setLayout(null);
        this.add(p);
        p.setBackground(java.awt.Color.orange);     //Color of the window
        Font fonttitle = new Font("Arial", Font.BOLD, 28);      //Font style (title)
        Font fontlabel = new Font("Calibri", Font.PLAIN, 16);       //Font style (labels)
        Font fontButton = new Font("Tahoma", Font.BOLD, 12);        //Font style (buttons)
        //My Menu Bar
        JMenuBar myMenuBar = new JMenuBar();
        this.setJMenuBar(myMenuBar);
    
        JMenu myMenu = new JMenu("File");       //Title of the menu
        myMenuBar.add(myMenu);
        //Options of the menu
        JMenuItem Menu = new JMenuItem("Main Menu");
        myMenu.add(Menu);
        Menu.addActionListener(this);
        Menu.setActionCommand("menu");
        
        JMenuItem Users = new JMenuItem("Customers");
        myMenu.add(Users);
        Users.addActionListener(this);
        Users.setActionCommand("customers");
        
        JMenuItem MemCard = new JMenuItem("Membership Card");
        myMenu.add(MemCard);
        MemCard.addActionListener(this);
        MemCard.setActionCommand("MemCard");
        
        JMenuItem Titles = new JMenuItem("Titles");
        myMenu.add(Titles);
        Titles.addActionListener(this);
        Titles.setActionCommand("titles");
        
        JMenuItem Rent = new JMenuItem("Rent");
        myMenu.add(Rent);
        Rent.addActionListener(this);
        Rent.setActionCommand("rent");
        /*//LogOut
        JMenuItem LogOut = new JMenuItem("Logout");
        myMenu.add(LogOut);
        LogOut.addActionListener(this);
        LogOut.setActionCommand("logout");*/
        //Close the program
        JMenuItem Close = new JMenuItem("Exit");
        myMenu.add(Close);
        Close.addActionListener(this);
        Close.setActionCommand("exit");
        //Title of window
        ltitle = new JLabel("Currenty Rented Titles");
        ltitle.setFont(fonttitle);
        ltitle.setBounds(350, 50, 350, 30);
        //Table with the information of all the rented Titles
        ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        //Table with the information of all the rented Titles        
        try{
        	DefaultTableModel model = new DefaultTableModel();
                   
        	PreparedStatement ps = null;
            ResultSet rs = null;
            //'refresh' will be the query that we will send to the database to show all the Rented Titles
            String refresh = "SELECT rent.rentId, CONCAT(customer.name, ' ', customer.surname) AS customerName, title.name AS RentedTitle, rent.rentDay, rent.returnDay, rent.returned, rent.latenessDeduction, rent.damageDeduction "
            		+ "FROM rent INNER JOIN customer ON rent.custId=customer.custId "
            		+ "INNER JOIN title ON rent.titleId=title.titleId "
            		+ "WHERE returned = 'NO';";
            System.out.println("Query refresh: " + refresh);
            //Adding the result to the rows of the table
            ps = conection.prepareStatement(refresh);
            rs = ps.executeQuery();
                   
            ResultSetMetaData rsMD = rs.getMetaData();
            int qttycol = rsMD.getColumnCount();
                
            model.addColumn("Rent ID");
            model.addColumn("Customer Name");
            model.addColumn("Rented Title");
            model.addColumn("Rent Day");
            model.addColumn("Expected Return Day");
            model.addColumn("Returned");
            model.addColumn("Lateness Deduction");
            model.addColumn("Damage Deduction");
                  
            while(rs.next()){
            	Object[] col = new Object[qttycol];
                    
                for(int i = 0; i<qttycol; i++){
                	col[i] = rs.getObject(i+1);
                }
                    
                model.addRow(col);
            }
            //Add the model to the table
        	JTable table = new JTable(model);
        	//Add the scroll to the table
            JScrollPane scroll= new JScrollPane(table);
            table.setBounds(40,120,1000,200);
            scroll.setBounds(40,120,1000,200);
            //Add the scroll to the Panel
            p.add(scroll);
                    
        } catch (SQLException ex){
        	JOptionPane.showMessageDialog(null, "Error Refreshing...!!");
        }
        
        //Button refresh
        btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(fontButton);
        btnRefresh.setBounds(40, 70, 100, 30);
        btnRefresh.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	//Call method 'refresh'
            	refresh();
            	//Add the model to the table
            	JTable table = new JTable(model);
            	//Add the scroll to the table
                JScrollPane scroll= new JScrollPane(table);
                table.setBounds(40,120,1000,200);
                scroll.setBounds(40,120,1000,200);
              //Add the scroll to the Panel
                p.add(scroll);
            }
        });
        
        lrentId = new JLabel("Rent ID");
        lrentId.setFont(fontlabel);
        lrentId.setBounds(70, 340, 80, 20);
        rentId = new JTextField();
        rentId.setBounds(70, 370, 80, 25);
        new NoLetters(rentId);
        new ValidLength(rentId, 3);
        
        //Button Search Rent
        btnRentSearch = new JButton("Search");
        btnRentSearch.setFont(fontButton);
        btnRentSearch.setBounds(200, 365, 90, 30);
        btnRentSearch.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	//Call method 'search'
            	search();
            	//Add the model to the table
            	JTable table = new JTable(model);
            	//Add the scroll to the table
                JScrollPane scroll= new JScrollPane(table);
                table.setBounds(40,120,1000,200);
                scroll.setBounds(40,120,1000,200);
                //Add the scroll to the Panel
                p.add(scroll);
            }
        });
        
        lname = new JLabel("First Name");
        lname.setFont(fontlabel);
        lname.setBounds(70, 420, 80, 20);
        name = new JTextField();
        name.setBounds(70, 460, 220, 25);
        new ValidLength(name, 50);
        new NoNumbers(name);
        //Button Search Name
        btnSearchName = new JButton("Search");
        btnSearchName.setFont(fontButton);
        btnSearchName.setBounds(340, 455, 90, 30);
        btnSearchName.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                //Call method 'searchName'
            	searchName();
            	//Add the model to the table
            	JTable table = new JTable(model);
            	//Add the scroll to the table
                JScrollPane scroll= new JScrollPane(table);
                table.setBounds(40,120,1000,200);
                scroll.setBounds(40,120,1000,200);
                //Add the scroll to the Panel
                p.add(scroll);
            	
            }
        });
        
        //Button Return Title
        btnReturn = new JButton("Return");
        btnReturn.setFont(fontButton);
        btnReturn.setBounds(150, 520, 90, 30);
        btnReturn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	//Call method 'returnScreen'
            	returnScreen();
            }
        });
        
        llatenessDeduction = new JLabel("Lateness Deduction");
        llatenessDeduction.setFont(fontlabel);
        llatenessDeduction.setBounds(70, 520, 170, 20);
        llatenessDeduction.setVisible(false);
        latenessDeduction = new JTextField();
        latenessDeduction.setBounds(70, 560, 80, 25);
        latenessDeduction.setText("0");
        latenessDeduction.setVisible(false);
        new ValidLength(name, 50);
        new NoLetters(latenessDeduction);
        
        ldamageDeduction = new JLabel("Damage Deduction");
        ldamageDeduction.setFont(fontlabel);
        ldamageDeduction.setBounds(230, 520, 170, 20);
        ldamageDeduction.setVisible(false);
        damageDeduction = new JTextField();
        damageDeduction.setBounds(230, 560, 80, 25);
        damageDeduction.setText("0");
        damageDeduction.setVisible(false);
        new ValidLength(name, 50);
        new NoLetters(damageDeduction);
        
        //Button Accept
        btnAccept = new JButton("Accept");
        btnAccept.setFont(fontButton);
        btnAccept.setBounds(70, 620, 90, 30);
        btnAccept.setVisible(false);
        btnAccept.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	//Validation of required fields
            	if(rentId.getText().equals("")) {		//We must type a Rent ID and Customer ID
            		JOptionPane.showMessageDialog(null, "Rent ID cannot be empty!");
            	}else {
            		//Call method 'returnScreen'
            		returnScreen();
	            	//Call method 'searchName'
	            	titleReturn();
            	}
            	
            }
        });
        
        //Button Cancel
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(fontButton);
        btnCancel.setBounds(220, 620, 90, 30);
        btnCancel.setVisible(false);
        btnCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	//Call method 'normalScreen'
            	normalScreen();
            }
        });
        
        
        rented = new JTextField();
        rented.setBounds(70, 670, 100, 25);
        rented.setVisible(false);
        
        rentedReturn = new JTextField();
        rentedReturn.setBounds(150, 670, 100, 25);
        rentedReturn.setVisible(false);
        
        available = new JTextField();
        available.setBounds(70, 580, 100, 25);
        available.setVisible(false);
        
        newAvailable = new JTextField();
        newAvailable.setBounds(250, 580, 100, 25);
        newAvailable.setVisible(false);
        
        titleId = new JTextField();
        titleId.setBounds(70, 600, 100, 25);
        titleId.setVisible(false);
        
        custId = new JTextField();
        custId.setBounds(70, 600, 100, 25);
        custId.setVisible(false);
        
        qttyRent = new JTextField();
        qttyRent.setBounds(70, 630, 100, 25);
        qttyRent.setVisible(false);
        
        newqttyRent = new JTextField();
        newqttyRent.setBounds(250, 630, 100, 25);
        newqttyRent.setVisible(false);
        
        p.add(ltitle);
        p.add(btnRefresh);
        p.add(lrentId);
        p.add(rentId);
        p.add(btnRentSearch);
        p.add(lname);
        p.add(name);
        p.add(btnSearchName);
        p.add(btnReturn);
        p.add(rented);
        p.add(rentedReturn);
        p.add(available);
        p.add(newAvailable);
        p.add(titleId);
        p.add(qttyRent);
        p.add(newqttyRent);
        p.add(llatenessDeduction);
        p.add(latenessDeduction);
        p.add(ldamageDeduction);
        p.add(damageDeduction);
        p.add(btnAccept);
        p.add(btnCancel);
	}
	
	//This method will modify the window to be able to see all the required information to return a Title
	public void returnScreen() {
		llatenessDeduction.setVisible(true);
		latenessDeduction.setVisible(true);
		ldamageDeduction.setVisible(true);
		damageDeduction.setVisible(true);
		
		btnReturn.setVisible(false);
		btnAccept.setVisible(true);
		btnCancel.setVisible(true);
	}
	
	//This method will return the components of the window to their original state
	public void normalScreen() {
		llatenessDeduction.setVisible(false);
		latenessDeduction.setVisible(false);
		ldamageDeduction.setVisible(false);
		damageDeduction.setVisible(false);
		
		btnReturn.setVisible(true);
		btnAccept.setVisible(false);
		btnCancel.setVisible(false);
	}
	
	//This method will search using the Rent ID
	//This method will search for a specific rent to see the details 
	public void search() {
		try {
			ConectionDB con = new ConectionDB();
	        Connection conection = con.conect();
	        
	        String filter = rentId.getText();
	        String where = "";
	        //Our filter must not be empty
	        if(!"".equals(filter)){
	            where = "WHERE rentId = " + filter;        //This means that if we do not type anything of the name, our WHERE will be empty and if something has been typed, our WHERE will contain the name
	        }
	        
			model = new DefaultTableModel();
	        
	    	PreparedStatement ps = null;
	        ResultSet rs = null;
	        //'refresh' will be the query that we will send to the database to show all the Rented Titles
	        String search = "SELECT rent.rentId, CONCAT(customer.name, ' ', customer.surname) AS customerName, title.name AS RentedTitle, rent.rentDay, rent.returnDay "
	        		+ "FROM rent INNER JOIN customer ON rent.custId=customer.custId "
	        		+ "INNER JOIN title ON rent.titleId=title.titleId " + where;
	        System.out.println("Query search: " + search);
	        //Adding the result to the rows of the table
	        ps = conection.prepareStatement(search);
	        rs = ps.executeQuery();
	               
	        ResultSetMetaData rsMD = rs.getMetaData();
	        int qttycol = rsMD.getColumnCount();
	            
	        model.addColumn("Rent ID");
	        model.addColumn("Customer Name");
	        model.addColumn("Rented Title");
	        model.addColumn("Rent Day");
	        model.addColumn("Expected Return Day");
	              
	        while(rs.next()){
	        	Object[] col = new Object[qttycol];
	                
	            for(int i = 0; i<qttycol; i++){
	            	col[i] = rs.getObject(i+1);
	            }
	                
	            model.addRow(col);
	        }
	        
	                
	    } catch (SQLException ex){
	    	JOptionPane.showMessageDialog(null, "Error Refreshing...!!");
	    }
    
	}
	
	//This method will Refresh our table after doing any changes
	//This method will Refresh our table after doing any changes
	public void refresh() {

        ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        try{
            
            model = new DefaultTableModel();
            
            PreparedStatement ps = null;
            ResultSet rs = null;
            //'refresh' will be the query that we will send to the database to show all the Customers
            String refresh = "SELECT rent.rentId, CONCAT(customer.name, ' ', customer.surname) AS customerName, title.name AS RentedTitle, rent.rentDay, rent.returnDay, rent.returned, rent.latenessDeduction, rent.damageDeduction "
            		+ "FROM rent "
            		+ "INNER JOIN customer ON rent.custId=customer.custId "
            		+ "INNER JOIN title ON rent.titleId=title.titleId "
            		+ "WHERE returned = 'NO';";
            System.out.println("Query refresh: " + refresh);
            //Adding the result to the rows of the table
            ps = conection.prepareStatement(refresh);
            rs = ps.executeQuery();
            
            ResultSetMetaData rsMD = rs.getMetaData();
            int qttycol = rsMD.getColumnCount();
            
            model.addColumn("Rent ID");
            model.addColumn("Customer Name");
            model.addColumn("Rented Title");
            model.addColumn("Rent Day");
            model.addColumn("Expected Return Day");
            model.addColumn("Returned");
            model.addColumn("Lateness Deduction");
            model.addColumn("Damage Deduction");
            
            while(rs.next()){
                Object[] col = new Object[qttycol];
                
                for(int i = 0; i<qttycol; i++){
                    col[i] = rs.getObject(i+1);
                }
                
                model.addRow(col);
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error Refreshing...!!");
        }
	}
	
	//This method will search using the name of the Customer
	//This method will search using the name of the Customer
	public void searchName() {
		ConectionDB con = new ConectionDB();
	    Connection conection = con.conect();
	        
	    String filter = name.getText();
	    String where = "";
	    //Our filter must not be empty
	    if(!"".equals(filter)){
	        where = "WHERE name LIKE '%" + filter + "%'";        //This means that if we do not type anything of the name, our WHERE will be empty and if something has been typed, our WHERE will contain the name
	    }
	    try{
	            
	        model = new DefaultTableModel();
	            
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        //'search' will be the query that we will send to the database to find the results
	        String search = "SELECT customer.custId, customer.name, customer.surname, membershipCard.Level, membershipCard.loyaltyPoints, membershipCard.freeRent, membershipCard.qttyRent "
	        		+ "FROM customer "
	         		+ "INNER JOIN membershipCard ON customer.custId=membershipCard.custId " + where;
	           
	        System.out.println(search);
	        ps = conection.prepareStatement(search);
	        rs = ps.executeQuery();
	        //Adding the result to the rows of the table
	        ResultSetMetaData rsMD = rs.getMetaData();
	        int qttycol = rsMD.getColumnCount();
	        
	        model.addColumn("ID");
	        model.addColumn("First Name");
	        model.addColumn("Second Name");
	        model.addColumn("Level");
	        model.addColumn("Loyalty Points");
	        model.addColumn("Free Rent");
	        model.addColumn("Titles Rented");

	        while(rs.next()){
	            Object[] col = new Object[qttycol];
	               
	            for(int i = 0; i<qttycol; i++){
	                col[i] = rs.getObject(i+1);
	            }
	                
	            model.addRow(col);
	        }
	    } catch (SQLException ex){
	        JOptionPane.showMessageDialog(null, "Error Refreshing...!!");
	    }
	}
	
	//This method will manage the return of a Title
	//This method will return the Title rented
	public void titleReturn() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        
        String filter = rentId.getText();
        String where = "";
        //Our filter must not be empty
        if(!"".equals(filter)){
            where = "WHERE rentId = " + filter;        //This means that if we do not type anything of the name, our WHERE will be empty and if something has been typed, our WHERE will contain the name
        }
        
		try {
			PreparedStatement pst = null;
            ResultSet rst = null;
			 //'titlerented' will be the query that will be send to the database to find if the Title has been returned
		    String titlerented = "SELECT returned FROM rent " + where;
		    System.out.println("title rented search: " + titlerented);
		    pst = conection.prepareStatement(titlerented);
		    rst = pst.executeQuery();
		    //We will take the result of the query and this will be written on the JTextField 'returned'
		    while(rst.next()) {
		     	rented.setText(rst.getString("returned"));
		        res = rst.getString("returned");
		        System.out.println("Is Rent?: " + rst.getString("returned"));
		    }
		    
		    if(rented.getText().equals("NO")) {
		    	
		    	try {
		    		rentedReturn.setText("YES");
					//'updaterented' will be the query that we will send to the database to find the results
		           	String updaterented = "UPDATE rent SET returned = ?, latenessDeduction = ?, damageDeduction = ? " + where;
		            System.out.println("My update Available: " + updaterented);
		            PreparedStatement newstatement = conection.prepareStatement(updaterented);
		                
		            newstatement.setString(1, rentedReturn.getText());
		            newstatement.setString(2, latenessDeduction.getText());
		            newstatement.setString(3, damageDeduction.getText());
		               
		            newstatement.execute();
		            
		            //Call the method 'returnAvailability'
		            returnAvailability();
		            //Call method 'returnQttyRented'
		            returnQttyRented();
		            
				}catch (SQLException ex){
			    	JOptionPane.showMessageDialog(null, "Error returning rented Title...!!");
			    }
		    } else {
		    	JOptionPane.showMessageDialog(null, "The Rent ID is not rented...!!");
		    }
		} catch (SQLException ex){
	    	JOptionPane.showMessageDialog(null, "The Rent ID is not correct...!!");
	    }
	}

	//This method will manage the quantity available of Titles
	//This method will manage the quantity of Titles available
	public void returnAvailability() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        
        String filter = rentId.getText();
        String where = "";
        //Our filter must not be empty
        if(!"".equals(filter)){
            where = "WHERE rentId = " + filter;
        }
        try {
        	PreparedStatement pst = null;
            ResultSet rst = null;
			 //'searchavailable' will be the query that will be send to the database to find the quantity available
		    String searchavailable = "SELECT title.available FROM title INNER JOIN rent ON title.titleId=rent.titleId " + where;
		    System.out.println("searchavailable: " + searchavailable);
		    pst = conection.prepareStatement(searchavailable);
		    rst = pst.executeQuery();
		    //We will take the result of the query and this will be written on the JTextField 'available'
		    while(rst.next()) {
		     	available.setText(rst.getString("available"));
		        res = rst.getString("available");
		        System.out.println("Quantity Available: " + rst.getString("available"));
		    }
		    
		    qttyAvailable = Integer.parseInt(available.getText());
		    System.out.println("Qtty Available: " + qttyAvailable);
		    
		    qttyAvailable ++;		//Adding +1 to the quantity Available
    	    System.out.println("Available Integer +1: " + qttyAvailable);
        	stravailable = Integer.toString(qttyAvailable);		//Converting the new quantity available to an Integer
        	System.out.println("stravailable: " + stravailable);
        	newAvailable.setText(stravailable);		//The value of that String will be written on a JTextField
        	System.out.println("New Qtty Availalble: " + stravailable);
        	
        	try {
            	PreparedStatement ps = null;
                ResultSet rs = null;
    			 //'seachtitleid' will be the query that will be send to the database to find the quantity available
    		    String seachtitleid = "SELECT title.titleId FROM title INNER JOIN rent ON title.titleId=rent.titleId " + where;
    		    System.out.println("searchavailable: " + seachtitleid);
    		    ps = conection.prepareStatement(seachtitleid);
    		    rs = ps.executeQuery();
    		    //We will take the result of the query and this will be written on the JTextField 'available'
    		    while(rs.next()) {
			     	titleId.setText(rs.getString("titleId"));
			        res = rs.getString("titleId");
			        System.out.println("Title ID: " + rs.getString("titleId"));
    		    }
	        	
    		    //Updating quantity available
    		    String newfilter = titleId.getText();
                String whereTitleId = "";
                //Our filter must not be empty
                if(!"".equals(newfilter)){
                	whereTitleId = "WHERE titleId = " + newfilter;
                }
    		    
	    	    try {
	    	    	System.out.println(whereTitleId);
	    	    	//'updateavailable' will be the query that we will send to the database to find the results
	            	String updateavailable = "UPDATE title SET available = ? " + whereTitleId;
	                System.out.println("My update Available: " + updateavailable);
	                PreparedStatement newstatement = conection.prepareStatement(updateavailable);
	                
	                newstatement.setString(1, newAvailable.getText());
	                
	                newstatement.execute();
	                
	    	    }catch (Exception e){      //If something goes wrong
	    	    	JOptionPane.showMessageDialog(null, "Error updating quantity Available");
	            }   
            }catch (Exception e){      //If something goes wrong
    	    	JOptionPane.showMessageDialog(null, "Error finding Title ID");
            }  
        } catch (SQLException ex){
	    	JOptionPane.showMessageDialog(null, "Error finding the availability...!!");
	    }    
	}
	
	//This method will manage the quantity of rented Titles of the Customer
	//This method will manage the number of Titles that the Customer has rented
	public void returnQttyRented() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        
        String filter = rentId.getText();
        String where = "";
        //Our filter must not be empty
        if(!"".equals(filter)){
            where = "WHERE rentId = " + filter;
        }
        try {
        	PreparedStatement pst = null;
            ResultSet rst = null;
			 //'searchavailable' will be the query that will be send to the database to find the quantity available
		    String searchavailable = "SELECT customer.custId FROM customer INNER JOIN rent ON customer.custId=rent.custId " + where;
		    System.out.println("searchavailable: " + searchavailable);
		    pst = conection.prepareStatement(searchavailable);
		    rst = pst.executeQuery();
		    //We will take the result of the query and this will be written on the JTextField 'available'
		    while(rst.next()) {
		     	custId.setText(rst.getString("custId"));
		        res = rst.getString("custId");
		        System.out.println("Customer ID: " + rst.getString("custId"));
		    }
		    
		    String newfilter = custId.getText();
	        String whereCustId = "";
	        //Our filter must not be empty
	        if(!"".equals(newfilter)){
	        	whereCustId = "WHERE custId = " + newfilter;
	        }
	        
	        String filternew = custId.getText();
	        String whereQtty = "";
	        //Our filter must not be empty
	        if(!"".equals(filternew)){
	        	whereQtty = "WHERE membershipCard.custId = " + filternew;
	        }
	        try {
	        	PreparedStatement psn = null;
	            ResultSet rsn = null;
				 //'searchavailable' will be the query that will be send to the database to find the quantity available
			    String searchqtty = "SELECT membershipCard.qttyRent FROM membershipCard INNER JOIN rent ON membershipCard.custId=rent.custId " + whereQtty;
			    System.out.println("searchqtty: " + searchqtty);
			    psn = conection.prepareStatement(searchqtty);
			    rsn = psn.executeQuery();
			    //We will take the result of the query and this will be written on the JTextField 'available'
			    while(rsn.next()) {
			    	qttyRent.setText(rsn.getString("qttyRent"));
			        res = rsn.getString("qttyRent");
			        System.out.println("Qtty Rent: " + rsn.getString("qttyRent"));
			    }
			    
			    strqttyRent = qttyRent.getText();		//Storing the content of the JTextField on a String
			    System.out.println(strqttyRent);
			    intqttyRent = Integer.parseInt(qttyRent.getText());		//Converting the value of that String to an Integer
			    
			    intqttyRent = intqttyRent -1;		//Subtracting -1 to the quantity of Rent available of the Customer
		 		System.out.println(intqttyRent);
		 		    
		 		QttyRent = Integer.toString(intqttyRent);		//Converting the integer to a String
		 		      
		 		newqttyRent.setText(QttyRent);		//The total of the quantity of titles rented will be storage a a String on a JTextField
			   
		 		try {
		        	
					 //'updaterentallowed' will be the query that will be send to the database to find the quantity available
				    String updaterentallowed = "UPDATE membershipCard SET qttyRent = ? " + whereCustId;
				    System.out.println("searchavailable: " + updaterentallowed);
				    PreparedStatement newstatement = conection.prepareStatement(updaterentallowed);
	                
	                newstatement.setString(1, newqttyRent.getText());
	                
	                newstatement.execute();

	                conection.close();
                    
                    JOptionPane.showMessageDialog(null, "The Title has been returned\n"
                    		+ "Please refresh");
                    normalScreen();
                    rentId.setText("");
                    name.setText("");
                    latenessDeduction.setText("0");
                    damageDeduction.setText("0");
				    
		        } catch (SQLException ex){
			    	JOptionPane.showMessageDialog(null, "Error updating Titles allowed to rent");
			    }
	        } catch (SQLException ex){
		    	JOptionPane.showMessageDialog(null, "Error finding quantity allowed to ren");
		    }     
        } catch (SQLException ex){
	    	JOptionPane.showMessageDialog(null, "Error finding the Customer ID...!!");
	    }   
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if(ac.equals("exit")){
            System.out.println("Exit the program");
            System.exit(0);
        } else if(ac.equals("menu")){
            System.out.println("Going to Main Menu");
            dispose();
        } else if(ac.equals("customers")){
            System.out.println("Going to Customers");
            new Customers();
            dispose();
        } else if(ac.equals("MemCard")){
            System.out.println("Going to Membership Card");
            new MembershipCards();
            dispose();
        } else if(ac.equals("titles")){
            System.out.println("Going to Titles");
            new Titles();
            dispose();
        } else if(ac.equals("rent")){
            System.out.println("Going to Rent");
            new Rent();
            dispose();
        } else if(ac.equals("logout")){
            System.out.println("Going back to Login");
            new LoginController();
            dispose();
        }	
	}
}
