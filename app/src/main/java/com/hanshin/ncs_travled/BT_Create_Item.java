package com.hanshin.ncs_travled;
import android.graphics.drawable.Drawable;
import android.text.Editable;

import java.util.ArrayList;


public class BT_Create_Item {

    ArrayList<String> contents; //포토북 내용 (이미지 내용)
    ArrayList<String> contents2; // 포토북 내용(비디오 내용)
    String user;//사용자
    String title; // 포토북 제목
    String date; //포토북 여행시작일
    String date2; // 포토북 여행종료일
    String member; //포토북 여행멤버
    String area; // 포토북 여행지역  ex) 경기도
    String city; // 포토북 여행도시
    Drawable cover;//포토북 커버

    public ArrayList<String> getContents() {
        return contents;
    }

    public void setContents(ArrayList<String> contents) {
        this.contents = contents;
    }

    public ArrayList<String> getContents2() {
        return contents2;
    }

    public void setContents2(ArrayList<String> contents2) {
        this.contents2 = contents2;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Drawable getCover() {
        return cover;
    }

    public void setCover(Drawable cover) {
        this.cover = cover;
    }
}
