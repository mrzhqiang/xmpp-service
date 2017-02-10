package cn.qiang.zhang.xmppservicesample.models;

import java.util.List;

import cn.qiang.zhang.xmppservicesample.BaseData;

/**
 * 展商
 * <p>
 * Created by mrZQ on 2016/12/19.
 */
public class Actor extends MetaProfiles {
    private static final long serialVersionUID = 6235141871052089895L;

    /**
     * logo 公司logo
     * code 展商编号
     * address 展商地址
     * saleDate 销售日期
     * productIntro 展品简介
     * leader 法人
     * leaderLinkInfo 法人联系信息
     * labels 展商标签
     * <p>
     * market --忽然之间多出来的字段
     */
    private static final String LOGO = "logo";
    private static final String CODE = "code";
    private static final String ADDRESS = "address";
    private static final String SALE_DATE = "saleDate";
    private static final String PRODUCT_INTRO = "productIntro";
    private static final String LEADER = "leader";
    private static final String LEADER_LINK_INFO = "leaderLinkInfo";
    private static final String LABELS = "labels";
    private static final String MARKET = "market";

    private long actorId;
    private String name;
    private String description;
    private long exhibitionId;
    private long boothId;

    private List<String> actorPicList;
    private List<ActorPtd> productList;
    private String exhId;
    private String comId;
    private String atrId;
    private Coordinate position;
    private boolean isLike;

    private String atrCode;

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public long getActorId() {
        return actorId;
    }

    public void setActorId(long actorId) {
        this.actorId = actorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(long exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public long getBoothId() {
        return boothId;
    }

    public void setBoothId(long boothId) {
        this.boothId = boothId;
    }

    public List<String> getActorPicList() {
        return actorPicList;
    }

    public void setActorPicList(List<String> actorPicList) {
        this.actorPicList = actorPicList;
    }

    public List<ActorPtd> getProductList() {
        return productList;
    }

    public void setProductList(List<ActorPtd> productList) {
        this.productList = productList;
    }

    public String getExhId() {
        return exhId;
    }

    public void setExhId(String exhId) {
        this.exhId = exhId;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getAtrId() {
        return atrId;
    }

    public void setAtrId(String atrId) {
        this.atrId = atrId;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public String getAtrCode() {
        return atrCode;
    }

    public void setAtrCode(String atrCode) {
        this.atrCode = atrCode;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Profiles属性
    ///////////////////////////////////////////////////////////////////////////
    public String getLogo() {
        return getProfiles(LOGO);
    }

    public void setLogo(String value) {
        putProfiles(LOGO, value);
    }

    public String getCode() {
        return getProfiles(CODE);
    }

    public void setCode(String value) {
        putProfiles(CODE, value);
    }

    public String getAddress() {
        return getProfiles(ADDRESS);
    }

    public void setAddress(String value) {
        putProfiles(ADDRESS, value);
    }

    public String getSaleDate() {
        return getProfiles(SALE_DATE);
    }

    public void setSaleDate(String value) {
        putProfiles(SALE_DATE, value);
    }

    public String getProductIntro() {
        return getProfiles(PRODUCT_INTRO);
    }

    public void setProductIntro(String value) {
        putProfiles(PRODUCT_INTRO, value);
    }

    public String getLeader() {
        return getProfiles(LEADER);
    }

    public void setLeader(String value) {
        putProfiles(LEADER, value);
    }

    public String getLeaderLinkInfo() {
        return getProfiles(LEADER_LINK_INFO);
    }

    public void setLeaderLinkInfo(String value) {
        putProfiles(LEADER_LINK_INFO, value);
    }

    public String getLabels() {
        return getProfiles(LABELS);
    }

    public void setLabels(String value) {
        putProfiles(LABELS, value);
    }

    public String getMarket() {
        return getProfiles(MARKET);
    }

    public void setMarket(String value) {
        putProfiles(MARKET, value);
    }

    private static class ActorPtd extends BaseData {
        private static final long serialVersionUID = -5150403484969086837L;

        long id;
        String brand;// 品牌
        String intro;// 描述
        String name;// 名字
        String typeName;// 类型名字
        List<String> productPic;// 展品图片

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<String> getProductPic() {
            return productPic;
        }

        public void setProductPic(List<String> productPic) {
            this.productPic = productPic;
        }
    }
}
