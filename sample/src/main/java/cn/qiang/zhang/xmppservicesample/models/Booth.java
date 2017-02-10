package cn.qiang.zhang.xmppservicesample.models;


/**
 * 展位
 * <p>
 * Created by mrZQ on 2016/12/19.
 */
public class Booth extends MetaProfiles {
    private static final long serialVersionUID = 2000035958945711391L;

    /**
     * srmId 展厅id
     * boothCode 展位号
     * boothX
     * boothY
     * width 实际宽度
     * length 实际高度
     * area 展位面积
     * owner 所有人
     * place 展位位置
     * money 展位总价
     */
    private static final String SRM_ID = "srmId";
    private static final String BOOTH_CODE = "boothCode";
    private static final String BOOTH_X = "boothX";
    private static final String BOOTH_Y = "boothY";
    private static final String WIDTH = "width";
    private static final String LENGTH = "length";
    private static final String AREA = "area";
    private static final String OWNER = "owner";
    private static final String PLACE = "place";
    private static final String MONEY = "money";

    private long boothId;
    private String name;
    private String description;
    private long exhibitionId;
    private String created;
    private String sceneId;
    private String actorId;
    private String actorName;
    private String position;

    private String exhId;
    private String bthId;
    private String atrId;
    private Scene scene;
    private String coord;

    public long getBoothId() {
        return boothId;
    }

    public void setBoothId(long boothId) {
        this.boothId = boothId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getExhId() {
        return exhId;
    }

    public void setExhId(String exhId) {
        this.exhId = exhId;
    }

    public String getBthId() {
        return bthId;
    }

    public void setBthId(String bthId) {
        this.bthId = bthId;
    }

    public String getAtrId() {
        return atrId;
    }

    public void setAtrId(String atrId) {
        this.atrId = atrId;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Profiles属性
    ///////////////////////////////////////////////////////////////////////////
    public String getSrmId() {
        return getProfiles(SRM_ID);
    }

    public void setSrmId(String value) {
        putProfiles(SRM_ID, value);
    }

    public String getBoothCode() {
        return getProfiles(BOOTH_CODE);
    }

    public void setBoothCode(String value) {
        putProfiles(BOOTH_CODE, value);
    }

    public String getBoothX() {
        return getProfiles(BOOTH_X);
    }

    public void setBoothX(String value) {
        putProfiles(BOOTH_X, value);
    }

    public String getBoothY() {
        return getProfiles(BOOTH_Y);
    }

    public void setBoothY(String value) {
        putProfiles(BOOTH_Y, value);
    }

    public String getWidth() {
        return getProfiles(WIDTH);
    }

    public void setWidth(String value) {
        putProfiles(WIDTH, value);
    }

    public String getLength() {
        return getProfiles(LENGTH);
    }

    public void setLength(String value) {
        putProfiles(LENGTH, value);
    }

    public String getArea() {
        return getProfiles(AREA);
    }

    public void setArea(String value) {
        putProfiles(AREA, value);
    }

    public String getOwner() {
        return getProfiles(OWNER);
    }

    public void setOwner(String value) {
        putProfiles(OWNER, value);
    }

    public String getPlace() {
        return getProfiles(PLACE);
    }

    public void setPlace(String value) {
        putProfiles(PLACE, value);
    }

    public String getMoney() {
        return getProfiles(MONEY);
    }

    public void setMoney(String value) {
        putProfiles(MONEY, value);
    }

}
