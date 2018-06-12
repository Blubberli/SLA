package de.ws.shared;

/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 * <p>
 * This class is in the <code>shared</code> package because we use it in both
 * the client code and on the server. On the client, we verify that the name is
 * valid before sending an RPC request so the user doesn't have to wait for a
 * network round trip to get feedback. On the server, we verify that the name is
 * correct to ensure that the input is correct regardless of where the RPC
 * originates.
 * </p>
 * <p>
 * When creating a class that is used on both the client and the server, be sure
 * that all code is translatable and does not use native JavaScript. Code that
 * is not translatable (such as code that interacts with a database or the file
 * system) cannot be compiled into client-side JavaScript. Code that uses native
 * JavaScript (such as Widgets) cannot be run on the server.
 * </p>
 */
public class FieldVerifier {

	/**
	 * Verifies that the specified name is valid for our service.
	 * 
	 * In this example, we only require that the name is at least four
	 * characters. In your application, you can use more complex checks to ensure
	 * that usernames, passwords, email addresses, URLs, and other fields have the
	 * proper syntax.
	 * 
	 * @param name the name to validate
	 * @return true if valid, false if invalid
	 */
	public static boolean isValidName(String name) {
		if (name == null) {
			return false;
		}
		return name.length() > 2;
	}
	public static boolean isValidPassword2(String psw) {
		if(!psw.matches(".*\\d+.*")) {
			return false;
		}
		return true;
	}
	public static boolean isValidPassword1(String psw) {
		if(psw.length()<7) {
			return false;
		}
		return true;
	}
	public static boolean isValidPassword3(String psw) {
		if(!psw.matches(".*[a-z].*")) {
			return false;
		}
		return true;
	}
	
	public static boolean passwordRepeatedCorrectly(String psw1, String psw2) {
		if(!psw1.equals(psw2) || psw1 == null || psw2 == null) {
			return false;
		}
		return true;
	}
}
