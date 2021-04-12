package com.hanshin.ncs_travled;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import static android.content.ContentValues.TAG;

public class BT_CreateActivity extends Activity {
    ArrayList<Uri> imageList = new ArrayList<Uri>();
    ArrayList<Uri> videoList = new ArrayList<Uri>();
    ArrayList<Uri> seeList = new ArrayList<Uri>();

    BT_GridViewAdapter adapter;

    TabLayout tabLayout;
    public static final int sub = 1001; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_create);

        tabLayout = findViewById(R.id.Htabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // TODO : tab의 상태가 선택 상태로 변경

                int pos = tab.getPosition();

                if (pos == 0) { // 첫 번째 탭 선택.
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivityForResult(intent, sub);
                } else if (pos == 1) { // 두 번째 탭 선택.
                    Toast.makeText(getApplicationContext(), "탭2", Toast.LENGTH_SHORT).show();
                } else if (pos == 2) { // 세 번째 탭 선택.
                    Toast.makeText(getApplicationContext(), "탭3", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        //메인페이지 버튼의정
        Button btnPhotoBookInfo = findViewById(R.id.btnPhotoBookInfo);
        Button btnPhotoBookCover = findViewById(R.id.btnPhotoBookCover);
        Button btnPhotoBookSave = findViewById(R.id.btnPhotoBookSave);
        Button btnPhotoBookPageCreate = findViewById(R.id.btnPhotoBookPageCreate);
        Button btnPhotoBookPageDelete = findViewById(R.id.btnPhotoBookPageDelete);
        final ImageView testimage = findViewById(R.id.testimage);

        //그리드뷰 + 어댑터
        GridView gridView = findViewById(R.id.gridview);
        adapter = new BT_GridViewAdapter(this, imageList, videoList);
        gridView.setAdapter(adapter);

        //포토북생성페이지에 정보버튼안에 대화상자 속성 정의
        LinearLayout dialogView;
        //포토북생성페이지에 정보 버튼을 클릭할 때 이벤트 작성
        btnPhotoBookInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout dialogView = (LinearLayout) View.inflate(BT_CreateActivity.this, R.layout.bt_dialog_photobookinfo, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(BT_CreateActivity.this);
                dlg.setTitle(" 포토북 정보");
                dlg.setView(dialogView);
                dlg.setIcon(R.drawable.ic_baseline_info_24);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();

            }
        });
        //포토북생성페이지에 표지 버튼을 클릭할 때 이벤트 작성
        btnPhotoBookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout dialogView = (LinearLayout) View.inflate(BT_CreateActivity.this, R.layout.bt_dialog_photobookcover, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(BT_CreateActivity.this);
                dlg.setTitle(" 포토북 표지");
                dlg.setView(dialogView);
                dlg.setIcon(R.drawable.ic_baseline_menu_book_24);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //확인 버튼 누를시 이벤트 작성하기


                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
        //포토북생성페이지에 페이지 추가 버튼을 클릭할 때 이벤트 작성
        btnPhotoBookPageCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BT_GridViewAdapter의 add메서드 실행

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/* video/*");
                startActivityForResult(intent, 1000);

                adapter.add(imageList, 1);
                adapter.add(videoList, 2);
                adapter.notifyDataSetChanged();
            }
        });
        //포토북생성페이지에 페이지 삭제 버튼을 클릭할 때 이벤트 작성
        btnPhotoBookPageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BT_GridViewAdapter의 delete메서드 실행
                adapter.delete();
                //삭제한 내용 보여주기
                adapter.notifyDataSetChanged();
            }
        });

        //포토북생성페이지에 저장 버튼을 클릭할 때 이벤트 작성
        btnPhotoBookSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                //업로드할때 날짜를 파일명앞에 지정해서, 파일을 분류
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss");
                Date now = new Date();
                String Datename = formatter.format(now);

                String city = "수원";
                String area = "경기도";
                String title = "book";
                //이미지 리스트를 파이어베이스에 업로드
                for (int i = 0; i < imageList.size(); i++) {
                    StorageReference imageRef = storageRef.child(area + "/" + city + "/" + title + "/" + Datename + "-image" + i); //파이어베이스에 업로드할 이미지 이름 지정
                    //  Uri file  = Uri.fromFile(new File("/sdcard/Android/data/com.hanshin.ncs_travled/files/Pictures/p.png")); // 파이어베이스 다운로드 경로 예시
                    //    Uri file  = Uri.fromFile(new File("/sdcard/Download/fashion.jpg")); //갤러리경로 예시

                    Uri file = Uri.parse(String.valueOf(imageList.get(i)));
                    UploadTask uploadTask = imageRef.putFile(file);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(BT_CreateActivity.this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(BT_CreateActivity.this, "이미지 업로드 성공", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //비디오 리스트를 파이어베이스에 업로드
                for (int i = 0; i < videoList.size(); i++) {
                    StorageReference videoRef = storageRef.child(area + "/" + city + "/" + title + "/" + Datename + "-video" + i); //파이어베이스에 업로드할 비디오 이름 지정
                    Uri file =Uri.parse(String.valueOf(videoList.get(i)));// 비디오리스트에서 내가 원하는 값을 집어넣음.
                    UploadTask uploadTask = videoRef.putFile(file);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(BT_CreateActivity.this, "업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(BT_CreateActivity.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
//                // 다운로드 테스트
//                String fileName = "suhyeon0";
//                File fileDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/suhyeon");
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
            }

        });
    }

    // Uri를 -> File로 데이터 형변환
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    //갤러리 생성하기 필요한 메서드
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Uri imagePath = data.getData();
            adapter.notifyDataSetChanged();
            if (imagePath.toString().contains("image")) {
                //갤러리에서 이미지 경로 받아와서 리스트에 추가하기
                imageList.add(imagePath);
            } else if (imagePath.toString().contains("video")) {
                //갤러리에서 비디오 경로 받아와서 리스트에 추가하기
                videoList.add(imagePath);
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

