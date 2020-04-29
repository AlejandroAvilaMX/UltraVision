package rent;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

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

public class Rent extends JFrame implements ActionListener{
	private JLabel ltitle, lname, lID, ltitleId, lCDtitle, lDVDtitle, lMovietitle, lSerietitle;
	private JTextField name, ID, titleId, CDtitle, DVDtitle, Movietitle, Serietitle, rentedDay, returnDay, stock, newAvailable, available, loyaltyPoints, newloyaltyPoints, freeRent, newfreeRent;
	private String res, resn, stravailable, strloyaltyPoints, qttyLoyaltyPoints, strfreeRent, qttyfreeRent;
	private int qttyStock, qttyAvailable, intloyaltyPoints, intfreeRent;
	boolean rentIsFree = false;
	private JButton btnRefresh, btnNewCDRent, btnNewDVDRent, btnNewMovieRent, btnNewSerieRent, btnSearchName, btnSearchCD, btnSearchDVD, btnSearchMovie, btnSearchSerie, btnSaveRent, btnCancel, btnFreeRent, btnSaveFreeRent;

	public Rent() {
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
        ltitle = new JLabel("Rent");
        ltitle.setFont(fonttitle);
        ltitle.setBounds(500, 50, 230, 20);
        //Table with the information of all the rented Titles
        ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        
        try{
        	DefaultTableModel model = new DefaultTableModel();
                   
        	PreparedStatement ps = null;
            ResultSet rs = null;
            //'refresh' will be the query that we will send to the database to show all the Rented Titles
            String refresh = "SELECT rent.rentId, CONCAT(customer.name, ' ', customer.surname) AS customerName, title.name AS RentedTitle, rent.rentDay, rent.returnDay, rent.returned, rent.latenessDeduction, rent.damageDeduction "
            		+ "FROM rent INNER JOIN customer ON rent.custId=customer.custId "
            		+ "INNER JOIN title ON rent.titleId=title.titleId;";
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
            model.addColumn("Return Day");
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
            JTable table = new JTable(model);
                
            JScrollPane scroll= new JScrollPane(table);
            table.setBounds(40,120,1000,200);
            scroll.setBounds(40,120,1000,200);
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
            	
                ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                try{
                    
                    DefaultTableModel model = new DefaultTableModel();
                    
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    //'refresh' will be the query that we will send to the database to show all the Customers
                    String refresh = "SELECT rent.rentId, CONCAT(customer.name, ' ', customer.surname) AS customerName, title.name AS RentedTitle, rent.rentDay, rent.returnDay, rent.returned, rent.latenessDeduction, rent.damageDeduction "
                    		+ "FROM rent "
                    		+ "INNER JOIN customer ON rent.custId=customer.custId "
                    		+ "INNER JOIN title ON rent.titleId=title.titleId;";
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
                    model.addColumn("Return Day");
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
        
        //New Rent CD Music button
        btnNewCDRent = new JButton("Rent CD Music");
        btnNewCDRent.setFont(fontButton);
        btnNewCDRent.setBounds(40, 360, 140, 30);
        btnNewCDRent.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	rentScreen();
            	lCDtitle.setVisible(true);
            	CDtitle.setVisible(true);
            	btnSearchCD.setVisible(true);
            	
            	//btnSaveUpdate.setVisible(false);
            }
        });
                
