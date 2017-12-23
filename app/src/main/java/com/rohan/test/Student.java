package com.rohan.test;

import java.io.Serializable;

/**
 * Created by Rohan on 12/15/2017.
 */

public class Student implements Serializable {
    private String s_id, name, mobile_no, pickup_point, fees_taken_by, entry_id;
    private int fees, paid_fees;


    public Student() {

    }


    public Student(String entry_id, String s_id, String name, String mobile_no, int fees, String pickup_point) {
        this.entry_id = entry_id;
        this.s_id = s_id;
        this.name = name;
        this.mobile_no = mobile_no;
        this.fees = fees;
        this.pickup_point = pickup_point;
        this.paid_fees = 0;
        this.fees_taken_by = "-";


    }

    public String getEntry_id() {
        return entry_id;
    }

    public String getS_id() {
        return s_id;
    }

    public String getName() {
        return name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public int getFees() {
        return fees;
    }

    public String getPickup_point() {
        return pickup_point;
    }

    public String getFees_taken_by() {
        return fees_taken_by;
    }

    public void setFees_taken_by(String fees_taken_by) {
        this.fees_taken_by = fees_taken_by;
    }

    public void setPaid_fees(int paid_fees) {
        this.paid_fees = paid_fees;
    }

    public int getPaid_fees() {
        return paid_fees;
    }


    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public void setPickup_point(String pickup_point) {
        this.pickup_point = pickup_point;
    }

    public void setEntry_id(String entry_id) {
        this.entry_id = entry_id;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }
}
