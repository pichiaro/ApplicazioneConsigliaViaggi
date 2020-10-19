package utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class NetworkUtility {

    public static boolean isWifiEnabled (Context context) {
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (!wifiManager.isWifiEnabled()) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isOnlyWifiEnabled(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                int type = networkInfo.getType();
                if (type == ConnectivityManager.TYPE_MOBILE) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNetworkEnabled(Context context) {
        return (NetworkUtility.isOnlyWifiEnabled(context) || NetworkUtility.isWifiEnabled(context));
    }

}
