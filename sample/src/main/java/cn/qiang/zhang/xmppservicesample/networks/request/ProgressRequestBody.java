package cn.qiang.zhang.xmppservicesample.networks.request;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 请求的进度监听body，主要是文件的上传进度回调
 * <p>
 * Created by mrZQ on 2016/11/22.
 */
public final class ProgressRequestBody extends RequestBody {

    // 实际的网络请求
    private final RequestBody requestBody;
    // 上传请求的进度回调——通常是对本地文件流的一个监听
    private final ProgressListener requestListener;
    // 资源处理
    private BufferedSink bufferedSink;

    /**
     * 只能调用这个方法来实现上传进度监听
     * @param requestListener 请求监听器，不能为null
     * @param requestBody     实际网络请求body，无法为null
     */
    public ProgressRequestBody(@NonNull RequestBody requestBody,
                               @NonNull ProgressListener requestListener) {
        this.requestBody = requestBody;
        this.requestListener = requestListener;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(sink(sink));
        }
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    private Sink sink(BufferedSink sink) {
        return new ForwardingSink(sink) {
            long bytesWrite = 0L;
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    contentLength = requestBody.contentLength();
                }
                bytesWrite += byteCount;
                // 将当前已完成的字节数分成100份的进度条值：88 * 100 / 800 = 11 即 11%
                final int progress = (int) (bytesWrite * 100 / contentLength);
                requestListener.onProgress(bytesWrite, contentLength, progress);
            }
        };
    }
}
