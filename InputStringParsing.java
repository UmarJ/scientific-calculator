public class InputStringParsing { // contains function for parsing the string input from user in a form that can be processed, and for checking for errors
	
	public static String removeSpaces(String s) { // function to remove spaces from string
		
		return s.replaceAll("\\s", "");		
	}
	
	public static String changeConstants(String expression) { // replace constants (pi) that have been entered with numbers
		
		for(int i = 0; i < expression.length(); i++) {
			
			if(expression.startsWith("pi", i) == true && Character.isDigit(expression.charAt(i - 1)) == true) // add a multiplication sign if there is a number to the left
				expression = expression.substring(0, i) + "*" + expression.substring(i);
		}
		
		expression = expression.replaceAll("pi", Double.toString(Math.PI)); // replace pi with Math.PI
		return expression;
	}
	
	public static String negationToNeg(String expression) { // change all unary negation operators to neg
		
		if(expression.charAt(0) == '-') // if negation is the first character, then convert it to neg
			expression = "neg" + expression.substring(1);

		
		for(int i = 1; i < expression.length(); i++) {

			if(expression.charAt(i) == '-' && (expression.charAt(i - 1) == '(' || Node.isBinaryOperator(Character.toString(expression.charAt(i - 1)), 0) == true)) {  // change - to neg if there is a bracket or a binary operator to the left
				expression = expression.substring(0, i) + "neg" + expression.substring(i + 1);
			}
		}
		
			return expression;
	}
	
	public static boolean inputBracketError(String expression) { // check if number of left brackets is equal to number of right brackets
		
		int bracketCounter = 0;
		
		for(int i = 0; i < expression.length(); i++) {
			
			if(expression.charAt(i) == '(') // increase counter for left brackets
				bracketCounter++;
			
			if(expression.charAt(i) == ')') // decrease counter for right brackets
				bracketCounter--;			
		}
		
		if (bracketCounter != 0) // if the number is not the same, return true
			return true;
		else
			return false;		
		
	}
	
	public static int numberOfOperations(String expression) { // calculate number of operations in the given expression
	
		int operations = 0;
	
		for (int i = 0; i < expression.length(); i++) // check each character
			if(Node.isValidOperator(expression, i) == true)
				operations++;

		return operations;
	
	}
}
