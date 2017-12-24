package com.rohan.test;

/**
 * Created by Rohan on 12/23/2017.
 */

public class User {
    private String user_email, desc, date;
    private int payment;

    public User() {

    }

    public User(String user_email, int payment, String desc, String date) {
        this.user_email = user_email;
        this.payment = payment;
        this.desc = desc;
        this.date = date;

    }

    public String getUser_email() {
        return user_email;
    }

    public String getDesc() {
        return desc;
    }

    public int getPayment() {
        return payment;
    }

    public String getDate() {
        return date;
    }
}
