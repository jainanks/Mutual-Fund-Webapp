package edu.cmu.cs.webapp.task7.controller;


import javax.servlet.http.HttpServletRequest;

import edu.cmu.cs.webapp.task7.model.CustomerDAO;
import edu.cmu.cs.webapp.task7.model.Model;


public class AccountManageC extends Action {
	private CustomerDAO customerDAO;

	public AccountManageC(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "accountManageC.do";
	}

	public String perform(HttpServletRequest request) {
		return "accountManageC.jsp";
	}
}
