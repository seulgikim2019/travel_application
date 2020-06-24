package com.example.third.Class;


public class AllTravelListClass {


    public String alltravel_date;
    public int alltravel_img;
    public String all_travel_img_name;
    public String alltravel_time;
    public String all_travel_content;


    public AllTravelListClass(String alltravel_date, int alltravel_img, String all_travel_img_name,String alltravel_time) {
        this.alltravel_date = alltravel_date;
        this.alltravel_img = alltravel_img;
        this.all_travel_img_name = all_travel_img_name;
        this.alltravel_time=alltravel_time;
    }

    public void setAlltravel_time(String alltravel_time) {
        this.alltravel_time = alltravel_time;
    }


    public String getAll_travel_img_name() {
        return all_travel_img_name;
    }


}
