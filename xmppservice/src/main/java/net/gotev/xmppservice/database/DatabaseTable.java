package net.gotev.xmppservice.database;

/**
 * @author Aleksandar Gotev
 */
public interface DatabaseTable {
    String getCreateSql();
    String getDropSql();
}
