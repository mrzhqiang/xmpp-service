package cn.qiang.zhang.xmppservicesample.models;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import cn.qiang.zhang.xmppservicesample.BaseData;


/**
 * 身份令牌
 * <p>
 * Created by mrZQ on 2016/11/16.
 */
public final class Token extends BaseData {
    private static final long serialVersionUID = 4661916330457012299L;
    /* Token认证的前缀 */
    private static final String TOKEN_BEARER = "Bearer ";

    @SerializedName(value = "userId", alternate = {"user_id", "user_Id"})
    private long userId;
    @SerializedName(value = "accessToken", alternate = {"access_token", "access_Token"})
    private String accessToken;
    @SerializedName(value = "tokenType", alternate = {"token_type", "token_Type"})
    private String tokenType;
    @SerializedName(value = "expiresIn", alternate = {"expires_in", "expires_In"})
    private long expiresIn;
    @SerializedName(value = "refreshToken", alternate = {"refresh_token", "refresh_Token"})
    private String refreshToken;

    /** 取得登录成功后返回的Token中的用户id */
    long userId() {
        return userId;
    }

    /** 检查用户的登录状态 */
    boolean check(long id) {
        return this.userId == id && !TextUtils.isEmpty(accessToken);
    }

    public String getAccessToken() {
        return accessToken;
    }

    /** 取得用户的认证权限 */
    public String author() {
        return TOKEN_BEARER + accessToken;
    }

    void replace(Token token) {
        this.userId = token.userId;
        this.accessToken = token.accessToken;
        this.tokenType = token.tokenType;
        this.expiresIn = token.expiresIn;
        this.refreshToken = token.refreshToken;
    }

}
