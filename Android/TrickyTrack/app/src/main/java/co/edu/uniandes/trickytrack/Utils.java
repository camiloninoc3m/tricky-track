package co.edu.uniandes.trickytrack;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import co.edu.uniandes.trickytrack.activities.PromPlaceActivity;

public class Utils {


    @SuppressLint("MissingPermission")
    public static String getPhone(Activity activity) {
        TelephonyManager tMgr = (TelephonyManager)
                activity.getSystemService(Context.TELEPHONY_SERVICE);

        String MyPhoneNumber = "3115321435";

        try {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request se missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return TODO;
            }
            MyPhoneNumber = tMgr.getLine1Number();
        } catch (NullPointerException ex) {
        }

        if (MyPhoneNumber.equals("")) {
            MyPhoneNumber = tMgr.getSubscriberId();
        }

        return MyPhoneNumber != null && MyPhoneNumber.length() > 2 ? MyPhoneNumber.substring(3) : null;
        //return MyPhoneNumber;
    }
}
