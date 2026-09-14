import com.itextpdf.text.DocumentException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ParttimePage extends JFrame {
	Color headerColor,totalColor;
	JPanel headerPanel,detailsPanel,additionPanel, addPanel,hourworkedPanel,allowancePanel,bonusPanel,bottomPanel,totalPanel,buttonPanel;
	JLabel title,employeeIDLabel, positionLabel,employeeNameLabel,emptyLabel,hourworkedLabel,hourworkedMultiplyLabel,hourworkedEqualLabel,allowanceLabel,bonusLabel,totalLabel;
	JTextField employeeIDField,positionField,employeeNameField,hourworkedField,hourlyRateField,hourworkedTotalField,allowanceField,bonusField,totalField;
	Font headerFont, labelFont,textfieldFont,inputtextfieldFont;
	TitledBorder tba;
	JButton saveNcalculateButton, printButton, cancelButton;
	Backend backendSystem;
	partTimeEmployee employee;
	Position empPosition;
	double hourlyRate, allowance;
    
    public ParttimePage(Backend b, Employee e) {
        super("Part Time Employee");
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
	labelFont = new Font("Segoe UI", Font.BOLD, 16);

	// textfield font
	textfieldFont = new Font("Arial", Font.PLAIN + Font.ITALIC, 13);
	
	// inputtextfield font;
	inputtextfieldFont = new Font("Arial", Font.PLAIN, 13);

        // Panel 2 for Employee Details
        detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setLayout(new GridLayout(2, 4, 30, 10));
	
        // employeeID
        employeeIDLabel = new JLabel("Employee ID         :");
	employeeIDLabel.setFont(labelFont); 
        employeeIDField = new JTextField(24);
	employeeIDField.setEditable(false); // Make the field non-editable
	employeeIDField.setFont(textfieldFont);
	detailsPanel.add(employeeIDLabel);
	detailsPanel.add(employeeIDField);

	// Position
	positionLabel = new JLabel("Position                :");
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

	// empty
	emptyLabel = new JLabel("		          ");
	detailsPanel.add(emptyLabel);

	detailsPanel.setBackground(Color.WHITE);
        add(detailsPanel);
        add(Gap(50));
	 

        // Panel 3 for Addition
        additionPanel = new JPanel();
        additionPanel.setLayout(new GridLayout(1, 2));
	additionPanel.setBackground(Color.WHITE);

        // Addition Panel
        addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
	tba = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(headerColor), "+Addition");
	tba.setTitleFont(new Font("Segoe UI", Font.BOLD, 18));
	addPanel.setBorder(tba);
	addPanel.setBackground(Color.WHITE); 

	// Inside Additon Panel has Hour Worked Panel,Allowance Panel, and Bonus Panel
        hourworkedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	hourworkedLabel = new JLabel("Hour Worked    : ");
	hourworkedLabel.setFont(labelFont);
	hourworkedField = new JTextField(5);
	hourworkedField.setFont(inputtextfieldFont);
	hourworkedMultiplyLabel = new JLabel("* RM");
	hourworkedMultiplyLabel.setFont(labelFont);
        hourlyRateField = new JTextField(5);
        hourlyRateField.setEditable(false); // Make the field non-editable
	hourlyRateField.setFont(textfieldFont);
        hourworkedEqualLabel = new JLabel("= RM");
      	hourworkedEqualLabel.setFont(labelFont);
	hourworkedTotalField = new JTextField(10);
        hourworkedTotalField.setEditable(false); // Make the field non-editable
	hourworkedTotalField.setFont(textfieldFont);
	hourworkedPanel.add(hourworkedLabel);
	hourworkedPanel.add(hourworkedField);
	hourworkedPanel.add(hourworkedMultiplyLabel);
	hourworkedPanel.add(hourlyRateField);
	hourworkedPanel.add(hourworkedEqualLabel);
	hourworkedPanel.add(hourworkedTotalField);
	hourworkedPanel.setBackground(Color.WHITE); 

        allowancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        allowanceLabel = new JLabel("Allowance         : RM");
	allowanceLabel.setFont(labelFont);
	allowanceField = new JTextField(12);
        allowanceField.setEditable(false); // Make the field non-editable
	allowanceField.setFont(textfieldFont);
	allowancePanel.add(allowanceLabel);
	allowancePanel.add(allowanceField);
	allowancePanel.setBackground(Color.WHITE); 


        bonusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	bonusLabel = new JLabel("Bonus                 : RM");
	bonusLabel.setFont(labelFont);
        bonusField = new JTextField(12);
	bonusField.setFont(inputtextfieldFont);
	bonusPanel.add(bonusLabel);
	bonusPanel.add(bonusField);
	bonusPanel.setBackground(Color.WHITE); 


	// Add hourworked Panel, allowancePanel and bonus panel into addition panel
        addPanel.add(hourworkedPanel);
        addPanel.add(allowancePanel);
        addPanel.add(bonusPanel);

	// Add addpanel into addition panel
        additionPanel.add(addPanel);
        add(additionPanel);
	add(Gap(60));

        // bottomPanel containbutton panel
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
	cancelButton = new JButton("Back");
	cancelButton.setBackground(Color.GRAY);
	cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
	cancelButton.setForeground(Color.WHITE);

     
	buttonPanel.add(saveNcalculateButton);
	buttonPanel.add(printButton);
	buttonPanel.add(cancelButton);
	buttonPanel.setBackground(Color.WHITE);

	// Add action listeners
        ButtonHandler handler = new ButtonHandler();
        saveNcalculateButton.addActionListener(handler);
        printButton.addActionListener(handler);
	cancelButton.addActionListener(handler);

	bottomPanel.add(totalPanel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(bottomPanel);
	add(Gap(190));

	// Get information required
	employee = (partTimeEmployee)e;
	try{
		empPosition = backendSystem.getPositionObject(e.empPosition);
	}
	catch(Exception ex){
		setVisible(false);
		CalculateSalaryPage calculateSalaryPage = new CalculateSalaryPage(backendSystem);
		JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	hourlyRate = empPosition.getHourlyRate(); 
	allowance = empPosition.getAllowance();

	// Preset value with fixed information
	employeeIDField.setText(employee.empID);
	positionField.setText(employee.empPosition);
	employeeNameField.setText(employee.empName);
	hourlyRateField.setText(backendSystem.numberDisplayFormat(hourlyRate));
	allowanceField.setText(backendSystem.numberDisplayFormat(allowance));
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
	hourworkedField.setText(backendSystem.numberDisplayFormat(employee.hourWorked));
	hourworkedTotalField.setText(backendSystem.numberDisplayFormat(employee.hourWorked * hourlyRate));
	bonusField.setText(backendSystem.numberDisplayFormat(employee.bonus));
	totalField.setText(backendSystem.numberDisplayFormat(employee.totalSalary));
    }

    
    private void generateSalarySlipPDFpt(){
		
	try {
		
            	backendSystem.generateSlip(employee);	
           		
        } catch (DocumentException | FileNotFoundException ex) {
            	JOptionPane.showMessageDialog(null, "Error generating PDF: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
		JOptionPane.showMessageDialog(null, "Error generating PDF: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
	}

     }

	
    

private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

             if (e.getSource() == saveNcalculateButton) {
		
		try{
                	// Store the key in value into the Employee object
			employee.hourWorked = Integer.valueOf(hourworkedField.getText());
			employee.bonus = Double.valueOf(bonusField.getText());

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
		generateSalarySlipPDFpt();
		
		
            } else if (e.getSource() == cancelButton) {

                // Handle Back button action
		// Close this windows
                setVisible(false);

                // Navigate back to the CalculateSalaryPage
		CalculateSalaryPage calculateSalaryPage = new CalculateSalaryPage(backendSystem);

            }
        }
    }

}

