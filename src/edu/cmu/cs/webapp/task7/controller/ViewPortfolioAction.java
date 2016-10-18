package edu.cmu.cs.webapp.task7.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;

import edu.cmu.cs.webapp.task7.databean.*;
import edu.cmu.cs.webapp.task7.model.*;
import edu.cmu.cs.webapp.task7.formbean.*;

public class ViewPortfolioAction extends Action {
	private FundPriceHistoryDAO historyDAO;
	private PositionDAO positionDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private CustomerDAO customerDAO;

	public ViewPortfolioAction(Model model) {
		// TODO Auto-generated constructor stub
		historyDAO = model.getFundPriceHistoryDAO();
		positionDAO = model.getPositionDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "viewAccount.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
			if (session.getAttribute("user") != null && session.getAttribute("user") instanceof CustomerBean) {

				CustomerBean customer = (CustomerBean) request	.getSession(false).getAttribute("user");
				
				customer = customerDAO.read(customer.getUserName());

				DecimalFormat df2 = new DecimalFormat("#,##0.00");
				DecimalFormat df3 = new DecimalFormat("#,##0.000");
				String cash = df2.format(customer.getCash() / 100.0);
				String balance = df2.format(transactionDAO.getValidBalance(customer.getUserName(), customer.getCash() / 100.0));

				request.setAttribute("cash", cash);
				request.setAttribute("balance", balance);
				
				PositionBean[] positionList = positionDAO.match(MatchArg.equals("userName",customer.getUserName()));

				FundDisplay[] fundList = null;
				if (positionList != null && positionList.length > 0) {
					
					fundList = new FundDisplay[positionList.length];

					for (int i = 0; i < positionList.length; i++) {
						fundList[i] = new FundDisplay();
						
						FundBean fb = fundDAO.read( positionList[i].getFundId() );
						double price = historyDAO.getLatestPrice(fb.getFundId()) / 100.0;
						double amount = positionList[i].getShares() / 1000.0 * price;
						
						fundList[i].setFundName(fb.getName());
						fundList[i].setTicker(fb.getSymbol());
						fundList[i].setShares(df3.format(positionList[i].getShares() / 1000.0));
						fundList[i].setTotal(df2.format(amount));
					}
				}

				request.setAttribute("fundList", fundList);

				request.setAttribute("lastTradingDay",
						transactionDAO.getLastDate(customer) == null ? "N/A" : transactionDAO.getLastDate(customer));
				
				request.setAttribute("customer", customer);

				return "viewAccount.jsp";	

			} else {
				// logout and re-login
				if (session.getAttribute("user") != null)
					session.removeAttribute("user");

				return "login.do";
			}

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "viewAccount.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "viewAccount.jsp";
		}

	}
}
