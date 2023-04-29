package com.example.applicationformyhomework_run;

public class WeatherResult {
    private String city;
    private String cityid;
    private String data;
    private String tem_day;
    private String tem_night;
    private String wea;
    private String wea_img;
    private String win;
    private String win_speed;
    private String tem;
    private int nums;
    private String update_time;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }


    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getTem() {
        return tem;
    }

    public String getData() {
        return data;
    }

    public String getTem_day() {
        return tem_day;
    }

    public String getTem_night() {
        return tem_night;
    }

    public String getWea() {
        return wea;
    }

    public String getWea_img() {
        return wea_img;
    }

    public String getWin() {
        return win;
    }

    public String getWin_speed() {
        return win_speed;
    }
}
