package rent;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RentedDates extends JFrame implements ActionListener{
	private JTextField rentedDay, returnDay;

	public RentedDates() {
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
        
        Calendar dayRented = Calendar.getInstance();
        System.out.println("Fecha original: " + formatearCalendar(dayRented));

		// Adding 3 days
        dayRented.add(Calendar.DAY_OF_YEAR, +3);
		System.out.println("+3 días: " + formatearCalendar(dayRented));
        
		
		
        rentedDay = new JTextField();
        rentedDay.setBounds(70, 520, 100, 25);
        rentedDay.setText(formatearCalendar(dayRented));		// The JTextField will take the current date as the required format
        //rentedDay.setVisible(false);
        
        returnDay = new JTextField();
        returnDay.setBounds(200, 520, 80, 25);
        returnDay.setText(formatearCalendar(dayRented));		// The JTextField will take the current date as the required format
        //returnDay.setVisible(false);
        
        p.add(rentedDay);
        p.add(returnDay);
       
	}
	

	//This method will configure the format of the dates
	public static String formatearCalendar(Calendar dayRented) {
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
		
		return dateFormat.format(dayRented.getTime());	
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}

/*

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
        	//Check availability
            try {
                //Our filter must not be empty
                if(!"".equals(filter)){
                    where = "WHERE titleID = " + filter;
                }
            	PreparedStatement pS = null;
                ResultSet rS = null;
                //'search' will be the query that will be send to the database to find the stock and available
                String Search = "SELECT available FROM title " + where;
                
                System.out.println(Search);
                pS = conection.prepareStatement(Search);
                rS = pS.executeQuery();
                //We will take the result of the query and this will be written on the JTextField 'stock'
                while(rS.next()) {
                	available.setText(rS.getString("available"));
                    res = rS.getString("available");
                    System.out.println("Available: " + rS.getString("available"));
                }
                //The quantity in Stock must be more than 0
                qttyAvailable = Integer.parseInt(available.getText());
                if(qttyStock > qttyAvailable) {
                	try{
                        //'addrent' will be query that will be send to the database to add a new register on the table rent                    
                        String addrent = "INSERT INTO rent (rentDay, returnDay, titleId, custId) VALUES(?, ?, ?, ?)"; 
                        System.out.println("Query new Rent " + addrent);
                        PreparedStatement statement = conection.prepareStatement(addrent);
                        statement.setString(1, rentedDay.getText());
                        statement.setString(2, returnDay.getText());
                        statement.setString(3, titleId.getText());
                        statement.setString(4, ID.getText());
                        
                        statement.executeUpdate();
                        
                        qttyStock -- ;
                        newQtty = Integer.toString(qttyStock);
                        //System.out.println("The new Stock quantity is: " + qttyStock);
                        System.out.println("The new Stock quantity is: " + newQtty);
                        
                        stock.setText(newQtty);
                        
                        try {
                        	//'newStock' will be the query that will be send to the database to add a the music details of the new Title
                           	String newStock = "UPDATE title SET stock = ? " + where; 
                           	System.out.println(stock.getText());
                            System.out.println("Query insert new CD: " + newStock);
                            
    	                    PreparedStatement newstatement = conection.prepareStatement(newStock);
    	                    statement.setString(1, stock.getText());
                            
                            newstatement.executeUpdate();
                            
                        } catch (Exception e){      //If something goes wrong
	                        JOptionPane.showMessageDialog(null, "Error with stock quantity");
	                    }
                        
                        conection.close();
                        
                        JOptionPane.showMessageDialog(null, "New Title rented successfully");
                        ID.setText("");
                        titleId.setText("");
                        
                    } catch (Exception e){      //If something goes wrong
                        JOptionPane.showMessageDialog(null, "Error renting a Title!");
                    }
                } else {
                	JOptionPane.showMessageDialog(null, "The rent is not possible! \n"
                			+ "Stock quantity of the Title is 0");
                }
            	
            	
            } catch (Exception e){      //If something goes wrong
                JOptionPane.showMessageDialog(null, "Error finding Availability!");
            }
        } else {
        	JOptionPane.showMessageDialog(null, "The rent is not possible! \n"
        			+ "Stock quantity of the Title is 0");
        }
    
        
    } catch (Exception e){      //If something goes wrong
        JOptionPane.showMessageDialog(null, "Error finding Stock!");
    }
}*/