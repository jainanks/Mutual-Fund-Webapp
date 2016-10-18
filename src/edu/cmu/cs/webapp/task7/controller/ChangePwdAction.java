
package edu.cmu.cs.webapp.task7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.task7.databean.CustomerBean;
import edu.cmu.cs.webapp.task7.databean.EmployeeBean;
import edu.cmu.cs.webapp.task7.formbean.ChangePwdForm;
import edu.cmu.cs.webapp.task7.model.CustomerDAO;
import edu.cmu.cs.webapp.task7.model.EmployeeDAO;
import edu.cmu.cs.webapp.task7.model.Model;


/*
 * Logs out by setting the "user" session attribute to null.
 * (Actions don't be much simpler than this.)
 */
public class ChangePwdAction extends Action {
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ChangePwdForm.class);
	private CustomerDAO customerDAO;
	private EmployeeDAO employeeDAO;

	public ChangePwdAction(Model model) {
		customerDAO = model.getCustomerDAO();
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return "changePwd.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {

			if (request.getSession().getAttribute("user") instanceof CustomerBean) {
				CustomerBean user = (CustomerBean) request.getSession().getAttribute("user");
				
				ChangePwdForm form = formBeanFactory.create(request);
				request.setAttribute("form", form);

				if (!form.isPresent()) {
					return "customerPSW.jsp";
				}

				errors.addAll(form.getValidationErrors());
				if (errors.size() > 0) {
					return "customerPSW.jsp";
				}
				
				if (!form.getCurrPwd().equals(customerDAO.read(user.getUserName()).getPassword())) {
					errors.add("Original password is incorrect");
				}
				if (errors.size() > 0) {
					return "customerPSW.jsp";
				}

				user.setPassword(form.getNewPwd());
				customerDAO.update(user);
				
				request.setAttribute("msg", "Password was changed successfully.");

				return "confirmationPage.jsp";
				
			} else if (request.getSession().getAttribute("user") instanceof EmployeeBean) {
				EmployeeBean user = (EmployeeBean) request.getSession().getAttribute("user");
				
				ChangePwdForm form = formBeanFactory.create(request);
				request.setAttribute("form", form);

				if (!form.isPresent()) {
					return "employeePSW.jsp";
				}

				errors.addAll(form.getValidationErrors());
				
				if (errors.size() > 0) {
					return "employeePSW.jsp";
				}
				
				if (!form.getCurrPwd().equals(employeeDAO.read(user.getUserName()).getPassword())) {
					errors.add("Incorrect original password");
				}
				
				if (errors.size() > 0) {
					return "employeePSW.jsp";
				}

				user.setPassword(form.getNewPwd());
				employeeDAO.update(user);
				
				request.getSession().setAttribute("msg", "Password was changed successfully.");

				if (errors.size() > 0) 
					return "employeePSW.jsp";	
				
				return "success.do";
			}
			else {
				if (request.getSession().getAttribute("user") != null)
					request.getSession().removeAttribute("user");

				return "login.do";
			}

        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }

	}
}
