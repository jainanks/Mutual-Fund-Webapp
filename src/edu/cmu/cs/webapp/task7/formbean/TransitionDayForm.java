
package edu.cmu.cs.webapp.task7.formbean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import org.mybeans.form.FormBean;

public class TransitionDayForm /* extends FormBean*/ {
	private String date;
	private String action;

	public TransitionDayForm(HttpServletRequest request) {
		action = request.getParameter("action");
		date	= request.getParameter("date");
	}

	public String getDate ()					{	return date;		}
	public String getAction()		 			{	return action;	}
	
	public boolean isPresent() {	return action != null; }
	
	// HashMap<String, Long> map: 
	// String:	    name in the jsp, related to fundid
	// String:		price
	public List<String> getValidationErrors (HashMap<String, String> map) {
		List<String> errors = new ArrayList<String>();

		if (date == null || date.length() == 0 )
			errors.add("Date is required");
		if (action == null) errors.add("Button is required");

		if (errors.size() > 0) 	return errors;
        if (!action.equals("create")) errors.add("Invalid button");
        
        try {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	dateFormat.setLenient(false);
        	dateFormat.parse(date);
        } catch (Exception e) {
        	errors.add("Invalid date input");
        }
        
        for (String price : map.values()) {
        	
			try {
				double d = Double.parseDouble(price);
		    	//2 digit allowed!
		    	int lastDotIndex = price.lastIndexOf(".");
		    	if (lastDotIndex != -1 && 
		    			price.substring( lastDotIndex + 1 ).length() > 2  && 
		    			Integer.parseInt(price.substring( lastDotIndex + 1 )) != 0){
					errors.add("Price format error!");
				}
		    	else if (d < 0.01 || d > 10000){
			    	errors.add("Price of shares must be between one cent (0.01) and ten thousand (10,000.00)");
			    } 
			} catch (Exception e) {
				errors.add("Price format error!");
			}        
        }
    
		return errors;
	}
}