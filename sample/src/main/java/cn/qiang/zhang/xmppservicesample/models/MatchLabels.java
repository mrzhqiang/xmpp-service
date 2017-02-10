package cn.qiang.zhang.xmppservicesample.models;

import java.util.List;

import cn.qiang.zhang.xmppservicesample.BaseData;


/**
 * <p>
 * Created by mrZQ on 2016/12/20.
 */
public final class MatchLabels extends BaseData {

    private static final long serialVersionUID = 8690332498925538206L;

    private List<String> matchLabels;

    public List<String> getMatchLabels() {
        return matchLabels;
    }

    public void setMatchLabels(List<String> matchLabels) {
        this.matchLabels = matchLabels;
    }
}
