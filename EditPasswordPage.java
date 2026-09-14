import javax.swing.*;
import java.awt.*;

public class EditPasswordPage {
	Backend backendSystem;
	String currentPassword, newPassword;
	JPanel currentPasswordPanel;
	JLabel label;
	JPasswordField currentPasswordField;
	int choice;
	Employee employee;

	
	
	public EditPasswordPage(Backend b){
		backendSystem = b;
		employee = null;
		
		// GUI Component for first dialog 
		label = new JLabel("Enter Current Password:");
		currentPasswordField = new JPasswordField(20);

		currentPasswordPanel = new JPanel(new GridLayout(2, 1, 10, 0));
		currentPasswordPanel.add(label);
		currentPasswordPanel.add(currentPasswordField);

		editPassword();
	}

	public EditPasswordPage(Backend b, Employee e){
		backendSystem = b;
		employee = e;
		
		// GUI Component for first dialog 
		label = new JLabel("Enter Current Password:");
		currentPasswordField = new JPasswordField(20);

		currentPasswordPanel = new JPanel(new GridLayout(2, 1, 10, 0));
		currentPasswordPanel.add(label);
		currentPasswordPanel.add(currentPasswordField);

		editPassword(employee);
	}

	public void editPassword(){
		choice = JOptionPane.showConfirmDialog(null, currentPasswordPanel, "Verify Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(choice == 0){
			currentPassword = currentPasswordField.getText();
			if (backendSystem.verifyLogin(currentPassword)){

				try{
					newPassword = backendSystem.getNewPassword();
					backendSystem.setPassword(newPassword);
				}
				catch (Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Cancelled", JOptionPane.WARNING_MESSAGE);
				}
				
				
			} else {
				 JOptionPane.showMessageDialog(null, "Invalid Password Entered!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void editPassword(Employee employee){
		choice = JOptionPane.showConfirmDialog(null, currentPasswordPanel, "Verify Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(choice == 0){
			currentPassword = currentPasswordField.getText();
			if (currentPassword.equals(employee.empPassword)){

				try{
					newPassword = backendSystem.getNewPassword();
					backendSystem.setPassword(newPassword, employee.empID);
				}
				catch (Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Cancelled", JOptionPane.WARNING_MESSAGE);
				}
				
			} else {
				 JOptionPane.showMessageDialog(null, "Invalid Password Entered!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}