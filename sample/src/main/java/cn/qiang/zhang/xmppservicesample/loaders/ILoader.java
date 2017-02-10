package cn.qiang.zhang.xmppservicesample.loaders;

import android.content.Context;
import android.view.View;

import java.io.Serializable;

/**
 * <p>
 * Created by mrZQ on 2017/2/8.
 */
public interface ILoader<T extends View> extends Serializable {

    void show(Context context, String path, T imageView);

    T create(Context context);

    void cancelTag(Context context);

    void resumeTag(Context context);

    void pauseTag(Context context);
}
