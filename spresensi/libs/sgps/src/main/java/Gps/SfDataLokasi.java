package Gps;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mac1 on 9/15/17.
 */

public class SfDataLokasi {

    private SharedPreferences sf;
    private SharedPreferences.Editor editor;
    private String sfDataLokasi = "Data Lokasi";
    private String sfLatitude = "Lokasi Latitude";
    private String sfLongitude = "Lokasi Longitude";
    private String sfProvinsi = "Lokasi Provinsi";
    private String sfKota = "Lokasi Kota";
    private String sfNegara = "Lokasi Negara";
    private Context context;

    public SfDataLokasi(Context context) {
        sf = context.getSharedPreferences(sfDataLokasi, Context.MODE_PRIVATE);
        editor = sf.edit();
        editor.apply();
    }

    public void save(double latitude, double longitude){

        editor.putString(sfLatitude,""+latitude);
        editor.putString(sfLongitude,""+longitude);

        Geocoding geocoding = new Geocoding(context,latitude,longitude);
        String negara = geocoding.getNegara();
        String provinsi = geocoding.getProvinsi();
        String kota = geocoding.getKota();

        editor.putString(sfProvinsi,provinsi);
        editor.putString(sfKota,kota);
        editor.putString(sfNegara,negara);
        editor.apply();
    }

    public String getProv(){
        String prov = sf.getString(sfProvinsi,"");
        if(prov.isEmpty()){
            double lat = Double.parseDouble(sf.getString(sfLatitude,""));
            double longt = Double.parseDouble(sf.getString(sfLongitude,""));

            Geocoding geocoding = new Geocoding(context,lat,longt);
            editor.putString(sfProvinsi,geocoding.getProvinsi());
            editor.apply();
            return geocoding.getProvinsi();
        }
        return prov;
    }

    public String getKota(){
        String kota = sf.getString(sfKota,"");
        if(kota.isEmpty()){
            double lat = Double.parseDouble(sf.getString(sfLatitude,""));
            double longt = Double.parseDouble(sf.getString(sfLongitude,""));

            Geocoding geocoding = new Geocoding(context,lat,longt);
            editor.putString(sfKota,geocoding.getKota());
            editor.apply();
            return geocoding.getKota();
        }
        return kota;
    }

    public String getNegara(){
        String negara = sf.getString(sfNegara,"");
        if(negara.isEmpty()){
            double lat = Double.parseDouble(sf.getString(sfLatitude,""));
            double longt = Double.parseDouble(sf.getString(sfLongitude,""));

            Geocoding geocoding = new Geocoding(context,lat,longt);
            editor.putString(sfNegara,geocoding.getNegara());
            editor.apply();
            return geocoding.getNegara();
        }
        return negara;
    }

}
