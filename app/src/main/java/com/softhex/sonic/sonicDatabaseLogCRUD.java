package com.softhex.sonic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Administrador on 10/07/2017.
 */

public class sonicDatabaseLogCRUD {

    private final String TABLE_LOG_ERRO = sonicConstants.TB_LOG_ERRO;
    sonicDatabase DB;
    Context ctx;


    public sonicDatabaseLogCRUD(Context ctx){
        this.ctx = ctx;
        this.DB = new sonicDatabase(ctx);
    }


    sonicDatabaseLogCRUD.Log Log = new Log();

    class Log {

        public long count(){

            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_LOG_ERRO);
            }catch (Exception e){
                e.printStackTrace();
            }

            return count;
        }

        public boolean saveLog(int linha, String erro, String activity, String classe, String metodo){

            ContentValues cv = new ContentValues();
            Calendar cal = GregorianCalendar.getInstance();
            SimpleDateFormat formatD = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatH = new SimpleDateFormat("HH:mm");
            String dateToStr = formatD.format(cal.getTime());
            String hourToStr = formatH.format(cal.getTime());
            Field[] fields = Build.VERSION_CODES.class.getFields();
            String osName = fields[Build.VERSION.SDK_INT + 1].getName();

            try{

                cv.put("android_manufacturer", Build.MANUFACTURER);
                cv.put("android_model", Build.MODEL);
                cv.put("android_sdk", Build.VERSION.SDK_INT);
                cv.put("android_name", osName);
                cv.put("android_release", Build.VERSION.RELEASE);
                cv.put("activity", activity);
                cv.put("class", classe);
                cv.put("method", metodo);
                cv.put("line", linha);
                cv.put("log", erro);
                cv.put("data", dateToStr);
                cv.put("hora", hourToStr);


            }catch (SQLException e){
                e.printStackTrace();
            }

            return DB.getWritableDatabase().insert(TABLE_LOG_ERRO, null, cv)>0;
        }

        public List<sonicSistemaLogHolder> selectLog(){
            List<sonicSistemaLogHolder> erros = new ArrayList<sonicSistemaLogHolder>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT * FROM "+TABLE_LOG_ERRO+" ORDER BY _id DESC " , null);

            while(cursor.moveToNext()){

                sonicSistemaLogHolder erro = new sonicSistemaLogHolder();

                erro.setCodigo(cursor.getInt(cursor.getColumnIndex("_id")));
                erro.setManufaturer(cursor.getString(cursor.getColumnIndex("android_manufacturer")));
                erro.setModel(cursor.getString(cursor.getColumnIndex("android_model")));
                erro.setSdk(cursor.getInt(cursor.getColumnIndex("android_sdk")));
                erro.setName(cursor.getString(cursor.getColumnIndex("android_name")));
                erro.setRelease(cursor.getString(cursor.getColumnIndex("android_release")));
                erro.setActivity(cursor.getString(cursor.getColumnIndex("activity")));
                erro.setClasse(cursor.getString(cursor.getColumnIndex("class")));
                erro.setMethod(cursor.getString(cursor.getColumnIndex("method")));
                erro.setLine(cursor.getInt(cursor.getColumnIndex("line")));
                erro.setLog(cursor.getString(cursor.getColumnIndex("log")));
                erro.setData(cursor.getString(cursor.getColumnIndex("data")));
                erro.setHora(cursor.getString(cursor.getColumnIndex("hora")));

                erros.add(erro);

            }
            cursor.close();
            return erros;
        }

        public String selectLastError() {

            String log = "";

            try {
                Cursor cursor = DB.getReadableDatabase().rawQuery(
                        "SELECT log FROM " + TABLE_LOG_ERRO + " ORDER BY _id DESC LIMIT 1 ", null);
                if (cursor.moveToFirst()) {
                    log = cursor.getString(cursor.getColumnIndex("log"));
                }
            }catch (SQLiteException e){
                e.getMessage();
                log = e.getMessage();
            }
            return log;
        }

        public boolean cleanLog(){

            return DB.getWritableDatabase().delete(TABLE_LOG_ERRO, null, null)>0;
        }

    }


}
