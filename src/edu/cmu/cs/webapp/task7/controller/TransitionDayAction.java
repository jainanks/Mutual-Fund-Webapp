package edu.cmu.cs.webapp.task7.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import edu.cmu.cs.webapp.task7.databean.CustomerBean;
import edu.cmu.cs.webapp.task7.databean.EmployeeBean;
import edu.cmu.cs.webapp.task7.databean.FundBean;
import edu.cmu.cs.webapp.task7.databean.FundPriceHistoryBean;
import edu.cmu.cs.webapp.task7.databean.PositionBean;
import edu.cmu.cs.webapp.task7.databean.TransactionBean;
import edu.cmu.cs.webapp.task7.formbean.TransitionDayForm;
import edu.cmu.cs.webapp.task7.model.CustomerDAO;
import edu.cmu.cs.webapp.task7.model.FundDAO;
import edu.cmu.cs.webapp.task7.model.FundPriceHistoryDAO;
import edu.cmu.cs.webapp.task7.model.Model;
import edu.cmu.cs.webapp.task7.model.PositionDAO;
import edu.cmu.cs.webapp.task7.model.TransactionDAO;

public class TransitionDayAction extends Action {

	private 	CustomerDAO 		customerDAO;
	private 	FundDAO				fundDAO;
	private	TransactionDAO	transactionDAO;
	private 	FundPriceHistoryDAO	fundPriceHistoryDAO;
	private 	PositionDAO			positionDAO;

