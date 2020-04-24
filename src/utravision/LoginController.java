package utravision;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class LoginController implements ActionListener{
	
	LoginModel model;
    LoginWindow view;
    WelcomeStaff welcome;
    WelcomeAdmin admin;
    
    public LoginController(){
        model = new LoginModel();
        view = new LoginWindow(this);   
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("b")){
            String un = view.getUsername();
            String pw = view.getPassword();
                        
            User userLogged = new User(un, pw);
            
            boolean login = model.connection(userLogged);
            System.out.println(login);
            //If the username or password are not correct
            if(login == false){
                JOptionPane.showMessageDialog(null, "Invalid Username or Password");
            }
            //If the username is empty
            if(un.equals("")){
               JOptionPane.showMessageDialog(null, "The Username cannot be empty");
            //If the password is empty
            }if(pw.equals("")){
                JOptionPane.showMessageDialog(null, "The Password cannot be empty");
            }
            //Validation of the User
            if(login){
                //If the user is Administrator
                if(un.equalsIgnoreCase("Admin") && pw.equalsIgnoreCase("Dublin")){
                    admin = new WelcomeAdmin(userLogged);
                    view.dispose();
                }
                //If the user is a Staff Member
                else{
                    welcome = new WelcomeStaff(userLogged);
                    view.dispose();
                }
            }      
        }
    }
}
