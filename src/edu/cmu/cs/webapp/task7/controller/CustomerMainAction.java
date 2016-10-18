package edu.cmu.cs.webapp.task7.controller;


import javax.servlet.http.HttpServletRequest;

import edu.cmu.cs.webapp.task7.model.CustomerDAO;
import edu.cmu.cs.webapp.task7.model.Model;


public class CustomerMainAction extends Action {
	private CustomerDAO customerDAO;

	public CustomerMainAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "customerMain.do";
	}

	public String perform(HttpServletRequest request) {
		return "customerMain.jsp";
	}
}
