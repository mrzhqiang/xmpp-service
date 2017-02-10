package cn.qiang.zhang.xmppservicesample.utils.logger;

/**
 * 设置
 * <p>
 * Created by mrZQ on 2016/12/31.
 */

final class Setting {

	/** 默认标签 */
	static final String DEFAULT_TAG = "Log";
	/* 日志打印开关 */
	static boolean showV = true;
	static boolean showD = true;
	static boolean showI = true;
	static boolean showW = true;
	static boolean showE = true;
	static boolean showWtf = true;
	/** 是否开启自定义标签，true 传值标签；false 固定标签 */
	static boolean isCustomTag = true;

	private Setting() {
		// no instance
	}
}
