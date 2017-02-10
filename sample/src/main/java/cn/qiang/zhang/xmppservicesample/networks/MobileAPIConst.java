package cn.qiang.zhang.xmppservicesample.networks;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

/**
 * API常量
 * <p>
 * 这里保存应用程序接口常量，比如使用的超文本协议类型、主机域名、端口号，以及服务入口。
 * <p>
 * 根据 “Rest APIs” 文件的 “HAL json” 规范，只要有几个常规接口和服务入口，就可以满足需求。
 * <p>
 * Created by mrZQ on 2016/11/16.
 */
public final class MobileAPIConst {


    private MobileAPIConst() {
        // no instance
    }

    ///////////////////////////////////////////////////////////////////////////
    // 地址相关
    ///////////////////////////////////////////////////////////////////////////
    /*超文本协议类型*/
    public static final String SCHEME = "http";
    public static final String _SCHEME = "https"; // 留待备用
    /*主机域名*/
    public static final String HOST = "apiv1.touchair.cn";
    public static final String HOST_1 = "uppc.touchair.cn"; // 通常不用，当前2016.11.18开放端口为9000
    /*开放端口*/
    public static final int PORT = 80;

    /*服务入口，这里仅作为备用，或在无法动态获取路径的情况下，采用最原始的方式得到路径*/
    public static final String PATH_TOKEN = "token"; // 登录，获取token
    public static final String PATH_USERS = "users"; // 用户信息
    public static final String PATH_SEARCH = "search"; // 搜索
    public static final String PATH_SMS_CODE = "users/smscode"; // 验证码
    public static final String PATH_AVATARS = "users/avatars"; // 上传头像
    public static final String PATH_LABELS = "users/labels"; // 编辑标签
    public static final String PATH_SEARCH_HISTORY = "searchhistory"; // 搜索历史
    public static final String PATH_RECOMMEND = "users/systemrecommend"; // 系统推荐
    public static final String PATH_FOOTPRINT = "users/footprint"; // 我的足迹

    ///////////////////////////////////////////////////////////////////////////
    // Key相关
    ///////////////////////////////////////////////////////////////////////////
    /* Token认证的前缀 */
    public static final String TOKEN_BEARER = "Bearer ";
    /* ApiKey认证的前缀 */
    public static final String API_KEY_BASIC = "Basic ";
    /* 权限Key */
    public static final String HEADER_AUTHORIZATION = "Authorization";
    /* 头像Key */
    public static final String KEY_AVATAR = "avatar";
    /* 短信Key */
    public static final String KEY_SMS_CODE = "smscode";
    /* 鉴权Key */
    public static final String KEY_TOKEN = "token";
    /* 标签Key */
    public static final String KEY_LABELS = "labels";
    /* 链接的本身Key */
    public static final String KEY_SELF = "self";

    ///////////////////////////////////////////////////////////////////////////
    // 参数相关
    ///////////////////////////////////////////////////////////////////////////
    /* Json媒体 */
    public static final MediaType MEDIA_JSON
            = MediaType.parse("application/json; charset=utf-8");
    /* From表单 */
    public static final MediaType MEDIA_URL_ENCODED
            = MediaType.parse("application/x-www-form-urlencoded");
    /* 上传文件 */
    public static final MediaType MEDIA_AVATAR = MultipartBody.FORM;
    /* 登录注册 */
    public static final String LOGIN_USER_ID = "userId";
    public static final String LOGIN_MOBILE = "mobile";
    public static final String LOGIN_SMS_CODE = "smscode";
    public static final String LOGIN_PASSWORD = "password";
    /* 微信登录 */
    public static final String LOGIN_WE_CHAT = "openId";
    /* Token过期 */
    public static final String TOKEN_GRANT_TYPE = "grant_type";
    public static final String TOKEN_REFRESH = "refresh_token";
    /* 搜索POI */
    public static final String POI_SCENE_ID = "sceneId";
    public static final String POI_FLOOR_ID = "floorId";
    public static final String POI_TYPE = "type";

    ///////////////////////////////////////////////////////////////////////////
    // 用户资料 Profiles Map Key
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // 其他常量
    ///////////////////////////////////////////////////////////////////////////
    /** 上传下载文件相关 */
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

    /**
     * 地址跃迁的分级前缀：
     * 通常是从 http://apiv1.touchair.cn/developerteam/android/user?name=mrzq
     * 转到 http://apiv1.touchair.cn/developerteam/ios
     * 不使用 ../ 说明只替换末尾path
     * 使用 ../ 表示替换 http://apiv1.touchair.cn/developerteam/ 之后的path
     * 按照返回父级路径的方式，类推，超出路径上限不出错，会保持主机域名的不可变。
     * 目前的用途是，服务器返回的是HAL+Json模式数据，在里面有_link，通过这个前缀可以快速实现HOST+LINK的拼装
     * 需要注意的是：resolve方式有可能返回null，但多数时候它是正常工作的，除非你计算失误，导致一个溢出的替换。
     * 如果传入的link是一个完整的超链接，则需要用newBuilder(url)方式去替换，而不是这里的../
     */
    public static final String RESOLVE = "../";

    /** 获取标签数量的上限 */
    public static final String NUMBER_LABEL = "10";

}
