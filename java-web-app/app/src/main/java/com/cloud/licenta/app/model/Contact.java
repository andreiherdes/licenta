package com.cloud.licenta.app.model;

public class Contact {

	// TABLE CONSTANTS
	public final static String CONTACT_TABLE = "CONTACTREQUESTS";
	public final static String FLD_ID = "REQUEST_ID";
	public final static String FLD_FIRST_NAME = "FIRST_NAME";
	public final static String FLD_LAST_NAME = "LAST_NAME";
	public final static String FLD_EMAIL = "EMAIL";
	public final static String FLD_TEXT = "REQUEST";

	private String firstName;
	private String lastName;
	private String email;
	private String text;

	public Contact() {
		firstName = "";
		lastName = "";
		email = "";
		text = "";
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
