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

import androidx.core.content.ContextCompat;

public class HT_CallBookList extends Activity {

    Button closeBtn;
    HT_ListViewAdapter adapter;
    ListView books_lv;
    TextView areaTv;

    //구글로그인 회원정보
    String loginName ="-";
    String loginEmail = "-";


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

        String area = getIntent.getStringExtra("nameOfArea");
        String city = getIntent.getStringExtra("nameOfCity");
        city.replaceAll("", " ");
        areaTv = findViewById(R.id.areaName_tv);
        areaTv.setText(area);

        adapter = new HT_ListViewAdapter();
        books_lv = findViewById(R.id.books_lv);
        books_lv.setAdapter(adapter);

        //                // 다운로드 테스트
//                FirebaseStorage storage = FirebaseStorage.getInstance();
//                StorageReference storageRef = storage.getReference();
//                String fileName = "suhyeon0";
//                File fileDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "area + "/" + city + "/" + title + "/" + Datename + "-image" + i");
//                final File downloadFile = new File(fileDir, fileName);
//
//               FirebaseStorage storage1 = FirebaseStorage.getInstance();
//                StorageReference storageReference = storage1.getReference();
//                StorageReference downloadRef = storageRef.child("060036bd-145e-4fd0-ae22-f577903a1744.png");
//
//                downloadRef.getFile(downloadFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                 public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                 Toast.makeText(BT_CreateActivity.this, "다운로드 성공", Toast.LENGTH_SHORT).show();
//                 Glide.with(BT_CreateActivity.this).load(new File(downloadFile.getAbsolutePath())).into(testimage); }
//                 }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(BT_CreateActivity.this, "다운로드 실패", Toast.LENGTH_SHORT).show();
//                }});

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
