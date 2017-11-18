package com.carehack.ashikvarma.patientportal.Models;

/**
 * Created by Ashik Varma on 10/16/2017.
 */

public class Doctor {

    private String department;
    private String name;
    private String place;

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}
