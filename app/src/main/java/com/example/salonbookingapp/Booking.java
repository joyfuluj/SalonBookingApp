package com.example.salonbookingapp;

public class Booking {
    private String salonName;
    private String menu;
    private String stylist;
    private String date;
    private String time;

    public Booking(String salonName, String menu, String stylist, String date, String time) {
        this.salonName = salonName;
        this.menu = menu;
        this.stylist = stylist;
        this.date = date;
        this.time = time;
    }

    // Getter メソッド
    public String getSalonName() {
        return salonName;
    }

    public String getMenu() {
        return menu;
    }

    public String getStylist() {
        return stylist;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
