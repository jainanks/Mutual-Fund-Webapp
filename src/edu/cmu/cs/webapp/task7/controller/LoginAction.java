package edu.cmu.cs.webapp.task7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.task7.databean.CustomerBean;
import edu.cmu.cs.webapp.task7.databean.EmployeeBean;
import edu.cmu.cs.webapp.task7.formbean.LoginForm;
import edu.cmu.cs.webapp.task7.model.CustomerDAO;
import edu.cmu.cs.webapp.task7.model.EmployeeDAO;
import edu.cmu.cs.webapp.task7.model.Model;


public class LoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);

	private EmployeeDAO employeeDAO;
	private CustomerDAO customerDAO;

	public LoginAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "login.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		// If user is already logged in, redirect to favorite.do
		if (session.getAttribute("user") != null) {
			if (session.getAttribute("user") instanceof CustomerBean) {
				return "viewAccount.do";
			}
			else {
				return "employeeMain.do";
			}
		}

		try {
			LoginForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "login.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "login.jsp";
			}

			// Look up the user
			if (form.isEmployee()) {
				EmployeeBean user = employeeDAO.read(form.getUserName());
				
				if (user == null) {
					errors.add("User Name not found");
					return "login.jsp";
				}

				if (!user.getPassword().equals(form.getPassword())) {
					errors.add("Incorrect password");
					return "login.jsp";
				}
				
				session.setAttribute("user", user);
				return "employeeMain.do";
			} else {
				CustomerBean user = customerDAO.read(form.getUserName());
				
				if (user == null) {
					errors.add("User Name not found");
					return "login.jsp";
				}

				if (!user.getPassword().equals(form.getPassword())) {
					errors.add("Incorrect password");
					return "login.jsp";
				}
				
				session.setAttribute("user", user);
				return "viewAccount.do";
			}
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "login.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "login.jsp";
        }
	}
}
