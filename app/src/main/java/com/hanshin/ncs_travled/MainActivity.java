package com.hanshin.ncs_travled;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends Activity {

    Button mapSelAreaBtn, mapGoyangnBtn, mapBuGwangBtn, mapSeoulBtn, mapAnAnBtn, mapSuwonBtn, mapSuYoBtn;
    ListView listview;
    HT_ListViewAdapter adapter;
    TabLayout tabLayout;
    public static final int sub = 1001; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////// 상단 맵
        tabLayout = findViewById(R.id.Htabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // TODO : tab의 상태가 선택 상태로 변경

                int pos = tab.getPosition();
                if (pos == 0) { // 첫 번째 탭 선택.
                    Toast.makeText(getApplicationContext(), "탭1", Toast.LENGTH_SHORT).show();
                } else if (pos == 1) { // 두 번째 탭 선택.
                    Intent intent = new Intent(getApplicationContext(), BT_CreateActivity.class);
                    startActivityForResult(intent, sub);
                } else if (pos == 2) { // 세 번째 탭 선택.
                    Intent intent = new Intent(getApplicationContext(),CT_Activity.class);
                    startActivityForResult(intent,sub);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

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
                AreaDialog areaDialog = new AreaDialog(MainActivity.this);
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