package cn.qiang.zhang.xmppservicesample.models;

import java.util.List;

import cn.qiang.zhang.xmppservicesample.BaseData;


/**
 * <p>
 * Created by mrZQ on 2017/1/19.
 */
public class TracesWrap extends BaseData {
    private static final long serialVersionUID = 2093796474697946742L;

    private List<Trace> traceList;

    public List<Trace> getTraceList() {
        return traceList;
    }

    public void setTraceList(List<Trace> traceList) {
        this.traceList = traceList;
    }
}
