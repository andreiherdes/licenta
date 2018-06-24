package com.cloud.licenta.app.model;

public class Register {

	private String firstName;
	private String lastName;
	private String emailReg;
	private String passwordReg;
	private boolean isOrganization;

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

	public String getEmailReg() {
		return emailReg;
	}

	public void setEmailReg(String emailReg) {
		this.emailReg = emailReg;
	}

	public String getPasswordReg() {
		return passwordReg;
	}

	public void setPasswordReg(String passwordReg) {
		this.passwordReg = passwordReg;
	}

	public boolean isOrganization() {
		return isOrganization;
	}

	public void setOrganization(boolean isOrganization) {
		this.isOrganization = isOrganization;
	}

}
