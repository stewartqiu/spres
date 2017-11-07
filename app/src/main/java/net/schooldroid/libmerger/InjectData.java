package net.schooldroid.libmerger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.schooldroid.spresensi.Utils.DataKehadiran;
import net.schooldroid.spresensi.Utils.SettingID;
import net.schooldroid.ssetting.Setting.SqliteSetting;

public class InjectData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inject_data);

        //TODO INJECT KARYAWAN
        SqliteSetting setting = new SqliteSetting(this);

        if(setting.ambil() == null || setting.ambil().isEmpty()) {
            setting.simpan(SettingID.folderKehadiran, "Riau/Pekanbaru");
            setting.simpan(SettingID.latPerusahaan, "0.5372369");
            setting.simpan(SettingID.longPerusahaan, "101.46958");
            setting.simpan(SettingID.radius, "20");
            setting.simpan(SettingID.persId, "88888888");
            setting.simpan(SettingID.namaKaryawan, "Andi");

            //TODO INJECT PIMPINAN]
           /* DataKehadiran dataKehadiran = new DataKehadiran(this);
            dataKehadiran.simpanPimpinan("0", "123456", "Budi", "04-11-2017", "08:00", "09:00");
            dataKehadiran.simpanPimpinan("0", "213546", "Sudi", "04-11-2017", "08:00", "09:00");*/
        }

        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
