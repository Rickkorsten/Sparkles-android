package com.fhict.sparklesandroid.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("succes_rate")
    @Expose
    private Integer succesRate;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("personal_details_id")
    @Expose
    private String personalDetailsId;
    @SerializedName("interest_id")
    @Expose
    private String interestId;
    @SerializedName("creation_date")
    @Expose
    private Date creationDate;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("date_of_birth")
    @Expose
    private Date dateOfBirth;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("preference")
    @Expose
    private String preference;
    @SerializedName("userImage")
    @Expose
    private String userImage;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSuccesRate() {
        return succesRate;
    }

    public void setSuccesRate(Integer succesRate) {
        this.succesRate = succesRate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonalDetailsId() {
        return personalDetailsId;
    }

    public void setPersonalDetailsId(String personalDetailsId) { this.personalDetailsId = personalDetailsId; }

    public String getInterestId() {
        return interestId;
    }

    public void setInterestId(String interestId) {
        this.interestId = interestId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    @Override
    public String toString() {
        return "User{" +
                "lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                ", succesRate=" + succesRate +
                ", language='" + language + '\'' +
                ", id='" + id + '\'' +
                ", personalDetailsId='" + personalDetailsId + '\'' +
                ", interestId='" + interestId + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", firstName='" + firstName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", gender='" + gender + '\'' +
                ", preference='" + preference + '\'' +
                ", userImage='" + userImage + '\'' +
                '}';
    }
}
