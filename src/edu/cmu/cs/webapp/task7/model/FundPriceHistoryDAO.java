package edu.cmu.cs.webapp.task7.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;




import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import edu.cmu.cs.webapp.task7.databean.FundPriceHistoryBean;

public class FundPriceHistoryDAO extends GenericDAO<FundPriceHistoryBean> {
	public FundPriceHistoryDAO(ConnectionPool cp, String tableName)
			throws DAOException {
		super(FundPriceHistoryBean.class, tableName, cp);
	}
	

	public FundPriceHistoryBean getLatestFundPrice(int fundId) throws RollbackException{
		FundPriceHistoryBean[] fundPriceHistory = match(MatchArg.equals("fundId", fundId));
		if(fundPriceHistory.length == 0) return null;
		return fundPriceHistory[fundPriceHistory.length-1];
	}

	public double getPrice(int fundId, String date) throws RollbackException{
		System.out.println("5");
		FundPriceHistoryBean[] fundPriceHistory = match(MatchArg.equals("fundId", fundId), MatchArg.equals("priceDate", date));
		System.out.println("6");
		
		System.out.println(fundId);
		
		return fundPriceHistory[fundPriceHistory.length-1].getPrice() / 100.0;
	}
	
	public long getLatestPrice (int id) throws RollbackException, ParseException {
		long price = 0;
		try {
			Transaction.begin();
			String date = null;
			FundPriceHistoryBean[] fb = match();
			if (fb != null && fb.length != 0) {
				Arrays.sort(fb);
				date = fb[fb.length - 1].getPriceDate();
			}
			price = read(id, date).getPrice();

			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
		return price;
	}
	
	public Date getLatestTradingDayDate () throws RollbackException, ParseException {

		Date date = null;
		try {
			Transaction.begin();

			FundPriceHistoryBean[] fb = match();

			if (fb != null && fb.length != 0) {
				Arrays.sort(fb);
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				dateFormat.setLenient(false);
				date = dateFormat.parse(fb[fb.length - 1].getPriceDate());
			}
			
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		return date;
	}
	
	public String getLatestTradingDayDateString () throws RollbackException, ParseException {
		String date = null;
		try {
			Transaction.begin();
			
			FundPriceHistoryBean[] fb = match();
			if (fb != null && fb.length != 0) {
				Arrays.sort(fb);
				date = fb[fb.length - 1].getPriceDate();
			}
			

			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
		return date;
	}
	
	public String getLatestTradingDayByCustomer (String userName) throws RollbackException, ParseException {
		String date = null;
		try {
			Transaction.begin();
			
			FundPriceHistoryBean[] fb = match(MatchArg.equals("userName", userName));
			if (fb != null && fb.length != 0) {
				Arrays.sort(fb);
				
				date = fb[fb.length - 1].getPriceDate();
			}
			
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
		return date;
	}
}
