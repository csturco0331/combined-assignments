package com.cooksys.ftd.assignments.file.model;

public class Student {
    private Contact contact;
    
    public Student() {
    	
    };
    
    public Student(Contact contact) {
    	this.contact = contact;
    }
    
    public Student(String firstName, String lastName, String email, String phoneNumber) {
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
