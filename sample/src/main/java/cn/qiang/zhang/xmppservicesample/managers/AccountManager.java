package cn.qiang.zhang.xmppservicesample.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import cn.qiang.zhang.xmppservicesample.models.Setting;
import cn.qiang.zhang.xmppservicesample.models.UserLocal;
import cn.qiang.zhang.xmppservicesample.models.WXToken;
import cn.qiang.zhang.xmppservicesample.models.WXUser;
import cn.qiang.zhang.xmppservicesample.utils.Util;


/**
 * 账户管理
 * <p>
 * Created by mrZQ on 2016/11/15.
 */
public final class AccountManager {
    private static final String TAG = "AccountManager";

    private static final String KEY_TA_USER = "touchat_user";
    private static final String KEY_SETTING = "touchat_setting";
    private static final String KEY_WX_TOKEN = "wx_token";
    private static final String KEY_WX_USER = "wx_user";

//    private static IWXAPI api;

    private Setting setting;
    private UserLocal user;
    private WXUser wxUser;
    private WXToken wxToken;

    private SharedPreferences msp;

    private static class HOLDER {
        private static AccountManager holder = new AccountManager();
    }

    /** 获取微信接口 */
//    public static IWXAPI getWXApi() {
//        return api;
//    }

    public static void init(Context context) {
        HOLDER.holder.create(context);
    }

    public static AccountManager getInstance() {
        return HOLDER.holder;
    }

    public synchronized void save(@NonNull UserLocal user) {
        msp.edit().putString(KEY_TA_USER, Util.encode(user.toString())).apply();
        this.user = user;
    }

    public synchronized void save(@NonNull WXUser wxUser) {
        msp.edit().putString(KEY_WX_USER, Util.encode(wxUser.toString())).apply();
        this.wxUser = wxUser;
    }

    public synchronized void save(@NonNull WXToken wxToken) {
        msp.edit().putString(KEY_WX_TOKEN, Util.encode(wxToken.toString())).apply();
        this.wxToken = wxToken;
    }

    public synchronized void save(@NonNull Setting setting) {
        msp.edit().putString(KEY_SETTING, Util.encode(setting.toString())).apply();
        this.setting = setting;
    }

    /** 清理设置 */
    public void clearSetting() {
        if (setting != null) {
            save(setting.clear());
        } else {
            save(new Setting());
        }
    }

    /** 获取匹配设置 */
    public int getMatching() {
        checkSetting();
        return setting.getMatching();
    }

    public UserLocal clearUser() {
        if (user != null) {
            save(user.clear());
        } else {
            save(new UserLocal());
        }
        return this.user;
    }

    public void clearWX() {
        msp.edit().remove(KEY_WX_USER).remove(KEY_WX_TOKEN).apply();
    }

    public synchronized Setting setting() {
        if (setting == null) {
            String str = msp.getString(KEY_SETTING, "");
            if (!str.isEmpty()) {
                setting = Setting.fromJson(Util.decode(str));
            } else {
                setting = new Setting();
            }
        }
        return setting;
    }

    /** 获取用户资料 */
    public synchronized UserLocal user() {
        if (user == null) {
            String str = msp.getString(KEY_TA_USER, "");
            if (!str.isEmpty()) {
                user = UserLocal.fromJson(Util.decode(str));
            } else {
                user = new UserLocal();
            }
        }
        return user;
    }

    /** 获取微信用户资料 */
    public synchronized WXUser wxUser() {
        if (wxUser == null) {
            String str = msp.getString(KEY_WX_USER, "");
            if (!str.isEmpty()) {
                wxUser = WXUser.fromJson(Util.decode(str));
            } else {
                wxUser = new WXUser();
            }
        }
        return wxUser;
    }

    /** 获取微信鉴权 */
    public synchronized WXToken wxToken() {
        if (wxToken == null) {
            String str = msp.getString(KEY_WX_TOKEN, "");
            if (!str.isEmpty()) {
                wxToken = WXToken.fromJson(Util.decode(str));
            } else {
                wxToken = new WXToken();
            }
        }
        return wxToken;
    }

    /** 检查登录状态 */
    public boolean checkSignIn() {
        return user == null || user.checkStatus();
    }

    private void create(Context context) {
        synchronized (AccountManager.class) {
            if (msp == null) {
                msp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
            }
            checkUser();
            checkSetting();
            checkWXUser();
            checkWXToken();
//            initWX(context);
        }
    }

    /** 检查设置 */
    private void checkSetting() {
        if (setting == null) {
            setting();
        }
    }

    private void checkUser() {
        if (user == null) {
            user();
        }
    }

    private void checkWXUser() {
        if (wxUser == null) {
            wxUser();
        }
    }

    private void checkWXToken() {
        if (wxToken == null) {
            wxToken();
        }
    }

    private void initWX(Context context) {
//        if (api == null) {
//            api = WXAPIFactory.createWXAPI(context, Constant.WX_APP_ID);
//            if (api.registerApp(Constant.WX_APP_ID)) {
//                Log.d(TAG, "WXAPIFactory register App successful");
//            }
//        }
    }

}
