import java.io.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import java.lang.Exception;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Element;

public class Backend extends JFrame{
	public static Vector<Position> positionList;
	Hashtable<String, Employee> employeeList;
	String adminName, adminPassword;
	String adminPasswordFile = "admin_pass.txt";
	File slipDir;


	public Backend(){
		// Initialize the attribute
		positionList = new Vector<Position>();
		employeeList = new Hashtable<String, Employee>();

		// Set default password, overwritten if password is loaded from file
		adminName = "admin";
		adminPassword = "admin";
		
		// Load information from file
		try{
			load();
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(null, ioe.getMessage(), "Load File: Error", JOptionPane.WARNING_MESSAGE);
		}
		catch(ClassNotFoundException e){
			JOptionPane.showMessageDialog(null, e.getMessage(), "Load File: Error", JOptionPane.WARNING_MESSAGE);
		}

		// Create Salary Slip folder if not exists
		String pathName = System.getProperty("user.dir");
		slipDir = new File(pathName + "/salarySlip");
		if(!slipDir.exists()){
			slipDir.mkdirs();
		}

		// Generate necessary file if not exists
		// by storing the information currently available
		try{
			saveAdminPasswordFile();
			storeEmployeeList();
			storePositionList();
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(null, ioe.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
		}
		
	}

	public String getNewPassword() throws Exception{
		String newPassword, retypePassword;
		JPanel newPasswordPanel;
		JLabel label1, label2;
		JPasswordField newPasswordField, confirmField;
		int choice;

		// GUI Component for second dialog
		label1 = new JLabel("Enter New Password:");
		label2 = new JLabel("Retype New Password:");

		newPasswordField = new JPasswordField(20);
		confirmField = new JPasswordField(20);

		newPasswordPanel = new JPanel(new GridLayout(5, 1, 10, 0));
		newPasswordPanel.add(label1);
		newPasswordPanel.add(newPasswordField);
		newPasswordPanel.add(new JLabel(""));
		newPasswordPanel.add(label2);
		newPasswordPanel.add(confirmField);

		choice = JOptionPane.showConfirmDialog(null, newPasswordPanel, "Change Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(choice == 0){
			newPassword = newPasswordField.getText();
			retypePassword = confirmField.getText();
			if (newPassword != null && !newPassword.trim().isEmpty()) {
				if(retypePassword.equals(newPassword)){
		              		return newPassword;
				}
				else{
					throw new Exception("Password retyped does not match!");
				}
			}
			else{
				throw new Exception("Empty password does not allowed!");
			}
	        } 
		else{
			throw new Exception("Password change cancelled!");
		}
	}

	public void setPassword(String newPassword){
		adminPassword = newPassword;
		saveAdminPasswordFile();
		JOptionPane.showMessageDialog(null, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	}

	public void setPassword(String newPassword, String empID){
		Employee tempEmp = employeeList.remove(empID);
		tempEmp.empPassword = newPassword;
		employeeList.put(empID, tempEmp);

		try{
			storeEmployeeList();
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(null, ioe.getMessage(), "Store File: Error", JOptionPane.WARNING_MESSAGE);
		}

		JOptionPane.showMessageDialog(null, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	}

	
	public boolean verifyLogin(String pw){
		if(adminPassword.equals(pw)){
			return true;
		}
		else{
			return false;
		}
	}

	public boolean verifyLogin(String n, String pw){
		if(adminName.equals(n) && adminPassword.equals(pw)){
			return true;
		}
		else{
			return false;
		}
	}

	public String getEmpGender(String empIC){
		String empGender;
		int lastDigit = Integer.valueOf(empIC.charAt(empIC.length() - 1));
		if((lastDigit % 2) == 0){
			empGender = "Female";
		}
		else{
			empGender = "Male";
		}
		return empGender;
	}

	public Calendar getEmpDOB(String empIC) throws Exception{
		// Get birthday information from IC field
		int year = Integer.valueOf(empIC.substring(0,2));
		int month = Integer.valueOf(empIC.substring(2,4));
		int day = Integer.valueOf(empIC.substring(4,6));

		// Month in Java is 0-based, Jan = 0
		month -= 1;
		
		// Check if month within range
		if((month < 0) || (month > 11)){
			throw new Exception("Invalid IC: Month of Birth is invalid!");
		}
		
		// Check if day of month within range
		Calendar tempCalendar = new GregorianCalendar(year, month, 1);
		int daysInMonth = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		if((day < 1) || (day > daysInMonth)){
			throw new Exception("Invalid IC: Day of Birth is invalid!");
		}

		// Convert year value to 4 digit
		Calendar dateOfBirth = Calendar.getInstance();
		if(year <= (dateOfBirth.get(Calendar.YEAR) % 100)){
			year += 2000;
		}
		else{
			year += 1900;
		}
		
		// Convert to Calendar format
		dateOfBirth.set(year, month, day);
		return dateOfBirth;
	}

	public String getEmpType(boolean fulltime){
		String empType;
		if(fulltime){
			empType = "Full Time";
		}
		else{
			empType = "Part Time";
		}
		return empType;
	}

	public String getSalaryStatus(boolean checkedSalary){
		String salaryStatus;
		if(checkedSalary){
			salaryStatus = "Checked";
		}
		else{
			salaryStatus = "Unchecked";
		}
		return salaryStatus;
	}

	public String calendarToString(Calendar date){
		try{
			String dateString = date.get(Calendar.DATE) + " - " + (date.get(Calendar.MONTH) + 1 ) + " - " + date.get(Calendar.YEAR);
			return dateString;
		}
		catch(NullPointerException npe){
			return "";
		}
	}

	public void addEmptyPosition() throws IOException{
		// Create and add empty position
		Position empty = new Position();
		positionList.addElement(empty);
		
		// Store in the file
		storePositionList();
	}

	public void savePosition(int index, String positionName, String salaryField, String allowanceField, String overtimeField, String hourlyField, String deductionField) throws IOException, Exception{
		Position newPosition = new Position();
	
		if(positionName.isEmpty()){
			throw new Exception("Empty Position Name is Not Allowed!");
		}
		else{
			// Compare the name with existing position with non-null name
			for(int i = 0; i < positionList.size(); i++){

				Position p = positionList.elementAt(i);

				// Skip to avoid comparing itself
				if(i == index){
					continue;
				}

				if(p.getPositionName() != null){
					if(p.getPositionName().equals(positionName)){
						throw new Exception("Same Position Name exists!");
					}
	
				}
			}
				
			newPosition.setPositionName(positionName);
		}

		if(!salaryField.isEmpty()){
			double basicSalary = Double.valueOf(salaryField);
			if(basicSalary < 0){
				throw new Exception("Invalid Salary is Entered!");
			}
			else{
				newPosition.setBasicSalary(basicSalary);
			}
		}
	
		if(!allowanceField.isEmpty()){
			double allowance = Double.valueOf(allowanceField);
			if(allowance < 0){
				throw new Exception("Invalid Allowance is Entered!");
			}
			else{
				newPosition.setAllowance(allowance);
			}
		}
	
		if(!overtimeField.isEmpty()){
			double overtimeRate = Double.valueOf(overtimeField); 
			if(overtimeRate < 0){
				throw new Exception("Invalid Overtime Rate is Entered!");
			}
			else{
				newPosition.setOvertimeRate(overtimeRate);
			}
		}
	
		if(!hourlyField.isEmpty()){
			double hourlyRate = Double.valueOf(hourlyField);
			if(hourlyRate < 0){
				throw new Exception("Invalid Hourly Rate is Entered!");
			}
			else{
				newPosition.setHourlyRate(hourlyRate);
			}
		}
	
		if(!deductionField.isEmpty()){
			double deductionRate = Double.valueOf(deductionField);
			if(deductionRate < 0){
				throw new Exception("Invalid Deduction Rate is Entered!");
			}
			else{
				newPosition.setDeductionRate(deductionRate);
			}
		}

		//Insert new information into vector
		positionList.setElementAt(newPosition, index);
	
		//Store in the file
		storePositionList();
	}

	public void removePosition(int index) throws IOException, ArrayIndexOutOfBoundsException{
		positionList.remove(index);
		storePositionList();
	}

	public void saveEmployee(String empID, String empName, String empIC, String empPosition, Calendar dateOfHired, String empType) throws IOException, Exception{
		Employee newEmp = null;
		String empGender;
		Calendar dateOfBirth;
		boolean edit = false;
		
		if(empName.isEmpty()){
			throw new Exception("Empty name is not allowed!");
		}		

		if(empIC.length() != 12){
			throw new Exception("Invalid IC: Length of IC is invalid!");
		}

		Enumeration<Employee> temp = employeeList.elements();
		while(temp.hasMoreElements()){
			Employee existedEmp = temp.nextElement();

			// Skip to avoid comparing itself
			if(empID.equals(existedEmp.empID)){
				edit = true;
				newEmp = existedEmp;
				continue;
			}

			if(empIC.equals(existedEmp.empIC)){
				throw new Exception("Same Employee's IC existed");
			}
		}
			
		empGender = getEmpGender(empIC);
	    	dateOfBirth = getEmpDOB(empIC); 

		if(dateOfHired == null){
			throw new Exception("Date of Hired is Empty!");
		}

		if(edit){
			employeeList.remove(empID, newEmp);
		}
	

		if(empType.equals("Full Time")){
			newEmp = new fullTimeEmployee(empID, empName, empIC, empPosition, empGender, dateOfBirth, dateOfHired);
		}
		else if(empType.equals("Part Time")){
			newEmp = new partTimeEmployee(empID, empName, empIC, empPosition, empGender, dateOfBirth, dateOfHired);
		}

		employeeList.put(empID, newEmp);

		storeEmployeeList();
	}

	public void removeEmployee(String empID){
		//Remove employee from Hash Table and save permanently
		employeeList.remove(empID);
				
		try{
			storeEmployeeList();
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(null, ioe.getMessage(), "Store Employee List to File: Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void searchAndSelect(JTable table){
		// Ask Input by name
		String keyword = JOptionPane.showInputDialog(null, "Enter Employee ID:", "Search Employee", JOptionPane.QUESTION_MESSAGE);

		//Check if the name is available in the list
		try{
			if(employeeList.containsKey(keyword)){
				//Select the employee matched
				int index;
				for(index = 0; index < table.getRowCount(); index++){
					
					if(((String)table.getValueAt(index, 0)).equals(keyword)){
						table.changeSelection(index, 0, false, false);
						break;
					}
				}
			}
			else{
				// The keyword is not found
				JOptionPane.showMessageDialog(null, "Employee not found!", "Search Employee: Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch(NullPointerException npe){
			// Generated when search function is closed
			// No handling required
		}
	}

	public static Position getPositionObject(String positionName) throws Exception{
		for(Position p : positionList){
			if(positionName.equals(p.getPositionName())){
				return p;
			}
		}
		throw new Exception("Employee's position is invalid!");
	}

	public String numberDisplayFormat(double value){
		if(value < 0){
			return new String("");
		}
		else{
			DecimalFormat currency = new DecimalFormat("0.00");
			return String.valueOf(currency.format(value));
		}
	}

	public String numberDisplayFormat(int value){
		if(value < 0){
			return new String("");
		}
		else{
			return String.valueOf(value);
		}
	}

	public void storePositionList() throws IOException{
		FileOutputStream file = new FileOutputStream("positionList.txt");
		ObjectOutputStream out = new ObjectOutputStream(file);

		out.writeObject(positionList);
		out.close();
		file.close();
	}

	public void storeEmployeeList() throws IOException{
		FileOutputStream file = new FileOutputStream("employeeList.txt");
		ObjectOutputStream out = new ObjectOutputStream(file);

		out.writeObject(employeeList);
		out.close();
		file.close();
	}		

	private void saveAdminPasswordFile() {
        	try {
	            BufferedWriter writer = new BufferedWriter(new FileWriter(adminPasswordFile));
	            writer.write(adminName);
	            writer.newLine();
	            writer.write(adminPassword);
	            writer.close();
	        } catch (IOException ie) {
	            JOptionPane.showMessageDialog(null, ie.getMessage(), "Save Password: Error", JOptionPane.WARNING_MESSAGE);
        	}
    	}

	private void loadAdminPasswordFile() {
        	try {
	            BufferedReader reader = new BufferedReader(new FileReader(adminPasswordFile));
	            adminName = reader.readLine();
	            adminPassword = reader.readLine();
	            reader.close();
	        } catch (IOException ie) {
		    JOptionPane.showMessageDialog(null, ie.getMessage(), "Load Password: Error", JOptionPane.WARNING_MESSAGE);
	        }
    	}

	public void load() throws IOException, ClassNotFoundException{
		//Load positionList
		FileInputStream file = new FileInputStream("positionList.txt");
		ObjectInputStream in = new ObjectInputStream(file);

		positionList = (Vector<Position>)in.readObject();
		in.close();
		file.close();

		//Load employeeList
		file = new FileInputStream("employeeList.txt");
		in = new ObjectInputStream(file);

		employeeList = (Hashtable<String, Employee>)in.readObject();
		in.close();
		file.close();

		//Load Password
		loadAdminPasswordFile();
	}

	public void generateSlip(fullTimeEmployee employee) throws DocumentException, FileNotFoundException, Exception{
		Position p = getPositionObject(employee.empPosition);
		Document document;
		JFileChooser fileChooser;
		Paragraph slipTitle,companyName, companyAddress, footer;
		PdfPTable additionTable,deductionTable;

		if(!employee.checkedSalary){
			throw new Exception("Employee Salary is Not Calculated!");
		}

		document = new Document();
		fileChooser = new JFileChooser();
	    	fileChooser.setDialogTitle("Save Salary Slip");

		// Set the preset path
		fileChooser.setCurrentDirectory(slipDir);

		// Systematically generated name for the slip as preset
		Calendar current = Calendar.getInstance();
		String filename = new String((current.get(Calendar.MONTH) + 1 ) + "-" + current.get(Calendar.YEAR) + "_" + employee.empID + ".pdf");
		fileChooser.setSelectedFile(new File(slipDir.toString() + "/" + filename));

		// Get selection from file chooser
    		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
       		
            		String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            		if (!filePath.endsWith(".pdf")) {
                		filePath += ".pdf";
            		}
			PdfWriter.getInstance(document, new FileOutputStream(filePath));
	            	document.open();
            
        	    	// Add the title
	    		slipTitle = new Paragraph("Salary Slip");
		    	slipTitle.setAlignment(Element.ALIGN_CENTER);
		    	document.add(slipTitle);

		    	// Add blank line
		    	document.add(new Paragraph(" "));

    			// Add company name
	    		companyName = new Paragraph("Smart Inc");
	    		companyName.setAlignment(Element.ALIGN_CENTER);
			document.add(companyName);

			// Add company address
			companyAddress = new Paragraph("1 Sentral, Jalan Stesen Sentral 5, 50470 Kuala Lumpur");
			companyAddress.setAlignment(Element.ALIGN_CENTER);
			document.add(companyAddress);

			// Add blank line
			document.add(new Paragraph(" "));

			// Add generated on
			SimpleDateFormat timeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa");
			document.add(new Paragraph("Generated on: " + timeString.format(current.getTime())));

			// Add blank line
			document.add(new Paragraph(" "));
	
			// Add employee details
			document.add(new Paragraph("Employee ID: " + employee.empID));
			document.add(new Paragraph("Employee Name: " + employee.empName));
			document.add(new Paragraph("Position: " + employee.empPosition));
			document.add(new Paragraph("Basic Salary: RM " + numberDisplayFormat(p.getBasicSalary())));
	
			// Add blank line
			document.add(new Paragraph(" "));
	
			// Create addition table
			additionTable = new PdfPTable(2);
			additionTable.setWidthPercentage(100);
			additionTable.addCell("Additions");
			additionTable.addCell("Amount (RM)");
	
			// Add rows for additions
			additionTable.addCell("Allowance");
			additionTable.addCell(numberDisplayFormat(p.getAllowance()));
			additionTable.addCell("Overtime");
			additionTable.addCell(numberDisplayFormat(employee.overtime * p.getOvertimeRate()));
			additionTable.addCell("Bonus");
			additionTable.addCell(numberDisplayFormat(employee.bonus));

			// Add the addition table to the document
			document.add(additionTable);

			// Add blank line
			document.add(new Paragraph(" "));
	
			// Create deduction table
			deductionTable = new PdfPTable(2);
			deductionTable.setWidthPercentage(100);
			deductionTable.addCell("Deductions");
			deductionTable.addCell("Amount (RM)");
	
			// Add rows for deductions
			deductionTable.addCell("SOCSO");
			deductionTable.addCell(numberDisplayFormat(employee.tax.SOCSO));
			deductionTable.addCell("EPF");
			deductionTable.addCell(numberDisplayFormat(employee.tax.EPF));
			deductionTable.addCell("EIS");
			deductionTable.addCell(numberDisplayFormat(employee.tax.EIS));
			deductionTable.addCell("Leave");
			deductionTable.addCell(numberDisplayFormat(employee.unpaidLeave * p.getDeductionRate()));
	
			// Add the deduction table to the document
			document.add(deductionTable);
	
			// Add blank line
			document.add(new Paragraph(" "));
	
			// Add net pay
			document.add(new Paragraph("Net Pay: RM " + numberDisplayFormat(employee.totalSalary)));
	
			// Add blank line
			document.add(new Paragraph(" "));
	
			// Add employer signature
			document.add(new Paragraph("APPROVED BY: "));
			// Add blank line
			document.add(new Paragraph(" "));
			// Add blank line
			document.add(new Paragraph(" "));
			document.add(new Paragraph("__________________"));
			// Add blank line
			document.add(new Paragraph(" "));
	
			// Add footer with generation time
			footer = new Paragraph("This is a system-generated salary slip.");
			footer.setAlignment(Element.ALIGN_CENTER);
			document.add(footer);
	
			// Close the document
			document.close();

			JOptionPane.showMessageDialog(null, "Salary slip successfully saved", "Print Success", JOptionPane.INFORMATION_MESSAGE);

		}else if (userSelection == JFileChooser.CANCEL_OPTION){
	  		JOptionPane.showMessageDialog(null, "Cancel saving salary slip","Print Cancel", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void generateSlip(partTimeEmployee employee) throws DocumentException, FileNotFoundException, Exception{
		Position p = getPositionObject(employee.empPosition);
		Document document;
		JFileChooser fileChooser;
		Paragraph slipTitle,companyName, companyAddress, footer;
		PdfPTable additionTable;


		if(!employee.checkedSalary){
			throw new Exception("Employee Salary is Not Calculated!");
		}

		document = new Document();
		fileChooser = new JFileChooser();
	    	fileChooser.setDialogTitle("Save Salary Slip");

		// Set the preset path
		fileChooser.setCurrentDirectory(slipDir);

		// Systematically generated name for the slip as preset
		Calendar current = Calendar.getInstance();
		String filename = new String((current.get(Calendar.MONTH) + 1 ) + "-" + current.get(Calendar.YEAR) + "_" + employee.empID + ".pdf");
		fileChooser.setSelectedFile(new File(slipDir.toString() + "/" + filename));

		// Get selection from file chooser
    		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
       		
            		String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            		if (!filePath.endsWith(".pdf")) {
                		filePath += ".pdf";
            		}
			PdfWriter.getInstance(document, new FileOutputStream(filePath));
	            	document.open();
            
        	    	// Add the title
		    	slipTitle = new Paragraph("Salary Slip");
		    	slipTitle.setAlignment(Element.ALIGN_CENTER);
		    	document.add(slipTitle);

		    	// Add blank line
		    	document.add(new Paragraph(" "));
	
		    	// Add company name
		    	companyName = new Paragraph("Smart Inc");
		    	companyName.setAlignment(Element.ALIGN_CENTER);
			document.add(companyName);

			// Add company address
			companyAddress = new Paragraph("1 Sentral, Jalan Stesen Sentral 5, 50470 Kuala Lumpur");
			companyAddress.setAlignment(Element.ALIGN_CENTER);
			document.add(companyAddress);
	
			// Add blank line
			document.add(new Paragraph(" "));

			// Add generated on
			SimpleDateFormat timeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa");
			document.add(new Paragraph("Generated on: " + timeString.format(current.getTime())));

			// Add blank line
			document.add(new Paragraph(" "));

			// Add employee details
			document.add(new Paragraph("Employee ID: " + employee.empID));
			document.add(new Paragraph("Employee Name: " + employee.empName));
			document.add(new Paragraph("Position: " + employee.empPosition));

			// Add blank line
			document.add(new Paragraph(" "));

			// Create addition table
			additionTable = new PdfPTable(2);
			additionTable.setWidthPercentage(100);
			additionTable.addCell("Additions");
			additionTable.addCell("Amount (RM)");

			// Add rows for additions
			additionTable.addCell("Hours worked * Hourly Pay");
			additionTable.addCell(numberDisplayFormat(employee.hourWorked * p.getHourlyRate()));
			additionTable.addCell("Allowance");
			additionTable.addCell(numberDisplayFormat(p.getAllowance()));
			additionTable.addCell("Bonus");
			additionTable.addCell(numberDisplayFormat(employee.bonus));

			// Add the addition table to the document
			document.add(additionTable);

			// Add blank line
			document.add(new Paragraph(" "));

			// Add net pay
			document.add(new Paragraph("Net Pay: RM " + numberDisplayFormat(employee.totalSalary)));

			// Add blank line
			document.add(new Paragraph(" "));

			// Add employer signature
			document.add(new Paragraph("APPROVED BY: "));
			// Add blank line
			document.add(new Paragraph(" "));
			// Add blank line
			document.add(new Paragraph(" "));
			document.add(new Paragraph("__________________"));
			// Add blank line
			document.add(new Paragraph(" "));

			// Add footer with generation time
			footer = new Paragraph("This is a system-generated salary slip.");
			footer.setAlignment(Element.ALIGN_CENTER);
			document.add(footer);

			// Close the document
			document.close();
	
			JOptionPane.showMessageDialog(null, "Salary slip successfully saved", "Print Success", JOptionPane.INFORMATION_MESSAGE);

		} else if (userSelection == JFileChooser.CANCEL_OPTION){
	  		JOptionPane.showMessageDialog(null, "Cancel saving salary slip","Print Cancel", JOptionPane.WARNING_MESSAGE);
	    	}
	}
}