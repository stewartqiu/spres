package net.schooldroid.spresensi.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import net.schooldroid.spresensi.Utils.ObjKehadiran;

import java.util.ArrayList;

/**
 * Created by Jekky on 31/10/2017.
 */

public class DataKehadiran extends SQLiteOpenHelper {

    private Context context;

    public static String namaTabel ="ObjKehadiran";
    public static String key_id = "ID";
    public static String key_persId = "PersID";
    public static String key_nama = "Nama";
    public static String key_tanggal = "Tanggal";
    public static String key_jamMasuk = "JamMasuk";
    public static String key_jamPulang = "JamPulang";

    public DataKehadiran(Context context) {
        super(context, "SchoolDroid_db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public boolean simpanPimpinan (String id, String persId, String nama, String tanggal, String jamMasuk, String jamPulang){

        SQLiteDatabase db = this.getWritableDatabase();
        if(!isTableExists(namaTabel,db)){
            BuatTabelJadwal();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(key_persId, persId);
        contentValues.put(key_nama, nama);
        contentValues.put(key_tanggal, tanggal);
        contentValues.put(key_jamMasuk, jamMasuk);
        contentValues.put(key_jamPulang, jamPulang);

        long result;
        if(!DataSudahAda(namaTabel,key_id,id,db)) {
            result = db.insert(namaTabel,null,contentValues);
            Log.d("ACTION", "NEW");
        }else{
            result = db.update(namaTabel,contentValues,key_id + " = ?", new String[] {id});
            Log.d("ACTION", "UPDATE");
        }

        Log.d("Result", "" + result);

        if (result == -1) {
            Log.d("Result", "GAGAL");
            return false;
        } else {
            Log.d("Result", "SIMPAN");
            return true;
        }
    }

    public boolean simpanKaryawan (String persId, String nama, String tanggal, String jamMasuk, String jamPulang){

        SQLiteDatabase db = this.getWritableDatabase();
        if(!isTableExists(namaTabel,db)){
            BuatTabelJadwal();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(key_persId, persId);
        contentValues.put(key_nama, nama);
        contentValues.put(key_tanggal, tanggal);
        contentValues.put(key_jamMasuk, jamMasuk);
        contentValues.put(key_jamPulang, jamPulang);

        long result;
        if(!DataSudahAda(namaTabel,key_tanggal,tanggal,db)) {
            result = db.insert(namaTabel,null,contentValues);
            Log.d("ACTION", "NEW");
        }else{
            result = db.update(namaTabel,contentValues,key_tanggal + " = ?", new String[] {tanggal});
            Log.d("ACTION", "UPDATE");
        }

        Log.d("Result", "" + result);

        if (result == -1) {
            Log.d("Result", "GAGAL");
            return false;
        } else {
            Log.d("Result", "SIMPAN");
            return true;
        }
    }

    public ArrayList<ObjKehadiran> ambil (String key, String value) {

        ArrayList<ObjKehadiran> result = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + namaTabel + " where " + key + " = '" + value + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        if(isTableExists(namaTabel,db)) {

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                ObjKehadiran obj = new ObjKehadiran();
                obj.setPersId(cursor.getString(1));
                obj.setNama(cursor.getString(2));
                obj.setTanggal(cursor.getString(3));
                obj.setJamMasuk(cursor.getString(4));
                obj.setJamPulang(cursor.getString(5));
                result.add(obj);
                Log.d("HASIL",cursor.getString(1)+"^"+cursor.getString(2)+"^"+cursor.getString(3));
            }

            cursor.close();
            return result;
        }
        else{
            return null;
        }
    }

    public ArrayList<String> ambilTanggal(){

        ArrayList<String> result = new ArrayList<>();

        String query = "select " + key_tanggal + " from " + namaTabel;

        SQLiteDatabase db = this.getWritableDatabase();
        if(isTableExists(namaTabel,db)) {

            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                for (int i = 0 ; i < cursor.getColumnCount();i++){
                    result.add(cursor.getString(i));
                }
            }

            cursor.close();
            return result;
        }
        else{
            return null;
        }

    }

    public ArrayList<ObjKehadiran> ambilTabel () {
        ArrayList<ObjKehadiran> result = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + namaTabel;

        SQLiteDatabase db = this.getWritableDatabase();
        if(isTableExists(namaTabel,db)) {

            Cursor cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                ObjKehadiran obj = new ObjKehadiran();
                obj.setPersId(cursor.getString(1));
                obj.setNama(cursor.getString(2));
                obj.setTanggal(cursor.getString(3));
                obj.setJamMasuk(cursor.getString(4));
                obj.setJamPulang(cursor.getString(5));
                result.add(obj);
                Log.d("HASIL",cursor.getString(1)+"^"+cursor.getString(2)+"^"+cursor.getString(3));
            }
            cursor.close();
            return result;
        }
        else{
            return null;
        }
    }

    public void hapus(String KeyFile , String isi){
        SQLiteDatabase db = this.getWritableDatabase();
        if(DataSudahAda(namaTabel,KeyFile,isi,db)){
            long result = db.delete(namaTabel,KeyFile + " = ?", new String[] {isi});
            Log.d("Result", "" + result);

            if (result == -1) {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context,"", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isTableExists(String tableName, SQLiteDatabase sqLiteDatabase) {

        if(sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = getReadableDatabase();
        }
        if(!sqLiteDatabase.isReadOnly()) {
            sqLiteDatabase = getReadableDatabase();
        }

        Cursor cursor = sqLiteDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                return true;
            }
        }
        return false;
    }

    public void BuatTabelJadwal(){
        SQLiteDatabase ourDatabase = this.getWritableDatabase();
        String buatTabelBaru = "create table if not exists "+ namaTabel +" ("+ key_id + " integer primary key autoincrement, " + key_persId + " text, " + key_nama + " text, " + key_tanggal + " text, "+ key_jamMasuk + " text, " + key_jamPulang + " text)";
        ourDatabase.execSQL(buatTabelBaru);
    }

    public boolean DataSudahAda (String NamaTabel, String KeyFile , String isi , SQLiteDatabase db){
        String query = "select * from " + NamaTabel + " where " + KeyFile + " = '" + isi +"'";
        Cursor data = db.rawQuery(query,null);

        if(data!=null) {
            if(data.getCount()>0) {
                data.close();
                return true;
            }
        }

        return false;
    }

}
