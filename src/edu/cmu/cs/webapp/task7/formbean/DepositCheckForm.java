package edu.cmu.cs.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean {
	private String userName1;
	private String userName2;
	private String amount;
	private String confAmount;
	private String action;

	public void setUserName1(String s) {
		userName1 = s.trim();
	}

	public void setUserName2(String s) {
		String source = s.trim();
		userName2 = source.toLowerCase();
	}

	public void setAmount(String s) {
		amount = s.trim();
	}

	public void setConfAmount(String s) {
		confAmount = s.trim();
	}

	public void setAction(String s) {
		action = s;
	}

	public String getUserName1() {
		return userName1;
	}

	public String getUserName2() {
		return userName2;
	}

	public String getAmount() {
		return amount;
	}

	public String getConfAmount() {
		return confAmount;
	}

	public String getAction() {
		return action;
	}

	public String getUserName() {
		if (userName1 != null && userName1.length() > 0)
			return userName1;
		else
			return userName2;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if ((userName1 == null || userName1.length() == 0)
				&& (userName2 == null || userName2.length() == 0))
			errors.add("Username is required");
		if (amount == null || amount.length() == 0)
			errors.add("Amount is required");
		if (confAmount == null || confAmount.length() == 0)
			errors.add("Confirm Amount is required");
		if (action == null)
			errors.add("Button is required");

		if (errors.size() > 0)
			return errors;
		
		if ((userName1 !=  null && userName2 != null && userName1.trim().length() != 0 && userName2.trim().length() != 0 && !userName1.trim().equals(userName2.trim()))) {
			errors.add("User names are inconsistent");
		}

		if (!action.equals("deposit"))
			errors.add("Invalid button");
		if (userName1.matches(".*[<>\"].*") && userName2.matches(".*[<>\"].*"))
			errors.add("User Name contains invalid characters: *[<. ");
		{	
			try {
				double d = Double.parseDouble(amount);
		    	//2 digit allowed!
		    	int lastDotIndex = amount.lastIndexOf(".");
		    	if (lastDotIndex != -1 && 
		    			amount.substring( lastDotIndex + 1 ).length() > 2  && 
		    			Integer.parseInt(amount.substring( lastDotIndex + 1 )) != 0){
					errors.add("Check amount error. Maximum 2 decimals are allowed.");
				}
		    	else if (d < 0.01 || d > 1000000000){
			    	errors.add("Amount must be between one cent (0.01) and a billion (1,000,000,000.00).");
			    } 
			} catch (Exception e) {
				errors.add("The amount entered is not valid.");
			}
			
		}
		
		if (errors.size() > 0)
			return errors;
		
		if (Double.parseDouble(amount) != Double.parseDouble(confAmount))
			// if (!amount.equals(confAmount))
			errors.add("Amounts must be identical");

		return errors;
	}

}