package io.recruitment.assessment.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    private int UserID;
    private String Firstname;
    private String Surname;
    private String AccessLevel;

    public User() {
    }

    public User(int UserID, String Firstname, String Surname, String AccessLevel) {
        this.UserID = UserID;
        this.Firstname = Firstname;
        this.Surname = Surname;
        this.AccessLevel = AccessLevel;
    }

    public User(User that) {
        this.UserID = that.UserID;
        this.Firstname = that.Firstname;
        this.Surname = that.Surname;
        this.AccessLevel = that.AccessLevel;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String Firstname) {
        this.Firstname = Firstname;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public String getAccessLevel() {
        return AccessLevel;
    }

    // NEED TO SOMEHOW MAKE THIS SECURE?
    public void setAccessLevel(String AccessLevel) {
        this.AccessLevel = AccessLevel;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserID:\"" + UserID + "\"," +
                "Firstname:\"" + Firstname + "\"," +
                "Surname:\"" + Surname + "\"," +
                "AccessLevel:\"" + AccessLevel + "\"}";
    }

}
