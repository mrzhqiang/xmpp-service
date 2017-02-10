package cn.qiang.zhang.xmppservicesample.managers;

import android.content.Context;
import android.content.Intent;

import net.gotev.xmppservice.XmppAccount;
import net.gotev.xmppservice.XmppService;
import net.gotev.xmppservice.XmppServiceBroadcastEventEmitter;
import net.gotev.xmppservice.XmppServiceBroadcastEventReceiver;
import net.gotev.xmppservice.XmppServiceCommand;

import cn.qiang.zhang.xmppservicesample.models.UserLocal;
import cn.qiang.zhang.xmppservicesample.utils.Constant;
import cn.qiang.zhang.xmppservicesample.utils.Util;
import cn.qiang.zhang.xmppservicesample.utils.logger.Log;

/**
 * XMPP 管理器
 * <p>
 * Created by mrZQ on 2017/1/3.
 */
public final class XmppManager extends XmppServiceBroadcastEventReceiver {
    private static final String TAG = "XmppManager";

    private static XmppAccount account;

    public static void init(Context context) {
        XmppServiceBroadcastEventEmitter.initialize(context, "TOUCHAT");
    }

    public static void start(Context context) {
        if (AccountManager.getInstance().checkSignIn()) {
            autonymLogin(context);
        } else {
            // 没有登录则匿名连接到xmpp
            anonymousLogin(context);
        }
    }

    public static XmppAccount getAccount() {
        return account;
    }

    /** 匿名登录 */
    public static void anonymousLogin(Context context) {
        account = new XmppAccount();
        account.setXmppJid(Util.getUniquePsuedoID());
        account.setServiceName(Constant.XMPP_DOMAIN);
        account.setPassword(Constant.XMPP_PASSWORD);
        account.setHost(Constant.XMPP_HOST);
        account.setPort(Integer.parseInt(Constant.XMPP_PROT));
//		account.setPriority();
//		account.setResourceName();
        account.setPersonalMessage("匿名用户");
        account.setPresenceMode(XmppAccount.PRESENCE_MODE_AVAILABLE);
        XmppServiceCommand.connect(context, account);
    }

    /** 实名登录 */
    public static void autonymLogin(Context context) {
        if (AccountManager.getInstance().checkSignIn()) {
            UserLocal user = AccountManager.getInstance().user();
            if (XmppService.isConnected()) {
                Log.d(TAG, "Xmpp disconnecting");
                XmppServiceCommand.disconnect(context);
            }
            account = new XmppAccount();
            String userId = String.valueOf(user.getId());
            String token = user.getToken().getAccessToken();
            account.setXmppJid("user_" + userId);
            account.setServiceName(Constant.XMPP_DOMAIN);
            account.setPassword(token);
            account.setHost(Constant.XMPP_HOST);
            account.setPort(Integer.parseInt(Constant.XMPP_PROT));
//		account.setPriority();
            account.setResourceName(Util.getUniquePsuedoID());
            account.setPersonalMessage("实名用户");
            account.setPresenceMode(XmppAccount.PRESENCE_MODE_AVAILABLE);
            XmppServiceCommand.connect(context, account);
        } else {
            context.sendBroadcast(new Intent(Constant.ACTION_SHOULD_LOGIN));
        }
    }

}
