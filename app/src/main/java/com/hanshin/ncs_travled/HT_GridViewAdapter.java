package com.hanshin.ncs_travled;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HT_GridViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<Uri> imageList;
    ArrayList<Uri> videoList;
    ArrayList<Uri> seeList = new ArrayList<Uri>();

    int videoNumber=1;
    int imageNumber=0;

    public HT_GridViewAdapter(Context c, ArrayList<Uri> imageList, ArrayList<Uri> videoList,  ArrayList<Uri> seeList) {
        context = c;
        this.imageList = imageList;
        this.videoList = videoList;
        this.seeList = seeList;
    }

    @Override
    public int getCount() {
        return seeList.size();
    }

    @Override
    public Object getItem(int position) {
        return seeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gallery, parent, false);

        }

        if(seeList.get(position).toString().contains("png") || seeList.get(position).toString().contains("jpeg")|| seeList.get(position).toString().contains("image")){
            ImageView image = convertView.findViewById(R.id.plus1);
            TextView text = convertView.findViewById(R.id.ht_ListTitle);
            Glide.with(context).load(seeList.get(position)).into(image);
            text.setText("이미지 #"+  String.valueOf(imageNumber));
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setPadding(0,5,0,5);
            imageNumber++;
        }else if(seeList.get(position).toString().contains("mp4") || seeList.get(position).toString().contains("video")){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(R.layout.gallery2, parent, false);
            ImageView videoImage = convertView.findViewById(R.id.plus2);
            TextView text = convertView.findViewById(R.id.ht_ListTitle2);
            videoImage.setImageResource(R.drawable.video);
            text.setText("비디오 #"+String.valueOf(videoNumber));
            videoNumber++;
        }

        return convertView;
    }

    public void add( ArrayList<Uri> imageList, ArrayList<Uri> videoList,  ArrayList<Uri> seeList) {
    this.imageList = imageList;
    this.videoList = videoList;
    this.seeList = seeList;
    }
}
