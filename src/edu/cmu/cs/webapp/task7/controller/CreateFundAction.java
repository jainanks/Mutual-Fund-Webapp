
package edu.cmu.cs.webapp.task7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.task7.databean.EmployeeBean;
import edu.cmu.cs.webapp.task7.databean.FundBean;
import edu.cmu.cs.webapp.task7.formbean.CreateFundForm;
import edu.cmu.cs.webapp.task7.model.FundDAO;
import edu.cmu.cs.webapp.task7.model.Model;

public class CreateFundAction extends Action {
	private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateFundForm.class);

	private FundDAO fundDAO;

	public CreateFundAction(Model model) {
		fundDAO = model.getFundDAO();
	}

	public String getName() {
		return "createFund.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// If user is already logged in, redirect to todolist.do
			if (session.getAttribute("user") != null && session.getAttribute("user") instanceof EmployeeBean) {
				CreateFundForm form = formBeanFactory.create(request);
				request.setAttribute("form", form);

				// If no params were passed, return with no errors so that the
				// form
				// will be presented (we assume for the first time).
				if (!form.isPresent()) {
					return "createFund.jsp";
				}

				// Any validation errors?
				errors.addAll(form.getValidationErrors());
				if (errors.size() != 0) {
					return "createFund.jsp";
				}
				try{
				Transaction.begin();
				
				FundBean[] funds1 = fundDAO.match(MatchArg.equals("name",
						form.getFundName()));
				FundBean[] funds2 = fundDAO.match(MatchArg.equals("symbol",
						form.getTicker()));
		        
		        if(funds1.length!=0){
		        	errors.add("Fund Name: <font color=\"black\">"+"'"+form.getFundName()+"'"+"</font> already exists");
					return "createFund.jsp";
		        }
		        
		        if(funds2.length!=0){
		        	errors.add("Ticker: <font color=\"black\">"+"'"+form.getTicker()+"'"+"</font> already exists");
					return "createFund.jsp";
		        }

				FundBean newFund = new FundBean();
				newFund.setName(form.getFundName());
				newFund.setSymbol(form.getTicker());
				
				fundDAO.createAutoIncrement(newFund);
				
				request.getSession().setAttribute("msg", "Fund <font color=\"black\">" +form.getTicker()+ "</font> has been created.");
				Transaction.commit();
				} finally {
					if (Transaction.isActive()) Transaction.rollback();
				}
				if (errors.size() > 0) 
					return "createFund.jsp";	
				
				return "success.do";
			} else {
				
				// logout and re-login
				if (session.getAttribute("user") != null)
					session.removeAttribute("user");
				return "login.do";
			}
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "createFund.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "createFund.jsp";
		}

	}
}
