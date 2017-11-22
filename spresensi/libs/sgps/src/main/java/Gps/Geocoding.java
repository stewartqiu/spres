package Gps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

/**
 * Created by Jekky on 28/10/2017.
 */

public class Geocoding {

    Address address;

    public Geocoding(Context context, double latitude , double longitude)   {

        Geocoder geocoder = new Geocoder(context);
        List<Address> listAddress = null;
        try {
            listAddress = geocoder.getFromLocation(latitude,longitude,1);
            address = listAddress.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getNegara (){
        return address.getCountryName();
    }

    public String getProvinsi (){
        return address.getAdminArea();
    }

    public String getKota (){
        return address.getSubAdminArea();
    }

    public String getFull (){
        return address.getAddressLine(0);
    }

}
