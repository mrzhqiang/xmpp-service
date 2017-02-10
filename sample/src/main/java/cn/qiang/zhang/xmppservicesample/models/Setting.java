package cn.qiang.zhang.xmppservicesample.models;


import cn.qiang.zhang.xmppservicesample.BaseData;

/**
 * 设置
 * <p>
 * Created by mrZQ on 2017/1/12.
 */
public final class Setting extends BaseData {
    private static final long serialVersionUID = 3924388796864643869L;

    private int matching = 0;

    public int getMatching() {
        return matching;
    }

    public void setMatching(int matching) {
        this.matching = matching;
    }

    public Setting clear() {
        matching = 0;
        return this;
    }

    public static Setting fromJson(String json) {
        return DEFAULT_GSON.fromJson(json, Setting.class);
    }
}
