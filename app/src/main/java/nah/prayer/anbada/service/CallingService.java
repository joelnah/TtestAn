package nah.prayer.anbada.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;


import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

import nah.prayer.anbada.R;
import nah.prayer.anbada.data.AllData;
import nah.prayer.anbada.util.LogUtil;

/**
 * Created by NAH on 2016-10-01.
 */

public class CallingService extends Service {

    public static final String EXTRA_CALL_NUMBER = "call_number";
    private SharedPreferences pref;
    private Intent intent;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        pref = getSharedPreferences(getString(R.string.app_name), this.MODE_PRIVATE);
        this.intent = intent;
        GetUserContactsList(intent.getStringExtra(EXTRA_CALL_NUMBER));
        return START_NOT_STICKY;
    }

    private void GetUserContactsList(String number){

            try{
                TelephonyManager mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                Class<?> mClass = Class.forName(mTelephonyManager.getClass().getName());
                Method mMethod = mClass.getDeclaredMethod("getITelephony");
                mMethod.setAccessible(true);
                ITelephony mITelephony = (ITelephony) mMethod.invoke(mTelephonyManager);
                mITelephony.endCall();
              //  audioManager.setRingerMode(backRington);

                if(pref.getBoolean(AllData.SET_NOTIFICATION,true)) {
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                            .setContentTitle(getString(R.string.service_block))
                            .setContentText(String.valueOf(number))
                            .setAutoCancel(true)
                            .setOngoing(false);

                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notificationBuilder.build());
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                LogUtil.writeLog("Exception : "+e);
                Log.d("nah","Exception : "+e);
            }

        stopService(intent);
    }





    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


  }