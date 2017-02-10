package cn.qiang.zhang.xmppservicesample.utils;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 通用方法集合
 * <p>
 * 将通用的方法静态化，并在这里集中管理维护
 * <p>
 * Created by mrZQ on 2016/10/27.
 */
public final class Util {

    /*服务器存储时间格式*/
    public static final String YMDTHMSSZ = "yyyy-MM-dd'T'HH:mm:sszzz";
    /*全时间*/
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /*年月日时分*/
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /*年月日*/
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    /*月日*/
    public static final String MM_DD = "MM-dd";
    /*时分*/
    public static final String HH_MM_A = "hh:mm a";
    /*星期*/
    public static final String EEEE = "EEEE";

    public static final SimpleDateFormat YMDHM_ZZZ =
            new SimpleDateFormat(YMDTHMSSZ, Locale.getDefault());

    public static final SimpleDateFormat ALL_TIME =
            new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.getDefault());
    public static final SimpleDateFormat Y_M_D_H_M =
            new SimpleDateFormat(YYYY_MM_DD_HH_MM, Locale.getDefault());
    public static final SimpleDateFormat YEAR_MONTH_DAY =
            new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault());
    public static final SimpleDateFormat MONTH_DAY =
            new SimpleDateFormat(MM_DD, Locale.getDefault());
    public static final SimpleDateFormat HOUR_MINUTE_MARKER =
            new SimpleDateFormat(HH_MM_A, Locale.getDefault());
    public static final SimpleDateFormat WEEK =
            new SimpleDateFormat(EEEE, Locale.getDefault());

    /** 本地时区 */
    public static TimeZone timeZoneDefault = TimeZone.getDefault();
    /** 北京时区 */
    public static TimeZone timeZoneBeiJing = TimeZone.getTimeZone("GMT+8");
    /** 24小时的毫秒值 */
    public static final long HOURS_24 = TimeUnit.HOURS.toMillis(24);

    ///////////////////////////////////////////////////////////////////////////
    // 格式化本地时区和北京时区的方法
    ///////////////////////////////////////////////////////////////////////////

    public static String defaultAll(Date date) {
        ALL_TIME.setTimeZone(timeZoneDefault);
        return ALL_TIME.format(date);
    }

    public static String defaultYMD(Date date) {
        YEAR_MONTH_DAY.setTimeZone(timeZoneDefault);
        return YEAR_MONTH_DAY.format(date);
    }

    public static String defaultMD(Date date) {
        MONTH_DAY.setTimeZone(timeZoneDefault);
        return MONTH_DAY.format(date);
    }

    public static String defaultHM(Date date) {
        HOUR_MINUTE_MARKER.setTimeZone(timeZoneDefault);
        return HOUR_MINUTE_MARKER.format(date);
    }

    public static String beijingAll(Date date) {
        ALL_TIME.setTimeZone(timeZoneBeiJing);
        return ALL_TIME.format(date);
    }

    public static String beijingYMD(Date date) {
        YEAR_MONTH_DAY.setTimeZone(timeZoneBeiJing);
        return YEAR_MONTH_DAY.format(date);
    }

    public static String beijingMD(Date date) {
        MONTH_DAY.setTimeZone(timeZoneBeiJing);
        return MONTH_DAY.format(date);
    }

    public static String beijingHM(Date date) {
        HOUR_MINUTE_MARKER.setTimeZone(timeZoneBeiJing);
        return HOUR_MINUTE_MARKER.format(date);
    }

    private Util() { /*no instance*/ }

    /***
     * 通过xyz找到旋转的屏幕方向——目前是给加速传感器监听回调使用
     * @return int 切换到——0:横屏竖立放置 1:竖立放置 2:水平放置
     */
    public static int getMaxDimenFactor(float x, float y, float z) {
        if ((Math.abs(x) > Math.abs(y)) && (Math.abs(x) > Math.abs(z))) {
            return 0;
        } else if ((Math.abs(y) > Math.abs(x)) && (Math.abs(y) > Math.abs(z))) {
            return 1;
        } else if ((Math.abs(z) > Math.abs(y)) && (Math.abs(z) > Math.abs(x))) {
            return 2;
        }
        return 2;
    }

    /** 服务器时间戳 */
    public static long serverTimeStamp = 0;

    /** 更新当前服务器时间戳 */
    public static void setServerTimeStamp(long newTime) {
        serverTimeStamp = newTime;
    }

    /**
     * @return 服务器当前时间(ms)
     */
    public static long getServerTime() {
        if (serverTimeStamp == 0) {
            return System.currentTimeMillis();
        }
        return serverTimeStamp + SystemClock.elapsedRealtime();
    }

    /**
     * @param timeStamp 时间戳，分服务器返回的时间戳和本地创建的时间戳
     * @return 距离时间戳的日期字符串说明
     */
    public static String timeStamp(long timeStamp, boolean isServerTimeStamp) {
        // 相差天数
        int days;
        // 默认本地时间
        long serverCurrentTime = System.currentTimeMillis();
        // 如果需要取出服务器当前时间
        if (isServerTimeStamp) {
            serverCurrentTime = getServerTime();
        }
        // （服务器当前时间或本地当前时间 - 事件时间） / 24小时 = 相差天数
        days = (int) ((serverCurrentTime - timeStamp) / HOURS_24);
        String timeString = "";
        if (days == 0) {
            timeString = "今天";
        } else if (days == 1) {
            timeString = "昨天";
        } else if (days >= 2 && days < 7) {
            timeString = WEEK.format(new Date(timeStamp));
        } else if (days >= 7 && days <= 365) {
            return timeString + defaultMD(new Date(timeStamp));
        } else {
            return timeString + defaultYMD(new Date(timeStamp));
        }
        return timeString + defaultHM(new Date(timeStamp));
    }

    //获得独一无二的Psuedo ID
    public static String getUniquePsuedoID() {

        String serial;
        String m_szDevIDShort; //13 位

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            m_szDevIDShort = "35" +
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.SUPPORTED_ABIS[0].length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 +
                    Build.HOST.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 +
                    Build.TYPE.length() % 10 +
                    Build.USER.length() % 10;
        } else {
            //noinspection deprecation
            m_szDevIDShort = "35" +
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.CPU_ABI.length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 +
                    Build.HOST.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 +
                    Build.TYPE.length() % 10 +
                    Build.USER.length() % 10;
        }

        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return Constant.XMPP_START + new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return Constant.XMPP_START + new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    public static String encode(String string) {
        byte[] mByte = Base64.encode(string.getBytes(), Base64.DEFAULT);
        return new String(mByte);
    }

    public static String decode(String string) {
        byte[] mByte = Base64.decode(string.getBytes(), Base64.DEFAULT);
        return new String(mByte);
    }

    /*类转字符串*/
    public static String obj2Str(Object obj) throws IOException {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.flush();
        // 注意：Base64.NO_WRAP表示省略所有换行符
        // Base64.DEFAULT表示一般超过76字符换行一次
        // 这里只在内部使用，采用默认的模式Base64.DEFAULT加密
        String listString = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        oos.close();
        baos.close();
        return listString;
    }

    //将序列化的数据还原成Object
    public static Object str2Obj(String str) throws IOException {
        byte[] mByte = Base64.decode(str.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(mByte);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object obj = null;
        try {
            obj = ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ois.close();
        bais.close();
        return obj;
    }

    public static void hideKeyboard(Context mContext, View view) {
        InputMethodManager imm = ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public static void showKeyboard(Context mContext, View view) {
        InputMethodManager imm = ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }


}
