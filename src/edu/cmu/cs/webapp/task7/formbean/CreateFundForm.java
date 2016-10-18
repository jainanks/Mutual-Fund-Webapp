package edu.cmu.cs.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean {
	private String fundName;
	private String ticker;
	private long iniPrice;
	private String action;

	public void setFundName(String v) {
		String source = v.trim();
		fundName = source.substring(0, 1).toUpperCase() + source.substring(1).toLowerCase();
	    }


	public void setTicker(String v) {
		String caps = v.trim();
		ticker = caps.toUpperCase();
	}

	public void setAction(String s) {
		action = s;
	}

	public String getFundName() {
		return fundName;
	}

	public String getTicker() {
		return ticker;
	}

	public String getAction() {
		return action;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (fundName == null || fundName.length() == 0)
			errors.add("Fund Name is required");
		if (ticker == null || ticker.length() == 0)
			errors.add("Ticker is required");
		if (action == null)
			errors.add("Button is required");

		if (errors.size() > 0)
			return errors;

		if (fundName.matches(".*[<>;\"].*"))
			errors.add("Fund Name may not contain angle brackets or quotes");
		if (ticker.matches(".*[<>\"];.*"))
			errors.add("Ticker Name may not contain angle brackets or quotes");

		if (!action.equals("create"))
			errors.add("Invalid button");

		return errors;
	}

	private String sanitize(String s) {
		return s.replace("&", "&amp;").replace("<", "&lt;")
				.replace(">", "&gt;").replace("\"", "&quot;");
	}
}
