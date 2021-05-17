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

public class CT_Create extends Activity {
    Button ct_close_btn, ct_SaveBtn;
    ImageButton ct_createImage;
    EditText ct_createTitle,ct_createContent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct_create);

        ct_close_btn= findViewById(R.id.ct_close_btn);
        ct_SaveBtn = findViewById(R.id.ct_SaveBtn);
        ct_createImage = findViewById(R.id.ct_createImage);
        ct_createContent = findViewById(R.id.ct_createContent);
        ct_createTitle = findViewById(R.id.ct_createTitle);

        String title;
        String contents;
        //제목 + 내용 정보를 UI를 통해서 가져온다.
        title = ct_createTitle.getText().toString();
        contents = ct_createContent.getText().toString();

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
