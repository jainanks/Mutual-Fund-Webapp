package edu.cmu.cs.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ResearchFundForm extends FormBean {
	private String fundName;
	
	private String action; // can be Search or Research Fund
	private String fromTime; // will not implement in first version
	private String toTime; // will not implement in first version
	private String fund2;	
	
	public String getFundName() {
		return fundName;
	}
	public String getFund1() {
		
		if (fundName != null && fundName.length() > 0)
			return fundName;
		else
			return fund2;
	}
	

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		// this is the first time entering this page
		if (action == null) {
			return errors;
		} else if (action.equals("Fund History")) {
			if (fundName == null || fundName.length() == 0 ) {
				errors.add("Fund has not been specified.");
				return errors;
			}
			
			if ((fundName !=  null && fund2 != null && fundName.trim().length() != 0 && fund2.trim().length() != 0 && !fundName.trim().equals(fund2.trim()))) {
				errors.add("Fund names inconcistency");
			}
			
		} else {
			errors.add("The operation(" + action + ") is not allowed.");

		}
		
		return errors;
	}

	public String getAction() {
		
		return action;
	}

	public void setAction(String button) {
		this.action = button;
		
	}


	public String getFund2() {
		return fund2;
	}


	public void setFund2(String fund2) {
		this.fund2 = fund2;
	}
	
	
}

