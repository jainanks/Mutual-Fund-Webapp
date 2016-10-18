package edu.cmu.cs.webapp.task7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.task7.databean.CustomerBean;
import edu.cmu.cs.webapp.task7.databean.EmployeeBean;
import edu.cmu.cs.webapp.task7.formbean.ResetPwdForm;
import edu.cmu.cs.webapp.task7.model.CustomerDAO;
import edu.cmu.cs.webapp.task7.model.Model;


/*
 * Logs out by setting the "user" session attribute to null.
 * (Actions don't be much simpler than this.)
 */
public class ResetPwdAction extends Action {
	private FormBeanFactory<ResetPwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ResetPwdForm.class);
	private CustomerDAO customerDAO;
	public ResetPwdAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "reset.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {

			  if (request.getSession().getAttribute("user") instanceof EmployeeBean) {
				
				ResetPwdForm form = formBeanFactory.create(request);
				request.setAttribute("form", form);
								
				request.setAttribute("customer", request.getParameter("customer"));

				if (!form.isActionPresent()) {
					return "resetPwd.jsp";
				}
				
				errors.addAll(form.getValidationErrors());
				
				if (errors.size() > 0) {
					return "resetPwd.jsp";
				}
				
				CustomerBean user = customerDAO.read(request.getParameter("customer"));
				
				user.setPassword(form.getNewPwd());
				customerDAO.update(user);
				request.getSession().setAttribute("msg", "Password is reset successfully!");

				if (errors.size() > 0) 
					return "resetPwd.jsp";	
				
				return "success.do";
			}
			else {
				if (request.getSession().getAttribute("user") != null)
					request.getSession().removeAttribute("user");

				return "login.do";
			}

        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "resetPwd.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "resetPwd.jsp";
        }

	}
}

