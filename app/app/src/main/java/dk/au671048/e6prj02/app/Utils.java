package dk.au671048.e6prj02.app;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.util.List;

public class Utils {
    private static final String TAG = "Utils";
    public static final Gson GSON = new Gson();
    public static final DateFormat DATE_FORMAT = DateFormat.getInstance();
    public static Geocoder GEOCODER;

    public static final int ADDRESS_SHORT = 0;
    public static final int ADDRESS_LONG = 1;

    public static int getWarningIconID(String type) {
        int id = R.drawable.ic_baseline_announcement_24;
        switch (type) {
            case "battery":
                id = R.drawable.ic_baseline_battery_alert_24;
                break;
            case "fullness":
                id = R.drawable.ic_baseline_delete_forever_24;
                break;
            default:
                break;
        }
        return id;
    }

/*
 * https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
 */
    public static String capitalize(String str) {
        if (null == str) {
            return null;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

/*
 * https://www.rishabhsoft.com/blog/android-geocoding-and-reverse-geocoding
 */
    public static String getAddressFromLocation(double latitude, double longitude) {
        return getAddressFromLocation(latitude, longitude, ADDRESS_SHORT);
    }

    public static String getAddressFromLocation(double latitude, double longitude, int mode) {
        String resultAddress = null;
        try {
            List<Address> result = GEOCODER.getFromLocation(latitude, longitude, 1);
            Address address = result.get(0);
            Log.d(TAG, "getAddressFromLocation: mode: " + mode + " " + address.toString());
            Log.d(TAG, "getAddressFromLocation: " + address.getThoroughfare() + " " + address.getFeatureName());
            resultAddress = address.getThoroughfare() + " " + address.getFeatureName();
            if (mode == ADDRESS_LONG) {
                resultAddress += ", " + address.getPostalCode() + " " + address.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultAddress;
    }
}
