package cn.qiang.zhang.xmppservicesample.utils.permission;

import android.Manifest;
import android.annotation.SuppressLint;


/**
 * 权限请求常量类
 * <p>
 * 在这里，将隐私权限进行分类，并每个分组对应一个请求代码。
 * <p>
 * Created by mrZQ on 2016/10/26.
 */
public class PermissionConst {

    /** 权限请求代码基础数值 */
    private static final int REQUEST_BASE_CODE = 100;

    /** 日历——请求代码 */
    public static final int REQUEST_CALENDAR = REQUEST_BASE_CODE + 1;
    /** 日历——权限常量 */
    public static final String[] CALENDAR = {
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
    };

    /** 照相机——请求代码 */
    public static final int REQUEST_CAMERA = REQUEST_BASE_CODE + 2;
    /** 照相机——权限常量 */
    public static final String[] CAMERA = {
            Manifest.permission.CAMERA
    };

    /** 通讯录——请求代码 */
    public static final int REQUEST_CONTACTS = REQUEST_BASE_CODE + 3;
    /** 通讯录——权限常量 */
    public static final String[] CONTACTS = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS
    };

    /** 位置——请求代码 */
    public static final int REQUEST_LOCATION = REQUEST_BASE_CODE + 4;
    /** 位置——权限常量 */
    public static final String[] LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    /** 录音——请求代码 */
    public static final int REQUEST_MICROPHONE = REQUEST_BASE_CODE + 5;
    /** 录音——权限常量 */
    public static final String[] MICROPHONE = {
            Manifest.permission.RECORD_AUDIO
    };

    /** 电话——请求代码 */
    public static final int REQUEST_PHONE = REQUEST_BASE_CODE + 6;
    /** 电话——权限常量 */
    @SuppressLint("InlinedApi")
    public static final String[] PHONE = {
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.ADD_VOICEMAIL
    };

    /** 传感器——请求代码 */
    public static final int REQUEST_SENSORS = REQUEST_BASE_CODE + 7;
    /** 传感器——权限常量 */
    @SuppressLint("InlinedApi")
    public static final String[] SENSORS = {
            Manifest.permission.BODY_SENSORS
    };

    /** 信息——请求代码 */
    public static final int REQUEST_SMS = REQUEST_BASE_CODE + 8;
    /** 信息——权限常量 */
    public static final String[] SMS = {
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
//            Manifest.permission.READ_CELL_BROADCASTS
    };

    /** 存储——请求代码 */
    public static final int REQUEST_STORAGE = REQUEST_BASE_CODE + 9;
    /** 存储——权限常量 */
    @SuppressLint("InlinedApi")
    public static final String[] STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

}
