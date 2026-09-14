import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;

public class ListEmployeePage extends JFrame {
    JPanel mainPanel, buttonPanel;
    JLabel headerLabel;
    JScrollPane scrollPane;
    TitledBorder tableBorder;
    JTable employeeTable;
    JButton btnAdd, btnEdit, btnDelete, btnSearch, btnBack;
    Backend backendSystem;

    public ListEmployeePage(Backend b) {
        super("List Employees");
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.backendSystem = b;

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(5, 5));
        mainPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 0, 102));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));

        ImageIcon originalIcon = new ImageIcon("listemployee.png");
        Image resizedImage = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(resizedImage);

        JLabel headerLabel = new JLabel("EMPLOYEE LIST", icon, JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalTextPosition(JLabel.RIGHT);
        headerLabel.setVerticalTextPosition(JLabel.CENTER);

        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(headerLabel);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.setPreferredSize(new Dimension(800, 70));


        // Create a table model with column names
        String[] columnNames = {"ID", "Name", "IC", "Position", "Gender", "Date of Birth", "Date of Hire", "Employee Type"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Add employees to the table model
        Enumeration<Employee> temp = backendSystem.employeeList.elements();
	while(temp.hasMoreElements()){
		Employee e = temp.nextElement();
		String[] column = {e.empID, e.empName, e.empIC, e.empPosition, e.empGender, backendSystem.calendarToString(e.dateOfBirth), backendSystem.calendarToString(e.dateOfHired), backendSystem.getEmpType(e.fulltime)};
		tableModel.addRow(column);
	}

        // Create a table with the model, and set isCellEditable to false
        employeeTable = new JTable(tableModel){
            private static final long serialVersionUID = 1L;
        
            public boolean isCellEditable(int row, int column) {                
                return false;               
            }
        };


        JScrollPane scrollPane = new JScrollPane(employeeTable);


        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAdd = createStyledButton("Add");
        btnEdit = createStyledButton("Edit");
        btnDelete = createStyledButton("Delete");
	btnSearch = createStyledButton("Search");
        btnBack = createStyledButton("Back");
        btnBack.setBackground(Color.gray);

        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
	buttonPanel.add(btnSearch);
        buttonPanel.add(btnBack);

        ButtonHandler handler = new ButtonHandler();
        btnAdd.addActionListener(handler);
        btnEdit.addActionListener(handler);
        btnDelete.addActionListener(handler);
	btnSearch.addActionListener(handler);
        btnBack.addActionListener(handler);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(0, 0, 102)); 
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); 
        return button;
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Implement actions for Add, Edit, Delete, Search, Back buttons here
            // For example, you can open new frames or dialogs for these operations
            // and interact with the backendSystem to make necessary changes.

            if (e.getSource() == btnAdd) {

                // Handle Add button action
		setVisible(false);
		AddEmployeePage addEmployeePage = new AddEmployeePage(backendSystem);

            } else if (e.getSource() == btnEdit) {

                // Handle Edit button action

		// Get the index of selected row
                int index = employeeTable.getSelectedRow();
		if(index == -1){
			JOptionPane.showMessageDialog(null, "No Employee is Selected!");
		}
		else{

			setVisible(false);
			String key = (String) employeeTable.getValueAt(index, 0);
			AddEmployeePage addEmployeePage = new AddEmployeePage(backendSystem, key);
		}

            } else if (e.getSource() == btnDelete) {

		// Handle Delete button action

		// Get the index of selected row
                int index = employeeTable.getSelectedRow();
		if(index == -1){
			JOptionPane.showMessageDialog(null, "No Employee is Selected!");
		}
		else{
			// Get selected Employee ID and Employee Name
			String name = (String) employeeTable.getValueAt(index, 1);
			String key = (String) employeeTable.getValueAt(index, 0);

			// Ask for confirmation
			int input = JOptionPane.showConfirmDialog(null, "Delete Employee: " + name + " ?", "Delete Employee", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if(input == 0){

				backendSystem.removeEmployee(key);

				//Remove employee from Employee List
				((DefaultTableModel)employeeTable.getModel()).removeRow(index);

				JOptionPane.showMessageDialog(null, "Employee \"" + name + "\" is deleted!");
				
			}
			else{
				JOptionPane.showMessageDialog(null, "Deletion of Employee \"" + name + "\" is cancelled!");
			}
		}

            } else if (e.getSource() == btnSearch) {

                // Handle Search button action
		
		backendSystem.searchAndSelect(employeeTable);
		
            } else if (e.getSource() == btnBack) {

                // Handle Back button action

                setVisible(false);

                // Navigate back to the AdminPage or the previous page
		AdminPage adminPage = new AdminPage(backendSystem);

            }
        }
    }
}