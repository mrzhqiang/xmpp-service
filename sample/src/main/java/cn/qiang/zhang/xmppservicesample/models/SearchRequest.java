package cn.qiang.zhang.xmppservicesample.models;


import cn.qiang.zhang.xmppservicesample.BaseData;

/**
 * <p>
 * Created by mrZQ on 2016/12/22.
 */
public final class SearchRequest extends BaseData {
    private static final long serialVersionUID = 6623374969875661384L;

    private String keywords;
    private String types;
    private int form;
    private int size;
    private boolean saveState;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public boolean isSaveState() {
        return saveState;
    }

    public void setSaveState(boolean saveState) {
        this.saveState = saveState;
    }
}
