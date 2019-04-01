package com.example.shimul.ridesharing;

public class SignUpData {
    String name,email,password,age,nid,carlisense,phone;

    public SignUpData(String name, String email, String password, String age, String nid, String carlisense, String phone) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.nid = nid;
        this.carlisense = carlisense;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getCarlisense() {
        return carlisense;
    }

    public void setCarlisense(String carlisense) {
        this.carlisense = carlisense;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
