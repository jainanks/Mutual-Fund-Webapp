package edu.cmu.cs.webapp.task7.controller;


import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import edu.cmu.cs.webapp.task7.model.EmployeeDAO;
import edu.cmu.cs.webapp.task7.model.Model;


public class EmployeeMainAction extends Action {
	private EmployeeDAO employeeDAO;

	public EmployeeMainAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return "employeeMain.do";
	}

	public String perform(HttpServletRequest request) {
		return "employeeMain.jsp";
	}
}
