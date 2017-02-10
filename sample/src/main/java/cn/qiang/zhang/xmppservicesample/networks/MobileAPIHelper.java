package cn.qiang.zhang.xmppservicesample.networks;

import android.app.Activity;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.qiang.zhang.xmppservicesample.App;
import cn.qiang.zhang.xmppservicesample.managers.AccountManager;
import cn.qiang.zhang.xmppservicesample.models.FootPrint;
import cn.qiang.zhang.xmppservicesample.models.MatchLabels;
import cn.qiang.zhang.xmppservicesample.models.Search;
import cn.qiang.zhang.xmppservicesample.models.SearchHistory;
import cn.qiang.zhang.xmppservicesample.models.SearchRequest;
import cn.qiang.zhang.xmppservicesample.models.SystemRecommend;
import cn.qiang.zhang.xmppservicesample.models.Token;
import cn.qiang.zhang.xmppservicesample.models.User;
import cn.qiang.zhang.xmppservicesample.models.UserLocal;
import cn.qiang.zhang.xmppservicesample.networks.callback.BaseCallback;
import cn.qiang.zhang.xmppservicesample.networks.callback.EmptyCallback;
import cn.qiang.zhang.xmppservicesample.networks.callback.JsonCallback;
import cn.qiang.zhang.xmppservicesample.networks.request.JsonBody;
import cn.qiang.zhang.xmppservicesample.utils.Constant;
import cn.qiang.zhang.xmppservicesample.utils.Util;
import cn.qiang.zhang.xmppservicesample.utils.logger.Log;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 服务器接口的帮助类
 * <p>
 * 提供相关服务接口的内部实现方式，界面只需要调用就行。
 * <p>
 * Created by mrZQ on 2016/11/14.
 */
public final class MobileAPIHelper {
    private static final String TAG = "MobileAPIHelper";

    /**
     * 权限认证
     */
    private final static String apiKey = apiKey(Constant.APIKEY, null);
    /**
     * 是否开启调试模式，默认不开启，如有需要，调用debug开启调试
     */
    private static boolean DEBUG = false;
    /**
     * Json解析工具
     */
    public static Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create();
    /** 服务器时间戳 */
    public static long serverTimeStamp = 0;

    /** 更新当前服务器时间戳 */
    public static void setServerTimeStamp(long serverTimeStamp) {
        MobileAPIHelper.serverTimeStamp = serverTimeStamp;
    }

    /**
     * @return 服务器当前时间(ms)
     */
    public static long getServerTime() {
        if (serverTimeStamp == 0) {
            return System.currentTimeMillis();
        }
        return serverTimeStamp + SystemClock.elapsedRealtime();
    }

    /**
     * 服务器响应时间毫秒值，减去系统开机时间的毫秒值时，就能得到一个服务器时间戳。
     * 当我们需要取服务器当前时间，只要将开机时间加上这个时间戳，得到的将是【同步的服务器时间】，
     * 然后，如果有服务器返回的消息时间戳，则可以通过对比两者之间的差值，得到消息是否当天生成。
     * @param serverTime 服务器响应时间
     */
    public static void updateOffsetServerTime(Date serverTime) {
        // milliseconds since boot, including time spent in sleep.
        long realTime = SystemClock.elapsedRealtime();
        // 服务器时间 - 系统开机时间 = 服务器时间戳
        MobileAPIHelper.setServerTimeStamp(serverTime.getTime() - realTime);
        Log.d(TAG, "服务器(本地时间)：" + Util.defaultAll(serverTime) +
                ", (北京时间)：" + Util.beijingAll(serverTime));
    }

    /**
     * 开启调试模式，会在日志中输出网络请求的详细出错信息——包括Exception
     */
    public static void debug() {
        DEBUG = true;
    }

    /** 全局的ApiKey权限认证 */
    public static Request.Builder apiKey(@NonNull Request.Builder request) {
        return request.addHeader(MobileAPIConst.HEADER_AUTHORIZATION, apiKey);
    }

    /** 全局用户权限认证 */
    public static Request.Builder token(Token token, Request.Builder request) {
        return request.addHeader(MobileAPIConst.HEADER_AUTHORIZATION, token.author());
    }

