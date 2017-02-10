package cn.qiang.zhang.xmppservicesample.networks.callback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Locale;

import cn.qiang.zhang.xmppservicesample.networks.BaseResponse;
import cn.qiang.zhang.xmppservicesample.networks.ErrorResponse;
import cn.qiang.zhang.xmppservicesample.networks.IConverter;
import cn.qiang.zhang.xmppservicesample.networks.MobileAPIHelper;
import cn.qiang.zhang.xmppservicesample.networks.request.ProgressListener;
import cn.qiang.zhang.xmppservicesample.utils.logger.Log;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;

/**
 * 内部处理 ok http3 的失败/响应回调，抽象方法分发到主线程
 * <p>
 * 设计思路：
 * 1.调用onCreate方法创建请求
 * 2.发起请求时，调用onStart方法
 * 3.等待ok http3 {@link Callback}回调——注意，它们在子线程中
 * 4.处理结果，分发到onSuccess/onFailed——post到主线程运行
 * 5.无论成功/失败，总是最后调用onComplete方法
 * <p>
 * Created by mrZQ on 2016/11/16.
 */
public abstract class BaseCallback<T> implements Callback, IConverter<T>, ProgressListener {

	///////////////////////////////////////////////////////////////////////////
	// 类：常量、变量、构造器
	///////////////////////////////////////////////////////////////////////////

	/** 日志标签 */
	private static final String TAG = "BaseCallback";
	/** 对Activity的一个弱引用，使子线程的回调运行在UI线程上 */
	private final WeakReference<Activity> weakReference;
	/** 需要显示的消息 */
	protected String message;
	/** 在创建比较耗时又希望用户暂时不操作的网络回调时，需要弹出一个对话框 */
	ProgressDialog dialog;

	/** 弱引用一个{@link Activity}的实例，用来{@link Activity#runOnUiThread(Runnable)} */
	BaseCallback(@NonNull Activity activity) {
		this(activity, null);
	}

	/**
	 * 创建带消息提示对话框的网络回调
	 * @param activity 发起请求的页面
	 * @param message 提示消息：给对话框标题使用
	 */
	BaseCallback(Activity activity, String message) {
		this.weakReference = new WeakReference<>(activity);
		this.message = message;
	}

	///////////////////////////////////////////////////////////////////////////
	// 可自由重载方法
	///////////////////////////////////////////////////////////////////////////

	/**
	 * 上传下载的进度
	 */
	protected void progress(long bytesRead, long contentLength, int progress) {
	}

	/**
	 * 初始化对话框，这里可以是细节更完美的对话框
	 */
	protected void showDialog() {
		if (message != null) {
			// 通常对话框只使用一次
			dialog = ProgressDialog.show(getActivity(), message, "请稍等..");
		}
	}

