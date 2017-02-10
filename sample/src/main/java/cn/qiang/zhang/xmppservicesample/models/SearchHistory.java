package cn.qiang.zhang.xmppservicesample.models;


import java.util.List;

import cn.qiang.zhang.xmppservicesample.BaseData;


/**
 * 搜索历史的返回
 * <p>
 * Created by mrZQ on 2017/1/4.
 */
public final class SearchHistory extends BaseData {
    private static final long serialVersionUID = 8558750627242444019L;

    private List<History> searchhistory;

    public List<History> getSearchHistory() {
        return searchhistory;
    }

    public void setSearchHistory(List<History> searchHistory) {
        this.searchhistory = searchHistory;
    }

    public static class History extends BaseData {
        private static final long serialVersionUID = -159808793749543190L;

        public String uuid;
        public String userId;
        public  String keywords;
        public  long total;
        public  String created;
        public  boolean saveState;

        public  List<Result> results;
    }

    public static class Result extends POI {
        private static final long serialVersionUID = 304779667011465850L;
        private User user;
        private Actor actor;
        private Booth booth;
        private Product product;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Actor getActor() {
            return actor;
        }

        public void setActor(Actor actor) {
            this.actor = actor;
        }

        public Booth getBooth() {
            return booth;
        }

        public void setBooth(Booth booth) {
            this.booth = booth;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }

}
