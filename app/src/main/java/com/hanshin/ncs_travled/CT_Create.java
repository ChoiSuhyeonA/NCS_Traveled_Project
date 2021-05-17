package com.hanshin.ncs_travled;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CT_Create extends Activity {
    Button ct_close_btn, ct_SaveBtn;
    ImageButton ct_createImage;
    EditText ct_createContent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct_create);

        ct_close_btn= findViewById(R.id.ct_close_btn);
        ct_SaveBtn = findViewById(R.id.ct_SaveBtn);
        ct_createImage = findViewById(R.id.ct_createImage);
        ct_createContent = findViewById(R.id.ct_createContent);

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
                Intent intent = new Intent(getApplicationContext(), CT_Activity.class);
                startActivity(intent);
            }
        });
    }
}
