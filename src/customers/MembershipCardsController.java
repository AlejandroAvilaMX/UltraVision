package customers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import utravision.ConectionDB;
import utravision.LoginWindow;

public class MembershipCardsController  implements ActionListener{
	MembershipCardsWindow view;

	public MembershipCardsController() {
		view = new MembershipCardsWindow(this); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("b")){
			
			//Validation of the mandatory fields
        	if(view.cardNumber.getText().equals("") || view.levelId.getText().equals("")) {		//We must type a Card Number and Level Id
        		JOptionPane.showMessageDialog(null, "Card Number and Level ID cannot be empty");
        	}else {
        		if(view.levelId.getText().equals("PR") || view.levelId.getText().equals("TV") || view.levelId.getText().equals("ML") || view.levelId.getText().equals("VL")) {		//If something goes wrong with our JComboBox we must type the Level Id correct
        			//Call method 'levelDescription'
        			levelDescription();
            		//Call method 'updateCard'
        			updateCard();
            	}
        	}   
		}
		
	}

	//This method will Update the details of the Credit Card
	public void updateCard() {
		//The Card Number cannot have less than 16 digits
		if(view.cardNumber.getText().length()<16) {
			JOptionPane.showMessageDialog(null, "The Card Number must have 16 digits...!!");
		} else {
			ConectionDB con = new ConectionDB();
	        Connection conection = con.conect();
	        //Declaring our 'where' condition to be used as filter
	        String filter = view.ID.getText();
	        System.out.println("My filter is: " + filter);
	        String where = "";
	        //Our filter must not be empty
	        if(!"".equals(filter)){
	            where = "WHERE IdCard = '" + filter + "'";
	            System.out.println("My where is: " + where);
	            try{
	                //'updatecard' will be the query that we will send to the database to find the results
	                String updatecard = "UPDATE membershipCard SET cardNumber = ?, levelId = ?, level = ? " + where; 
	                System.out.println(updatecard);
	                PreparedStatement statement = conection.prepareStatement(updatecard);
	                statement.setString(1, view.cardNumber.getText());
	                statement.setString(2, view.levelId.getText());
	                statement.setString(3, view.level.getText());
	                statement.execute();
		                   
	                conection.close();
		                
	                JOptionPane.showMessageDialog(null, "Membeship Card updated successfully\n"
	                		+ "Please Refresh");
	                view.ID.setText("");
	                view.name.setText("");
	                view.levelId.setText("");
	                view.cardNumber.setText("");
		            
	                normalScreen();
	                view.btnSaveUpdate.setVisible(false);
	                
	                } catch (Exception e){		//If something goes wrong
	                	JOptionPane.showMessageDialog(null, "Error updating Membership Card! \n"
	                			+ "· Card ID must be a valid numeric ID");
	                }
	        } else{       //The ID must have a valid ID number
	            JOptionPane.showMessageDialog(null, "Error updating Customer! \n"
	                    + "· The ID cannot be empty");
	        }
		}
	}
	
	//This method will return the components of the window to their original state
	public void normalScreen() {
		view.lID.setVisible(true);
		view.lCustomerId.setVisible(false);
		view.lcardNumber.setVisible(false);
		view.cardNumber.setVisible(false);
		view.llevelId.setVisible(false);
		view.comboLevel.setVisible(false);
		
		//btnNew.setVisible(true);
		view.btnUpdateCard.setVisible(true);
		//btnDeleteCard.setVisible(true);
		
		view.btnCancel.setVisible(false);
		
		view.cardNumber.setText("");
		view.ID.setText("");
	}
	
	//This method will take the written value of the Level Id and depending on it, it will add the description of the level
	private void levelDescription() {
		if(view.levelId.getText().equals("PR")) {
    		System.out.println("LevelId: " + view.levelId.getText());
    		view.level.setText("Premium");
    		System.out.println("Level Description: " + view.level.getText());
		}else {
			if(view.levelId.getText().equals("TV")) {
                		System.out.println("LevelId: " + view.levelId.getText());
                		view.level.setText("TV Lover");
                		System.out.println("Level Description: " + view.level.getText());
			}else {
				if(view.levelId.getText().equals("ML")) {
                		System.out.println("LevelId: " + view.levelId.getText());
                		view.level.setText("Music Lover");
                		System.out.println("Level Description: " + view.level.getText());
				}else {
					if(view.levelId.getText().equals("VL")) {
                		System.out.println("LevelId: " + view.levelId.getText());
                		view.level.setText("Video Lover");
                		System.out.println("Level Description: " + view.level.getText());
					}
				}
			}
		}
	}
}
