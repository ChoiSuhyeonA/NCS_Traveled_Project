package com.hanshin.ncs_travled;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class HT_CallBookList extends Activity {

    Button closeBtn;
    HT_ListViewAdapter adapter;
    ListView books_lv;
    TextView areaTv;

    //구글로그인 회원정보
    String loginName ="-";
    String loginEmail = "-";

    //지역, 도시
    String area;
    String city;

    //파이어베이스
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    BT_Create_Item item  = new BT_Create_Item();

    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> cover = new ArrayList<String>();
    ArrayList<String> member = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> date2 = new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ht_call_booklist);

        //로그인한 회원정보를 가져오는 변수
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            //회원정보 이름
            loginName = signInAccount.getDisplayName();
            //회원정보 이메일
            loginEmail = signInAccount.getEmail();
        }

        closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent getIntent = getIntent();

        area = getIntent.getStringExtra("nameOfArea");
        city = getIntent.getStringExtra("nameOfCity");

        switch (city){
            case "성남\n용인":
                city = "성남,용인";
                break;
            case "부천 / 광명":
                city = "부천,광명";
                break;
            case "안산 / 안양":
                city = "안산,안양";
                break;
            case "서구\n동구":
                city = "서구,동구";
                break;
            case "계양\n부평":
                city = "계양,부평";
                break;
            case "북구 / 금정":
                city = "북구,금정";
                break;
            case "남포동 / 서면":
                city = "남포동,서면";
                break;
            case "사하구 / 영도 / 남구":
                city = "사하구,영도,남구";
                break;
            case "둔산\n서구":
                city = "둔산,서구";
                break;
            case "대홍\n중구":
                city = "대홍,중구";
                break;
            case "유성구 / 궁동":
                city = "유성구,궁동";
                break;
            case "수성구\n수성못":
                city = "수성구,수성못";
                break;
            case "서구-북구":
                city = "서구,북구";
                break;
            case "중구 / 동성로":
                city = "중구,동성로";
                break;
            case "이월드 / 남구":
                city = "이월드,남구";
                break;
            case "서구\n상무":
                city = "서구,상무";
                break;
            case "북구 / 전남대":
                city = "북구,전남대";
                break;
            case "충장로 / 동명":
                city = "충장로,동명";
                break;

        }
        city.replaceAll("", " ");
        areaTv = findViewById(R.id.areaName_tv);
        areaTv.setText(area+" - "+city);

        adapter = new HT_ListViewAdapter();
        books_lv = findViewById(R.id.books_lv);
        books_lv.setAdapter(adapter);


//        //파이어베이스 데이터 정보가져오기 오류발생
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection(loginEmail).document(area).collection(city).document("수현북")
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                DocumentSnapshot document = task.getResult();
//                BT_Create_Item item = document.toObject(BT_Create_Item.class);
//
//                areaTv.setText(item.getTitle());
//            }
//        });

        //지역에 등록된 모든 포토북 이름정보 찾아오기
        db.collection(loginEmail).document(area).collection(city).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    //컬렉션 아래에 있는 모든 정보를 가져온다.

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //document.getData() or document.getId() 등등 여러 방법으로
                        //데이터를 가져올 수 있다.
                          item = document.toObject(BT_Create_Item.class);
                          //필드의 포토북 제목명을 가져와서 ArrayList에 저장
                          title.add(item.getTitle());
                    }
                    //필드에 포토북이 몇개 있는지 확인해서 출력.
                    Toast.makeText(HT_CallBookList.this, "포토북 개수:" +String.valueOf(title.size()), Toast.LENGTH_SHORT).show();
                    //파이어베이스에 저장된 포토북 제목을 모두 출력
                    for(int i=0; i<title.size(); i++){
                        Toast.makeText(HT_CallBookList.this, " 포토북 제목:"+ title.get(i), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(HT_CallBookList.this, "로딩실패", Toast.LENGTH_SHORT).show();
                }
            }
         //에러가 발생됐을 경우.
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(HT_CallBookList.this, "데이터 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        //각 포토북의 정보를 Arraylist에 저장.
        for(int i=0; i<title.size(); i++){
            db.collection(loginEmail).document(area).collection(city).document(title.get(i)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    item = document.toObject(BT_Create_Item.class);
                    //포토북의 내용들을 리스트에 저장
                    cover.add(item.getCover());
                    date.add(item.getDate());
                    date2.add(item.getDate2());
                    member.add(item.getMember());
                }
            });
        }





        //리스트를 뿌려줌
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage1), "book_test", "수원", "AAA", "2020/03/15");

        books_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
