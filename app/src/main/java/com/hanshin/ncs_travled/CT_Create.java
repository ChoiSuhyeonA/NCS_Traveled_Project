package com.hanshin.ncs_travled;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class CT_Create extends Activity {
    Button ct_close_btn, ct_SaveBtn;
    ImageButton ct_createImage;
    EditText ct_createTitle,ct_createDate, ct_createContent;

    //구글로그인 회원정보
    String loginName ="";
    String loginEmail = "";

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

        String title;
        String contents;
        String date;
        //제목 + 내용 정보를 UI를 통해서 가져온다.
        title = ct_createTitle.getText().toString();
        contents = ct_createContent.getText().toString();
        date = ct_createDate.getText().toString();


        //갤러리 선택 버튼 클릭시.
        ct_createImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //갤러리 이동해서 이미지 선택할 수 있게 클릭
            }
        });


        //닫기 버튼 클릭했을 경우
        ct_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //저장 버튼 클릭했을 경우
        ct_SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //파이어베이스 데이터 + 이미지 업로드하기

                Intent intent = new Intent(getApplicationContext(), CT_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
