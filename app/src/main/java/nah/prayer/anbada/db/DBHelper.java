package nah.prayer.anbada.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import nah.prayer.anbada.R;
import nah.prayer.anbada.data.AllData;

import static nah.prayer.anbada.BaseAct.app;

/**
 * Created by Nah on 2017-01-06.
 */

public class DBHelper extends SQLiteOpenHelper {

    private String db_name = "Anbade" ;

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 어플이름 이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE " + db_name + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, type CHAR(2), num VARCHAR, str VARCHAR);");
        //db.execSQL("CREATE TABLE " + R.string.app_name + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, price INTEGER, create_at TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String type, String num, String str) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        app.Logs("INSERT INTO " + db_name + " VALUES(null, '" + type + "', '" + num + "', '" + str + "');");
        db.execSQL("INSERT INTO " + db_name + " VALUES(null, '" + type + "', '" + num + "', '" + str + "');");

        //static 변수 변경
        getResultType(db, type);
        db.close();
    }

    public void update(String type, String num, String str, String _id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        if(type.equals("P"))
            db.execSQL("UPDATE " + db_name + " SET num='" + num + "' WHERE type='" + type + "';");
        else {
            app.Logs("UPDATE " + db_name + " SET num='" + num + "' , str='" + str + "' WHERE _id='" + _id + "';");
            db.execSQL("UPDATE " + db_name + " SET num='" + num + "' , str='" + str + "' WHERE _id='" + _id + "';");
            getResultType(db, type);
        }
        db.close();
    }

    public void delete(String type, String _id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM " + db_name + " WHERE _id='" + _id + "';");
        getResultType(db, type);
        db.close();
    }

    public void getResultAll() {
        AllData.a = null; AllData.a = new ArrayList<>();
        AllData.b = null; AllData.b = new ArrayList<>();
        AllData.o = null; AllData.o = new ArrayList<>();

        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM " + db_name + ";", null);

        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("_id",cursor.getString(0));
            map.put("num",cursor.getString(2));
            map.put("content",cursor.getString(3));
            if(cursor.getString(1).equals("B")){
                AllData.b.add(map);
            }else if(cursor.getString(1).equals("A")){
                AllData.a.add(map);
            }else if(cursor.getString(1).equals("O")){
                AllData.o.add(map);
            }
        }
        db.close();
        //return result;
    }
    public void getResultType(SQLiteDatabase db, String type) {
        app.Logs("type : "+type);
        if (type.equals("B")) {
            AllData.b = null;
            AllData.b = new ArrayList<>();
        }
        else if(type.equals("O")) {
            AllData.o = null;
            AllData.o = new ArrayList<>();
        }

        Cursor cursor = db.rawQuery("SELECT * FROM " + db_name + " WHERE type='"+type+"';", null);
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("_id",cursor.getString(0));
            map.put("num",cursor.getString(2));
            map.put("content",cursor.getString(3));
            if (type.equals("B"))
                AllData.b.add(map);
            else if(type.equals("O"))
                AllData.o.add(map);
        }
    }

    //password
    public String getResultPw() {
        String result = null;
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + db_name + " WHERE type='P';", null);
        while (cursor.moveToNext()) {
            result = cursor.getString(2);
        }
        db.close();
        return result;
    }
}