	/**
	 * 隐藏对话框，重载可以实现不同的消失风格，比如延时退出
	 */
	protected void hideDialog() {
		if (message != null && dialog != null && dialog.isShowing()) {
			// 取消对话框
			dialog.dismiss();
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// 生命周期
	///////////////////////////////////////////////////////////////////////////

	/** 创建 */
	public Request onCreate(Request.Builder builder) {
		// 设置发起请求的页面为tag，并构建最终请求——销毁时取消tag请求
		return builder.tag(getActivity()).build();
	}

	/** 启动 */
	public void onStart(Call call) {
		Log.d(TAG, call.request().toString());
		call.enqueue(this);
		// 显示对话框
		showDialog();
	}

	/** 成功 */
	abstract public void onSuccess(BaseResponse<T> baseResponse);

	/** 失败 */
	public void onFailed(BaseResponse<ErrorResponse> baseResponse) {
		Log.d(TAG, baseResponse.toString());
		if (message != null) {
			new AlertDialog.Builder(getActivity())
					.setTitle(message + "失败")
					.setMessage(baseResponse.getData().getMessage())
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							dialogInterface.dismiss();
						}
					})
					.show();
		}
	}

	/** 完成 */
	public void onComplete() {
		hideDialog();
	}

	///////////////////////////////////////////////////////////////////////////
	// 底层回调
	///////////////////////////////////////////////////////////////////////////

	@Override
	public void onProgress(long bytesRead, long contentLength, int progress) {
		sendProgress(bytesRead, contentLength, progress);
	}

	@Override
	public void onFailure(Call call, IOException e) {
		// 这里是请求出错或失败的回调，代表底层没有发出去请求
		sendFailed(MobileAPIHelper.createError(call, null, e));
	}

	@Override
	public void onResponse(Call call, okhttp3.Response response) throws IOException {
		try {
			// 更新返回头中的服务器时间
			if (response.networkResponse() != null) {
				updateServerTime(response);
			}
			// 处理响应
			disposeResponse(call, response);
		} catch (Exception e) {
			// 捕捉异常，发送开发者消息，有响应回调失败，说明是解析异常0
			sendFailed(MobileAPIHelper.createError(call, null, e));
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// 发送到UI线程
	///////////////////////////////////////////////////////////////////////////

	/**
	 * 发送上传/下载进度到主线程
	 */
	@SuppressWarnings("WeakerAccess")
	void sendProgress(final long bytesRead,
	                          final long contentLength,
	                          final int progress) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				progress(bytesRead, contentLength, progress);
			}
		});
	}

	/**
	 * 发送成功数据到主线程
	 */
	void sendSuccess(final BaseResponse<T> baseResponse) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				onSuccess(baseResponse);
				onComplete();
			}
		});
	}

	/**
	 * 发送错误数据到主线程
	 */
	void sendFailed(final BaseResponse<ErrorResponse> baseResponse) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				onFailed(baseResponse);
				onComplete();
			}
		});
	}

	///////////////////////////////////////////////////////////////////////////
	// 常用方法
	///////////////////////////////////////////////////////////////////////////

	protected Activity getActivity() {
		return weakReference.get();
	}

	/** 通用的响应处理，重载这里将完成不同接口的结果分发 */
	protected void disposeResponse(Call call, okhttp3.Response response) throws Exception {
		// 取得响应中的code，便于处理过程中抛出异常的分析
		int code = response.code();
		// 如果是成功的返回——官方文档说，有响应不一定是成功的，需要在这里判断
		if (response.isSuccessful()) {
			Log.d(TAG, "is Network Response:" + (response.networkResponse() != null));
			Log.d(TAG, "is Cache Response:" + (response.cacheResponse() != null));
			// 取得响应码和转换后的内容
			T data = convert(response);
			// 发送到主线程
			sendSuccess(new BaseResponse<>(call, code, data));
		} else {
			// 创建失败的消息，发送到主线程
			sendFailed(MobileAPIHelper.createError(call, response, null));
		}
	}

	/** 更新服务器时间 */
	protected void updateServerTime(okhttp3.Response response) throws Exception {
		// 打印Headers
		Log.d(TAG, "headers:" + response.headers().toString());
		// 取得服务器响应时间——GMT
		Date time = response.headers().getDate("Date");
		// 如果时间存在
		if (time != null) {
			// 去更新服务器时间戳
			MobileAPIHelper.updateOffsetServerTime(time);
		}
		// 显示此次底层从发送请求到响应的耗时
		long sentTime = response.sentRequestAtMillis();
		long receivedTime = response.receivedResponseAtMillis();
		Log.d(TAG, String.format(Locale.getDefault()
				, "Ping:%d ms"
				, (receivedTime - sentTime)));
	}

	/**
	 * 获取回调的泛型最终形态
	 * <p>
	 * 如果不是Class则使用Gson的TypeToken类进行获取
	 * @return {@link Type} or {@link TypeToken}
	 */
	Type getType() {
		Type type
				= ((ParameterizedType) this.getClass()
				.getGenericSuperclass())
				.getActualTypeArguments()[0];
		if (type instanceof Class) {
			return type;
		} else {
			return new TypeToken<T>() {}.getType();
		}
	}

}
