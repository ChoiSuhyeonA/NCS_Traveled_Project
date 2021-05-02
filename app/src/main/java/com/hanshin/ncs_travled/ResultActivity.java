package com.hanshin.ncs_travled;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ResultActivity extends AppCompatActivity {
    private FirebaseAuth mAuth ;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG =ResultActivity.class.getSimpleName();

    TextView name, mail;
    Button logout, revoke, goHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        logout = findViewById(R.id.logout);
        revoke = findViewById(R.id.revoke);
        goHome = findViewById(R.id.goHome);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);

        mAuth = FirebaseAuth.getInstance();

        //로그인한 회원정보를 가져오는 변수
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            //회원정보 이름
            name.setText(signInAccount.getDisplayName());
            //회원정보 이메일
            mail.setText(signInAccount.getEmail());
        }
        //로그아웃 하기
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show();

                Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        //탈퇴하기
        revoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //경고창 보여주기
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(ResultActivity.this);
                alt_bld.setMessage("회원탈퇴 하시겠습니까?").setCancelable(false).setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 네 클릭식 탈퇴하기
                                Toast.makeText(getApplicationContext(), "회원탈퇴 성공", Toast.LENGTH_SHORT).show();
                                mAuth.getCurrentUser().delete();
                                Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 아니오 클릭. dialog 닫기.

                        dialog.cancel();
                    }
                });
                AlertDialog alert = alt_bld.create();
                alert.setTitle("회원탈퇴");
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255,62,79,92)));
                alert.show();



            }
        });
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}