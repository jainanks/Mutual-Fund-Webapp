package edu.cmu.cs.webapp.task7.controller;


import javax.servlet.http.HttpServletRequest;

import edu.cmu.cs.webapp.task7.model.CustomerDAO;
import edu.cmu.cs.webapp.task7.model.EmployeeDAO;
import edu.cmu.cs.webapp.task7.model.Model;


public class AccountManageE extends Action {
	private EmployeeDAO employeeDAO;

	public AccountManageE(Model model) {
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return "accountManageE.do";
	}

	public String perform(HttpServletRequest request) {
		return "accountManageE.jsp";
	}
}