	public TransitionDayAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
		positionDAO = model.getPositionDAO();
	}

	public String getName() {
		return "transitionDay.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// If user is already logged in, redirect to todolist.do
			if (session.getAttribute("user") != null && session.getAttribute("user") instanceof EmployeeBean) {
				TransitionDayForm form = new TransitionDayForm(request);
				request.setAttribute("form", form);
				
				// read all customers into list
				FundBean[] fundList =  fundDAO.match();
				request.setAttribute("fundList", fundList);
				
				NumberFormat formatter = new DecimalFormat("#0.00");     		
				HashMap<Integer, String> price_map = new HashMap<Integer, String>();
				String lastTradingDay = fundPriceHistoryDAO.getLatestTradingDayDateString ();
				for (FundBean fb : fundList) {
					if (lastTradingDay == null) {
						price_map.put(fb.getFundId(),"N/A");
						continue;
					}
					FundPriceHistoryBean tmp = fundPriceHistoryDAO.read(fb.getFundId() ,lastTradingDay);
					if (tmp == null) {
						price_map.put(fb.getFundId(),"N/A");
					} else {
						price_map.put(fb.getFundId(),  formatter.format(tmp.getPrice() / 100.0 ));
					}
				}
				request.setAttribute("price_map", price_map);
				
				// check date
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	        	SimpleDateFormat inputDate = new  SimpleDateFormat("yyyy-MM-dd");
	        	dateFormat.setLenient(false);
	        	inputDate.setLenient(false);
	        	
	        	Date lastFundDay = fundPriceHistoryDAO.getLatestTradingDayDate();
	        	Date lastTranDay = transactionDAO.getLatestDate();
	        	Date lastDay = null;
	        	if (lastFundDay != null && lastTranDay != null) {
	        		lastDay = lastFundDay.compareTo(lastTranDay) <= 0 ? lastTranDay : lastFundDay;
	        	} else {
	        		lastDay = lastFundDay == null ? lastTranDay : lastFundDay;
	        	}
	        	request.setAttribute("lastDay", lastDay == null ? "" : inputDate.format(lastDay));
				
				// If no params were passed, return with no errors so that the
				// form will be presented (we assume for the first time).
				if (!form.isPresent()) {
					return "transitionDay.jsp";
				}
				
				// Any validation errors?
				HashMap<String, String> map = new HashMap<String, String>();
				for (FundBean fb : fundList) {
					map.put("fund_" + fb.getFundId(), request.getParameter("fund_" + fb.getFundId()));
				}
				errors.addAll(form.getValidationErrors(map));

				if (errors.size() != 0) {
					return "transitionDay.jsp";
				}
				
				Date date = inputDate.parse(form.getDate());
	        	
	        	if (lastDay != null && date.compareTo(lastDay) <= 0) {
	        		errors.add("The input date has to be greater than the last trading day");
	        	}
	        		        	
				if (errors.size() != 0) {
					return "transitionDay.jsp";
				}
				
				String today = dateFormat.format(date);
				
				try {
					Transaction.begin();
					
					// update prices
					for (FundBean fb : fundList) {			
						FundPriceHistoryBean fphb = new FundPriceHistoryBean();
						fphb.setFundId(fb.getFundId());
						fphb.setPriceDate(today);
						fphb.setPrice( (long)(Double.parseDouble(request.getParameter("fund_" + fb.getFundId())) * 100) );
						fundPriceHistoryDAO.create(fphb);
					}
	
					
					// process pending transactions
					for (TransactionBean tb : transactionDAO.match(MatchArg.equals("executeDate", null))){
						CustomerBean cb = customerDAO.read(tb.getUserName());
						
						switch(tb.getTransactionType()) {
							case TransactionBean.SELL_FUND:
								if (positionDAO.read(tb.getUserName(), tb.getFundId()) != null) {
									PositionBean pb = positionDAO.read(tb.getUserName(), tb.getFundId());

									if (pb.getShares() - tb.getShares()== 0) {
										positionDAO.delete(tb.getUserName(), tb.getFundId());
									} else {
										pb.setShares(pb.getShares() - tb.getShares());
										positionDAO.update(pb);
									}
									
									double price = fundPriceHistoryDAO.read(tb.getFundId() ,  today).getPrice() / 100.0;
									long amount = (long) (price * tb.getShares() / 1000 * 100);
									cb.setCash(cb.getCash() +  amount);
									customerDAO.update(cb);
									tb.setAmount(amount);
								}
								break;
							case TransactionBean.BUY_FUND:						
								long shares = 0;
								double price = fundPriceHistoryDAO.read(tb.getFundId(), today).getPrice()  / 100.0 ;
								if (positionDAO.read(tb.getUserName() , tb.getFundId()) == null) {
									double amount = tb.getAmount() / 100.00;
									shares = (long) (amount / price * 1000);
									
									PositionBean pb = new PositionBean();
									pb.setUserName(tb.getUserName());
									pb.setFundId(tb.getFundId());
									pb.setShares(shares);
									positionDAO.create(pb);
									
								} else {
									double amount = tb.getAmount() / 100.00;
									
									shares = (long) (amount / price * 1000);
									
									PositionBean pb = positionDAO.read(tb.getUserName(),  tb.getFundId());
									pb.setShares(shares + pb.getShares());
									positionDAO.update(pb);
								}
							
								tb.setAmount((long)(shares / 1000.0 * price * 100));
								tb.setShares(shares);
										
								cb.setCash(cb.getCash() -  tb.getAmount());
								customerDAO.update(cb);
								break;
							case TransactionBean.REQ_CHECK:
								cb.setCash(cb.getCash() -  tb.getAmount());
								customerDAO.update(cb);
								break;
							case TransactionBean.DPT_CHECK:
								cb.setCash(cb.getCash() +  tb.getAmount());
								customerDAO.update(cb);
								break;	
							default:
								break;
						}
						
						tb.setExecuteDate(today);
						transactionDAO.update(tb);
					}
					
					Transaction.commit();
				} finally {
					if (Transaction.isActive()) {
						Transaction.rollback();
					}
				}
				
				
//				price_map = new HashMap<Integer, String>();
//				lastTradingDay = fundPriceHistoryDAO.getLatestTradingDayDateString ();
//				for (FundBean fb : fundList) {
//					if (lastTradingDay == null) {
//						price_map.put(fb.getFundId(),"N/A");
//						continue;
//					}
//					FundPriceHistoryBean tmp = fundPriceHistoryDAO.read(fb.getFundId() ,lastTradingDay);
//					if (tmp == null) {
//						price_map.put(fb.getFundId(),"N/A");
//					} else {
//						price_map.put(fb.getFundId(),  formatter.format(tmp.getPrice() / 100.0 ));
//					}
//				}
//				request.setAttribute("price_map", price_map);
//				
//				lastFundDay = fundPriceHistoryDAO.getLatestTradingDayDate();
//	        	lastTranDay = transactionDAO.getLatestDate();
//	        	lastDay = null;
//	        	if (lastFundDay != null && lastTranDay != null) {
//	        		lastDay = lastFundDay.compareTo(lastTranDay) <= 0 ? lastTranDay : lastFundDay;
//	        	} else {
//	        		lastDay = lastFundDay == null ? lastTranDay : lastFundDay;
//	        	}
//				
//	        	request.setAttribute("lastDay", lastDay == null ? "" : inputDate.format(lastDay));
	   

				if (errors.size() > 0) 
					return "transitionDay.jsp";	
				
				request.getSession().setAttribute("msg", "Transition day for "+ form.getDate() + " was set.");
				
				return "success.do";
			} else {
				// logout and re-login
				if (session.getAttribute("user") != null)
					session.removeAttribute("user");

				return "login.do";
			}
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "transitionDay.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "transitionDay.jsp";
		}

	}
}