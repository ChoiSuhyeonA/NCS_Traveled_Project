package com.hanshin.ncs_travled;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class BT_GridViewAdapter extends BaseAdapter {

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

    public BT_GridViewAdapter(MainActivity M, ArrayList<Uri> imageList) {
        seeArrayList = imageList;
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

        if(String.valueOf(seeArrayList.get(position)).contains("video")){
            image.setImageResource(R.drawable.video);
        } else if(String.valueOf(seeArrayList.get(position)).contains("image")){
            image.setImageURI(seeArrayList.get(position));
        }

        image.setScaleType(ImageView.ScaleType.FIT_XY);

        image.setPadding(5,5,5,5);

        //이미지뷰 클릭할 때 이벤트 작성
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(),"버튼테스트",Toast.LENGTH_SHORT).show();

            }
        });

        return  convertView;
    }


    public void add(ArrayList<Uri> list,  int i){
        int type = i;
        if(type == 1){
            //1번일 경우 이미지를 리스트에 추가
            imageArrayList = list;
        }
        else if(type == 2){
            //2번일 경우 비디오를 리스트에 추가
            videoArrayList = list;
        }
        else if(type == 3){
            seeArrayList = list;
        }

    }
    public void delete(){
        int i = seeArrayList.size();
        //삭제할 페이지가 있을 경우에만 삭제하도록 조건문 설정.
        if(i >0){
            if(String.valueOf(seeArrayList.get(i-1)).contains("image")){
                int i1 = imageArrayList.size();
                if(i1>0){
                    imageArrayList.remove(i1-1);
                }
            }else if(String.valueOf(seeArrayList.get(i-1)).contains("video")){
                int i1 = videoArrayList.size();
                if(i1>0){
                    videoArrayList.remove(i1-1);
                }
            }
           seeArrayList.remove(i-1);
        }
    }


}