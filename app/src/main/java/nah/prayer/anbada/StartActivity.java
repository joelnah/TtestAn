package nah.prayer.anbada;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import nah.prayer.anbada.data.AllData;

public class StartActivity extends BaseAct {
    private static final int REQUEST_CODE_LOCATION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setContentView(R.layout.activity_start);
        if (Build.VERSION.SDK_INT >= 23
                && ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(this,
                        new String[] {
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_CONTACTS },
                        REQUEST_CODE_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[] {
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_CONTACTS },
                        REQUEST_CODE_LOCATION);
            }

        } else {
            PermissionSuccese();
        }



    }

    private void PermissionSuccese(){

        if(pref.getBoolean(allData.FRIST, false)){

        }

        if(allData.a ==null || allData.b ==null || allData.o ==null) {
            dbHelper.getResultAll();
        }

        if(pref.getBoolean(allData.SET_PW,false)){
            Intent i = new Intent(this, PasswordAct.class);
            i.putExtra("isReturnedForground",false);
            startActivity(i);
            finish();
        }else {
            startActivity(new Intent(this, MainAct.class));
            finish();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_LOCATION:
                // 퍼미션 사용이 허가된 경우
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 동의 버튼 선택
                    PermissionSuccese();
                } else {
                    // 사용이 거부된 경우의 대응
                    Toast.makeText(this, "권한사용을 동의하셔야 이용이 가능합니다.", Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(this, MainAct.class));
                   finish();
                }
                break;

            default:
                break;
        }
    }

}
