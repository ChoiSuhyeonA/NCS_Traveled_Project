package com.hanshin.ncs_travled;

import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.tabs.TabLayout;

public class HT_Activity extends Activity {

    Button mapSelAreaBtn, mapGoyangnBtn, mapBuGwangBtn, mapSeoulBtn, mapAnAnBtn, mapSuwonBtn, mapSuYoBtn;
    ListView listview;
    HT_ListViewAdapter adapter;
    TabLayout tabLayout;
    public static final int sub = 1001; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/
    //구글로그인 회원정보
    String loginName ="";
    String loginEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ht_main);

        Button HomeBtn = findViewById(R.id.HomeBtn);
        Button BookBtn = findViewById(R.id.BookBtn);
        Button CommunityBtn = findViewById(R.id.CommunityBtn);
        //탭 화면전환 버튼
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
            Toast.makeText(HT_Activity.this, loginName+" "+loginEmail, Toast.LENGTH_SHORT).show();
        }


        mapSelAreaBtn = findViewById(R.id.homeMap_selAreaBtn);
        mapGoyangnBtn = findViewById(R.id.homeMap_goyangBtn);
        mapBuGwangBtn = findViewById(R.id.homeMap_bugwangBtn);
        mapSeoulBtn = findViewById(R.id.homeMap_seoulBtn);
        mapAnAnBtn = findViewById(R.id.homeMap_ananBtn);
        mapSuwonBtn = findViewById(R.id.homeMap_suwonBtn);
        mapSuYoBtn = findViewById(R.id.homeMap_suyoBtn);

        mapSelAreaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                AreaDialog areaDialog = new AreaDialog(HT_Activity.this);
                areaDialog.callFunction();
            }
        });

        mapGoyangnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "버튼테스트", Toast.LENGTH_SHORT).show();
            }
        });
        mapBuGwangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mapSeoulBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mapAnAnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mapSuwonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mapSuYoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //////// 하단 포토북

        adapter = new HT_ListViewAdapter();

        listview = findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.cover_spring), "Book1", "수원", "AAA", "2020/03/15");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.cover_autumn), "Book2", "서울", "BBB", "2020/02/21");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.cover_summer), "Book3", "고양", "CCC", "2020/01/04");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.cover_winter), "Book4", "광명", "DDD", "2019/12/23");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                HT_Listview_Item item = (HT_Listview_Item) parent.getItemAtPosition(position);
                String titleStr = item.getTitle();
                String placeStr = item.getPlace();
                String memberStr = item.getMember();
                String dateStr = item.getDate();
                Drawable coverDrawable = item.getCover();
                // TODO : use item data.
            }
        });
    }


}