    /** 构造apiKey权限认证 */
    private static String apiKey(String apiKey, String password) {
        if (apiKey == null) { apiKey = "null"; }
        if (password == null) { password = "null"; }
        return MobileAPIConst.API_KEY_BASIC
                + Base64.encodeToString((apiKey + ":" + password).getBytes(), Base64.NO_WRAP);
    }

    /** 获取用户id路径 */
    public static HttpUrl getIdPath(long id) {
        return OkHttp.path(MobileAPIConst.PATH_USERS, String.valueOf(id));
    }

    /**
     * 微信注册
     */
    public static void wxSignUp(@NonNull String openid, BaseCallback callback) {
        // 1.拼装表单
        FormBody formBody = OkHttp.formBody(MobileAPIConst.LOGIN_WE_CHAT, openid);
        // 2.创建Url：默认域名+Path：/token
        HttpUrl url = OkHttp.path(MobileAPIConst.PATH_TOKEN);
        // 3.创建不缓存数据的post请求构造器，传入表单
        Request.Builder builder = apiKey(OkHttp.noCache(url)).post(formBody);
        // 4.发送请求，实现回调
        OkHttp.send(builder, callback);
    }

    public static Token getToken() {
        UserLocal user = AccountManager.getInstance().user();
        return user.getToken();
    }

    public static void getUserInfo(BaseCallback callback) {
        UserLocal user = AccountManager.getInstance().user();
        long id = user.getId();
        getUserInfo(id, callback);
    }

    /** 通过id获取用户信息 */
    public static void getUserInfo(long id, BaseCallback callback) {
        // 1.创建Url：默认域名+/users/id
        HttpUrl url = getIdPath(id);
        // 2.得到token
        Token token = getToken();
        if (token == null) {
//            callback.onFailed(null);
//            callback.onComplete();
            return;
        }
        // 3.创建请求构造器，带token认证、不缓存数据、get方式
        Request.Builder builder = token(token, OkHttp.noCache(url));
        // 4.发送请求
        OkHttp.send(builder, callback);
    }

    /**
     * 修改用户信息
     */
    public static void changeUserInfo(@NonNull User newUserInfo, @NonNull JsonCallback<User> callback) {
        // 1.创建Url：默认域名+/users
        HttpUrl url = OkHttp.path(MobileAPIConst.PATH_USERS);
        // 2.得到token
        Token token = getToken();
        if (token != null) {
            // 3.创建请求构造器，带token认证、不缓存数据、get方式
            Request.Builder builder = token(token, OkHttp.noCache(url));
            // 4.put序列化后的用户信息
            builder.put(new JsonBody.Builder<User>().add(newUserInfo).build());
            // 5.发送请求
            OkHttp.send(builder, callback);
        }

    }

    private static HttpUrl getAvatarPath() {
        User user = AccountManager.getInstance().user();
        String avatarPath = user.avatarPath();
        return OkHttp.link(avatarPath);
    }

    private static MultipartBody getAvatarBody(String header, String avatarPath) {
        // 通过路径创建文件
        File avatar = new File(avatarPath);
        // 通过文件创建请求body
        RequestBody requestBody = RequestBody.create(MobileAPIConst.MEDIA_AVATAR, avatar);
        // 创建Multipart
        return OkHttp.multipartBody(
                MobileAPIConst.MEDIA_AVATAR,
                header,
                avatar.getName(),
                requestBody
        );
    }

    public static void uploadAvatar(@NonNull String avatarPath, BaseCallback callback) {
        // 1.创建Url：默认域名+/users/avatars
        HttpUrl url = getAvatarPath();
        // 2.创建Multipart
        MultipartBody body = getAvatarBody(MobileAPIConst.KEY_AVATAR, avatarPath);
        // 3.得到token
        Token token = getToken();
        if (token != null) {
            // 6.token+post——回调监听上传进度
            Request.Builder builder = token(token, OkHttp.noCache(url))
                    .post(OkHttp.getProgressRequestBody(body, callback));
            // 7.上传文件
            OkHttp.upLoading(builder, callback);
        }
    }

