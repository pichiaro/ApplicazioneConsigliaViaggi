package components;

import java.util.AbstractList;

import models.ObjectsModel;

public abstract class AbstractQueriesInterface {

    public abstract void initialize();

    public abstract AbstractList<ObjectsModel> select(String query);

    public abstract boolean insert(String tableName, AbstractList<String> attributes, AbstractList<Object> values);

    public abstract boolean executeCommand(String update);

    public abstract AbstractList<String> getSuggestions();

    public abstract String getUserIDAttribute();

    public abstract String getUserNicknameAttribute();

    public abstract String getUserPasswordAttribute();

    public abstract AbstractList<String> getAttributes(String tablename);

    public abstract String getTableName(Object object);

    public abstract String getUserTableName();

    public String getExistUserIDSelect(String userId, boolean isText) {
        if (isText) {
            return "select * from " + this.getUserTableName() + " where " + this.getUserIDAttribute() + " = '" + userId + "'";
        }
        else {
            return "select * from " + this.getUserTableName() + " where " + this.getUserIDAttribute() + " = " + userId;
        }
    }

    public String getExistNicknameSelect(String nickname) {
        return "select * from " + this.getUserTableName() + " where " + this.getUserNicknameAttribute() + " = '" + nickname + "'";
    }

    public String getPasswordSelect(String userId, boolean isText) {
        if (isText) {
            return "select " + this.getUserPasswordAttribute() + " from " + this.getUserTableName() + " where " + this.getUserIDAttribute() + " = '" + userId + "'";
        }
        else {
            return "select " + this.getUserPasswordAttribute() + " from " + this.getUserTableName() + " where " + this.getUserIDAttribute() + " = " + userId;
        }
    }

}
