package com.hanshin.ncs_travled;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

public class BT_Activity extends Activity {
    ArrayList<Uri> imageList = new ArrayList<Uri>();
    ArrayList<Uri> videoList = new ArrayList<Uri>();
    //리스트에 출력될 자료 (실제 x)
    ArrayList<Uri> seeList = new ArrayList<Uri>();



    BT_GridViewAdapter adapter;
    TabLayout tabLayout;
    public static final int sub = 1001; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/
    static BT_Create_Item bt_item  = new BT_Create_Item(); ; //파이어베이스 스토어에 등록할 데이터 클래스

    //구글로그인 회원정보
    String loginName ="";
    String loginEmail = "";


    //리스트뷰에서 이미지 & 영상의 내용을 입력하기
    //이미지 컨텐츠
    static ArrayList<String> contents1 = new ArrayList<String>();
    //비디오 컨텐츠
    static ArrayList<String> contents2= new ArrayList<String>();
    //컨트츠에 출력될 자료 (실제 x)
    static ArrayList<String> contents= new ArrayList<String>();

    //계산을 하기 위한 변수
    int a1;
    int a2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_create);
        //컨테츠 ArrayList 초기화
        contents1.clear();
        contents2.clear();
        contents.clear();

        //로그인한 회원정보를 가져오는 변수
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            //회원정보 이름
            loginName = signInAccount.getDisplayName();
            //회원정보 이메일
            loginEmail = signInAccount.getEmail();

        }

        Button HomeBtn = findViewById(R.id.HomeBtn);
        Button BookBtn = findViewById(R.id.BookBtn);
        Button CommunityBtn = findViewById(R.id.CommunityBtn);

        HomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HT_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        BookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BT_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        CommunityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CT_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        //메인페이지 버튼의정보
        Button btnPhotoBookInfo = findViewById(R.id.btnPhotoBookInfo);
        Button btnPhotoBookCover = findViewById(R.id.btnPhotoBookCover);
        Button btnPhotoBookSave = findViewById(R.id.btnPhotoBookSave);
        Button btnPhotoBookPageCreate = findViewById(R.id.btnPhotoBookPageCreate);
        Button btnPhotoBookPageDelete = findViewById(R.id.btnPhotoBookPageDelete);
