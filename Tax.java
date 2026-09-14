import java.io.*;

public class Tax implements Serializable{
	double SOCSO, EPF, EIS;

	public Tax(){
		SOCSO = -1;
		EPF = -1;
		EIS = -1;
	}

	public void calculateTax(double amount){
		calculateSOCSO(amount);
		calculateEPF(amount);
		calculateEIS(amount);
	}

	public void calculateSOCSO(double amount){
		int n = -1;
		
		if(amount >= 200){
			if(amount > 5000){
				n = 47;
			}
			else{
				n = (int)((amount - 1 - 200) / 100);
			}
		}
		
		if(n == -1){
			if(amount > 30){
				if(amount > 50){
					if(amount > 70){
						if(amount > 100){
							if(amount > 140)
								SOCSO = 0.85;
							else
								SOCSO = 0.6;	
						}
						else
							SOCSO = 0.4;
					}
					else
						SOCSO = 0.3;
				}
				else
					SOCSO = 0.2;
			}
			else
				SOCSO = 0.1;
		}
		else{
			SOCSO = n * 0.5 + 1.25;
		}
	}

	public void calculateEPF(double amount){
		EPF = amount * 0.11;
	}
	
	public void calculateEIS(double amount){
		EIS = amount * 0.002;
	}
}