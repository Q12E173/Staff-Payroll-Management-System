import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Dimension;

public class MainPage extends JFrame {
    private JButton adminButton, employeeButton;
    private JLabel usernameLabel, passwordLabel, titleLabel, loginLabel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    Container container;
    JPanel leftPanel, centerPanel, titlePanel, usernamePanel, passwordPanel, loginPanel, buttonPanel, rightPanel;
    Backend backendSystem;

    public MainPage() {
        super("Staff Payroll Management System");
	backendSystem = new Backend();
        container = getContentPane();
        container.setLayout(new GridLayout(1, 2)); // Split the frame into two main panels

        // Left Panel with BoxLayout for vertical stacking
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Creating a panel that will center its components
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Adding some vertical spacing above the title
        centerPanel.add(Box.createVerticalStrut(20)); // 20 pixels of vertical spacing

        // Title Label centered
        titleLabel = new JLabel("Staff Payroll Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segeo UI", Font.BOLD, 20)); // Set font for the title
        titleLabel.setForeground(new Color(0, 0, 0));
        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);

        // Username Panel centered with reduced spacing
        usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0)); // Reduced horizontal gap, minimal vertical gap
        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        // Password Panel centered with reduced spacing
        passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0)); // Similarly reduced gaps
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // Login Label at CENTER
        loginLabel = new JLabel("Login as", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
        loginLabel.setForeground(new Color(102, 102, 102));
        loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPanel.add(loginLabel);

        Dimension buttonSize = new Dimension(100, 30);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Adjust the horizontal gap as needed
        adminButton = new JButton("Admin");
        adminButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        adminButton.setBackground(new Color(0,0,102)); 
        adminButton.setForeground(Color.WHITE);
        employeeButton = new JButton("Employee");
        employeeButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        employeeButton.setBackground(new Color(0,0,102)); 
        employeeButton.setForeground(Color.WHITE);
        buttonPanel.add(adminButton);
        buttonPanel.add(employeeButton);
        adminButton.setPreferredSize(buttonSize);
        employeeButton.setPreferredSize(buttonSize);

        // Adding the title and other components to the centerPanel
        centerPanel.add(titlePanel); // Add the title with spacing above
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(usernamePanel);
        centerPanel.add(passwordPanel);
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(loginPanel);
        centerPanel.add(buttonPanel);
       

        leftPanel.add(centerPanel);
        container.add(leftPanel);

        // Right Panel with an image
        rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Use FlowLayout for simplicity
        ImageIcon image = new ImageIcon("Payroll_In_India_FeatureImage_02.png");
        rightPanel.add(new JLabel(image));
        container.add(rightPanel);

        ButtonHandler handler = new ButtonHandler();
        adminButton.addActionListener(handler);
        employeeButton.addActionListener(handler);

        setSize(790, 500); 
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[]) {
        MainPage app = new MainPage();
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == adminButton){
		if(backendSystem.verifyLogin(usernameField.getText(), passwordField.getText())){
			setVisible(false);
			AdminPage adminPage = new AdminPage(backendSystem);
		}  
		else{
			usernameField.setText("");
			passwordField.setText("");
			JOptionPane.showMessageDialog(null, "Invalid Admin Username or Password Entered!", "Admin Login", JOptionPane.WARNING_MESSAGE);
		}
	    }
	    else if(e.getSource() == employeeButton){
		String empID = usernameField.getText();
		String empPassword = passwordField.getText();
		try{
			Employee employee = backendSystem.employeeList.get(empID);
			if(empID.equals(employee.empID) && empPassword.equals(employee.empPassword)){
				setVisible(false);
				EmployeePage employeePage = new EmployeePage(backendSystem, employee);
			}
			else{
				throw new Exception("Invalid Employee Password Entered!");
			} 
		}
		catch(NullPointerException npe){
			usernameField.setText("");
			passwordField.setText("");
			JOptionPane.showMessageDialog(null, "Employee ID does not exists!", "Employee Login", JOptionPane.WARNING_MESSAGE);
		}
		catch(Exception ex){
			usernameField.setText("");
			passwordField.setText("");
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Employee Login", JOptionPane.WARNING_MESSAGE);
		}
		
		
	    }
        }
    }
}