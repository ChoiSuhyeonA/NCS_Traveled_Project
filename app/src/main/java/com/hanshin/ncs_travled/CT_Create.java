package com.hanshin.ncs_travled;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.hanshin.ncs_travled.CT_Activity.listAdapter;

public class CT_Create extends Activity {
    Button ct_close_btn, ct_SaveBtn;
    ImageButton ct_createImage;
    EditText ct_createTitle,ct_createDate, ct_createContent;

    //선택한 갤러리 이미지
    Uri image;

    //구글로그인 회원정보 및 데이타
    String loginName ="";
    String loginEmail = "";
    String pageNumber="";


    static CT_Create_Item ct_item = new CT_Create_Item();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct_create);

        //로그인한 회원정보를 가져오는 변수
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            //회원정보 이름
            loginName = signInAccount.getDisplayName();
            //회원정보 이메일
            loginEmail = signInAccount.getEmail();
        }

        ct_close_btn= findViewById(R.id.ct_close_btn);
        ct_SaveBtn = findViewById(R.id.ct_SaveBtn);
        ct_createImage = findViewById(R.id.ct_createImage);
        ct_createContent = findViewById(R.id.ct_createContent);
        ct_createTitle = findViewById(R.id.ct_createTitle);
        ct_createDate = findViewById(R.id.ct_createDate);

        //업로드할때 날짜를 폴더명뒤에 지정해서, 파일을 분류
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss");
        Date now = new Date();
        final String Datename = formatter.format(now);
        //커뮤니티 게시글을 분류
        pageNumber = loginEmail+"|"+Datename;

        //갤러리 선택 버튼 클릭시.
        ct_createImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //갤러리 이동해서 이미지 선택할 수 있게 클릭
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/* ");
                startActivityForResult(intent, 1000);
            }
        });


        //닫기 버튼 클릭했을 경우
        ct_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CT_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        //저장 버튼 클릭했을 경우
        ct_SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //제목 + 내용 정보를 UI를 통해서 가져온다.
                if(image==null){
                    Toast.makeText(CT_Create.this, "이미지를 선택해주세요", Toast.LENGTH_SHORT).show();
                }else if(String.valueOf(ct_createTitle.getText()).equals("")){
                    Toast.makeText(CT_Create.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(String.valueOf(ct_createContent.getText()).equals("")){
                    Toast.makeText(CT_Create.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(String.valueOf(ct_createDate.getText()).equals("")){
                    Toast.makeText(CT_Create.this, "날짜를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                
                else{
                    ct_item.setTitle(String.valueOf(ct_createTitle.getText()));
                    ct_item.setContents(String.valueOf(ct_createContent.getText()));
                    ct_item.setDate(String.valueOf(ct_createDate.getText()));
                    ct_item.setEmail(loginEmail);
                    ct_item.setName(loginName);
                    ct_item.setPageNumber(pageNumber);
                    ct_item.setRealDate(Datename);
                    dataUpload();
                }

            }
        });
    }

    private void dataUpload() {


        Map<String, Object> community = new HashMap<>();
        community.put("email", ct_item.getEmail());
        community.put("name", ct_item.getName());
        community.put("date", ct_item.getDate());
        community.put("title", ct_item.getTitle());
        community.put("contents", ct_item.getContents());
        community.put("pageNumber", ct_item.getPageNumber());
        community.put("realDate", ct_item.getRealDate());

        //파이어베이스 스토어 업로드 (데이터)
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // 파이어스토어 ( 이메일명/ 지역 / 도시 /포토북명으로 데이터 분류)
        db.collection("community").document().set(community).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listAdapter.notifyDataSetChanged();
                Toast.makeText(CT_Create.this, "데이터 업로드 성공", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CT_Create.this, "데이터 업로드 실패", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });


        //파이어스토리지 업로드
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        //이미지 리스트를 파이어베이스에 업로드

          StorageReference imageRef = storageRef.child("community" +"/" + loginEmail + "/" + pageNumber ); //파이어베이스에 업로드할 이미지 이름 지정
            UploadTask uploadTask = imageRef.putFile(image);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    listAdapter.notifyDataSetChanged();
                    //업로드 성공할시 데이타 초기화
                    ct_item = new CT_Create_Item();
                    Intent intent = new Intent(getApplicationContext(), CT_Activity.class);
                    startActivity(intent);
                    finish();
                }
            });


    }

    //갤러리 생성하기 필요한 메서드
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            image = data.getData();
            ct_createImage.setImageURI(image);
        }
    }
}
