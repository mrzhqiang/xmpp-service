package cn.qiang.zhang.xmppservicesample.models;

import java.util.ArrayList;
import java.util.List;

import cn.qiang.zhang.xmppservicesample.BaseData;


/**
 * 系统推荐
 * <p>
 * Created by mrZQ on 2017/1/17.
 */
public class SystemRecommend extends BaseData {
    private static final long serialVersionUID = 6699455517107063365L;

    private List<Recommend> systemRecommend;
    private String sourcesInfo;

    public List<RecommendData> toData() {
        List<RecommendData> datas = new ArrayList<>();
        if (systemRecommend != null && systemRecommend.size() > 0) {
            for (Recommend recommend : systemRecommend) {
                int grede = recommend.getGrade();
                if (recommend.getMatchActors() != null && recommend.getMatchActors().size() > 0) {
                    for (Actor actor : recommend.getMatchActors()) {
                        RecommendData data = new RecommendData();
                        data.setDrade(grede);
                        data.setActor(actor);
                        datas.add(data);
                    }
                }
                if (recommend.getMatchUsers() != null && recommend.getMatchUsers().size() > 0) {
                    for (User user : recommend.getMatchUsers()) {
                        RecommendData data = new RecommendData();
                        data.setDrade(grede);
                        data.setUser(user);
                        datas.add(data);
                    }
                }

            }
        }
        return datas;
    }

    public List<Recommend> getSystemRecommend() {
        return systemRecommend;
    }

    public void setSystemRecommend(List<Recommend> systemRecommend) {
        this.systemRecommend = systemRecommend;
    }

    public String getSourcesInfo() {
        return sourcesInfo;
    }

    public void setSourcesInfo(String sourcesInfo) {
        this.sourcesInfo = sourcesInfo;
    }

    public static class Recommend extends BaseData {
        private static final long serialVersionUID = 4687826665915232964L;

        private int grade;
        private List<Actor> matchActors;
        private List<User> matchUsers;

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public List<Actor> getMatchActors() {
            return matchActors;
        }

        public void setMatchActors(List<Actor> matchActors) {
            this.matchActors = matchActors;
        }

        public List<User> getMatchUsers() {
            return matchUsers;
        }

        public void setMatchUsers(List<User> matchUsers) {
            this.matchUsers = matchUsers;
        }
    }
}
