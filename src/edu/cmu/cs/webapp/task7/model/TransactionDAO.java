package edu.cmu.cs.webapp.task7.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;




import edu.cmu.cs.webapp.task7.databean.CustomerBean;
import edu.cmu.cs.webapp.task7.databean.TransactionBean;

public class TransactionDAO extends GenericDAO<TransactionBean> {
	public TransactionDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(TransactionBean.class, tableName, cp);
	}
	
	public double getValidBalance (String userName, double amount) throws RollbackException {
		TransactionBean[] tbs = null;
		try {
			Transaction.begin();
			
			// How to execute select * from table where transactionType IS NULL
			tbs =  match(MatchArg.equals("executeDate", null), MatchArg.equals("userName", userName));
			
			if (tbs != null) {
				for (TransactionBean t : tbs) {
					switch(t.getTransactionType()) {
					case TransactionBean.BUY_FUND:
						amount -= t.getAmount() / 100.00;
						break;
					case TransactionBean.REQ_CHECK:
						amount -= t.getAmount() / 100.00;
						break;
					case TransactionBean.DPT_CHECK:
						amount += t.getAmount() / 100.00;
						break;	
					default:
						break;
					}
				}
			}
			
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		
		return amount;
	}
	
	
	public boolean requestCheck (String userName, double balance, double amount) throws RollbackException {
		boolean isSucceeded = false;
		try {
			Transaction.begin();
			
			TransactionBean[] tbs =  match(MatchArg.equals("executeDate", null), MatchArg.equals("userName", userName));
			
			if (tbs != null) {
				for (TransactionBean t : tbs) {
					switch(t.getTransactionType()) {
					case TransactionBean.BUY_FUND:
						balance -= t.getAmount() / 100.00;
						break;
					case TransactionBean.REQ_CHECK:
						balance -= t.getAmount() / 100.00;
						break;
					case TransactionBean.DPT_CHECK:
						balance += t.getAmount() / 100.00;
						break;	
					default:
						break;
					}
				}
			}
			
			if (balance >= amount) {
				TransactionBean tb = new TransactionBean();
				tb.setUserName(userName);
				tb.setExecuteDate(null);
				tb.setTransactionType(TransactionBean.REQ_CHECK);
				tb.setAmount((long)(amount * 100));
				createAutoIncrement(tb);
				isSucceeded = true;
			}
			
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		
		return isSucceeded;
	}
	
	
	public double getValidShares (String userName, double shares, int fundId) throws RollbackException {
		TransactionBean[] tbs = null;
		try {
			Transaction.begin();
			
			tbs =  match(MatchArg.equals("fundId", fundId), MatchArg.equals("transactionType", TransactionBean.SELL_FUND), 
					MatchArg.equals("executeDate", null), MatchArg.equals("userName", userName));
			
			if (tbs != null) {
				for (TransactionBean t : tbs) {
					shares -= t.getShares() / 1000.0;
				}
			}
			
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		
		return shares;
	}
	
	
	public boolean sellShares (String userName, double shares, int fundId, double shares_sell) throws RollbackException {
		boolean isSucceeded = false;
		try {
			Transaction.begin();
			
			TransactionBean[] tbs  =  match(MatchArg.equals("fundId", fundId), MatchArg.equals("transactionType", TransactionBean.SELL_FUND), 
					MatchArg.equals("executeDate", null), MatchArg.equals("userName", userName));
			
			if (tbs != null) {
				for (TransactionBean t : tbs) {
					shares -= t.getShares() / 1000.0;
				}
			}
			
			System.out.println(shares + " " +shares_sell);
			
			if (shares >= shares_sell) {
				
		        TransactionBean transbean= new TransactionBean();
		        transbean.setUserName(userName);
		        transbean.setFundId(fundId);
		        transbean.setShares((long)(shares_sell * 1000.0));
		        transbean.setTransactionType(TransactionBean.SELL_FUND);
		        transbean.setExecuteDate(null);
		        create(transbean);
		        
				isSucceeded = true;
			}
			
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		
		return isSucceeded;
	}
	
	public String getLastDate(CustomerBean c) throws RollbackException{
		String date = null;
		try {
			Transaction.begin();
		
			TransactionBean[] transaction  = match(MatchArg.notEquals("executeDate", null), MatchArg.equals("userName", c.getUserName()));
			if( transaction == null || transaction.length == 0) date = null;
			else {
				Arrays.sort(transaction);
				date = transaction[transaction.length-1].getExecuteDate();
			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		
		return date;
	}
	
	public Date getLatestDate () throws RollbackException, ParseException {
		Date date = null;
		try {
			Transaction.begin();
		
			TransactionBean[] t =  match(MatchArg.notEquals("executeDate", null));
			if (t != null && t.length != 0) {
				Arrays.sort(t);
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				dateFormat.setLenient(false);
				date = dateFormat.parse(t[t.length - 1].getExecuteDate());
			}
			
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		return date;
	}

	public boolean buyFund (String userName, double balance, double amount, int fundid) throws RollbackException {
		boolean isSucceeded = false;
		try {
			Transaction.begin();
			
			TransactionBean[] tbs =  match(MatchArg.equals("executeDate", null), MatchArg.equals("userName", userName));
			
			if (tbs != null) {
				for (TransactionBean t : tbs) {
					switch(t.getTransactionType()) {
					case TransactionBean.BUY_FUND:
						balance -= t.getAmount() / 100.00;
						break;
					case TransactionBean.REQ_CHECK:
						balance -= t.getAmount() / 100.00;
						break;
					case TransactionBean.DPT_CHECK:
						balance += t.getAmount() / 100.00;
						break;	
					default:
						break;
					}
				}
			}
			
			if (balance >= amount) {
				TransactionBean tb = new TransactionBean();
				tb.setUserName(userName);
				tb.setFundId(fundid);
				tb.setExecuteDate(null);
				tb.setTransactionType(TransactionBean.BUY_FUND);
				tb.setAmount((long)(amount * 100));
				createAutoIncrement(tb);							
				isSucceeded = true;
			}
			
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		
		return isSucceeded;
	}
}
