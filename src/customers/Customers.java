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
	private JLabel lID, lemail, lname, lsurname, lphoneNumber, lstreet, lnumber, lpostalCode, lcity, lcountry;
    private JTextField ID, name, surname, phoneNumber, street, number, postalCode, city, country, email;
    private JButton btnSearch, btnNew, btnUpdateCustomer, btnDeleteCustomer, btnSaveNew, btnSaveUpdate, btnCancel;
     
	public Customers() {
		this.setVisible(true);
        this.setSize(1100, 730);     //Size of the window
        this.setTitle("Customers");       //Title of the window
        
        JPanel p = new JPanel();
        p.setLayout(null);
        this.add(p);
        p.setBackground(java.awt.Color.orange);     //Color of the window
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
        //LogOut
        JMenuItem LogOut = new JMenuItem("Logout");
        myMenu.add(LogOut);
        LogOut.addActionListener(this);
        LogOut.setActionCommand("logout");
        //Close the program
        JMenuItem Close = new JMenuItem("Exit");
        myMenu.add(Close);
        Close.addActionListener(this);
        Close.setActionCommand("exit");
        
        //Button refresh
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(fontButton);
        btnRefresh.setBounds(40, 70, 100, 30);
        btnRefresh.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                try{
                    
                    DefaultTableModel model = new DefaultTableModel();
                    
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    
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

                    JTable table = new JTable(model);
                    
                    JScrollPane scroll= new JScrollPane(table);
                    table.setBounds(40,120,1000,200);
                    scroll.setBounds(40,120,1000,200);

                    p.add(scroll);
                    
                } catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error Refreshing...!!");
                }
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

                    JTable table = new JTable(model);
                    JScrollPane scroll= new JScrollPane(table);
                    table.setBounds(40,120,1000,200);
                    scroll.setBounds(40,120,1000,200);

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
        
        //Search button
        btnSearch = new JButton("Search");
        btnSearch.setFont(fontButton);
        btnSearch.setBounds(340, 435, 110, 30);
        btnSearch.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                
                String filter = name.getText();
                String where = "";
                
                if(!"".equals(filter)){
                    where = "WHERE name LIKE '%" + filter + "%'";        //This means that if we do not type anything of the name, our WHERE will be empty and if something has been typed, our WHERE will contain the name
                }
                try{
                    
                    DefaultTableModel model = new DefaultTableModel();
                    
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    
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

                    JTable table = new JTable(model);
                    JScrollPane scroll= new JScrollPane(table);
                    table.setBounds(40,120,1000,200);
                    scroll.setBounds(40,120,1000,200);

                    p.add(scroll);
                    
                } catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error Refreshing...!!");
                }
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
        
        //New button
        btnNew = new JButton("New");
        btnNew.setFont(fontButton);
        btnNew.setBounds(900, 400, 100, 30);
        btnNew.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	btnSaveNew.setVisible(true);
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
            	editScreen();
            }
        });
        
        //Delete button
        btnDeleteCustomer = new JButton("Delete");
        btnDeleteCustomer.setFont(fontButton);
        btnDeleteCustomer.setBounds(900, 580, 100, 30);
        btnDeleteCustomer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                
                String filter = ID.getText();
                String where = "";
                System.out.println("My filter is " + filter);
                if(!"".equals(filter)){     //
                    where = "WHERE custId = '" + filter + "'";
                    System.out.println("My WHERE is: " + where);
                    try{
                                       
                    //JOptionPane.showMessageDialog(null, "Connected successfully");
                                        
                    String updateuser = "DELETE FROM customer WHERE custId = ?"; 
                    
                    PreparedStatement statement = conection.prepareStatement(updateuser);
                    statement.setString(1, ID.getText());
                    System.out.println("my query is: " +updateuser);
                    statement.execute();
                       
                    conection.close();
                    
                    JOptionPane.showMessageDialog(null, "Customer deleted successfully");
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
                    
                } catch (Exception e){      //If something goes wrong
                    JOptionPane.showMessageDialog(null, "Error deleting the Customer!");
                }
                }
                else{       //The ID must be a valid ID number
                    JOptionPane.showMessageDialog(null, "Error deleting the Customer! Possible reassons: \n"
                            + "* The ID cannot be empty");
                }
            }
        });
        
        //Save New VL button
        btnSaveNew = new JButton("Save");
        btnSaveNew.setFont(fontButton);
        btnSaveNew.setBounds(900, 442, 100, 30);
        btnSaveNew.setVisible(false);
        btnSaveNew.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	
            	if(name.getText().equals("") || surname.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "Name and Surname cannot be empty");
            	}else {
            		ConectionDB con = new ConectionDB();
                    Connection conection = con.conect();
                    
                    try{
                        
                        //JOptionPane.showMessageDialog(null, "Connected successfully");
                                            
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
                           
                        conection.close();
                        
                        JOptionPane.showMessageDialog(null, "New User inserted successfully");
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
                        
                    } catch (Exception e){      //If something goes wrong
                        JOptionPane.showMessageDialog(null, "Error inserting a new User!");
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
            	if(name.getText().equals("") || surname.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "Name and surname cannot be empty");
            	}else {
            		ConectionDB con = new ConectionDB();
                    Connection conection = con.conect();
                    
                    String filter = ID.getText();
                    System.out.println("My filter is: " + filter);
                    String where = "";
                    
                    if(!"".equals(filter)){
                        where = "WHERE custId = '" + filter + "'";
                        System.out.println("My where is: " + where);
                        try{
                                           
                        //JOptionPane.showMessageDialog(null, "Connected successfully");
                                            
                        String updateartist = "UPDATE customer SET name = ?, surname = ?, street = ?, number = ?, postalCode = ?, city = ?, country = ?, phoneNumber = ?, email = ? " + where; 
                        System.out.println(updateartist);
                        PreparedStatement statement = conection.prepareStatement(updateartist);
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
                        
                        JOptionPane.showMessageDialog(null, "User updated successfully");
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
                        JOptionPane.showMessageDialog(null, "Error updating Customer! \n"
                                + "* Customer ID must be a valid numeric ID");
                        }
                        }
                    else{       //The ID must have a valid ID number
                        JOptionPane.showMessageDialog(null, "Error updating Customer! \n"
                                + "* The ID cannot be empty");
                    }
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
        p.add(btnNew);
        p.add(btnUpdateCustomer);
        p.add(btnDeleteCustomer);
        p.add(btnSaveNew);
        p.add(btnSaveUpdate);
        p.add(btnCancel);
        
        this.validate();
        this.repaint();
	}
	
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
		btnNew.setVisible(false);
		btnUpdateCustomer.setVisible(false);
		btnDeleteCustomer.setVisible(false);
		btnCancel.setVisible(true);
	}
	
	public void normalScreen() {
		lsurname.setVisible(false);
		surname.setVisible(false);
		lstreet.setVisible(false);
		street.setVisible(false);
		lnumber.setVisible(false);
		number.setVisible(false);
		lpostalCode.setVisible(false);
		postalCode.setVisible(false);
		lcity.setVisible(false);
		city.setVisible(false);
		lcountry.setVisible(false);
		country.setVisible(false);
		lphoneNumber.setVisible(false);
		phoneNumber.setVisible(false);
		lemail.setVisible(false);
		email.setVisible(false);
		btnNew.setVisible(true);
		btnUpdateCustomer.setVisible(true);
		btnDeleteCustomer.setVisible(true);
		btnCancel.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if(ac.equals("exit")){
            System.out.println("Exit the program");
            System.exit(0);
        }
		else if(ac.equals("menu")){
            System.out.println("Going to Main Menu");
            dispose();
        }
        else if(ac.equals("customers")){
            System.out.println("Going to Customers");
            new Customers();
            dispose();
        }
        else if(ac.equals("MemCard") || ac.equals("btnMembership")){
            System.out.println("Going to Membership Card");
            new MembershipCards();
            dispose();
        }
        else if(ac.equals("titles")){
            System.out.println("Going to Titles");
            new Titles();
            dispose();
        }
        else if(ac.equals("rent")){
            System.out.println("Going to Rent");
            new Rent();
            dispose();
        }
        else if(ac.equals("logout")){
            System.out.println("Going back to Login");
            new LoginController();
            dispose();
        }	
	}
}
