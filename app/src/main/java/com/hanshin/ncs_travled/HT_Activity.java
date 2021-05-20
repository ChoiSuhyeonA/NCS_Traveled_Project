package com.hanshin.ncs_travled;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HT_Activity extends Activity {
    //레이아웃버
    ConstraintLayout const1,const2,const3,const4,const5,const6;
    //지역버튼
    Button selArea[] = new Button[6] ;
    //도시버튼
    Button selBooklist[] = new Button[44];
    //설정버튼
    ImageButton settingBtn[] = new ImageButton[6];
    ListView listview;
    HT_ListViewAdapter adapter;

    public static final int sub = 1000; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/
    public static final int sub1 = 1001; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/
    //구글로그인 회원정보
    String loginName ="-";
    String loginEmail = "-";

    //현재 지역 선
    String area="서울,경기";

    //파이어베이스
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    BT_Create_Item item  = new BT_Create_Item();

    int cc =0;
    final BackgroundThreads2 thread = new BackgroundThreads2();

    static  ArrayList<String> areaList = new ArrayList<String>();
    static  ArrayList<String> cityList = new ArrayList<String>();
    static ArrayList<String> title = new ArrayList<String>();
    static ArrayList<String> cover = new ArrayList<String>();
    static ArrayList<String> member = new ArrayList<String>();
    static ArrayList<String> date = new ArrayList<String>();
    static ArrayList<String> date2 = new ArrayList<String>();
    //이미지 컨텐츠
    static ArrayList<ArrayList<String>> contents1 = new ArrayList<ArrayList<String>>();
    //비디오 컨텐츠
    static ArrayList<ArrayList<String>> contents2= new ArrayList<ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ht_main);

        areaList.clear();
        cityList.clear();
        title.clear();
        cover.clear();
        member.clear();
        date.clear();
        date2.clear();
        contents1.clear();
        contents2.clear();

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
//            Toast.makeText(HT_Activity.this, loginName+" "+loginEmail, Toast.LENGTH_SHORT).show();
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
                    intent.putExtra("nameOfArea",area);
                    intent.putExtra("nameOfCity",selBooklist[num].getText());
                    startActivityForResult(intent, sub);
