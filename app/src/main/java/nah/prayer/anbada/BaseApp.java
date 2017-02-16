package nah.prayer.anbada;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import nah.prayer.anbada.dialog.LoadingDialog;

/**
 * Created by NAH on 2016-10-01.
 */

public class BaseApp extends Application {
    public static LoadingDialog pd;
    public static BaseApp appInstance;
    private Toast toast;

    public BaseApp() {
        appInstance = this;
    }
    public static BaseApp getInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
    }

    public void ToastMessage(String msg){
        if(toast == null)
            toast = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
        //Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
    public void Logs(String log){
        Log.d("nah",log);
    }

    public void showProgress(Context context) {
        if (context == null) {
            pd.setCancelable(false);
            return;
        }
        if (pd != null) {
            hideProgress();
        }
        pd = new LoadingDialog(context);
        pd.show();
    }


    public void hideProgress() {
        if (pd != null) {
            try {
                pd.dismiss();

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }


    /////////////////////

    private AppStatus mAppStatus = AppStatus.FOREGROUND;
    public BaseApp get(Context context) {
        return (BaseApp) context.getApplicationContext();
    }

    public AppStatus getAppStatus() {
        return mAppStatus;
    }

    // check if app is foreground
    public boolean isForeground() {
        return mAppStatus.ordinal() > AppStatus.BACKGROUND.ordinal();
    }

    // check if app is return foreground
    public boolean isReturnedForground() {
        return mAppStatus.ordinal() == AppStatus.RETURNED_TO_FOREGROUND.ordinal();
    }

    public enum AppStatus {
        BACKGROUND,                // app is background
        RETURNED_TO_FOREGROUND,    // app returned to foreground(or first launch)
        FOREGROUND;                // app is foreground
    }

    public class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        // running activity count
        private int running = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (++running == 1) {
                // running activity is 1,
                // app must be returned from background just now (or first launch)
                mAppStatus = AppStatus.RETURNED_TO_FOREGROUND;
            } else if (running > 1) {
                // 2 or more running activities,
                // should be foreground already.
                mAppStatus = AppStatus.FOREGROUND;
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (--running == 0) {
                // no active activity
                // app goes to background
                mAppStatus = AppStatus.BACKGROUND;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    }



}
