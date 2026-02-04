package com.example.d424_software_engineering_capstonee.models;

public class Search {
    private String type;
    private String price;
    private String title;
    private String dates;
    private int id;

    public String getDetails() {
        return details;
    }

    public int getId() {
        return id;
    }

    public String getDates() {
        return dates;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    private String details;

    public Search(String type, int id, String details, String title, String dates, String price){
        this.type=type;
        this.id = id;
        this.details=details;
        this.price= price;
        this.dates=dates;
        this.title=title;
    }


}
