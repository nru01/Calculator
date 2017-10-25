
import java.lang.Math;

public class Expression {
	
	private String expr;
	private static final int MAX_ARGUMENTS = 4;
	private static final String[] OPERATIONS = {"!","^","*","/","%","\\","+","-"};
	private static final int[] PRECEDENCE = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
											0,4,0,0,0,2,0,0,0,0,2,1,0,1,0,2,
											0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,0,0,0,0,2,0,3,0,
											0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
											0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,};
	public Expression(String expr) {
		String error = checkForErrors(expr);
		if (error != null) {
			systemErr(error);
			this.expr = "";
		} else {
			this.expr = expr;
		}
	}
	public Expression(Expression e) {
		String error = checkForErrors(e.expr);
		if (error != null) {
			systemErr(error);
			this.expr = "";
		} else {
			this.expr = e.expr;
		}
	}
	
	/**
	 * Evaluates the current expression
	 * @return The simplified expression
	 */
	public String evaluate() {
		return solve(this.expr);
	}
	/**
	 * Evaluates expr.
	 * @param expr A mathematical expression as a String.
	 * @return A number mathematically equivalent to expr.
	 */
	private static String solve(String expr) {
		String newExpr = new String(expr.trim());
		boolean isNegative = true;
		char chr1;
		int[] ops;
		double[] getnumReturn;
		String[] strDeleteRtrn;
		String[] arguments = new String[MAX_ARGUMENTS]; int argCounter = 0;
		for (int index = 0; index<newExpr.length(); index++) {
			chr1 = newExpr.charAt(index);
			if (isNumeric(chr1)) {
				getnumReturn = getNumber(newExpr, index);
				arguments[argCounter] = String.valueOf(getnumReturn[0]);
				strDeleteRtrn = deleteString(newExpr, index, (int) getnumReturn[1]);
				newExpr = strDeleteRtrn[0];
				index = Integer.valueOf(strDeleteRtrn[1]);
			}
			if (isOperation(String.valueOf(chr1))) {
				
			}
			
		}
		return newExpr;
	}
	
	/**
	 * Does the operation on the arguments and returns the value.
	 * @param operation
	 * @param arguments
	 * @return
	 */
	public static String doOperation(String operation, String[] arguments) {
		String value = "0";
		double[] args = new double[arguments.length];
		for (int i = 0; i<arguments.length; i++) {
			args[i] = Double.valueOf(arguments[i]);
		}
		
		
		switch (operation) {
		case "!":
			value = fact(arguments[0]);
			break;
		case "^":
			value = String.valueOf(Math.pow(args[0], args[1]));
			break;
		case "*":
			value = String.valueOf((args[0]*args[1]));
			break;
		case "/":
			value = String.valueOf((args[0]/args[1]));
			break;
		case "\\":
			value = String.valueOf((args[1]/args[0]));
			break;
		case "%":
			value = String.valueOf((args[0]%args[1]));
			break;
		case "+":
			value = String.valueOf((args[0]+args[1]));
			break;
		case "-":
			value = String.valueOf((args[0]-args[1]));
			break;
		}
		
		
		return value;
	}
	/**
	 * Takes the factorial of the argument
	 * @param arg String of the double.
	 * @return The arg as an integer times all numbers before it until 0.
	 */
	public static String fact(String arg) {
		long product = 1;
		double a = (Double.valueOf(arg));
		int argument = (int) a;
		if (argument<0) argument*=-1;
		if (argument!=0) {
			for (int i = argument; i>0; i--) {
				product*=i;
			}
		}
		return String.valueOf(product);
	}
	
	/**
	 * 
	 * @param str
	 * @param beg index
	 * @param end index
	 * @return A string with the portion deleted and the new index after portion deleted.
	 */
	public static String[] deleteString(String str, int beg, int end) {
		int newInd = end;
		String newStr = str;
		
		newStr = newStr.substring(0, beg)+newStr.substring(end);
		newInd -= end-beg;
		String[] rtrn = {newStr, String.valueOf(newInd)};
		return rtrn;
	}
	/**
	 * Checks the expression for errors.
	 * @param expression The expression to be checked as a String.
	 * @return Returns the error message of the error that is present.
	 * If there is no error present null is returned.
	 */
	private static String checkForErrors(String expression) {
		String error = null;
		if (!parenthesisMatch(expression)) {
			error = "Parenthesis out of order: missing left or right parenthesis.";
		} else if (!squareBracketsMatch(expression)) {
			error = "Square bracket out of order: missing left or right bracket.";
		} else if (!curlyBracketsMatch(expression)) {
			error = "Curly bracket out of order: missing left or right bracket.";
		}
		
		return error;
	}
	/**
	 * Fixes (fix-able) syntax errors.
	 * @param expression A mathematical expression.
	 * @return The new expression without the (fix-able) errors.
	 */
	private static String fixSyntaxErrors(String expression) {
		String newExpression = expression.trim();
		
		return newExpression;
	}
	
	/**
	 * @param expression A mathematical expression as a String.
	 * @return Returns true if the expression has correctly formatted parenthesis.
	 */
	private static boolean parenthesisMatch(String expression)
	{
	    int pc = 0;
	    int pm = 1;
	    for (int i = 0, chr1; i<expression.length(); i++) {
	        chr1 = expression.charAt(i);
	        if (chr1 == '(') pc++;
	        else if (chr1 == ')') pc--;
	        if (pc<0) pm = 0;
	    }
	    if (pc!=0) pm = 0;
	    return pm != 0;
	}
	/**
	 * @param expression A mathematical expression as a String.
	 * @return Returns true if the expression has correctly formatted square brackets.
	 */
	private static boolean squareBracketsMatch(String expression)
	{
	    int sbc = 0;
	    int sbm = 1;
	    for (int i = 0, chr1; i<expression.length(); i++) {
	        chr1 = expression.charAt(i);
	        if (chr1 == '[') sbc++;
	        else if (chr1 == ']') sbc--;
	        if (sbc<0) sbm = 0;
	    }
	    if (sbc!=0) sbm = 0;
	    return sbm != 0;
	}
	/**
	 * @param expression A mathematical expression as a String.
	 * @return Returns true if the expression has correctly formatted curly brackets.
	 */
	private static boolean curlyBracketsMatch(String expression)
	{
	    int cbc = 0;
	    int cbm = 1;
	    for (int i = 0, chr1; i<expression.length(); i++) {
	        chr1 = expression.charAt(i);
	        if (chr1 == '{') cbc++;
	        else if (chr1 == '}') cbc--;
	        if (cbc<0) cbm = 0;
	    }
	    if (cbc!=0) cbm = 0;
	    return cbm != 0;
	}
	
	/**
	 * @param expr A mathematical expression.
	 * @param index The index where the number starts.
	 * @return An array of the number followed by the index after it ends.
	 */
	private static double[] getNumber(String expr, int index) {
		int newIndex = expr.length(), decCount = 0;
		boolean isExcess = false, isFloat = false, isScientific = false, negativeTaken = false;
		String num = "", excess = "";
		char chr1;
		for (int i = index; i<expr.length(); i++) {
			chr1 = expr.charAt(i);
			if (isNumeric(chr1)) {
				if (!isExcess)
					num+=chr1;
				else 
					excess+=chr1;
				if (isFloat) {
					decCount++;
				}
			} else if (isDecimal(chr1)) {
				isFloat = true;
				num+=chr1;
			} else if (chr1 == 'E') {
				isScientific = true;
				isExcess = false;
				num+=chr1;
			} else if (isScientific && chr1 == '-' && !negativeTaken) {
				num+=chr1;
				negativeTaken = true;
			} else {
				newIndex = i-1;
				break;
			}
			if (decCount==15) {
				isExcess = true;
			}
		}
		double[] rtrn = {Double.valueOf(num), newIndex};
		return rtrn;
	}
	/**
	 * @param startIndex The index of the expression where the square brackets come after
	 * @param expression The expression
	 * @return The expression inside of the square brackets after the startIndex.
	 */
	private static int[] insideExpressionOfSquareAt(int startIndex, String expression) {
		
		int parenthesisCount = 0;
		int endIndex = 0;
		@SuppressWarnings("unused")
		String newExpression;
		boolean endOfExpression = false;
		for (int i = startIndex; !endOfExpression; i++) {
			
			if (expression.substring(i, i+1).equals("[")) {
				parenthesisCount++;
				if (parenthesisCount == 1) {
					startIndex = i+1;
				}
			}
			
			if (expression.substring(i, i+1).equals("]")) {
				parenthesisCount--;
				if (parenthesisCount == 0) {
					endIndex = i;
					newExpression = expression.substring(startIndex, endIndex);
					endOfExpression = true;
				}
			}
		}
		int[] rtrn1 = new int[2];
		rtrn1[0] = startIndex-1; rtrn1[1] = endIndex+1;
		return rtrn1; // returns the beg. and end of parenthesis // changed from newExpression
	}/**
	 * @param startIndex The index of the expression where the curly brackets come after
	 * @param expression The expression
	 * @return The expression inside of the curly brackets after the startIndex.
	 */
	private static int[] insideExpressionOfCurlyAt(int startIndex, String expression) {
		
		int parenthesisCount = 0;
		int endIndex = 0;
		@SuppressWarnings("unused")
		String newExpression;
		boolean endOfExpression = false;
		for (int i = startIndex; !endOfExpression; i++) {
			
			if (expression.substring(i, i+1).equals("{")) {
				parenthesisCount++;
				if (parenthesisCount == 1) {
					startIndex = i+1;
				}
			}
			
			if (expression.substring(i, i+1).equals("}")) {
				parenthesisCount--;
				if (parenthesisCount == 0) {
					endIndex = i;
					newExpression = expression.substring(startIndex, endIndex);
					endOfExpression = true;
				}
			}
		}
		int[] rtrn1 = new int[2];
		rtrn1[0] = startIndex-1; rtrn1[1] = endIndex+1;
		return rtrn1; // returns the beg. and end of parenthesis // changed from newExpression
	}
	/**
	 * @param startIndex The index of the expression where the parenthesis come after
	 * @param expression The expression
	 * @return The expression inside of the parenthesis after the startIndex.
	 */
	private static int[] insideExpressionAt(int startIndex, String expression) {
		
		int parenthesisCount = 0;
		int endIndex = 0;
		@SuppressWarnings("unused")
		String newExpression;
		boolean endOfExpression = false;
		for (int i = startIndex; !endOfExpression; i++) {
			
			if (expression.substring(i, i+1).equals("(")) {
				parenthesisCount++;
				if (parenthesisCount == 1) {
					startIndex = i+1;
				}
			}
			
			if (expression.substring(i, i+1).equals(")")) {
				parenthesisCount--;
				if (parenthesisCount == 0) {
					endIndex = i;
					newExpression = expression.substring(startIndex, endIndex);
					endOfExpression = true;
				}
			}
		}
		int[] rtrn1 = new int[2];
		rtrn1[0] = startIndex-1; rtrn1[1] = endIndex+1;
		return rtrn1; // returns the beg. and end of parenthesis // changed from newExpression
	}
	/**
	 * @return A String[] of all strings inside of parenthesis in this.expr.
	 */
	public String[] insideExpressions() {
		
		int startIndex = 0;
		int endIndex = 0;
		int parenthesisCount = 0;
		int expCount = 0;
		
		
		for (int i = 0; i<=this.expr.length()-1; i++) {
			
			if (this.expr.substring(i, i+1).equals("(")) {
				parenthesisCount++;
			}
			
			if (this.expr.substring(i, i+1).equals(")")) {
				parenthesisCount--;
				if (parenthesisCount == 0) {
					expCount++;
				}
			}
		}
		
		String expression[] = new String[expCount];
		int expIndex = 0;
		parenthesisCount = 0;
		
		for (int i = 0; i<=this.expr.length()-1; i++) {
			
			if (this.expr.substring(i, i+1).equals("(")) {
				parenthesisCount++;
				if (parenthesisCount == 1) {
					startIndex = i+1;
				}
			}
			
			if (this.expr.substring(i, i+1).equals(")")) {
				parenthesisCount--;
				if (parenthesisCount == 0) {
					endIndex = i;
					expression[expIndex] = this.expr.substring(startIndex, endIndex);
					expIndex++;
				}
			}
		}
		
		return expression;
	}
	
	/**
	 * @param c A character
	 * @return Returns true if the character, c, is a number.
	 */
	private static boolean isNumeric(char c) {
		boolean isN = false;
		if (c>='0'&&c<='9')
			isN = true;
		return isN;
	}
	/**
	 * @param c A character
	 * @return Returns true if the character, c, is a decimal point.
	 */
	private static boolean isDecimal(char c) {
		boolean isD = false;
		if (c=='.')
			isD = true;
		return isD;
	}
	/**
	 * @param c A character
	 * @return Returns true if the character, c, is an alpha-numerical character.
	 */
	private static boolean isAlphaNumeric(char c) {
		boolean isAN = false;
		if (c>='0'&&c<='9'||c>='A'&&c<='Z'||c>='a'&&c<='z')
			isAN = true;
		return isAN;
	}
	/**
	 * @param c A character
	 * @return Returns true if the character, c, is an alpha.
	 */
	private static boolean isAlpha(char c) {
		boolean isA = false;
		if (c>='A'&&c<='Z'||c>='a'&&c<='z')
			isA = true;
		return isA;
	}
	private static boolean isOperation(String op) {
		boolean isO = false;
		for (int i = 0; i<OPERATIONS.length; i++) {
			isO = (op.equals(OPERATIONS[i]))? true: isO;
		}
		
		return isO;
	}
	
	/**
	 * @param Message outputs the message to the screen.
	 */
	private static void systemOut(String message) {
		System.out.println("\t"+message);
	}
	/**
	 * @param Message outputs the message as an error, to the screen.
	 */
	private static void systemErr(String message) {
		System.err.println("\t"+message);
	}
}
