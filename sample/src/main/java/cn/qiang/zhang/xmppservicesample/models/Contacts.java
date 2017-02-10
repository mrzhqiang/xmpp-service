package cn.qiang.zhang.xmppservicesample.models;

import net.gotev.xmppservice.XmppRosterEntry;
import net.gotev.xmppservice.database.models.Message;

import java.util.List;

import cn.qiang.zhang.xmppservicesample.BaseData;

/**
 * <p>
 * Created by mrZQ on 2016/12/26.
 */
public final class Contacts extends BaseData {
    private static final long serialVersionUID = -8477430817275424613L;

    private UserLocal user;
    private String name;
    private List<User> userList;
    private boolean isCheck;
    private List<Message> messageList;
    private XmppRosterEntry xmppRosterEntry;

    public XmppRosterEntry getXmppRosterEntry() {
        return xmppRosterEntry;
    }

    public void setXmppRosterEntry(XmppRosterEntry xmppRosterEntry) {
        this.xmppRosterEntry = xmppRosterEntry;
    }

    public UserLocal getUser() {
        return user;
    }

    public void setUser(UserLocal user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
