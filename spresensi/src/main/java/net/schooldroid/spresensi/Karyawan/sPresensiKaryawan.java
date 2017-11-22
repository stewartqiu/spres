package net.schooldroid.spresensi.Karyawan;


import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import net.schooldroid.spresensi.Utils.Function;
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
    OnAbsenListener listener;

    public sPresensiKaryawan() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_karyawan, container, false);

        sqliteSetting = new SqliteSetting(getContext());
        sqlite = new DataKehadiran(getContext());

        initiateGps();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.tb_pres_karyawan);

        if(setTitle){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        }

        if(!Function.isTimeAutomatic(getContext())){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(getContext().getString(R.string.time_not_auto));
            builder.setPositiveButton(getContext().getString(R.string.atur_tanggal), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getContext().startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
                }
            });
            builder.create().show();
        }
        else{
            ambilData();
        }

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

        AdapterPresKaryawan adapter = new AdapterPresKaryawan(list,getActivity(),listener);
        review.setAdapter(adapter);
    }

    private void ambilData (){

        String tanggal = sDate.getNow("dd-MM-yyyy");

        String persid = sqliteSetting.ambil1(SettingID.persId);
        String nama = sqliteSetting.ambil1(SettingID.namaKaryawan);

        if(sqlite.ambilTabel()!=null) {
            list = sqlite.ambil(DataKehadiran.key_persId,persid);

            for(int i = 0;i<list.size();i++){
                ObjKehadiran obj = list.get(i);
                String tgl = obj.getTanggal();
                String plg = obj.getJamPulang();
                String msk = obj.getJamMasuk();

                if(!tgl.equals(tanggal) && plg.isEmpty()){
                    list.get(i).setJamPulang("-");
                    sqlite.simpanKaryawan(persid,nama,tgl,msk,"-");
                }
            }
        }

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

                boolean isMock = location.isFromMockProvider();
                if(isMock) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(getContext().getString(R.string.using_mock));
                    builder.setPositiveButton(getContext().getString(R.string.matikan_mock), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getContext().startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
                        }
                    });
                    builder.create().show();
                }
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

    public void setOnAbsentListener(OnAbsenListener listener){
        this.listener = listener;
    }

    public interface OnAbsenListener {
        void onAbsen (ObjKehadiran data);
    }

}