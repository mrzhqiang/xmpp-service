package cn.qiang.zhang.xmppservicesample.utils.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态权限请求类
 * <p>
 * 使用方法：
 * 1.可以调用{@link #with(Activity)}或{@link #with(Fragment)}创建当前请求类的实例，
 * 调用{@link #permissions(String...)}添加需要请求的权限列表（可以是不同分组的权限），
 * 调用{@link #addRequestCode(int)}添加权限请求代码，最后调用{@link #request()}发起请求。
 * <p>
 * 2.可以直接调用{@link #needPermission(Activity, int, String)}、{@link #needPermission(Fragment, int,
 * String)}获取单个权限；
 * 直接调用{@link #needPermission(Activity, int, String[])}、{@link #needPermission(Fragment, int,
 * String[])}获取多个权限。
 * 注意：并不局限于权限分组，也可以是不同分组的权限。
 * <p>
 * 3.采取上面两种方法中的其中一个，然后重写{@link Activity#onRequestPermissionsResult(int, String[], int[])}
 * 或{@link Fragment#onRequestPermissionsResult(int, String[], int[])}回调，
 * 在super之前调用{@link #onRequestPermissionsResult(Activity, int, String[], int[])}
 * 或{@link #onRequestPermissionsResult(Fragment, int, String[], int[])}。
 * 4.最后在发起请求的Activity或Fragment中创建任意有效方法，建议命名为Successful、Failed和Rationale之类，加上
 * 注解{@link PermissionSuccess}、{@link PermissionFail}、{@link PermissionRationale}即可。
 * <p>
 * 示例代码：
 * <p>
 * 在Activity或Fragment中
 * <pre>
 *     <code>
 * // 第一种
 * Permission.with(this)
 *          .permissions(new String[] {Manifest.permission.CAMERA})
 *          .addRequestCode(100)
 *          .request();
 * // 第二种
 * Permission.needPermission(this, 100, new String[] {Manifest.permission.CAMERA});
 * // 加上注解——前面不需要-
 * -@PermissionSuccess(requestCode = 100)
 * public void perSuccessful(){
 *      Log.d(TAG, "request Permission Successful!");
 * }
 *     </code>
 * </pre>
 * <p>
 * Created by mrZQ on 2016/10/25.
 */
public class Permission {

    /** 权限列表 */
    private String[] mPermissions;
    /** 请求代码 */
    private int mRequestCode;
    /** 活动或碎片对象 */
    private Object object;

    /**
     * 私有构造器
     * @param object 对象——Activity or Fragment
     */
    private Permission(Object object) {
        this.object = object;
    }

    /**
     * 创建——用于Activity
     * @param activity 活动
     * @return this
     */
    public static Permission with(Activity activity) {
        return new Permission(activity);
    }

    /**
     * 创建——用于Fragment
     * @param fragment 碎片
     * @return this
     */
    public static Permission with(Fragment fragment) {
        return new Permission(fragment);
    }

    /**
     * 赋予权限列表
     * @param permissions 需要请求的权限列表
     * @return this
     */
    public Permission permissions(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    /**
     * 添加请求代码
     * @param requestCode 请求代码
     * @return this
     */
    public Permission addRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    /**
     * 开始请求权限
     */
    public void request() {
        requestPermissions(object, mRequestCode, mPermissions);
    }

    /**
     * 另一种请求权限的方法——、多个权限
     * @param activity    活动
     * @param requestCode 请求代码
     * @param permissions 权限列表
     */
    public static void needPermission(Activity activity, int requestCode, String[] permissions) {
        requestPermissions(activity, requestCode, permissions);
    }

    /**
     * 另一种请求权限的方法——碎片、多个权限
     * @param fragment    碎片
     * @param requestCode 请求代码
     * @param permissions 权限列表
     */
    public static void needPermission(Fragment fragment, int requestCode, String[] permissions) {
        requestPermissions(fragment, requestCode, permissions);
    }

    /**
     * 另一种请求权限的方法——活动、单个权限
     * @param activity    活动
     * @param requestCode 请求代码
     * @param permission  单个权限
     */
    @SuppressWarnings("unused")
    public static void needPermission(Activity activity, int requestCode, String permission) {
        needPermission(activity, requestCode, new String[]{permission});
    }

    /**
     * 另一种请求权权限的方法——碎片、单个权限
     * @param fragment    碎片
     * @param requestCode 请求代码
     * @param permission  单个权限
     */
    @SuppressWarnings("unused")
    public static void needPermission(Fragment fragment, int requestCode, String permission) {
        needPermission(fragment, requestCode, new String[]{permission});
    }

    /**
     * 请求权限的方法
     * @param object      对象——Activity or Fragment
     * @param requestCode 请求代码
     * @param permissions 权限列表
     */
    private static void requestPermissions(Object object, int requestCode, String[] permissions) {
        // 得到未被授权的权限列表
        List<String> deniedPermissions = PermissionUtil.getDeniedPermissions(
                PermissionUtil.getActivity(object), permissions);
        // 如果还有未被授权的权限
        if (deniedPermissions.size() > 0) {
            // 请求授权：如果用户拒绝授权并不再询问，调用此方法会立即回调onRequestPermissionsResult
            requestPermissions(object, requestCode, deniedPermissions);
        } else {
            // 调用成功方法
            doExecuteSuccess(object, requestCode);
        }
    }

    /**
     * 检测是否用户拒绝授权，并设置不再提示
     * @param object            所在活动 或 碎片
     * @param deniedPermissions 未被授权的权限列表
     * @return true 当用户拒绝授权后应用再次发起请求；false 第一次询问或版本低于6.0（23）或用户拒绝再次询问
     * 1.用户安装后，到第一次请求权限期间，调用这个方法内的同名方法，直接返回false
     * 2.第一次请求权限，用户拒绝，然后调用同名方法时，返回true表示弹出提示框
     * 3.第二次拒绝权限时，出现不再询问的勾选，在这之前调用同名方法返回true
     * 4.直到勾选不再询问后，同名方法返回false，此时应在请求回调中判断，并提示用户进入设置给予权限
     */
    private static boolean shouldShowRequestPermissionRationale(Object object,
                                                                List<String> deniedPermissions) {
        Activity activity = PermissionUtil.getActivity(object);
        if (activity == null) {
            throw new NullPointerException("Object cast Activity or Fragment return null");
        }
        for (String permission : deniedPermissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 请求权限的方法
     * @param object            对象——Activity or Fragment
     * @param requestCode       请求代码
     * @param deniedPermissions 未被授权的权限列表
     */
    private static void requestPermissions(Object object, int requestCode, List<String>
            deniedPermissions) {
        if (object instanceof Activity) { // 来自活动
            ActivityCompat.requestPermissions(
                    (Activity) object,
                    deniedPermissions.toArray(new String[deniedPermissions.size()]),
                    requestCode);
        } else if (object instanceof Fragment) { // 来自碎片
            ((Fragment) object).requestPermissions(
                    deniedPermissions.toArray(new String[deniedPermissions.size()]),
                    requestCode);
        } else { // 来自未知类——抛出异常
            throw new IllegalArgumentException(object.getClass().getName() + " is not supported");
        }
    }

    /**
     * 执行成功回调
     * @param activity    当前所在活动或碎片的对象
     * @param requestCode 请求代码
     */
    private static void doExecuteSuccess(Object activity, int requestCode) {
        // 根据实例的Class对象找到其中拥有成功注解的方法，然后比对请求代码是否相同，再返回所注解方法的Method对象
        Method executeMethod = PermissionUtil.findMethodWithRequestCode(activity.getClass(),
                PermissionSuccess.class, requestCode);
        // 利用反射执行这个方法
        executeMethod(activity, executeMethod);
    }

    /**
     * 执行失败回调
     * @param activity    当前所在活动或碎片的对象
     * @param requestCode 请求代码
     */
    private static void doExecuteFail(Object activity, int requestCode) {
        // 根据实例的Class对象找到其中拥有失败注解的方法，然后比对请求代码是否相同，再返回所注解方法的Method对象
        Method executeMethod = PermissionUtil.findMethodWithRequestCode(activity.getClass(),
                PermissionFail.class, requestCode);

        // 利用反射执行这个方法
        executeMethod(activity, executeMethod);
    }

    /**
     * 执行授权依据回调
     * @param activity    当前所在活动或碎片的对象
     * @param requestCode 请求代码
     */
    private static void doExecuteRationale(Object activity, int requestCode) {
        // 根据实例的Class对象找到其中拥有依据注解的方法，然后比对请求代码是否相同，再返回所注解方法的Method对象
        Method executeMethod = PermissionUtil.findMethodWithRequestCode(activity.getClass(),
                PermissionRationale.class, requestCode);
        if (executeMethod == null) {
            // 可能没有设置依据方法，或请求代码不匹配，因此认定不提示用户为什么需要这个权限，则直接反射失败方法
            doExecuteFail(activity, requestCode);
        } else {
            // 利用反射执行这个方法
            executeMethod(activity, executeMethod);
        }
    }

    /**
     * 执行回调中的方法
     * @param activity      当前所在活动或碎片的对象
     * @param executeMethod 对应方法
     */
    private static void executeMethod(Object activity, Method executeMethod) {
        if (executeMethod != null) {
            try {
                // 设置可访问标志
                if (!executeMethod.isAccessible()) executeMethod.setAccessible(true);
                // 调用这个方法
                executeMethod.invoke(activity);
            } catch (IllegalAccessException
                    | IllegalArgumentException
                    | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 权限请求的回调
     * @param activity     当前活动
     * @param requestCode  请求代码
     * @param permissions  权限列表
     * @param grantResults 授权返回
     */
    public static void onRequestPermissionsResult(Activity activity, int requestCode,
                                                  String[] permissions, int[] grantResults) {
        requestResult(activity, requestCode, permissions, grantResults);
    }

    /**
     * 权限请求的回调
     * @param fragment     当前碎片
     * @param requestCode  请求代码
     * @param permissions  权限列表
     * @param grantResults 授权结果
     */
    public static void onRequestPermissionsResult(Fragment fragment, int requestCode,
                                                  String[] permissions, int[] grantResults) {
        requestResult(fragment, requestCode, permissions, grantResults);
    }

    /**
     * 权限请求回调到活动或碎片中的对应成功失败方法
     * @param obj          活动或碎片的对象
     * @param requestCode  请求代码
     * @param permissions  权限列表
     * @param grantResults 授权结果
     */
    private static void requestResult(Object obj, int requestCode, String[] permissions,
                                      int[] grantResults) {
        List<String> deniedPermissions = new ArrayList<>();
        // 遍历结果，得到未被授权的权限常量
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }
        // 如果有任何一个未被授权
        if (deniedPermissions.size() > 0) {
            // 如果用户已经拒绝过一次授权，并且设置不再询问，那么就需要调用提示方法，因为系统的弹窗不再出现
            if (!shouldShowRequestPermissionRationale(obj, deniedPermissions)) {
                // 告诉用户为什么需要权限——但如果用户没有创建提示权限依据的方法，则直接反射失败方法
                doExecuteRationale(obj, requestCode);
            } else {
                // 下次还可以弹出系统弹窗，因此直接反射失败方法
                doExecuteFail(obj, requestCode);
            }
        } else {
            // 全部授权，反射成功方法
            doExecuteSuccess(obj, requestCode);
        }
    }
}