    /**
     * 创建错误回调的静态方法，因为是在子线程中调用，需要加上锁
     * @param call     {@link Call}
     * @param response {@link Response}
     * @param e        回调异常
     * @return {@link BaseResponse<ErrorResponse>}
     */
    public synchronized static BaseResponse<ErrorResponse> createError(
            Call call,
            Response response,
            Exception e) {
        // 服务器错误
        if (response != null && response.code() == 500) {
            try {
                String jsonStr = response.body().string();
                ErrorResponse errorResponse = gson.fromJson(jsonStr, ErrorResponse.class);
                //解析后直接返回
                return new BaseResponse<>(call, 500, errorResponse);
            } catch (IOException e1) {
                // 解析异常，递归返回
                return createError(call, null, e1);
            }
        }
        // 正常错误:无响应、本地异常、无网络；网络响应出错、用户取消请求、服务器返回错误
        ErrorResponse errorResponse = new ErrorResponse();
        if (call != null) {
            // 表示有请求，通常这里不会为null，打印tag
            errorResponse.setMoreInfo("call tag: " + call.request().tag().toString());
        }
        // 在非调试模式下，返回code、message，不返回所有出错细节
        if (!MobileAPIHelper.DEBUG) {
            if (response != null) {
                errorResponse.setHttpStatus(response.code());
                errorResponse.setCode(0);
                errorResponse.setMessage(response.message());
                errorResponse.setDeveloperMessage(getMessage(response.code()));
                errorResponse.setProperty(response.request().toString());
                response.close();
            } else {
                errorResponse.setCode(-100);
                errorResponse.setMessage("无响应,请检查网络连接");
                if (!App.getInstance().isNetworkConnected()) {
                    errorResponse.setCode(-101);
                    errorResponse.setMessage("无连接,请检查网络设置");
                }
            }
            if (e != null) {
                errorResponse.setCode(-200);
                errorResponse.setMessage("出现异常,请反馈错误日志");
                errorResponse.setDeveloperMessage(e.getMessage());
                if (!App.getInstance().isNetworkConnected()) {
                    errorResponse.setCode(-101);
                    errorResponse.setMessage("无连接,请检查网络设置");
                }
            }
            return new BaseResponse<>(call, errorResponse.getCode(), errorResponse);
        }

        // 2016/12/29 分类到本地错误消息
        Map<String, String> localMessage = errorResponse.getLocalMessage();
        if (localMessage == null) {
            localMessage = new HashMap<>();
        }
        // 以下用于发送给开发者调式的解析，通常打印在日志中，线上版本不显示内容
        if (call != null) {
            localMessage.put("call request", call.request().toString());
            localMessage.put("call headers", call.request().headers().toString());
            localMessage.put("call isCanceled", call.isCanceled()
                    ? Boolean.TRUE.toString()
                    : Boolean.FALSE.toString());
            localMessage.put("call isExecuted", call.isExecuted()
                    ? Boolean.TRUE.toString()
                    : Boolean.FALSE.toString());
        } else {
            // 通常不存在null的情况
            localMessage.put("call", "null");
        }
        if (response != null) {
            localMessage.put("response request", response.request().toString());
            localMessage.put("response headers", response.headers().toString());
            localMessage.put("response code", String.valueOf(response.code()));
            localMessage.put("response isSuccessful", String.valueOf(response.isSuccessful()));
            localMessage.put("response isRedirect", String.valueOf(response.isRedirect()));
            localMessage.put("response network", String.valueOf(response.networkResponse()));
            localMessage.put("response cache", String.valueOf(response.cacheResponse()));
            localMessage.put("response prior", String.valueOf(response.priorResponse()));
            localMessage.put("response message", response.message());
            errorResponse.setCode(response.code());
            errorResponse.setHttpStatus(response.code());
            errorResponse.setMessage(response.message());
            response.close();
        } else {
            localMessage.put("response", "null");
            errorResponse.setCode(-100);
            errorResponse.setMessage("无响应,请检查网络连接");
            if (!App.getInstance().isNetworkConnected()) {
                errorResponse.setCode(-101);
                errorResponse.setMessage("无连接,请检查网络设置");
            }
        }
        if (e != null) {
            errorResponse.setCode(-200);
            errorResponse.setMessage("出现异常,请反馈错误日志");
            errorResponse.setDeveloperMessage(e.getMessage());
            if (!App.getInstance().isNetworkConnected()) {
                errorResponse.setCode(-101);
                errorResponse.setMessage("无连接,请检查网络设置");
            }
        } else {
            localMessage.put("exception", "null");
        }
        errorResponse.setLocalMessage(localMessage);
        return new BaseResponse<>(call, errorResponse.getCode(), errorResponse);
    }

