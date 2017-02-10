package cn.qiang.zhang.xmppservicesample.utils;

import java.util.List;

/**
 * 抛出异常的便捷工具
 * <p>
 * Created by mrZQ on 2016/12/30.
 */
public final class ThrowUtil {
    private ThrowUtil() {
        // no instance
    }

    /** 要求传入的对象不能是null，否则将抛出空指针异常 */
    public static <T> T nonNull(T obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
        return obj;
    }

    /** 要求访问的索引不能越界，否则将抛出越界异常 */
    public static void indexNonOut(List list, int getIndex) {
        if (getIndex < 0 || (list != null && getIndex >= list.size())) {
            throw new ArrayIndexOutOfBoundsException(
                    list.getClass().getName() + " size: " + list.size() + ", index: " + getIndex);
        }
    }
}
