package com.hanshin.ncs_travled;

import android.graphics.drawable.Drawable;

public class CT_Item {
    private Drawable imageDrawable ;
    private String titleStr ;
    private String dateStr ;

    public void setCover(Drawable cover) {
        imageDrawable = cover ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDate(String date) {
        dateStr = date ;
    }

    public Drawable getCover() {
        return this.imageDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDate() {
        return this.dateStr ;
    }
}
