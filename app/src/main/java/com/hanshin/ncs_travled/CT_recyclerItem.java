package com.hanshin.ncs_travled;

public class CT_recyclerItem {
    private int image;
    private String title;
    private String date;

    public CT_recyclerItem(int image, String title, String date){
        this.image = image;
        this.title = title;
        this.date = date;
    }
    public int getImage(){
        return this.image;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDate(){
        return this.date;
    }

    public void setImage(int image){
        this.image = image;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDate(String date){
        this.date = date;
    }

}
