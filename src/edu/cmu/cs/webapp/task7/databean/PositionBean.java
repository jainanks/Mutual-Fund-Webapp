package edu.cmu.cs.webapp.task7.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("userName,fundId")
public class PositionBean {
	private String 	userName;
	private int 		fundId;
	private long 		shares;
	
	public void setUserName			(String v) 	{ userName = v; }
	public void setFundId					(int v)			{ fundId = v; 	}
	public void setShares 					(long  v)		{ shares = v;	}

	public String 	getUserName	() { return userName; 	}
	public int 		getFundId			() { return fundId; 	}
	public long 	getShares 			() { return shares;	}
}
