package com.revature.models;

public class Account {
	private int acc_id;
	private int acc_owner;
	private String acc_type;
	private double acc_amount;
	
	public Account(int acc_id, int acc_owner, String acc_type, double acc_amount) {
		super();
		this.acc_id = acc_id;
		this.acc_owner = acc_owner;
		this.acc_type = acc_type;
		this.acc_amount = acc_amount;
	}
	
	public Account(int acc_owner, String acc_type, double acc_amount) {
		super();
		this.acc_id = 0;
		this.acc_owner = acc_owner;
		this.acc_type = acc_type;
		this.acc_amount = acc_amount;
	}
	
	public int getAcc_id() {
		return acc_id;
	}
	public void setAcc_id(int acc_id) {
		this.acc_id = acc_id;
	}
	public int getAcc_owner() {
		return acc_owner;
	}
	public void setAcc_owner(int acc_owner) {
		this.acc_owner = acc_owner;
	}
	public String getAcc_type() {
		return acc_type;
	}
	public void setAcc_type(String acc_type) {
		this.acc_type = acc_type;
	}
	public double getAcc_amount() {
		return acc_amount;
	}
	public void setAcc_amount(double acc_amount) {
		this.acc_amount = acc_amount;
	}
	
	@Override
	public String toString() {
		return "Account [acc_id=" + acc_id + ", acc_owner=" + acc_owner + ", acc_type=" + acc_type + ", acc_amount="
				+ acc_amount + "]";
	}
}
