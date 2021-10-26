package com.nivacreation.login.model;

public class UserSupport {

    String firstName, lastName, contact;

    public UserSupport() {

    }

    public UserSupport(String firstName, String lastName, String contact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
