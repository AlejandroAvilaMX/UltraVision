/**
 * This is the Access Window, we can add and delete users to use the program
 * 
 * author: Cesar Alejandro Avila Calderon		Student Number: 2018451
 */
package access;
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
import rent.Rent;
import titles.Titles;
import utravision.ConectionDB;
import utravision.LoginController;

public class Access extends JFrame implements ActionListener{
	private JLabel ltitle, lusername, lpassword;
	private JTextField username, password;
	private DefaultTableModel model;

	public Access() {
		this.setVisible(true);
        this.setSize(550, 550);     //Size of the window
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
        //Title of the window
        ltitle = new JLabel("Access");
        ltitle.setFont(fonttitle);
        ltitle.setBounds(220, 50, 230, 20);
        //Button refresh
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(fontButton);
        btnRefresh.setBounds(40, 90, 100, 30);
        btnRefresh.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                //Call the method 'refresh'
            	refresh();
            	//Add the model to the table
                JTable table = new JTable(model);
                //Add the scroll to the table
                JScrollPane scroll= new JScrollPane(table);
                table.setBounds(40,140,200,120);
                scroll.setBounds(40,140,200,120);
                //Add the scroll to the Panel
                p.add(scroll);
            }
        });
        
        lusername = new JLabel("Username");
        lusername.setFont(fontlabel);
        lusername.setBounds(70,280, 80, 20);
        username = new JTextField();
        username.setBounds(70, 310, 120, 25);
        
        lpassword = new JLabel("Password");
        lpassword.setFont(fontlabel);
        lpassword.setBounds(380, 280, 80, 20);
        password = new JTextField();
        password.setBounds(380, 310, 120, 25);
        //Button New
        JButton btnNew = new JButton("New");
        btnNew.setFont(fontButton);
        btnNew.setBounds(100, 400, 100, 30);
        btnNew.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
            	//Validation of mandatory fields
            	if(username.getText().equals("") || password.getText().equals("")) {		//we must type something in username and password
            		JOptionPane.showMessageDialog(null, "Username and Password cannot be empty");
            	} else {
            		//Call method 'newAccess'
            		newAccess();
            	}      
            }
        });
        
        //Delete button
        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(fontButton);
        btnDelete.setBounds(300, 400, 100, 30);
        btnDelete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                //Call mehod 'deleteAccess'
            	deleteAccess();
            }
        });
        
        p.add(ltitle);
        p.add(btnRefresh);
        p.add(lusername);
        p.add(username);
        p.add(lpassword);
        p.add(password);
        p.add(btnNew);
        p.add(btnDelete);
        
        this.validate();
        this.repaint();
	}
	
	public void refresh() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        try{
            
            model = new DefaultTableModel();
            
            PreparedStatement ps = null;
            ResultSet rs = null;
            //'refresh' will be the query that we will show all the information on the table access
            String refresh = "SELECT username, password FROM access;";
            //Adding the result to the rows of the table
            ps = conection.prepareStatement(refresh);
            rs = ps.executeQuery();
            
            ResultSetMetaData rsMD = rs.getMetaData();
            int qttycol = rsMD.getColumnCount();
            
            model.addColumn("Username");
            model.addColumn("Password");
            
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
	
	public void newAccess() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        
        try{
        	//'adduser' will be the query that we will send to the database to add the new access
        	String adduser = "INSERT INTO access (username, password) VALUES(?, ?)"; 
            
            PreparedStatement statement = conection.prepareStatement(adduser);
            statement.setString(1, username.getText());
            statement.setString(2, password.getText());
            
            statement.executeUpdate();
               
            conection.close();
            
            JOptionPane.showMessageDialog(null, "New Access inserted successfully\n"
            		+ "Please Refresh");
            username.setText("");
            password.setText("");
            
        } catch (Exception e){      //If something goes wrong
            JOptionPane.showMessageDialog(null, "Error inserting a new User!");
        }
	}
	
	public void deleteAccess() {
		ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        //We will use the username as the filter
        String filter = username.getText();
        String where = "";
        System.out.println("My filter is " + filter);
        //Our filter must not be empty
        if(!"".equals(filter)){     //
            where = "WHERE username = '" + filter + "'";
            System.out.println("My WHERE is: " + where);
            try{
            //'deleteuser' will be the query that we will send to the database to delete the access
            String deleteuser = "DELETE FROM access WHERE username = ?"; 
            
            PreparedStatement statement = conection.prepareStatement(deleteuser);
            statement.setString(1, username.getText());
            System.out.println("my query is: " +deleteuser);
            statement.execute();
               
            conection.close();
            
            JOptionPane.showMessageDialog(null, "Access deleted successfully\n"
            		+ "Please Refresh");
            username.setText("");
            password.setText("");
            
            } catch (Exception e){      //If something goes wrong
            	JOptionPane.showMessageDialog(null, "Error deleting the Access!");
            }
            
        } else {       //The username must be a valid username
            JOptionPane.showMessageDialog(null, "Error deleting the Access! Possible reassons: \n"
                    + "* The username cannot be empty");
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
            //new UltraVision();
            dispose();
        }	
	}
}
