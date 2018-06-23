package com.cloud.licenta.app.model;

public class User {

	// TABLE CONSTANTS
	public final static String USER_TABLE = "USERS";
	public final static String FLD_ID = "ID";
	public final static String FLD_PASSWORD = "PASSWORD";
	public final static String FLD_FIRST_NAME = "FIRSTNAME";
	public final static String FLD_LAST_NAME = "LASTNAME";
	public final static String FLD_EMAIL = "EMAIL";
	public final static String FLD_PHONE_NUMBER = "PHONENUMBER";

	private Long id;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;

	public User(Long id, String password, String email, String firstName, String lastName, String phoneNumber) {
		super();
		this.id = id;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}

	public User() {
		this.id = (long) 0;
		this.password = "";
		this.email = "";
		this.firstName = "";
		this.lastName = "";
		this.phoneNumber = "";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", phoneNumber=" + phoneNumber + "]";
	}

}
