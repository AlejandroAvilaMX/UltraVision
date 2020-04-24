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

public class VL extends JFrame implements ActionListener {
	private JLabel ltitle, ltitleId, lname, lreleaseYear, lgenre, lproductionCompany, ldirector, lcountry, lduration, llanguage, lformat, lstock, lavailable, lrentPrice;
    private JTextField titleId, name, lastRegister, releaseYear, genre, productionCompany, director, country, duration, language, format, stock, available, rentPrice, typeId, type, movieId;
    private String res;
    private JButton btnSearch, btnNew, btnUpdateVL, btnDeleteVL, btnSaveNew, btnSaveUpdate, btnCancel;
    
	public VL() {
		this.setVisible(true);
        this.setSize(1100, 730);     //Size of the window
        this.setTitle("DVD and Blue-Ray Titles");       //Title of the window
        
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
        
        ltitle = new JLabel("DVD and Blue-Ray Titles");
        ltitle.setFont(fonttitle);
        ltitle.setBounds(350, 50, 430, 40);
        
        //Button refresh
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(fontButton);
        btnRefresh.setBounds(40, 70, 110, 30);
        btnRefresh.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
     
		        ConectionDB con = new ConectionDB();
		        Connection conection = con.conect();
		        	try{
		        		DefaultTableModel model = new DefaultTableModel();
		                    
		                PreparedStatement ps = null;
		                ResultSet rs = null;
		                    
		                String refresh = "SELECT title.titleId, title.name, title.releaseYear, title.genre, movie.productionCompany, movie.director, movie.country, movie.duration, movie.language, movie.format, movie.stock, movie.available, movie.rentPrice "
		                		+ "FROM title "
		                		+ "INNER JOIN movie ON title.titleId=movie.movieId; ";
		                //Adding the result to the rows of the table
		                ps = conection.prepareStatement(refresh);
		                rs = ps.executeQuery();
		                    
		                ResultSetMetaData rsMD = rs.getMetaData();
		                int qttycol = rsMD.getColumnCount();
		                    
		                model.addColumn("ID");
		                model.addColumn("Name");
		                model.addColumn("Release Year");
		                model.addColumn("Genre");
		                model.addColumn("Production Company");
		                model.addColumn("Director");
		                model.addColumn("Country");
		                model.addColumn("Duration");
		                model.addColumn("Language");
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
        
        ltitleId = new JLabel("ID");
        ltitleId.setFont(fontlabel);
        ltitleId.setBounds(70, 360, 80, 20);
        titleId = new JTextField();
        titleId.setBounds(70, 390, 80, 25);
        new NoLetters(titleId);
        new ValidLength(titleId, 5);
        
        format = new JTextField();
        format.setBounds(420, 360, 80, 25);
        format.setText("DVD");
        format.setVisible(false);
        lastRegister = new JTextField();
        lastRegister.setBounds(200, 360, 80, 25);
        lastRegister.setVisible(false);
        
        lname = new JLabel("Name");
        lname.setFont(fontlabel);
        lname.setBounds(200, 360, 80, 20);
        name = new JTextField();
        name.setBounds(200, 390, 320, 25);
        new ValidLength(name, 50);
        
        //Search button
        btnSearch = new JButton("Search");
        btnSearch.setFont(fontButton);
        btnSearch.setBounds(540, 385, 90, 30);
        btnSearch.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                
                String filter = name.getText();
                String where = "";
                
                if(!"".equals(filter)){
                    where = "WHERE title.name LIKE '%" + filter + "%'";        //This means that if we do not type anything of the name, our WHERE will be empty and if something has been typed, our WHERE will contain the name
                }
                try{
                    
                    DefaultTableModel model = new DefaultTableModel();
                    
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    
                    String search = "SELECT title.titleId, title.name, title.releaseYear, title.genre, movie.productionCompany, movie.director, movie.country, movie.duration, movie.language, movie.format, movie.stock, movie.available, movie.rentPrice "
                    		+ "FROM title "
                    		+ "INNER JOIN movie ON title.titleId=movie.movieId " + where;
                    
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
	                model.addColumn("Production Company");
	                model.addColumn("Director");
	                model.addColumn("Country");
	                model.addColumn("Duration");
	                model.addColumn("Language");
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
        lreleaseYear.setBounds(70, 430, 120, 20);
        lreleaseYear.setVisible(false);
        releaseYear = new JTextField();
        releaseYear.setBounds(70, 470, 80, 25);
        releaseYear.setVisible(false);
        new NoLetters(releaseYear);
        new ValidLength(releaseYear, 4);
        
        lgenre = new JLabel("Genre");
        lgenre.setFont(fontlabel);
        lgenre.setBounds(230, 430, 510, 20);
        lgenre.setVisible(false);
        genre = new JTextField();
        genre.setBounds(230, 470, 120, 25);
        genre.setVisible(false);
        new NoNumbers(genre);
        new ValidLength(genre, 20);
        
        lproductionCompany = new JLabel("Production Company");
        lproductionCompany.setFont(fontlabel);
        lproductionCompany.setBounds(430, 430, 160, 20);
        lproductionCompany.setVisible(false);
        productionCompany = new JTextField();
        productionCompany.setBounds(430, 470, 150, 25);
        productionCompany.setVisible(false);
        new ValidLength(productionCompany, 25);
        
        ldirector = new JLabel("Director");
        ldirector.setFont(fontlabel);
        ldirector.setBounds(70, 510, 120, 20);
        ldirector.setVisible(false);
        director = new JTextField();
        director.setBounds(70, 550, 210, 25);
        director.setVisible(false);
        new NoNumbers(director);
        new ValidLength(director, 40);
        
        lcountry = new JLabel("Country");
        lcountry.setFont(fontlabel);
        lcountry.setBounds(310, 510, 120, 20);
        lcountry.setVisible(false);
        country = new JTextField();
        country.setBounds(310, 550, 220, 25);
        country.setVisible(false);
        new NoNumbers(country);
        new ValidLength(country, 20);
        
        lduration = new JLabel("Duration");
        lduration.setFont(fontlabel);
        lduration.setBounds(550, 510, 120, 20);
        lduration.setVisible(false);
        duration = new JTextField();
        duration.setBounds(550, 550, 100, 25);
        duration.setVisible(false);
        new ValidLength(duration, 20);
        
        llanguage = new JLabel("Language");
        llanguage.setFont(fontlabel);
        llanguage.setBounds(680, 510, 120, 20);
        llanguage.setVisible(false);
        language = new JTextField();
        language.setBounds(680, 550, 100, 25);
        language.setVisible(false);
        new NoNumbers(language);
        new ValidLength(language, 30);
        
        lformat = new JLabel("Format");
        lformat.setFont(fontlabel);
        lformat.setBounds(70, 590, 120, 20);
        lformat.setVisible(false);
        format = new JTextField();
        format.setBounds(70, 630, 100, 25);
        format.setVisible(false);
        new NoNumbers(format);
        new ValidLength(format, 10);
        
        lstock = new JLabel("Stock");
        lstock.setFont(fontlabel);
        lstock.setBounds(220, 590, 120, 20);
        lstock.setVisible(false);
        stock = new JTextField();
        stock.setBounds(220, 630, 100, 25);
        stock.setVisible(false);
        new NoLetters(stock);
        new ValidLength(stock, 3);
        
        
        lavailable = new JLabel("Available");
        lavailable.setFont(fontlabel);
        lavailable.setBounds(360, 590, 120, 20);
        lavailable.setVisible(false);
        available = new JTextField();
        available.setBounds(360, 630, 100, 25);
        available.setVisible(false);
        new NoLetters(available);
        new ValidLength(available, 3);
        
        lrentPrice = new JLabel("Rent Price");
        lrentPrice.setFont(fontlabel);
        lrentPrice.setBounds(500, 590, 120, 20);
        lrentPrice.setVisible(false);
        rentPrice = new JTextField();
        rentPrice.setBounds(500, 630, 100, 25);
        rentPrice.setVisible(false);
        new ValidLength(rentPrice, 5);
        
        typeId = new JTextField();
        typeId.setBounds(510, 620, 80, 25);
        typeId.setVisible(false);
        typeId.setText("VL");
        type = new JTextField();
        type.setBounds(510, 620, 80, 25);
        type.setVisible(false);
        type.setText("Movie");
        
        //New button
        btnNew = new JButton("New");
        btnNew.setFont(fontButton);
        btnNew.setBounds(860, 400, 100, 30);
        btnNew.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	btnSaveNew.setVisible(true);
            	editScreen();
            }
        });
        
