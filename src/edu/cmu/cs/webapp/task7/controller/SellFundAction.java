package edu.cmu.cs.webapp.task7.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;



import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.task7.databean.CustomerBean;
import edu.cmu.cs.webapp.task7.databean.FundBean;
import edu.cmu.cs.webapp.task7.databean.FundDisplay;
import edu.cmu.cs.webapp.task7.databean.PositionBean;
import edu.cmu.cs.webapp.task7.model.*;
import edu.cmu.cs.webapp.task7.formbean.*;

public class SellFundAction  extends Action {
	private FormBeanFactory<SellFundForm> formBeanFactory = FormBeanFactory.getInstance(SellFundForm.class);

	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private TransactionDAO transactionDAO;
	
	public SellFundAction(Model model) {
		fundDAO = model.getFundDAO();
    	positionDAO=model.getPositionDAO();
    	transactionDAO=model.getTransactionDAO();
	}

	public String getName() { return "sellFund.do"; }

    public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			SellFundForm form = formBeanFactory.create(request);
			if (session.getAttribute("user") != null	&& session.getAttribute("user") instanceof CustomerBean) {
				
				//CHECK FOR THE NUMBER OF SHARES
				CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("user");

				DecimalFormat df3 = new DecimalFormat("#,##0.000");

				ArrayList<FundDisplay> fundList = null;
				PositionBean[] positionList = positionDAO.match(MatchArg.equals("userName",customer.getUserName()));
				
				if (positionList != null && positionList.length > 0) {
					fundList = new ArrayList<FundDisplay>();
					
					for (int i = 0; i < positionList.length; i++) {
						FundDisplay fd = new FundDisplay();
						
						FundBean fund = fundDAO.read(positionList[i].getFundId());
						
						fd.setFundId(positionList[i].getFundId());
						fd.setFundName(fund.getName());
						fd.setTicker(fund.getSymbol());
						double validShare = transactionDAO.getValidShares(customer.getUserName() , positionList[i].getShares() / 1000.0, fund.getFundId());
						fd.setShares(df3.format(validShare));
						
						if (validShare >= 0.001) {
							fundList.add(fd);
						}
					}
				}
				
				request.setAttribute("fundList", fundList);
				
				if (!form.isPresent()) {
					return "sellFund.jsp";
				}
				
				errors.addAll(form.getValidationErrors());
			      
		        if (errors.size() > 0) return "sellFund.jsp";
	        
		        Double sharesToSell = Double.parseDouble(form.getShares());

		       if (! transactionDAO.sellShares(customer.getUserName(), positionDAO.read(customer.getUserName(), Integer.parseInt(form.getFundId())).getShares() / 1000.0, Integer.parseInt(form.getFundId()), sharesToSell) ){
		    	   errors.add("The shares to sell exceed the shares held");
		    	   return "sellFund.jsp";	
		       }
		        
		        request.removeAttribute("form");
		        request.getSession().setAttribute("msg", form.getShares()+" shares sold successfully.");		        
//		        positionList = positionDAO.match(MatchArg.equals("userName",customer.getUserName()));
//
//				if (positionList != null && positionList.length > 0) {
//					fundList = new ArrayList<FundDisplay>();
//					
//					for (int i = 0; i < positionList.length; i++) {
//						FundDisplay fd = new FundDisplay();
//						
//						FundBean fund = fundDAO.read(positionList[i].getFundId());
//						
//						fd.setFundId(positionList[i].getFundId());
//						fd.setFundName(fund.getName());
//						fd.setTicker(fund.getSymbol());
//						double validShare = transactionDAO.getValidShares(customer.getUserName() , positionList[i].getShares() / 1000.0, fund.getFundId());
//						fd.setShares(df3.format(validShare));
//						
//						if (validShare >= 0.001) {
//							fundList.add(fd);
//						}
//					}
//				}
//				
//				request.setAttribute("fundList", fundList);

//				if (errors.size() > 0) 
//					return "sellFund.jsp";	
				
				return "success.do";
			} else {
				// logout and re-login
				if (session.getAttribute("user") != null)	session.removeAttribute("user");

				return "login.do";
			}

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "sellFund.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "sellFund.jsp";
		}
    }
    
    
}
