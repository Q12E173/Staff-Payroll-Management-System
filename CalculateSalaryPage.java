import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class CalculateSalaryPage extends JFrame {
    Backend backendSystem;
    JPanel mainPanel, headerPanel, tablePanel, buttonPanel;
    JLabel headerLabel, chooseEmployeeLabel;
    JTable table;
    JScrollPane scrollPane;
    ImageIcon icon;
    JButton calculateButton, searchButton, resetButton, resetAllButton, backButton;
    int countOfUncalculatedSalary = 0;

    public CalculateSalaryPage(Backend b) {
        setTitle("Calculate Salary");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	backendSystem = b;

        // Main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(5, 5)); // Add some spacing

        // Header panel with label and icon
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0,0,102));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));

        icon = new ImageIcon("small.png");

        headerLabel = new JLabel("CALCULATE SALARY", icon, JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 30)); // Set the label font size bigger
        headerLabel.setForeground(Color.WHITE); // Set the label text color to white for contrast
        headerLabel.setHorizontalTextPosition(JLabel.RIGHT); // Position text to the right of the icon
        headerLabel.setVerticalTextPosition(JLabel.CENTER); // Center text vertically in line with the icon

        headerPanel.add(Box.createHorizontalGlue()); // Add space to the left of the label
        headerPanel.add(headerLabel);
        headerPanel.add(Box.createHorizontalGlue()); // Add space to the right of the label

        // Set the preferred height of the headerPanel
        headerPanel.setPreferredSize(new Dimension(800, 70));

        // Label "Choose Employee" directly above the table
        chooseEmployeeLabel = new JLabel("Choose Employee");
        chooseEmployeeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Increase the font size
        chooseEmployeeLabel.setHorizontalAlignment(JLabel.LEFT);

        // Table with specified columns
        String[] columnNames = {
                "ID", "Name", "Position", "Employee Type", "Salary Status"
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model){
        	private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
        		return false;               
		};
	};
        table.setFillsViewportHeight(true);

	// Add employees to the table model
	Enumeration<Employee> temp = backendSystem.employeeList.elements();
	while(temp.hasMoreElements()){
		Employee e = temp.nextElement();
		if(e.checkedSalary == false){
			countOfUncalculatedSalary++;
		}
		String[] column = {e.empID, e.empName, e.empPosition, backendSystem.getEmpType(e.fulltime), backendSystem.getSalaryStatus(e.checkedSalary)};
		model.addRow(column);
	}
	


        // Scroll pane for table
        scrollPane = new JScrollPane(table);

        // Panel for the label and table
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(chooseEmployeeLabel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel with "Calculate", "Edit", "Reset", "Back" buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center the buttons at the bottom
        calculateButton = new JButton("Calculate");
        searchButton = new JButton("Search");
        resetButton = new JButton("Reset");
	resetAllButton = new JButton("Reset All");
        backButton = new JButton("Back");

        // Set styles for Calculate, Edit, Reset, and Back buttons
        calculateButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        calculateButton.setBackground(new Color(0,0,102)); 
        calculateButton.setForeground(Color.WHITE);

        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        searchButton.setBackground(new Color(0,0,102)); 
        searchButton.setForeground(Color.WHITE);

        resetButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resetButton.setBackground(new Color(0,0,102)); 
        resetButton.setForeground(Color.WHITE);

        resetAllButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resetAllButton.setBackground(new Color(0,0,102)); 
        resetAllButton.setForeground(Color.WHITE);

        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);

        Dimension buttonSize = new Dimension(120, 40); // Adjust the dimensions

        calculateButton.setPreferredSize(buttonSize); // Set button size
        searchButton.setPreferredSize(buttonSize);
        resetButton.setPreferredSize(buttonSize);
	resetAllButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        buttonPanel.add(calculateButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(resetButton);
	buttonPanel.add(resetAllButton);
        buttonPanel.add(backButton);

        // Add action listeners
        ButtonHandler handler = new ButtonHandler();
        calculateButton.addActionListener(handler);
        searchButton.addActionListener(handler);
        resetButton.addActionListener(handler);
	resetAllButton.addActionListener(handler);
	backButton.addActionListener(handler);

        // Adding components to the main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Adding main panel to the frame
        getContentPane().add(mainPanel);

	setVisible(true);
	
	if(countOfUncalculatedSalary > 0){
		JOptionPane.showMessageDialog(null, countOfUncalculatedSalary + " employees' salary are not calculated!", "Uncalculated Salary", JOptionPane.WARNING_MESSAGE);
	}
	else{
		JOptionPane.showMessageDialog(null, "All employees' salary are calculated!", "Uncalculated Salary", JOptionPane.INFORMATION_MESSAGE);
	}
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == calculateButton) {

                // Get Employee
		int index = table.getSelectedRow();

		if(index == -1){
			JOptionPane.showMessageDialog(null, "No Employee is Selected!");
		}
		else{
			String empID = (String) table.getValueAt(index, 0);
			Employee employeeToBeCalculate = backendSystem.employeeList.get(empID);
	
			// Enter page accordingly
			if(employeeToBeCalculate.fulltime){
				setVisible(false);
				FulltimePage fulltimePage = new FulltimePage(backendSystem, employeeToBeCalculate);
			}
			else{
				setVisible(false);
				ParttimePage parttimePage = new ParttimePage(backendSystem, employeeToBeCalculate);
			}
		}		
		
            } else if (e.getSource() == searchButton) {
                // Handle Search button action
		backendSystem.searchAndSelect(table);
		
            } else if (e.getSource() == resetButton) {

		// Get the employee
		int index = table.getSelectedRow();

		if(index == -1){
			JOptionPane.showMessageDialog(null, "No Employee is Selected!");
		}
		else{
			String empID = (String) table.getValueAt(index, 0);
			Employee employeeToBeReset = backendSystem.employeeList.get(empID);
		
			// Ask confirmation
			int choice = JOptionPane.showConfirmDialog(null, "Reset Salary for: " + employeeToBeReset.empName + " ?", "Reset Salary", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if(choice == 0){
				// Reset Salary
				employeeToBeReset.reset();
	
				try{
			 		backendSystem.storeEmployeeList();
	
					// Update "Salary Status"
					employeeToBeReset = backendSystem.employeeList.get(empID);
					String salaryStatus = backendSystem.getSalaryStatus(employeeToBeReset.checkedSalary);
					table.setValueAt(salaryStatus, index, 4);
				}
				catch(IOException ioe){
					JOptionPane.showMessageDialog(null, ioe.getMessage(), "Store Employee List to File: Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		}			
		
            } else if (e.getSource() == resetAllButton) {
                
		int choice = JOptionPane.showConfirmDialog(null, "Reset Salary for all employees?", "Reset Salary", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if(choice == 0){
			// Reset salary for all employee
			for(int index = 0; index < table.getRowCount(); index++){
				String empID = (String) table.getValueAt(index, 0);
				Employee employeeToBeReset = backendSystem.employeeList.get(empID);
				employeeToBeReset.reset();
				String salaryStatus = backendSystem.getSalaryStatus(employeeToBeReset.checkedSalary);
				table.setValueAt(salaryStatus, index, 4);
			}
			
			// Store the changes permanently
			try{
		 		backendSystem.storeEmployeeList();
			}
			catch(IOException ioe){
				JOptionPane.showMessageDialog(null, ioe.getMessage(), "Store Employee List to File: Error", JOptionPane.WARNING_MESSAGE);
			}

			JOptionPane.showMessageDialog(null, "Salary of all employees are reset", "Reset Salary", JOptionPane.INFORMATION_MESSAGE);
		}

            }else if (e.getSource() == backButton) {

                setVisible(false);
		AdminPage adminPage = new AdminPage(backendSystem);
            }
        }
    }
}
