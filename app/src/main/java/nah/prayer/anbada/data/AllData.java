package nah.prayer.anbada.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nah on 2017-01-03.
 */

public class AllData {
    private static AllData allDataInstance;

    public static AllData getInnstance() {
        if (allDataInstance == null) {
            allDataInstance = new AllData();
        }
        return allDataInstance;
    }

    //pref
    public final static String FRIST = "FRIST";
    public final static String SET_PW = "SET_PW"; // 토글 패스워드
    public final static String SET_SERVICE = "SET_SERVICE"; //토글 서비스
    public final static String SET_NOTIFICATION = "SET_NOTIFICATION"; //토글 알림
    public final static String IS_PW = "IS_PW"; //패스워드 설정 여부 true면 있음
    public final static String IS_BLOCK_ALL = "IS_BLOCK_ALL"; //지역번호 전체차단 여부

    public static String p;
    public static ArrayList<HashMap> a;
    public static ArrayList<HashMap> b;
    public static ArrayList<HashMap> o;

}
