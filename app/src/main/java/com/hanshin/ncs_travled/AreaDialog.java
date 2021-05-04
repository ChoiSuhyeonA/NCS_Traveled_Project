package com.hanshin.ncs_travled;

import android.app.Dialog;
import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class AreaDialog {

    private Context context;
    private  CustomDialogListener customDialogListener;

    public void setDialogListener(CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }

    interface CustomDialogListener{
        void onAreaClicked(String name);
    }

    public AreaDialog(Context context) {
        this.context = context;
    }

    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.ht_selarea_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button seoulGyeongButton = (Button) dlg.findViewById(R.id.seoulGyeongSelBtn);
        final Button incheonButton = (Button) dlg.findViewById(R.id.incheonSelBtn);
        final Button busanButton = (Button) dlg.findViewById(R.id.busanSelBtn);
        final Button daejeonButton = (Button) dlg.findViewById(R.id.daejeonSelBtn);
        final Button daeguButton = (Button) dlg.findViewById(R.id.daeguSelBtn);
        final Button gwangjuButton = (Button) dlg.findViewById(R.id.gwangjuSelBtn);

        seoulGyeongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogListener.onAreaClicked(seoulGyeongButton.getText().toString());
                dlg.dismiss();
            }
        });
        incheonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogListener.onAreaClicked(incheonButton.getText().toString());
                dlg.dismiss();
            }
        });
        busanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogListener.onAreaClicked(busanButton.getText().toString());
                dlg.dismiss();
            }
        });
        daejeonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogListener.onAreaClicked(daejeonButton.getText().toString());
                dlg.dismiss();
            }
        });
        daeguButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogListener.onAreaClicked(daeguButton.getText().toString());
                dlg.dismiss();
            }
        });
        gwangjuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogListener.onAreaClicked(gwangjuButton.getText().toString());
                dlg.dismiss();
            }
        });
    }
}
