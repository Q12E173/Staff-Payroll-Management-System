import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AdminPositionPage extends JFrame{
	JComboBox positionSelection;
	Backend backendSystem;
	JLabel headerLabel, titleLabel, nameLabel, emptyLabel, allowanceLabel, salaryLabel, overtimeLabel, deductionLabel, hourlyLabel;
	JPanel mainPanel,headerPanel,centerContainer,DropdownPanel,detailsPanel,FullPartPanel, fullTimePanel,salaryPanel,overtimePanel,deductPanel,partTimePanel,hourlyPanel,buttonPanel;
	ImageIcon icon;
	TitledBorder tbf,tbp;
	JButton btnSave, btnBack, btnAdd, btnDelete;
	JTextField nameField, allowanceField, salaryField, overtimeField, deductionField, hourlyField;
	Position position;
	String positionName, basicSalary, allowance, overtimeRate, hourlyRate, deductionRate;


	public AdminPositionPage(Backend b){
		super("List Position");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 700);
	        
		backendSystem = b;

		// Main panel
        	mainPanel = new JPanel();
        	mainPanel.setLayout(new BorderLayout(5, 5)); // Add some spacing


		// Header panel with label and icon
        	headerPanel = new JPanel();
       		headerPanel.setBackground(new Color(0,0,102));
        	headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));

        	icon = new ImageIcon("position small.png");

        	headerLabel = new JLabel("POSITION", icon, JLabel.CENTER);
        	headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 30)); // Set the label font size bigger
        	headerLabel.setForeground(Color.WHITE); // Set the label text color to white for contrast
        	headerLabel.setHorizontalTextPosition(JLabel.RIGHT); // Position text to the right of the icon
        	headerLabel.setVerticalTextPosition(JLabel.CENTER); // Center text vertically in line with the icon

       		headerPanel.add(Box.createHorizontalGlue()); // Add space to the left of the label
       		headerPanel.add(headerLabel);
       		headerPanel.add(Box.createHorizontalGlue()); // Add space to the right of the label

       		// Set the preferred height of the headerPanel
       		headerPanel.setPreferredSize(new Dimension(900, 70));
		
		// Panel 2 for DropdownPanel
		DropdownPanel = new JPanel();
		DropdownPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		DropdownPanel.setBackground(Color.WHITE);
		DropdownPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Adjust the top and bottom padding as needed


		Vector<String> tempList = new Vector<String>();
		for(Position p : backendSystem.positionList){
			tempList.addElement(p.getPositionName() + " (" + p.getStatus() + ") ");
		}
		positionSelection = new JComboBox(tempList);

		titleLabel = new JLabel("Position");
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
		DropdownPanel.add(titleLabel);
		DropdownPanel.add(positionSelection);
		

		// Panel 3 for Details Panel
		detailsPanel = new JPanel();
        		detailsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
 
		//Position Name
		nameLabel = new JLabel("Position Name:         ");
		nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
		nameField = new JTextField(20);
		nameField.setFont(new Font("Arial", Font.PLAIN, 13)); 
		detailsPanel.add(nameLabel);
		detailsPanel.add(nameField);
		
		//Empty
		emptyLabel = new JLabel("                                                ");
		detailsPanel.add(emptyLabel);

		//Allowance
		allowanceLabel = new JLabel("Allowance:            RM");
		allowanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
		allowanceField = new JTextField(15);
		allowanceField.setFont(new Font("Arial", Font.PLAIN, 13)); 
		detailsPanel.add(allowanceLabel);
		detailsPanel.add(allowanceField);

		detailsPanel.setBackground(Color.WHITE);
		detailsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Adjust the top and bottom padding as needed


		// Panel 4 for Full Time and Part Time
        	FullPartPanel = new JPanel();
        	FullPartPanel.setLayout(new GridLayout(1, 2));
		FullPartPanel.setBackground(Color.WHITE);
		FullPartPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Adjust the top and bottom padding as needed


		// Full Time Panel
		fullTimePanel = new JPanel();
		fullTimePanel.setLayout(new BoxLayout(fullTimePanel, BoxLayout.Y_AXIS));
		tbf = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 102)), "Full Time");
		tbf.setTitleFont(new Font("Segoe UI", Font.BOLD, 18));
		fullTimePanel.setBorder(tbf);
		fullTimePanel.setBackground(Color.WHITE);

		// Inside Full Time Panel has Basic Salary Panel, Overtime Rate Panel, and Deduction Rate Panel
		salaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		salaryLabel = new JLabel("Basic Salary       : RM");
		salaryLabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
        	salaryField = new JTextField(17);
		salaryField.setFont(new Font("Arial", Font.PLAIN, 13)); 
		salaryPanel.add(salaryLabel);
		salaryPanel.add(salaryField);
		salaryPanel.setBackground(Color.WHITE); 

		overtimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		overtimeLabel = new JLabel("Overtime Rate   : RM");
		overtimeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
        	overtimeField = new JTextField(17);
		overtimeField.setFont(new Font("Arial", Font.PLAIN, 13)); 
		overtimePanel.add(overtimeLabel);
		overtimePanel.add(overtimeField);
		overtimePanel.setBackground(Color.WHITE); 

		deductPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		deductionLabel = new JLabel("Deduction Rate : RM");
		deductionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
       		deductionField = new JTextField(17);
		deductionField.setFont(new Font("Arial", Font.PLAIN, 13)); 
		deductPanel.add(deductionLabel);
		deductPanel.add(deductionField);
		deductPanel.setBackground(Color.WHITE); 

		// Add Basic Salary Panel, Overtime Rate Panel, and Deduction RatePanel into Full Time panel
       		fullTimePanel.add(salaryPanel);
       		fullTimePanel.add(overtimePanel);
       		fullTimePanel.add(deductPanel);


		// Part Time Panel
		partTimePanel = new JPanel();
		partTimePanel.setLayout(new BoxLayout(partTimePanel, BoxLayout.Y_AXIS));
		tbp = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 102)), "Part Time");
		tbp.setTitleFont(new Font("Segoe UI", Font.BOLD, 18));
		partTimePanel.setBorder(tbp);
		partTimePanel.setBackground(Color.WHITE);

		// Inside Part Time Panel has Hourly Rate Panel
		hourlyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		hourlyLabel = new JLabel("Hourly Rate        : RM");
		hourlyLabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
        	hourlyField = new JTextField(17);
		hourlyField.setFont(new Font("Arial", Font.PLAIN, 13)); 
		hourlyPanel.add(hourlyLabel);
		hourlyPanel.add(hourlyField);
		hourlyPanel.setBackground(Color.WHITE); 


		// Add Hourly RatePanel into Part Time panel
        	partTimePanel.add(hourlyPanel);

		
		// Add Full Time panel and Part Time panel into FullPart panel
        	FullPartPanel.add(fullTimePanel);
        	FullPartPanel.add(partTimePanel);
		
		buttonPanel = new JPanel();
        	buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10)); 
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Adjust the top and bottom padding as needed

		btnSave = new JButton("Save");
		btnSave.setBackground(new Color(0,0,102));
		btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnSave.setForeground(Color.WHITE);
		btnAdd = new JButton("Add");
		btnAdd.setBackground(new Color(0,0,102));
		btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnAdd.setForeground(Color.WHITE);
		btnDelete = new JButton("Delete");
		btnDelete.setBackground(new Color(0,0,102));
		btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnDelete.setForeground(Color.WHITE);
		btnBack = new JButton("Back");
		btnBack.setBackground(Color.GRAY);
		btnBack.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnBack.setForeground(Color.WHITE);

		buttonPanel.add(btnAdd);
		buttonPanel.add(btnSave);
		buttonPanel.add(btnDelete);
		buttonPanel.add(btnBack);

		// Add action listeners
		ButtonHandler handler = new ButtonHandler();
        	btnSave.addActionListener(handler);
		btnAdd.addActionListener(handler);
		btnDelete.addActionListener(handler);
		btnBack.addActionListener(handler);
		positionSelection.addActionListener(handler);

		// Create a container for the panels in the center
		JPanel centerContainer = new JPanel();
		centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.Y_AXIS));

		
		// Add the panels to the center container
		centerContainer.add(DropdownPanel);
		centerContainer.add(detailsPanel);
		centerContainer.add(FullPartPanel);
		centerContainer.add(buttonPanel);


		// Adding components to the main panel
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(centerContainer, BorderLayout.CENTER); // Add the center container


		// Adding main panel to the frame
       		getContentPane().add(mainPanel);
		if(backendSystem.positionList.size() != 0){
			positionSelection.setSelectedIndex(0);
		}
		setVisible(true);
	}

	private void updateFields(Position position){
		nameField.setText(position.getPositionName());
		allowanceField.setText(backendSystem.numberDisplayFormat(position.getAllowance()));
		salaryField.setText(backendSystem.numberDisplayFormat(position.getBasicSalary()));
		overtimeField.setText(backendSystem.numberDisplayFormat(position.getOvertimeRate()));
		deductionField.setText(backendSystem.numberDisplayFormat(position.getDeductionRate()));
		hourlyField.setText(backendSystem.numberDisplayFormat(position.getHourlyRate()));
	}


    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == positionSelection){
		int index = positionSelection.getSelectedIndex();
		if(index == -1){
			return;
		}
		position = backendSystem.positionList.elementAt(index);
				
		updateFields(position);
	    }
	    else if(e.getSource() == btnSave){
		try{
			int index = positionSelection.getSelectedIndex();
			if(index == -1){
				throw new Exception("Please press add button before adding new position");
			}
			
			// Get all information in text format		
			positionName = nameField.getText();
			basicSalary = salaryField.getText();
			allowance = allowanceField.getText();
			overtimeRate = overtimeField.getText();
			hourlyRate = hourlyField.getText();
			deductionRate = deductionField.getText();

			// Save position information
			backendSystem.savePosition(index, positionName, basicSalary, allowance, overtimeRate, hourlyRate, deductionRate);

			// Get the position from the list
			position = backendSystem.positionList.elementAt(index);

			//Update the combo box list
			positionSelection.insertItemAt(position.getPositionName() + " (" + position.getStatus() + ") ", index);
			positionSelection.removeItemAt(index + 1);
			positionSelection.setSelectedIndex(index);			

			JOptionPane.showMessageDialog(null, "Position's information is saved!");
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(null, ioe.getMessage(), "Store Position List to File: Error", JOptionPane.WARNING_MESSAGE);
		}
		catch (Exception ex){
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Save Position Information: Error", JOptionPane.ERROR_MESSAGE);
		}
				
	    }
	    else if(e.getSource() == btnAdd){
		try{
			//Create empty position
			position = new Position();

			//Create empty position into position list
			backendSystem.addEmptyPosition();

			//Update the combo box list
			int index = backendSystem.positionList.size() - 1;
			positionSelection.addItem(position.getPositionName() + " (" + position.getStatus() + ") ");
			positionSelection.setSelectedIndex(index);

		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(null, ioe.getMessage(), "Store Position List to File: Error", JOptionPane.WARNING_MESSAGE);
		}
	    }
	    else if(e.getSource() == btnDelete){
		try{
			positionName = position.getPositionName();
			int choice = JOptionPane.showConfirmDialog(null, "Delete Position: " + positionName + " ?", "Delete Positon", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if(choice == 0){
			
				// Get index to delete from vector and choice, then store it
				int index = positionSelection.getSelectedIndex();
				backendSystem.removePosition(index);
				positionSelection.removeItemAt(index);

				// By default, after remove from the combo box, the selected index minus 1
				// So unexpected outcome occured for index 0
				if(index == 0){
					if(backendSystem.positionList.size() == 0){
						positionSelection.setSelectedIndex(-1);
					}
					else{
						positionSelection.setSelectedIndex(0);
					}
				}
				
				JOptionPane.showMessageDialog(null, "Delete Position \"" + positionName + "\" Successfully!", "Delete Positon", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(null, "Delete Position \"" + positionName + "\" Cancelled!", "Delete Positon", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(null, ioe.getMessage(), "Store Position List to File: Error", JOptionPane.WARNING_MESSAGE);
		}
		catch(ArrayIndexOutOfBoundsException ae){
			JOptionPane.showMessageDialog(null, "No position left, please add new position", "Position List", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(NullPointerException npe){
			JOptionPane.showMessageDialog(null, "No position is selected", "Delete Position: Error", JOptionPane.WARNING_MESSAGE);
		}
		
	    }
	    else if(e.getSource() == btnBack){
		setVisible(false);
		AdminPage adminPage = new AdminPage(backendSystem);
	    }
        }
    }

		
}
