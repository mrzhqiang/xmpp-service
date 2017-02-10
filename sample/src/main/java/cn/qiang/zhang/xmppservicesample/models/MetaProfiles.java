package cn.qiang.zhang.xmppservicesample.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Created by mrZQ on 2017/2/8.
 */
class MetaProfiles extends Links {
    private static final long serialVersionUID = 4033684097469782409L;

    /** 通常是Map中没有这个Key时的返回值 */
    private static final String NOT_FILLED = "";

    // 如果不希望继承下面两个属性，可以直接实现序列化接口，然后调用 DEFAULT_GSON 或 NOT_FILLED
    /** 配置、描述、简介、档案等 */
    Map<String, String> profiles;
    /** 元数据、额外说明等 */
    Map<String, String> metadata;

    Map<String, String> getProfiles() {
        if (profiles == null) {
            profiles = new HashMap<>();
        }
        return profiles;
    }

    Map<String, String> getMetadata() {
        if (metadata == null) {
            metadata = new HashMap<>();
        }
        return metadata;
    }

    String getProfiles(@NonNull String key) {
        String value = getProfiles().get(key);
        return TextUtils.isEmpty(value) ? NOT_FILLED : value;
    }

    void putProfiles(@NonNull String key, @Nullable String value) {
        getMetadata().put(key, value);
    }

    String getMetadata(@NonNull String key) {
        String value = getMetadata().get(key);
        return TextUtils.isEmpty(value) ? NOT_FILLED : value;
    }

    void putMetadata(@NonNull String key, @Nullable String value) {
        getMetadata().put(key, value);
    }

}
