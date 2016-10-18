package edu.cmu.cs.webapp.task7.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import model.PhotoDAO;



import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.task7.databean.CustomerBean;
import edu.cmu.cs.webapp.task7.databean.EmployeeBean;
import edu.cmu.cs.webapp.task7.databean.FundBean;
import edu.cmu.cs.webapp.task7.databean.TransactionBean;
import edu.cmu.cs.webapp.task7.databean.PositionBean;
import edu.cmu.cs.webapp.task7.model.*;
import edu.cmu.cs.webapp.task7.formbean.*;

public class BuyFundAction extends Action {
	private FormBeanFactory<BuyForm> formBeanFactory = FormBeanFactory
			.getInstance(BuyForm.class);

	private FundDAO fundDAO;
	private CustomerDAO customerDAO;
	private PositionDAO posDAO;
	private TransactionDAO transactionDAO;

	public BuyFundAction(Model model) {
		fundDAO = model.getFundDAO();
		customerDAO = model.getCustomerDAO();
		posDAO = model.getPositionDAO();
		transactionDAO = model.getTransactionDAO();
	}

	public String getName() {
		return "buyFund.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up the errors list
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			if (request.getSession().getAttribute("user") == null || request.getSession().getAttribute("user") instanceof EmployeeBean) {
				errors.add("Please log in as a customer");
				return "login.jsp";
			}

			// Set up user list for nav barS
			request.setAttribute("customerList", customerDAO.match());

			CustomerBean user = (CustomerBean) request.getSession(false).getAttribute("user");

			FundBean[] fundList = fundDAO.match();
			request.setAttribute("fundList", fundList);
			
			user = customerDAO.read(user.getUserName());
			double availableBalance = transactionDAO.getValidBalance(user.getUserName(), user.getCash() / 100.0);
			DecimalFormat df2 = new DecimalFormat("#,##0.00");
			String availableBalanceString = "$" + df2.format(availableBalance);
			
			request.setAttribute("balance", availableBalanceString);

			BuyForm form = formBeanFactory.create(request);

			if (!form.isPresent()) {
				return "buyFund.jsp";
			}
			errors.addAll(form.getValidationErrors());
			request.setAttribute("errors", errors);

			if (errors.size() > 0)
				return "buyFund.jsp";

			String fundName = form.getFundName();
			
			double amount = Double.parseDouble(form.getAmount());
			
			FundBean[] fb = fundDAO.match(MatchArg.equalsIgnoreCase("name", fundName));
			if (fb == null || fb.length == 0) {
				errors.add("Fund name does not exist");
				return "buyFund.jsp";
			}
			
			user = customerDAO.read(user.getUserName());
			if (! transactionDAO.buyFund(user.getUserName(), user.getCash(), amount, fb[0].getFundId()) ){
				errors.add("You do not have enough cash available.");
				return "buyFund.jsp";	
			}

			user = customerDAO.read(user.getUserName());
			availableBalance = transactionDAO.getValidBalance(user.getUserName(), user.getCash() / 100.0);
			availableBalanceString = "$" + df2.format(availableBalance);
			
			request.setAttribute("balance", availableBalanceString);

			request.getSession().setAttribute("msg", "$"+form.getAmount()+ " in funds purchased successfully.");
			
			if (errors.size() > 0) 
				return "buyFund.jsp";	
			
			return "success.do";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		}
	}

}
