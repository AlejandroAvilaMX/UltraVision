package utravision;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow extends JFrame implements ActionListener{
	
	private JLabel ltitle, lusername, lpassword;
    private JTextField username;
    private JPasswordField password;
    private final LoginController controllerInternalRef;

	public LoginWindow(LoginController controller) {
		
		this.controllerInternalRef = controller;
        attributesSetter();
        components();
        validation();
    }
	
	private void attributesSetter(){
        this.setVisible(true);
        this.setSize(410,430);      //Size of the window
        this.setTitle("Login");     //Title of the window
    }

	private void components(){
        JPanel p = new JPanel();
        p.setLayout(null);
        this.add(p);
        p.setBackground(java.awt.Color.orange);     //Color of the window
        Font fonttitle = new Font("Arial", Font.BOLD, 28);      //Font style (title)
        Font fontlabel = new Font("Calibri", Font.PLAIN, 16);       //Font style (label)
        Font fontbutton = new Font("Tahoma", Font.BOLD, 12);        //Font style (buttons)
        //My Menu Bar
        JMenuBar myMenuBar = new JMenuBar();
        this.setJMenuBar(myMenuBar);
    
        JMenu myMenu = new JMenu("File");       //Title of the Menu
        myMenuBar.add(myMenu);
        //Options of the menu
        //Close the program
        JMenuItem Close = new JMenuItem("Close");
        myMenu.add(Close);
        Close.addActionListener(this);
        Close.setActionCommand("close");
    
        ltitle = new JLabel("Ultra-Vision");
        ltitle.setFont(fonttitle);
        ltitle.setBounds(110, 20, 230, 40);
        
        lusername = new JLabel("Username");
        lusername.setFont(fontlabel);
        lusername.setBounds(90, 100, 80, 20);
        username = new JTextField();
        username.setBounds(90, 130, 200, 25);
        
        lpassword = new JLabel("Password");
        lpassword.setFont(fontlabel);
        lpassword.setBounds(90, 190, 80, 20);
        password = new JPasswordField();
        password.setBounds(90, 220, 200, 25);
        //Button Login
        JButton button = new JButton("Login");
        button.setFont(fontbutton);
        button.setBounds(150, 290, 90, 30);
        button.addActionListener((ActionListener) controllerInternalRef);
        button.setActionCommand("b");
        
        p.add(ltitle);
        p.add(lusername);
        p.add(username);
        p.add(lpassword);
        p.add(password);
        p.add(button);
        
    }
    
    private void validation(){
        this.validate();
        this.repaint();
    }
    
    public String getUsername(){
        return username.getText();
    }
    public String getPassword(){
        return password.getText();
    }
    //Actions of the Menu
    @Override
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();
        if(ac.equals("close")){
            System.out.println("Exit the program");
            System.exit(0);
        }
    }
}
