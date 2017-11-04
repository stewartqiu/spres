package net.schooldroid.spresensi.Pimpinan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.schooldroid.sdatelib.sDate;
import net.schooldroid.spresensi.R;

import net.schooldroid.spresensi.Utils.DataKehadiran;
import net.schooldroid.spresensi.Utils.ObjKehadiran;
import net.schooldroid.spresensi.Utils.SettingID;
import net.schooldroid.ssetting.Setting.SqliteSetting;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class sPresensiPimpinan extends Fragment {

    SqliteSetting setting;

    RecyclerView review;
    boolean setTitle;
    String title;

    public sPresensiPimpinan() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pimpinan, container, false);

        if(setTitle){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        }

        setting = new SqliteSetting(getContext());

        if(setting.ambil1(SettingID.latPerusahaan) == null || setting.ambil1(SettingID.latPerusahaan).isEmpty()) {
            setting.simpan(SettingID.folderKehadiran,"Riau/Pekanbaru"); // TODO CONTOH
            setting.simpan(SettingID.latPerusahaan,"0.5372369");
            setting.simpan(SettingID.longPerusahaan, "101.46958");
            setting.simpan(SettingID.radius, "20");
        }

        viewHandler(view);

        ambilData();

        return view;
    }


    public void setTitleonToolbar (String title){
        setTitle = true;
        this.title = title;
    }

    private void viewHandler(View view){

        review = view.findViewById(R.id.reviewPresensiPimpinan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        review.setLayoutManager(layoutManager);
        review.setHasFixedSize(true);

    }

    private void ambilData(){

        String now = sDate.getNow("dd-MM-yyyy");

        DataKehadiran dataKehadiran = new DataKehadiran(getContext());
        ArrayList<ObjKehadiran> list = dataKehadiran.ambil(DataKehadiran.key_tanggal,now);

        AdapterPresPimpinan adapter = new AdapterPresPimpinan(list,getActivity());
        review.setAdapter(adapter);

    }

}
