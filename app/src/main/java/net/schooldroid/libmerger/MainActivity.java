package net.schooldroid.libmerger;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import net.schooldroid.spresensi.Karyawan.sPresensiKaryawan;
import net.schooldroid.spresensi.Pimpinan.sPresensiPimpinan;
import net.schooldroid.spresensi.Utils.ObjKehadiran;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btmNav();
    }

    private void btmNav (){

        BottomNavigationViewEx btm = (BottomNavigationViewEx) findViewById(R.id.btm);
        btm.setTextSize(9);
        btm.enableItemShiftingMode(false);
        btm.enableShiftingMode(false);
        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction t = manager.beginTransaction();
                switch (item.getItemId()){
                    case R.id.frag1:
                        sPresensiKaryawan kar = new sPresensiKaryawan();
                        t.replace(R.id.content,kar).commit();
                        kar.setOnAbsentListener(new sPresensiKaryawan.OnAbsenListener() {
                            @Override
                            public void onAbsen(ObjKehadiran data) {
                                Log.d("LISTENER",data.getJamMasuk());
                                Log.d("LISTENER",data.getJamPulang());
                            }
                        });
                        return true;
                    case R.id.frag2:
                        sPresensiPimpinan pim = new sPresensiPimpinan();
                        t.replace(R.id.content,pim).commit();
                        return true;
                }

                return false;
            }
        });
        btm.setSelectedItemId(R.id.frag2);

    }
}
