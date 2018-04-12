package com.example.saksham.blackoutrestuarant.item;

import java.util.List;

/**
 * Created by saksham_ on 09-Apr-18.
 */

public class OrderRequest {

    private String phone;
    private String name;
    private String flatNo;
    private String street;
    private String landmark;
    private String pincode;
    private String city;
    private String state;
    private List<CartDetail> foodList;
    public OrderRequest(){

    }

    public OrderRequest(String phone, String name, String flatNo, String street, String landmark, String pincode, String city, String state,List<CartDetail> foodList) {
        this.phone = phone;
        this.name = name;
        this.flatNo = flatNo;
        this.street = street;
        this.landmark = landmark;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.foodList=foodList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public List<CartDetail> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<CartDetail> foodList) {
        this.foodList = foodList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