    /**
     * @param code HTTP通用返回
     *             400   （错误请求） 服务器不理解请求的语法。
     *             401   （未授权） 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。
     *             403   （禁止） 服务器拒绝请求。
     *             404   （未找到） 服务器找不到请求的网页。
     *             405   （方法禁用） 禁用请求中指定的方法。
     *             406   （不接受） 无法使用请求的内容特性响应请求的网页。
     *             407   （需要代理授权） 此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。
     *             408   （请求超时）  服务器等候请求时发生超时。
     *             409   （冲突）  服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。
     *             410   （已删除）  如果请求的资源已永久删除，服务器就会返回此响应。
     *             411   （需要有效长度） 服务器不接受不含有效内容长度标头字段的请求。
     *             412   （未满足前提条件） 服务器未满足请求者在请求中设置的其中一个前提条件。
     *             413   （请求实体过大） 服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。
     *             414   （请求的 URI 过长） 请求的 URI（通常为网址）过长，服务器无法处理。
     *             415   （不支持的媒体类型） 请求的格式不受请求页面的支持。
     *             416   （请求范围不符合要求） 如果页面无法提供请求的范围，则服务器会返回此状态代码。
     *             417   （未满足期望值） 服务器未满足”期望”请求标头字段的要求。
     */
    private static String getMessage(int code) {
        switch (code) {
            case 400:
                return "非法请求";
            case 401:
                return "未授权";
            case 403:
                return "禁止请求";
            case 404:
                return "未知请求";
            case 405:
                return "方法禁用";
            case 406:
                return "不接受的请求";
            case 407:
                return "需要代理授权";
            case 408:
                return "请求超时";
            case 409:
                return "请求冲突";
            case 410:
                return "资源已删除";
            case 411:
                return "需要有效长度";
            case 412:
                return "未满足前提条件";
            case 413:
                return "文件大小超出限制";
            case 414:
                return "请求的URI过长";
            case 415:
                return "不支持的媒体类型";
            case 416:
                return "请求范围不符合要求";
            case 417:
                return "未满足期望值";
            default:
                break;
        }
        return "无法连接服务器" + code;
    }

    public static void searchPOI(String query, int from, int size, String type,
                                 BaseCallback<Search> callback) {
        HttpUrl url = getSearchPath();
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKeywords(query);
        searchRequest.setForm(from);
        searchRequest.setSize(size);
        if (!TextUtils.isEmpty(type)) {
            searchRequest.setTypes(type);
        }
        Request.Builder builder = OkHttp.normalCache(url);
        if (AccountManager.getInstance().checkSignIn()) {
            // 已登录
            Token token = MobileAPIHelper.getToken();
            builder = MobileAPIHelper.token(token, builder);
        } else {
            // 匿名登录
            builder = MobileAPIHelper.apiKey(builder);
        }
        builder.post(new JsonBody.Builder<SearchRequest>().add(searchRequest).build());
        OkHttp.send(builder, callback);
    }

    private static HttpUrl getSearchPath() {
        // host/search
        return OkHttp.path(MobileAPIConst.PATH_SEARCH);
    }

    public static void addLabel(String label, JsonCallback<User> callback) {
        HttpUrl url = getLabelPath(label);
        Token token = MobileAPIHelper.getToken();
        Request.Builder builder = MobileAPIHelper.token(token, OkHttp.noCache(url));
        builder.post(new FormBody.Builder().build());
        OkHttp.send(builder, callback);
    }