        //Update button
        btnUpdateVL = new JButton("Update");
        btnUpdateVL.setFont(fontButton);
        btnUpdateVL.setBounds(860, 485, 100, 30);
        btnUpdateVL.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	btnSaveUpdate.setVisible(true);
            	editScreen();
            }
        });
        
        //Delete button
        btnDeleteVL = new JButton("Delete");
        btnDeleteVL.setFont(fontButton);
        btnDeleteVL.setBounds(860, 575, 100, 30);
        btnDeleteVL.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){

            	ConectionDB con = new ConectionDB();
                Connection conection = con.conect();                
                
                String filter = titleId.getText();
                String where = "";
                System.out.println("My filter is " + filter);
                if(!"".equals(filter)){     //
                    where = "WHERE titleId = '" + filter + "'";
                    System.out.println("My WHERE is: " + where);
                    try{
                                       
                    //JOptionPane.showMessageDialog(null, "Connected successfully");
                                        
                    	String deletedvd = "DELETE FROM movie WHERE movieId = ?"; 
                        
                        PreparedStatement statementcd = conection.prepareStatement(deletedvd);
                        statementcd.setString(1, titleId.getText());
                        System.out.println("my query is: " + deletedvd);
                        statementcd.execute();
                    
                    try {
                    	
                        String deletetitle = "DELETE FROM title WHERE titleId = ?"; 
                        
                        PreparedStatement statement = conection.prepareStatement(deletetitle);
                        statement.setString(1, titleId.getText());
                        System.out.println("my query is: " + deletetitle);
                        statement.execute();
                    	
                    } catch(SQLException ex) {
                    	JOptionPane.showMessageDialog(null, "Error deleting the Movie...!!");
                    }
                       
                    conection.close();
                    
                    JOptionPane.showMessageDialog(null, "Movie Title deleted successfully");
                    titleId.setText("");
                    name.setText("");
                    releaseYear.setText("");
                    genre.setText("");
                    productionCompany.setText("");
                    director.setText("");
                    country.setText("");
                    duration.setText("");
                    language.setText("");
                    format.setText("");
                    stock.setText("");
                    available.setText("");
                    rentPrice.setText("");
                    lastRegister.setText("");
                    
                    } catch (Exception e){      //If something goes wrong
                    	JOptionPane.showMessageDialog(null, "Error deleting the Movie Title!");
                    	}
                    }
                else{       //The ID must be a valid ID number
                    JOptionPane.showMessageDialog(null, "Error deleting the Movie Title! Possible reassons: \n"
                            + "	� The ID cannot be empty");
                }
            }
        });
        
        //Save New VL button
        btnSaveNew = new JButton("Save");
        btnSaveNew.setFont(fontButton);
        btnSaveNew.setBounds(860, 442, 100, 30);
        btnSaveNew.setVisible(false);
        btnSaveNew.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	
            	if(name.getText().equals("") || releaseYear.getText().equals("") || stock.getText().equals("") || available.getText().equals("") || rentPrice.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "One or more required fields are empty, please check: \n"
            				+ "	  � Name\n"
            				+ "	  � Release Year\n"
            				+ "   � Quantity in stock\n"
            				+ "   � Quantity Available\n"
            				+ "	  � Rent Price\n");
            	}else {
            		//Validation of Quantity Available and Quantity in Stock
            		String txtStock = stock.getText(), txtAvailable = available.getText();
                    int qttyStock = Integer.parseInt(txtStock), qttyAvailable = Integer.parseInt(txtAvailable);
                    
                    if(qttyAvailable > qttyStock) {
                    	System.out.println("Qtty Stock: " + qttyStock);
                    	System.out.println("Qtty Available " + qttyAvailable);
                    	JOptionPane.showMessageDialog(null, "The available quantity cannot be bigger than the stock quantity");
                    }else {
                    	//Validation of Current Year less than 2020
                    	String txtReleaseYear = releaseYear.getText();
                    	int yearNumber = Integer.parseInt(txtReleaseYear);
                    	
                    	if(yearNumber <= 2020) {
                    		ConectionDB con = new ConectionDB();
                            Connection conection = con.conect();
                            try{
                                                    
                                String addtitle = "INSERT INTO title (name, releaseYear, genre, typeId, type) VALUES(?, ?, ?, ?, ?)"; 
                                System.out.println("Query insert new Title: " + addtitle);
                                PreparedStatement statement = conection.prepareStatement(addtitle);
                                statement.setString(1, name.getText());
                                statement.setString(2, releaseYear.getText());
                                statement.setString(3, genre.getText());
                                statement.setString(4, typeId.getText());
                                statement.setString(5, type.getText());
                                
                                statement.executeUpdate();
                                
                                try{
                                    
                                    PreparedStatement ps = null;
                                    ResultSet rs = null;
                                    
                                    String search = "SELECT titleId FROM title ORDER BY titleId DESC LIMIT 1";
                                    
                                    System.out.println(search);
                                    ps = conection.prepareStatement(search);
                                    rs = ps.executeQuery();
                                    
                                    while(rs.next()) {
                                    	lastRegister.setText(rs.getString("titleId"));
                                    	res = rs.getString("titleId");
                                    	System.out.println("The last register is: " + rs.getString("titleId"));
                                    }
                                    
                                    try {
                                    	String addmovie = "INSERT INTO movie (productionCompany, director, country, duration, language, format, stock, available, rentPrice, movieId) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
                                        System.out.println("Query insert new DVD: " + addmovie);
                                        PreparedStatement newstatement = conection.prepareStatement(addmovie);
                                        newstatement.setString(1, productionCompany.getText());
                                        newstatement.setString(2, director.getText());
                                        newstatement.setString(3, country.getText());
                                        newstatement.setString(4, duration.getText());
                                        newstatement.setString(5, language.getText());
                                        newstatement.setString(6, format.getText());
                                        newstatement.setString(7, stock.getText());
                                        newstatement.setString(8, available.getText());
                                        newstatement.setString(9, rentPrice.getText());
                                        newstatement.setString(10, lastRegister.getText());
                                        
                                        newstatement.executeUpdate();
                                    	
                                    } catch (SQLException ex) {
                                    	JOptionPane.showMessageDialog(null, "Error inserting new Movie...!!");
                                    }
                                    
                                } catch (SQLException ex){
                                    JOptionPane.showMessageDialog(null, "Error finding last Register...!!");
                                }
                                                     
                                conection.close();
                                
                                JOptionPane.showMessageDialog(null, "New Movie inserted successfully");
                                titleId.setText("");
                                name.setText("");
                                releaseYear.setText("");
                                genre.setText("");
                                productionCompany.setText("");
                                director.setText("");
                                country.setText("");
                                duration.setText("");
                                language.setText("");
                                format.setText("");
                                stock.setText("");
                                available.setText("");
                                rentPrice.setText("");
                                lastRegister.setText("");
                                
                            } catch (Exception e){      //If something goes wrong
                                JOptionPane.showMessageDialog(null, "Error inserting a new Movie Title!");
                                }
                    	}else {
                    		JOptionPane.showMessageDialog(null, "Release year cannot be after the current year");
                    		}
                    	}
                    }
            	}
        });
        
        //Save Update VL button
        btnSaveUpdate = new JButton("Save");
        btnSaveUpdate.setFont(fontButton);
        btnSaveUpdate.setBounds(860, 442, 100, 30);
        btnSaveUpdate.setVisible(false);
        btnSaveUpdate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	
            	if(name.getText().equals("") || releaseYear.getText().equals("") || stock.getText().equals("") || available.getText().equals("") || rentPrice.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "One or more required fields are empty, please check: \n"
            				+ "	  � Name\n"
            				+ "	  � Release Year\n"
            				+ "	  � Artist Name\n"
            				+ "   � Quantity in stock\n"
            				+ "   � Quantity Available\n"
            				+ "	  � Rent Price\n");
            	}else {
            		ConectionDB con = new ConectionDB();
                    Connection conection = con.conect();
                    
                    String filter = titleId.getText();
                    System.out.println("My title filter is: " + filter);
                    String filterdvd = titleId.getText();
                    System.out.println("My dvd filter is: " + filter);
                    String where = "";
                    
                    if(!"".equals(filter)){
                        where = "WHERE titleId = '" + filter + "'";
                        System.out.println("My where is: " + where);
	                    try{
	                                            
	                        String updatetitle = "UPDATE title SET name = ?, releaseYear = ?, genre = ? " + where; 
	                        System.out.println("My update title: " + updatetitle);
	                        PreparedStatement statement = conection.prepareStatement(updatetitle);
	                        statement.setString(1, name.getText());
	                        statement.setString(2, releaseYear.getText());
	                        statement.setString(3, genre.getText());
	                        
                            statement.execute();
	                        
                            if(!"".equals(filterdvd)){
                                where = "WHERE movieId = '" + filter + "'";
                                System.out.println("My where is: " + where);
                                try{
                                	String updatedvd = "UPDATE movie SET productionCompany = ?, director = ?, country = ?, duration = ?, language = ?, format = ?, stock = ?, available = ?, rentPrice = ? " + where;
    	                            System.out.println("My update DVD: " + updatedvd);
    	                            PreparedStatement newstatement = conection.prepareStatement(updatedvd);

    	                            newstatement.setString(1, productionCompany.getText());
                                    newstatement.setString(2, director.getText());
                                    newstatement.setString(3, country.getText());
                                    newstatement.setString(4, duration.getText());
                                    newstatement.setString(5, language.getText());
                                    newstatement.setString(6, format.getText());
                                    newstatement.setString(7, stock.getText());
                                    newstatement.setString(8, available.getText());
                                    newstatement.setString(9, rentPrice.getText());
    	                            
    	                            newstatement.execute();
                                }catch(SQLException ex) {
                                	JOptionPane.showMessageDialog(null, "Error updating Movie...!!");
                            	}
                                
                            }
                                               
	                        conection.close();
	                        
	                        JOptionPane.showMessageDialog(null, "Title updated successfully");
	                        titleId.setText("");
	                        name.setText("");
	                        releaseYear.setText("");
	                        genre.setText("");
	                        productionCompany.setText("");
	                        director.setText("");
	                        country.setText("");
	                        duration.setText("");
	                        language.setText("");
	                        format.setText("");
	                        stock.setText("");
	                        available.setText("");
	                        rentPrice.setText("");
	                        lastRegister.setText("");
	                        
	                    } catch (Exception e){      //If something goes wrong
	                        JOptionPane.showMessageDialog(null, "Error updating Movie Title!");
	                        }
	                    }
                    else{       //The ID must have a valid ID number
                    	JOptionPane.showMessageDialog(null, "Error updating Movie! \n"
                    			+ "	� The ID cannot be empty");
                    	}
                    }
            	}
            });
        
        //Cancel button
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(fontButton);
        btnCancel.setBounds(860, 530, 100, 30);
        btnCancel.setVisible(false);
        btnCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	normalScreen();
            	btnSaveNew.setVisible(false);
            	btnSaveUpdate.setVisible(false);
            }
        });
        
        p.add(ltitle);
        p.add(lastRegister);
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
        p.add(lproductionCompany);
        p.add(productionCompany);
        p.add(ldirector);
        p.add(director);
        p.add(lcountry);
        p.add(country);
        p.add(lduration);
        p.add(duration);
        p.add(llanguage);
        p.add(language);
        p.add(lformat);
        p.add(format);
        p.add(lstock);
        p.add(stock);
        p.add(lavailable);
        p.add(available);
        p.add(lrentPrice);
        p.add(rentPrice);
        p.add(typeId);
        p.add(type);
        p.add(btnNew);
        p.add(btnUpdateVL);
        p.add(btnDeleteVL);
        p.add(btnSaveNew);
        p.add(btnSaveUpdate);
        p.add(btnCancel);
        
        this.validate();
        this.repaint();
        }
	
	public void editScreen() {
		lreleaseYear.setVisible(true);
		releaseYear.setVisible(true);
		lgenre.setVisible(true);
		genre.setVisible(true);
		lproductionCompany.setVisible(true);
		productionCompany.setVisible(true);
		ldirector.setVisible(true);
		director.setVisible(true);
		lcountry.setVisible(true);
		country.setVisible(true);
		lduration.setVisible(true);
		duration.setVisible(true);
		llanguage.setVisible(true);
		language.setVisible(true);
		lformat.setVisible(true);
		format.setVisible(true);
		lstock.setVisible(true);
		stock.setVisible(true);
		lavailable.setVisible(true);
		available.setVisible(true);
		lrentPrice.setVisible(true);
		rentPrice.setVisible(true);
		
		btnNew.setVisible(false);
		btnUpdateVL.setVisible(false);
		btnDeleteVL.setVisible(false);
		
		btnCancel.setVisible(true);
	}
	
	public void normalScreen() {
		lreleaseYear.setVisible(false);
		releaseYear.setVisible(false);
		lgenre.setVisible(false);
		genre.setVisible(false);
		lproductionCompany.setVisible(false);
		productionCompany.setVisible(false);
		ldirector.setVisible(false);
		director.setVisible(false);
		lcountry.setVisible(false);
		country.setVisible(false);
		lduration.setVisible(false);
		duration.setVisible(false);
		llanguage.setVisible(false);
		language.setVisible(false);
		lformat.setVisible(false);
		format.setVisible(false);
		lstock.setVisible(false);
		stock.setVisible(false);
		lavailable.setVisible(false);
		available.setVisible(false);
		lrentPrice.setVisible(false);
		rentPrice.setVisible(false);
		
		btnNew.setVisible(true);
		btnUpdateVL.setVisible(true);
		btnDeleteVL.setVisible(true);
		
		btnCancel.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if(ac.equals("exit")){
            System.out.println("Exit the program");
            System.exit(0);
        }
        else if(ac.equals("customers")){
            System.out.println("Going to Customers");
            new Customers();
            dispose();
        }
        else if(ac.equals("MemCard")){
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
