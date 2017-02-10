package cn.qiang.zhang.xmppservicesample.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that wraps access to the runtime permissions API in M and provides basic helper
 * methods.
 * <p>
 * Created by mrZQ on 2016/10/26.
 */
public class PermissionUtil {

    /**
     * It can not be outside instantiation, Through the static way call methods
     */
    private PermissionUtil() { /*can not be outside instantiation*/ }

    /**
     * 检查是否对某个权限分组授权
     * @param context    上下文
     * @param permission 权限分组，注意这里不能是单个不相关的权限，将无法正确判断
     * @return true 当前分组权限已全部获得；false 有未被获取的权限
     */
    public static boolean checkPermissions(Context context, String... permission) {
        for (String value : permission) {
            int permissionState = ContextCompat.checkSelfPermission(context, value);
            if (permissionState != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查权限，找出未被授权的列表
     * @param activity   当前活动
     * @param permission 权限列表
     * @return 未被授权的权限列表
     */
    public static List<String> getDeniedPermissions(Activity activity, String... permission) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            int permissionState = ContextCompat.checkSelfPermission(activity, value);
            if (permissionState != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    /**
     * 根据已知注解类型，判断是否为匹配的请求代码
     * @param m           方法信息
     * @param clazz       类信息
     * @param requestCode 请求代码
     * @return true 匹配请求代码；false 不匹配请求代码
     */
    private static boolean isEqualRequestCodeFromAnnotation(Method m, Class clazz, int
            requestCode) {
        if (clazz.equals(PermissionFail.class)) {
            return requestCode == m.getAnnotation(PermissionFail.class).requestCode();
        } else if (clazz.equals(PermissionSuccess.class)) {
            return requestCode == m.getAnnotation(PermissionSuccess.class).requestCode();
        } else if (clazz.equals(PermissionRationale.class)) {
            return requestCode == m.getAnnotation(PermissionRationale.class).requestCode();
        }
        return false;
    }

    /**
     * 从请求代码中找到对应方法
     * <p>
     * 先遍历clazz中的所有方法，再匹配对应注解的方法，如果方法、注解和代码全部匹配，则返回这个方法
     * @param clazz       持有对应注解接口的类.class
     * @param annotation  注解接口
     * @param requestCode 请求代码
     * @param <A>         限定为Annotation边界的泛型
     * @return 方法类
     */
    static <A extends Annotation> Method findMethodWithRequestCode(
            Class clazz,
            Class<A> annotation, int requestCode) {
        // 遍历类中所有方法——需要包括继承的方法
        for (Method method : clazz.getMethods()) {
            // 当前方法有提出此种注解
            if (method.isAnnotationPresent(annotation)) {
                // 来自成功或失败、依据方法且匹配请求代码
                if (isEqualRequestCodeFromAnnotation(method, annotation, requestCode)) {
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 获得当前Activity
     * <p>
     * 1.对于Fragment，先进行类型转换，然后通过get方法拿到
     * 2.对于Activity，类型转换后返回
     * 3.对于其他类型，返回null
     * @param object 对象
     * @return 活动
     */
    public static Activity getActivity(Object object) {
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof Activity) {
            return (Activity) object;
        }
        return null;
    }
}
