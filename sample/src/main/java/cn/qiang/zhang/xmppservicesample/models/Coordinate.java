package cn.qiang.zhang.xmppservicesample.models;


import cn.qiang.zhang.xmppservicesample.BaseData;

/**
 * 地球经纬度坐标
 * <p>
 * Created by mrZQ on 2017/1/14.
 */
public class Coordinate extends BaseData {
    private static final long serialVersionUID = 6760723493358940804L;

    private double lat; // 经度
    private double lon; // 纬度

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
