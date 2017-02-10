package cn.qiang.zhang.xmppservicesample.models;

import android.support.annotation.NonNull;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.qiang.zhang.xmppservicesample.utils.logger.Log;


/**
 * 本地用户信息、资料，用于保持登录状态即Token
 * <p>
 * Created by mrZQ on 2017/2/8.
 */
public final class UserLocal extends User {
    private static final long serialVersionUID = -3928629856093618253L;

    /** 当前类实例需要排除的属性 */
    private static final String[] EXCLUSION_LIST = new String[]{
            "mobile", "JID", "openId", "password"
    };

    /** 保护隐私的解析和序列化策略 */
    private static final ExclusionStrategy STRATEGY = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            String name = f.getName();
            for (String exclusion : EXCLUSION_LIST) {
                if (name.contains(exclusion)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };

    /** 使用这个实例解析和序列化本地用户信息 */
    private static final Gson LOCAL_GSON = new GsonBuilder()
            // 序列化空字段
            .serializeNulls()
            // 序列化时，排除字段
            .addSerializationExclusionStrategy(STRATEGY)
            // 反序列化时，排除字段
            .addDeserializationExclusionStrategy(STRATEGY)
            // 美化输出
            .setPrettyPrinting()
            .create();

    /* token字段仅供客户端使用 */
    private Token token;

    public Token getToken() {
        if (this.token == null) {
            this.token = new Token();
        }
        return token;
    }

    public void setToken(Token token) {
        this.id = token.userId();
        getToken().replace(token);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 自定义方法，非get set
    ///////////////////////////////////////////////////////////////////////////

    /** 从服务器更新本地用户信息 */
    public void update(@NonNull User newUser) {
        if (newUser.id < 0 || newUser.id != this.id) {
            Log.e("newUser is different from current UserLocal, cannot update!");
            return;
        }
        this.role = newUser.role;
        this.nickName = newUser.nickName;
        this.externalId = newUser.externalId;
        this.mobile = newUser.mobile;
        this.openId = newUser.openId;
        this.avatar = newUser.avatar;
        this.password = newUser.password;
        this.created = newUser.created;
        this.labels = newUser.labels;
        this.JID = newUser.JID;
        this.profiles = newUser.getProfiles();
        this.metadata = newUser.getMetadata();
        this._links = newUser.getLinks();
    }

    /** 清空用户登录信息：手机号和头像不清除，以备后续开发快速登录功能 */
    public UserLocal clear() {
        this.token = null;

        this.role = "";
        this.nickName = "";
        this.externalId = "";
        this.openId = "";
        this.password = "";
        this.created = null;
        this.labels = null;
        this.JID = "";
        this.profiles = null;
        this.metadata = null;
        this._links = null;
        return this;
    }

    /** 检查登录状态 */
    public boolean checkStatus() {
        return token != null && token.check(this.id);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 序列化和解析相关
    ///////////////////////////////////////////////////////////////////////////
    @Override
    protected Gson gson() {
        return LOCAL_GSON;
    }

    /** 把保存在本地的sp文件字符串，解析为对象 */
    public static UserLocal fromJson(String json) {
        return LOCAL_GSON.fromJson(json, UserLocal.class);
    }

}
