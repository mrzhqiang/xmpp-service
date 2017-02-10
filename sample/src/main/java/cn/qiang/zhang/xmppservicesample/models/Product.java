package cn.qiang.zhang.xmppservicesample.models;

/**
 * <p>
 * Created by mrZQ on 2016/12/19.
 */
public class Product extends MetaProfiles {
    private static final long serialVersionUID = -3360569369079351272L;

    ///////////////////////////////////////////////////////////////////////////
    // 产品简介、描述、档案 profiles
    ///////////////////////////////////////////////////////////////////////////
    // 品牌
    private static final String BRAND = "brand";
    // 采购价格
    private static final String BUY_PRICE = "buyPrice";
    // 销售价格
    private static final String SALE_PRICE = "salePrice";
    // 标签
    private static final String LABELS = "labels";

    private long id;
    private String name;
    private String description;
    private long exhibitionId;
    private long actorId;
    private String boothId;

    private long typeId;
    private String position;

    private String comId;
    private String exhId;
    private String pdtId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getActorId() {
        return actorId;
    }

    public void setActorId(long actorId) {
        this.actorId = actorId;
    }

    public String getBoothId() {
        return boothId;
    }

    public void setBoothId(String boothId) {
        this.boothId = boothId;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getExhId() {
        return exhId;
    }

    public void setExhId(String exhId) {
        this.exhId = exhId;
    }

    public String getPdtId() {
        return pdtId;
    }

    public void setPdtId(String pdtId) {
        this.pdtId = pdtId;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Profiles 属性
    ///////////////////////////////////////////////////////////////////////////

    public String getBrand() {
        return getProfiles(BRAND);
    }

    public void setBrand(String value) {
        putProfiles(BRAND, value);
    }

    public String getBuyPrice() {
        return getProfiles(BUY_PRICE);
    }

    public void setBuyPrice(String value) {
        putProfiles(BUY_PRICE, value);
    }


    public String getSalePrice() {
        return getProfiles(SALE_PRICE);
    }

    public void setSalePrice(String value) {
        putProfiles(SALE_PRICE, value);
    }

    public String getLabels() {
        return getProfiles(LABELS);
    }

    public void setLabels(String value) {
        putProfiles(LABELS, value);
    }


}
