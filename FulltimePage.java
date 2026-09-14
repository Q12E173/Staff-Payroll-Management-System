import com.itextpdf.text.DocumentException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;


public class FulltimePage extends JFrame {
	Color headerColor,totalColor;
	JPanel headerPanel,detailsPanel,addDeductPanel, addPanel,allowancePanel,overtimePanel,bonusPanel,deductPanel,socsoPanel,epfPanel,eisPanel,leavePanel,bottomPanel,totalPanel,buttonPanel;
	JLabel title,employeeIDLabel, positionLabel,employeeNameLabel,basicSalaryLabel,allowanceLabel,overtimeLabel,overtimeMultiplyLabel,overtimeEqualLabel,bonusLabel,socsoLabel,epfLabel,eisLabel,leaveLabel,leaveMultiplyLabel,leaveEqualLabel,totalLabel;
	JTextField employeeIDField,positionField,employeeNameField,basicSalaryField,allowanceField,overtimeField,overtimeRateField,overtimeTotalField,bonusField,socsoField,epfField,eisField,leaveField,leaveDeductionRateField,leaveTotalField,totalField;
	Font headerFont, labelFont,textfieldFont,inputtextfieldFont;
	TitledBorder tba,tbd;
	JButton saveNcalculateButton, printButton, backButton;
	Backend backendSystem;
	fullTimeEmployee employee;
	Position empPosition;
	double basicSalary, allowance, overtimeRate, hourlyRate, deductionRate;

    
    public FulltimePage(Backend b, Employee e) {
        super("Full Time Employee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(900, 700);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	backendSystem = b;

        // Header Panel
        headerPanel = new JPanel();
        title = new JLabel("Calculate Salary", SwingConstants.CENTER);

	// Set header panel background
	headerColor = new Color(0,0,102);

	// Set header font type
	headerFont = new Font("Segoe UI", Font.BOLD, 50);
	title.setFont(headerFont);
	title.setForeground(Color.WHITE);

	headerPanel.setBackground(headerColor);
	headerPanel.add(title);
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(headerPanel);
	
	//add gap between panel
	add(Gap(50));
	
	// label font
	labelFont = new Font("Segoe UI", Font.BOLD, 15);

	// textfield font
	textfieldFont = new Font("Arial", Font.PLAIN + Font.ITALIC, 13);
	
	// inputtextfield font;
	inputtextfieldFont = new Font("Arial", Font.PLAIN, 13);

        // Panel 2 for Employee Details
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(2, 4, 30, 10));
	
	// employeeID
        employeeIDLabel = new JLabel("Employee ID        :");
	employeeIDLabel.setFont(labelFont); 
        employeeIDField = new JTextField(24);
	employeeIDField.setEditable(false); // Make the field non-editable
	employeeIDField.setFont(textfieldFont);
	detailsPanel.add(employeeIDLabel);
	detailsPanel.add(employeeIDField);

	// Position
	positionLabel = new JLabel("Position                 :");
	positionLabel.setFont(labelFont);
 	positionField = new JTextField(24);
	positionField.setEditable(false); // Make the field non-editable
	positionField.setFont(textfieldFont);
	detailsPanel.add(positionLabel);
	detailsPanel.add(positionField);
 
	// employeeName
	employeeNameLabel = new JLabel("Employee Name  :");
	employeeNameLabel.setFont(labelFont);
	employeeNameField = new JTextField(24);
	employeeNameField.setEditable(false); // Make the field non-editable
	employeeNameField.setFont(textfieldFont);
	detailsPanel.add(employeeNameLabel);
	detailsPanel.add(employeeNameField);

	// basic salary
	basicSalaryLabel = new JLabel("Basic Salary(RM)  :");
	basicSalaryLabel.setFont(labelFont);
        basicSalaryField = new JTextField(24);
	basicSalaryField.setEditable(false); // Make the field non-editable
	basicSalaryField.setFont(textfieldFont); 
	detailsPanel.add(basicSalaryLabel);
	detailsPanel.add(basicSalaryField);

	detailsPanel.setBackground(Color.WHITE);
        add(detailsPanel);
	add(Gap(50));


        // Panel 3 for Addition and Deduction
        addDeductPanel = new JPanel();
        addDeductPanel.setLayout(new GridLayout(1, 2));
	addDeductPanel.setBackground(Color.WHITE);

        // Addition Panel
        addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
	tba = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(headerColor), "+Addition");
	tba.setTitleFont(new Font("Segoe UI", Font.BOLD, 18));
	addPanel.setBorder(tba);
	addPanel.setBackground(Color.WHITE); 

	// Inside Additon Panel has Allowance Panel, Overtime Panel, and Bous Panel
        allowancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        allowanceLabel = new JLabel("Allowance   : RM");
	allowanceLabel.setFont(labelFont);
	allowanceField = new JTextField(12);
        allowanceField.setEditable(false); // Make the field non-editable
	allowanceField.setFont(textfieldFont);
	allowancePanel.add(allowanceLabel);
	allowancePanel.add(allowanceField);
	allowancePanel.setBackground(Color.WHITE); 

        overtimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	overtimeLabel = new JLabel("Overtime     :");
	overtimeLabel.setFont(labelFont);
	overtimeField = new JTextField(5);
	overtimeField.setFont(inputtextfieldFont);
	overtimeMultiplyLabel = new JLabel("* RM");
	overtimeMultiplyLabel.setFont(labelFont);
        overtimeRateField = new JTextField(5);
        overtimeRateField.setEditable(false); // Make the field non-editable
	overtimeRateField.setFont(textfieldFont);
        overtimeEqualLabel = new JLabel("= RM");
      	overtimeEqualLabel.setFont(labelFont);
	overtimeTotalField = new JTextField(10);
        overtimeTotalField.setEditable(false); // Make the field non-editable
	overtimeTotalField.setFont(textfieldFont);
	overtimePanel.add(overtimeLabel);
	overtimePanel.add(overtimeField);
	overtimePanel.add(overtimeMultiplyLabel);
	overtimePanel.add(overtimeRateField);
	overtimePanel.add(overtimeEqualLabel);
	overtimePanel.add(overtimeTotalField);
	overtimePanel.setBackground(Color.WHITE); 

        bonusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	bonusLabel = new JLabel("Bonus           : RM");
	bonusLabel.setFont(labelFont);
        bonusField = new JTextField(12);
	bonusField.setFont(inputtextfieldFont);
	bonusPanel.add(bonusLabel);
	bonusPanel.add(bonusField);
	bonusPanel.setBackground(Color.WHITE); 


	// Add allowancePanel,overtime Panel and bonus panel into addition panel
        addPanel.add(allowancePanel);
        addPanel.add(overtimePanel);
        addPanel.add(bonusPanel);


