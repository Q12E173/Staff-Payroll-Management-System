import java.io.*;

public class Position implements Serializable{
	private String positionName, status;
	private double basicSalary, allowance, overtimeRate, hourlyRate, deductionRate;

	public Position(){
		positionName = null;
		basicSalary = -1;
		allowance = -1;
		overtimeRate = -1;
		hourlyRate = -1;
		deductionRate = -1;
		checkStatus();
	}	

	public Position(String newName, double newSalary, double newAllowance, double newOvertimeRate, double newHourlyRate, double newDeductionRate){
		positionName = newName;
		basicSalary = newSalary;
		allowance = newAllowance;
		overtimeRate = newOvertimeRate;
		hourlyRate = newHourlyRate;
		deductionRate = newDeductionRate;
		checkStatus();
	}
	
	public String getPositionName(){
		return positionName;
	}

	public void setPositionName(String newName){
		positionName = newName;
		checkStatus();
	}

	public double getBasicSalary(){
		return basicSalary;
	}

	public void setBasicSalary(double newSalary){
		basicSalary = newSalary;
		checkStatus();
	}

	public double getAllowance(){
		return allowance;
	}

	public void setAllowance(double newAllowance){
		allowance = newAllowance;
		checkStatus();
	}

	public double getOvertimeRate(){
		return overtimeRate;
	}

	public void setOvertimeRate(double newRate){
		overtimeRate = newRate;
		checkStatus();
	}

	public double getHourlyRate(){
		return hourlyRate;
	}

	public void setHourlyRate(double newRate){
		hourlyRate = newRate;
		checkStatus();
	}

	public double getDeductionRate(){
		return deductionRate;
	}

	public void setDeductionRate(double newRate){
		deductionRate = newRate;
		checkStatus();
	}

	public void checkStatus(){
		int count = 0;
	
		if(positionName != null){
			count += 1;
		}	
		if(basicSalary != -1){
			count += 1;
		}	
		if(allowance != -1){
			count += 1;
		}
		if(overtimeRate != -1){
			count += 1;
		}
		if(hourlyRate != -1){
			count += 1;
		}
		if(deductionRate != -1){
			count += 1;
		}

		if(count < 1){
			status = "unset";
		}
		else if(count < 6){
			status = "incomplete";
		}
		else{
			status = "complete";
		}
	}

	public String getStatus(){
		return status;
	}

}
