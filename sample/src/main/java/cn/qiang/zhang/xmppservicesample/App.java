package cn.qiang.zhang.xmppservicesample;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import net.gotev.xmppservice.Logger;
import net.gotev.xmppservice.XmppServiceBroadcastEventEmitter;

import java.util.List;

import cn.qiang.zhang.xmppservicesample.utils.logger.Log;

/**
 * <p>
 * Created by mrZQ on 2017/1/14.
 */
public class App extends Application {

    private static App app;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Logger.setLogLevel(Logger.LogLevel.DEBUG);
        XmppServiceBroadcastEventEmitter.initialize(this, "XMPP_SERVICE_DEMO");
    }


    /** 网络是否存在、是否已连接 */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isAvailable() && ni.isConnected();
    }

    /** Wifi网络是否已连接 */
    public boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Network[] nis = cm.getAllNetworks();
            for (Network n : nis) {
                NetworkInfo ni = cm.getNetworkInfo(n);
                if (ni != null && ni.getType() == ConnectivityManager.TYPE_WIFI) {
                    return ni.isAvailable() && ni.isConnected();
                }
            }
        } else {
            //noinspection deprecation
            NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return ni != null && ni.isAvailable() && ni.isConnected();
        }
        return false;
    }

    /** 判断app是在后台还是在前台运行 */
    public static boolean isBackground() {
        ActivityManager activityManager = (ActivityManager) app.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(app.getPackageName())) {
                /* BACKGROUND=400 EMPTY=500 FOREGROUND=100 GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200 */
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(app.getPackageName(), "处于后台" + appProcess.processName);
                    return true;
                } else {
                    Log.i(app.getPackageName(), "处于前台" + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }
}
