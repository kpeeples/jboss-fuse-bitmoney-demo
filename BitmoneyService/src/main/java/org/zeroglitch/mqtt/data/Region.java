package org.zeroglitch.mqtt.data;

public class Region {
	
	private int id = 0;
	private String name = "";
	private double rate = 0.0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	@Override
	public String toString() {
		return "Region [id=" + id + ", name=" + name + ", rate=" + rate + "]";
	}
	
	
	

}
