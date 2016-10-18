package edu.cmu.cs.webapp.task7.model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import edu.cmu.cs.webapp.task7.databean.EmployeeBean;

public class Model {
	private 	CustomerDAO 		customerDAO;
	private 	EmployeeDAO 		employeeDAO;
	private 	PositionDAO 			positionDAO;
	private 	FundDAO				fundDAO;
	private	TransactionDAO	transactionDAO;
	private 	FundPriceHistoryDAO	fundPriceHistoryDAO;

	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);
			
			customerDAO   = new CustomerDAO (pool, "customer");
			employeeDAO   = new EmployeeDAO(pool, "employee");
			positionDAO   = new PositionDAO(pool, "position");
			fundDAO   = new FundDAO(pool, "fund");
			transactionDAO   = new TransactionDAO(pool, "transaction");
			fundPriceHistoryDAO   = new FundPriceHistoryDAO(pool, "fund_price_history");
					
			EmployeeBean eb = employeeDAO.read( "root");
			if (eb == null) {
				eb = new EmployeeBean();
				eb.setUserName("root");
				eb.setFirstName("root");
				eb.setLastName("root");
				eb.setPassword("root");
				employeeDAO.create(eb);
			}
			
		} catch (DAOException | RollbackException e) {
			throw new ServletException(e);
		}
	}

	public 	CustomerDAO		getCustomerDAO () 		{ return customerDAO; }
	public 	EmployeeDAO		getEmployeeDAO  ()		{ return employeeDAO; }
	public 	PositionDAO 			getPositionDAO 	()			{ return positionDAO; }
	public 	FundDAO				getFundDAO		()				{ return fundDAO; }
	public		TransactionDAO	getTransactionDAO()		{ return transactionDAO; }
	public 	FundPriceHistoryDAO	getFundPriceHistoryDAO ()	{ return fundPriceHistoryDAO; }
}
