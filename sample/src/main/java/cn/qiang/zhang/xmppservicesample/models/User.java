package cn.qiang.zhang.xmppservicesample.models;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息、资料、认证权限
 * <p>
 * Created by mrZQ on 2016/11/15.
 */
public class User extends MetaProfiles {
    private static final long serialVersionUID = 2636805651453249511L;

    private static final String REAL_NAME = "realName";
    private static final String COMPANY = "company";
    private static final String DEPARTMENT = "department";
    private static final String RANK = "rank";
    private static final String EMAIL = "email";
    private static final String CITY = "city";
    private static final String ADDRESS = "address";
    private static final String FAX = "fax";
    private static final String ZIP_CODE = "zipCode";
    private static final String TELEPHONE = "telephone";
    private static final String INDUSTRY = "industry";

    long id;
    String role;
    String nickName;
    String externalId;
    String mobile;
    String openId;
    String avatar;
    String password;
    String created;

    List<String> labels;
    String JID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<String> getLabels() {
        if (labels == null) {
            labels = new ArrayList<>();
        }
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getJID() {
        return JID;
    }

    public void setJID(String JID) {
        this.JID = JID;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 从profiles中获取信息
    ///////////////////////////////////////////////////////////////////////////
    public String getIndustry() {
        return getProfiles(INDUSTRY);
    }

    @SuppressWarnings("unused")
    public void setIndustry(String industry) {
        putProfiles(INDUSTRY, industry);
    }

    public String getAddress() {
        return getProfiles(ADDRESS);
    }

    public void setAddress(String address) {
        putProfiles(ADDRESS, address);
    }

    public String getCity() {
        return getProfiles(CITY);
    }

    public void setCity(String city) {
        putProfiles(CITY, city);
    }

    public String getCompany() {
        return getProfiles(COMPANY);
    }

    public void setCompany(String company) {
        putProfiles(COMPANY, company);
    }

    public String getDepartment() {
        return getProfiles(DEPARTMENT);
    }

    public void setDepartment(String department) {
        putProfiles(DEPARTMENT, department);
    }

    public String getEmail() {
        return getProfiles(EMAIL);
    }

    public void setEmail(String email) {
        putProfiles(EMAIL, email);
    }

    public String getFax() {
        return getProfiles(FAX);
    }

    public void setFax(String fax) {
        putProfiles(FAX, fax);
    }

    public String getRank() {
        return getProfiles(RANK);
    }

    public void setRank(String rank) {
        putProfiles(RANK, rank);
    }

    public String getRealName() {
        return getProfiles(REAL_NAME);
    }

    public void setRealName(String realName) {
        putProfiles(REAL_NAME, realName);
    }

    public String getTelephone() {
        return getProfiles(TELEPHONE);
    }

    public void setTelephone(String telephone) {
        putProfiles(TELEPHONE, telephone);
    }

    public String getZipCode() {
        return getProfiles(ZIP_CODE);
    }

    public void setZipCode(String zipCode) {
        putProfiles(ZIP_CODE, zipCode);
    }

    public String showBestName() {
        if (!TextUtils.isEmpty(nickName)) {
            return nickName;
        }
        if (!TextUtils.isEmpty(getRealName())) {
            return getRealName();
        }
        return mobile;
    }

}
