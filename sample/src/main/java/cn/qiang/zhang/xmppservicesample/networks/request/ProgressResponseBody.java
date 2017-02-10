package cn.qiang.zhang.xmppservicesample.networks.request;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 响应的进度监听body，主要是文件的传递进度回调
 * <p>
 * 看okhttp3的官方api文档介绍说，即使你的内存只有1Gb，他们也可以让你加载超过这个值的文件，或许响应会一直持续
 * 较长的时间，因此读取文件流时，可以用这个显示进度回调。
 * <p>
 * Created by mrZQ on 2016/11/22.
 */
public final class ProgressResponseBody extends ResponseBody {

    // 实际的网络响应体
    private final ResponseBody responseBody;
    // 响应监听器
    private final ProgressListener progressListener;
    // 资源处理
    private BufferedSource bufferedSource;

    /**
     * 必须调用这个构造方法来实现响应的进度监听
     * @param responseBody     实际返回的响应body
     * @param progressListener 响应监听器
     */
    public ProgressResponseBody(@NonNull ResponseBody responseBody,
                                @NonNull ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(BufferedSource source) {
        return new ForwardingSource(source) {
            // 当前读取字节
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                long contentLength = responseBody.contentLength();
                // 将当前已完成的字节数分成100份的进度条值：80 * 100 / 800 = 10 即 10%
                final int progress = (int) (totalBytesRead * 100 / contentLength);
                progressListener.onProgress(totalBytesRead, contentLength, progress);
                return bytesRead;
            }

        };
    }
}
