package utravision;

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
import javax.swing.table.DefaultTableModel;

import customers.Customers;
import customers.MembershipCards;
import rent.Rent;
import titles.Titles;

public class WelcomeStaff extends JFrame implements ActionListener{
	private JLabel ltitle;

	public WelcomeStaff(User userLogged) {
		this.setVisible(true);
        this.setSize(370, 400);     //Size of the window
        this.setTitle("Ultra-Vision");       //Title of the window
        
        JPanel p = new JPanel();
        p.setLayout(null);
        this.add(p);
        p.setBackground(java.awt.Color.orange);     //Color of the window
        Font fonttitle = new Font("Arial", Font.BOLD, 28);      //Font style (title)
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
        
        ltitle = new JLabel("Main Menu");
        ltitle.setFont(fonttitle);
        ltitle.setBounds(80, 50, 230, 20);
       
        //Button Customers
        JButton btnCustomers = new JButton("Customers");
        btnCustomers.setFont(fontButton);
        btnCustomers.setBounds(40, 140, 110, 30);
        btnCustomers.addActionListener(this);
        btnCustomers.setActionCommand("btnCustomers");      
        //Button Membership Card
        JButton btnMemCard = new JButton("Membership");
        btnMemCard.setFont(fontButton);
        btnMemCard.setBounds(200, 140, 110, 30);
        btnMemCard.addActionListener(this);
        btnMemCard.setActionCommand("btnMemCard");
        //Button btnTitles
        JButton btnTitles = new JButton("Titles");
        btnTitles.setFont(fontButton);
        btnTitles.setBounds(40, 240, 110, 30);
        btnTitles.addActionListener(this);
        btnTitles.setActionCommand("btnTitles");
        //Button Art Pieces
        JButton btnRent = new JButton("Rent");
        btnRent.setFont(fontButton);
        btnRent.setBounds(200, 240, 110, 30);
        btnRent.addActionListener(this);
        btnRent.setActionCommand("btnRent");
        
        p.add(ltitle);
        p.add(btnCustomers);
        p.add(btnMemCard);
        p.add(btnTitles);
        p.add(btnRent);
        
        this.validate();
        this.repaint();
        
    }
    //Actions of the buttons and menu
    @Override
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();
        if(ac.equals("exit")){
            System.out.println("Exit the program");
            System.exit(0);
        }
        else if(ac.equals("customers") || ac.equals("btnCustomers")){
            System.out.println("Going to Customers");
            new Customers();
            dispose();
        }
        else if(ac.equals("MemCard") || ac.equals("btnMemCard")){
            System.out.println("Going to Membership Card");
            new MembershipCards();
            dispose();
        }
        else if(ac.equals("titles") || ac.equals("btnTitles")){
            System.out.println("Going to Titles");
            new Titles();
            dispose();
        }
        else if(ac.equals("rent") || ac.equals("btnRent")){
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
