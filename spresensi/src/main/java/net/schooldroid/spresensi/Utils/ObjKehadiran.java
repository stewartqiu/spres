package net.schooldroid.spresensi.Utils;

import java.util.Comparator;

/**
 * Created by Jekky on 30/10/2017.
 */

public class ObjKehadiran {

    String persId, nama, tanggal, jamMasuk, jamPulang;

    public ObjKehadiran(){

    }

    public ObjKehadiran(String persId, String nama, String tanggal, String jamMasuk, String jamPulang) {
        this.persId = persId;
        this.nama = nama;
        this.tanggal = tanggal;
        this.jamMasuk = jamMasuk;
        this.jamPulang = jamPulang;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJamMasuk() {
        return jamMasuk;
    }

    public void setJamMasuk(String jamMasuk) {
        this.jamMasuk = jamMasuk;
    }

    public String getJamPulang() {
        return jamPulang;
    }

    public void setJamPulang(String jamPulang) {
        this.jamPulang = jamPulang;
    }

    public String getPersId() {
        return persId;
    }

    public void setPersId(String persId) {
        this.persId = persId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }


    public static Comparator<ObjKehadiran> SortTgl = new Comparator<ObjKehadiran>() {
        @Override
        public int compare(ObjKehadiran o1, ObjKehadiran o2) {
            String urut1 = o1.getTanggal().toUpperCase();
            String urut2 = o2.getTanggal().toUpperCase();

            return urut1.compareTo(urut2);
        }
    };

    public static Comparator<ObjKehadiran> SortTglTerbalik = new Comparator<ObjKehadiran>() {
        @Override
        public int compare(ObjKehadiran o1, ObjKehadiran o2) {
            String urut1 = o1.getTanggal().toUpperCase();
            String urut2 = o2.getTanggal().toUpperCase();

            return urut2.compareTo(urut1);
        }
    };

    public static Comparator<ObjKehadiran> SortJamMasuk = new Comparator<ObjKehadiran>() {
        @Override
        public int compare(ObjKehadiran o1, ObjKehadiran o2) {
            String urut1 = o1.getJamMasuk().toUpperCase();
            String urut2 = o2.getJamMasuk().toUpperCase();

            return urut1.compareTo(urut2);
        }
    };

}