        //New Rent DVD Video Music button
        btnNewDVDRent = new JButton("Rent DVD Music");
        btnNewDVDRent.setFont(fontButton);
        btnNewDVDRent.setBounds(320, 360, 140, 30);
        btnNewDVDRent.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	rentScreen();
            	lDVDtitle.setVisible(true);
            	DVDtitle.setVisible(true);
            	btnSearchDVD.setVisible(true);
            	//btnSaveNew.setVisible(false);
            	//btnSaveUpdate.setVisible(false);
            }
        });
        
        //New Rent Movie button
        btnNewMovieRent = new JButton("Rent Movie");
        btnNewMovieRent.setFont(fontButton);
        btnNewMovieRent.setBounds(610, 360, 140, 30);
        btnNewMovieRent.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	rentScreen();
            	lMovietitle.setVisible(true);
            	Movietitle.setVisible(true);
            	btnSearchMovie.setVisible(true);
            	//btnSaveNew.setVisible(false);
            	//btnSaveUpdate.setVisible(false);
            }
        });
        
        //New Rent TV Box Serie button
        btnNewSerieRent = new JButton("Rent TV Serie");
        btnNewSerieRent.setFont(fontButton);
        btnNewSerieRent.setBounds(895, 360, 140, 30);
        btnNewSerieRent.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	rentScreen();
            	lSerietitle.setVisible(true);
            	Serietitle.setVisible(true);
            	btnSearchSerie.setVisible(true);
            	//btnSaveNew.setVisible(false);
            	//btnSaveUpdate.setVisible(false);
            }
        });
        
        lID = new JLabel("Customer ID");
        lID.setFont(fontlabel);
        lID.setBounds(70, 340, 80, 20);
        lID.setVisible(false);
        ID = new JTextField();
        ID.setBounds(70, 370, 80, 25);
        ID.setVisible(false);
        new NoLetters(ID);
        new ValidLength(ID, 5);
        
        lname = new JLabel("First Name");
        lname.setFont(fontlabel);
        lname.setBounds(200, 340, 80, 20);
        lname.setVisible(false);
        name = new JTextField();
        name.setBounds(200, 370, 220, 25);
        name.setVisible(false);
        new ValidLength(name, 50);
        new NoNumbers(name);
        //Button Search
        btnSearchName = new JButton("Search");
        btnSearchName.setFont(fontButton);
        btnSearchName.setBounds(440, 365, 90, 30);
        btnSearchName.setVisible(false);
        btnSearchName.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                
                String filter = name.getText();
                String where = "";
                //Our filter must not be empty
                if(!"".equals(filter)){
                    where = "WHERE name LIKE '%" + filter + "%'";        //This means that if we do not type anything of the name, our WHERE will be empty and if something has been typed, our WHERE will contain the name
                }
                try{
                    
                    DefaultTableModel model = new DefaultTableModel();
                    
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    //'search' will be the query that we will send to the database to find the results
                    String search = "SELECT customer.custId, customer.name, customer.surname, membershipCard.loyaltyPoints, membershipCard.freeRent "
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
                    model.addColumn("Loyalty Points");
                    model.addColumn("Free Rent");

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
        
        ltitleId = new JLabel("Title ID");
        ltitleId.setFont(fontlabel);
        ltitleId.setBounds(70, 430, 80, 20);
        ltitleId.setVisible(false);
        titleId = new JTextField();
        titleId.setBounds(70, 470, 80, 25);
        titleId.setVisible(false);
        new NoLetters(titleId);
        new ValidLength(titleId, 5);
        
        lCDtitle = new JLabel("Name of the CD Music");
        lCDtitle.setFont(fontlabel);
        lCDtitle.setBounds(200, 430, 150, 20);
        lCDtitle.setVisible(false);
        CDtitle = new JTextField();
        CDtitle.setBounds(200, 470, 320, 25);
        CDtitle.setVisible(false);
        new ValidLength(name, 50);
        
        //Button search
        btnSearchCD = new JButton("Search");
        btnSearchCD.setFont(fontButton);
        btnSearchCD.setBounds(540, 465, 90, 30);
        btnSearchCD.setVisible(false);
        btnSearchCD.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                
                String filter = CDtitle.getText();
                System.out.println("My filter is " + filter);
                String where = "";
                //Our filter must not be empty
                if(!"".equals(filter)){
                    where = "WHERE title.name LIKE '%" + filter + "%'";        //This means that if we do not type anything of the name, our WHERE will be empty and if something has been typed, our WHERE will contain the name
                    System.out.println("My where SQL is " + where);
                }
                try{
                    
                    DefaultTableModel model = new DefaultTableModel();
                    
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    //'search' will be the query that we will send to the database to show the search result
                    String search = "SELECT title.titleId, title.name, title.releaseYear, title.genre, music.artist, title.stock, title.available, title.rentPrice "
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
        
        lDVDtitle = new JLabel("Name of the DVD Music");
        lDVDtitle.setFont(fontlabel);
        lDVDtitle.setBounds(200, 430, 180, 20);
        lDVDtitle.setVisible(false);
        DVDtitle = new JTextField();
        DVDtitle.setBounds(200, 470, 320, 25);
        DVDtitle.setVisible(false);
        new ValidLength(name, 50);
        
        //Button DVD search
        btnSearchDVD = new JButton("Search");
        btnSearchDVD.setFont(fontButton);
        btnSearchDVD.setBounds(540, 465, 90, 30);
        btnSearchDVD.setVisible(false);
        btnSearchDVD.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                
                String filter = DVDtitle.getText();
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
                    String search = "SELECT title.titleId, title.name, title.releaseYear, title.genre, music.artist, title.stock, title.available, title.rentPrice "
                    		+ "FROM title "
                    		+ "INNER JOIN music ON title.titleId=music.musicId " + where +" AND format = 'DVD'";
                    
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
        
        lMovietitle = new JLabel("Name of the Movie");
        lMovietitle.setFont(fontlabel);
        lMovietitle.setBounds(200, 430, 130, 20);
        lMovietitle.setVisible(false);
        Movietitle = new JTextField();
        Movietitle.setBounds(200, 470, 320, 25);
        Movietitle.setVisible(false);
        new ValidLength(name, 50);
        
        //Search button
        btnSearchMovie = new JButton("Search");
        btnSearchMovie.setFont(fontButton);
        btnSearchMovie.setBounds(540, 465, 90, 30);
        btnSearchMovie.setVisible(false);
        btnSearchMovie.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                
                String filter = Movietitle.getText();
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
                    String search = "SELECT title.titleId, title.name, title.releaseYear, title.genre, movie.format, title.stock, title.available, title.rentPrice "
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
        
        lSerietitle = new JLabel("Name of the TV Serie");
        lSerietitle.setFont(fontlabel);
        lSerietitle.setBounds(200, 430, 150, 20);
        lSerietitle.setVisible(false);
        Serietitle = new JTextField();
        Serietitle.setBounds(200, 470, 320, 25);
        Serietitle.setVisible(false);
        new ValidLength(name, 50);

        //Search button
        btnSearchSerie = new JButton("Search");
        btnSearchSerie.setFont(fontButton);
        btnSearchSerie.setBounds(540, 465, 90, 30);
        btnSearchSerie.setVisible(false);
        btnSearchSerie.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                ConectionDB con = new ConectionDB();
                Connection conection = con.conect();
                
                String filter = Serietitle.getText();
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
                    String search = "SELECT title.titleId, title.name, tvboxset.language, tvboxset.seasons, tvboxset.episodes, title.stock, title.available, title.rentPrice  "
                    		+ "FROM title "
                    		+ "INNER JOIN tvboxset ON title.titleId=tvboxset.serieId " + where;
                    
                    System.out.println(search);
                    ps = conection.prepareStatement(search);
                    rs = ps.executeQuery();
                    //Adding the result to the rows of the table
                    ResultSetMetaData rsMD = rs.getMetaData();
                    int qttycol = rsMD.getColumnCount();
                    
                    model.addColumn("ID");
	                model.addColumn("Name");
	                model.addColumn("Language");
	                model.addColumn("Seasons");
	                model.addColumn("Episodes");
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
        
        
        Calendar dayRented = Calendar.getInstance();
        System.out.println("Current date: " + formatCalendar(dayRented));		//The Rent day will be the current day

		rentedDay = new JTextField();
        rentedDay.setBounds(70, 520, 100, 25);
        rentedDay.setText(formatCalendar(dayRented));		// The JTextField will take the current date as the required format
        rentedDay.setVisible(false);
        
        // Calculating the Return Day (Adding 3 days from the rent day)
        dayRented.add(Calendar.DAY_OF_YEAR, +3);
		System.out.println("+3 days: " + formatCalendar(dayRented));
        returnDay = new JTextField();
        returnDay.setBounds(200, 520, 80, 25);
        returnDay.setText(formatCalendar(dayRented));		// The JTextField will take the current date as the required format
        returnDay.setVisible(false);
        
        stock = new JTextField();
        stock.setBounds(70, 570, 100, 25);
        stock.setVisible(false);
        
        available = new JTextField();
        available.setBounds(200, 570, 100, 25);
        available.setVisible(false);
        
        newAvailable = new JTextField();
        newAvailable.setBounds(70, 600, 100, 25);
        newAvailable.setVisible(false);
        
        loyaltyPoints = new JTextField();
        loyaltyPoints.setBounds(70, 630, 100, 25);
        loyaltyPoints.setVisible(false);
        
        newloyaltyPoints = new JTextField();
        newloyaltyPoints.setBounds(200, 630, 100, 25);
        newloyaltyPoints.setVisible(false);
        
        freeRent = new JTextField();
        freeRent.setBounds(70, 170, 100, 25);
        freeRent.setVisible(false);
        
        newfreeRent = new JTextField();
        newfreeRent.setBounds(70, 170, 100, 25);
        newfreeRent.setVisible(false);
        
        //Save Rent button
        btnSaveRent = new JButton("Rent Title");
        btnSaveRent.setFont(fontButton);
        btnSaveRent.setBounds(300, 550, 100, 30);
        btnSaveRent.setVisible(false);
        btnSaveRent.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	//Validation of required fields
            	if(ID.getText().equals("") || ltitleId.getText().equals("")) {		//We must type a Customer ID and Title ID
            		JOptionPane.showMessageDialog(null, "Customer ID and Title ID cannot be empty");
            	}else {
            		ConectionDB con = new ConectionDB();
            	    Connection conection = con.conect();
            	    //Check stock
            	    try {
            	    	String filter = titleId.getText();
            	        String where = "";
            	        //Our filter must not be empty
            	        if(!"".equals(filter)){
            	            where = "WHERE titleID = " + filter;
            	        }
            	    	PreparedStatement ps = null;
            	        ResultSet rs = null;
            	        //'search' will be the query that will be send to the database to find the stock and available
            	        String search = "SELECT stock FROM title " + where;
            	        System.out.println(search);
            	        ps = conection.prepareStatement(search);
            	        rs = ps.executeQuery();
            	        //We will take the result of the query and this will be written on the JTextField 'stock'
            	        while(rs.next()) {
            	        	stock.setText(rs.getString("stock"));
            	            res = rs.getString("stock");
            	            System.out.println("Stock: " + rs.getString("stock"));
            	        }
            	        //The quantity in Stock must be more than 0
            	        qttyStock = Integer.parseInt(stock.getText());
            	        if(qttyStock > 0) {

            	        	checkAvailability();

            	        }else {
            	        	JOptionPane.showMessageDialog(null, "The rent is not possible! \n"
            	        			+ "Stock quantity of the Title is 0");
            	        }
            	    } catch (Exception e){      //If something goes wrong
            	        JOptionPane.showMessageDialog(null, "Error finding the Stock quantity! \n"
            	        		+ "Please check the ID of the Title");
            	    }
            	}
            }
        });
        
        //Cancel button
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(fontButton);
        btnCancel.setBounds(600, 550, 100, 30);
        btnCancel.setVisible(false);
        btnCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	normalScreen();
            	resetTextField();
            }
        });
        
        btnFreeRent = new JButton("Free Rent");
        btnFreeRent.setFont(fontButton);
        btnFreeRent.setBounds(450, 600, 100, 30);
        btnFreeRent.setVisible(false);//If the Customer has a Free Rent
        btnFreeRent.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	checkFreeRent();
                
            }
        });
        
        btnSaveFreeRent = new JButton("Use Free Rent");
        btnSaveFreeRent.setFont(fontButton);
        btnSaveFreeRent.setBounds(300, 550, 140, 30);
        btnSaveFreeRent.setVisible(false); 
        btnSaveFreeRent.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	useFreeRent();
                
            }
        });
        
        p.add(ltitle);
        p.add(btnRefresh);
        p.add(btnNewCDRent);
        p.add(btnNewDVDRent);
        p.add(btnNewMovieRent);
        p.add(btnNewSerieRent);
        p.add(lID);
        p.add(ID);
        p.add(lname);
        p.add(name);
        p.add(btnSearchName);
        p.add(ltitleId);
        p.add(titleId);
        p.add(lCDtitle);
        p.add(CDtitle);
        p.add(btnSearchCD);
        p.add(lDVDtitle);
        p.add(DVDtitle);
        p.add(btnSearchDVD);
        p.add(lMovietitle);
        p.add(Movietitle);
        p.add(btnSearchMovie);
        p.add(lSerietitle);
        p.add(Serietitle);
        p.add(btnSearchSerie);
        p.add(rentedDay);
        p.add(returnDay);
        p.add(btnSaveRent);
        p.add(btnCancel);
        p.add(stock);
        p.add(newAvailable);
        p.add(available);
        p.add(loyaltyPoints);
        p.add(newloyaltyPoints);
        p.add(btnFreeRent);
        p.add(freeRent);
        p.add(newfreeRent);
        p.add(btnSaveFreeRent);
        
        this.validate();
        this.repaint();
	}

	//This will modify the window to be able to see all the required information to rent a Title
	public void rentScreen() {
		lID.setVisible(true);
		ID.setVisible(true);
		lname.setVisible(true);
		name.setVisible(true);
		btnSearchName.setVisible(true);
		ltitleId.setVisible(true);
		titleId.setVisible(true);

		btnNewCDRent.setVisible(false);
		btnSearchCD.setVisible(false);
		btnNewDVDRent.setVisible(false);
		btnSearchDVD.setVisible(false);
		btnNewMovieRent.setVisible(false);
		btnSearchMovie.setVisible(false);
		btnNewSerieRent.setVisible(false);
		btnSearchSerie.setVisible(false);
		
		btnSaveRent.setVisible(true);
		btnCancel.setVisible(true);
		btnFreeRent.setVisible(true);
	}
	
	public void normalScreen() {
		lID.setVisible(false);
		ID.setVisible(false);
		lname.setVisible(false);
		name.setVisible(false);
		btnSearchName.setVisible(false);
		ltitleId.setVisible(false);
		titleId.setVisible(false);
		lCDtitle.setVisible(false);
    	CDtitle.setVisible(false);
    	lDVDtitle.setVisible(false);
    	DVDtitle.setVisible(false);
    	lMovietitle.setVisible(false);
    	Movietitle.setVisible(false);
    	lSerietitle.setVisible(false);
    	Serietitle.setVisible(false);
    	
		btnSearchCD.setVisible(false);
		btnSearchDVD.setVisible(false);
		btnSearchMovie.setVisible(false);
		btnSearchSerie.setVisible(false);
		
		btnNewCDRent.setVisible(true);
		btnNewDVDRent.setVisible(true);
		btnNewMovieRent.setVisible(true);
		btnNewSerieRent.setVisible(true);
		
		btnSaveRent.setVisible(false);
		btnCancel.setVisible(false);
		btnFreeRent.setVisible(false);
		btnSaveFreeRent.setVisible(false);
	}
	
	//This will check if the title has enough quantity to rent	
	public void checkAvailability() {
			
			ConectionDB con = new ConectionDB();
	        Connection conection = con.conect();
	        
	        String filter = titleId.getText();
	        String where = "";
	        //Our filter must not be empty
	        if(!"".equals(filter)){
	            where = "WHERE titleID = " + filter;
	        }
	        
        	try{
        		
        		PreparedStatement pst = null;
    	        ResultSet rst = null;
    	        
    	        //'newsearch' will be the query that will be send to the database to find the stock and available
    	        String newsearch = "SELECT available FROM title " + where;
    	        System.out.println(newsearch);
    	        pst = conection.prepareStatement(newsearch);
    	        rst = pst.executeQuery();
    	        //We will take the result of the query and this will be written on the JTextField 'available'
    	        while(rst.next()) {
    	        	available.setText(rst.getString("available"));
    	            res = rst.getString("available");
    	            System.out.println("Available: " + rst.getString("available"));
    	        }
    	        //The quantity available must not be less than quantity in Stock
    	        qttyAvailable = Integer.parseInt(available.getText());
    	        if(qttyAvailable == 0) {
    	        	JOptionPane.showMessageDialog(null, "The rent is not possible! \n"
    	        			+ "There is not enough titles availables to rent");
    	        }else {
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
                        
                        //Calculating to new Available quantity
    		    	    
    		    	    qttyAvailable --;		//Subtracting -1 to the quantity Available
    		    	    System.out.println("Available Integer -1: " + qttyAvailable);
        	        	stravailable = Integer.toString(qttyAvailable);		//Converting the new quantity available to an Integer
        	        	System.out.println("stravailable: " + stravailable);
        	        	newAvailable.setText(stravailable);		//The value of that String will be written on a JTextField
        	        	System.out.println("New Qtty Availalble: " + stravailable);
        	        	//Updating quantity available
    		    	    try {
    		    	    	//'updateavailable' will be the query that we will send to the database to find the results
                        	String updateavailable = "UPDATE title SET available = ? " + where;
                            System.out.println("My update Available: " + updateavailable);
                            PreparedStatement newstatement = conection.prepareStatement(updateavailable);
                            
                            newstatement.setString(1, newAvailable.getText());
                            
                            newstatement.execute();
                            
    		    	    }catch (Exception e){      //If something goes wrong
    		    	    	JOptionPane.showMessageDialog(null, "Error updating quantity Available");
                        }

                        //Manage the LoyaltyPoints
                        loyaltyPoints();
                        
                        conection.close();
                        
                        JOptionPane.showMessageDialog(null, "New Title rented successfully");
                        ID.setText("");
                        titleId.setText("");
                        available.setText("");
                        newAvailable.setText("");
                        
                        normalScreen();
                        
    	        	}catch (Exception e){      //If something goes wrong
                        JOptionPane.showMessageDialog(null, "Error renting a Title!\n"
                        		+ "Please check the Customer and Title ID");
                    }
    	        }
    	    } catch (Exception e){      //If something goes wrong
                JOptionPane.showMessageDialog(null, "Error finding Availability!");
            }
		}
		
		//This will manage the Loyalty Points	
		public void loyaltyPoints() {
					
			ConectionDB con = new ConectionDB();
            Connection conection = con.conect();
            
            try{
            	//Declaring our 'where' condition to be used as filter
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
    	        //We will take the result of the query and this will be written on the JTextField 'loyaltyPoints'
    	        while(rs.next()) {
    	        	loyaltyPoints.setText(rs.getString("loyaltyPoints"));
    	            res = rs.getString("loyaltyPoints");
    	            System.out.println("Loyalty Points: " + rs.getString("loyaltyPoints"));
    	        }
    	        
    	        strloyaltyPoints = loyaltyPoints.getText();		//Storing the content of the JTextField on a String
    	        System.out.println(strloyaltyPoints);
    	        intloyaltyPoints = Integer.parseInt(loyaltyPoints.getText());		//Converting the value of that String to an Integer
    	        intloyaltyPoints = intloyaltyPoints +10;		//Adding +10 to the Loyalty Points of the Customer
    	        System.out.println(intloyaltyPoints);
    	        
    	        qttyLoyaltyPoints = Integer.toString(intloyaltyPoints);
    	        
    	        newloyaltyPoints.setText(qttyLoyaltyPoints);		//The value of that String will be written on a JTextField
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
    	        
    	        freeRent();
    	        
    	        conection.close();
    	        
    	        //JOptionPane.showMessageDialog(null, "Loyalty Points updated successfully");
    	        loyaltyPoints.setText("");
    	        newloyaltyPoints.setText("");
    	        
    	        
            } catch (Exception e){      //If something goes wrong
            	JOptionPane.showMessageDialog(null, "Error finding Loyalty Points!");
            }
				
		}
		
		public void freeRent() {
			if(intloyaltyPoints % 100 == 0) {		//The free rent will be available when the loyalty points is any number multiple of 100
				numberOffreeRent();		//Check the Number of Free Rents available
				JOptionPane.showMessageDialog(null, "Congratulations! \n"
	        			+ "You have " + intloyaltyPoints + " Loyalty Points\n"
	        					+ "You have " + qttyfreeRent + " available Free Rent(s)");
	        	rentIsFree = true;		//The Customer has at least one Free Rent
	        }
		}
		
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
	                System.out.println("My update Free Rent: " + freeRentLeft);
	                PreparedStatement newstatement = conection.prepareStatement(freeRentLeft);
	                
	                newstatement.setString(1, newfreeRent.getText());
	                
	                newstatement.execute();
		        	
		        }catch (Exception e){      //If something goes wrong
	            	JOptionPane.showMessageDialog(null, "Error calculating Free Rent left!");
	            }
		        conection.close();
		        
		        //JOptionPane.showMessageDialog(null, "Free Rent used successfully");
		        freeRent.setText("");
		        newfreeRent.setText("");
		        
	        } catch (Exception e){      //If something goes wrong
	        	JOptionPane.showMessageDialog(null, "Error finding Free Rent!");
	        }
		}
	
		public void checkFreeRent() {
			ConectionDB con = new ConectionDB();
            Connection conection = con.conect();
            if(ID.getText().equals("")) {
            	JOptionPane.showMessageDialog(null, "Customer ID empty!");
            }else {
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
        	        //We will take the result of the query and this will be written on the JTextField 'freeRent'
        	        while(rs.next()) {
        	        	freeRent.setText(rs.getString("freeRent"));
        	            res = rs.getString("freeRent");
        	            System.out.println("Free Rent: " + rs.getString("freeRent"));
        	        }
        	        
        	        strfreeRent = freeRent.getText();
        	        System.out.println(strfreeRent);
        	        intfreeRent = Integer.parseInt(freeRent.getText());
        	        //Validation if the Customer has any Free Rent Available
        	        if(intfreeRent > 0) {
        	        	JOptionPane.showMessageDialog(null, "This Customer has " + intfreeRent + " Free Rent available(s)");
        	        	btnSaveFreeRent.setVisible(true);
        	        	btnFreeRent.setVisible(false);
        	        	btnSaveRent.setVisible(false);
        	        	
        	        } else {
        	        	JOptionPane.showMessageDialog(null, "The Customer does not have any Free Rent Available");
        	        }
                } catch (Exception e){      //If something goes wrong
                	JOptionPane.showMessageDialog(null, "Error finding Loyalty Points!");
                }
            }
		}
		
		public void useFreeRent() {
			ConectionDB con = new ConectionDB();
	        Connection conection = con.conect();
	        //Title ID must not be empty
	        if(titleId.getText().equals("")) {
            	JOptionPane.showMessageDialog(null, "Title ID empty!");
            } else {
	        
	        String filter = titleId.getText();
	        String whereTitleId = "";
	        //Our filter must not be empty
	        if(!"".equals(filter)){
	        	whereTitleId = "WHERE titleID = " + filter;
	            System.out.println("whereTitleId: " + whereTitleId);
	        }	
	        //Check if the Title has availability
	        try {
	        	PreparedStatement pst = null;
		        ResultSet rst = null;
		        
		        //'newsearch' will be the query that will be send to the database to find the stock and available
		        String newsearch = "SELECT available FROM title " + whereTitleId;
		        System.out.println("newsearch: " + newsearch);
		        pst = conection.prepareStatement(newsearch);
		        rst = pst.executeQuery();
		        //We will take the result of the query and this will be written on the JTextField 'available'
		        while(rst.next()) {
		        	available.setText(rst.getString("available"));
		            res = rst.getString("available");
		            System.out.println("Available: " + rst.getString("available"));
		        }
		        
		        qttyAvailable = Integer.parseInt(available.getText());
		        System.out.println("Available Integer" + qttyAvailable);
		        //The quantity available must not be 0
		        if(qttyAvailable == 0) {
		        	JOptionPane.showMessageDialog(null, "The rent is not possible! \n"
		        			+ "There is not enough titles availables to rent");
		        } else {
		        	//Calculating to new Available quantity
		    	    
		    	    qttyAvailable --;		//Subtracting -1 to the quantity Available
		    	    System.out.println("Available Integer -1: " + qttyAvailable);
    	        	stravailable = Integer.toString(qttyAvailable);		//Converting the new quantity available to an Integer
    	        	System.out.println("stravailable: " + stravailable);
    	        	newAvailable.setText(stravailable);		//The value of that String will be written on a JTextField
    	        	System.out.println("New Qtty Availalble: " + stravailable);
    	        	//Updating quantity available
		    	    try {
		    	    	//'updateavailable' will be the query that we will send to the database to find the results
                    	String updateavailable = "UPDATE title SET available = ? " + whereTitleId;
                        System.out.println("My update Available: " + updateavailable);
                        PreparedStatement newstatement = conection.prepareStatement(updateavailable);
                        
                        newstatement.setString(1, newAvailable.getText());
                        
                        newstatement.execute();
                        //JOptionPane.showMessageDialog(null, "available updated");
                        //Saving information of the rent of the Title
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
	                        //JOptionPane.showMessageDialog(null, "information updated");
	                        //Check and calculate Loyalty Points
	                        try {
	                        	String newfilter = ID.getText();
	                            String whereCustId = "";
	                            //Our filter must not be empty
	                            if(!"".equals(newfilter)){
	                            	whereCustId = "WHERE custId = " + newfilter;
	                                System.out.println("whereCustId: " + whereCustId);
	                            }	        
	                            
	                        	PreparedStatement ps = null;
	                	        ResultSet rs = null;
	                	        
	                        	//'search' will be the query that will be send to the database to find the stock and available
	                	        String search = "SELECT loyaltyPoints FROM membershipCard " + whereCustId;
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
	                	        //The Customer must have at least 100 Loyalty Points
	                	        if(intloyaltyPoints < 100) {
	                	        	JOptionPane.showMessageDialog(null, "The Customer does not have enough Loyalty Points for a Free Rent!");
	                	        	btnSaveRent.setVisible(true);
	                	        	btnFreeRent.setVisible(true);
	                	        	btnSaveFreeRent.setVisible(false);
	                	        	resetTextField();
	                	        } else {
	                	        	intloyaltyPoints = intloyaltyPoints -100;		//Adding +10 to the Loyalty Points of the Customer
		                	        System.out.println(intloyaltyPoints);
		                	        
		                	        qttyLoyaltyPoints = Integer.toString(intloyaltyPoints);
	                	        
		                	        newloyaltyPoints.setText(qttyLoyaltyPoints);		//The value of that String will be written on a JTextField
		                	        System.out.println("New Loyalty Points: " + qttyLoyaltyPoints);
		                	        
		                	        //Updating Loyalty Points
		                	        try {
		                	        	
		                	        	//'newloyaltypoints' will be the query that we will send to the database to find the results
		                            	String newloyaltypoints = "UPDATE membershipCard SET loyaltyPoints = ? " + whereCustId;
		                                System.out.println("My update Loyalty Points: " + newloyaltypoints);
		                                PreparedStatement newstatement1 = conection.prepareStatement(newloyaltypoints);
		                                
		                                newstatement1.setString(1, newloyaltyPoints.getText());
		                                
		                                newstatement1.execute();
		                                //JOptionPane.showMessageDialog(null, "Loyalty Points updated!");
		                                //Check and calculate new quantity of Free Rent
		                                try {		                                	
		    	                	        PreparedStatement nps = null;
		    	                	        ResultSet nrs = null;
		    	                	        //'searchnew' will be the query that will be send to the database to find the stock and available
		    	                	        String searchnew = "SELECT freeRent FROM membershipCard " + whereCustId;
		    	            		        System.out.println(searchnew);
		    	            		        nps = conection.prepareStatement(searchnew);
		    	            		        nrs = nps.executeQuery();
		    	            		        //We will take the result of the query and this will be written on the JTextField 'freeRent'
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
		    	            		        //Updating Free Renty quantity
		    	            		        try {
		    	            		        	//'freeRentLeft' will be the query that we will send to the database to find the results
		    	            	            	String freeRentLeft = "UPDATE membershipCard SET freeRent = ? " + whereCustId;
		    	            	                System.out.println("My update Free Rent: " + freeRentLeft);
		    	            	                PreparedStatement statementnew = conection.prepareStatement(freeRentLeft);
		    	            	                
		    	            	                statementnew.setString(1, newfreeRent.getText());
		    	            	                
		    	            	                statementnew.execute();
		    	            	                
		    	            	                conection.close();
		    	                                
		    	                                JOptionPane.showMessageDialog(null, "New Title rented successfully using Free Points");
		    	                                ID.setText("");
		    	                                titleId.setText("");
		    	                                available.setText("");
		    	                                newAvailable.setText("");
		    	                                
		    	                                normalScreen();
		    	            	                
		    	            		        } catch (Exception e){      //If something goes wrong
			    	    	                    JOptionPane.showMessageDialog(null, "Error updating quantity of Free Rents");
			    	    	                }
		                                } catch (Exception e){      //If something goes wrong
		    	    	                    JOptionPane.showMessageDialog(null, "Error calculating quantity of Free Rents");
		    	    	                }
		                	        } catch (Exception e){      //If something goes wrong
			    	                    JOptionPane.showMessageDialog(null, "Error updating Loyalty Points!");
			    	                }
	                	        }
	                        } catch (Exception e){      //If something goes wrong
	    	                    JOptionPane.showMessageDialog(null, "Error calculating Loyalty Points!");
	    	                }
    		        	} catch (Exception e){      //If something goes wrong
    	                    JOptionPane.showMessageDialog(null, "Error inserting information of the rent");
    	                }
		    	    } catch (Exception e){      //If something goes wrong
	                    JOptionPane.showMessageDialog(null, "Error updating quantity Available");
	                }
		        }
	        }catch (Exception e){      //If something goes wrong
	            JOptionPane.showMessageDialog(null, "Error finding Availability of Title");
	        }
	        
            }
		}
		
		
		//This method will configure the format of the dates	
		public static String formatCalendar(Calendar dayRented) {
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
		
			return dateFormat.format(dayRented.getTime());	
		}

		public void resetTextField() {
			name.setText("");
			ID.setText("");
			titleId.setText("");
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
