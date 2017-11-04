package net.schooldroid.libmerger;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import net.schooldroid.spresensi.Karyawan.sPresensiKaryawan;
import net.schooldroid.spresensi.Pimpinan.sPresensiPimpinan;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btmNav();
    }

    private void btmNav (){

        BottomNavigationViewEx btm = (BottomNavigationViewEx) findViewById(R.id.btm);
        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction t = manager.beginTransaction();
                switch (item.getItemId()){
                    case R.id.frag1:
                        sPresensiKaryawan kar = new sPresensiKaryawan();
                        t.replace(R.id.content,kar).commit();
                        kar.setTitleonToolbar("Presensi");
                        return true;
                    case R.id.frag2:
                        sPresensiPimpinan pim = new sPresensiPimpinan();
                        t.replace(R.id.content,pim).commit();
                        pim.setTitleonToolbar("Presensi");
                        return true;
                }

                return false;
            }
        });
        btm.setSelectedItemId(R.id.frag2);

    }
}
