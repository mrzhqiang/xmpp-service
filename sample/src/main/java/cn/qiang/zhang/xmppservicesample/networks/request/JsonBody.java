package cn.qiang.zhang.xmppservicesample.networks.request;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;

/**
 * 根据okhttp3规范建立的json类型requestBody
 * <p>
 * Created by mrZQ on 2016/11/18.
 */
public final class JsonBody<T> extends RequestBody {

	// 首先要定义传递的内容类型
	private static final MediaType CONTENT_TYPE = MediaType.parse("application/json");
	// 然后定义数据类型，因为这里是jsonBody，所以直接解析泛型数据实体为json就行
	private final T data;
	// 创建解析工具，这里是Gson
	private final Gson gson;

	private JsonBody(T data) {
		this.data = data;
		gson = new GsonBuilder()
				// 序列化null字段
//                .serializeNulls()
				// 添加序列化策略：忽略不需要上传的字段
				.addSerializationExclusionStrategy(new ExclusionStrategy() {

					@Override
					public boolean shouldSkipField(FieldAttributes f) {
						String name = f.getName();
						return /*name.contains("token")
								|| */name.contains("userId")
								|| name.contains("_links");
					}

					@Override
					public boolean shouldSkipClass(Class<?> clazz) {
						return false;
					}
				})
				// 格式化输出
				.setPrettyPrinting()
				.create();
	}

	public T data() {
		return data;
	}

	/** 解析json的方法 */
	private String toJson() {
		return gson.toJson(data, new TypeToken<T>() {}.getType());
	}

	@Override
	public MediaType contentType() {
		return CONTENT_TYPE;
	}

	@Override
	public long contentLength() throws IOException {
		return writeOrCountBytes(null, true);
	}

	@Override
	public void writeTo(BufferedSink sink) throws IOException {
		writeOrCountBytes(sink, false);
	}

	private long writeOrCountBytes(BufferedSink sink, boolean countBytes) {
		long byteCount = 0L;
		Buffer buffer;
		if (countBytes) {
			buffer = new Buffer();
		} else {
			buffer = sink.buffer();
		}
		buffer.writeUtf8(toJson());
		if (countBytes) {
			byteCount = buffer.size();
			buffer.clear();
		}
		return byteCount;
	}

	public static final class Builder<T> {

		private final List<T> dataList = new ArrayList<>();

		public Builder add(T data) {
			dataList.add(data);
			return this;
		}

		private int size() {
			return dataList.size();
		}

		private T first() {
			if (size() > 0) {
				return dataList.get(0);
			}
			return null;
		}

		private List<T> list() {
			return dataList;
		}

		public JsonBody<T> build() {
			return new JsonBody<>(first());
		}

		@SuppressWarnings("unused")
		public List<JsonBody<T>> buildList() {
			List<JsonBody<T>> list = new ArrayList<>();
			for (T data :
					list()) {
				JsonBody<T> jsonBody = new JsonBody<>(data);
				list.add(jsonBody);
			}
			return list;
		}
	}
}
