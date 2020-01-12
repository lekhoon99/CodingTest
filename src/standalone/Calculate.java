package standalone;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Performs calculation based on a String input.
 * With assumption that the string will always consist of numbers, operators and brackets separated by spaces.
 * @author Lek Hoon
 *
 */
public class Calculate {
	
	private static Pattern bracketParamRegex = Pattern.compile("(\\([^\\(\\)]*\\))");
	private static Pattern multiplyOrDivideRegex = Pattern.compile("([0-9]+[\\.[0-9]+]?\\s(\\*|\\/)\\s[0-9]+[\\.[0-9]+]?)");

	public static void main(String args[]) {
		
		String[] equations = {"1 + 1", "2 * 2", "1 + 2 + 3", "6 / 2", "11 + 23", "11.1 + 23", "1 + 1 * 3", 
							"( 11.5 + 15.4 ) + 10.1", "23 - ( 29.3 - 12.5 )", "10 - ( 2 + 3 * ( 7 - 5 ) )", 
							"2 + ( 3 + 5 - 2 ) - 3 + ( 7 - 1 )", "10 + 3 * 2 + 2 * 4" };
		
		for (String equation: equations) {
			
			double result = calculate(equation);
			
			//Print output
			if (result == -1) {
				System.out.println("Invalid question: " + equation + "\n");
			} else {
				System.out.println(equation.concat(" = ").concat(String.valueOf(result)).concat("\n"));
			}
		}
	}
	
	public static double calculate(String sum) {
		
		double result = 0;
		
		//First, find bracket params, calculate and replace it with calculated value
		Matcher m = bracketParamRegex.matcher(sum);
		
		while (m.find()) {
			System.out.println("sum: " + sum);
			sum = findAndReplaceBracketParam(m);
			System.out.println("sum after replaced: " + sum);
			
			m = bracketParamRegex.matcher(sum);
		}
		
		//Then, evaluate the remaining equation
		result = evalEquation(sum);
		
		return result;
	} 
	
	//find bracket params, calculate and replace it with calculated value
	private static String findAndReplaceBracketParam(Matcher m) {
		
		String matchedValue = m.group(0);
		System.out.println("matchedValue for bracket params: " + matchedValue);
		
		double result = evalEquation(matchedValue);
		return m.replaceFirst(String.valueOf(result));
	}
	
	//Evaluation the remaining equation (* / + -)
	private static double evalEquation(String equation) {

		equation = equation.replaceAll("\\( ", "");
		equation = equation.replaceAll(" \\)", "");
		
		//Operator precedence: First, do Multiply and Divide
		Matcher m = multiplyOrDivideRegex.matcher(equation);
		while (m.find()) {
			System.out.println("equation: " + equation);
			equation = findAndReplaceMulDivParam(m);
			System.out.println("equation after replaced: " + equation);
			
			m = multiplyOrDivideRegex.matcher(equation);
		}

		//Operator precedence: Then do Add and Subtract		
		double result = -1;
		
		//Split as List
		String[] params = equation.split(" ");
		
		if (params.length > 1) {

			for (int i=0; i<params.length-1; i++) {
	
				//If param is operator + or -	
				if (params[i].equals("+")) {
					if (result == -1) {
						result = Double.parseDouble(params[i-1]) + Double.parseDouble(params[i+1]);
					} else {
						result = result + Double.parseDouble(params[i+1]);
					}
					
				} else if (params[i].equals("-")) {
					if (result == -1) {
						result = Double.parseDouble(params[i-1]) - Double.parseDouble(params[i+1]);	
					} else {
						result = result - Double.parseDouble(params[i+1]);
					}
				} else if (!isNumber(params[i])) { 	//not a valid param
					return -1;
				}		
			}
		} else if (params.length == 1) {
			result = Double.parseDouble(equation);
		}
		
		return result;
	}
	
	//find multiply or divide params, calculate and replace it with calculated value
	private static String findAndReplaceMulDivParam(Matcher m) {
		
		String matchedValue = m.group(0);
		System.out.println("matchedValue for mul/div params: " + matchedValue);
		
		double result = -1;
		String[] params = matchedValue.split(" ");
		
		if (params.length == 3) {
			for (int i=0; i<params.length; i++) {
				if (params[i].equals("*")) {
					result = Double.parseDouble(params[i-1]) * Double.parseDouble(params[i+1]);
				} else if (params[i].equals("/")) {
					result = Double.parseDouble(params[i-1]) / Double.parseDouble(params[i+1]);
				}
			}
		}		
		return m.replaceFirst(String.valueOf(result));
	}
	
	private static boolean isNumber(String param) {		
		try {
			Double.parseDouble(param);
			
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
}
