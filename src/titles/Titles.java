package titles;

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
import utravision.ConectionDB;
import utravision.LoginController;

public class Titles extends JFrame implements ActionListener{
	private JLabel ltitle;
	
	public Titles() {
		this.setVisible(true);
        this.setSize(1100, 550);     //Size of the window
        this.setTitle("Titles");       //Title of the window
        
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
        
        ltitle = new JLabel("Titles");
        ltitle.setFont(fonttitle);
        ltitle.setBounds(500, 50, 230, 20);
        
        //Table with the information of All Titles
        ConectionDB con = new ConectionDB();
        Connection conection = con.conect();
        	try{
        		DefaultTableModel model = new DefaultTableModel();
                    
                PreparedStatement ps = null;
                ResultSet rs = null;
                    
                String refresh = "SELECT titleId, name, releaseYear, genre, typeId, type FROM title;";
                //Adding the result to the rows of the table
                ps = conection.prepareStatement(refresh);
                rs = ps.executeQuery();
                    
                ResultSetMetaData rsMD = rs.getMetaData();
                int qttycol = rsMD.getColumnCount();
                    
                model.addColumn("ID");
                model.addColumn("Name");
                model.addColumn("ReleaseYear");
                model.addColumn("Genre");
                model.addColumn("Type ID");
                model.addColumn("Type");
                    
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
        
        //MC Filter
        JButton btnMC = new JButton("MC");
        btnMC.setFont(fontButton);
        btnMC.setBounds(40, 390, 110, 30);
        btnMC.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0){
        		new MC();
        	}
        	
        });

        //ML Filter
        JButton btnML = new JButton("ML");
        btnML.setFont(fontButton);
        btnML.setBounds(330, 390, 110, 30);
        btnML.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0){
        		new ML();
        	}
        	
        });
        
        //VL Filter
        JButton btnVL = new JButton("VL");
        btnVL.setFont(fontButton);
        btnVL.setBounds(620, 390, 110, 30);
        btnVL.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0){
        		new VL();
        	}
        	
        });
        
      //TV Filter
        JButton btnTV = new JButton("TV");
        btnTV.setFont(fontButton);
        btnTV.setBounds(925, 390, 110, 30);
        btnTV.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0){
        		new TV();
        	}
        	
        });
        
        p.add(ltitle);
        p.add(btnMC);
        p.add(btnML);
        p.add(btnVL);
        p.add(btnTV);
        
        this.validate();
        this.repaint();
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