    private static HttpUrl getLabelPath(String label) {
        User user = AccountManager.getInstance().user();
        String labelPath = user.labelPath();
        return OkHttp.link(labelPath, label);
    }

    private static HttpUrl getLabelPath() {
        return getLabelPath(MobileAPIConst.NUMBER_LABEL);
    }

    public static void removeLabel(String s, JsonCallback<User> callback) {
        HttpUrl url = getLabelPath(s);
        Token token = MobileAPIHelper.getToken();
        Request.Builder builder = MobileAPIHelper.token(token, OkHttp.noCache(url));
        builder.delete();
        OkHttp.send(builder, callback);
    }

    public static void getLabels(JsonCallback<MatchLabels> callback) {
        HttpUrl url = getLabelPath();
        Token token = MobileAPIHelper.getToken();
        Request.Builder builder = MobileAPIHelper.token(token, OkHttp.noCache(url));
        OkHttp.send(builder, callback);
    }

    public static void updateUserInfo(Activity activity) {
        if (!AccountManager.getInstance().checkSignIn()) {
            // 未登录不更新用户信息
            return;
        }
        // 1.创建Url：默认域名+/users/id
        UserLocal user = AccountManager.getInstance().user();
        long id = user.getId();
        HttpUrl url = getIdPath(id);
        // 2.得到token
        Token token = user.getToken();
        // 3.创建请求构造器，带token认证、不缓存数据、get方式
        Request.Builder builder = token(token, OkHttp.noCache(url));
        // 4.发送请求
        OkHttp.send(builder, new JsonCallback<User>(activity) {
            @Override
            public void onSuccess(BaseResponse<User> baseResponse) {
                Log.d(TAG, "更新成功:" + baseResponse.toString());
                UserLocal oldUser = AccountManager.getInstance().user();
                oldUser.update(baseResponse.getData());
                AccountManager.getInstance().save(oldUser);
                if (oldUser.getToken() != null) {
                    Log.d(TAG, "token " + oldUser.getToken());
                }
            }
        });
    }

    public static void searchHistory(JsonCallback<SearchHistory> callback) {
        HttpUrl url = OkHttp.path(MobileAPIConst.PATH_SEARCH_HISTORY, "5");
        Token token = MobileAPIHelper.getToken();
        Request.Builder builder = MobileAPIHelper.token(token, OkHttp.noCache(url));
        OkHttp.send(builder, callback);
    }

    public static void saveState(Activity activity, String keywords) {
        HttpUrl url = getSearchPath();
        Token token = MobileAPIHelper.getToken();
        if (token != null) {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setKeywords(keywords);
            searchRequest.setSaveState(true);
            Request.Builder builder = MobileAPIHelper.token(token, OkHttp.noCache(url));
            builder.post(new JsonBody.Builder<SearchRequest>().add(searchRequest).build());
            OkHttp.send(builder, new EmptyCallback(activity, null));
        }
    }

    public static void getMatchResults(long id, JsonCallback<SystemRecommend> callback) {
        HttpUrl url = getRecommendPath(id);
        Token token = MobileAPIHelper.getToken();
        if (token != null) {
            Request.Builder builder = MobileAPIHelper.token(token, OkHttp.noCache(url));
            OkHttp.send(builder, callback);
        }

    }

    private static HttpUrl getRecommendPath(long id) {
        return OkHttp.paths(MobileAPIConst.PATH_RECOMMEND + File.separator + id);
    }

    public static void getFootPrint(JsonCallback<FootPrint> callback) {
//        User user = AccountManager.getInstance().user();
//        HttpUrl url = getFootPrint(user.userId());
        HttpUrl url = getFootPrint(76);
        Token token = MobileAPIHelper.getToken();
        if (token != null) {
            Request.Builder builder = MobileAPIHelper.token(token, OkHttp.noCache(url));
            builder.post(new JsonBody.Builder<SearchRequest>().add(new SearchRequest()).build());
            OkHttp.send(builder, callback);
        }
    }

    private static HttpUrl getFootPrint(long userId) {
        return OkHttp.paths(MobileAPIConst.PATH_FOOTPRINT + File.separator + userId);
    }
}
