package edu.cmu.cs.webapp.task7.databean;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.genericdao.PrimaryKey;

@PrimaryKey("fundId,priceDate")
public class FundPriceHistoryBean implements Comparable<FundPriceHistoryBean> {
	private int 		fundId;
	private String 	priceDate;
	private long	 	price;
	
	public void setFundId			(int v)			{ fundId = v; 	}
	public void setPriceDate 		(String v)		{ priceDate = v;	}
	public void setPrice				(long  v)		{ price = v;	}
	
	public int 		getFundId		()		{ return fundId; 	}
	public String 	getPriceDate 	()	 	{ return priceDate;	}
	public long 	getPrice		 	()	 	{ return price;	}
	
	@Override
	public int compareTo(FundPriceHistoryBean fb) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    	dateFormat.setLenient(false);
		try {
			return dateFormat.parse(this.priceDate).compareTo(dateFormat.parse(fb.priceDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
