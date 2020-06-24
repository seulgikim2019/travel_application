package com.example.third.Class;


public class TravelListClass {


    public String travel_date1;
    public String travel_list_check;
    public int travel_img;
    public String travel_img_name;


    public TravelListClass(String travel_date1,String travel_list_check,int travel_img, String travel_img_name) {
        this.travel_date1 = travel_date1;
        this.travel_list_check=travel_list_check;
        this.travel_img = travel_img;
        this.travel_img_name = travel_img_name;
    }


    public String getTravel_date1() {
        return travel_date1;
    }


    public int getTravel_img() {
        return travel_img;
    }


    public String getTravel_img_name() {
        return travel_img_name;
    }

}
