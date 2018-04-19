/**
 * Created by Ksenia on 4/18/2018.
 * 040892102
 * CST2335_010
 */

package com.example.delle6330.assignment1;

/**
 * object of Patient
 */
public class Patient {

    /**
     * doctor choice of patient
     */
    String doctorChoice;
    /**
     * name, address, birthday and reason for a visit of a patient
     */
    String name, address, birthday, visitReason;
    /**
     * phone number and health card number of a patient
     */
    String phoneNumber, ssNumber;

    /**
     * additional questions of each patient
     */
    String addQuestion1, addQuestion2;

    /**
     * id of a patient
     */
    int id;

    public Patient(){}

    public Patient(int id, String doctorChoice, String name, String address, String birthday, String phoneNumber,
                   String ssNumber, String visitReason, String addQuestion1, String addQuestion2){

        this.id = id;
        this.name = name;
        this.address = address;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.ssNumber = ssNumber;
        this.visitReason = visitReason;
        this.doctorChoice = doctorChoice;
        this.addQuestion1 = addQuestion1;
        this.addQuestion2 = addQuestion2;
    }

    public void setDoctorChoice(String doctorChoice) {
        this.doctorChoice = doctorChoice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSsNumber(String ssNumber) {
        this.ssNumber = ssNumber;
    }

    public void setAddQuestion1(String addQuestion1) {
        this.addQuestion1 = addQuestion1;
    }

    public void setAddQuestion2(String addQuestion2) {
        this.addQuestion2 = addQuestion2;
    }

    public String getDoctorChoice() {
        return doctorChoice;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSsNumber() {
        return ssNumber;
    }

    public String getAddQuestion1() {
        return addQuestion1;
    }

    public String getAddQuestion2() {
        return addQuestion2;
    }
}
