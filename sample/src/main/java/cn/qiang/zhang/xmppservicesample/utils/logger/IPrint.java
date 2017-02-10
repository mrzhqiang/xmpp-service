package cn.qiang.zhang.xmppservicesample.utils.logger;

/**
 * 打印接口
 * <p>
 * Created by mrZQ on 2016/12/31.
 */
interface IPrint {

	int v(String tag, String msg);

	int v(String tag, String msg, Throwable tr);

	int d(String tag, String msg);

	int d(String tag, String msg, Throwable tr);

	int i(String tag, String msg);

	int i(String tag, String msg, Throwable tr);

	int w(String tag, String msg);

	int w(String tag, String msg, Throwable tr);

	int w(String tag, Throwable tr);

	int e(String tag, String msg);

	int e(String tag, String msg, Throwable tr);

	int wtf(String tag, Throwable tr);

	int wtf(String tag, String msg);

	int wtf(String tag, String msg, Throwable tr);

}
