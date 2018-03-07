package com.emeraldElves.alcohollabelproject.data;

import com.emeraldElves.alcohollabelproject.Data.EmailAddress;
import com.emeraldElves.alcohollabelproject.Data.PhoneNumber;
import com.emeraldElves.alcohollabelproject.Data.UserType;

import java.util.Objects;

public class User {

    public static final String DB_TABLE = "users";
    public static final String DB_NAME = "name";
    public static final String DB_PASSWORD = "password";
    public static final String DB_USER_TYPE = "user_type";
    public static final String DB_ID = "id";
    public static final String DB_APPROVED = "approved";
    public static final String DB_COMPANY = "company";
    public static final String DB_ADDRESS = "address";
    public static final String DB_PHONE = "phone";
    public static final String DB_EMAIL = "email";
    public static final String DB_PERMIT_NO = "permit_no";
    public static final String DB_REP_ID = "rep_id";


    private String name;
    private String password;
    private UserType type;
    private long id;
    private boolean approved;
    private String company;
    private String address;
    private PhoneNumber phoneNumber;
    private EmailAddress email;
    private long repID;
    private long permitNo;

    public User(String name, String password, UserType type) {
        this.name = name;
        this.password = password;
        this.type = type;
        id = -1;
        approved = false;
        company = "";
        address = "";
        phoneNumber = new PhoneNumber("");
        email = new EmailAddress("");
        repID = -1;
        permitNo = -1;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    public long getRepID() {
        return repID;
    }

    public void setRepID(long repID) {
        this.repID = repID;
    }

    public long getPermitNo() {
        return permitNo;
    }

    public void setPermitNo(long permitNo) {
        this.permitNo = permitNo;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public UserType getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", approved=" + approved +
                ", company='" + company + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email=" + email +
                ", repID=" + repID +
                ", permitNo=" + permitNo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                approved == user.approved &&
                repID == user.repID &&
                permitNo == user.permitNo &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                type == user.type &&
                Objects.equals(company, user.company) &&
                Objects.equals(address, user.address) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, password, type, id, approved, company, address, phoneNumber, email, repID, permitNo);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
