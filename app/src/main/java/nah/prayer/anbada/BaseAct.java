package nah.prayer.anbada;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import nah.prayer.anbada.data.AllData;
import nah.prayer.anbada.db.DBHelper;

public class BaseAct extends AppCompatActivity {
    public static BaseApp app;
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public AllData allData;
    public DBHelper dbHelper;
    public EncryptedPreferences encryptedPreferences;
    private final String KEY_VALUE_STRING = "Rejoice in the LORD";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = BaseApp.getInstance();
        allData = AllData.getInnstance();
        pref = getSharedPreferences(getString(R.string.app_name), this.MODE_PRIVATE);
        editor = pref.edit();
        app.Logs(getClass().getName().trim());
        dbHelper = new DBHelper(getApplicationContext(), getString(R.string.app_name)+".db", null, 1);
        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword("pw").build();
        encryptedPreferences.edit().putString("pw", KEY_VALUE_STRING).apply();
    }
}
