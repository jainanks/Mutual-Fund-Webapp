package edu.cmu.cs.webapp.task7.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.cs.webapp.task7.databean.CustomerBean;
import edu.cmu.cs.webapp.task7.model.CustomerDAO;
import edu.cmu.cs.webapp.task7.model.Model;


public class SuccessAction extends Action {

	public SuccessAction(Model model) {
	}

	public String getName() {
		return "success.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			if (session.getAttribute("user") instanceof CustomerBean) {
				request.setAttribute("msg", session.getAttribute("msg"));
				session.removeAttribute("msg");
				return "confirmationPageCus.jsp";
			}
			else {
				request.setAttribute("msg", session.getAttribute("msg"));
				session.removeAttribute("msg");
				return "confirmationPageEmp.jsp";
			}
		}
		else {
			return "login.do";
		}
	}
}
