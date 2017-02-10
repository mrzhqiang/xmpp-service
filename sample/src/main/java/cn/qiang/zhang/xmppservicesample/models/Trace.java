package cn.qiang.zhang.xmppservicesample.models;

import java.util.Date;

import cn.qiang.zhang.xmppservicesample.BaseData;
import cn.qiang.zhang.xmppservicesample.utils.Util;


/**
 * 足迹
 * <p>
 * Created by mrZQ on 2017/1/14.
 */
public class Trace extends BaseData {
    private static final long serialVersionUID = -6119577228966618071L;

    private String created;
    private Location location = new Location();
    private long groupIndex;

    public long getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(long groupIndex) {
        this.groupIndex = groupIndex;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static Trace create(float[] lonat) {
        Trace trace = new Trace();
        String created = Util.YMDHM_ZZZ.format(new Date());
        trace.setCreated(created);
        trace.getLocation().getPosition().setLon(lonat[0]);
        trace.getLocation().getPosition().setLat(lonat[1]); // 1纬度
        trace.getLocation().setConstId(1234);
        trace.getLocation().setSceneId("4321");
        return trace;
    }
}
