package com.hanshin.ncs_travled;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class CT_Activity extends Activity {

    CT_Adapter listAdapter;
    RecyclerView recyclerView;
    CT_recyclerAdapter recyclerAdapter;
    ListView listview;
    Button writeBtn;
    //구글로그인 회원정보
    String loginName ="";
    String loginEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct_list);

        Button HomeBtn = findViewById(R.id.HomeBtn);
        Button BookBtn = findViewById(R.id.BookBtn);
        Button CommunityBtn = findViewById(R.id.CommunityBtn);

        HomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HT_Activity.class);
                startActivity(intent);
            }
        });
        BookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BT_Activity.class);
                startActivity(intent);
            }
        });
        CommunityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CT_Activity.class);
                startActivity(intent);
            }
        });

        //로그인한 회원정보를 가져오는 변수
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            //회원정보 이름
            loginName = signInAccount.getDisplayName();
            //회원정보 이메일
            loginEmail = signInAccount.getEmail();
            Toast.makeText(CT_Activity.this, loginName+" "+loginEmail, Toast.LENGTH_SHORT).show();
        }

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

        recyclerView.addItemDecoration(new CT_recyclerDecoration(30)); // 아이템 간격

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
