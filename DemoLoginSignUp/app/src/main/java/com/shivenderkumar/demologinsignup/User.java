package com.shivenderkumar.demologinsignup;

import androidx.annotation.NonNull;

public class User {
    private String name;
    private String city;
    private String gender;
    private int age;

    public User(String name, String city, String gender, int age) {
        this.name = name;
        this.city = city;
        this.gender = gender;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return ""+age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @NonNull
    @Override
    public String toString() {
        return ""+this.getName()+" "+this.getCity()+" "+this.getGender()+" "+this.getAge();
    }
}
