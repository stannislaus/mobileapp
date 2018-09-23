package com.driverapp.riderapp.Model;

public class Rider {
    private String email, password, phone, name;

    public Rider(){
    }
    public Rider(String email, String password, String phone, String name){
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
