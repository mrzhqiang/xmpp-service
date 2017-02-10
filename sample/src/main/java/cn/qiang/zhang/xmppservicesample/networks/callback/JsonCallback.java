package cn.qiang.zhang.xmppservicesample.networks.callback;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import okhttp3.Response;

/**
 * 将json结果解析成对应的泛型，只需要实现成功的回调，如果有需要的话，也可以重载失败和完成的回调
 * <p>
 * Created by mrZQ on 2016/11/21.
 */
public abstract class JsonCallback<T> extends BaseCallback<T> {

	protected JsonCallback(Activity activity) {
		super(activity);
	}

	protected JsonCallback(Activity activity, String message) {
		super(activity, message);
	}

	@Override
	public T convert(Response response) throws Exception {
		String json = response.body().string();
		Logger.json(json);
		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
		return gson.fromJson(json, getType());
	}

}
