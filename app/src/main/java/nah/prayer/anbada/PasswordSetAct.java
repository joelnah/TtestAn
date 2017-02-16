package nah.prayer.anbada;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nah.prayer.anbada.data.AllData;

public class PasswordSetAct extends BaseAct {

   // int num[] = {R.id.pw0,R.id.pw1,R.id.pw2,R.id.pw3,R.id.pw4,R.id.pw5,R.id.pw6,R.id.pw7,R.id.pw8,R.id.pw9,R.id.pw_back};
    private ArrayList<String> numList;
    private TextView input_pw;
    private String KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_set);


        numList = new ArrayList<>();

        LayoutSet();
        //db에 등록된 비밀번호를 가져옴
        KEY = dbHelper.getResultPw();
        //등록된 비밀번호가 없을시 null
        if(KEY != null){
            input_pw.setText(getString(R.string.pw_old));
        }

        ((ImageView) findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((ImageView) findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numList.size() == 4){
                    String getPw="";
                    for(String s : numList)
                        getPw += s;

                    if(input_pw.getText().toString().equals(getString(R.string.pw_old))) {
                        if(encryptedPreferences.getUtils().decryptStringValue(KEY).equals(getPw)){
                            input_pw.setText(getString(R.string.pw_new));
                        }else{
                            app.ToastMessage(getString(R.string.toast_pw_no2));
                        }
                        numClear();
                    }
                    else
                    {
                        if(KEY != null){
                            dbHelper.update("P",encryptedPreferences.getUtils().encryptStringValue(getPw),"","");
                        }else{
                            dbHelper.insert("P",encryptedPreferences.getUtils().encryptStringValue(getPw),"");
                            editor.putBoolean(AllData.IS_PW, true).commit();
                        }
                        editor.putBoolean(AllData.SET_PW, true).commit();
                        finish();
                    }
                }else{
                    app.ToastMessage(getString(R.string.toast_pw_no));
                }
            }
        });

        ((TextView) findViewById(R.id.btn_view_num)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ((LinearLayout) findViewById(R.id.layout_num_group)).setVisibility(View.VISIBLE);
                        return true;
                    case MotionEvent.ACTION_UP:
                        ((LinearLayout) findViewById(R.id.layout_num_group)).setVisibility(View.GONE);
                        return false;
                }
                return false;
            }
        });

    }

    private ImageView pw_iv1,pw_iv2,pw_iv3,pw_iv4;
    private TextView pw_tv1,pw_tv2,pw_tv3,pw_tv4;
    private void LayoutSet(){
        input_pw = (TextView) findViewById(R.id.input_pw);
        pw_iv1 = (ImageView) findViewById(R.id.pw_iv1);
        pw_iv2 = (ImageView) findViewById(R.id.pw_iv2);
        pw_iv3 = (ImageView) findViewById(R.id.pw_iv3);
        pw_iv4 = (ImageView) findViewById(R.id.pw_iv4);
        pw_tv1 = (TextView) findViewById(R.id.pw_tv1);
        pw_tv2 = (TextView) findViewById(R.id.pw_tv2);
        pw_tv3 = (TextView) findViewById(R.id.pw_tv3);
        pw_tv4 = (TextView) findViewById(R.id.pw_tv4);

    }

    private void numClear(){
        numList.clear();
        pw_iv1.setSelected(false);
        pw_tv1.setText("");
        pw_iv2.setSelected(false);
        pw_tv2.setText("");
        pw_iv3.setSelected(false);
        pw_tv3.setText("");
        pw_iv4.setSelected(false);
        pw_tv4.setText("");
    }

    public void numClick(View v){
        switch (v.getId()){
            case R.id.pw_back:
                if(numList.size() != 0){
                    numList.remove(numList.size()-1);
                }
                break;
            default:
                if(numList.size() < 4){
                    numList.add(((TextView) findViewById(v.getId())).getText().toString());
                }
                break;
        }

        switch (numList.size()){
            case 0:
                pw_iv1.setSelected(false);
                pw_tv1.setText("");
                break;
            case 1:
                pw_iv1.setSelected(true);
                pw_tv1.setText(numList.get(0));
                pw_iv2.setSelected(false);
                pw_tv2.setText("");
                break;
            case 2:
                pw_iv2.setSelected(true);
                pw_tv2.setText(numList.get(1));
                pw_iv3.setSelected(false);
                pw_tv3.setText("");
                break;
            case 3:
                pw_iv3.setSelected(true);
                pw_tv3.setText(numList.get(2));
                pw_iv4.setSelected(false);
                pw_tv4.setText("");
                break;
            case 4:
                pw_iv4.setSelected(true);
                pw_tv4.setText(numList.get(3));
                break;
        }
    }
}
