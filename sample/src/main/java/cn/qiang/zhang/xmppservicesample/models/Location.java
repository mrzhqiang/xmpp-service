package cn.qiang.zhang.xmppservicesample.models;


import cn.qiang.zhang.xmppservicesample.BaseData;

/**
 * 位置
 * <p>
 * Created by mrZQ on 2017/1/14.
 */
public class Location extends BaseData {
    private static final long serialVersionUID = 5059563119189895766L;

    private String sceneId;
    private int constId;

    private Coordinate position = new Coordinate();

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public int getConstId() {
        return constId;
    }

    public void setConstId(int constId) {
        this.constId = constId;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
