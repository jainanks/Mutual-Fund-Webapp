
package edu.cmu.cs.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class LoginForm extends FormBean {
	private String userName;
	private String password;
	private String action;

	public void setUserName(String s) { userName = s.trim(); }
	public void setPassword(String s) { password = s.trim();	}
	public void setAction(String s) {	action = s;	}
	
	public String getUserName() {	return userName;	}
	public String getPassword() {		return password;	}
	public String getAction()		 {		return action;	}
	
	public boolean isEmployee() { return action != null && action.equals("employee");}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (userName == null || userName.length() == 0)
			errors.add("Username is required");
		if (password == null || password.length() == 0)
			errors.add("Password is required");
		if (action == null)
			errors.add("Button is required");

		if (errors.size() > 0)
			return errors;
		
        if (!action.equals("customer") && !action.equals("employee")) errors.add("Invalid button");
        if (userName.matches(".*[<>\"].*")) errors.add("User Name may not contain angle brackets or quotes");

		return errors;
	}

}