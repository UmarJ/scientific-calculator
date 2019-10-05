/* List of Valid Operations:
 * 
 * + : Addition
 * - : Subtraction
 * * : Multiplication
 * / : Division
 * ^ : Exponentiation
 * sin : Sine
 * cos : Cosine
 * tan : Tangent
 * log : Logarithm
 * ln : Natural Logarithm
 * arcs : Arc Sine 
 * arcc : Arc Cos
 * arct : Arc Tan
 * sinh : Hyperbolic Sine
 * cosh : Hyperbolic Cosine
 * tanh : Hyperbolic Tangent
 * abs : Absolute Value
 * neg : Negation
 * sqrt : Square Root
 * cbrt : Cube Root

*/

import java.util.Scanner;

public class CalculatorApplication {

    public static void main(String[] args) {  
    	
    	int choice = 1;
        
        while(true) {
        
        System.out.print("Please enter expression: "); // prompt for input
        
        Scanner input = new Scanner(System.in);
        String expression = input.nextLine();
        
        if (InputStringParsing.inputBracketError(expression) == true) { // check if number of left and right brackets is same
        	System.out.println("Error! Number of left and right brackets is not the same!");
        	continue;
        }

        expression = InputStringParsing.removeSpaces(expression); // remove spaces from expression
        expression = expression.toLowerCase(); // change the entire expression to lower case
        expression = InputStringParsing.changeConstants(expression); // change constants to numerical values in the expression
        expression = InputStringParsing.negationToNeg(expression);
        
        if(InputStringParsing.numberOfOperations(expression) == 0) {        	
        	System.out.println(expression);
        	continue;
        }
        
        Node root = new Node();
        root.expression = expression; // transfer expression to node      
        root.convertNodeToTree(); // convert the node to tree
        root.calculateNode(); // process the expression in the root
               
        System.out.println("The answer is: " + root.number); // print the answer
        System.out.print("Enter 0 to exit, or any other number to calculate again: ");
        
        choice = input.nextInt();
        
        if(choice == 0)
        	System.exit(0);

        }
    }
}
