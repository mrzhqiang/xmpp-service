package cn.qiang.zhang.xmppservicesample.models;


import cn.qiang.zhang.xmppservicesample.BaseData;

/**
 * POI：兴趣点、信息点
 * <p>
 * Created by mrZQ on 2016/12/19.
 */
public class POI extends MetaProfiles {
    private static final long serialVersionUID = -8105250400321432832L;

    private double score;
    private String uuid;
    private String created;
    private String version;
    private String type;
    private String location;

    private long userId;
    private long actorId;
    private long boothId;
    private long productId;

    // 这里依赖的是POI的静态内部类，降低模型之间的耦合
    private Embedded _embedded;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getActorId() {
        return actorId;
    }

    public void setActorId(long actorId) {
        this.actorId = actorId;
    }

    public long getBoothId() {
        return boothId;
    }

    public void setBoothId(long boothId) {
        this.boothId = boothId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public User getUserPOI() {
        if (type.equals("user")) {
            return _embedded.user;
        }
        return null;
    }

    public Actor getActorPOI() {
        if (type.equals("actor")) {
            return _embedded.actor;
        }
        return null;
    }

    public Booth getBoothPOI() {
        if (type.equals("booth")) {
            return _embedded.booth;
        }
        return null;
    }

    public Product getProductPOI() {
        if (type.equals("product")) {
            return _embedded.product;
        }
        return null;
    }

    private static class Embedded extends BaseData {
        private static final long serialVersionUID = -6681760653999014510L;
        User user;
        Actor actor;
        Booth booth;
        Product product;
    }
}
