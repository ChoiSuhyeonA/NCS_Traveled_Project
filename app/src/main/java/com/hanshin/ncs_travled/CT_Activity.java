package com.hanshin.ncs_travled;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CT_Activity extends Activity {

    static CT_Adapter listAdapter;
    RecyclerView recyclerView;
    CT_recyclerAdapter recyclerAdapter;
    ListView listview;
    Button writeBtn;
    //구글로그인 회원정보
    String loginName = "";
    String loginEmail = "";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    static CT_Create_Item ct_item = new CT_Create_Item();

    //커뮤니티 데이타
    ArrayList<String> email = new ArrayList<String>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> contents = new ArrayList<String>();
    ArrayList<String> pageNumber = new ArrayList<String>();
    ArrayList<Uri> image = new ArrayList<Uri>();

    //파이어스토리지 체크변수
    int k;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct_list);

        email.clear();
        name.clear();
        title.clear();
        date.clear();
        contents.clear();
        pageNumber.clear();
        image.clear();


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

        //로그인한 회원정보를 가져오는 변수
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            //회원정보 이름
            loginName = signInAccount.getDisplayName();
            //회원정보 이메일
            loginEmail = signInAccount.getEmail();
        }

        ///////////// 관리자 게시판
        init();
        getData();

        //게시글
        communityGetData();

        //글쓰기 버튼 클릭
        writeBtn = findViewById(R.id.ct_writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CT_Create.class);
                startActivity(intent);
                finish();
            }
        });


    }

    //커뮤니티에 등록된 모든 데이타 가져오기기
    private void communityGetData() {

        db.collection("community").orderBy("realDate", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //컬렉션 아래에 있는 모든 정보를 가져온다.
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ct_item = document.toObject(CT_Create_Item.class);
                        pageNumber.add(ct_item.getPageNumber());
                        email.add(ct_item.getEmail());
                        name.add(ct_item.getName());
                        title.add(ct_item.getTitle());
                        date.add(ct_item.getDate());
                        contents.add(ct_item.getContents());

                    }
                    firestoreImageAdd();


                } else {
                    Toast.makeText(CT_Activity.this, "로딩실패", Toast.LENGTH_SHORT).show();
                }
            }
            //에러가 발생됐을 경우.
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(CT_Activity.this, "데이터 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void firestoreImageAdd() {
        // 커뮤니티 이미지 다운로드
        image.clear();

        BackgroundThread thread = new BackgroundThread();

        for (k=0; k<pageNumber.size(); k++) {

            StorageReference storageReference = storage.getReference();
            StorageReference storageRef = storageReference.child(("community" + "/" + email.get(k) + "/" + pageNumber.get(k)));
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    image.add(uri);
                    listAdd();
                }
            });
            thread.run();
        }


    }




    private void listAdd() {
        ///////////// 사용자 게시판
        listAdapter = new CT_Adapter();
        listview = findViewById(R.id.ct_listview);
        listview.setAdapter(listAdapter);

        for (int i = 0; i < image.size(); i++) {
            listAdapter.addItem(image.get(i), title.get(i), date.get(i));
            listAdapter.notifyDataSetChanged();
        }
        listClick();
    }

    private void listClick() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                final View dialogView = (LinearLayout) View.inflate(CT_Activity.this, R.layout.ct_list_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(CT_Activity.this);

                ImageView dlg_img = dialogView.findViewById(R.id.ct_dialog_createImage);
                EditText dlg_date = (EditText) dialogView.findViewById(R.id.ct_dialog_createDate);
                EditText dlg_title = (EditText) dialogView.findViewById(R.id.ct_dialog_createTitle);
                EditText dlg_contents = (EditText) dialogView.findViewById(R.id.ct_dialog_createContent);

                Glide.with(getApplicationContext()).load(image.get(position)).into(dlg_img);
                dlg_img.setScaleType(ImageView.ScaleType.FIT_XY);
                dlg_date.setText(date.get(position));
                dlg_title.setText(title.get(position));
                dlg_contents.setText(contents.get(position));

                dlg.setView(dialogView);
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
    }


    //리사이클러뷰
    private void init() {
        recyclerView = findViewById(R.id.ct_recycler);
        recyclerView.addItemDecoration(new CT_recyclerDecoration(30)); // 아이템 간격
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new CT_recyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void getData() {
        CT_recyclerItem recyclerItem = new CT_recyclerItem(R.drawable.ad1, "", "");
        recyclerAdapter.addItem(recyclerItem);
        recyclerItem = new CT_recyclerItem(R.drawable.ad2, "", "");
        recyclerAdapter.addItem(recyclerItem);
        recyclerItem = new CT_recyclerItem(R.drawable.ad3, "", "");
        recyclerAdapter.addItem(recyclerItem);
        recyclerItem = new CT_recyclerItem(R.drawable.ad4, "", "");
        recyclerAdapter.addItem(recyclerItem);

    }


}
//대기 시간을 할 수 있도록 하는 클래스
class BackgroundThread extends  Thread{
    public  void run(){
            try{
                Thread.sleep(600);
            }catch (Exception e){
            }
    }
}
