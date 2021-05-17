package com.hanshin.ncs_travled;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HT_GridViewAdapter extends BaseAdapter {
    
    Context context;
    ArrayList<Uri> imageList;
    ArrayList<Uri> videoList;

    public HT_GridViewAdapter(Context c, ArrayList<Uri> imageList, ArrayList<Uri> videoList) {
        context = c;
        this.imageList = imageList;
        this.videoList = videoList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gallery, parent, false);
        }
        ImageView image = convertView.findViewById(R.id.plus1);

        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setPadding(3,5,3,5);

        Glide.with(context).load(imageList.get(position)).into(image);

        return convertView;
    }

    public void add(ArrayList<Uri> imageList, ArrayList<Uri> videoList) {
    this.imageList = imageList;
    this.videoList = videoList;
    }
}
