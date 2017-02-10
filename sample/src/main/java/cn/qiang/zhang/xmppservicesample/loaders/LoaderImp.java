package cn.qiang.zhang.xmppservicesample.loaders;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cn.qiang.zhang.xmppservicesample.R;

/**
 * <p>
 * Created by mrZQ on 2017/2/8.
 */
public class LoaderImp implements ILoader<ImageView> {
    private static final long serialVersionUID = 2670126155982881898L;

    private static final int DEFAULT_LOADING = R.mipmap.ic_launcher;
    private static final int DEFAULT_ERROR = R.mipmap.ic_launcher;

    @DrawableRes
    private int loadingRes = DEFAULT_LOADING;
    @DrawableRes
    private int errorRes = DEFAULT_ERROR;

    public LoaderImp setLoadingRes(int loadingRes) {
        this.loadingRes = loadingRes > 0 ? loadingRes : DEFAULT_LOADING;
        return this;
    }

    public LoaderImp setErrorRes(int errorRes) {
        this.errorRes = errorRes > 0 ? errorRes : DEFAULT_ERROR;
        return this;
    }

    @Override
    public void show(@NonNull final Context context, String path, @NonNull ImageView imageView) {
        if (TextUtils.isEmpty(path)) {
            imageView.setImageResource(errorRes);
            return;
        }
        Picasso.with(context)
                .load(path)
                .placeholder(loadingRes)
                .error(errorRes)
                .tag(context)
                .into(imageView);
    }

    @Override
    public ImageView create(Context context) {
        return new ImageView(context);
    }

    @Override
    public void cancelTag(Context context) {
        Picasso.with(context).cancelTag(context);
    }

    @Override
    public void resumeTag(Context context) {
        Picasso.with(context).resumeTag(context);
    }

    @Override
    public void pauseTag(Context context) {
        Picasso.with(context).pauseTag(context);
    }
}
