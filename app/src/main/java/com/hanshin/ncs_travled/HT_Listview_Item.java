package com.hanshin.ncs_travled;

        import android.graphics.drawable.Drawable;

public class HT_Listview_Item {
    private Drawable coverDrawable ;
    private String titleStr ;
    private String placeStr ;
    private String memberStr ;
    private String dateStr ;

    public void setCover(Drawable cover) {
        coverDrawable = cover ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setPlace(String place) {
        placeStr = place ;
    }
    public void setMember(String member) {
        memberStr = member ;
    }
    public void setDate(String date) {
        dateStr = date ;
    }

    public Drawable getCover() {
        return this.coverDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getPlace() {
        return this.placeStr ;
    }
    public String getMember() {
        return this.memberStr ;
    }
    public String getDate() {
        return this.dateStr ;
    }
}
