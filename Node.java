public class Node {	
	
	boolean isComputable = false; // true if expression can be computed
	int nodeNumber = 0; // node number of a child node
	double number; // result of expression stored in the node 
	String expression = "";
	String operator = ""; // operation to be performed
	int childNodes = 0; // number of child nodes
	Node parent = null; // parent of the node, this is null if the node is the root
	Node child []; // array of children nodes
		
	
	public static final String validOperators [] = {"+", "-", "*", "/", "^", "sin", "cos", "tan", "log", "ln", "arcs", "arcc", "arct", "sinh", "cosh", "tanh", "abs", "neg", "sqrt", "cbrt"}; // array of valid operators
	public static final String validBinaryOperators [] = {"+", "-", "*", "/", "^"}; // array of valid binary operators
	
	public Node() { // no argument constructor
		
	}	
	
	private Node(Node parent, int nodeNumber) { // constructor for initializing child nodes
		
		this.parent = parent;
		this.nodeNumber = nodeNumber;
	}    
    
    private void addNodes(int number) { // add a number of nodes to the current node
    	
    	childNodes = number;
    	child = new Node[number];
    	
    	for (int i = 0; i < childNodes; i++) {
    		
    		child[i] = new Node(this, i); // initialize the nodes with this node as parent
    	}    	
    }    
    
	private void deleteNode() { // delete a node by removing its reference from its parent and removing the parent's reference from the node
		
		Node parent = this.parent;
		parent.child[this.nodeNumber] = null;
		this.parent = null;		
	}	
	
	public void convertNodeToTree() { // recursively convert each node to a tree until leaves are reached	
		
		if (isComputable == false) { // add child nodes if the expression is not computable currently
			makeChildNodes();
			transferToChild();
		}
		
		for (int i = 0; i < childNodes; i++) { // convert each child node to a tree	
			child[i].convertNodeToTree();
		}		
	}
	
	public void calculateNode() { // calculate value of expression stored in a node
		
		Node parent = getComputableParent(); // parent having nodes that are all computable
		int firstComputableNode = -1;
		
		for (int i = 0; i < parent.childNodes; i++) { // iterate until a non-null node is reached
			
			if(parent.child[i] != null) {
				firstComputableNode = i;
				break;				
			}			
		}
		
		if (parent.child[firstComputableNode].operator.contentEquals("")) { // binary operations are stored in a way that the first valid node does not have any operator if it is a valid operator
			
			binaryOperation(parent.child[firstComputableNode], parent.child[firstComputableNode + 1]); // perform binary operation on the two nodes
			
			if(firstComputableNode + 1 + 1 == parent.childNodes) { // if the node is the last node left, transfer the value to the parent
				
				parent.number = parent.child[firstComputableNode + 1].number;
				parent.isComputable = true;
				parent.child[firstComputableNode + 1].deleteNode();
			}
		}
		
		else {
			
			unaryOperation(parent.child[firstComputableNode]); // if the node has an operator then a unary operation has to be performed
			parent.child[firstComputableNode].operator = ""; // remove the node's operator
			parent.child[firstComputableNode].isComputable = true;
			
			if(parent.child[firstComputableNode].siblings() == 0) { // if the node does not have any siblings
				parent.number = parent.child[firstComputableNode].number; // transfer value from child to parent
				parent.isComputable = true; // set parent to computable
				parent.child[firstComputableNode].deleteNode(); // delete the node
			}			
		}
		
		if (this.isComputable == false)
			this.calculateNode();		
	}
	
	private static void binaryOperation(Node a, Node b) { // list of binary operations
		
		switch(b.operator) {
		
		case "+": // addition
			b.number = MathFunctions.addition(a.number, b.number);
			break;
		
		case "-": // subtraction
			b.number = MathFunctions.subtraction(a.number, b.number);
			break;

		case "*": // multiplication
			b.number = MathFunctions.multiplication(a.number, b.number);
			break;
			
		case "/": // division
			b.number = MathFunctions.division(a.number, b.number);
			break;
			
		case "^": // exponent
			b.number = MathFunctions.exponent(a.number, b.number);
			break;
		
		}
		
		b.operator = ""; // remove operator for second node
		a.deleteNode(); // delete the first node
		
	}
	
	private static void unaryOperation(Node a) { // list of unary operations
		
		switch(a.operator) {
		
		case "sin": // sine
			a.number = MathFunctions.sine(a.number);
			break;
			
		case "cos": // cosine
			a.number = MathFunctions.cosine(a.number);
			break;
			
		case "tan": // tangent
			a.number = MathFunctions.tangent(a.number);
			break;
			
		case "log": // logarithm
			a.number = MathFunctions.log(a.number);
			break;
			
		case "ln": // natural logarithm
			a.number = MathFunctions.naturalLog(a.number);
			
		case "arcs": // sine inverse
			a.number = MathFunctions.arcSine(a.number);
			break;
			
		case "arcc": // cosine inverse
			a.number = MathFunctions.arcCosine(a.number);
			break;
			
		case "arct": // tangent inverse
			a.number = MathFunctions.arcTangent(a.number);
			break;
			
		case "abs": // absolute value of number
			a.number = MathFunctions.absolute(a.number);
			break;
			
		case "sinh": // sine hypotenuse 
			a.number = MathFunctions.sinh(a.number);
			break;
			
		case "cosh": // cosine hypotenuse 
			a.number = MathFunctions.cosh(a.number);
			break;
			
		case "tanh": // tangent hypotenuse 
			a.number = MathFunctions.tanh(a.number);
			break;
			
		case "neg": // negation of a number
			a.number = MathFunctions.negation(a.number);
			break;
			
		case "sqrt": // square root of a number
			a.number = MathFunctions.squareRoot(a.number);
			break;
			
		case "cbrt": // cube root of a number
			a.number = MathFunctions.cubeRoot(a.number);
			break;		
		}		
	}	
    
    private void makeChildNodes() { // make child nodes depending on the expression given
    	
    	int nodeCounter = 1; // at least 1 node has to be made if this function is called    	
    	
    	if (findNextOperator(0, "+", "-") != -1) { // if addition and subtraction is to be done, the expression will be split into nodes for addition and subtraction first
    		
    		for (int i = 0; i < expression.length(); i++) { // increase node counter for every operator found
    		
    			i = findNextOperator(i, "+", "-");
    		
    			if(i == -1)
    				break;
    		
    			nodeCounter++;
    		}
    	}    	
    	
    	else if (findNextOperator(0, "*", "/") != -1) { // if addition and subtraction is not to be done, then the expression will be split into nodes for multiplication and division
    		
    		for (int i = 0; i < expression.length(); i++) { // increase node counter for every operator found
        		
        		i = findNextOperator(i, "*", "/");
        		
        		if(i == -1)
        			break;
        		
        		nodeCounter++;
        	}
    	}
    	
    	else if (findNextOperator(0, "^") != -1) { // only 2 nodes are to be made for exponentiation	
        		nodeCounter++;
    	}    	
    	
    	else { // nodes for unary operators

    		for (int i = 0; i < expression.length(); i++) {
    		
    			i = findNextOperator(i);

    			if(i == -1)
    				break;
    		
    			nodeCounter++;
    		
    			if(isValidOperator(expression, i + 1) == true) { // if there is an operator directly in front of an operator then another node is not made
    				i += operatorLength(expression, i);
    			}
    		}
    		
    		if(isBinaryOperator(expression, 0) == false && isValidOperator(expression, 0) == true) // if the first operation is unary then make 1 less node
    			nodeCounter--;
    	}
    	    	
    	addNodes(nodeCounter); // add the nodes to the parent    	
    }
    
    
    private void transferToChild() { // expression is broken up and transferred to child nodes
    	
    	
    	if(findNextOperator(0, "+", "-") != -1) { // nodes for addition and subtraction are made first
    		
    		child[0].expression = expression.substring(0, findNextOperator(0, "+", "-")); // expression for first child node
    		int currentIndex = -1;
    		
    		for (int i = 1; i < childNodes - 1; i++) { // transfer expression to each child node
    		
    			currentIndex = findNextOperator(currentIndex + 1, "+", "-"); // change index to the next operator after the current operator
    			child[i].expression = expression.substring(currentIndex, findNextOperator(currentIndex  + 2, "+", "-")); // make a substring from current index to the next operator ignoring any operators directly in front of the current operator
	
    		}
    	
    		child[childNodes - 1].expression = expression.substring(findNextOperator(currentIndex + 1, "+", "-")); // expression for last child node
    	
    		}
    	
    	else if (findNextOperator(0, "*", "/") != -1) {
    		
    		child[0].expression = expression.substring(0, findNextOperator(0, "*", "/")); // expression for first child node
    		int currentIndex = -1;
    		
    		for (int i = 1; i < childNodes - 1; i++) { // transfer expression to each child node
    		
    			currentIndex = findNextOperator(currentIndex + 1, "*", "/"); // change index to the next operator after the current operator
    			child[i].expression = expression.substring(currentIndex, findNextOperator(currentIndex  + 2, "*", "/")); // make a substring from current index to the next operator ignoring any operators directly in front of the current operator
	
    		}
    	
    		child[childNodes - 1].expression = expression.substring(findNextOperator(currentIndex + 1, "*", "/")); // expression for last child node
    	
    		}
    	
    	else if (findNextOperator(0, "^") != -1) { // only two nodes are to be made for exponentiation, for the left and right operand
    		
    		child[0].expression = expression.substring(0, findNextOperator(0, "^")); // expression for first child node
    		child[1].expression = expression.substring(findNextOperator(0, "^")); // expression for second child node
    		
    		}
    	
    	else { // nodes for unary operations
    		
    		child[0].expression = expression.substring(0, findNextOperator(0)); // expression for first child node
    		int currentIndex = -1;

    	
    		for (int i = 1; i < childNodes - 1; i++) { // transfer expression to each child node
    		
    			currentIndex = findNextOperator(currentIndex + 1); // change index to the next operator after the current operator
    			child[i].expression = expression.substring(currentIndex, findNextOperator(currentIndex  + operatorLength(expression, currentIndex) + 1)); // make a substring from current index to the next operator ignoring any operators directly in front of the current operator
    		
    			if(isValidOperator(expression, currentIndex + 1) == true) { // increase the index by 1 to avoid an operator directly in front of current operator
    				currentIndex += operatorLength(expression, currentIndex + 1);
    			}    		
    		}
    	
    		child[childNodes - 1].expression = expression.substring(findNextOperator(currentIndex + 1)); // expression for last child node
    	}
 
    	
    	
    	for (int i = 0; i < childNodes; i++) {
            
    		child[i].separateOperator(); // separate the operator from expression
    		child[i].removeBraces(); // remove any extra braces
    		
    		if(child[i].numberOfOperations() == 0) {
    			child[i].number = Double.parseDouble(child[i].expression); // parse the expression into a double
    			child[i].isComputable = true;
    		}
    	}
    }
    
    private void removeBraces() { // remove extra braces

    		if (expression.length() == 1)
    			return;
    		
    		if (expression.charAt(0) == '(') // remove brace pair if the first character is a left brace
    			expression = removeBracePair(expression, 0);    	
    }    

	private void separateOperator() { // separate operator from expression
		
		if (isValidOperator(expression, 0) == true) {
			operator = expression.substring(0, operatorLength(expression, 0));
			expression = expression.substring(operatorLength(expression, 0));
		}		
	}    
    
    private int findNextOperator(int startingIndex) { // find the next operator from the current index
    	
    	for (int index = startingIndex; index < expression.length(); index++) {
    		
    		if (expression.charAt(index) == '(') { // skip expressions in braces
    			
    				index = findClosingBrace(expression, index);
    				continue;   		
    		}
    		
    		if(isValidOperator(expression, index) == true) // check if there is a valid operator at the current index
    			return index;        		
    		}

    	return -1;    	
    }
    
    private int findNextOperator(int startingIndex, String ... operatorList) { // find the index of the next operator from the given list of operators
    	
    	for (int index = startingIndex; index < expression.length(); index++) {
    		
    		if (expression.charAt(index) == '(') { // skip expressions in braces
    			
    				index = findClosingBrace(expression, index);
    				continue;   		
    		}
    		
    		for(String operator: operatorList) {
    			if(expression.startsWith(operator, index) == true) // check if there is a valid operator at the current index
    				return index;
    		}
    	}

    	return -1; // returns -1 if operator is not found
    }
    
    
    private static int findClosingBrace(String expression, int startingIndex) { // find closing brace given string and starting index
    	
    	int unclosedBraces = 0;
    	
    	for (int i = startingIndex; ; i++) {
    		
    		if (expression.charAt(i) == '(') { // increase number of unclosed braces if left brace is found
    			
    			unclosedBraces++;
    			continue;
    		}
    		
    		if (expression.charAt(i) == ')') { // reduce number of unclosed braces if right brace is found
    			
    			unclosedBraces--;    			
    		}
    		
    		if (unclosedBraces == 0) { // return index of final closing brace
    			
    			return i;
    		}
    	}    	
    }
    
    private String removeBracePair(String expression, int openingBraceIndex) { // remove a pair of braces given expression and starting index
    	
    	int closingBraceIndex = findClosingBrace(expression, openingBraceIndex);
    	
        return expression.substring(0, openingBraceIndex) + expression.substring(openingBraceIndex + 1, closingBraceIndex) + expression.substring(closingBraceIndex + 1); // return string without the brace pair
    	
    }    
	
	private Node getComputableParent() { // get a parent all of whose children are computable
		
 		boolean allChildNodesComputable = true;
		int nodeNotComputable = -1;
		
		for (int i = 0; i < childNodes; i++) { // check all child nodes whether they are computable, break if one non-computable node is found
			
			if(child[i] == null)
				continue;
			
			if(child[i].isComputable != true) {
				allChildNodesComputable = false;
				nodeNotComputable = i;
				break;			
			}
			
		}
		
		if (allChildNodesComputable == true) // return this node if all of its children are computable
			return this;
		else
			return child[nodeNotComputable].getComputableParent(); // call the function for the non-computable child if there is one
			
	}

	public static boolean isValidOperator(String expression, int startingIndex) { // given an index, check if it is the first index of a valid operator
		
		for (String operator: validOperators)
			if(expression.startsWith(operator, startingIndex) == true) {
				
				return true;				
			}
		
		return false;
	}
	
	public static boolean isBinaryOperator(String expression, int startingIndex) { // given an index, check if it is the first index of a valid binary operator
		
		for (String operator: validBinaryOperators)
			if(expression.startsWith(operator, startingIndex) == true) {
				return true;
			}
		return false;	
	
	}	
	
	private static int operatorLength(String expression, int startingIndex) { // return the length of the operator
		
		for (String operator: validOperators)
			if(expression.startsWith(operator, startingIndex) == true)
				return operator.length();
		
		return -1;
	}

	private int numberOfOperations() { // calculate number of operators in the expression
	
		int operations = 0;
	
		for (int i = 0; i < expression.length(); i++) 		
			if(isValidOperator(expression, i) == true) // increase the number of operations if a valid operator is found
				operations++;
	
		return operations;
	}
	
	private int siblings() { // returns the number of non-null siblings of a node		
		
		int siblings = -1; // initialized to -1 as the node itself will be counted as a sibling
		
		for(int i = 0; i < parent.childNodes; i++) {
			
			if(parent.child[i] == null)
				continue;
			else
				siblings++;
			
		}		
		return siblings;		
	}
}
