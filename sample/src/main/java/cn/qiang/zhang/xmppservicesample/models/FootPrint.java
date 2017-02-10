package cn.qiang.zhang.xmppservicesample.models;

import java.util.List;

import cn.qiang.zhang.xmppservicesample.BaseData;


/**
 * 足迹
 * <p>
 * Created by mrZQ on 2017/1/19.
 */
public class FootPrint extends BaseData {
    private static final long serialVersionUID = 7360918065351653397L;

    private List<List<Trace>> footprint;

    public List<List<Trace>> getFootprint() {
        return footprint;
    }

    public void setFootprint(List<List<Trace>> footprint) {
        this.footprint = footprint;
    }
}
