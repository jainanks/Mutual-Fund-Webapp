package edu.cmu.cs.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import java.util.regex.Pattern;



public class BuyForm extends FormBean {
	// private String amount;
	private String amount;
	private String fund;
	private String fund2;
	private String action;

	public String getAmount() {
		return amount;
	}
	
	public String getAction () {
		return action;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getFundName() {
		if (fund != null && fund.length() > 0)
			return fund;
		else
			return fund2;
	}
	
	public void setAction (String v) {
		action = v;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (amount == null || amount.length() == 0) {
			errors.add("Please enter an amount.");
		} else if (!checkDecimal(amount)) {
			errors.add("A maximum of 2 decimal places are allowed");
		}
		if (amount != null && amount.matches(".*[<>\"].*"))
			errors.add("Buy amount format error");
		
		if ((fund == null || fund.length() == 0)
				&& (fund2 == null || fund2.length() == 0)) {
			errors.add("Please choose a fund");
		}
		
		if ((fund !=  null && fund2 != null && fund.trim().length() != 0 && fund2.trim().length() != 0  && !fund.trim().equals(fund2.trim()))) {
			errors.add("Fund names are inconsistent");
		}
		
		if (action == null) {
			errors.add("Button is required");
		}

		if (errors.size() > 0)
			return errors;
		try {

			double amt = Double.parseDouble(amount);
			amt = Math.round(amt * 100);
			amt = amt / 100;

			if (amt < 10) {
				errors.add("Please enter an amount greater than $10");
			} else if (amt > 1000000000) {
				errors.add("Please enter an amount lesser than one billion ($1,000,000,000.00)");
			}
		} catch (NumberFormatException nfe) {
			errors.add("Please enter amount in digits only.");
		}
		
		if (!action.equals("buy")) {
			errors.add("Invalid button");
		}

		return errors;
	}

	public boolean checkDecimal(String input) {
		Pattern p = Pattern.compile("[+-]?[0-9]+.{0,1}[0-9]{0,2}");
		return p.matcher(input).matches();
	}

	public String getFund2() {
		return fund2;
	}

	public void setFund2(String fund2) {
		this.fund2 = fund2;
	}
}
