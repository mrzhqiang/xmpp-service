package cn.qiang.zhang.xmppservicesample.networks;

import okhttp3.Call;

/**
 * 调用信息、响应代码和返回数据
 * <p>
 * 1.调用信息包含：请求链接url、发起页面tag和请求方法（getInstance、put、post等等）
 * 2.响应代码表示：有响应一般是HTTP code；返回错误和出现异常时通常是-1，表示无法连接服务器或本地网络问题
 * 3.返回数据范围：请求失败创建的错误结果；出现异常创建的异常结果；或者服务器正常返回的有效数据和错误数据
 * <p>
 * Created by mrZQ on 2016/11/23.
 */
public class BaseResponse<T> {

	private int code;

	private Call call;

	private T data;

	public BaseResponse(Call call, int code, T data) {
		this.call = call;
		this.code = code;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public Call getCall() {
		return call;
	}

	public T getData() {
		return data;
	}

	@Override
	public String toString() {
		return "===BaseResponse===" +
				"\ncode:\n" + code +
				"\ncall:\n" + call.request().toString() +
				"\ndata:\n" + data.toString();
	}
}
