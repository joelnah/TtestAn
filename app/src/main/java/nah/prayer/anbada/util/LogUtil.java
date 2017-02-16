package nah.prayer.anbada.util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * Created by Nah on 2017-02-09.
 */

public class LogUtil {
    public static void writeLog(String str) {
        String str_Path_Full = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/nah_log.txt";

        File file;
        try {
            file = new File(str_Path_Full);
            if (file.exists() == false) {
                file.createNewFile();
            }
            BufferedWriter bfw = new BufferedWriter(new FileWriter(str_Path_Full,true));
           // BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(str_Path_Full), "EUC-KR"));
           // String content = new String(str.getBytes(), "EUC-KR");
            bfw.write(new SimpleDateFormat("MM-dd HH:mm:ss.SSS: ", Locale.getDefault()).format(System.currentTimeMillis()) + " : "+ str);
            bfw.write("\n");
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            Log.d("nah","LogUtil.writeLog.IOException : "+e);
        }
    }
}
