/**
 * This is the Customers window.
 * We can see the details of the all the Customers, updated them and Delete them.
 * It will be able to search the information, using the Customer name
 * 
 * author: Cesar Alejandro Avila Calderon		Student Number: 2018451
 */
package customers;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

import rent.Rent;
import titles.Titles;
import utravision.ConectionDB;
import utravision.LoginController;
import validations.NoLetters;
import validations.NoNumbers;
import validations.ToUpperCase;
import validations.ValidLength;

public class Customers extends JFrame implements ActionListener{
	private JLabel ltitle, lID, lemail, lname, lsurname, lphoneNumber, lstreet, lnumber, lpostalCode, lcity, lcountry, lcardNumber, llevelId;
    private JTextField ID, name, surname, phoneNumber, street, number, postalCode, city, country, email, cardNumber, levelId, lastRegister, level;
    private JComboBox<String> comboLevel;
    private String res;
    private JButton btnSearch, btnNew, btnUpdateCustomer, btnDeleteCustomer, btnSaveNew, btnSaveUpdate, btnCancel;
    private DefaultTableModel model;
     
	public Customers() {
		this.setVisible(true);
        this.setSize(1100, 760);     //Size of the window
        this.setTitle("Customers");       //Title of the window
        
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
        //Title of the menu
        JMenu myMenu = new JMenu("File");
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
        ltitle = new JLabel("Customers");
        ltitle.setFont(fonttitle);
        ltitle.setBounds(500, 50, 230, 20);
        //Button refresh
        JButton btnRefresh = new JButton("Refresh");
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
        
        //Button MembershipCardss
        JButton btnMemCard = new JButton("Membership Cards");
        btnMemCard.setFont(fontButton);
        btnMemCard.setBounds(865, 70, 170, 30);
        btnMemCard.addActionListener(this);
        btnMemCard.setActionCommand("btnMembership");
        //My JTable
        try{
            ConectionDB con = new ConectionDB();
            Connection conection = con.conect();
            DefaultTableModel model = new DefaultTableModel();
                    
            PreparedStatement ps = null;
            ResultSet rs = null;
            //'refresh' will be the query that we will send to the database to show all the Membership Cards
            String refresh = "SELECT custId, name, surname, street, number, postalCode, city, country, phoneNumber, email FROM customer";     //Showing all the information of the table
                    
            ps = conection.prepareStatement(refresh);
            rs = ps.executeQuery();
            //Adding the result to the columns                    
            ResultSetMetaData rsMD = rs.getMetaData();
            int qttycol = rsMD.getColumnCount();
                    
            model.addColumn("ID");
            model.addColumn("First Name");
            model.addColumn("Second Name");
            model.addColumn("Street");
            model.addColumn("Number");
            model.addColumn("Post Code");
            model.addColumn("City");
            model.addColumn("Country");
            model.addColumn("phoneNumber");
            model.addColumn("Email");
                    
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
            //Add the Scroll to the Panel
            p.add(scroll);
                   
        } catch (SQLException ex){
        	JOptionPane.showMessageDialog(null, "Error Refreshing...!!");
        }
        
        lID = new JLabel("ID");
        lID.setFont(fontlabel);
        lID.setBounds(70, 340, 80, 20);
        ID = new JTextField();
        ID.setBounds(70, 370, 80, 25);
        new NoLetters(ID);
        new ValidLength(ID, 5);
        
        lname = new JLabel("First Name");
        lname.setFont(fontlabel);
        lname.setBounds(70, 410, 80, 20);
        name = new JTextField();
        name.setBounds(70, 440, 220, 25);
        new ValidLength(name, 50);
        new NoNumbers(name);
        //Button Search
        btnSearch = new JButton("Search");
        btnSearch.setFont(fontButton);
        btnSearch.setBounds(340, 435, 110, 30);
        btnSearch.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                //Call method 'search'
            	search();
            	//Add the model to the table
                JTable table = new JTable(model);
                //Add the scroll to the table
                JScrollPane scroll= new JScrollPane(table);
                table.setBounds(40,120,1000,200);
                scroll.setBounds(40,120,1000,200);
                //Add Scroll to the Panel
                p.add(scroll);
            }
        });
        
        lsurname = new JLabel("Second Name");
        lsurname.setFont(fontlabel);
        lsurname.setBounds(600, 410, 100, 20);
        lsurname.setVisible(false);
        surname = new JTextField();
        surname.setBounds(600, 440, 220, 25);
        surname.setVisible(false);
        new ValidLength(surname, 50);
        new NoNumbers(surname);
        
        lstreet = new JLabel("Street");
        lstreet.setFont(fontlabel);
        lstreet.setBounds(70, 480, 80, 20);
        lstreet.setVisible(false);
        street = new JTextField();
        street.setBounds(70, 510, 180, 25);
        street.setVisible(false);
        new ValidLength(street, 50);
        
        lnumber = new JLabel("Number");
        lnumber.setFont(fontlabel);
        lnumber.setBounds(350, 480, 80, 20);
        lnumber.setVisible(false);
        number = new JTextField();
        number.setBounds(350, 510, 50, 25);
        number.setVisible(false);
        new ToUpperCase(number);
        new ValidLength(number, 10);
        
        lpostalCode = new JLabel("Post Code");
        lpostalCode.setFont(fontlabel);
        lpostalCode.setBounds(480, 480, 80, 20);
        lpostalCode.setVisible(false);
        postalCode = new JTextField();
        postalCode.setBounds(480, 510, 60, 25);
        postalCode.setVisible(false);
        new ToUpperCase(postalCode);
        new ValidLength(postalCode, 5);
        
        lcity = new JLabel("City");
        lcity.setFont(fontlabel);
        lcity.setBounds(640, 480, 80, 20);
        lcity.setVisible(false);
        city = new JTextField();
        city.setBounds(640, 510, 180, 25);
        city.setVisible(false);
        new NoNumbers(city);
        new ValidLength(city, 40);
        
        lcountry = new JLabel("Country");
        lcountry.setFont(fontlabel);
        lcountry.setBounds(70, 550, 80, 20);
        lcountry.setVisible(false);
        country = new JTextField();
        country.setBounds(70, 580, 190, 25);
        country.setVisible(false);
        new NoNumbers(country);
        new ValidLength(country, 20);
        
        lphoneNumber = new JLabel("Phone Number");
        lphoneNumber.setFont(fontlabel);
        lphoneNumber.setBounds(370, 550, 100, 20);
        lphoneNumber.setVisible(false);
        phoneNumber = new JTextField();
        phoneNumber.setBounds(370, 580, 120, 25);
        phoneNumber.setVisible(false);
        new ValidLength(phoneNumber, 15);
        
        lemail = new JLabel("Email");
        lemail.setFont(fontlabel);
        lemail.setBounds(600, 550, 80, 20);
        lemail.setVisible(false);
        email = new JTextField();
        email.setBounds(600, 580, 220, 25);
        email.setVisible(false);
        new ValidLength(email, 70);
        
        lcardNumber = new JLabel("Card Number");
        lcardNumber.setFont(fontlabel);
        lcardNumber.setBounds(70, 620, 180, 20);
        lcardNumber.setVisible(false);
        cardNumber = new JTextField();
        cardNumber.setBounds(70, 650, 220, 25);
        cardNumber.setVisible(false);
        new NoLetters(cardNumber);
        new ValidLength(cardNumber, 16);
        
        llevelId = new JLabel("Level ID");
        llevelId.setFont(fontlabel);
        llevelId.setBounds(360, 620, 80, 20);
        llevelId.setVisible(false);
        //JComboBox to select the Level Id
        comboLevel = new JComboBox<String>();
        comboLevel.addItem("VL");
        comboLevel.addItem("ML");
        comboLevel.addItem("TV");
        comboLevel.addItem("PR");
        comboLevel.setBounds(360, 650, 80, 25);
        comboLevel.setVisible(false);
        
        levelId = new JTextField();
        levelId.setBounds(360, 650, 80, 25);
        levelId.setText("VL");		//The default value of the JTextField will be 'VL' (same as the JComboBox)
        levelId.setVisible(false);
        //This will be write the Level Id to a JTextField depending on the selection of the JComboBox
        comboLevel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				levelId.setText(comboLevel.getSelectedItem().toString());		//We will catch the selection of the ComboBox and convert it to String
			}
		});
        new NoNumbers(levelId);
        new ToUpperCase(levelId);
        new ValidLength(levelId, 2);
        
        lastRegister = new JTextField();
        lastRegister.setBounds(480, 650, 80, 25);
        lastRegister.setVisible(false);
        
        level = new JTextField();
        level.setEditable(false);
        level.setBounds(180, 510, 80, 25);
        level.setVisible(false);
        level.setText("Video Lover");
        //New button
        btnNew = new JButton("New");
        btnNew.setFont(fontButton);
        btnNew.setBounds(900, 400, 100, 30);
        btnNew.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	btnSaveNew.setVisible(true);
            	ID.setEditable(false);
            	editScreen();
            }
        });
        //Update button
        btnUpdateCustomer = new JButton("Update");
        btnUpdateCustomer.setFont(fontButton);
        btnUpdateCustomer.setBounds(900, 490, 100, 30);
        btnUpdateCustomer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	btnSaveUpdate.setVisible(true);
            	updateScreen();
            }
        });
        //Delete button
        btnDeleteCustomer = new JButton("Delete");
        btnDeleteCustomer.setFont(fontButton);
        btnDeleteCustomer.setBounds(900, 580, 100, 30);
        btnDeleteCustomer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	//Call the method to delete the Customer
            	deleteCustomer();
            }
        });
        //Save New VL button
        btnSaveNew = new JButton("Save");
        btnSaveNew.setFont(fontButton);
        btnSaveNew.setBounds(900, 442, 100, 30);
        btnSaveNew.setVisible(false);
        btnSaveNew.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	//Validation of required fields
            	if(name.getText().equals("") || surname.getText().equals("") || cardNumber.getText().equals("")) {		//We must type a Name and surname 
            		JOptionPane.showMessageDialog(null, "Name, Surname and Card Number cannot be empty");
            	}else {
            		//Call the method to get the description of the level
            		levelDescription();
            		//The Card Number cannot have less than 16 digits
            		if(cardNumber.getText().length()<16) {
            			JOptionPane.showMessageDialog(null, "The Card Number must have 16 digits...!!");
            		} else {
	            		//Call the method to save the new Customer
	            		newCustomer();
            		}
            	}      
            }
        });
        
        //Save Update VL button
        btnSaveUpdate = new JButton("Save");
        btnSaveUpdate.setFont(fontButton);
        btnSaveUpdate.setBounds(900, 442, 100, 30);
        btnSaveUpdate.setVisible(false);
        btnSaveUpdate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	//Validation of the mandatory fields
            	if(name.getText().equals("") || surname.getText().equals("")) {		//We must type a name and surname
            		JOptionPane.showMessageDialog(null, "Name and surname cannot be empty");
            	}else {
            		//Call method to save the update information
            		updateCustomer();
            	}
            }
        });
        //Cancel button
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(fontButton);
        btnCancel.setBounds(900, 530, 100, 30);
        btnCancel.setVisible(false);
        btnCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	normalScreen();
            	btnSaveNew.setVisible(false);
            	btnSaveUpdate.setVisible(false);
            	ID.setEditable(true);
            }
        });
        
        p.add(btnMemCard);
        p.add(btnRefresh);
        p.add(lID);
        p.add(ID);
        p.add(name);
        p.add(lname);
        p.add(lsurname);
        p.add(surname);
        p.add(btnSearch);
        p.add(lstreet);
        p.add(street);
        p.add(lnumber);
        p.add(number);
        p.add(lpostalCode);
        p.add(postalCode);
        p.add(lcity);
        p.add(city);
        p.add(lcountry);
        p.add(country);
        p.add(lphoneNumber);
        p.add(phoneNumber);
        p.add(lemail);
        p.add(email);
        p.add(lcardNumber);
        p.add(cardNumber);
        p.add(llevelId);
        p.add(comboLevel);
        p.add(levelId);
        p.add(lastRegister);
        p.add(level);
        p.add(btnNew);
        p.add(btnUpdateCustomer);
        p.add(btnDeleteCustomer);
        p.add(btnSaveNew);
        p.add(btnSaveUpdate);
        p.add(btnCancel);
        
        this.validate();
        this.repaint();
	}
	//This method will modify the window to be able to see all the required information to add a new customer and his membership card
	public void editScreen() {
		lsurname.setVisible(true);
		surname.setVisible(true);
		lstreet.setVisible(true);
		street.setVisible(true);
		lnumber.setVisible(true);
		number.setVisible(true);
		lpostalCode.setVisible(true);
		postalCode.setVisible(true);
		lcity.setVisible(true);
		city.setVisible(true);
		lcountry.setVisible(true);
		country.setVisible(true);
		lphoneNumber.setVisible(true);
		phoneNumber.setVisible(true);
		lemail.setVisible(true);
		email.setVisible(true);
		lcardNumber.setVisible(true);
		cardNumber.setVisible(true);
		llevelId.setVisible(true);
		comboLevel.setVisible(true);
		btnNew.setVisible(false);
		btnUpdateCustomer.setVisible(false);
		btnDeleteCustomer.setVisible(false);
		btnCancel.setVisible(true);
	}
	//This method will return the components of the window to their original state.
	public void normalScreen() {
		ID.setText("");
		name.setText("");
		lsurname.setVisible(false);
		surname.setVisible(false);
		surname.setText("");
		lstreet.setVisible(false);
		street.setVisible(false);
		street.setText("");
		lnumber.setVisible(false);
		number.setVisible(false);
		number.setText("");
		lpostalCode.setVisible(false);
		postalCode.setVisible(false);
		postalCode.setText("");
		lcity.setVisible(false);
		city.setVisible(false);
		city.setText("");
		lcountry.setVisible(false);
		country.setVisible(false);
		country.setText("");
		lphoneNumber.setVisible(false);
		phoneNumber.setVisible(false);
		phoneNumber.setText("");
		lemail.setVisible(false);
		email.setVisible(false);
		email.setText("");
		lcardNumber.setVisible(false);
		cardNumber.setVisible(false);
		cardNumber.setText("");
		llevelId.setVisible(false);
		comboLevel.setVisible(false);
		btnNew.setVisible(true);
		btnUpdateCustomer.setVisible(true);
		btnDeleteCustomer.setVisible(true);
		btnCancel.setVisible(false);
	}
	//This method will modify the window to be able to see all the required information to update Customers
	public void updateScreen() {
		lsurname.setVisible(true);
		surname.setVisible(true);
		lstreet.setVisible(true);
		street.setVisible(true);
		lnumber.setVisible(true);
		number.setVisible(true);
		lpostalCode.setVisible(true);
		postalCode.setVisible(true);
		lcity.setVisible(true);
		city.setVisible(true);
		lcountry.setVisible(true);
		country.setVisible(true);
		lphoneNumber.setVisible(true);
		phoneNumber.setVisible(true);
		lemail.setVisible(true);
		email.setVisible(true);

		btnNew.setVisible(false);
		btnUpdateCustomer.setVisible(false);
		btnDeleteCustomer.setVisible(false);
		btnCancel.setVisible(true);
	}
	//This method will Refresh our table after doing any changes
	public void refresh() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        try{
            
            model = new DefaultTableModel();
            
            PreparedStatement ps = null;
            ResultSet rs = null;
            //'refresh' will be the query that we will send to the database to show all the Customers
            String refresh = "SELECT custId, name, surname, street, number, postalCode, city, country, phoneNumber, email FROM customer;";
            //Adding the result to the rows of the table
            ps = conection.prepareStatement(refresh);
            rs = ps.executeQuery();
            
            ResultSetMetaData rsMD = rs.getMetaData();
            int qttycol = rsMD.getColumnCount();
            
            model.addColumn("ID");
            model.addColumn("First Name");
            model.addColumn("Second Name");
            model.addColumn("Street");
            model.addColumn("Number");
            model.addColumn("Post Code");
            model.addColumn("City");
            model.addColumn("Country");
            model.addColumn("phoneNumber");
            model.addColumn("Email");
            
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
	//This method will search by name
	public void search() {
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
            String search = "SELECT custId, name, surname, street, number, postalCode, city, country, phoneNumber, email FROM customer " + where;
            
            System.out.println(search);
            ps = conection.prepareStatement(search);
            rs = ps.executeQuery();
            //Adding the result to the rows of the table
            ResultSetMetaData rsMD = rs.getMetaData();
            int qttycol = rsMD.getColumnCount();
            
            model.addColumn("ID");
            model.addColumn("First Name");
            model.addColumn("Second Name");
            model.addColumn("Street");
            model.addColumn("Number");
            model.addColumn("Post Code");
            model.addColumn("City");
            model.addColumn("Country");
            model.addColumn("phoneNumber");
            model.addColumn("Email");
            
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
	//This method will save the information of a new Customer
	public void newCustomer() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        
        try{
            //'adduser' will be query that will be send to the database to add a new register on the table customer                    
            String adduser = "INSERT INTO customer (name, surname, street, number, postalCode, city, country, phoneNumber, email) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
            
            PreparedStatement statement = conection.prepareStatement(adduser);
            statement.setString(1, name.getText());
            statement.setString(2, surname.getText());
            statement.setString(3, street.getText());
            statement.setString(4, number.getText());
            statement.setString(5, postalCode.getText());
            statement.setString(6, city.getText());
            statement.setString(7, country.getText());
            statement.setString(8, phoneNumber.getText());
            statement.setString(9, email.getText());
            
            statement.executeUpdate();
            //Once the information of the table customer is inserted, we will take the last Customer Id registered
            lastCustomer();
            
            conection.close();
            
            JOptionPane.showMessageDialog(null, "New User inserted successfully\n"
            		+ "Please refresh");
            ID.setText("");
            name.setText("");
            surname.setText("");
            street.setText("");
            number.setText("");
            postalCode.setText("");
            city.setText("");
            country.setText("");
            phoneNumber.setText("");
            email.setText("");
            cardNumber.setText("");
            ID.setEditable(true);
            
        } catch (Exception e){      //If something goes wrong
            JOptionPane.showMessageDialog(null, "Error inserting a new User!");
        }
	}
	//This method will take the last Customer register
	public void lastCustomer() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
		try{
            
            PreparedStatement ps = null;
            ResultSet rs = null;
            //'search' will be the query that will be send to the database to find the last Customer Id added
            String search = "SELECT custId FROM customer ORDER BY custId DESC LIMIT 1";
            
            System.out.println(search);
            ps = conection.prepareStatement(search);
            rs = ps.executeQuery();
            //We will take the result of the query and this will be written on the JTextField 'lastRegister'
            while(rs.next()) {
            	lastRegister.setText(rs.getString("custId"));
            	res = rs.getString("custId");
            	System.out.println("The last register is: " + rs.getString("custId"));
            }
            //Once we know the last Customer Id and when the Id is already on the JTextField we will insert the information on the table membershiCard
            saveCard();
        } catch (SQLException ex){		//If something goes wrong when we try to find the Id of the last Customer 
            JOptionPane.showMessageDialog(null, "Error finding last Register...!!");
        }
	}
	//This method will save the Card Number
	public void saveCard() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
		try{
            //'addcard' will be the query that will be send to the database to add a the Card details of the new customer
            String addcard = "INSERT INTO membershipCard (cardNumber, levelId, level, custId) VALUES(?, ?, ?, ?)"; 
            System.out.println(cardNumber.getText());
            System.out.println(levelId.getText());
            System.out.println(level.getText());
            System.out.println(lastRegister.getText());
            System.out.println(addcard);
            PreparedStatement stt = conection.prepareStatement(addcard);
            stt.setString(1, cardNumber.getText());
            stt.setString(2, levelId.getText());
            stt.setString(3, level.getText());
            stt.setString(4, lastRegister.getText());
            System.out.println("The levelID is: " + levelId.getText());
            stt.executeUpdate();
            
        } catch (Exception e){      //If something goes wrong when we try to save the details of the Membership Card 
        	JOptionPane.showMessageDialog(null, "Error inserting a new Membership Card!");
        }
	}
	//This method will update the details of the Customer
	public void updateCustomer() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        //Declaring our 'where' condition to be used as filter
        String filter = ID.getText();
        System.out.println("My filter is: " + filter);
        String where = "";
        //Our filter must not be empty
        if(!"".equals(filter)){
            where = "WHERE custId = '" + filter + "'";
            System.out.println("My where is: " + where);
            try{
            //'updatecustomer' will be the query that we will send to the database to find the results             
            String updatecustomer = "UPDATE customer SET name = ?, surname = ?, street = ?, number = ?, postalCode = ?, city = ?, country = ?, phoneNumber = ?, email = ? " + where; 
            System.out.println(updatecustomer);
            PreparedStatement statement = conection.prepareStatement(updatecustomer);
            statement.setString(1, name.getText());
            statement.setString(2, surname.getText());
            statement.setString(3, street.getText());
            statement.setString(4, number.getText());
            statement.setString(5, postalCode.getText());
            statement.setString(6, city.getText());
            statement.setString(7, country.getText());
            statement.setString(8, phoneNumber.getText());
            statement.setString(9, email.getText());
            
            statement.execute();
               
            conection.close();
            
            JOptionPane.showMessageDialog(null, "User updated successfully\n"
            		+ "Please refresh");
            ID.setText("");
            name.setText("");
            surname.setText("");
            street.setText("");
            number.setText("");
            postalCode.setText("");
            city.setText("");
            country.setText("");
            phoneNumber.setText("");
            email.setText("");
            
            } catch (Exception e){
            	JOptionPane.showMessageDialog(null, "Error updating Customer! \n"		//If the Customer ID is not valid
            			+ "	· Customer ID must be a valid numeric ID");
            }
            
        } else {       //The ID must not be empty
            JOptionPane.showMessageDialog(null, "Error updating Customer! \n"
                    + "	· The ID cannot be empty");
        }
	}
	//This method will delete the Customer
	public void deleteCustomer() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        //Declaring our 'where' condition to be used as filter
        String filter = ID.getText();
        String where = "";
        System.out.println("My filter is " + filter);
        //Our filter must not be empty
        if(!"".equals(filter)){     //
            where = "WHERE custId = '" + filter + "'";
            System.out.println("My WHERE is: " + where);
            try{
            	//'deleteCard' will be the query that we will send to the database to delete Membership Card
            	String deleteCard = "DELETE FROM membershipCard WHERE IdCard = ?"; 
                
                PreparedStatement statement = conection.prepareStatement(deleteCard);
                statement.setString(1, ID.getText());
                System.out.println("my query is: " + deleteCard);
                statement.execute();
                
                //Call method to delete the Membership Card of the Customer
                deleteCard();
                
                conection.close();
                
                JOptionPane.showMessageDialog(null, "Customer deleted successfully\n"
                		+ "Please refresh");
                ID.setText("");
                name.setText("");
                surname.setText("");
                street.setText("");
                number.setText("");
                postalCode.setText("");
                city.setText("");
                country.setText("");
                phoneNumber.setText("");
                email.setText("");
                cardNumber.setText("");
                lastRegister.setText("");

            } catch (Exception e){      //If something goes wrong when trying to delete the Customer
            	JOptionPane.showMessageDialog(null, "Error deleting the Customer!");
            }
            
        }else {       //The ID must not be a empty
            JOptionPane.showMessageDialog(null, "Error deleting the Customer! Possible reassons: \n"
                    + "	· The ID cannot be empty");
        }
	}
	//This method will delete the Membership Card of the Customer
	public void deleteCard() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
		try {
        	//'deleteCustomer' will be the query that we will send to the database to delete Customer
        	String deleteCustomer = "DELETE FROM customer WHERE custId = ?"; 
            
            PreparedStatement stt = conection.prepareStatement(deleteCustomer);
            stt.setString(1, ID.getText());
            System.out.println("my query is: " + deleteCustomer);
            stt.execute();
            
        } catch(SQLException ex) {		//If something goes wrong when trying to delete the Membership Card
        	JOptionPane.showMessageDialog(null, "Error deleting the Membership Card...!!");
        }
	}
	//This method will take the written value of the Level Id and depending on it, it will add the description of the level
	public void levelDescription() {
		if(levelId.getText().equals("PR")) {
    		System.out.println("LevelId: " + levelId.getText());
    		level.setText("Premium");
    		System.out.println("Level Description: " + level.getText());
		}else {
			if(levelId.getText().equals("TV")) {
                		System.out.println("LevelId: " + levelId.getText());
                		level.setText("TV Lover");
                		System.out.println("Level Description: " + level.getText());
			}else {
				if(levelId.getText().equals("ML")) {
                		System.out.println("LevelId: " + levelId.getText());
                		level.setText("Music Lover");
                		System.out.println("Level Description: " + level.getText());
				}else {
					if(levelId.getText().equals("VL")) {
                		System.out.println("LevelId: " + levelId.getText());
                		level.setText("Video Lover");
                		System.out.println("Level Description: " + level.getText());
					}
				}
			}
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
        } else if(ac.equals("MemCard") || ac.equals("btnMembership")){
            System.out.println("Going to Membership Card");
            new MembershipCards();
            dispose();
        } else if(ac.equals("titles")){
            System.out.println("Going to Titles");
            new Titles();
            dispose();
        }
        else if(ac.equals("rent")){
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
