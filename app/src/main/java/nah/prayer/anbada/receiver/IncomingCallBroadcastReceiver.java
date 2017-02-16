package nah.prayer.anbada.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Locale;

import nah.prayer.anbada.R;
import nah.prayer.anbada.data.AllData;
import nah.prayer.anbada.service.CallingService;
import nah.prayer.anbada.util.LogUtil;

import static nah.prayer.anbada.BaseAct.app;

/**
 * Created by NAH on 2016-10-01.
 */

public class IncomingCallBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = "PHONE STATE";
    private static String mLastState;
    private String phone_num;
    private String area_num;
    private Context con;
    private AllData allData;
   // private AudioManager    audioManager;
  // private Context con;

  //  private final Handler mHandler = new Handler(Looper.getMainLooper());
  private SharedPreferences pref;



    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d(TAG,"onReceive()");


     //   this.con = context;
   //     audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        /**
         * http://mmarvick.github.io/blog/blog/lollipop-multiple-broadcastreceiver-call-state/
         * 2번 호출되는 문제 해결
         */
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state.equals(mLastState)) {
            return;

        } else {
            mLastState = state;

        }
        con = context;
        allData = AllData.getInnstance();
        pref = con.getSharedPreferences(con.getString(R.string.app_name), con.MODE_PRIVATE);

        if (!pref.getBoolean(allData.SET_SERVICE,true))
            return;


        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
      //      int ringMode = audioManager.getRingerMode();

        //    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

          //  final String phone_num = PhoneNumberUtils.formatNumber(incomingNumber);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                phone_num = PhoneNumberUtils.formatNumber(incomingNumber, Locale.getDefault().getCountry());
            } else {
                phone_num = PhoneNumberUtils.formatNumber(incomingNumber);
            }
            Log.d("nah","get 번호 : "+phone_num);

            String area_is[] = phone_num.split("-");
            area_num = area_is[0];
            LogUtil.writeLog("\n---///////////////////////////////////////////-\nCall AreaNumber : "+ area_num);
/*            if(area_num.equals("010")){
                for (int i = 0 ; i < allData.b.size() ; i++) {
                    if(phone_num.replace("-","").equals(allData.b.get(i).get("num"))){
                        StartService();
                        break;
                    }
                }
            }else{
                PremitCheck();
            }*/
            PremitCheck();
        }


    }

    private void PremitCheck(){
        //허용리스트 체크
        boolean check = false;
        for (int i = 0 ; i < allData.o.size() ; i++) {
            if(phone_num.replace("-","").equals(allData.o.get(i).get("num"))){
                check = true;
                break;
            }
        }
        LogUtil.writeLog("Checking OK List : "+check);
        app.Logs("Checking OK List : "+check);
        if(!check)
            AllBlockCheck();
    }


    private void AllBlockCheck(){
        //지역번호 전체차단 체크
        LogUtil.writeLog("Checking area number all block : "+pref.getBoolean(allData.IS_BLOCK_ALL,true));
        app.Logs("Checking area number all block : "+pref.getBoolean(allData.IS_BLOCK_ALL,true));
        if(pref.getBoolean(allData.IS_BLOCK_ALL,true)){
            PhoneBookCheck();
        }else{
            AreaBlockCheck();
        }

    }
    private void PhoneBookCheck(){
        //전화번호부 체크
        boolean check = false;
        Cursor c = app.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone_num)),
                new String[] {ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        while(c.moveToNext()){
            Log.d("nah","contactName : "+c.getString(c.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME)));
            check = true;
            break;
        }
        c.close();

        LogUtil.writeLog("Checking phone book : "+check);
        app.Logs("Checking phone book : "+check);

        if(check){
            BlockListCheck();
        }else {
            StartService();
        }
    }

    private void AreaBlockCheck(){
        //차단 지역번호 체크
        LogUtil.writeLog("Checking block area number : "+area_num+" : "+pref.getBoolean(area_num,false));
        app.Logs("Checking block area number : "+area_num+" : "+pref.getBoolean(area_num,false));
        if(pref.getBoolean(area_num,false)){
            PhoneBookCheck();
        }else{
            BlockListCheck();
        }
    }



    private void BlockListCheck(){
        //차단 등록리스트 체크
        boolean check = false;
        for (int i = 0 ; i < allData.b.size() ; i++) {
            if(phone_num.replace("-","").equals(allData.b.get(i).get("num"))){
                check = true;
                break;
            }
        }
        LogUtil.writeLog("Checking block list : "+check);
        app.Logs("Checking block list : "+check);
        if(check){
            StartService();
        }
    }

    private void StartService(){
        LogUtil.writeLog("Block");
        app.Logs("Block");
        Intent serviceIntent = new Intent(con, CallingService.class);
        serviceIntent.putExtra(CallingService.EXTRA_CALL_NUMBER, phone_num);
        con.startService(serviceIntent);
    }

}