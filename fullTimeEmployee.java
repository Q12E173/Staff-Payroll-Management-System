import java.util.*;
import java.lang.Exception;

public class fullTimeEmployee extends Employee{
	int overtime, unpaidLeave;
	Tax tax;

	public fullTimeEmployee(String empID, String empName, String empIC, String empPosition, String empGender, Calendar dateOfBirth, Calendar dateOfHired){
		super(empID, empName, empIC, empPosition, empGender, dateOfBirth, dateOfHired);
		fulltime = true;
		overtime = -1;
		unpaidLeave = -1;
		tax = new Tax();
	}

	public void calculateSalary(Vector<Position> vector) throws Exception{
		Position position = Backend.getPositionObject(empPosition);
		double OT, leave;
		double totalTax;
		int index;
		
		OT = overtime * position.getOvertimeRate();
		leave = unpaidLeave * position.getHourlyRate();

		tax.calculateTax(position.getBasicSalary() + OT);
		totalTax = tax.SOCSO + tax.EPF + tax.EIS;

		totalSalary = position.getBasicSalary() + OT + position.getAllowance() + bonus - totalTax - leave;
		checkedSalary = true;
	}

	public void reset(){
		overtime = -1;
		unpaidLeave = -1;
		bonus = -1;
		totalSalary = -1;
		tax = new Tax();
		checkedSalary = false;
	}

}