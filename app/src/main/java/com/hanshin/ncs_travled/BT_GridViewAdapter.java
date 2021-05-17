package com.hanshin.ncs_travled;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class BT_GridViewAdapter extends BaseAdapter  {

    Context context;
    ArrayList<Uri> imageArrayList; // 갤러리에서 가져온 이미지 경로를 저장한 리스트
    ArrayList<Uri> videoArrayList; //갤러리에서 가져온 비디오 경로를 저장한 리스트
    ArrayList<Uri> seeArrayList;  // 그리드뷰에서 잠깐 보여주는 리스트



    public BT_GridViewAdapter(Context c, ArrayList<Uri> imageList, ArrayList<Uri> videoList, ArrayList<Uri> seeList){
        context = c;
        imageArrayList = imageList;
        videoArrayList = videoList;
        seeArrayList = seeList;
    }

    @Override
    public int getCount() {
        return seeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return seeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.plus, parent, false);
        }
        ImageView image = convertView.findViewById(R.id.plus1);

        TextView text = convertView.findViewById(R.id.bt_ListContentTV);


        if(BT_Activity.contents.get(position).contains("-")){
            text.setText("글 작성 x");
        }else{
            text.setText("글 작성 o");
        }


        if(String.valueOf(seeArrayList.get(position)).contains("video")){
            image.setImageResource(R.drawable.video);
        } else if(String.valueOf(seeArrayList.get(position)).contains("image")){
            image.setImageURI(seeArrayList.get(position));
        }


        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setPadding(3,5,3,5);



        return  convertView;
    }


    public void add(ArrayList<Uri> list,  int i){
        int type = i;
        if(type == 1){
            //1번일 경우 이미지를 리스트에 추가
            imageArrayList = list;
        } else if(type == 2){
            //2번일 경우 비디오를 리스트에 추가
            videoArrayList = list;
        } else if(type == 3){
            seeArrayList = list;
        }

    }
    public void delete() {
        int i = seeArrayList.size();
        //        //삭제할 페이지가 있을 경우에만 삭제하도록 조건문 설정.
        if (i > 0) {
            //이미지일 경우
            if (String.valueOf(seeArrayList.get(i - 1)).contains("image")) {
                int i1 = imageArrayList.size();
                if (i1 > 0) {
                    imageArrayList.remove(i1 - 1);
                    BT_Activity.contents1.remove(i1 - 1);
                    BT_Activity.contents.remove(i1 - 1);
                }
                seeArrayList.remove(i - 1);
                //비디오일 경우
            } else if (String.valueOf(seeArrayList.get(i - 1)).contains("video")) {
                int i1 = videoArrayList.size();
                if (i1 > 0) {
                    videoArrayList.remove(i1 - 1);
                    BT_Activity.contents2.remove(i1 - 1);
                    BT_Activity.contents.remove(i1 - 1);
                }
                seeArrayList.remove(i - 1);
            }
        }


    }
}