package cn.qiang.zhang.xmppservicesample.networks.callback;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

/**
 * 位图回调，通常是用来加载临时图片，这是一个示例，后续有可能删除，也有可能给自定义View使用。
 * <p>
 * Created by mrZQ on 2016/11/23.
 */
public abstract class BitmapCallback extends BaseCallback<Bitmap> {

    /**
     * 需要弱引用一个Activity的实例，用来post回调到UI线程
     * @param activity {@link Activity}
     */
    public BitmapCallback(Activity activity) {
        super(activity);
    }

    @Override
    public Bitmap convert(Response response) throws Exception {
        Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
        response.close();
        return bitmap;
    }

}
