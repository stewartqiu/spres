package net.schooldroid.spresensi.Karyawan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.schooldroid.spresensi.R;

import java.util.ArrayList;
import net.schooldroid.sdatelib.sDate;
import net.schooldroid.spresensi.Utils.DataKehadiran;
import net.schooldroid.spresensi.Utils.ObjKehadiran;
import net.schooldroid.spresensi.Utils.SettingID;
import net.schooldroid.ssetting.Setting.SqliteSetting;

import Gps.sGPS;
import es.dmoral.toasty.Toasty;


public class AdapterPresKaryawan extends RecyclerView.Adapter<AdapterPresKaryawan.MyViewHolder>  {

    ArrayList<ObjKehadiran> arrayList;
    DataKehadiran sqlite;
    Activity activity;
    sGPS gps;
    SqliteSetting setting;
    String nama;
    String persid;

    public AdapterPresKaryawan(ArrayList<ObjKehadiran> list, Activity activity){
        arrayList = list;
        this.activity = activity;
        sqlite = new DataKehadiran(activity);
        gps = new sGPS(activity);
        setting = new SqliteSetting(activity);
        nama = setting.ambil1(SettingID.namaKaryawan);
        persid = setting.ambil1(SettingID.persId);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pres_karyawan_item, parent, false);
        AdapterPresKaryawan.MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String tanggal = arrayList.get(position).getTanggal();
        String masuk = arrayList.get(position).getJamMasuk();
        String pulang = arrayList.get(position).getJamPulang();

        holder.tvTanggal.setText(tanggal);
        holder.tvMasuk.setText(masuk);
        holder.tvPulang.setText(pulang);

        if(masuk == null || masuk.isEmpty()){
            holder.tvMasuk.setVisibility(View.GONE);
            holder.btnMasuk.setVisibility(View.VISIBLE);
            holder.btnPulang.setVisibility(View.GONE);
            holder.tvPulang.setVisibility(View.GONE);
        }
        else if (pulang == null || pulang.isEmpty()){
            holder.tvMasuk.setVisibility(View.VISIBLE);
            holder.btnMasuk.setVisibility(View.GONE);
            holder.btnPulang.setVisibility(View.VISIBLE);
            holder.tvPulang.setVisibility(View.GONE);
        }
        else{
            holder.tvMasuk.setVisibility(View.VISIBLE);
            holder.btnMasuk.setVisibility(View.GONE);
            holder.btnPulang.setVisibility(View.GONE);
            holder.tvPulang.setVisibility(View.VISIBLE);
        }

        holder.btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SqliteSetting setting = new SqliteSetting(activity);
                double latPusat = Double.parseDouble(setting.ambil1(SettingID.latPerusahaan));
                double longPusat = Double.parseDouble(setting.ambil1(SettingID.longPerusahaan));
                final int radius = Integer.parseInt(setting.ambil1(SettingID.radius));
                final Location pusat = new Location("");
                pusat.setLatitude(latPusat);
                pusat.setLongitude(longPusat);

                final ProgressDialog progress = new ProgressDialog(activity);
                progress.setMessage("Loading...");
                progress.show();

                gps.on(new com.google.android.gms.location.LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        progress.dismiss();

                        Log.d("LAT",""+location.getLatitude());
                        Log.d("LONG",""+location.getLongitude());

                        boolean isInLoc = sGPS.isInLocation(pusat,location,radius);

                        if(isInLoc){
                            String jamNow = sDate.getNow("HH:mm:ss");

                            sqlite.simpanKaryawan(persid,nama,tanggal,jamNow,"");
                            arrayList.get(position).setPersId(persid);
                            arrayList.get(position).setNama(nama);
                            arrayList.get(position).setTanggal(tanggal);
                            arrayList.get(position).setJamMasuk(jamNow);

                            holder.tvMasuk.setText(jamNow);
                            holder.tvMasuk.setVisibility(View.VISIBLE);
                            holder.btnMasuk.setVisibility(View.GONE);
                            holder.btnPulang.setVisibility(View.VISIBLE);

                            notifyDataSetChanged();

                            Toasty.success(activity,"Berhasil absen",Toast.LENGTH_LONG,true).show();

                        }else{
                            Toasty.error(activity,"GPS mendeteksi bahwa anda berada diluar radius",Toast.LENGTH_LONG,true).show();
                        }

                        gps.off();

                    }
                });


            }
        });

        holder.btnPulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SqliteSetting setting = new SqliteSetting(activity);
                double latPusat = Double.parseDouble(setting.ambil1(SettingID.latPerusahaan));
                double longPusat = Double.parseDouble(setting.ambil1(SettingID.longPerusahaan));
                final int radius = Integer.parseInt(setting.ambil1(SettingID.radius));
                final Location pusat = new Location("");
                pusat.setLatitude(latPusat);
                pusat.setLongitude(longPusat);

                final ProgressDialog progress = new ProgressDialog(activity);
                progress.setMessage("Loading...");
                progress.show();
                gps.on(new com.google.android.gms.location.LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        progress.dismiss();

                        Log.d("LAT",""+location.getLatitude());
                        Log.d("LONG",""+location.getLongitude());

                        boolean isInLoc = sGPS.isInLocation(pusat,location,radius);

                        if(isInLoc){
                            String jamNow = sDate.getNow("HH:mm:ss");

                            String jamMsk = holder.tvMasuk.getText().toString();
                            sqlite.simpanKaryawan(persid,nama,tanggal,jamMsk,jamNow);
                            arrayList.get(position).setJamPulang(jamNow);

                            holder.tvPulang.setText(jamNow);
                            holder.btnPulang.setVisibility(View.GONE);
                            holder.tvPulang.setVisibility(View.VISIBLE);

                            notifyDataSetChanged();

                            Toasty.success(activity,"Berhasil absen",Toast.LENGTH_LONG,true).show();

                        }else{
                            Toasty.error(activity,"GPS mendeteksi bahwa anda berada diluar radius",Toast.LENGTH_LONG,true).show();
                        }

                        gps.off();

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        if(arrayList!=null){
            return arrayList.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTanggal,tvMasuk,tvPulang;
        Button btnMasuk,btnPulang;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTanggal = itemView.findViewById(R.id.tvTanggalKaryawan);
            tvMasuk = itemView.findViewById(R.id.tvMasukKaryawan);
            tvPulang = itemView.findViewById(R.id.tvPulangKaryawan);

            btnMasuk = itemView.findViewById(R.id.btnMasukKaryawan);
            btnPulang = itemView.findViewById(R.id.btnPulangKaryawan);

        }
    }

}