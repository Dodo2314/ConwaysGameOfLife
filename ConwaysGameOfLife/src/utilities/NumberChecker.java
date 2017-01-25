package utilities;

public class NumberChecker {
	
	public boolean isInteger(String toCheck){
		try  
		  {  
		    @SuppressWarnings("unused")
			double d = Double.parseDouble(toCheck);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true;  
	}
	
	public TextFieldNumberErrors isIntegerInRange(String toCheck, int min, int max){
		if(isInteger(toCheck)){
			int num = Integer.parseInt(toCheck);
			if(num >= min && num <= max){
				return TextFieldNumberErrors.IS_NUMBER_IN_RANGE;
			}else{
				return TextFieldNumberErrors.NUMBER_NOT_IN_RANGE;
			}
		}else{
			return TextFieldNumberErrors.NOT_A_NUMBER;
		}
	}
	
	public TextFieldNumberErrors isIntegerInRange(String toCheck, int min){
		if(isInteger(toCheck)){
			int num = Integer.parseInt(toCheck);
			if(num >= min){
				return TextFieldNumberErrors.IS_NUMBER_IN_RANGE;
			}else{
				return TextFieldNumberErrors.NUMBER_NOT_IN_RANGE;
			}
		}else{
			return TextFieldNumberErrors.NOT_A_NUMBER;
		}
	}
	
	public enum TextFieldNumberErrors{
		IS_NUMBER_IN_RANGE,
		NUMBER_NOT_IN_RANGE,
		NOT_A_NUMBER
	}
}
