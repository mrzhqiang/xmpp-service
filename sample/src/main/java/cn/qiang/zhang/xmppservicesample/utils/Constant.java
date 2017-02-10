package cn.qiang.zhang.xmppservicesample.utils;


/**
 * 通用常量类
 * <p>
 * 包括字符串常量、整型常量、格式化常量等。
 * <p>
 * Created by mrZQ on 2016/10/14.
 */
public class Constant {

    private Constant() {/* cannot be instantiated */}

    /*ApiKey*/
    public static final String APIKEY = "d0f785ea57b341a3a114beb16cef1b97";

    /*微信相关*/
    public static final String WX_APP_ID = "wx043d418da736d678";

    public static final String WX_SECRET = "fa16ddc5ab055da9aa5db819cc9990d6";
    // 开发平台主页
    public static final String WX_HOST = "https://api.weixin.qq.com";
    // 路径
    public static final String P_SNS = "sns";
    public static final String P_OAUTH2 = "oauth2";
    public static final String P_TOKEN = "access_token";
    public static final String P_REFRESH_TOKEN = "refresh_token";
    public static final String P_USER_INFO = "userinfo";
    public static final String P_AUTH = "auth";
    // 查询，获取token
    public static final String Q_APP_ID = "appid";
    public static final String Q_SECRET = "secret";
    public static final String Q_CODE = "code";
    public static final String Q_GRANT_TYPE = "grant_type";
    public static final String QV_AUTH = "authorization_code";
    // 查询，获取用户资料
    public static final String Q_ACC_TOKEN = "access_token";
    public static final String Q_OPEN_ID = "openid";
    /*图聚SDK常量*/
    public static final String PALMAP_HOST_URL = "http://api.ipalmap.com/";

    // 显示楼层的英文
//    public static final Param<String> FLOOR_SHOW_FIELD = new Param<>("address", String.class);
    /*图聚*/
    public static final double offset_impal[] = {-9.346, 4.0162};


    /*触达*/
    public static final double offset_touchair[] = {-4.4, -2.66};

    /** 经测量的触达时间坐标XY轴与卫星XY轴之间的偏移角度关系 */
    public static final double SIN_BETA = Math.sin(Math.toRadians(-90));
    public static final double COS_BETA = Math.cos(Math.toRadians(-90));
    /** 经计算的上海图聚世界坐标XY轴与卫星XY轴之间的偏移角度关系 */

    /*内容相关*/
//    public static final String CONTENT_TYPE = ContentActivity.class.getName();
    private static final int CONTENT_CODE = 100;
    // 展会通知
    public static final int ACCOUNT_EXHIBITION_NOTICE = CONTENT_CODE + 1;
    // 系统推荐
    public static final int ACCOUNT_SYSTEM_RECOMMENDED = CONTENT_CODE + 2;
    // 搜索历史
    public static final int ACCOUNT_SEARCH_HISTORY = CONTENT_CODE + 3;
    // 我的足迹
    public static final int ACCOUNT_MY_TRACE = CONTENT_CODE + 4;
    // 修改个人信息
    public static final int ACCOUNT_CHANGED_USER_INFO = CONTENT_CODE + 5;
    // 意见反馈
    public static final int ACCOUNT_FEED_BACK = CONTENT_CODE + 6;
    // 编辑标签
    public static final int ACCOUNT_EDIT_LABEL = CONTENT_CODE + 7;
    /*详情相关*/
//    public static final String DETAILS_TYPE = DetailsActivity.class.getName();

    public static final String DETAILS_DATA = "poi_data";
    private static final int DETAILS_CODE = 200;
    public static final int USER_DETAILS = DETAILS_CODE + 1;
    public static final int BOOTH_DETAILS = DETAILS_CODE + 2;
    public static final int ACTOR_DETAILS = DETAILS_CODE + 3;
    public static final int PRODUCT_DETAILS = DETAILS_CODE + 4;
    /** 是否需要返回的登录页面KEY */
    public static final String LOGIN_RESULT_STATE = "login_result_state";

    public static final String ACTION_SHOULD_LOGIN = "action_should_login";
    public static final String ACTION_LOGIN_STATUS = "action_login_status";

    /*XMPP*/
    public static final String XMPP_HOST = "apiv1.touchair.cn";
    public static final String XMPP_PROT = "5222";
    public static final String XMPP_DOMAIN = "touchat.touchair.cn";
    public static final String XMPP_PASSWORD = APIKEY;
    public static final String XMPP_START = "device_";
}
