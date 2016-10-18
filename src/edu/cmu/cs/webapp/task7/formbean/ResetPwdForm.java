 
package edu.cmu.cs.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ResetPwdForm extends FormBean {

	private	String customer;
	private String newPwd;
	private String confPwd;
	private String action2;
	
	public void setCustomer(String s)     { customer     = s.trim(); }
	public void setNewPwd(String s)     { newPwd     = s.trim(); }
	public void setConfPwd(String s) { confPwd = s.trim(); }
	public void setAction2(String s) { action2 = s; }
	
	public String getNewPwd()     { return newPwd;     }
	public String getConfPwd()     { return confPwd;     }
	public String getCustomer()     { return customer;     }
	public String getAciton2()		{ return action2; }
	
	public boolean isActionPresent() { return action2 != null; }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		
		
		if (newPwd == null || newPwd.length() == 0) {
			errors.add("New password is required");
		}
		
		if (confPwd == null || confPwd.length() == 0) {
			errors.add("Confirm Password is required");
		}
		if (action2 == null || action2.length() == 0) {
			errors.add("button is required");
		}
		
		if (errors.size() > 0) {
			return errors;
		}
		
		if (!newPwd.equals(confPwd)) {
			errors.add("Passwords do not match");
		}
		
		if (!action2.equals("reset")){
			errors.add("Invalid button");
		}

		return errors;
	}
}


