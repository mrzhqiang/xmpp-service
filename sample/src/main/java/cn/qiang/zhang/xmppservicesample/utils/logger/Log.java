package cn.qiang.zhang.xmppservicesample.utils.logger;

import com.orhanobut.logger.Logger;

/**
 * 日志打印入口
 * <p>
 * 通过适配的接口，将{@link android.util.Log 系统日志打印工具}替换为格式内容日志工具<br>
 * 其中，json、xml、Map、List、Set等对象，推荐使用{@link Logger 第三方日志打印工具}<br>
 * 基本内容最好使用自定义工具打印——1.完全兼容系统日志工具；2.精简所需信息；3.替换只需要修改导入包
 * <p>
 * Created by mrZQ on 2016/10/13.
 */
public final class Log {

	/** 默认打印接口 */
	private static final IPrint DEFAULT = new SimplePrint();

	/** 打印接口 */
	private static IPrint mLogger = DEFAULT;

	private Log() {/* no instance */}

	/** 简易调试打印 */
	public static void d(String content) {
		if (!Setting.showD) return;
		getLogger().d(tag(), content);
	}

	/** 系统相关的调试打印1 */
	public static void d(String tag, String content) {
		if (!Setting.showD) return;
		getLogger().d(tag(tag), content);
	}

	/** 系统相关的调试打印2 */
	public static void d(String tag, String content, Throwable tr) {
		if (!Setting.showD) return;
		getLogger().d(tag(tag), content, tr);
	}

	/** 简易错误打印 */
	public static void e(String content) {
		if (!Setting.showE) return;
		getLogger().e(tag(), content);
	}

	/** 系统相关的错误打印1 */
	public static void e(String tag, String content) {
		if (!Setting.showE) return;
		getLogger().e(tag(tag), content);
	}

	/** 系统相关的错误打印2 */
	public static void e(String tag, String content, Throwable tr) {
		if (!Setting.showE) return;
		getLogger().e(tag(tag), content, tr);
	}

	/** 简易信息打印 */
	public static void i(String content) {
		if (!Setting.showI) return;
		getLogger().i(tag(), content);
	}

	/** 系统相关的信息打印1 */
	public static void i(String tag, String content) {
		if (!Setting.showI) return;
		getLogger().i(tag(tag), content);
	}

	/** 系统相关的信息打印2 */
	public static void i(String tag, String content, Throwable tr) {
		if (!Setting.showI) return;
		getLogger().i(tag(tag), content, tr);
	}

	/** 简易详情打印 */
	public static void v(String content) {
		if (!Setting.showV) return;
		getLogger().v(tag(), content);
	}

	/** 系统相关的详情打印1 */
	public static void v(String tag, String content) {
		if (!Setting.showV) return;
		getLogger().v(tag(tag), content);
	}

	/** 系统相关的详情打印2 */
	public static void v(String tag, String content, Throwable tr) {
		if (!Setting.showV) return;
		getLogger().v(tag(tag), content, tr);
	}

	/** 简易警告打印 */
	public static void w(String content) {
		if (!Setting.showW) return;
		getLogger().w(tag(), content);
	}

	/** 系统相关的警告打印1 */
	public static void w(String tag, String content) {
		if (!Setting.showW) return;
		getLogger().w(tag(tag), content);
	}

	/** 系统相关的警告打印2 */
	public static void w(String tag, Throwable tr) {
		if (!Setting.showW) return;
		getLogger().w(tag(tag), tr);
	}

	/** 系统相关的警告打印3 */
	public static void w(String tag, String content, Throwable tr) {
		if (!Setting.showW) return;
		getLogger().w(tag(tag), content, tr);
	}

	/** 简易诡异打印 */
	public static void wtf(String content) {
		if (!Setting.showWtf) return;
		getLogger().wtf(tag(), content);
	}

	/** 系统相关的诡异打印1 */
	public static void wtf(String tag, String content) {
		if (!Setting.showWtf) return;
		getLogger().wtf(tag(tag), content);
	}

	/** 系统相关的诡异打印2 */
	public static void wtf(String tag, Throwable tr) {
		if (!Setting.showWtf) return;
		getLogger().wtf(tag(tag), tr);
	}

	/** 系统相关的诡异打印3 */
	public static void wtf(String tag, String content, Throwable tr) {
		if (!Setting.showWtf) return;
		getLogger().wtf(tag(tag), content, tr);
	}

	/**
	 * @param isCustomTag ture 表示自定义TAG；false 表示预定义TAG，默认
	 */
	public static void customTag(boolean isCustomTag) {
		Setting.isCustomTag = isCustomTag;
	}

	/**
	 * @param showD true 开启调试日志，默认；false 关闭调试日志
	 */
	public static void debug(boolean showD) {
		Setting.showD = showD;
	}

	/** 获取打印接口实例 */
	public static IPrint getLogger() {
		return mLogger == null ? mLogger = DEFAULT : mLogger;
	}

	/** 自定义日志打印接口 */
	public static void setLogger(IPrint customLogger) {
		mLogger = customLogger == null ? DEFAULT : customLogger;
	}

	/** 设置漂亮的打印接口 */
	@SuppressWarnings("unused")
	public static void pretty() {
		setLogger(new LoggerPrint());
	}

	/** 使用固定tag，用于没有tag参数的方法 */
	private static String tag() {
		return Setting.DEFAULT_TAG;
	}

	/** 内部判断是否使用固定tag */
	private static String tag(String tag) {
		return Setting.isCustomTag ? tag : Setting.DEFAULT_TAG;
	}

}
