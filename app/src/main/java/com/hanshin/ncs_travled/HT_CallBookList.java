package com.hanshin.ncs_travled;

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
import com.google.android.gms.tasks.OnCompleteListener;
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

    ArrayList<String> title;

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
            Toast.makeText(
                    HT_CallBookList.this, loginName+" "+loginEmail, Toast.LENGTH_SHORT).show();
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
//        db.collection("loginEmail").document("area").collection("city").document("수현북")
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                DocumentSnapshot document = task.getResult();
//                BT_Create_Item item = document.toObject(BT_Create_Item.class);
//
//                areaTv.setText(item.getTitle());
//            }
//        });

        //리스트를 뿌려
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage1), "Book1", "수원", "AAA", "2020/03/15");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage2), "Book2", "서울", "BBB", "2020/02/21");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage3), "Book3", "고양", "CCC", "2020/01/04");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage4), "Book4", "광명", "DDD", "2019/12/23");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage1), "Book5", "수원", "AAA", "2020/03/15");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage2), "Book6", "서울", "BBB", "2020/02/21");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage3), "Book7", "고양", "CCC", "2020/01/04");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage4), "Book8", "광명", "DDD", "2019/12/23");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage1), "Book9", "수원", "AAA", "2020/03/15");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage2), "Book10", "서울", "BBB", "2020/02/21");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage3), "Book11", "고양", "CCC", "2020/01/04");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.bookcoverimage4), "Book12", "광명", "DDD", "2019/12/23");

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