//        final ImageView testimage = findViewById(R.id.testimage);


        //그리드뷰 + 어댑터
        GridView gridView = findViewById(R.id.gridview);
        adapter = new BT_GridViewAdapter(this, imageList, videoList, seeList);
        gridView.setAdapter(adapter);

        //그리드뷰를 아이템을 클릭했을 때 리스너 발생.
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, final int position, long id) {
                final View  dialogView =(LinearLayout)  View.inflate(com.hanshin.ncs_travled.BT_Activity.this,  R.layout.bt_dialog_list, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(com.hanshin.ncs_travled.BT_Activity.this);

                dlg.setTitle("내용을 입력하세요");
                dlg.setView(dialogView);
                dlg.setIcon(R.drawable.ic_baseline_content_paste_24);
                dlg.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText listContent =dialogView.findViewById(R.id.listContent);

                        //리스트 데이터가 비디오면
                        if(String.valueOf(seeList.get(position)).contains("video")){
                            //비디오 컨텐츠 저장
                            a2=0;
                            for( a1=0; a1<position; a1++){
                                if(seeList.get(a1).toString().contains("image")){
                                    a2++;
                                }
                            }
                            contents2.set(position-a2, listContent.getText().toString());
                        } else if(String.valueOf(seeList.get(position)).contains("image")){
                            //이미지 컨텐츠 저장
                            a2= 0;
                            for( a1=0; a1<position; a1++){
                                if(seeList.get(a1).toString().contains("video")){
                                    a2++;
                                }
                            }
                            contents1.set(position-a2,listContent.getText().toString());
                        }
                        contents.set(position, listContent.getText().toString());
                        adapter.notifyDataSetChanged();
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });


        //포토북생성페이지에 정보버튼안에 대화상자 속성 정의
        LinearLayout dialogView;
        //포토북생성페이지에 정보 버튼을 클릭할 때 이벤트 작성
        btnPhotoBookInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                final LinearLayout dialogView = (LinearLayout) View.inflate(BT_Activity.this, R.layout.bt_dialog_photobookinfo, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(BT_Activity.this);
                dlg.setTitle(" 포토북 정보");
                dlg.setView(dialogView);
                dlg.setIcon(R.drawable.ic_baseline_info_24);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final EditText EditPhotoBookTitle =  dialogView.findViewById(R.id.EditPhotoBookTitle);
                        final EditText EditPhotoBookTravelDate = dialogView.findViewById(R.id.EditPhotoBookTravelDate);
                        final EditText EditPhotoBookTravelDate2 = dialogView.findViewById(R.id.EditPhotoBookTravelDate2);
                        final EditText EditPhotoBookTravelMember = dialogView.findViewById(R.id.EditPhotoBookTravelMember);
                        final EditText EditPhotoBookTravelArea =dialogView.findViewById(R.id.EditPhotoBookTravelArea);
                        final EditText EditPhotoBookTravelCity = dialogView.findViewById(R.id.EditPhotoBookTravelCity);

                        bt_item.setTitle(EditPhotoBookTitle.getText().toString());
                        bt_item.setDate(EditPhotoBookTravelDate.getText().toString());
                        bt_item.setDate2(EditPhotoBookTravelDate2.getText().toString());
                        bt_item.setMember(EditPhotoBookTravelMember.getText().toString());
                        bt_item.setArea(EditPhotoBookTravelArea.getText().toString());
                        bt_item.setCity(EditPhotoBookTravelCity.getText().toString());


//                        //지역선택, 정보버튼 컨텍스트 메뉴 등록
//                        registerForContextMenu(ButtonPhotoBookTravelArea);
//                        registerForContextMenu(ButtonPhotoBookTravelCity);
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
                adapter.notifyDataSetChanged();
                final LinearLayout dialogView = (LinearLayout) View.inflate(BT_Activity.this, R.layout.bt_dialog_photobookcover, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(BT_Activity.this);
                dlg.setTitle(" 포토북 표지");
                dlg.setView(dialogView);
                dlg.setIcon(R.drawable.ic_baseline_menu_book_24);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //확인 버튼 누를시 이벤트 작성하기

                        CheckBox bookCover1 = dialogView.findViewById(R.id.bookCover1);
                        CheckBox bookCover2= dialogView.findViewById(R.id.bookCover2);
                        CheckBox bookCover3 = dialogView.findViewById(R.id.bookCover3);
                        CheckBox bookCover4 = dialogView.findViewById(R.id.bookCover4);
                        CheckBox bookCover5 = dialogView.findViewById(R.id.bookCover5);
                        CheckBox bookCover6 = dialogView.findViewById(R.id.bookCover6);
                        CheckBox bookCover7 = dialogView.findViewById(R.id.bookCover7);
                        CheckBox bookCover8 = dialogView.findViewById(R.id.bookCover8);


                        if(bookCover1.isChecked()) bt_item.setCover("1");
                        if(bookCover2.isChecked()) bt_item.setCover("2");
                        if(bookCover3.isChecked()) bt_item.setCover("3");
                        if(bookCover4.isChecked()) bt_item.setCover("4");
                        if(bookCover5.isChecked()) bt_item.setCover("5");
                        if(bookCover6.isChecked()) bt_item.setCover("6");
                        if(bookCover7.isChecked()) bt_item.setCover("7");
                        if(bookCover8.isChecked()) bt_item.setCover("8");

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

                //bt_그리드뷰 어댑터의 add 메서드 실행 (이미지, 비디오 리스트 전송)
                adapter.add(imageList, 1);
                adapter.add(videoList, 2);
                adapter.add(seeList, 3);
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
                //파이어베이스 스토리지 업로드 (이미지 ,영상)
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                //업로드할때 날짜를 파일명앞에 지정해서, 파일을 분류
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss");
                Date now = new Date();
                String Datename = formatter.format(now);

                //저장하기전에 포토북 내용잘 작성했는지 확인하기

                    //이미지 리스트를 파이어베이스에 업로드
                    for (int i = 0; i < imageList.size(); i++) {   ///     이메일/지역/도시/포토북명으로 데이터 저장
                        StorageReference imageRef = storageRef.child(loginEmail+"/"+bt_item.getArea().trim() + "/" + bt_item.getCity().trim() + "/" + bt_item.getTitle().trim() + "/" + Datename + "-image" + i); //파이어베이스에 업로드할 이미지 이름 지정
                        //  Uri file  = Uri.fromFile(new File("/sdcard/Android/data/com.hanshin.ncs_travled/files/Pictures/p.png")); // 파이어베이스 다운로드 경로 예시
                        //    Uri file  = Uri.fromFile(new File("/sdcard/Download/fashion.jpg")); //갤러리경로 예시

                        Uri file = Uri.parse(String.valueOf(imageList.get(i)));
                        UploadTask uploadTask = imageRef.putFile(file);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(BT_Activity.this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(BT_Activity.this, "이미지 업로드 성공", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    //비디오 리스트를 파이어베이스에 업로드
                    for (int i = 0; i < videoList.size(); i++) {      ///     이메일/지역/도시/포토북명으로 데이터 저장
                        StorageReference videoRef = storageRef.child(loginEmail+"/"+bt_item.getArea().trim()  + "/" + bt_item.getCity().trim() + "/" + bt_item.getTitle().trim()  + "/" + Datename + "-video" + i); //파이어베이스에 업로드할 비디오 이름 지정
                        Uri file =Uri.parse(String.valueOf(videoList.get(i)));// 비디오리스트에서 내가 원하는 값을 집어넣음.
                        UploadTask uploadTask = videoRef.putFile(file);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(BT_Activity.this, "비디오 업로드 실패", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(BT_Activity.this, "비디오 업로드 성공", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
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

                    //파이어베이스 스토어 업로드 (데이터)
                    FirebaseFirestore db = FirebaseFirestore.getInstance();



                    Map<String , Object> member = new HashMap<>();
                    member.put("title", bt_item.getTitle().trim());
                    member.put("date", bt_item.getDate());
                    member.put("date2", bt_item.getDate2());
                    member.put("member", bt_item.getMember());
                    member.put("area", bt_item.getArea().trim());
                    member.put("city", bt_item.getCity().trim());
                    member.put("cover", bt_item.getCover());
                    member.put("contents",  bt_item.getContents());
                    member.put("contents2", bt_item.getContents2());

                    // 파이어스토어 ( 이메일명/ 지역 / 도시 /포토북명으로 데이터 분류)
                    db.collection(loginEmail).document(bt_item.getArea().trim()).collection(bt_item.getCity().trim()).document(bt_item.getTitle().trim()).set(member).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(BT_Activity.this, "데이터 업로드 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HT_Activity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(BT_Activity.this, "데이터 업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    });

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
            //어댑터 최신화
            adapter.notifyDataSetChanged();
            if (imagePath.toString().contains("image")) {
                //갤러리에서 이미지 경로 받아와서 리스트에 추가하기
                imageList.add(imagePath);
                seeList.add(imagePath);
                contents1.add("-");
                contents.add("-");

            } else if (imagePath.toString().contains("video")) {
                //갤러리에서 비디오 경로 받아와서 리스트에 추가하기
                videoList.add(imagePath);
                seeList.add(imagePath);
                contents2.add("-");
                contents.add("-");

            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



//    //포토북 정보에서 사용하는 컨텍스트메뉴 메서드 (메뉴창을 만듭니다)
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        MenuInflater menuInflater = getMenuInflater();
//        if( v == ButtonPhotoBookTravelArea){
//            menu.setHeaderTitle("지역선택");
//            menuInflater.inflate(R.menu.area, menu);
//        }
//        if( v== ButtonPhotoBookTravelCity){
//            menu.setHeaderTitle("도시선택");
//            menuInflater.inflate(R.menu.city,menu);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.itemSuwon:
//                bt_item.setPhotoBookTravelCity("수원");
//                return true;
//            case R.id.itemSeoul:
//                bt_item.setPhotoBookTravelArea("서울");
//
//        }
//        return false;
//    }
}

