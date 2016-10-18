package edu.cmu.cs.webapp.task7.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.cmu.cs.webapp.task7.databean.CustomerBean;
import edu.cmu.cs.webapp.task7.model.Model;


public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
        Model model = new Model(getServletConfig());

        Action.add(new LoginAction(model));
        Action.add(new CreateFundAction(model));
        Action.add(new LogoutAction(model));
        Action.add(new EmployeeMainAction(model));
        Action.add(new SellFundAction(model));
        Action.add(new CustomerMainAction(model));
        Action.add(new CreateEmployeeAction(model));
        Action.add(new CreateCustomerAction(model));
        Action.add(new DepositCheckAction(model));
        Action.add(new TransitionDayAction(model));
        Action.add(new ViewCustomerAction(model));
        Action.add(new RequestCheckAction(model));
        Action.add(new ChangePwdAction(model));
        Action.add(new ViewCustomerAccountAction(model));
        Action.add(new ResetPwdAction(model));
        Action.add(new HistoryAction(model));
        Action.add(new ViewPortfolioAction(model));
        Action.add(new BuyFundAction(model));
        Action.add(new ResearchFundAction(model));
        Action.add(new AccountManageC(model));
        Action.add(new AccountManageE(model));
        Action.add(new SuccessAction(model));
}


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nextPage = performTheAction(request);
        sendToNextPage(nextPage,request,response);
    }
    
    /*
     * Extracts the requested action and (depending on whether the user is logged in)
     * perform it (or make the user login).
     * @param request
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) {
        HttpSession session     = request.getSession(true);
        String      servletPath = request.getServletPath();
        String      action = getActionName(servletPath);

        if (session.getAttribute("user") == null) {
        	// If the user hasn't logged in, so login is the only option
			return Action.perform("login.do",request);
        }
        
        if (action.equals("welcome")) {
        	// User is logged in, but at the root of our web app
        	if (session.getAttribute("user") instanceof CustomerBean)
        		return Action.perform("customerMain.do",request);
        	else 
        		return Action.perform("employeeMain.do",request);
        }
        
      	// Let the logged in user run his chosen action
		return Action.perform(action,request);
    }
    
    /*
     * If nextPage is null, send back 404
     * If nextPage ends with ".do", redirect to this page.
     * If nextPage ends with ".jsp", dispatch (forward) to the page (the view)
     *    This is the common case
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	if (nextPage == null) {
    		response.sendError(HttpServletResponse.SC_NOT_FOUND,request.getServletPath());
    		return;
    	}
    	
    	if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
    	}
    	
    	if (nextPage.endsWith(".jsp")) {
	   		RequestDispatcher d = request.getRequestDispatcher("WEB-INF/" + nextPage);
	   		d.forward(request,response);
	   		return;
    	}
    	
    	throw new ServletException(Controller.class.getName()+".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

	/*
	 * Returns the path component after the last slash removing any "extension"
	 * if present.
	 */
    private String getActionName(String path) {
    	// We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash+1);
    }
}
