package org.zeroglitch.mqtt.data;

public class User {
	int id = -1;
	String userId = null;
	String domain = null;
	int balance = -1;
	
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
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", domain=" + domain
				+ ", balance=" + balance + "]";
	}
	

	
	
	

}
