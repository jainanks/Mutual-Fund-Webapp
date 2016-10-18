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
import edu.cmu.cs.webapp.task7.formbean.CreateCustomerForm;
import edu.cmu.cs.webapp.task7.model.CustomerDAO;
import edu.cmu.cs.webapp.task7.model.Model;

public class CreateCustomerAction extends Action {
	private FormBeanFactory<CreateCustomerForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateCustomerForm.class);

	private CustomerDAO customerDAO;

	public CreateCustomerAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "createCustomer.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// If user is already logged in, redirect to todolist.do
			if (session.getAttribute("user") != null && session.getAttribute("user") instanceof EmployeeBean) {
				CreateCustomerForm form = formBeanFactory.create(request);
				request.setAttribute("form", form);

				// If no params were passed, return with no errors so that the
				// form
				// will be presented (we assume for the first time).
				if (!form.isPresent()) {
					return "createCustomer.jsp";
				}

				// Any validation errors?
				errors.addAll(form.getValidationErrors());
				if (errors.size() != 0) {
					return "createCustomer.jsp";
				}

				if (customerDAO.read(form.getUserName()) != null) {
					errors.add("This username already exists");
					return "createCustomer.jsp";
				}

				CustomerBean newUser = new CustomerBean();
				newUser.setUserName(form.getUserName());
				newUser.setLastName(form.getLastName());
				newUser.setFirstName(form.getFirstName());
				newUser.setPassword(form.getPassword());
				newUser.setAddress1(form.getAddress1());
				newUser.setAddress2(form.getAddress2());
				newUser.setCity(form.getCity());
				newUser.setState(form.getState());
				newUser.setZipcode(form.getZipcode());
				newUser.setCash(0);

				customerDAO.create(newUser);
				
				request.removeAttribute("form");;
				request.getSession().setAttribute("msg", "Customer account created successfully");

				if (errors.size() > 0) 
					return "createCustomer.jsp";	
				
				return "success.do";
			} else {
				
				// logout and re-login
				if (session.getAttribute("user") != null)
					session.removeAttribute("user");

				return "login.do";
			}
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "createCustomer.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "createCustomer.jsp";
		}

	}
}
