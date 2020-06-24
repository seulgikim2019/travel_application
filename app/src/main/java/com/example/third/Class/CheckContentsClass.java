package com.example.third.Class;

public class CheckContentsClass {

    public boolean check_box;
    public String check_name;

    public CheckContentsClass(String check_name, boolean check_box) {

        this.check_name = check_name;
        this.check_box = check_box;
    }

    public boolean getCheck_box() {
        return check_box;
    }

    public void setCheck_box(boolean check_box) {
        this.check_box = check_box;
    }

}
