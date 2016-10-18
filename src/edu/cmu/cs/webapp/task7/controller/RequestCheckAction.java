package edu.cmu.cs.webapp.task7.controller;

import java.util.ArrayList;
import java.util.List;
import java.text.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.task7.databean.CustomerBean;
import edu.cmu.cs.webapp.task7.formbean.RequestCheckForm;
import edu.cmu.cs.webapp.task7.model.CustomerDAO;
import edu.cmu.cs.webapp.task7.model.Model;
import edu.cmu.cs.webapp.task7.model.TransactionDAO;

public class RequestCheckAction extends Action {
	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory
			.getInstance(RequestCheckForm.class);

	private TransactionDAO transactionDAO;
	private CustomerDAO customerDAO;

	public RequestCheckAction(Model model) {
		transactionDAO = model.getTransactionDAO();
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "requestCheck.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// If user is already logged in, redirect to todolist.do
			if (session.getAttribute("user") != null && session.getAttribute("user") instanceof CustomerBean) {
				RequestCheckForm form = formBeanFactory.create(request);
				request.setAttribute("form", form);
				
				CustomerBean customer = (CustomerBean) session.getAttribute("user");
				customer = customerDAO.read(customer.getUserName());
				double availableBalance = transactionDAO.getValidBalance(customer.getUserName(), customer.getCash() / 100.0 );
				
				NumberFormat formatter = new DecimalFormat("#,##0.00"); 
				
				String balance = formatter.format(availableBalance);
				session.setAttribute("balance", balance);
					
				// If no params were passed, return with no errors so that the
				// form will be presented (we assume for the first time).
				if (!form.isPresent()) {
					return "requestCheck.jsp";
				}

				// Any validation errors?
				errors.addAll(form.getValidationErrors());
				
				if (errors.size() != 0) {
					return "requestCheck.jsp";
				}
				
				customer = customerDAO.read(customer.getUserName());
				if (!transactionDAO.requestCheck(customer.getUserName(), customer.getCash() / 100.0, Double.parseDouble(form.getAmount()))) {
					errors.add("Amount requested is higher than cash available");
					return "requestCheck.jsp";
				} 
				
				request.getSession().setAttribute("msg", "A check in the amount of $"+ formatter.format(Double.parseDouble(form.getAmount()))+ " has been requested.");

//				availableBalance = transactionDAO.getValidBalance(customer.getUserName(), customer.getCash() / 100.0 );
//				balance = formatter.format(availableBalance);
//				session.setAttribute("balance", balance);
//				
//				if (errors.size() > 0) 
//					return "requestCheck.jsp";	
				
				return "success.do";
			} else {
				// logout and re-login
				if (session.getAttribute("user") != null)
					session.removeAttribute("user");

				return "login.do";
			}
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "requestCheck.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "requestCheck.jsp";
		}
	}
}