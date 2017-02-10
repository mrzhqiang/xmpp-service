package cn.qiang.zhang.xmppservicesample.networks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.qiang.zhang.xmppservicesample.models.WXToken;
import cn.qiang.zhang.xmppservicesample.networks.callback.BaseCallback;
import cn.qiang.zhang.xmppservicesample.networks.request.ProgressListener;
import cn.qiang.zhang.xmppservicesample.networks.request.ProgressRequestBody;
import cn.qiang.zhang.xmppservicesample.networks.request.ProgressResponseBody;
import cn.qiang.zhang.xmppservicesample.utils.Constant;
import cn.qiang.zhang.xmppservicesample.utils.ThrowUtil;
import cn.qiang.zhang.xmppservicesample.utils.logger.Log;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 根据 ok http3 v3.4.2 设计的便捷类
 * <p>
 * 1.内部构建一个连接超时30s、读写超时30s和最大10Mb缓存空间的客户端实例
 * 2.通过获取{@link #customize}变量，可以设定更多细节，比如添加一个拦截器，设置代理，实现Cookie管理等
 * 3.但通常来说，这种只用在特定环境，比如上传下载的进度监听，以及其他特殊需求
 * <p>
 * Created by mrZQ on 2016/11/17.
 */
public final class OkHttp {
    private static final String TAG = "OkHttp";

    /** 默认缓存大小 */
    private static final int DEFAULT_CACHE_SIZE_UNIT = 10 * 1024 * 1024; // 10Mb
    /**
     * OkHttpClient是{@link Call}的工厂，Call用于发送HTTP请求和保存响应数据。
     * <p>
     * OkHttpClient推荐为单例共享模式，使用new创建默认设置；使用{@link OkHttpClient.Builder}自定义配置。
     * <p>
     * 关于配置，可以设定超时值，添加拦截器，设置缓存目录，加代理，证书认证等。
     * <p>
     * 通过{@link OkHttpClient#newBuilder()}定制特殊客户端，共享同一个连接池和线程池，这是为特定目的所构建。
     * <p>
     * 手动关闭客户端是没有必要的，因为在闲置的时候会自动释放资源。
     * <p>
     * ok http3 是基于java的网络请求工具，不依赖android SDK。
     * <p>
     * 请注意，以下方法在完全退出的时候会自动调用，手动调用有可能造成异常，除非你保证退出后不再使用当前客户端。
     * <p>
     * client.dispatcher().executorService().shutdown(); // 关闭调度程序的执行服务
     * client.connectionPool().evictAll(); // 清理连接池
     * client.cache().close(); // 关闭缓存器
     */
    private static OkHttpClient client;
    /** 主页构造器 */
    private static final HttpUrl host = _host();

    private OkHttp() {
        // no instance
    }

    /** 初始化 ok http3 客户端 */
    public static void init(@NonNull Context context) {
        if (client == null) {
            File cacheDir = context.getExternalCacheDir();
            if (cacheDir == null) {
                cacheDir = context.getCacheDir();
            }
            cacheDir = new File(cacheDir.toString(), "ok_http_cache");
            client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .cache(new Cache(cacheDir, DEFAULT_CACHE_SIZE_UNIT))
                    .build();
        }
    }

    /** 因为已确保是在程序初始化时调用一次，所以不做异步检测 */
    private static OkHttpClient client() {
        ThrowUtil.nonNull(client, "client is null, please invoking init()");
        return client;
    }

    /**
     * 获取可以派生特定客户端的构造器
     * <p>
     * 这将共享当前客户端的连接池、线程池、配置，通过新的构造器，你可以实现一些特别的功能。
     * 比如：连接超时设为50秒；或者添加一个日志拦截器。
     * <p>
     * 请注意，派生的特定客户端只用在特殊场合，全局共享的客户端不应该被改变。
     * @return {@link OkHttpClient.Builder 客户端构造器}
     */
    private static OkHttpClient.Builder customize() {
        ThrowUtil.nonNull(client, "client is null, please invoking init()");
        return client.newBuilder();
    }

    ///////////////////////////////////////////////////////////////////////////
    // URL部分
    ///////////////////////////////////////////////////////////////////////////

    /** 通过字符串拼装 */
    public static HttpUrl url(String url) {
        return HttpUrl.parse(url);
    }

    /** 通过URL拼装 */
    public static HttpUrl url(URL url) {
        return HttpUrl.get(url);
    }

    /** 通过URI拼装 */
    public static HttpUrl url(URI url) {
        return HttpUrl.get(url);
    }

    /** 拼装后的默认主页，使用build()完成构建 */
    private static HttpUrl.Builder host() {
        return host(MobileAPIConst.SCHEME, MobileAPIConst.HOST, MobileAPIConst.PORT);
    }

    /** 默认主页 */
    private static HttpUrl _host() {
        return host().build();
    }

    /** 高度自由的主页拼接方法 */
    private static HttpUrl.Builder host(String scheme, String host, int port) {
        return new HttpUrl.Builder().scheme(scheme).host(host).port(port);
    }

    /** 微信主页构造器 */
    private static HttpUrl.Builder wxHost() {
        return url(Constant.WX_HOST).newBuilder();
    }

    /** 微信主页 */
    private static HttpUrl wx_host() {
        return wxHost().build();
    }

    /** 微信token路径构造器 */
    private static HttpUrl.Builder wxToken() {
        return wxHost()
                .addPathSegment(Constant.P_SNS)
                .addPathSegment(Constant.P_OAUTH2)
                .addPathSegment(Constant.P_TOKEN);
    }

    /** 微信获取token的链接 */
    public static HttpUrl wxGetToken(String code) {
        return wxToken()
                .addQueryParameter(Constant.Q_APP_ID, Constant.WX_APP_ID)
                .addQueryParameter(Constant.Q_SECRET, Constant.WX_SECRET)
                .addQueryParameter(Constant.Q_CODE, code)
                .addQueryParameter(Constant.Q_GRANT_TYPE, Constant.QV_AUTH)
                .build();
    }

    /** 微信获取用户信息的路径构造器 */
    private static HttpUrl.Builder wxUserInfo() {
        return wxHost()
                .addPathSegment(Constant.P_SNS)
                .addPathSegment(Constant.P_USER_INFO);
    }

    /** 微信获取用户信息的链接 */
    public static HttpUrl wxGetUserInfo(WXToken token) {
        String accessToken = token.accessToken();
        String openid = token.openId();
        return wxUserInfo()
                .addQueryParameter(Constant.Q_ACC_TOKEN, accessToken)
                .addQueryParameter(Constant.Q_OPEN_ID, openid)
                .build();
    }

    /** 微信刷新token的构造器 */
    private static HttpUrl.Builder wxRefreshToken() {
        return wxHost()
                .addPathSegment(Constant.P_SNS)
                .addPathSegment(Constant.P_OAUTH2)
                .addPathSegment(Constant.P_REFRESH_TOKEN);
    }

    /** 微信刷新token的链接 */
    public static HttpUrl wxRefresh(WXToken token) {
        String refreshToken = token.refreshToken();
        return wxRefreshToken()
                .addQueryParameter(Constant.Q_APP_ID, Constant.WX_APP_ID)
                .addQueryParameter(Constant.Q_GRANT_TYPE, Constant.P_REFRESH_TOKEN)
                .addQueryParameter(Constant.P_REFRESH_TOKEN, refreshToken)
                .build();
    }

    /** 检查token有效性的构造器 */
    private static HttpUrl.Builder checkToken() {
        return wxHost()
                .addPathSegment(Constant.P_SNS)
                .addPathSegment(Constant.P_AUTH);
    }

    /** 检查token有效性的链接 */
    public static HttpUrl checkToken(WXToken token) {
        String accessToken = token.accessToken();
        return checkToken()
                .addQueryParameter(Constant.Q_APP_ID, Constant.WX_APP_ID)
                .addQueryParameter(Constant.P_TOKEN, accessToken)
                .build();
    }

    /**
     * 在默认主页后面添加路径
     * 自动编码
     * @param paths "aaa", "bbb", "ccc"
     * @return http://apiv1.touchair.cn/aaa/bbb/ccc
     * @see HttpUrl.Builder#addPathSegment(String)
     */
    public static HttpUrl path(String... paths) {
        HttpUrl.Builder builder = host();
        for (String path : paths) {
            builder.addPathSegment(path);
        }
        return builder.build();
    }

    /**
     * 在默认主页后面添加路径
     * 忽略编码
     * @param paths "aa%20a", "bb%99b", "cc%11c"
     * @return http://apiv1.touchair.cn/aa%20a/bb%99b/cc%11c
     * @see HttpUrl.Builder#addEncodedPathSegment(String)
     */
    public static HttpUrl pathEd(String... paths) {
        HttpUrl.Builder builder = host();
        for (String path : paths) {
            builder.addEncodedPathSegment(path);
        }
        return builder.build();
    }

    /**
     * 在默认主页后面添加链接
     * 自动编码
     * @param paths "/uppc/scenes/abcdef-12345"
     * @return http://apiv1.touchair.cn/uppc/scenes/abcdef-12345
     * @see HttpUrl.Builder#addPathSegments(String)
     */
    public static HttpUrl paths(String paths) {
        return host().addPathSegments(paths).build();
    }

    /**
     * 在默认主页后面添加链接
     * 忽略编码
     * @see HttpUrl.Builder#addEncodedPathSegments(String)
     */
    public static HttpUrl pathsEd(String paths) {
        return host().addEncodedPathSegments(paths).build();
    }

    /**
     * 添加查询语句
     * 自动编码
     * @param name  "username"
     * @param value "mrzq"
     * @return http://apiv1.touchair.cn?username=mrzq
     * @see HttpUrl.Builder#addQueryParameter(String, String)
     */
    public static HttpUrl query(String name, String value) {
        return host().addQueryParameter(name, value).build();
    }

    /**
     * 添加查询语句
     * 忽略编码
     * @see HttpUrl.Builder#addQueryParameter(String, String)
     */
    public static HttpUrl queryEd(String name, String value) {
        return host().addQueryParameter(name, value).build();
    }

    /**
     * 添加查询语句列表
     * @param list "username", "mrzq", "password", "12345"
     * @return http://apiv1.touchair.cn?username=mrzq%20&password=12345
     * @see HttpUrl.Builder#addQueryParameter(String, String)
     */
    public static HttpUrl queryList(List<String> list) {
        if (list == null || list.size() % 2 != 0) {
            return null;
        }
        HttpUrl.Builder builder = host();
        for (int i = 0; i < list.size(); i = i + 2) {
            builder.addQueryParameter(list.get(i), list.get(i + 1));
        }
        return builder.build();
    }

    /**
     * 在指定主页后面添加路径
     * 自动编码
     * @param url   http://apiv1.touchair.cn/users
     * @param paths smscodes
     * @return http://apiv1.touchair.cn/users/smscodes
     * @see HttpUrl.Builder#addPathSegments(String)
     */
    public static HttpUrl paths(HttpUrl url, String paths) {
        return url.newBuilder().addPathSegments(paths).build();
    }

    /**
     * 在指定主页后面添加路径
     * 忽略编码
     * @see HttpUrl.Builder#addEncodedPathSegments(String)
     */
    public static HttpUrl pathsEd(HttpUrl url, String paths) {
        return url.newBuilder().addEncodedPathSegments(paths).build();
    }

    /**
     * 在指定主页后面添加链接
     * 自动编码
     * @param url   http://apiv1.touchair.cn/users
     * @param paths "smscodes", "verify"
     * @return http://apiv1.touchair.cn/users/smscodes/verify
     * @see HttpUrl.Builder#addPathSegment(String)
     */
    public static HttpUrl path(HttpUrl url, String... paths) {
        HttpUrl.Builder builder = url.newBuilder();
        for (String path : paths) {
            builder.addPathSegment(path);
        }
        return builder.build();
    }

    /**
     * 在指定主页后面添加链接
     * 忽略编码
     * @see HttpUrl.Builder#addEncodedPathSegment(String)
     */
    public static HttpUrl pathEd(HttpUrl url, String... paths) {
        HttpUrl.Builder builder = url.newBuilder();
        for (String path : paths) {
            builder.addEncodedPathSegment(path);
        }
        return builder.build();
    }

    /** 加上路径 */
    public static HttpUrl link(String href) {
        return host.resolve(href);
    }

    /** 替换最后一级路径 */
    public static HttpUrl link(HttpUrl url, String href) {
        return link(url, 0, href);
    }

    /** 取代指定分级后的路径，1表示替换最后两级路径 */
    public static HttpUrl link(HttpUrl url, int r, String href) {
        String link = "";
        for (; r > 0; r--) {
            link += MobileAPIConst.RESOLVE;
        }
        link += href;
        return url.resolve(link);
    }

    /** 前面加路径，后面再加一个链接 */
    public static HttpUrl link(String paths, String href) {
        return host.newBuilder(paths).addPathSegment(href).build();
    }

    /**
     * 增加锚点
     * 自动编码
     * @param url      http://apiv1.touchair.cn/users
     * @param fragment abc
     * @return http://apiv1.touchair.cn/users#abc
     * @see HttpUrl.Builder#fragment(String)
     */
    public static HttpUrl fragment(HttpUrl url, String fragment) {
        return url.newBuilder().fragment(fragment).build();
    }

    /**
     * 增加锚点
     * 忽略编码
     * @see HttpUrl.Builder#encodedFragment(String)
     */
    public static HttpUrl fragmentEd(HttpUrl url, String fragment) {
        return url.newBuilder().encodedFragment(fragment).build();
    }

    /**
     * 设定账户/密码，通常用于电子邮件地址
     * @param url      http://apiv1.touchair.cn
     * @param username qiang.zhang
     * @param password 1234
     * @return http://qiang.zhang:1234@apiv1.touchair.cn
     * @see HttpUrl.Builder#username(String)
     * @see HttpUrl.Builder#password(String)
     */
    public static HttpUrl account(HttpUrl url, String username, String password) {
        return url.newBuilder().username(username).password(password).build();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 表单部分——请求body
    ///////////////////////////////////////////////////////////////////////////

    public static RequestBody jsonBody(String jsonString) {
        return RequestBody.create(MobileAPIConst.MEDIA_JSON, jsonString);
    }

    public static RequestBody contentBody(String mediaType, String content) {
        return RequestBody.create(MediaType.parse(mediaType), content);
    }

    public static RequestBody fileBody(String mediaType, File file) {
        return RequestBody.create(MediaType.parse(mediaType), file);
    }

    public static FormBody formBody(String name, String value) {
        return new FormBody.Builder().add(name, value).build();
    }

    public static FormBody formBody(Map<String, String> nameAndValue) {
        if (nameAndValue == null) {
            return null;
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : nameAndValue.entrySet()) {
            String name = entry.getKey().trim();
            String value = entry.getValue().trim();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
                Log.d(TAG, "name:" + name + ", value:" + value);
                continue;
            }
            builder.add(name, value);
        }
        return builder.build();
    }

    public static RequestBody formBodyEd(Map<String, String> nameValue) {
        if (nameValue == null) {
            return null;
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : nameValue.entrySet()) {
            String name = entry.getKey().trim();
            String value = entry.getValue().trim();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
                Log.d(TAG, "name:" + name + ", value:" + value);
                continue;
            }
            builder.addEncoded(name, value);
        }
        return builder.build();
    }

    public static MultipartBody.Part part(RequestBody body) {
        return MultipartBody.Part.create(body);
    }

    public static MultipartBody.Part part(Headers headers, RequestBody body) {
        return MultipartBody.Part.create(headers, body);
    }

    public static MultipartBody.Part part(String name, String value) {
        return MultipartBody.Part.createFormData(name, value);
    }

    public static MultipartBody.Part part(String name, String filename, RequestBody body) {
        return MultipartBody.Part.createFormData(name, filename, body);
    }

    public static MultipartBody mixed(List<MultipartBody.Part> partList) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.MIXED);
        for (MultipartBody.Part part : partList) {
            builder.addPart(part);
        }
        return builder.build();
    }

    public static MultipartBody alternative(List<MultipartBody.Part> partList) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.ALTERNATIVE);
        for (MultipartBody.Part part : partList) {
            builder.addPart(part);
        }
        return builder.build();
    }

    public static MultipartBody digest(List<MultipartBody.Part> partList) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.DIGEST);
        for (MultipartBody.Part part : partList) {
            builder.addPart(part);
        }
        return builder.build();
    }

    public static MultipartBody parallel(List<MultipartBody.Part> partList) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.PARALLEL);
        for (MultipartBody.Part part : partList) {
            builder.addPart(part);
        }
        return builder.build();
    }

    public static MultipartBody form(List<MultipartBody.Part> partList) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (MultipartBody.Part part : partList) {
            builder.addPart(part);
        }
        return builder.build();
    }

    /** 上传头像 */
    public static MultipartBody multipartBody(MediaType mediaType,
                                              String headerAvatar,
                                              String fileName,
                                              RequestBody requestBody) {
        return new MultipartBody.Builder()
                .setType(mediaType)
                .addFormDataPart(headerAvatar, fileName, requestBody)
                .build();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 请求部分
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 拿到默认主页的get请求，post、put、delete等方式通过请求构造器创建
     * <p>
     * 注意：这个方法可以有缓存
     */
    public static Request _request() {
        return request(host).build();
    }

    /**
     * 根据{@link HttpUrl}创建请求的构造器
     * @param httpUrl 包含详细的地址信息
     * @return {@link Request.Builder}
     */
    public static Request.Builder request(HttpUrl httpUrl) {
        return new Request.Builder().url(httpUrl);
    }

    /**
     * 缓存控制器
     * @return {@link CacheControl.Builder}
     */
    private static CacheControl.Builder cacheControl() {
        return new CacheControl.Builder();
    }

    /**
     * 默认缓存
     */
    public static Request.Builder ageCache(HttpUrl httpUrl) {
        return ageCache(httpUrl, 24 * 60 * 60);
    }

    /**
     * 默认缓存
     */
    public static Request.Builder staleCache(HttpUrl httpUrl) {
        return staleCache(httpUrl, 24 * 60 * 60 * 7 * 4);
    }

    /**
     * 有待验证的请求
     * <p>
     * 强制刷新(相当于浏览器ctrl+F5)，因为当服务器返回一个maxAge=30，在30秒内，无论多少次请求都是从缓存读数据
     * 如果已知对方更改信息并提交服务器上，那么就需要刷新功能，将maxAge设为0是强制访问，有可能缓存不存在，无法
     * 确定是否可以更新(有待进一步验证)，那么就需要强制刷新了。
     * @param httpUrl 网址链接
     * @return 请求构造器
     */
    public static Request.Builder refresh(HttpUrl httpUrl) {
        return normalCache(httpUrl).addHeader("pragma", "no-cache");
    }

    /**
     * 构建默认请求（缓存将由服务器控制）
     * @return {@link Request.Builder}
     */
    public static Request.Builder normalCache(HttpUrl httpUrl) {
        return ageCache(httpUrl, 3600);
    }

    public static Request.Builder noCache(HttpUrl httpUrl) {
        return request(httpUrl).cacheControl(CacheControl.FORCE_NETWORK);
    }

    /**
     * 构建只读缓存的请求
     * <p>
     * 因为这个请求只读缓存，当没有缓存文件的时候，就会报504错误，那么就在504回调中发起正常的请求
     * @param httpUrl 包含详细的地址信息
     * @return {@link Request.Builder}
     */
    public static Request.Builder cached(HttpUrl httpUrl) {
        return request(httpUrl).cacheControl(CacheControl.FORCE_CACHE);
    }

    private static Request.Builder ageCache(HttpUrl httpUrl, int age) {
        return request(httpUrl)
                .cacheControl(cacheControl().maxAge(age, TimeUnit.SECONDS).build());
    }

    private static Request.Builder staleCache(HttpUrl httpUrl, int stale) {
        return request(httpUrl)
                .cacheControl(cacheControl().maxStale(stale, TimeUnit.SECONDS).build());
    }

    ///////////////////////////////////////////////////////////////////////////
    // 发送部分
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 发送普通的网络请求
     * @param builder  请求构造器
     * @param callback 回调
     * @return 当前调用者
     */
    public static Call send(Request.Builder builder, BaseCallback callback) {
        // 调用回调的创建方法生成请求
        Request request = callback.onCreate(builder);
        // 通过客户端和请求创建Call
        Call call = client().newCall(request);
        // 启动这个call
        callback.onStart(call);
        // 返回call备用
        return call;
    }

    /**
     * 发送上传文件的网络请求，会重建一个已调整过读写超时的客户端，用来发送请求和收取回调
     * <p>
     * 通常这个方法是用来额外接收上传文件的进度回调，没有这种需求就使用普通的send方法
     * @param builder  请求构造器
     * @param callback 回调
     * @return 当前调用者
     */
    public static Call upLoading(Request.Builder builder, BaseCallback callback) {
        Request request = callback.onCreate(builder);
        Call call = getProgressClient().build().newCall(request);
        // 启动这个call
        callback.onStart(call);
        return call;
    }

    /**
     * 发送下载文件的网络请求
     * @param callback 回调
     * @return 当前调用者
     */
    public static Call downLoading(Request.Builder builder, BaseCallback callback) {
        Request request = callback.onCreate(builder);
        Call call = getProgressClient(callback).newCall(request);
        // 启动这个call
        callback.onStart(call);
        return call;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 其他部分
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 通过tag取消请求——准备请求和运行中的，已完成的不取消
     * @param tag 标签，通常是所在的Activity.this
     */
    public static boolean cancelTag(Object tag) {
        boolean isSuccessful = false;
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
                isSuccessful = true;
            }
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
                isSuccessful = true;
            }
        }
        return isSuccessful;
    }

    /**
     * 获取客户端Builder，为带进度条延长读写超时时间
     * @return 请求客户端Builder
     */
    private static OkHttpClient.Builder getProgressClient() {
        return customize()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * 获取实现响应进度回调监听的okhttp3客户端
     * <p>
     * 先拷贝一个共享的客户端，然后添加网络拦截器，在这个拦截器中，通过请求拿到响应，通过响应的newBuilder方法
     * 重新拼装响应body，自然而然的，当有数据操作时，将回调传递进入的响应监听器
     * @param progressListener 响应监听器
     * @return 特殊需求的客户端，共享底层连接池等，仅Builder中的参数可能不一致
     */
    private static OkHttpClient getProgressClient(final ProgressListener progressListener) {
        // 自定义客户端，将读写超时设为更大的120s，并添加一个网络拦截器，将原先的响应body包装上进度回调
        return getProgressClient()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        return response.newBuilder()
                                .body(new ProgressResponseBody(response.body(), progressListener))
                                .build();
                    }
                })
                .build();
    }

    /**
     * 获取实现请求进度回调监听的okhttp3客户端
     * @param requestBody      实际的请求body
     * @param progressListener 请求监听器
     * @return 经过包装的请求body，内部实现进度监听
     */
    public static ProgressRequestBody getProgressRequestBody(RequestBody requestBody,
                                                             ProgressListener progressListener) {
        return new ProgressRequestBody(requestBody, progressListener);
    }

    /**
     * 从响应中获取文件名——头 或 Url
     * @param response 响应
     * @return 文件名
     */
    public static String getNetFileName(Response response) {
        String fileName = getHeaderFileName(response);
        // 如果获取的Header中没有文件信息，则去拿url中的路径或域名加上当前时间毫秒值作为文件名
        if (TextUtils.isEmpty(fileName)) {
            HttpUrl url = response.request().url();
            fileName = getUrlFileName(url);
        }
        // 如果连域名都不存在，那只能是未知的文件，这也恰好符合定义——域名不存在你从哪里得来的文件？
        if (TextUtils.isEmpty(fileName)) { fileName = "unknown" + System.currentTimeMillis(); }
        return fileName;
    }

    /**
     * 通过头信息寻找文件名
     * @param response 响应
     * @return fileName
     */
    private static String getHeaderFileName(Response response) {
        // 通过 Content-Disposition 获取文件信息中的 "attachment;filename=FileName.txt" 得到文件名
        String dispositionHeader = response.header(MobileAPIConst.HEADER_CONTENT_DISPOSITION);
        if (dispositionHeader != null) {
            String split = "filename=";
            int indexOf = dispositionHeader.indexOf(split);
            if (indexOf != -1) {
                String fileName = dispositionHeader.substring(indexOf + split.length(),
                                                              dispositionHeader.length());
                fileName = fileName.replaceAll("\"", "");   //文件名可能包含双引号,需要去除
                return fileName;
            }
        }
        return null;
    }

    /**
     * 通过HttpUrl获取其中的路径或者域名作为文件名
     * @param url {@link HttpUrl}
     * @return 路径或域名
     */
    private static String getUrlFileName(HttpUrl url) {
        // 拿到编码后的路径——不包括查询语句和片段
        String path = url.encodedPath();
        // 如果它是空的或null
        if (TextUtils.isEmpty(path)) {
            // 那就把域名中的.去掉作为文件名
            return url.host().replaceAll("\\.", "");
        }
        // 路径的末尾通常是文件名，拿到它
        int indexOf = path.lastIndexOf("/");
        if (indexOf != -1) {
            return path.substring(indexOf + 1);
        }
        return path;
    }
}
