package com.cooksys.serialization.assignment.model;

public class Instructor {
	private Contact contact;

	public Instructor() {

	};

	public Instructor(Contact contact) {
		this.contact = contact;
	}

	public Instructor(String firstName, String lastName, String email, String phoneNumber) {
		contact.setFirstName(firstName);
		contact.setLastName(lastName);
		contact.setEmail(email);
		contact.setPhoneNumber(phoneNumber);
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
}
