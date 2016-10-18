package edu.cmu.cs.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ChangePwdForm extends FormBean {
	private String currPwd;
	private String newPwd;
	private String confPwd;
	private String action;
	
	public void setCurrPwd(String s) { currPwd = s.trim(); }
	public void setNewPwd(String s)     { newPwd     = s.trim(); }
	public void setConfPwd(String s) { confPwd = s.trim(); }
	public void setAction(String s) 			 { action  = s;  }
	
	public String getCurrPwd() { return currPwd; }
	public String getNewPwd()     { return newPwd;     }
	public String getConfPwd()     { return confPwd;     }
	public String getAction		   ()	{ return action;}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (currPwd == null || currPwd.length() == 0) {
			errors.add("Original password is required");
		}
		
		if (newPwd == null || newPwd.length() == 0) {
			errors.add("New password is required");
		}
		
		if (confPwd == null || confPwd.length() == 0) {
			errors.add("Confirm password is required");
		}
		
		if (action == null)
			errors.add("Button is required");
		
		if (errors.size() > 0) {
			return errors;
		}
		
		if (!newPwd.equals(confPwd)) {
			errors.add("Passwords do not match");
		}
		
		if (!action.equals("change"))
			errors.add("Invalid button");

		return errors;
	}
}
