package com.hanshin.ncs_travled;
import android.graphics.drawable.Drawable;
import android.text.Editable;


public class BT_Create_Item {
    String contents[]; //포토북 내용 (이미지 내용)
    String contents2[]; // 포토북 내용(비디오 내용)
    String contents3[]; // 포토북 내용 (음성 내용)
    String user;//사용자
    String photoBookTitle; // 포토북 제목
    String photoBookTravelDate; //포토북 여행시작일
    String photoBookTravelDate2; // 포토북 여행종료일
    String photoBookTravelMember; //포토북 여행멤버
    String photoBookTravelArea; // 포토북 여행지역  ex) 경기도
    String photoBookTravelCity; // 포토북 여행도시
    Drawable photoBookTravelCover;//포토북 커버

    public String[] getContents() {
        return contents;
    }

    public void setContents(String[] contents) {
        this.contents = contents;
    }

    public String[] getContents2() {
        return contents2;
    }

    public void setContents2(String[] contents2) {
        this.contents2 = contents2;
    }

    public String[] getContents3() {
        return contents3;
    }

    public void setContents3(String[] contents3) {
        this.contents3 = contents3;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhotoBookTitle() {
        return photoBookTitle;
    }

    public void setPhotoBookTitle(String photoBookTitle) {
        this.photoBookTitle = photoBookTitle;
    }

    public String getPhotoBookTravelDate() {
        return photoBookTravelDate;
    }

    public void setPhotoBookTravelDate(String photoBookTravelDate) {
        this.photoBookTravelDate = photoBookTravelDate;
    }

    public String getPhotoBookTravelDate2() {
        return photoBookTravelDate2;
    }

    public void setPhotoBookTravelDate2(String photoBookTravelDate2) {
        this.photoBookTravelDate2 = photoBookTravelDate2;
    }

    public String getPhotoBookTravelMember() {
        return photoBookTravelMember;
    }

    public void setPhotoBookTravelMember(String photoBookTravelMember) {
        this.photoBookTravelMember = photoBookTravelMember;
    }

    public String getPhotoBookTravelArea() {
        return photoBookTravelArea;
    }

    public void setPhotoBookTravelArea(String photoBookTravelArea) {
        this.photoBookTravelArea = photoBookTravelArea;
    }

    public String getPhotoBookTravelCity() {
        return photoBookTravelCity;
    }

    public void setPhotoBookTravelCity(String photoBookTravelCity) {
        this.photoBookTravelCity = photoBookTravelCity;
    }

    public Drawable getPhotoBookTravelCover() {
        return photoBookTravelCover;
    }

    public void setPhotoBookTravelCover(Drawable photoBookTravelCover) {
        this.photoBookTravelCover = photoBookTravelCover;
    }
}
