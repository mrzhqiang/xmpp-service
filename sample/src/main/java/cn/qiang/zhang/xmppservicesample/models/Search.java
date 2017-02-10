package cn.qiang.zhang.xmppservicesample.models;

import java.util.List;

import cn.qiang.zhang.xmppservicesample.BaseData;


/**
 * 搜索（人、展位、展商、展品）
 * <p>
 * Created by mrZQ on 2016/12/2.
 */
public class Search extends Links {
    private static final long serialVersionUID = -8105250400321432832L;

    private long total;
    private String uuid;
    private String created;
    private String keywords;

    private Embedded _embedded;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Embedded getEmbedded() {
        if (_embedded == null) {
            _embedded = new Embedded();
        }
        return _embedded;
    }

    public void setEmbedded(Embedded _embedded) {
        this._embedded = _embedded;
    }

    public List<POI> getResults() {
        return getEmbedded().results;
    }

    private static class Embedded extends BaseData {
        private static final long serialVersionUID = -821122188564509668L;

        List<POI> results;
    }
}
