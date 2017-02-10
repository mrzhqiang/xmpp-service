package cn.qiang.zhang.xmppservicesample.utils.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h3>
 * 权限请求依据
 * </h3>
 * <p>
 * Created by mrZQ on 2016/10/28.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionRationale {
    int requestCode();
}
