package nah.prayer.anbada.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import nah.prayer.anbada.PasswordSetAct;
import nah.prayer.anbada.R;
import nah.prayer.anbada.data.AllData;


public class NahMenu extends Activity{

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private ImageView tog_service, tog_nofi, tog_pass;
    private TextView btn_set_pw;

    private PackageInfo pi = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.LEFT;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_nah_menu);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int)(dm.widthPixels * 0.8);
        int height = (int)(dm.heightPixels);

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


        pref = getSharedPreferences(getString(R.string.app_name), this.MODE_PRIVATE);
        editor = pref.edit();
        LayoutSet();
    }

    private void LayoutSet(){

        try {

            pi = getPackageManager().getPackageInfo(getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {

// TODO Auto-generated catch block

            e.printStackTrace();

        }

        ((TextView) findViewById(R.id.version)).setText(pi.versionName);
        tog_service = (ImageView) findViewById(R.id.tog_service);
        tog_nofi = (ImageView) findViewById(R.id.tog_nofi);
        tog_pass = (ImageView) findViewById(R.id.tog_pass);

        btn_set_pw = (TextView) findViewById(R.id.btn_set_pw);
        btn_set_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NahMenu.this, PasswordSetAct.class));
                finish();
            }
        });


        if(pref.getBoolean(AllData.SET_SERVICE,true))
            tog_service.setSelected(true);
        if(pref.getBoolean(AllData.SET_NOTIFICATION,true))
            tog_nofi.setSelected(true);
        if(pref.getBoolean(AllData.SET_PW,false))
            tog_pass.setSelected(true);
    }

/*    public void menuClick(View v){
*//*        Intent intent = getIntent();
        intent.putExtra("id", v.getId());
        setResult(RESULT_OK,intent);*//*
        Toast.makeText(this, "들와따", Toast.LENGTH_SHORT).show();
        switch (v.getId()){
            case R.id.btn_set_pw:
                startActivity(new Intent(this, PasswordSetAct.class));
                break;
        }
        finish();
    }*/
    public void toggleClick(View v){
        switch (v.getId()){
            case R.id.tog_service:
                if(tog_service.isSelected()){
                    tog_service.setSelected(false);
                }else{
                    tog_service.setSelected(true);
                }
                editor.putBoolean(AllData.SET_SERVICE , tog_service.isSelected()).commit();
                break;
            case R.id.tog_nofi:
                if(tog_nofi.isSelected()){
                    tog_nofi.setSelected(false);
                }else{
                    tog_nofi.setSelected(true);
                }
                editor.putBoolean(AllData.SET_NOTIFICATION , tog_nofi.isSelected()).commit();
                break;
            case R.id.tog_pass:
                //등록된 비밀번호 여부 체크
                if(pref.getBoolean(AllData.IS_PW,false)){
                    if(tog_pass.isSelected()){
                        tog_pass.setSelected(false);
                    }else{
                        tog_pass.setSelected(true);
                    }
                    editor.putBoolean(AllData.SET_PW , tog_pass.isSelected()).commit();
                }else{
                    startActivity(new Intent(this, PasswordSetAct.class));
                }


                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,R.anim.slide_out_left);
    }

}