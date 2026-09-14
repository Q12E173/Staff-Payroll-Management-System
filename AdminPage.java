import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminPage extends JFrame{
   JPanel headerPanel,titlePanel,logoutPanel, panel;
   ImageIcon headerIcon;
   JLabel headerLabel;
   Font buttonFont;
   JButton btnLogout, btnListPosition, btnListEmployee, btnCalculateSalary, btnEditPassword;
   Backend backendSystem;
   
    public AdminPage(Backend b){
        // Create the frame.
        super("Admin Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700); 
	backendSystem = b;
        
        // Header panel with an image, a text label, and a logout button
        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout()); // use BorderLayout for headerPanel
	titlePanel = new JPanel();
	titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // panel for title and icon
        titlePanel.setBackground(Color.blue); // Set the background color to blue
        
	headerIcon = new ImageIcon("working.png"); // Load the image for the header
        headerLabel = new JLabel("ADMIN MENU", headerIcon, JLabel.LEFT); // set the icon at left hand side
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 60)); // Set the font type, style, and size
        headerLabel.setForeground(Color.WHITE); // Set the text color to white
        headerLabel.setHorizontalTextPosition(SwingConstants.RIGHT); //set the text at right hand side
        headerLabel.setVerticalTextPosition(SwingConstants.CENTER); //set the text at center
        
        titlePanel.add(headerLabel); // Add title and icon to titlePanel 
	headerPanel.add(titlePanel, BorderLayout.CENTER); // Add titlePanel to center of headerPanel
        
	// Create a separate panel for the logout button to make easily to control the size of logout button
	logoutPanel = new JPanel();
	logoutPanel.setLayout(new BorderLayout());
	logoutPanel.setOpaque(false); // Make it transparent
	
	// Logout button at the top right
	btnLogout = new JButton("X");
	btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
	btnLogout.setPreferredSize(new Dimension(60, 20)); // Width and height of the button
	btnLogout.setBackground(Color.RED);
	logoutPanel.add(btnLogout, BorderLayout.EAST); // Add logout button to the right side of logoutPanel
        
	headerPanel.add(logoutPanel, BorderLayout.NORTH);



        // Create the panel to hold buttons
        panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10)); // rows, cols, horizontalgap, verticalgap

	// Set button text size
        buttonFont = new Font("Segoe UI", Font.BOLD,18);

        // Create buttons with images
        btnListPosition = new JButton(new ImageIcon("jobs.png"));
        btnListEmployee = new JButton(new ImageIcon("listemployee.png"));
        btnCalculateSalary = new JButton(new ImageIcon("budget.png"));
        btnEditPassword = new JButton(new ImageIcon("pass.png"));

        // Set text and set font 
        btnListPosition.setText("List Position");
        btnListPosition.setFont(buttonFont);
        btnListEmployee.setText("List Employee");
        btnListEmployee.setFont(buttonFont);
        btnCalculateSalary.setText("Calculate Salary");
        btnCalculateSalary.setFont(buttonFont);
        btnEditPassword.setText("Edit Password");
        btnEditPassword.setFont(buttonFont);

        // Set button to have text under the icon
        btnListPosition.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnListPosition.setHorizontalTextPosition(SwingConstants.CENTER);
        btnListEmployee.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnListEmployee.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCalculateSalary.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnCalculateSalary.setHorizontalTextPosition(SwingConstants.CENTER);
        btnEditPassword.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnEditPassword.setHorizontalTextPosition(SwingConstants.CENTER);
        
        // Add buttons to panel
        panel.add(btnListPosition);
        panel.add(btnListEmployee);
        panel.add(btnCalculateSalary);
        panel.add(btnEditPassword);
   
	//Add action listener
	ButtonHandler handler = new ButtonHandler();
        btnListPosition.addActionListener(handler);
	btnListEmployee.addActionListener(handler);
	btnCalculateSalary.addActionListener(handler);
	btnEditPassword.addActionListener(handler);
	btnLogout.addActionListener(handler);

        // Add header panel to the frame at the top
        add(headerPanel, BorderLayout.NORTH);

        // Add panel to frame.
        add(panel, BorderLayout.CENTER);
        
        // Display the frame.
        setVisible(true);
    }

    

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == btnListPosition){
		setVisible(false);
		AdminPositionPage adminPositionPage = new AdminPositionPage(backendSystem);
	    }
            else if(e.getSource() == btnListEmployee){
		setVisible(false);
		ListEmployeePage listPositionPage = new ListEmployeePage(backendSystem);
	    }
            else if(e.getSource() == btnCalculateSalary){
		setVisible(false);
		CalculateSalaryPage calculateSalaryPage = new CalculateSalaryPage(backendSystem);
	    }
	    else if(e.getSource() == btnLogout){
		setVisible(false);
		MainPage mainPage = new MainPage();
	    }
	    else if(e.getSource()==btnEditPassword){
		EditPasswordPage editPasswordPage = new EditPasswordPage(backendSystem);
	    }

        }
    }
}

