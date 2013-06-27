package org.zeroglitch.mqtt.data;

public class Transaction {
	
	int id = -1;
	String userId = "";
	int debit = 0;
	int credit = 0;
	String trandate = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getDebit() {
		return debit;
	}
	public void setDebit(int debit) {
		this.debit = debit;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getTrandate() {
		return trandate;
	}
	public void setTrandate(String trandate) {
		this.trandate = trandate;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", userId=" + userId + ", debit="
				+ debit + ", credit=" + credit + ", trandate=" + trandate + "]";
	}
	
	
	

}
