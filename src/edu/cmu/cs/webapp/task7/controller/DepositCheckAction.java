package edu.cmu.cs.webapp.task7.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.task7.databean.CustomerBean;
import edu.cmu.cs.webapp.task7.databean.EmployeeBean;
import edu.cmu.cs.webapp.task7.databean.TransactionBean;
import edu.cmu.cs.webapp.task7.formbean.DepositCheckForm;
import edu.cmu.cs.webapp.task7.model.CustomerDAO;
import edu.cmu.cs.webapp.task7.model.Model;
import edu.cmu.cs.webapp.task7.model.TransactionDAO;

public class DepositCheckAction extends Action {
	private FormBeanFactory<DepositCheckForm> formBeanFactory = FormBeanFactory
			.getInstance(DepositCheckForm.class);

	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;

	public DepositCheckAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
	}

	public String getName() {
		return "depositCheck.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// If user is already logged in, redirect to todolist.do
			if (session.getAttribute("user") != null && session.getAttribute("user") instanceof EmployeeBean) {
				DepositCheckForm form = formBeanFactory.create(request);
				request.setAttribute("form", form);
				
				// read all customers into list
				request.setAttribute("customerList", customerDAO.match());
				
				// If no params were passed, return with no errors so that the
				// form
				// will be presented (we assume for the first time).
				if (!form.isPresent()) {
					return "depositCheck.jsp";
				}

				// Any validation errors?
				errors.addAll(form.getValidationErrors());
				if (errors.size() != 0) {
					return "depositCheck.jsp";
				}

				if (customerDAO.read(form.getUserName()) == null) {
					errors.add("Customer does not exist");
					return "depositCheck.jsp";
				}

				TransactionBean tb = new TransactionBean();
				tb.setUserName(form.getUserName());
				//tb.setFundId(0);
				tb.setExecuteDate(null);
				//tb.setShares(0);
				tb.setTransactionType(TransactionBean.DPT_CHECK);
				tb.setAmount((long) (Double.parseDouble(form.getAmount()) * 100));

				transactionDAO.createAutoIncrement(tb);
				
				NumberFormat formatter = new DecimalFormat("#,##0.00");
				request.getSession().setAttribute("msg", "A deposit of $"+ formatter.format(Double.parseDouble(form.getAmount()))+ " was made into <font color=\"black\">" +form.getUserName()+ "'s</font> account.");
				request.removeAttribute("form");

				if (errors.size() > 0) 
					return "depositeCheck.jsp";	
				
				return "success.do";
			} else {
				// logout and re-login
				if (session.getAttribute("user") != null)
					session.removeAttribute("user");

				return "login.do";
			}
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "depositCheck.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "depositCheck.jsp";
		}

	}
}
