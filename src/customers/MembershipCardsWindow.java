/**
 * This is Membership Card window.
 * We can see the details of the Membership Card as well as edit them.
 * It will be able to search the information, using the Customer name
 * 
 * author: Cesar Alejandro Avila Calderon		Student Number: 2018451
 */
package customers;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class MembershipCardsWindow extends JFrame implements ActionListener{
	public JLabel ltitle, lID, lname, lcardNumber, llevelId, llevel, lloyaltyPoints, lfreeRent, lCustomerId;
	public JTextField ID, name, cardNumber, level, levelId, loyaltyPoints, freeRent;
	public JComboBox<String> comboLevel;
	public JButton btnSearch, btnNew, btnUpdateCard, btnDeleteCard, btnSaveUpdate, btnCancel, btnSaveNew;
	private DefaultTableModel model;
	private final MembershipCardsController controllerInternalRef;
	
	
	public MembershipCardsWindow(MembershipCardsController controller) {
		
		this.controllerInternalRef = controller;
        attributesSetter();
        components();
        validation();
    }
	
	private void attributesSetter(){
		this.setVisible(true);
        this.setSize(1100, 730);     //Size of the window
        this.setTitle("Membeship Cards Window");       //Title of the window
    }

	private void components() {
		
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
        //Title of the Window
        ltitle = new JLabel("Membership Cards");
        ltitle.setFont(fonttitle);
        ltitle.setBounds(420, 50, 290, 40);
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
        //Button MembershipCards
        JButton btnCustomers = new JButton("Customers");
        btnCustomers.setFont(fontButton);
        btnCustomers.setBounds(865, 70, 100, 30);
        btnCustomers.addActionListener(this);
        btnCustomers.setActionCommand("btnCustomers");
        
        lID = new JLabel("Card ID");
        lID.setFont(fontlabel);
        lID.setBounds(70, 340, 120, 20);
        ID = new JTextField();
        ID.setBounds(70, 370, 80, 25);
        new NoLetters(ID);
        new ValidLength(ID, 5);
        
        lCustomerId = new JLabel("Customer ID");
        lCustomerId.setFont(fontlabel);
        lCustomerId.setBounds(70, 340, 120, 20);
        lCustomerId.setVisible(false);
        
        lname = new JLabel("Customer Name");
        lname.setFont(fontlabel);
        lname.setBounds(70, 410, 120, 20);
        name = new JTextField();
        name.setBounds(70, 440, 220, 25);
        new ValidLength(name, 50);
        new NoNumbers(name);
        //Button search
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
                //Add the scroll to the Panel
                p.add(scroll);
            }
        });
        
        lcardNumber = new JLabel("Card Number");
        lcardNumber.setFont(fontlabel);
        lcardNumber.setBounds(500, 410, 180, 20);
        lcardNumber.setVisible(false);
        cardNumber = new JTextField();
        cardNumber.setBounds(500, 440, 220, 25);
        cardNumber.setVisible(false);
        new NoLetters(cardNumber);
        new ValidLength(cardNumber, 16);
        
        llevelId = new JLabel("Level ID");
        llevelId.setFont(fontlabel);
        llevelId.setBounds(70, 480, 80, 20);
        llevelId.setVisible(false);
        //JComboBox to select the Level Id
        comboLevel = new JComboBox<String>();
        comboLevel.addItem("VL");
        comboLevel.addItem("ML");
        comboLevel.addItem("TV");
        comboLevel.addItem("PR");
        comboLevel.setBounds(70, 510, 80, 25);
        comboLevel.setVisible(false);
        
        levelId = new JTextField();
        levelId.setBounds(200, 510, 80, 25);
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
        
        llevel = new JLabel("Level");
        llevel.setFont(fontlabel);
        llevel.setBounds(180, 480, 80, 20);
        llevel.setVisible(false);
        level = new JTextField();
        level.setEditable(false);
        level.setBounds(180, 510, 80, 25);
        level.setVisible(false);
        
        /*
        //Save New Card button
        btnSaveNew = new JButton("Save");
        btnSaveNew.setFont(fontButton);
        btnSaveNew.setBounds(250, 600, 100, 30);
        btnSaveNew.setVisible(false);
        btnSaveNew.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	
            	if(cardNumber.getText().equals("") || levelId.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "Card Number and Level ID cannot be empty");
            	}else {
            		if(levelId.getText().equals("PR") || levelId.getText().equals("TV") || levelId.getText().equals("ML") || levelId.getText().equals("VL")) {

            			levelDescription();
                		
                		ConectionDB con = new ConectionDB();
                        Connection conection = con.conect();
                        
                        try{
                                                
                            String addcard = "INSERT INTO membershipCard (cardNumber, levelId, level, custId) VALUES(?, ?, ?, ?)"; 
                            System.out.println(cardNumber.getText());
                            System.out.println(levelId.getText());
                            System.out.println(level.getText());
                            System.out.println(ID.getText());
                            System.out.println(addcard);
                            PreparedStatement statement = conection.prepareStatement(addcard);
                            statement.setString(1, cardNumber.getText());
                            statement.setString(2, levelId.getText());
                            statement.setString(3, level.getText());
                            statement.setString(4, ID.getText());
                            System.out.println("The levelID is: " + levelId.getText());
                            statement.executeUpdate();
                               
                            conection.close();
                            
                            JOptionPane.showMessageDialog(null, "New Membership Card inserted successfully");
                            ID.setText("");
                            cardNumber.setText("");
                            levelId.setText("");
                            level.setText("");   
                            
                            normalScreen();
                            btnSaveNew.setVisible(false);
                            
                            } catch (Exception e){      //If something goes wrong
                            	JOptionPane.showMessageDialog(null, "Error inserting a new Membership Card!");
                            	}   		
                		}else {
                			JOptionPane.showMessageDialog(null, "Error inserting a new Membership Card!\n"
                					+ "The only valid options for the level ID are:\n"
                					+ "   � VL\n"
                					+ "   � ML\n"
                					+ "   � TV\n"
                					+ "   � PR\n");
                			}
            		} 
            	}
            });*/
        
        //Update Card button
        btnSaveUpdate = new JButton("Save");
        btnSaveUpdate.setFont(fontButton);
        btnSaveUpdate.setBounds(250, 600, 100, 30);
        btnSaveUpdate.setVisible(false);
        btnSaveUpdate.addActionListener((ActionListener) controllerInternalRef);
        btnSaveUpdate.setActionCommand("b");
        
        //Cancel button
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(fontButton);
        btnCancel.setBounds(450, 600, 100, 30);
        btnCancel.setVisible(false);
        btnCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	//Call method 'normalScreen'
            	normalScreen();
            	btnSaveNew.setVisible(false);
            	btnSaveUpdate.setVisible(false);
            }
        });
        
        /*lloyaltyPoints = new JLabel("Loyalty Points");
        lloyaltyPoints.setFont(fontlabel);
        lloyaltyPoints.setBounds(300, 480, 120, 20);
        loyaltyPoints = new JTextField();
        loyaltyPoints.setBounds(300, 510, 80, 25);
        
        lfreeRent = new JLabel("Free Rent");
        lfreeRent.setFont(fontlabel);
        lfreeRent.setBounds(420, 480, 80, 20);
        freeRent = new JTextField();
        freeRent.setBounds(420, 510, 80, 25);*/
        
        /*//New button
        btnNew = new JButton("New");
        btnNew.setFont(fontButton);
        btnNew.setBounds(870, 400, 100, 30);
        btnNew.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	btnSaveNew.setVisible(true);
            	editScreen();     
            }
        });*/
        
        //Update button
        btnUpdateCard = new JButton("Update");
        btnUpdateCard.setFont(fontButton);
        btnUpdateCard.setBounds(870, 490, 100, 30);
        btnUpdateCard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	btnSaveUpdate.setVisible(true);
            	//Call method 'editScreen'
            	editScreen();     
            }
        });
        
        /*//Delete button
        btnDeleteCard = new JButton("Delete");
        btnDeleteCard.setFont(fontButton);
        btnDeleteCard.setBounds(870, 580, 100, 30);
        btnDeleteCard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                
                String filter = ID.getText();
                String where = "";
                System.out.println("My filter is " + filter);
                if(!"".equals(filter)){     //
                    where = "WHERE IdCard = '" + filter + "'";
                    System.out.println("My WHERE is: " + where);
                    try{
                                       
                    //JOptionPane.showMessageDialog(null, "Connected successfully");
                                        
                    String updateuser = "DELETE FROM membershipCard WHERE IdCard = ?"; 
                    
                    PreparedStatement statement = conection.prepareStatement(updateuser);
                    statement.setString(1, ID.getText());
                    System.out.println("my query is: " +updateuser);
                    statement.execute();
                       
                    conection.close();
                    
                    JOptionPane.showMessageDialog(null, "Membership Card deleted successfully");
                    ID.setText("");
                    
                } catch (Exception e){      //If something goes wrong
                    JOptionPane.showMessageDialog(null, "Error deleting the Membership Card! Possible reassons: \n"
                            + "* The Card ID might not be correct!");
                }
                }
                else{       //The ID must be a valid ID number
                    JOptionPane.showMessageDialog(null, "Error deleting the Membership Card! Possible reassons: \n"
                            + "* The Card ID cannot be empty");
                }
            }
        });*/
        
        p.add(ltitle);
        p.add(btnRefresh);
        p.add(lID);
        p.add(ID);
        p.add(lCustomerId);
        p.add(lname);
        p.add(name);
        p.add(btnSearch);
        p.add(btnCustomers);
        p.add(lcardNumber);
        p.add(cardNumber);
        p.add(llevelId);
        p.add(levelId);
        p.add(comboLevel);
        p.add(llevel);
        p.add(level);
        /*p.add(lloyaltyPoints);
        p.add(loyaltyPoints);
        p.add(lfreeRent);
        p.add(freeRent);*/
        p.add(levelId);
        //p.add(btnSaveNew);
        p.add(btnSaveUpdate);
        p.add(btnCancel);
        //p.add(btnNew);
        p.add(btnUpdateCard);
        //p.add(btnDeleteCard);
	}
	
	private void validation(){
        this.validate();
        this.repaint();
    }
	
	
	//This method will modify the window to be able to see all the required information to update the membership card
	public void editScreen() {
		lID.setVisible(false);
		lCustomerId.setVisible(true);
		lcardNumber.setVisible(true);
		cardNumber.setVisible(true);
		llevelId.setVisible(true);
		comboLevel.setVisible(true);
		
		//btnNew.setVisible(false);
		btnUpdateCard.setVisible(false);
		//btnDeleteCard.setVisible(false);
		
		btnCancel.setVisible(true);
	}
	//This method will return the components of the window to their original state
	public void normalScreen() {
		lID.setVisible(true);
		lCustomerId.setVisible(false);
		lcardNumber.setVisible(false);
		cardNumber.setVisible(false);
		llevelId.setVisible(false);
		comboLevel.setVisible(false);
		
		//btnNew.setVisible(true);
		btnUpdateCard.setVisible(true);
		//btnDeleteCard.setVisible(true);
		
		btnCancel.setVisible(false);
		
		cardNumber.setText("");
    	ID.setText("");
	}
	//This method will Refresh our table after doing any changes
	public void refresh() {
        ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        try{
            
            model = new DefaultTableModel();
            
            PreparedStatement ps = null;
            ResultSet rs = null;
            //'refresh' will be the query that we will send to the database to show all the Membership Cards
            String refresh = "SELECT membershipCard.idCard, CONCAT(customer.name, ' ', customer.surname) AS customerName, customer.custId, membershipCard.cardNumber, membershipCard.levelId, membershipCard.level, membershipCard.loyaltyPoints, membershipCard.freeRent "
            		+ "FROM membershipCard "
            		+ "INNER JOIN customer ON membershipCard.idCard=customer.custId;";
            //Adding the result to the rows of the table
            ps = conection.prepareStatement(refresh);
            rs = ps.executeQuery();
            
            ResultSetMetaData rsMD = rs.getMetaData();
            int qttycol = rsMD.getColumnCount();
            
            model.addColumn("Card Id");
            model.addColumn("Customer Name");
            model.addColumn("Customer Id");
            model.addColumn("Card Number");
            model.addColumn("Level Id");
            model.addColumn("Level");
            model.addColumn("Loyalty Points");
            model.addColumn("Free Rent");
            
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
            where = "WHERE customer.name LIKE '%" + filter + "%'";        //This means that if we do not type anything of the name, our WHERE will be empty and if something has been typed, our WHERE will contain the name
        }
        try{
            
            model = new DefaultTableModel();
            
            PreparedStatement ps = null;
            ResultSet rs = null;
            //'search' will be the query that we will send to the database to find the results
            String search = "SELECT membershipCard.idCard, CONCAT(customer.name, ' ', customer.surname) AS customerName, customer.custId, membershipCard.cardNumber, membershipCard.levelId, membershipCard.level, membershipCard.loyaltyPoints, membershipCard.freeRent "
            		+ "FROM membershipCard "
            		+ "INNER JOIN customer ON membershipCard.idCard=customer.custId " + where;
            
            System.out.println(search);
            ps = conection.prepareStatement(search);
            rs = ps.executeQuery();
            //Adding the result to the rows of the table
            ResultSetMetaData rsMD = rs.getMetaData();
            int qttycol = rsMD.getColumnCount();
            
            model.addColumn("Card Id");
            model.addColumn("Customer Name");
            model.addColumn("Customer Id");
            model.addColumn("Card Number");
            model.addColumn("Level Id");
            model.addColumn("Level");
            model.addColumn("Loyalty Points");
            model.addColumn("Free Rent");
            
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
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if(ac.equals("exit")){
            System.out.println("Exit the program");
            System.exit(0);
        } else if(ac.equals("menu")){
            System.out.println("Going to Main Menu");
            dispose();
        } else if(ac.equals("customers") || ac.equals("btnCustomers")){
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