        // Deduction Panel
	deductPanel = new JPanel();
        deductPanel.setLayout(new BoxLayout(deductPanel, BoxLayout.Y_AXIS));
        tbd = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(headerColor), "-Deduction");
	tbd.setTitleFont(new Font("Segoe UI", Font.BOLD, 18));
	deductPanel.setBorder(tbd);
	deductPanel.setBackground(Color.WHITE); 

	// Inside Deduction Panel has Socso Panel, Efp Panel, Eis Panel and Leave Panel
        socsoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	socsoLabel = new JLabel("SOCSO   : RM");
	socsoLabel.setFont(labelFont);
        socsoField = new JTextField(12);
        socsoField.setEditable(false); // Make the field non-editable
	socsoField.setFont(textfieldFont);
       	socsoPanel.add(socsoLabel);
	socsoPanel.add(socsoField);
	socsoPanel.setBackground(Color.WHITE); 
	
        epfPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	epfLabel = new JLabel("EPF         : RM");
	epfLabel.setFont(labelFont);
        epfField = new JTextField(12);
        epfField.setEditable(false); // Make the field non-editable
	epfField.setFont(textfieldFont);
       	epfPanel.add(epfLabel);
	epfPanel.add(epfField);
	epfPanel.setBackground(Color.WHITE); 

        eisPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	eisLabel = new JLabel("EIS          : RM");
	eisLabel.setFont(labelFont);
	eisField = new JTextField(12);
        eisField.setEditable(false); // Make the field non-editable
	eisField.setFont(textfieldFont);
       	eisPanel.add(eisLabel);
	eisPanel.add(eisField);
	eisPanel.setBackground(Color.WHITE); 
     
        leavePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	leaveLabel = new JLabel("Leave     :");
	leaveLabel.setFont(labelFont);
        leaveField = new JTextField(5);
	leaveField.setFont(inputtextfieldFont);
        leaveMultiplyLabel = new JLabel("* RM");
        leaveMultiplyLabel.setFont(labelFont);
	leaveDeductionRateField = new JTextField(5);
	leaveDeductionRateField.setEditable(false); // Make the field non-editable
	leaveDeductionRateField.setFont(textfieldFont);
	leaveEqualLabel = new JLabel("= RM");
	leaveEqualLabel.setFont(labelFont);
      	leaveTotalField = new JTextField(10);
        leaveTotalField.setEditable(false); // Make the field non-editable
	leaveTotalField.setFont(textfieldFont);
	leavePanel.add(leaveLabel);
     	leavePanel.add(leaveField);
	leavePanel.add(leaveMultiplyLabel);
	leavePanel.add(leaveDeductionRateField);
	leavePanel.add(leaveEqualLabel);
	leavePanel.add(leaveTotalField);
	leavePanel.setBackground(Color.WHITE); 
	

	// Add socsoPanel,epfPanel, eisPanel, and leavePanel into deduction panel
        deductPanel.add(socsoPanel);
        deductPanel.add(epfPanel);
        deductPanel.add(eisPanel);
        deductPanel.add(leavePanel);

	// Add addpanel and deduct panel into addDeduct panel
        addDeductPanel.add(addPanel);
        addDeductPanel.add(deductPanel);
        add(addDeductPanel);
	add(Gap(80));

        // bottomPanel contain total panel and button panel
        bottomPanel = new JPanel(new BorderLayout());
	bottomPanel.setBackground(Color.WHITE);
	
	totalColor = new Color(255,255,102);
        totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	totalLabel = new JLabel("Total  : RM");
	totalLabel.setFont(labelFont);
        totalField = new JTextField(10);
	totalField.setEditable(false); // Make the field non-editable
	totalField.setFont(textfieldFont);
        totalPanel.add(totalLabel);
	totalPanel.add(totalField);
	totalPanel.setBackground(totalColor);
	
	
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	saveNcalculateButton = new JButton("Calculate & Save");
	saveNcalculateButton.setBackground(headerColor); 
	saveNcalculateButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
	saveNcalculateButton.setForeground(Color.WHITE);
	printButton = new JButton("Print");
	printButton.setBackground(headerColor);
	printButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
	printButton.setForeground(Color.WHITE);
	backButton = new JButton("Back");
	backButton.setBackground(Color.GRAY);
	backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
	backButton.setForeground(Color.WHITE);

	buttonPanel.add(saveNcalculateButton);
	buttonPanel.add(printButton);
	buttonPanel.add(backButton);
	buttonPanel.setBackground(Color.WHITE);

        // Add action listeners
        ButtonHandler handler = new ButtonHandler();
        saveNcalculateButton.addActionListener(handler);
        printButton.addActionListener(handler);
	backButton.addActionListener(handler);

        bottomPanel.add(totalPanel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(bottomPanel);
	add(Gap(190));

	// Get information required
	employee = (fullTimeEmployee)e;
	try{
		empPosition = backendSystem.getPositionObject(e.empPosition);
	}
	catch(Exception ex){
		setVisible(false);
		CalculateSalaryPage calculateSalaryPage = new CalculateSalaryPage(backendSystem);
		JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	
	basicSalary = empPosition.getBasicSalary();
	allowance = empPosition.getAllowance();
	overtimeRate = empPosition.getOvertimeRate();
	deductionRate = empPosition.getDeductionRate();
	

	// Preset value with fixed information
	employeeIDField.setText(employee.empID);
	positionField.setText(employee.empPosition);
	employeeNameField.setText(employee.empName);
	basicSalaryField.setText(backendSystem.numberDisplayFormat(basicSalary));
	allowanceField.setText(backendSystem.numberDisplayFormat(allowance));
	overtimeRateField.setText(backendSystem.numberDisplayFormat(overtimeRate));
	leaveDeductionRateField.setText(backendSystem.numberDisplayFormat(deductionRate));
	updateField();

	setVisible(true);

    }

    private JPanel Gap(int height){
	JPanel gapPanel = new JPanel();
	gapPanel.setPreferredSize(new Dimension(0, height));
	gapPanel.setBackground(Color.WHITE);
	return gapPanel;
    }

    private void updateField(){
	// Update value for affected field

	overtimeField.setText(backendSystem.numberDisplayFormat(employee.overtime));
	overtimeTotalField.setText(backendSystem.numberDisplayFormat(employee.overtime * overtimeRate));
	bonusField.setText(backendSystem.numberDisplayFormat(employee.bonus));
	socsoField.setText(backendSystem.numberDisplayFormat(employee.tax.SOCSO));
	epfField.setText(backendSystem.numberDisplayFormat(employee.tax.EPF));
	eisField.setText(backendSystem.numberDisplayFormat(employee.tax.EIS));
	leaveField.setText(backendSystem.numberDisplayFormat(employee.unpaidLeave));
	leaveTotalField.setText(backendSystem.numberDisplayFormat(employee.unpaidLeave * deductionRate));
	totalField.setText(backendSystem.numberDisplayFormat(employee.totalSalary));
    }    

     private void generateSalarySlipPDF() {
    	
    	try {
		
		backendSystem.generateSlip(employee);

        } catch (DocumentException | FileNotFoundException ex) {
        	 JOptionPane.showMessageDialog(null, "Error generating PDF", "Print Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
		JOptionPane.showMessageDialog(null, "Error generating PDF: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
	}

     }



    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

             if (e.getSource() == saveNcalculateButton) {
		
		try{
                	// Store the key in value into the Employee object
			employee.overtime = Integer.valueOf(overtimeField.getText());
			employee.bonus = Double.valueOf(bonusField.getText());
			employee.unpaidLeave = Integer.valueOf(leaveField.getText());

			// Calculate and store the information
			employee.calculateSalary(backendSystem.positionList);
			backendSystem.storeEmployeeList();
		}
		catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(null, "Incomplete Field!", "Error", JOptionPane.WARNING_MESSAGE);
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(null, ioe.getMessage(), "Store Employee List to File: Error", JOptionPane.WARNING_MESSAGE);
		}
		catch (Exception ex){
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		// Update to reflect calculated value
		updateField();
		

            } else if (e.getSource() == printButton) {

		// Handle Print button action
		generateSalarySlipPDF();
		
		
            } else if (e.getSource() == backButton) {

                // Handle Back button action
		// Close this windows
                setVisible(false);

                // Navigate back to the CalculateSalaryPage
		CalculateSalaryPage calculateSalaryPage = new CalculateSalaryPage(backendSystem);

            }
        }
    }

}


