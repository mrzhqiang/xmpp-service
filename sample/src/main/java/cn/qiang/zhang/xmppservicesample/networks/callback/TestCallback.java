package cn.qiang.zhang.xmppservicesample.networks.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;

import cn.qiang.zhang.xmppservicesample.BaseActivity;
import cn.qiang.zhang.xmppservicesample.networks.BaseResponse;
import cn.qiang.zhang.xmppservicesample.networks.ErrorResponse;
import cn.qiang.zhang.xmppservicesample.utils.logger.Log;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 源码字符串类型的回调监听，仅用于测试和示例。
 * <p>
 * Created by mrZQ on 2016/11/23.
 */
public final class TestCallback extends BaseCallback<String> {

	private static final String TAG = "TestCallback";

	public TestCallback(Activity activity, @NonNull String message) {
		super(activity, message);
	}

	/*测试请置于扩展BaseActivity的页面中*/
	private BaseActivity baseActivity() {
		return (BaseActivity) getActivity();
	}

	@Override
	public Request onCreate(Request.Builder builder) {
		Log.d(TAG, this.getClass().getSimpleName());
		return super.onCreate(builder);
	}

	@Override
	public void onStart(Call call) {
		super.onStart(call);
	}

	@Override
	protected void showDialog() {
		dialog = ProgressDialog.show(getActivity(), message, "Test");
	}

	@Override
	public void onSuccess(BaseResponse<String> baseResponse) {
		Log.d(TAG, baseResponse.toString());
		baseActivity().message("Successful", baseResponse.toString()).show();
	}

	@Override
	public String convert(Response response) throws Exception {
		String content = response.body().string();
		Log.d(TAG, content);
		return content;
	}

	@Override
	public void onFailed(BaseResponse<ErrorResponse> baseResponse) {
		baseActivity().message("Failed", baseResponse.toString()).show();
	}

	@Override
	public void onComplete() {
		super.onComplete();
		Log.d(TAG, this.getClass().getSimpleName());
	}

	@Override
	protected void hideDialog() {
		super.hideDialog();
		Log.d(TAG, this.getClass().getSimpleName());
	}
}
