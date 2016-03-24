package com.test.jpjensen;

/**
 * Created by jjensen on 3/24/16.
 */
public class Person {
    private String firstName;
    private String lastName;

    public Person (){

    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    @Override
    public String toString(){
        return "firstname: " + firstName + ", lastname: " + lastName;
    }
}
