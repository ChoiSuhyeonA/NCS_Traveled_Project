package com.hanshin.ncs_travled;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.tabs.TabLayout;

public class HT_Activity extends Activity {
    ConstraintLayout const1,const2,const3,const4,const5,const6;
    Button selArea[] = new Button[6] ;
    Button selBooklist[] = new Button[44];

    ImageButton settingBtn[] = new ImageButton[6];
    ListView listview;
    HT_ListViewAdapter adapter;
    TabLayout tabLayout;
    public static final int sub = 1001; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/
    //구글로그인 회원정보
    String loginName ="-";
    String loginEmail = "-";
    String areaPush;

    String aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ht_main);

        Button HomeBtn = findViewById(R.id.HomeBtn);
        Button BookBtn = findViewById(R.id.BookBtn);
        Button CommunityBtn = findViewById(R.id.CommunityBtn);

        loginName ="-";
        loginEmail = "-";

        //탭 화면전환 버튼
        HomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HT_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        BookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BT_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        CommunityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CT_Activity.class);
                startActivity(intent);
                finish();
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

        const1 = findViewById(R.id.const1);
        const2 = findViewById(R.id.const2);
        const3 = findViewById(R.id.const3);
        const4 = findViewById(R.id.const4);
        const5 = findViewById(R.id.const5);
        const6 = findViewById(R.id.const6);

        selArea[0] = findViewById(R.id.homeMap_const1_selAreaBtn);
        selArea[1] = findViewById(R.id.homeMap_const2_selAreaBtn);
        selArea[2] = findViewById(R.id.homeMap_const3_selAreaBtn);
        selArea[3] = findViewById(R.id.homeMap_const4_selAreaBtn);
        selArea[4] = findViewById(R.id.homeMap_const5_selAreaBtn);
        selArea[5] = findViewById(R.id.homeMap_const6_selAreaBtn);

        for(int i=0;i<6;i++){ //지역선택 버튼 리스너 등록
            selArea[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                    AreaDialog areaDialog = new AreaDialog(HT_Activity.this);
                    areaDialog.callFunction();
                    areaDialog.setDialogListener(new AreaDialog.CustomDialogListener(){
                        @Override
                        public void onAreaClicked(String name) {
                            switch (name){
                                case "서울/경기":
                                    const1.setVisibility(View.VISIBLE);
                                    const2.setVisibility(View.GONE);
                                    const3.setVisibility(View.GONE);
                                    const4.setVisibility(View.GONE);
                                    const5.setVisibility(View.GONE);
                                    const6.setVisibility(View.GONE);
                                    aa = "서울,경기";

                                    break;
                                case "인천":
                                    const1.setVisibility(View.GONE);
                                    const2.setVisibility(View.VISIBLE);
                                    const3.setVisibility(View.GONE);
                                    const4.setVisibility(View.GONE);
                                    const5.setVisibility(View.GONE);
                                    const6.setVisibility(View.GONE);
                                    aa = "인천";
                                    break;
                                case "부산":
                                    const1.setVisibility(View.GONE);
                                    const2.setVisibility(View.GONE);
                                    const3.setVisibility(View.VISIBLE);
                                    const4.setVisibility(View.GONE);
                                    const5.setVisibility(View.GONE);
                                    const6.setVisibility(View.GONE);
                                    aa = "부산";
                                    break;
                                case "대전":
                                    const1.setVisibility(View.GONE);
                                    const2.setVisibility(View.GONE);
                                    const3.setVisibility(View.GONE);
                                    const4.setVisibility(View.VISIBLE);
                                    const5.setVisibility(View.GONE);
                                    const6.setVisibility(View.GONE);
                                    aa = "대전";
                                    break;
                                case "대구":
                                    const1.setVisibility(View.GONE);
                                    const2.setVisibility(View.GONE);
                                    const3.setVisibility(View.GONE);
                                    const4.setVisibility(View.GONE);
                                    const5.setVisibility(View.VISIBLE);
                                    const6.setVisibility(View.GONE);
                                    aa = "대구";
                                    break;
                                case "광주":
                                    const1.setVisibility(View.GONE);
                                    const2.setVisibility(View.GONE);
                                    const3.setVisibility(View.GONE);
                                    const4.setVisibility(View.GONE);
                                    const5.setVisibility(View.GONE);
                                    const6.setVisibility(View.VISIBLE);
                                    aa = "광주";
                                    break;
                            }
                        }
                    });
                }
            });
        }

        //각 버튼 위치
        selBooklist[0] = findViewById(R.id.homeMap_const1_ananBtn);
        selBooklist[1] = findViewById(R.id.homeMap_const1_bugwangBtn);
        selBooklist[2] = findViewById(R.id.homeMap_const1_goyangBtn);
        selBooklist[3] = findViewById(R.id.homeMap_const1_seoulBtn);
        selBooklist[4] = findViewById(R.id.homeMap_const1_suwonBtn);
        selBooklist[5] = findViewById(R.id.homeMap_const1_suyoBtn);
        selBooklist[6] = findViewById(R.id.homeMap_const2_gaebuBtn);
        selBooklist[7] = findViewById(R.id.homeMap_const2_gangBtn);
        selBooklist[8] = findViewById(R.id.homeMap_const2_jungnamBtn);
        selBooklist[9] = findViewById(R.id.homeMap_const2_seodongBtn);
        selBooklist[10] = findViewById(R.id.homeMap_const2_wungBtn);
        selBooklist[11] = findViewById(R.id.homeMap_const2_younnamBtn);
        selBooklist[12] = findViewById(R.id.homeMap_const3_bugumBtn);
        selBooklist[13] = findViewById(R.id.homeMap_const3_bujinBtn);
        selBooklist[14] = findViewById(R.id.homeMap_const3_cenBtn);
        selBooklist[15] = findViewById(R.id.homeMap_const3_haeBtn);
        selBooklist[16] = findViewById(R.id.homeMap_const3_namseoBtn);
        selBooklist[17] = findViewById(R.id.homeMap_const3_sayonaBtn2);
        selBooklist[18] = findViewById(R.id.homeMap_const3_seodongBtn);
        selBooklist[19] = findViewById(R.id.homeMap_const4_daeBtn);
        selBooklist[20] = findViewById(R.id.homeMap_const4_daejungBtn2);
        selBooklist[21] = findViewById(R.id.homeMap_const4_dongBtn);
        selBooklist[22] = findViewById(R.id.homeMap_const4_dunseoBtn);
        selBooklist[23] = findViewById(R.id.homeMap_const4_eunBtn);
        selBooklist[24] = findViewById(R.id.homeMap_const4_youguBtn);
        selBooklist[25] = findViewById(R.id.homeMap_const5_bugwangBtn);
        selBooklist[26] = findViewById(R.id.homeMap_const5_dalBtn);
        selBooklist[27] = findViewById(R.id.homeMap_const5_dalseoBtn);
        selBooklist[28] = findViewById(R.id.homeMap_const5_dongBtn);
        selBooklist[29] = findViewById(R.id.homeMap_const5_jundoBtn2);
        selBooklist[30] = findViewById(R.id.homeMap_const5_seobuBtn);
        selBooklist[31] = findViewById(R.id.homeMap_const5_susuBtn);
        selBooklist[32] = findViewById(R.id.homeMap_const6_bujunBtn);
        selBooklist[33] = findViewById(R.id.homeMap_const6_chudoBtn);
        selBooklist[34] = findViewById(R.id.homeMap_const6_dongBtn);
        selBooklist[35] = findViewById(R.id.homeMap_const6_gwangBtn);
        selBooklist[36] = findViewById(R.id.homeMap_const6_namBtn);
        selBooklist[37] = findViewById(R.id.homeMap_const6_seosaBtn);
        selBooklist[38] = findViewById(R.id.homeMap_const1_selAreaBtn);
        selBooklist[39] = findViewById(R.id.homeMap_const2_selAreaBtn);
        selBooklist[40] = findViewById(R.id.homeMap_const3_selAreaBtn);
        selBooklist[41] = findViewById(R.id.homeMap_const4_selAreaBtn);
        selBooklist[42] = findViewById(R.id.homeMap_const5_selAreaBtn);
        selBooklist[43] = findViewById(R.id.homeMap_const6_selAreaBtn);

        for(int i=0;i<selBooklist.length;i++){ // 각 지역별 포토북 리스트 불러오는 리스너 등록
            final int num = i;
            selBooklist[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), HT_CallBookList.class);
                    intent.putExtra("nameOfArea",aa);
                    intent.putExtra("nameOfCity",selBooklist[num].getText());
                    startActivityForResult(intent, sub);
                    Toast.makeText(getApplicationContext(), selBooklist[num].getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        //세팅버튼 등록
        settingBtn[0] = findViewById(R.id.settingBtn1);
        settingBtn[1] = findViewById(R.id.settingBtn2);
        settingBtn[2] = findViewById(R.id.settingBtn3);
        settingBtn[3] = findViewById(R.id.settingBtn4);
        settingBtn[4] = findViewById(R.id.settingBtn5);
        settingBtn[5] = findViewById(R.id.settingBtn6);

        //세팅 버튼 리스너
        for(int i=0;i<6;i++) { 
            settingBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //환경설정페이지로 이동
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intent);
                }
            });
        }    
      
        //////// 하단 포토북

        adapter = new HT_ListViewAdapter();

        listview = findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage1), "Book1", "수원", "AAA", "2020/03/15");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage3), "Book2", "서울", "BBB", "2020/02/21");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage2), "Book3", "고양", "CCC", "2020/01/04");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage4), "Book4", "광명", "DDD", "2019/12/23");

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