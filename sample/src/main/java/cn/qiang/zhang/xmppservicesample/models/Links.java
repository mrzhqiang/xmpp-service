package cn.qiang.zhang.xmppservicesample.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cn.qiang.zhang.xmppservicesample.BaseData;


/**
 * HAL-JSON返回的_links字段，在这里进行相关解析
 * <p>
 * Created by mrZQ on 2017/2/8.
 */
class Links extends BaseData {
    private static final long serialVersionUID = -7781972161177939354L;

    ///////////////////////////////////////////////////////////////////////////
    // links链接的Key，因为是服务器动态生成，所以这里存一下常量
    ///////////////////////////////////////////////////////////////////////////
    /* 规范的资源链接 */
    // 自身
    private static final String SELF = "self";
    // 上一页
    private static final String PREV = "prev";
    // 下一页
    private static final String NEXT = "next";
    // 初始页
    private static final String FIRST = "first";
    // 最后一页
    private static final String LAST = "last";
    /* 自定义的资源链接 */
    // 头像
    private static final String AVATAR = "avatars";
    // 短信
    private static final String SMS_CODE = "smscode";
    // 鉴权
    private static final String TOKEN = "token";
    // 标签
    private static final String LABELS = "labels";

    // 动态生成的_links
    Map<String, Href> _links;

    Map<String, Href> getLinks() {
        if (_links == null) {
            _links = new HashMap<>();
        }
        return _links;
    }

    public String self() {
        return check(SELF);
    }

    public String prev() {
        return check(PREV);
    }

    public String next() {
        return check(NEXT);
    }

    public String first() {
        return check(FIRST);
    }

    public String last() {
        return check(LAST);
    }

    public String avatarPath() {
        return check(AVATAR);
    }

    public String smscodePath() {
        return check(SMS_CODE);
    }

    public String labelPath() {
        return check(LABELS);
    }

    public String tokenPath() {
        return check(TOKEN);
    }

    private String check(@NonNull String keyStr) {
        if (_links != null && !_links.isEmpty()) {
            Href href = _links.get(keyStr);
            if (href != null) {
                return href.href;
            }
        }
        return "";
    }

    /**
     * HAL-JSON的_links字段中有"self"等资源链接，这里是用于跳转到相对于host的某个路径。
     * <p>
     * Created by mrZQ on 2016/12/12.
     */
    private static class Href implements Serializable {
        private static final long serialVersionUID = -997033041016410389L;

        String href;
    }

}
