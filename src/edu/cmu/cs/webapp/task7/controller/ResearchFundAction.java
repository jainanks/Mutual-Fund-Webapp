package edu.cmu.cs.webapp.task7.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.task7.databean.*;
import edu.cmu.cs.webapp.task7.formbean.*;
import edu.cmu.cs.webapp.task7.model.*;


public class ResearchFundAction extends Action {
	//private static Logger logger = Logger.getLogger(ResearchFundAction.class);
	private FormBeanFactory<ResearchFundForm> formBean = FormBeanFactory
			.getInstance(ResearchFundForm.class);
	
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	
	public ResearchFundAction(Model model) {
		this.fundDAO = model.getFundDAO();
		this.fundPriceHistoryDAO =model.getFundPriceHistoryDAO();

	}
	
	@Override
	public String getName() {
		return "researchFund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		 
		
		try {
			if (request.getSession().getAttribute("user") == null || 
					request.getSession().getAttribute("user") instanceof EmployeeBean) {
				errors.add("Please log in as a customer");
				return "login.jsp";
			}
			
			ResearchFundForm form = formBean.create(request);
			
			FundBean[] funds = fundDAO.match();
			
			request.setAttribute("funds", funds);
			
			if (!form.isPresent()) {
				return "researchFunds.jsp";
			}
			
			request.setAttribute("form", form);
			request.setAttribute("description", "");
			
			errors.addAll(form.getValidationErrors());
			
			if (errors.size() != 0) {
				return "researchFunds.jsp";
			}
			
			String fund = form.getFund1();
			
			
			FundBean[] tmp_fb = fundDAO.match(MatchArg.equalsIgnoreCase("name", fund));
			if (tmp_fb == null || tmp_fb.length == 0) {
				errors.add("Fund name does not exist");
				return "researchFunds.jsp";
			}
			
			int fndId = tmp_fb[0].getFundId();
			
			
			if (form.getAction()!=null &&form.getAction().equals("Fund History")) {
				
				
				List<Map<String,String>> fundPriceHistory = getFundPriceHistory(fndId); 
				if (fundPriceHistory.isEmpty()) 
					errors.add("There is no history for " + fundDAO.read(fndId).getName() + " fund");
				request.setAttribute("fundPriceHistory", fundPriceHistory);
				request.setAttribute("ticker", fundDAO.read(fndId).getSymbol());
				request.setAttribute("fundTitle", fundDAO.read(fndId).getName());
				request.setAttribute("chartData", chartData(fndId));
			}
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "researchFunds.jsp";
		} /*catch (RollbackException e) {
			errors.add("Error in database. Please try again later.");
			return "researchFunds.jsp";
		}*/catch (Exception e) {
			errors.add("");
			//logger.error(e.getMessage());
			return "researchFunds.jsp";
		}
		
			return "researchFunds.jsp";	
		
	}

	
	
	private List<Map<String, String>> getFundPriceHistory(
			int fundId) throws RollbackException {
		List<Map<String,String>> fundPriceHistory = new ArrayList<Map<String,String>>();
		FundPriceHistoryBean[] fundPriceHistoryBean;
		
			fundPriceHistoryBean = fundPriceHistoryDAO. match(MatchArg.equals("fundId", fundId));
		if(fundPriceHistoryBean != null){
			for(FundPriceHistoryBean hBean: fundPriceHistoryBean){
				
				String id=Integer.toString(hBean.getFundId());
				Map<String,String> tmp = new HashMap<String,String>();
				tmp.put("fundId",id);
				NumberFormat formatter = new DecimalFormat("#,##0.00");
				String newPrice=formatter.format(hBean.getPrice()/100);
				tmp.put("price",newPrice);
				tmp.put("date",hBean.getPriceDate());
				tmp.put("fundName", fundDAO.read(hBean.getFundId()).getName());
				
				fundPriceHistory.add(tmp);
			}
			return fundPriceHistory;
		}
		else{
			return fundPriceHistory;
		}

	}
	
	private String chartData(
			int fundId) throws RollbackException {
		FundPriceHistoryBean[] fundPriceHistoryBean = fundPriceHistoryDAO. match(MatchArg.equals("fundId",fundId));
		StringBuilder data = new StringBuilder();
		if(fundPriceHistoryBean != null){
			for(FundPriceHistoryBean hBean: fundPriceHistoryBean){
				data.append(hBean.getPriceDate());
				data.append(",");
				data.append(((double)hBean.getPrice())/100);
				data.append(",");
			}
		}
			
		/*System.out.println(data.toString());*/
		return data.toString();

	}
}
