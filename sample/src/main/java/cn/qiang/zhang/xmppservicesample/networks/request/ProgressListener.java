package cn.qiang.zhang.xmppservicesample.networks.request;

/**
 * 上传/下载的实时进度监听
 * <p>
 * Created by mrZQ on 2016/11/22.
 */
public interface ProgressListener {

	/**
	 * @param bytesRead 当前字节
	 * @param contentLength 内容长度
	 * @param progress 当前进度
	 */
	void onProgress(long bytesRead, long contentLength, int progress);

}
