package com.hanshin.ncs_travled;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;


public class HT_Result extends Activity {
    Button closeBtn;
    TextView titleTv;
    GridView books_gv;
    ImageView img ;
    HT_GridViewAdapter adapter;

    //구글로그인 회원정보
    String loginName = "-";
    String loginEmail = "-";

    //지역, 도시, 제목
    String area;
    String city;
    String title;

    //파이어베이스
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    BT_Create_Item item = new BT_Create_Item();

    //이미지 파일+ 비디오 파일
    ArrayList<Uri> imageList = new ArrayList<Uri>();
    ArrayList<Uri> videoList = new ArrayList<Uri>();

    //이미지 내용 + 비디오 내용
    ArrayList<String> contents;
    ArrayList<String> contents2;

    ArrayList<String> fileName = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ht_result);

        imageList.clear();

        //로그인한 회원정보를 가져오는 변수
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            //회원정보 이름
            loginName = signInAccount.getDisplayName();
            //회원정보 이메일
            loginEmail = signInAccount.getEmail();
        }

        Intent getIntent = getIntent();

        area = getIntent.getStringExtra("nameOfArea");
        city = getIntent.getStringExtra("nameOfCity");
        title = getIntent.getStringExtra("nameOfTitle");
        contents = getIntent.getStringArrayListExtra("nameOfContents1");
        contents2 = getIntent.getStringArrayListExtra("nameOfContents2");



        //창닫기 버튼을 클릭할때
        closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleTv = findViewById(R.id.TitleName_tv);
        titleTv.setText(city + " - " + title);

        //그리드뷰
        books_gv = findViewById(R.id.books_gv);



       // 이미지 + 비디오 파일 다운로드
        StorageReference storageReference = storage.getReference();
        StorageReference storageRef = storageReference.child((loginEmail+"/"+area + "/" + city + "/" + title ));
        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if(uri.toString().contains("png") || uri.toString().contains("image")){
                                imageList.add(uri);
                            }
                            if(uri.toString().contains("mp4") || uri.toString().contains("video")){
                                videoList.add(uri);
                            }
                            listAdd();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HT_Result.this, "데이터 다운로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });



//        StorageReference storageReference = storage.getReference();
//        StorageReference imageRef = storageReference.child("경로명");
//        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                imageList.add(uri);
//                Glide.with(getApplicationContext()).load(imageList.get(0)).into(img);
//                Toast.makeText(HT_Result.this, "데이터 다운로드 성공", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(HT_Result.this, "데이터 다운로드 실패", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        });




    }
    //그리드뷰에 데이터 출력
    private void listAdd() {
        adapter = new HT_GridViewAdapter(this,imageList,videoList);
        books_gv.setAdapter(adapter);

        adapter.add(imageList, videoList);
        adapter.notifyDataSetChanged();
    }

}

