package com.example.third.Class;


import java.io.Serializable;

public class TravelContentsClass implements Serializable {


    public String travel_all_time;
    public String travel_all_todo;
    public String travel_all_detail;



    public TravelContentsClass(String travel_all_time, String travel_all_todo, String travel_all_detail) {
        this.travel_all_time = travel_all_time;
        this.travel_all_todo = travel_all_todo;
        this.travel_all_detail = travel_all_detail;
    }


    public String getTravel_all_time() {
        return travel_all_time;
    }

    public void setTravel_all_time(String travel_all_time) {
        this.travel_all_time = travel_all_time;
    }

    public String getTravel_all_todo() {
        return travel_all_todo;
    }

    public void setTravel_all_todo(String travel_all_todo) {
        this.travel_all_todo = travel_all_todo;
    }

    public String getTravel_all_detail() {
        return travel_all_detail;
    }

    public void setTravel_all_detail(String travel_all_detail) {
        this.travel_all_detail = travel_all_detail;
    }



}
