/**
 * This is the class for the MC option (CD Music Titles).
 * It will contain all the information related to CD.
 * It will be possible to add new CD Titles, as well as update and delete them.
 * Search tool will be available
 * 
 * author: Cesar Alejandro Avila Calderon		Student Number: 2018451
 */
package titles;

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

import customers.Customers;
import customers.MembershipCards;
import rent.Rent;
import utravision.ConectionDB;
import utravision.LoginController;
import validations.NoLetters;
import validations.NoNumbers;
import validations.ValidLength;

public class MC extends JFrame implements ActionListener{
	private JLabel ltitle, ltitleId, lname, lreleaseYear, lgenre, lartist, lproductionCompany, lstock, lavailable, lrentPrice;
    private JTextField titleId, lastRegister, name, releaseYear, genre, typeId, type, artist, productionCompany, format, stock, available, rentPrice, musicId;
    private String res;
    private JButton btnSearch, btnNew, btnUpdateMC, btnDeleteMC, btnSaveNew, btnSaveUpdate, btnCancel;
    DefaultTableModel model;

	public MC() {
		this.setVisible(true);
        this.setSize(1100, 730);     //Size of the window
        this.setTitle("CD Music Titles");       //Title of the window
        
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
        ltitle = new JLabel("CD Music Titles");
        ltitle.setFont(fonttitle);
        ltitle.setBounds(450, 50, 230, 20);
        //Button refresh
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(fontButton);
        btnRefresh.setBounds(40, 70, 110, 30);
        btnRefresh.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	refresh();
            	
            	JTable table = new JTable(model);
                
                JScrollPane scroll= new JScrollPane(table);
                table.setBounds(40,120,1000,200);
                scroll.setBounds(40,120,1000,200);
                p.add(scroll);
		        	
            }    
        }); 
        
        ltitleId = new JLabel("ID");
        ltitleId.setFont(fontlabel);
        ltitleId.setBounds(70, 360, 80, 20);
        titleId = new JTextField();
        titleId.setBounds(70, 390, 80, 25);
        new NoLetters(titleId);
        new ValidLength(titleId, 5);
        
        format = new JTextField();
        format.setBounds(420, 360, 80, 25);
        format.setText("CD");
        format.setVisible(false);
        lastRegister = new JTextField();
        lastRegister.setBounds(200, 360, 80, 25);
        lastRegister.setVisible(false);

        lname = new JLabel("Name");
        lname.setFont(fontlabel);
        lname.setBounds(70, 430, 80, 20);
        name = new JTextField();
        name.setBounds(70, 470, 320, 25);
        new ValidLength(name, 50);
        
        //Button search
        btnSearch = new JButton("Search");
        btnSearch.setFont(fontButton);
        btnSearch.setBounds(420, 465, 90, 30);
        btnSearch.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                
                String filter = name.getText();
                String where = "";
                //Our filter must not be empty
                if(!"".equals(filter)){
                    where = "WHERE title.name LIKE '%" + filter + "%'";        //This means that if we do not type anything of the name, our WHERE will be empty and if something has been typed, our WHERE will contain the name
                }
                try{
                    
                    DefaultTableModel model = new DefaultTableModel();
                    
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    //'search' will be the query that we will send to the database to show the search result
                    String search = "SELECT title.titleId, title.name, title.releaseYear, title.genre, music.artist, music.productionCompany, music.format, title.stock, title.available, title.rentPrice "
                    		+ "FROM title "
                    		+ "INNER JOIN music ON title.titleId=music.musicId " + where +" AND format = 'CD'";
                    
                    System.out.println(search);
                    ps = conection.prepareStatement(search);
                    rs = ps.executeQuery();
                    //Adding the result to the rows of the table
                    ResultSetMetaData rsMD = rs.getMetaData();
                    int qttycol = rsMD.getColumnCount();
                    
                    model.addColumn("ID");
	                model.addColumn("Name");
	                model.addColumn("Release Year");
	                model.addColumn("Genre");
	                model.addColumn("Artist");
	                model.addColumn("Production Company");
	                model.addColumn("Format");
	                model.addColumn("Stock");
	                model.addColumn("Available");
	                model.addColumn("Rent Price");
                    
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
        
        lreleaseYear = new JLabel("Release Year");
        lreleaseYear.setFont(fontlabel);
        lreleaseYear.setBounds(560, 430, 140, 20);
        lreleaseYear.setVisible(false);
        releaseYear = new JTextField();
        releaseYear.setBounds(560, 470, 60, 25);
        releaseYear.setVisible(false);
        new NoLetters(releaseYear);
        new ValidLength(releaseYear, 4);
        
        lgenre = new JLabel("Genre");
        lgenre.setFont(fontlabel);
        lgenre.setBounds(670, 430, 80, 20);
        lgenre.setVisible(false);
        genre = new JTextField();
        genre.setBounds(670, 470, 150, 25);
        genre.setVisible(false);
        new NoNumbers(genre);
        new ValidLength(genre, 20);
        
        lartist = new JLabel("Artist");
        lartist.setFont(fontlabel);
        lartist.setBounds(70, 510, 80, 20);
        lartist.setVisible(false);
        artist = new JTextField();
        artist.setBounds(70, 550, 150, 25);
        artist.setVisible(false);
        new ValidLength(artist, 45);
        
        lproductionCompany = new JLabel("Production Company");
        lproductionCompany.setFont(fontlabel);
        lproductionCompany.setBounds(360, 510, 140, 20);
        lproductionCompany.setVisible(false);
        productionCompany = new JTextField();
        productionCompany.setBounds(360, 550, 220, 25);
        productionCompany.setVisible(false);
        new ValidLength(productionCompany, 25);
        
        lstock = new JLabel("Stock");
        lstock.setFont(fontlabel);
        lstock.setBounds(70, 590, 80, 20);
        lstock.setVisible(false);
        stock = new JTextField();
        stock.setBounds(70, 620, 150, 25);
        stock.setVisible(false);
        new NoLetters(stock);
        new ValidLength(stock, 3);
        
        lavailable = new JLabel("Available");
        lavailable.setFont(fontlabel);
        lavailable.setBounds(340, 590, 80, 20);
        lavailable.setVisible(false);
        available = new JTextField();
        available.setBounds(340, 620, 80, 25);
        available.setVisible(false);
        new NoLetters(available);
        new ValidLength(available, 3);
        
        lrentPrice = new JLabel("Rent Price");
        lrentPrice.setFont(fontlabel);
        lrentPrice.setBounds(510, 590, 80, 20);
        lrentPrice.setVisible(false);
        rentPrice = new JTextField();
        rentPrice.setBounds(510, 620, 80, 25);
        rentPrice.setVisible(false);
        new ValidLength(rentPrice, 5);
        
        typeId = new JTextField();
        typeId.setBounds(620, 620, 80, 25);
        typeId.setVisible(false);
        typeId.setText("MC");		//By default, the type Id will be MC
        type = new JTextField();
        type.setBounds(710, 620, 80, 25);
        type.setVisible(false);
        type.setText("Music");		//By default, the type will be Music
        
        //New button
        btnNew = new JButton("New");
        btnNew.setFont(fontButton);
        btnNew.setBounds(900, 400, 100, 30);
        btnNew.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0){
        		btnSaveNew.setVisible(true);
            	editScreen();
            	titleId.setEditable(false);
            	}
        	});
        
        //Update button
        btnUpdateMC = new JButton("Update");
        btnUpdateMC.setFont(fontButton);
        btnUpdateMC.setBounds(900, 485, 100, 30);
        btnUpdateMC.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	btnSaveUpdate.setVisible(true);
            	editScreen();
            }
        });
        
        //Delete button
        btnDeleteMC = new JButton("Delete");
        btnDeleteMC.setFont(fontButton);
        btnDeleteMC.setBounds(900, 575, 100, 30);
        btnDeleteMC.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                
            	ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                //Declaring our 'where' condition to be used as filter
                String filter = titleId.getText();
                String where = "";
                System.out.println("My filter is " + filter);
                //Our filter must not be empty
                if(!"".equals(filter)){     //
                    where = "WHERE titleId = '" + filter + "'";
                    System.out.println("My WHERE is: " + where);
                    try{
                    	//'deletecd' will be the query that we will send to the database to delete the cd
                    	String deletecd = "DELETE FROM music WHERE musicId = ?"; 
                        
                        PreparedStatement statementcd = conection.prepareStatement(deletecd);
                        statementcd.setString(1, titleId.getText());
                        System.out.println("my query is: " + deletecd);
                        statementcd.execute();
                    
	                    try {
	                    	//'deletetitle' will be the query that we will send to the database to delete the title
	                        String deletetitle = "DELETE FROM title WHERE titleId = ?"; 
	                        
	                        PreparedStatement statement = conection.prepareStatement(deletetitle);
	                        statement.setString(1, titleId.getText());
	                        System.out.println("my query is: " + deletetitle);
	                        statement.execute();
	                    	
	                    } catch(SQLException ex) {
	                    	JOptionPane.showMessageDialog(null, "Error deleting CD...!!");		//If something goes wrong when trying to delete the CD
	                    }
	                    conection.close();
                    
	                    JOptionPane.showMessageDialog(null, "CD Title deleted successfully");
	                    titleId.setText("");
	                    name.setText("");
	                    releaseYear.setText("");
	                    genre.setText("");
	                    typeId.setText("");
	                    artist.setText(""); 
	                    productionCompany.setText("");
	                    stock.setText("");
	                    available.setText("");
	                    rentPrice.setText("");
	                    lastRegister.setText("");
	                    
                    
                    } catch (Exception e){      //If something goes wrong
                    	JOptionPane.showMessageDialog(null, "Error deleting the CD Title!");
                    }    
                } else {       //The ID must not be empty
                    JOptionPane.showMessageDialog(null, "Error deleting the CD Title! Possible reassons: \n"
                    		+ "	� The ID cannot be empty");
                }
            }
        });
        
        //Save New MC button
        btnSaveNew = new JButton("Save");
        btnSaveNew.setFont(fontButton);
        btnSaveNew.setBounds(900, 442, 100, 30);
        btnSaveNew.setVisible(false);
        btnSaveNew.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	//Validation of required fields
            	if(name.getText().equals("") || releaseYear.getText().equals("") || artist.getText().equals("") || stock.getText().equals("") || available.getText().equals("") || rentPrice.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "One or more required fields are empty, please check: \n"
            				+ "	  � Name\n"
            				+ "	  � Release Year\n"
            				+ "	  � Artist Name\n"
            				+ "   � Quantity in stock\n"
            				+ "   � Quantity Available\n"
            				+ "	  � Rent Price\n");
            	}else {
            		//Validation of Quantity Available and Quantity in Stock
            		String txtStock = stock.getText(), txtAvailable = available.getText();
                    int qttyStock = Integer.parseInt(txtStock), qttyAvailable = Integer.parseInt(txtAvailable);
                    
                    if(qttyAvailable > qttyStock) {		//Quantity available must be bigger than quantity in stock
                    	System.out.println("Qtty Stock: " + qttyStock);
                    	System.out.println("Qtty Available " + qttyAvailable);
                    	JOptionPane.showMessageDialog(null, "The available quantity cannot be bigger than the stock quantity");
                    }else {
                    	//Validation of Current Year less than 2020
	                    String txtReleaseYear = releaseYear.getText();
	                    int yearNumber = Integer.parseInt(txtReleaseYear);
                    	
	                    if(yearNumber <= 2020) {		//Year number must be equal or less than current year (2020)
	                    	ConectionDB con = new ConectionDB();
	                        Connection conection = con.conect();
	                       	try{
	                       		//'addtitle' will be the query that we will send to the database to add the title
	                          	String addtitle = "INSERT INTO title (name, releaseYear, genre, typeId, type, stock, available, rentPrice) VALUES(?, ?, ?, ?, ?, ?, ?, ?)"; 
		                        System.out.println("Query insert new title: " + addtitle);
		                        PreparedStatement statement = conection.prepareStatement(addtitle);
		                        statement.setString(1, name.getText());
		                        statement.setString(2, releaseYear.getText());
		                        statement.setString(3, genre.getText());
		                        statement.setString(4, typeId.getText());
		                        statement.setString(5, type.getText());
		                        statement.setString(6, stock.getText());
		                        statement.setString(7, available.getText());
		                        statement.setString(8, rentPrice.getText());
		                           
		                        statement.executeUpdate();
		                        //Once the information of the table title is inserted, we will take the last title Id registered
		                        try{
		                         	PreparedStatement ps = null;
		                            ResultSet rs = null;
		                            //'search' will be the query that will be send to the database to find the last Title Id added
		                            String search = "SELECT titleId FROM title ORDER BY titleId DESC LIMIT 1";
		                            
		                            System.out.println(search);
		                            ps = conection.prepareStatement(search);
		                            rs = ps.executeQuery();
		                            //We will take the result of the query and this will be written on the JTextField 'lastRegister'
		                            while(rs.next()) {
		                            	lastRegister.setText(rs.getString("titleId"));
		                                res = rs.getString("titleId");
		                                System.out.println("The last register is: " + rs.getString("titleId"));
		                            }
		                            //Once we know the last Title Id and when the Id is already on the JTextField we will insert the information on the table music
		                            try {
		                              	//'addcd' will be the query that will be send to the database to add a the music details of the new Title
		                               	String addcd = "INSERT INTO music (artist, productionCompany, format, musicId) VALUES(?, ?, ?, ?)"; 
		                                System.out.println("Query insert new CD: " + addcd);
		                                PreparedStatement newstatement = conection.prepareStatement(addcd);
		                                newstatement.setString(1, artist.getText());
		                                newstatement.setString(2, productionCompany.getText());
		                                newstatement.setString(3, format.getText());
		                                newstatement.setString(4, lastRegister.getText());
		                                
		                                newstatement.executeUpdate();
		                                       
		                            } catch (SQLException ex) {
		                            	JOptionPane.showMessageDialog(null, "Error inserting new CD...!!");		//If something goes wrong when trying to insert a new CD
		                            }    
		                        } catch (SQLException ex){		//If something goes wrong when we try to find the Id of the last Title
		                           	JOptionPane.showMessageDialog(null, "Error finding last Register...!!");
		                        }
		                            	
		                        conection.close();
                                
		                        JOptionPane.showMessageDialog(null, "New CD Title inserted successfully");
		                        titleId.setText("");
		                        name.setText("");
		                        releaseYear.setText("");
		                        genre.setText("");
		                        artist.setText(""); 
		                        productionCompany.setText("");
		                        stock.setText("");
		                        available.setText("");
		                        rentPrice.setText("");
		                        lastRegister.setText(""); 
		                        titleId.setEditable(true);
		                    
	                       	} catch (Exception e){      //If something goes wrong
	                        	JOptionPane.showMessageDialog(null, "Error inserting a new CD Title!");
	                        }
	                    }else {		//If the Release Year is greater than current year (2020)
	                    	JOptionPane.showMessageDialog(null, "Release year cannot be after the current year");
	                    }   
                    }   
            	}	
            }    
        });
        
        //Save Update MC button
        btnSaveUpdate = new JButton("Save");
        btnSaveUpdate.setFont(fontButton);
        btnSaveUpdate.setBounds(900, 442, 100, 30);
        btnSaveUpdate.setVisible(false);
        btnSaveUpdate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	//Validation of required fields
            	if(name.getText().equals("") || releaseYear.getText().equals("") || artist.getText().equals("") || stock.getText().equals("") || available.getText().equals("") || rentPrice.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "One or more required fields are empty, please check: \n"
            				+ "	  � Name\n"
            				+ "	  � Release Year\n"
            				+ "	  � Artist Name\n"
            				+ "   � Quantity in stock\n"
            				+ "   � Quantity Available\n"
            				+ "	  � Rent Price\n");
            		
            	}else {
	            	//Validation of Quantity Available and Quantity in Stock
	        		String txtStock = stock.getText(), txtAvailable = available.getText();
	        		int qttyStock = Integer.parseInt(txtStock), qttyAvailable = Integer.parseInt(txtAvailable);

	        		if(qttyAvailable > qttyStock) {		//Quantity available must be bigger than quantity in stock
	                   	System.out.println("Qtty Stock: " + qttyStock);
	                   	System.out.println("Qtty Available " + qttyAvailable);
	                   	JOptionPane.showMessageDialog(null, "The available quantity cannot be bigger than the stock quantity");
	        		}else {
	        			//Validation of Current Year less than 2020
		                String txtReleaseYear = releaseYear.getText();
		                int yearNumber = Integer.parseInt(txtReleaseYear);
	                	
		                if(yearNumber <= 2020) {		//Year number must be equal or less than current year (2020)
		                	ConectionDB con = new ConectionDB();
		                    Connection conection = con.conect();
		                    //Declaring our 'where' condition to be used as filter
		                    String filter = titleId.getText();
		                    System.out.println("My title filter is: " + filter);
		                    String filtercd = titleId.getText();
		                    System.out.println("My cd filter is: " + filter);
		                    String where = "";
		                    //Our filter must not be empty
		                    if(!"".equals(filter)){
		                    	where = "WHERE titleId = '" + filter + "'";
		                        System.out.println("My where is: " + where);
		    	                try{
		    	                	//'updatetitle' will be the query that we will send to the database to update the title           
		    	                	String updatetitle = "UPDATE title SET name = ?, releaseYear = ?, genre = ?, stock = ?, available = ?, rentPrice = ? " + where; 
		    	                    System.out.println("My update title: " + updatetitle);
		    	                    PreparedStatement statement = conection.prepareStatement(updatetitle);
		    	                    statement.setString(1, name.getText());
		    	                    statement.setString(2, releaseYear.getText());
		    	                    statement.setString(3, genre.getText());
		    	                    statement.setString(4, stock.getText());
		    	                    statement.setString(5, available.getText());
		    	                    statement.setString(6, rentPrice.getText());
		    	                     
		                            statement.execute();
		                            //Our filter must not be empty
		                            if(!"".equals(filtercd)){
		                            	where = "WHERE musicId = '" + filter + "'";
		                                System.out.println("My where is: " + where);
		                                try{
		                                	//'updatecd' will be the query that we will send to the database to find the results
		                                	String updatecd = "UPDATE music SET artist = ?, productionCompany = ? " + where;
		        	                        System.out.println("My update CD: " + updatecd);
		        	                        PreparedStatement newstatement = conection.prepareStatement(updatecd);
		        	                        
		        	                        newstatement.setString(1, artist.getText());
		        	                        newstatement.setString(2, productionCompany.getText());
		        	                        
		        	                        newstatement.execute();
		                                }catch(SQLException ex) {
		                                  	JOptionPane.showMessageDialog(null, "Error updating CD...!!");		//If something goes wrong when trying to update a CD
		                                }  
		                            }
		                             
		                            conection.close();
		    	                        
		    	                    JOptionPane.showMessageDialog(null, "CD Title updated successfully");
		    	                    titleId.setText("");
		    	                    name.setText("");
		    	                    releaseYear.setText("");
		    	                    genre.setText("");
		    	                    artist.setText(""); 
		    	                    productionCompany.setText("");
		    	                    stock.setText("");
		    	                    available.setText("");
		    	                    rentPrice.setText("");
		    	                    lastRegister.setText("");
		    	                    
		    	                } catch (Exception e){
		    	                	JOptionPane.showMessageDialog(null, "Error updating CD Title!");		//If something goes wrong when trying to update a Title
		    	                }        
		                    }else{       //The ID must not be empty
			                    JOptionPane.showMessageDialog(null, "Error updating CD! \n"
			                    		+ "	� The ID cannot be empty");
		                    }    
		                }else {		//If the Release Year is greater than current year (2020)
		                   	JOptionPane.showMessageDialog(null, "Release year cannot be after the current year");
		                }   
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
            	titleId.setEditable(true);
            }
        });
        
        p.add(ltitle);
        p.add(lastRegister);
        p.add(format);
        p.add(btnRefresh);
        p.add(ltitleId);
        p.add(titleId);
        p.add(lname);
        p.add(name);
        p.add(btnSearch);
        p.add(lreleaseYear);
        p.add(releaseYear);
        p.add(lgenre);
        p.add(genre);
        p.add(lartist);
        p.add(artist);
        p.add(lproductionCompany);
        p.add(productionCompany);
        p.add(lstock);
        p.add(stock);
        p.add(lavailable);
        p.add(available);
        p.add(lrentPrice);
        p.add(rentPrice);
        p.add(typeId);
        p.add(type);
        p.add(btnNew);
        p.add(btnDeleteMC);
        p.add(btnUpdateMC);
        p.add(btnSaveNew);
        p.add(btnSaveUpdate);
        p.add(btnCancel);
        
        this.validate();
        this.repaint();
	}
	//This will modify the window to be able to see all the required information to update the membership card
	public void editScreen() {
		lreleaseYear.setVisible(true);
		releaseYear.setVisible(true);
		lgenre.setVisible(true);
		genre.setVisible(true);
		lartist.setVisible(true);
		artist.setVisible(true);
		lproductionCompany.setVisible(true);
		productionCompany.setVisible(true);
		lstock.setVisible(true);
		stock.setVisible(true);
		lavailable.setVisible(true);
		available.setVisible(true);
		lrentPrice.setVisible(true);
		rentPrice.setVisible(true);
		
		btnNew.setVisible(false);
		btnUpdateMC.setVisible(false);
		btnDeleteMC.setVisible(false);
		
		btnCancel.setVisible(true);
	}
	//This method will return the components of the window to their original state
	public void normalScreen() {
		titleId.setText("");
		name.setText("");
		lreleaseYear.setVisible(false);
		releaseYear.setVisible(false);
		releaseYear.setText("");
		lgenre.setVisible(false);
		genre.setVisible(false);
		genre.setText("");
		lartist.setVisible(false);
		artist.setVisible(false);
		artist.setText("");
		lproductionCompany.setVisible(false);
		productionCompany.setVisible(false);
		productionCompany.setText("");
		lstock.setVisible(false);
		stock.setVisible(false);
		stock.setText("");
		lavailable.setVisible(false);
		available.setVisible(false);
		available.setText("");
		lrentPrice.setVisible(false);
		rentPrice.setVisible(false);
		rentPrice.setText("");
		
		btnNew.setVisible(true);
		btnUpdateMC.setVisible(true);
		btnDeleteMC.setVisible(true);
		
		btnCancel.setVisible(false);
	}
	//This wil refresh our table after doing any changes
	public void refresh() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        	try{
        		model = new DefaultTableModel();
                    
                PreparedStatement ps = null;
                ResultSet rs = null;
                //'refresh' will be the query that we will send to the database to show all the titles that are of the CD type
                String refresh = "SELECT title.titleId, title.name, title.releaseYear, title.genre, music.artist, music.productionCompany, music.format, title.stock, title.available, title.rentPrice  "
                		+ "FROM title  INNER JOIN music "
                		+ "ON title.titleId=music.musicId "
                		+ "WHERE music.format = 'CD';";
                System.out.println(refresh);
                //Adding the result to the rows of the table
                ps = conection.prepareStatement(refresh);
                rs = ps.executeQuery();
                    
                ResultSetMetaData rsMD = rs.getMetaData();
                int qttycol = rsMD.getColumnCount();
                    
                model.addColumn("ID");
                model.addColumn("Name");
                model.addColumn("Release Year");
                model.addColumn("Genre");
                model.addColumn("Artist");
                model.addColumn("Production Company");
                model.addColumn("Format");
                model.addColumn("Stock");
                model.addColumn("Available");
                model.addColumn("Rent Price");
                    
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
