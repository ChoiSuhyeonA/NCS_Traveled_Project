package com.hanshin.ncs_travled;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CT_Activity extends Activity {

    CT_Adapter listAdapter;
    RecyclerView recyclerView;
    CT_recyclerAdapter recyclerAdapter;
    ListView listview;
    Button writeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct_list);

        ///////////// 관리자 게시판
        init();
        getData();

        ///////////// 사용자 게시판 
        listAdapter = new CT_Adapter();

        listview = findViewById(R.id.ct_listview);
        listview.setAdapter(listAdapter);

        listAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.cover_spring),"Book1","2020/03/15");
        listAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.cover_autumn),"Book2","2020/02/21");
        listAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.cover_summer),"Book3","2020/01/04");
        listAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.cover_winter),"Book4","2019/12/23");

        writeBtn = findViewById(R.id.ct_writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"글 쓰기 버튼",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init() {
        recyclerView = findViewById(R.id.ct_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new CT_recyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
    }
    private void getData() {
        CT_recyclerItem recyclerItem = new CT_recyclerItem(R.drawable.cover_spring,"list1","2020/02/12");
        recyclerAdapter.addItem(recyclerItem);
        recyclerItem = new CT_recyclerItem(R.drawable.cover_autumn,"list2","2020/02/12");
        recyclerAdapter.addItem(recyclerItem);
        recyclerItem = new CT_recyclerItem(R.drawable.cover_summer,"list3","2020/02/12");
        recyclerAdapter.addItem(recyclerItem);
        recyclerItem = new CT_recyclerItem(R.drawable.cover_winter,"list4","2020/02/12");
        recyclerAdapter.addItem(recyclerItem);
    }

}
