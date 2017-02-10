package cn.qiang.zhang.xmppservicesample.networks;

import okhttp3.Response;

/**
 * 数据转换接口
 * <p>
 * 通常它是json类型，或文件、位图、xml等。
 * <p>
 * Created by mrZQ on 2016/11/21.
 */
public interface IConverter<T> {
	/**
	 * 响应数据处理
	 * @param response okhttp3的回调数据
	 * @return 转换结果
	 * @throws Exception 转换过程中可能抛出的异常
	 */
	T convert(Response response) throws Exception;
}
