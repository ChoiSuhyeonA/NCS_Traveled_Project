package com.hanshin.ncs_travled;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CT_recyclerViewHolder extends  RecyclerView.ViewHolder {
    ImageView imageView ;
    TextView titleStr ;
    TextView dateStr ;


    public CT_recyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        this.imageView = itemView.findViewById(R.id.ct_imageView);
        this.titleStr = itemView.findViewById(R.id.ct_title);
        this.dateStr = itemView.findViewById(R.id.ct_date);
    }

    public void onBind(CT_recyclerItem data){
        this.imageView.setImageResource(data.getImage());
        this.titleStr.setText(data.getTitle());
        this.dateStr.setText(data.getDate());
    }
}
