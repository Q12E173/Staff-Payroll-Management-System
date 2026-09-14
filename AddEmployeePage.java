import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.SimpleDateFormat;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import java.io.*;

public class AddEmployeePage extends JFrame {
    Color headerColor;
    JPanel headerPanel, formPanel, buttonPanel;
    JLabel titleLabel, empIdLabel, nameLabel, icLabel, dobLabel, genderLabel, positionLabel, dateOfHireLabel, typeEmployeeLabel;
    JTextField empIdField, nameField, icField, dobField, genderField;
    JComboBox<String> positionDropdown, typeEmployeeDropdown;
    JDateChooser dateOfHireChooser;
    JButton btnAdd, btnCancel;
    Backend backendSystem;
    String empID, empName, empIC, empPosition, empGender;
    Calendar dateOfBirth, dateOfHired;
    Employee newEmp = null;
    boolean edit;

    public AddEmployeePage(Backend b) {
        super("Add Employee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
	setVisible(true);	

        backendSystem = b;
	edit = false;

        setLayout(new BorderLayout());

        headerPanel = createHeaderPanel();
        formPanel = createFormPanel();
        buttonPanel = createButtonPanel();

        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);

        generateEmployeeID();

        icField.getDocument().addDocumentListener(new ICFieldDocumentListener());
    }

    public AddEmployeePage(Backend b, String ID) {
	super("Edit Employee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
	setVisible(true);

        backendSystem = b;
	edit = true;

        setLayout(new BorderLayout());

        headerPanel = createHeaderPanel();
        formPanel = createFormPanel();
        buttonPanel = createButtonPanel();

        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);

        icField.getDocument().addDocumentListener(new ICFieldDocumentListener());

	// Little customization for Edit Employee Page
	titleLabel.setText("EDIT EMPLOYEE");
	btnAdd.setText("Save");

	// Preset with existing information
	newEmp = backendSystem.employeeList.get(ID);
	empIdField.setText(newEmp.empID);
	nameField.setText(newEmp.empName);
	icField.setText(newEmp.empIC);
	dobField.setText(backendSystem.calendarToString(newEmp.dateOfBirth));
	genderField.setText(newEmp.empGender);
	
	positionDropdown.setSelectedItem(newEmp.empPosition);
	typeEmployeeDropdown.setSelectedItem(backendSystem.getEmpType(newEmp.fulltime));
	dateOfHireChooser.setCalendar(newEmp.dateOfHired);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());

        titleLabel = new JLabel("ADD EMPLOYEE", SwingConstants.CENTER);

        headerPanel.setBackground(new Color(0, 0, 102));

        Font headerFont = new Font("Segoe UI", Font.BOLD, 50);
        titleLabel.setFont(headerFont);
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 15));
        formPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        formPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);

        empIdLabel = new JLabel("Employee ID:");
        nameLabel = new JLabel("Name:");
        icLabel = new JLabel("IC Number:");
        dobLabel = new JLabel("Date of Birth:");
        genderLabel = new JLabel("Gender:");
        positionLabel = new JLabel("Position:");
        dateOfHireLabel = new JLabel("Date of Hire:");
        typeEmployeeLabel = new JLabel("Type of Employee:");

        empIdLabel.setFont(labelFont);
        nameLabel.setFont(labelFont);
        icLabel.setFont(labelFont);
        dobLabel.setFont(labelFont);
        genderLabel.setFont(labelFont);
        positionLabel.setFont(labelFont);
        dateOfHireLabel.setFont(labelFont);
        typeEmployeeLabel.setFont(labelFont);

        empIdField = new JTextField(15);
        nameField = new JTextField(15);
        icField = new JTextField(15);
        dobField = new JTextField(15);
        genderField = new JTextField(15);

        Vector<String> positionOptions = new Vector<String>();

        for (Position p : backendSystem.positionList) {
		
	    // Choice consists only positions with complete information
	    String status = p.getStatus();
	    if(status.equals("complete")){
		positionOptions.addElement(p.getPositionName());
	    }
            
        }

        positionDropdown = new JComboBox<>(positionOptions);
        String[] typeEmployeeOptions = {"Full Time", "Part Time"};
        dateOfHireChooser = new JDateChooser();
        typeEmployeeDropdown = new JComboBox<>(typeEmployeeOptions);

        formPanel.add(empIdLabel);
        formPanel.add(empIdField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(icLabel);
        formPanel.add(icField);
        formPanel.add(dobLabel);
        formPanel.add(dobField);
        formPanel.add(genderLabel);
        formPanel.add(genderField);
        formPanel.add(positionLabel);
        formPanel.add(positionDropdown);
        formPanel.add(dateOfHireLabel);
        formPanel.add(dateOfHireChooser);
        formPanel.add(typeEmployeeLabel);
        formPanel.add(typeEmployeeDropdown);

	// Auto generated field is not editable
	empIdField.setEditable(false);
        dobField.setEditable(false);
        genderField.setEditable(false);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        btnAdd = createStyledButton("Add");
        btnCancel = createStyledButton("Cancel");
        btnCancel.setBackground(Color.gray);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);

        ButtonHandler handler = new ButtonHandler();
        btnAdd.addActionListener(handler);
        btnCancel.addActionListener(handler);

        return buttonPanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setBackground(new Color(0, 0, 102));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void generateEmployeeID() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String empID = "EMP" + sdf.format(new Date());
        empIdField.setText(empID);
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnAdd) {
		try{
	                empID = empIdField.getText();
			empName = nameField.getText();
			empIC = icField.getText();
			empPosition =  (String) positionDropdown.getSelectedItem();
			dateOfHired = dateOfHireChooser.getCalendar();	
	                String empType = (String) typeEmployeeDropdown.getSelectedItem();

			backendSystem.saveEmployee(empID, empName, empIC, empPosition, dateOfHired, empType);
			
			setVisible(false);
			ListEmployeePage listEmployeePage = new ListEmployeePage(backendSystem);

			if(edit){
				JOptionPane.showMessageDialog(null, "Employee \"" + empName + "\" is edited successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(null, "Employee \"" + empName + "\" is added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(null, ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
                
                
            } else if (e.getSource() == btnCancel) {
                setVisible(false);
		ListEmployeePage listEmployeePage = new ListEmployeePage(backendSystem);
                if(edit){
			JOptionPane.showMessageDialog(null, "Edit Employee \"" + newEmp.empName + "\" : Process Cancelled!", "Edit Employee", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(null, "Add Employee: Process Cancelled!", "Add Employee", JOptionPane.INFORMATION_MESSAGE);
		}
            }
        }
    }

    private class ICFieldDocumentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateFields();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateFields();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateFields();
        }

        private void updateFields() {
            String empIC = icField.getText();
            if (empIC.length() == 12) {
		// Get employee's date of birth from IC
		try{
	                dateOfBirth = backendSystem.getEmpDOB(empIC);
			dobField.setText(backendSystem.calendarToString(dateOfBirth));

			// Get employee's gender from IC
	                empGender = backendSystem.getEmpGender(empIC);
	                genderField.setText(empGender);
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid IC: Length of IC is invalid!", JOptionPane.ERROR_MESSAGE);
		}
        

            }
        }
    }
}