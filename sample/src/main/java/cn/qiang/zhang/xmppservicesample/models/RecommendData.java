package cn.qiang.zhang.xmppservicesample.models;


import cn.qiang.zhang.xmppservicesample.BaseData;

/**
 * <p>
 * Created by mrZQ on 2017/1/18.
 */
public class RecommendData extends BaseData {
    private static final long serialVersionUID = 8683325483719549471L;

    private int drade;
    private Actor actor;
    private User user;

    public int getDrade() {
        return drade;
    }

    public void setDrade(int drade) {
        this.drade = drade;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
