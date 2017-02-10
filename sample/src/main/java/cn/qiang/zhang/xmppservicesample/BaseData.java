package cn.qiang.zhang.xmppservicesample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * 基础的数据模型
 * <p>
 * Created by mrZQ on 2016/12/19.
 */
public class BaseData implements Serializable {
    private static final long serialVersionUID = -2179369168047042191L;

    /** 默认解析和序列化不存在的字段为null，打印漂亮格式 */
    protected static final Gson DEFAULT_GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    protected Gson gson() {
        return DEFAULT_GSON;
    }

    @Override
    public String toString() {
        return gson().toJson(this);
    }

}
