import java.util.*;
import java.lang.Exception;

public class partTimeEmployee extends Employee{
	int hourWorked;

	public partTimeEmployee(String empID, String empName, String empIC, String empPosition, String empGender, Calendar dateOfBirth, Calendar dateOfHired){
		super(empID, empName, empIC, empPosition, empGender, dateOfBirth, dateOfHired);
		fulltime = false;
		hourWorked = -1;
	}

	public void calculateSalary(Vector<Position> vector) throws Exception{
		Position position = Backend.getPositionObject(empPosition);
		int index;
		double moneyEarned;
		
		moneyEarned = hourWorked * position.getHourlyRate();
		totalSalary = moneyEarned + position.getAllowance() + bonus;
		checkedSalary = true;
	}

	public void reset(){
		hourWorked = -1;
		bonus = -1;
		totalSalary = -1;
		checkedSalary = false;
	}
}