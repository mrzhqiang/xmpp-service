package cn.qiang.zhang.xmppservicesample.networks.callback;

import android.app.Activity;
import android.widget.Toast;

import cn.qiang.zhang.xmppservicesample.networks.BaseResponse;
import cn.qiang.zhang.xmppservicesample.networks.ErrorResponse;
import cn.qiang.zhang.xmppservicesample.networks.MobileAPIHelper;
import cn.qiang.zhang.xmppservicesample.utils.logger.Log;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 空回调，表示不关心成功结果，只需要得知状态如何。比如：发送验证码，成功不返回任何内容。
 * <p>
 * Created by mrZQ on 2016/12/12.
 */
public class EmptyCallback extends JsonCallback<ErrorResponse> {

    private static final String TAG = "EmptyCallback";

    public EmptyCallback(Activity activity, String message) {
        super(activity, message);
    }

    @Override
    public void onSuccess(BaseResponse<ErrorResponse> baseResponse) {
        // 这里成功返回的参数baseResponse是null，不能使用
        Log.d(TAG, "请求成功");
        if (message != null) {
            showMessage(message + "成功");
        }
    }

    private void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void disposeResponse(Call call, Response response) throws Exception {
        // 空的回调不关心成功结果200-299
        if (response.isSuccessful()) {
            sendSuccess(null);
            response.close();
        } else {
            sendFailed(MobileAPIHelper.createError(call, response, null));
        }
    }
}
