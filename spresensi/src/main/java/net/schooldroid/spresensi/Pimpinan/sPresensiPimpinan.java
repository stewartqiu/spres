package net.schooldroid.spresensi.Pimpinan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.schooldroid.sdatelib.sDate;
import net.schooldroid.spresensi.R;

import net.schooldroid.spresensi.Utils.DataKehadiran;
import net.schooldroid.spresensi.Utils.ObjKehadiran;
import net.schooldroid.spresensi.Utils.SettingID;
import net.schooldroid.ssetting.Setting.SqliteSetting;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class sPresensiPimpinan extends Fragment {

    SqliteSetting setting;

    RecyclerView review;
    boolean setTitle;
    String title;
    TextView tvTanggal;

    DataKehadiran dataKehadiran;

    public sPresensiPimpinan() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pimpinan, container, false);

        //setHasOptionsMenu(true);

        dataKehadiran = new DataKehadiran(getContext());
        setting = new SqliteSetting(getContext());

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.tb_pres_pimpinan);

        if(setTitle){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        }

        viewHandler(view);

        return view;
    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pimpinan,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }*/

    public void setTitleonToolbar (String title){
        setTitle = true;
        this.title = title;
    }

    private void viewHandler(View view){

        tvTanggal = view.findViewById(R.id.tvTanggalPim);

        review = view.findViewById(R.id.reviewPresensiPimpinan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        review.setLayoutManager(layoutManager);
        review.setHasFixedSize(true);

        ambilTanggal();

    }

    private void ambilTanggal(){

        String now = sDate.getNow("dd-MM-yyyy");

        tvTanggal.setText(now);
        ambilData(now);

        tvTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                final int day = c.get(Calendar.DAY_OF_MONTH);
                final int month = c.get(Calendar.MONTH);
                final int year = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String d = ""+i2;
                        if(i2<10){
                            d = "0"+i2;
                        }
                        String picked = d + "-" + (i1+1) + "-" + i;
                        tvTanggal.setText(picked);
                        ambilData(picked);
                        Toast.makeText(getContext(), picked, Toast.LENGTH_SHORT).show();
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

    }

    private void ambilData (String tanggal){
        if(tanggal!=null && !tanggal.isEmpty()) {
            ArrayList<ObjKehadiran> list = dataKehadiran.ambil(DataKehadiran.key_tanggal, tanggal);

            AdapterPresPimpinan adapter = new AdapterPresPimpinan(list, getActivity());
            review.setAdapter(adapter);
        }
    }

}