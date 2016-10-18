package edu.cmu.cs.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateEmployeeForm extends FormBean {
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String confirmPassword ;
	private String action;
	private String agree;
	
	public String getFirstName()	 { return firstName; }
	public String getLastName() 	 { return lastName;  }
	public String getUserName() 	 { return userName;  }
	public String getPassword()  { return password;  }
	public String getConfirmPassword()   { return confirmPassword;   }
	public String getAction ()	{ return action;}
	public String getAgree () 	{ return agree;}
	
	public void setFirstName(String s) { firstName = s.trim();  }
	public void setLastName(String s)  { lastName = s.trim();  }
	public void setUserName(String s)  { userName = s.trim(); } 
	public void setPassword(String s)  { password  = s.trim();                  }
	public void setConfirmPassword(String s)  { confirmPassword  = s.trim();      }
	public void setAction(String s)  { action  = s;  }
	public void setAgree(String s)  { agree  = s;   }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (userName == null || userName.length() == 0)
			errors.add("User Name is required");
		if (firstName == null || firstName.length() == 0)
			errors.add("First Name is required");
		if (lastName == null || lastName.length() == 0)
			errors.add("Last Name is required");
		if (password == null || password.length() == 0)
			errors.add("Password is required");
		if (confirmPassword == null || confirmPassword.length() == 0)
			errors.add("Confirm password is required");
		if (!password.equals(confirmPassword)) 
			errors.add("Passwords are not the same");
		if (action == null)
			errors.add("Button is required");

		if (errors.size() > 0)
			return errors;

		if (userName.matches(".*[<>\"].*"))
			errors.add("User Name may not contain angle brackets or quotes");
		if (firstName.matches(".*[<>\"].*"))
			errors.add("First Name may not contain angle brackets or quotes");
		if (lastName.matches(".*[<>\"].*"))
			errors.add("Last Name may not contain angle brackets or quotes");
		if (password.matches(".*[<>\"].*"))
			errors.add("Password may not contain angle brackets or quotes");
		if (!action.equals("create"))
			errors.add("Invalid button");
		
		return errors;
	}
	private String sanitize(String s) {
    	return s.replace("&", "&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
	}
}
