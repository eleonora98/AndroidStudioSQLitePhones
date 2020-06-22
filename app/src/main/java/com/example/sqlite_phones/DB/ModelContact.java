package com.example.sqlite_phones.DB;

import java.io.Serializable;

public class ModelContact implements Serializable {
    private String id;
    private String name;
    private String phone;
    private String category;
    private String email;
    private String address;

    public ModelContact(String id, String name, String phone, String category, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.category = category;
        this.email = email;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String phone) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String phone) {
        this.address = address;
    }

}
