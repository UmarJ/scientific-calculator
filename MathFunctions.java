import java.lang.Math;

public class MathFunctions { // class for mathematical functions

	public static double addition(double a, double b) {
		
		return a + b;
	}
	
	public static double subtraction(double a, double b) {
		
		return a - b;
	}
	
	public static double multiplication(double a, double b) {
		
		return a * b;
	}
	
	public static double division(double a, double b) {
	
		return a / b;
	}
	
	public static double exponent(double a, double b) {
		
		return Math.pow(a, b);
	}
	
	public static double sine(double a) {
		
		return Math.sin(a);
	}
	
	public static double cosine(double a) {
		
		return Math.cos(a);
	}	
	
	public static double tangent(double a) {
		
		return Math.tan(a);
	}
	
	public static double log(double a) {
		
		return Math.log10(a);		
	}
	
	public static double naturalLog(double a) {
		
		return Math.log(a);
	}
	
	public static double arcSine(double a) {
		
		return Math.asin(a);
	}
	
	public static double arcCosine(double a) {
		
		return Math.acos(a);
	}
	
	public static double arcTangent(double a) {
		
		return Math.atan(a);
	}
		
	public static double absolute(double a) {
		
		return Math.abs(a);
	}
	
	public static double sinh(double a) {
		
		return Math.sinh(a);
	}
	
	public static double cosh(double a) {
		
		return Math.cosh(a);
	}
	
	public static double tanh(double a) {
		
		return Math.tanh(a);
	}
	
	public static double negation(double a) {
		
		return -a;
	}
	
	public static double squareRoot(double a) {
		
		return Math.sqrt(a);
	}

	public static double cubeRoot(double a) {
	
		return Math.cbrt(a);
	}	
}
