package cn.qiang.zhang.xmppservicesample.models;


import cn.qiang.zhang.xmppservicesample.BaseData;

/**
 * 展会通知
 * <p>
 * Created by mrZQ on 2016/11/3.
 */
public final class ExhibitionNotice extends BaseData {
    private static final long serialVersionUID = -2004007520934537291L;

    private String title;
    private boolean markRead;
    private String content;
    private long timeStamp;

    private ExhibitionNotice(String title, String content, long timeStamp) {
        this.title = title;
        this.content = content;
        this.timeStamp = timeStamp;
        markRead = false;
    }

    public static ExhibitionNotice make(String title, String content, long time) {
        return new ExhibitionNotice(title, content, time);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isMarkRead() {
        return markRead;
    }

    public void setMarkRead(boolean markRead) {
        this.markRead = markRead;
    }
}
