import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileFilter;

public class EmployeePage extends JFrame{
   JPanel headerPanel,titlePanel,logoutPanel, panel;
   ImageIcon headerIcon;
   JLabel headerLabel;
   Font buttonFont;
   JButton btnLogout, btnCheckSlip, btnPassword;
   Backend backendSystem;
   JFileChooser fileChooser;
   Employee employee;
   
    public EmployeePage(Backend b, Employee e){
        // Create the frame.
        super("Employee Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700); 
	backendSystem = b;
	employee = e;
        
        // Header panel with an image, a text label, and a logout button
        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout()); // use BorderLayout for headerPanel
	titlePanel = new JPanel();
	titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // panel for title and icon
        titlePanel.setBackground(Color.BLUE); // Set the background color to blue
        
	headerIcon = new ImageIcon("recruitment.png"); // Load the image for the header
        headerLabel = new JLabel("EMPLOYEE MENU", headerIcon, JLabel.LEFT); // set the icon at left hand side
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
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100)); 

        // Set button text size
        buttonFont = new Font("Segoe UI", Font.BOLD,18);

        // Create buttons with images
        btnCheckSlip = new JButton(new ImageIcon("payment.png"));
        btnPassword = new JButton(new ImageIcon("password.png"));

        // Set text and set font 
        btnCheckSlip.setText("Check Slip");
        btnCheckSlip.setFont(buttonFont);
        btnPassword.setText("Edit Password");
        btnPassword.setFont(buttonFont);

        // Set button to have text under the icon
        btnCheckSlip = new JButton(new ImageIcon(getClass().getResource("payment.png")));
        btnCheckSlip.setText("Check Slip");
        btnCheckSlip.setFont(buttonFont);
        btnCheckSlip.setPreferredSize(new Dimension(390, 340)); // Square button size
        btnCheckSlip.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnCheckSlip.setHorizontalTextPosition(SwingConstants.CENTER);

        btnPassword = new JButton(new ImageIcon(getClass().getResource("password.png")));
        btnPassword.setText("Edit Password");
        btnPassword.setFont(buttonFont);
        btnPassword.setPreferredSize(new Dimension(390, 340)); // Square button size
        btnPassword.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnPassword.setHorizontalTextPosition(SwingConstants.CENTER);
        
        // Add buttons to panel
        panel.add(btnCheckSlip);
        panel.add(btnPassword);
   
	//Add action listener
	ButtonHandler handler = new ButtonHandler();
        	btnCheckSlip.addActionListener(handler);
	btnPassword.addActionListener(handler);
	btnLogout.addActionListener(handler);

        // Add header panel to the frame at the top
        add(headerPanel, BorderLayout.NORTH);

        // Add panel to frame.
        add(panel, BorderLayout.CENTER);
        
        // Display the frame.
        setVisible(true);

	checkFirstLogin();
    }

    private void checkFirstLogin(){
	if(employee.empPassword.equals("")){
		String newPassword = "";

		do{
			try{
				newPassword = backendSystem.getNewPassword();
				backendSystem.setPassword(newPassword, employee.empID);
			}
			catch (Exception ex){
				if(ex.getMessage().equals("Password change cancelled!")){
					JOptionPane.showMessageDialog(null, "First login must set a password", "Change Password", JOptionPane.WARNING_MESSAGE);	
				}
				else{
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Change Password: Error", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		}while(newPassword.isEmpty());
	}
    }

    private void openPaySlip(){
	fileChooser = new JFileChooser(backendSystem.slipDir);
	fileChooser.setDialogTitle("Open Salary Slip");
	fileChooser.setFileFilter(new FileFilter(){
		public boolean accept(File dir){
			return dir.getName().endsWith(employee.empID + ".pdf");
		}

		public String getDescription(){
			return ".pdf";
		}
	});

	int userSelection = fileChooser.showSaveDialog(this);

	if (userSelection == JFileChooser.APPROVE_OPTION) {
		String filePath = fileChooser.getSelectedFile().getAbsolutePath();
		if(Desktop.isDesktopSupported()){
			try{
				File paySlip = new File(filePath);
				
				// Open the pay slip using PDF viewer from the machine
				Desktop.getDesktop().open(paySlip);
			}
			catch(IOException ex){
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Open Salary Slip: Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
    }
    

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == btnCheckSlip){
		
		openPaySlip();
		
	    }
	    else if(e.getSource() == btnLogout){
		setVisible(false);
		MainPage mainPage = new MainPage();
	    }
	    else if(e.getSource()==btnPassword){
		EditPasswordPage editPasswordPage = new EditPasswordPage(backendSystem, employee);
	    }

        }

    }
}