//                    Toast.makeText(getApplicationContext(), selBooklist[num].getText(), Toast.LENGTH_SHORT).show();
                }
            });
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
                                    area="서울,경기";

                                    dataInitialization();
                                    firebaseData();
                                    break;
                                case "인천":
                                    const1.setVisibility(View.GONE);
                                    const2.setVisibility(View.VISIBLE);
                                    const3.setVisibility(View.GONE);
                                    const4.setVisibility(View.GONE);
                                    const5.setVisibility(View.GONE);
                                    const6.setVisibility(View.GONE);
                                    area="인천";
                                    dataInitialization();
                                    firebaseData();
                                    break;
                                case "부산":
                                    const1.setVisibility(View.GONE);
                                    const2.setVisibility(View.GONE);
                                    const3.setVisibility(View.VISIBLE);
                                    const4.setVisibility(View.GONE);
                                    const5.setVisibility(View.GONE);
                                    const6.setVisibility(View.GONE);
                                    area="부산";
                                    dataInitialization();
                                    firebaseData();
                                    break;
                                case "대전":
                                    const1.setVisibility(View.GONE);
                                    const2.setVisibility(View.GONE);
                                    const3.setVisibility(View.GONE);
                                    const4.setVisibility(View.VISIBLE);
                                    const5.setVisibility(View.GONE);
                                    const6.setVisibility(View.GONE);
                                    area="대전";
                                    dataInitialization();
                                    firebaseData();
                                    break;
                                case "대구":
                                    const1.setVisibility(View.GONE);
                                    const2.setVisibility(View.GONE);
                                    const3.setVisibility(View.GONE);
                                    const4.setVisibility(View.GONE);
                                    const5.setVisibility(View.VISIBLE);
                                    const6.setVisibility(View.GONE);
                                    area="대구";
                                    dataInitialization();
                                    firebaseData();
                                    break;
                                case "광주":
                                    const1.setVisibility(View.GONE);
                                    const2.setVisibility(View.GONE);
                                    const3.setVisibility(View.GONE);
                                    const4.setVisibility(View.GONE);
                                    const5.setVisibility(View.GONE);
                                    const6.setVisibility(View.VISIBLE);
                                    area="광주";
                                    dataInitialization();
                                    firebaseData();
                                    break;
                            }
                        }
                    });
                }
            });
        }
        //파이어베이스 데이타 가져오기
        firebaseData();
    }

    //데이타 초기화
    private void dataInitialization() {
        //리스트뷰 초기화
        adapter = new HT_ListViewAdapter();
        listview = findViewById(R.id.listview1);
        listview.setAdapter(adapter);
        //포토북 데이타 초기화
        areaList.clear();
        cityList.clear();
        title.clear();
        cover.clear();
        member.clear();
        date.clear();
        date2.clear();
        contents1.clear();
        contents2.clear();
    }

    private void firebaseData() {
        //지역에 등록된 모든 포토북 이름정보 찾아오기
        db.collection(loginEmail).document("info").collection(area).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    //컬렉션 아래에 있는 모든 정보를 가져온다.

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        item = document.toObject(BT_Create_Item.class);
                        //필드의 모든포토북 데이타를 가져와서 ArrayList에 저장
                        cityList.add(item.getCity());
                        title.add(item.getTitle());
                        cc++;
                    }
                    thread.run();

                    if(cityList.size()>0){
                        dataCollect();
                    }

                }
                else{
                    Toast.makeText(HT_Activity.this, "로딩실패", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HT_Activity.this, "로딩실패", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void dataCollect() {
        final BackgroundThreads2 thread = new BackgroundThreads2();


        for(int i=0; i<cityList.size(); i++){
            db.collection(loginEmail).document(area).collection(cityList.get(i)).document(title.get(i)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    item = document.toObject(BT_Create_Item.class);
                    //포토북의 내용들을 리스트에 저장
                    cover.add(item.getCover());
                    date.add(item.getDate());
                    date2.add(item.getDate2());
                    member.add(item.getMember());
                    contents1.add(item.getContents());
                    contents2.add(item.getContents2());
                    //실행순서 제어하기
                    dataAdd();
                }
            });
            thread.run();
        }

    }

    //하단 리스트뷰에 데이터 추가하기
    private void dataAdd() {
        //////// 하단 포토북
        adapter = new HT_ListViewAdapter();
        listview = findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        Toast.makeText(HT_Activity.this, area+":"+" 포토북 개수"+ String.valueOf(cover.size()), Toast.LENGTH_SHORT).show();

        for(int i=0; i<cover.size(); i++){
            if(cover.get(i).equals("1"))
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage1), title.get(i), cityList.get(i), member.get(i), date.get(i)+" ~ "+ date2.get(i));
            if(cover.get(i).equals("2"))
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage2), title.get(i), cityList.get(i), member.get(i), date.get(i)+" ~ "+ date2.get(i));
            if(cover.get(i).equals("3"))
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage3), title.get(i), cityList.get(i), member.get(i), date.get(i)+" ~ "+ date2.get(i));
            if(cover.get(i).equals("4"))
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage4), title.get(i), cityList.get(i), member.get(i), date.get(i)+" ~ "+ date2.get(i));
            if(cover.get(i).equals("5"))
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage5), title.get(i), cityList.get(i), member.get(i), date.get(i)+" ~ "+ date2.get(i));
            if(cover.get(i).equals("6"))
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage6), title.get(i), cityList.get(i), member.get(i), date.get(i)+" ~ "+ date2.get(i));
            if(cover.get(i).equals("7"))
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage7), title.get(i), cityList.get(i), member.get(i), date.get(i)+" ~ "+ date2.get(i));
            if(cover.get(i).equals("8"))
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage8), title.get(i), cityList.get(i), member.get(i), date.get(i)+" ~ "+ date2.get(i));
        }
        adapter.notifyDataSetChanged();


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), HT_Result.class);
                intent.putExtra("nameOfArea",area);
                intent.putExtra("nameOfCity",cityList.get(position));
                intent.putExtra("nameOfTitle",title.get(position));
                intent.putExtra("nameOfContents1",contents1.get(position));
                intent.putExtra("nameOfContents2",contents2.get(position));

                startActivityForResult(intent, sub);


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
//대기 시간을 할 수 있도록 하는 클래스
class BackgroundThreads2 extends  Thread{
    public  void run(){
        try{
            Thread.sleep(100);
        }catch (Exception e){
        }
    }
}