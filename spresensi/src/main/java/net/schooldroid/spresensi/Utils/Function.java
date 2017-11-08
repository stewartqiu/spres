package net.schooldroid.spresensi.Utils;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by Jekky on 08/11/2017.
 */

public class Function {

    public static boolean isTimeAutomatic(Context c) {
        return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
    }

}
