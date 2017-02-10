package cn.qiang.zhang.xmppservicesample.models;

/**
 * <p>
 * Created by mrZQ on 2017/1/3.
 */
public class Scene extends Links {
    private static final long serialVersionUID = -8573819566553877352L;

    private String uuid;
    private String name;
    private String status;
    private String created;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
