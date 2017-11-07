package net.schooldroid.spresensi.Karyawan;


import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.LocationListener;

import net.schooldroid.sdatelib.sDate;
import net.schooldroid.spresensi.R;
import net.schooldroid.spresensi.Utils.DataKehadiran;
import net.schooldroid.spresensi.Utils.ObjKehadiran;
import net.schooldroid.spresensi.Utils.SettingID;
import net.schooldroid.ssetting.Setting.SqliteSetting;

import java.util.ArrayList;
import Gps.sGPS;


public class sPresensiKaryawan extends Fragment {

    SqliteSetting sqliteSetting;
    RecyclerView review;
    DataKehadiran sqlite;
    ArrayList<ObjKehadiran> list = new ArrayList<>();
    boolean setTitle;
    String title;

    public sPresensiKaryawan() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_karyawan, container, false);

        sqliteSetting = new SqliteSetting(getContext());
        sqlite = new DataKehadiran(getContext());

        //FAKE
       /* if(sqliteSetting.ambil1(SettingID.persId) == null || sqliteSetting.ambil1(SettingID.persId).isEmpty()){
            sqliteSetting.simpan(SettingID.persId , "88888888");
            sqliteSetting.simpan(SettingID.namaKaryawan,"Andi");
        }*/

        initiateGps();

        if(setTitle){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        }


        ambilData();
        viewHandler(view);

        return view;
    }

    public void setTitleonToolbar (String title){
        setTitle = true;
        this.title = title;
    }

    private void viewHandler(View view){
        review = view.findViewById(R.id.reviewPresensiKaryawan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        review.setLayoutManager(layoutManager);
        review.setHasFixedSize(true);

        addToday();

        AdapterPresKaryawan adapter = new AdapterPresKaryawan(list,getActivity());
        review.setAdapter(adapter);
    }

    private void ambilData (){

        String persid = sqliteSetting.ambil1(SettingID.persId);
        String nama = sqliteSetting.ambil1(SettingID.namaKaryawan);

        if(sqlite.ambilTabel()!=null) {
            list = sqlite.ambil(DataKehadiran.key_persId,persid);
        }
       /* else{
            //FAKE

            sqlite.simpanKaryawan(persid,nama,"18-10-2017","08:00:00","17:00:00");
            sqlite.simpanKaryawan(persid,nama,"19-10-2017","08:00:00","17:00:00");
            sqlite.simpanKaryawan(persid,nama,"20-10-2017","08:00:00","17:00:00");
            sqlite.simpanKaryawan(persid,nama,"21-10-2017","08:00:00","17:00:00");
            sqlite.simpanKaryawan(persid,nama,"22-10-2017","08:00:00","17:00:00");
            sqlite.simpanKaryawan(persid,nama,"23-10-2017","08:00:00","17:00:00");
            sqlite.simpanKaryawan(persid,nama,"24-10-2017","08:00:00","17:00:00");
            sqlite.simpanKaryawan(persid,nama,"25-10-2017","08:00:00","17:00:00");
            sqlite.simpanKaryawan(persid,nama,"26-10-2017","08:00:00","17:00:00");
            sqlite.simpanKaryawan(persid,nama,"27-10-2017","08:00:00","17:00:00");
            sqlite.simpanKaryawan(persid,nama,"28-10-2017","08:00:00","17:00:00");
            sqlite.simpanKaryawan(persid,nama,"29-10-2017","08:00:00","17:00:00");
            sqlite.simpanKaryawan(persid,nama,"30-10-2017","08:00:00","17:00:00");
            list = sqlite.ambil(DataKehadiran.key_persId,persid);
        }*/
    }

    private void addToday(){
        String tanggal = sDate.getNow("dd-MM-yyyy");

        ArrayList<String> listTanggal = sqlite.ambilTanggal();
        if (listTanggal == null || !listTanggal.contains(tanggal)) {
            list.add(new ObjKehadiran("","",tanggal,"",""));
        }

    }

    private void initiateGps(){
        final sGPS gps = new sGPS(getContext());
        gps.on(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("KAR","Initiate GPS");
            }
        });

        final int TIMER = 15000;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gps.off();
            }
        }, TIMER);
    }

}