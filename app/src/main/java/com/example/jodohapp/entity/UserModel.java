package com.example.jodohapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {

    private String username;
    private String name;
    private String password;
    private String age;
    private String gender;
    private String phone;
    private String location;
    private String picture;

    public UserModel(){

    }
    public UserModel(String username, String name, String password, String age, String gender, String phone, String location, String picture) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.location = location;
        this.picture = picture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.name);
        dest.writeString(this.password);
        dest.writeString(this.age);
        dest.writeString(this.gender);
        dest.writeString(this.phone);
        dest.writeString(this.location);
        dest.writeString(this.picture);
    }

    public void readFromParcel(Parcel source) {
        this.username = source.readString();
        this.name = source.readString();
        this.password = source.readString();
        this.age = source.readString();
        this.gender = source.readString();
        this.phone = source.readString();
        this.location = source.readString();
        this.picture = source.readString();
    }

    protected UserModel(Parcel in) {
        this.username = in.readString();
        this.name = in.readString();
        this.password = in.readString();
        this.age = in.readString();
        this.gender = in.readString();
        this.phone = in.readString();
        this.location = in.readString();
        this.picture = in.readString();
    }

    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
