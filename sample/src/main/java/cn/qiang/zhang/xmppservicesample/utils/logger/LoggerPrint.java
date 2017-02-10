package cn.qiang.zhang.xmppservicesample.utils.logger;

import com.orhanobut.logger.Logger;

/**
 * 使用{@link Logger}适配Android日志工具
 * <p>
 * Created by mrZQ on 2016/12/31.
 */

final class LoggerPrint implements IPrint {

	@Override
	public int v(String tag, String msg) {
		Logger.t(tag).v(msg);
		return 0;
	}

	@Override
	public int v(String tag, String msg, Throwable tr) {
		Logger.log(Logger.VERBOSE, tag, msg, tr);
		return 0;
	}

	@Override
	public int d(String tag, String msg) {
		Logger.t(tag).d(msg);
		return 0;
	}

	@Override
	public int d(String tag, String msg, Throwable tr) {
		Logger.log(Logger.DEBUG, tag, msg, tr);
		return 0;
	}

	@Override
	public int i(String tag, String msg) {
		Logger.t(tag).i(msg);
		return 0;
	}

	@Override
	public int i(String tag, String msg, Throwable tr) {
		Logger.log(Logger.INFO, tag, msg, tr);
		return 0;
	}

	@Override
	public int w(String tag, String msg) {
		Logger.t(tag).w(msg);
		return 0;
	}

	@Override
	public int w(String tag, String msg, Throwable tr) {
		Logger.log(Logger.WARN, tag, msg, tr);
		return 0;
	}

	@Override
	public int w(String tag, Throwable tr) {
		Logger.log(Logger.INFO, tag, null, tr);
		return 0;
	}

	@Override
	public int e(String tag, String msg) {
		Logger.t(tag).e(msg);
		return 0;
	}

	@Override
	public int e(String tag, String msg, Throwable tr) {
		Logger.log(Logger.ERROR, tag, msg, tr);
		return 0;
	}

	@Override
	public int wtf(String tag, Throwable tr) {
		Logger.log(Logger.ASSERT, tag, null, tr);
		return 0;
	}

	@Override
	public int wtf(String tag, String msg) {
		Logger.t(tag).wtf(msg);
		return 0;
	}

	@Override
	public int wtf(String tag, String msg, Throwable tr) {
		Logger.log(Logger.ASSERT, tag, msg, tr);
		return 0;
	}
}
