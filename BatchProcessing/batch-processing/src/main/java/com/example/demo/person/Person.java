package com.example.demo.person;

import javax.validation.constraints.Size;

public class Person {
	@Size(min = 3, max = 4, message = "length should be either 3 or 4")
    private String lastName;
	@Size(min = 3, max = 4, message = "length should be either 3 or 4")
    private String firstName;

    public Person() {

    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "firstName: " + firstName + ", lastName: " + lastName;
    }

}
