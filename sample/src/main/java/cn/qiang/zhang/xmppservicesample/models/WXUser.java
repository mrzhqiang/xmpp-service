package cn.qiang.zhang.xmppservicesample.models;


import cn.qiang.zhang.xmppservicesample.BaseData;

/**
 * 微信用户资料
 * <p>
 * Created by mrZQ on 2016/12/14.
 */
public final class WXUser extends BaseData {
    private static final long serialVersionUID = 7564177824153804534L;

    /*正常返回*/
    private String openid;
    private String nickname;
    private int sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String[] privilege;
    private String unionid;
    private String language;
    /*错误返回*/
    private String errcode;
    private String errmsg;

    public String openId() {
        return openid;
    }

    public String nickname() {
        return nickname;
    }

    public String sex() {
        return sex == 1 ? "男" : "女";
    }

    public String province() {
        return province;
    }

    public String city() {
        return city;
    }

    public String country() {
        return country;
    }

    public String headImgUrl() {
        return headimgurl;
    }

    public String[] privilege() {
        return privilege;
    }

    public String unionId() {
        return unionid;
    }

    public String errCode() {
        return errcode;
    }

    public String errMsg() {
        return errmsg;
    }

    public static WXUser fromJson(String json) {
        return DEFAULT_GSON.fromJson(json, WXUser.class);
    }
}
