package edu.cmu.cs.webapp.task7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.task7.databean.EmployeeBean;
import edu.cmu.cs.webapp.task7.formbean.CreateEmployeeForm;
import edu.cmu.cs.webapp.task7.model.EmployeeDAO;
import edu.cmu.cs.webapp.task7.model.Model;

public class CreateEmployeeAction extends Action {
	private FormBeanFactory<CreateEmployeeForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateEmployeeForm.class);

	private EmployeeDAO employeeDAO;

	public CreateEmployeeAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return "createEmployee.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// If user is already logged in, redirect to todolist.do
			if (session.getAttribute("user") != null && session.getAttribute("user") instanceof EmployeeBean) {
				CreateEmployeeForm form = formBeanFactory.create(request);
				request.setAttribute("form", form);

				// If no params were passed, return with no errors so that the
				// form
				// will be presented (we assume for the first time).
				if (!form.isPresent()) {
					return "createEmployee.jsp";
				}

				// Any validation errors?
				errors.addAll(form.getValidationErrors());
				if (errors.size() != 0) {
					return "createEmployee.jsp";
				}

				if (employeeDAO.read(form.getUserName()) != null) {
					errors.add("This employee username already exists.");
					return "createEmployee.jsp";
				}

				EmployeeBean newUser = new EmployeeBean();
				newUser.setUserName(form.getUserName());
				newUser.setLastName(form.getLastName());
				newUser.setFirstName(form.getFirstName());
				newUser.setPassword(form.getPassword());

				employeeDAO.create(newUser);
				
				request.getSession().setAttribute("msg", "Employee account " +form.getUserName()+ " was created successfully.");
				request.removeAttribute("form");
				
				if (errors.size() > 0) 
					return "createEmployee.jsp";	
				
				return "success.do";
			} else {
				
				// logout and re-login
				if (session.getAttribute("user") != null)
					session.removeAttribute("user");

				return "login.do";
			}
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "createEmployee.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "createEmployee.jsp";
		}

	}
}
