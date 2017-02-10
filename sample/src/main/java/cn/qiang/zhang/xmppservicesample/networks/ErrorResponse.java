package cn.qiang.zhang.xmppservicesample.networks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * 服务器错误，通过返回的json解析为实体，极端情况下主动创建给调试打印
 * <p>
 * Created by mrZQ on 2016/11/16.
 */
public final class ErrorResponse {

    private int httpStatus;
    private int code;
    private String property;
    private String message;
    private String developerMessage;
    private String moreInfo;

    /*本地错误消息，通常是捕捉抛出的异常*/
    private Map<String, String> localMessage;

    ErrorResponse() {}

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public Map<String, String> getLocalMessage() {
        return localMessage;
    }

    public void setLocalMessage(Map<String, String> localMessage) {
        this.localMessage = localMessage;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
