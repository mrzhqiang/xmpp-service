package cn.qiang.zhang.xmppservicesample.models;


import cn.qiang.zhang.xmppservicesample.BaseData;

/**
 * 微信的鉴权，暂时这样设定
 * <p>
 * Created by mrZQ on 2016/12/13.
 */
public final class WXToken extends BaseData {
    private static final long serialVersionUID = -7006797729222318440L;

    /*正常返回*/
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;
    /*错误返回*/
    private String errcode;
    private String errmsg;
    /*创建时间*/
    private long timeStamp = System.currentTimeMillis();

    public String accessToken() {
        return access_token;
    }

    public String refreshToken() {
        return refresh_token;
    }

    public String openId() {
        return openid;
    }

    public String errMsg() {
        return errmsg;
    }

    public String errCode() {
        return errcode;
    }

    public static WXToken fromJson(String json) {
        return DEFAULT_GSON.fromJson(json, WXToken.class);
    }
}
