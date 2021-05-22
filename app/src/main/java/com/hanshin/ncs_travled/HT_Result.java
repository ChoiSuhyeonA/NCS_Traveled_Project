package com.hanshin.ncs_travled;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

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
    
    //대화상자에 보여줄 위젯
    ImageView img ;
    VideoView video;
    EditText text, text2;
    
    HT_GridViewAdapter adapter;

    MediaController controller;
    //구글로그인 회원정보
    String loginName = "-";
    String loginEmail = "-";

    //지역, 도시, 제목
    String area;
    String city;
    String title;

    //계산을 하기 위한 변수
    int a1;
    int a2;

    //파이어베이스
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    BT_Create_Item item = new BT_Create_Item();

    //이미지 파일+ 비디오 파일
    ArrayList<Uri> imageList = new ArrayList<Uri>();
    ArrayList<Uri> videoList = new ArrayList<Uri>();
    ArrayList<Uri> seeList = new ArrayList<Uri>();

    //이미지 내용 + 비디오 내용
    ArrayList<String> contents;
    ArrayList<String> contents2;

    ArrayList<String> fileName = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ht_result);

        if(videoList!=null){
            videoList.clear();
        }
        imageList.clear();
        seeList.clear();

        //동영상 재생할 수 있는 컨트롤러
        controller = new MediaController(this);

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

        final BackgroundThread thread = new BackgroundThread();

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
                                seeList.add(uri);
                            }
                            if(uri.toString().contains("mp4") || uri.toString().contains("video")){
                                videoList.add(uri);
                                seeList.add(uri);
                            }
                            listAdd();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HT_Result.this, "데이터 다운로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                    thread.run();
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
        adapter = new HT_GridViewAdapter(this,imageList,videoList, seeList);
        books_gv.setAdapter(adapter);

        adapter.add(imageList, videoList, seeList);
        adapter.notifyDataSetChanged();

        books_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                //이미지 대화상자
                final View dialogView = (LinearLayout) View.inflate(com.hanshin.ncs_travled.HT_Result.this, R.layout.ht_dialog_list, null);
                //비디오 대화상자
                final View dialogView2 = (LinearLayout) View.inflate(com.hanshin.ncs_travled.HT_Result.this, R.layout.ht_dialog_list2, null);

                AlertDialog.Builder dlg = new AlertDialog.Builder(com.hanshin.ncs_travled.HT_Result.this);
                //이미지 대화상자
                img = dialogView.findViewById(R.id.ht_dialog_image);
                text = dialogView.findViewById(R.id.ResultlistContent);
                //비디오 대화상자
                video = dialogView2.findViewById(R.id.ht_dialog_video);
                text2  = dialogView2.findViewById(R.id.ResultlistContent2);

                if(seeList.get(position).toString().contains("png") || seeList.get(position).toString().contains("jpeg")|| seeList.get(position).toString().contains("image")){
                    a2 = 0;
                    for (a1 = 0; a1 < position; a1++) {
                        if (seeList.get(a1).toString().contains("mp4") || seeList.get(a1).toString().contains("video")) {
                            a2++;
                        }
                    }
                    Glide.with(getApplicationContext()).load(seeList.get(position)).into(img);
                    img.setScaleType(ImageView.ScaleType.FIT_XY);
                    text.setText(contents.get(position-a2));

                    dlg.setView(dialogView);
                }else if(seeList.get(position).toString().contains("mp4") || seeList.get(position).toString().contains("video")){
                    a2 = 0;
                    for (a1 = 0; a1 < position; a1++) {
                        if (seeList.get(a1).toString().contains("png") || seeList.get(a1).toString().contains("jpeg")|| seeList.get(a1).toString().contains("image")) {
                            a2++;
                        }
                    }
                    controller.setAnchorView(video);
                    video.setMediaController(controller); //미디어 제어 버튼부 세팅
                    video.setVideoURI(seeList.get(position));
                    video.start();
                    text2.setText(contents2.get(position-a2));
                    
                    dlg.setView(dialogView2);
                }

                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
    }


}

//대기 시간을 할 수 있도록 하는 클래스
class BackgroundThreads extends  Thread{
    public  void run(){
        try{
            Thread.sleep(300);
        }catch (Exception e){
        }
    }
}
