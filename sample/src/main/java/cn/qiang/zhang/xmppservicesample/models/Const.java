package cn.qiang.zhang.xmppservicesample.models;

import java.util.Map;

/**
 * <p>
 * Created by mrZQ on 2017/1/3.
 */
public class Const extends Links {
    private static final long serialVersionUID = 3843866240633973345L;

    private String uuid;
    private Scene scene;
    private long constId;
    private String installationSpec;
    private String deviceId;
    private double angle;
    private Map<String, String> coords;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public long getConstId() {
        return constId;
    }

    public void setConstId(long constId) {
        this.constId = constId;
    }

    public String getInstallationSpec() {
        return installationSpec;
    }

    public void setInstallationSpec(String installationSpec) {
        this.installationSpec = installationSpec;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Map<String, String> getCoords() {
        return coords;
    }

    public void setCoords(Map<String, String> coords) {
        this.coords = coords;
    }
}
