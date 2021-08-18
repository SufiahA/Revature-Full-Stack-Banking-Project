package com.revature.models;

import java.util.List;

public class User {
	private int user_id;
	private String user_name;
	private String user_pass;
	private String first_name;
	private String last_name;
	private boolean active;
	private boolean employee;
	
	public User(int user_id, String user_name, String user_pass, String first_name, String last_name, boolean active,
			boolean employee) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_pass = user_pass;
		this.first_name = first_name;
		this.last_name = last_name;
		this.active = active;
		this.employee = employee;
	}
	
	public User(String user_name, String user_pass, String first_name, String last_name) {
		super();
		this.user_id = 0;
		this.user_name = user_name;
		this.user_pass = user_pass;
		this.first_name = first_name;
		this.last_name = last_name;
		this.active = false;
		this.employee = false;
	}
	
	//Getters and Setters
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public String getUser_pass() {
		return user_pass;
	}
	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isEmployee() {
		return employee;
	}
	public void setEmployee(boolean employee) {
		this.employee = employee;
	}
	
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name + ", user_pass=" + user_pass + ", first_name="
				+ first_name + ", last_name=" + last_name + ", active=" + active + ", employee=" + employee + "]";
	}
	
}
