package net.schooldroid.spresensi.Pimpinan;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.schooldroid.spresensi.R;
import net.schooldroid.spresensi.Utils.ObjKehadiran;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jekky on 03/11/2017.
 */

public class AdapterPresPimpinan extends RecyclerView.Adapter<AdapterPresPimpinan.MyViewHolder> {

    ArrayList<ObjKehadiran> arrayList;
    Activity activity;

    public AdapterPresPimpinan(ArrayList<ObjKehadiran> arrayList, Activity activity) {
        if(arrayList!=null) {
            Collections.sort(arrayList, ObjKehadiran.SortJamMasuk);
        }
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pres_pimpinan_item, parent, false);
        AdapterPresPimpinan.MyViewHolder myViewHolder = new AdapterPresPimpinan.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String id = arrayList.get(position).getNama();
        String nama = arrayList.get(position).getNama();
        String tanggal = arrayList.get(position).getTanggal();
        String jamMasuk = arrayList.get(position).getJamMasuk();
        String jamPulang = arrayList.get(position).getJamPulang();

        holder.tvNama.setText(nama);
        holder.tvMasuk.setText(jamMasuk);
        holder.tvPulang.setText(jamPulang);

    }

    @Override
    public int getItemCount() {
        if(arrayList!=null){
            return arrayList.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama,tvMasuk,tvPulang;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNamaPim);
            tvMasuk = itemView.findViewById(R.id.tvMasukPim);
            tvPulang = itemView.findViewById(R.id.tvPulangPim);

        }
    }

}
