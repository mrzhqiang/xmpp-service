package cn.qiang.zhang.xmppservicesample.networks.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Window;

import cn.qiang.zhang.xmppservicesample.networks.OkHttp;
import okhttp3.Request;

/**
 * 自动在开始前弹出进度条对话框，在完成后隐藏的对话框回调，继承于{@link JsonCallback}，因此成功返回的是实体
 * <p>
 * Created by mrZQ on 2016/11/22.
 */
public abstract class ProgressCallback<T> extends JsonCallback<T> {

	/**
	 * 实时显示进度，因此消息不能为null
	 * @param activity 所在页面
	 * @param message 消息内容：显示于标题栏
	 */
	protected ProgressCallback(Activity activity, @NonNull String message) {
		super(activity, message);
	}

	@Override
	public Request onCreate(Request.Builder builder) {
		// 因为进度加载的对话框通常可以单个取消，因此重写这个方法
		return builder.tag(this).build();
	}

	@Override
	protected void showDialog() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			dialog = new ProgressDialog(getActivity(),
					android.R.style.Theme_Material_Light_Dialog);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			dialog = new ProgressDialog(getActivity(),
					android.R.style.Theme_DeviceDefault_Light_Dialog);
		} else {
			dialog = new ProgressDialog(getActivity());
		}
		// 无标题
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 按下返回键，调用cancel方法——非dismiss方法，则会有onCancelListener回调
		dialog.setCancelable(true);
		// 横向进度条
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setMessage(message);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialogInterface) {
				// 取消请求
				OkHttp.cancelTag(this);
			}
		});
		dialog.show();
	}

	@Override
	public void progress(long bytesRead, long contentLength, int progress) {
		if (message != null && dialog.isShowing()) {
			dialog.setProgress(progress);
		}
	}

}
