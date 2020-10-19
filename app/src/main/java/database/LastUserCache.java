package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.LinkedList;

import models.ObjectsModel;

public class LastUserCache extends AbstractUserCache {

    private final static String CREATE_USER = "create table if not exists user (user_id text primary key, user_name text unique not null, password text not null, mode_flag integer not null, connection_flag integer not null, unique(user_id, password)) without rowid";
    public final static String INSERT_USER = "insert into user (user_id,user_name,password,mode_flag,connection_flag) values (?,?,?,?,?)";
    public final static String DELETE_USER = "delete from user";
    public final static String NAME = "user_cache.db";
    public final static int VERSION = 1;

    public LastUserCache(Context context) {
        super(context, LastUserCache.NAME, null, LastUserCache.VERSION);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        this.onCreate(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        if (!this.hasResult("select * user")) {
            try {
                sqLiteDatabase.execSQL(LastUserCache.CREATE_USER);
            } catch (Exception exception) {

            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }

    @Override
    public void insertUser(String userId, String username, String password, int mode, int connectionAction) {
        if (userId.length() > 0) {
            if (password.length() > 0) {
                if (mode == AbstractUserCache.NO_MODE || mode == AbstractUserCache.ONLY_PASSWORD_MODE || mode == AbstractUserCache.CREDENTIALS_MODE || mode == AbstractUserCache.AUTO) {
                    if (connectionAction == AbstractUserCache.CONNECTED || connectionAction == AbstractUserCache.DISCONNECTED) {
                        SQLiteDatabase database = this.getWritableDatabase();
                        database.execSQL(LastUserCache.DELETE_USER);
                        SQLiteStatement sqLiteStatement = database.compileStatement(LastUserCache.INSERT_USER);
                        sqLiteStatement.bindString(1, userId);
                        sqLiteStatement.bindString(2, username);
                        sqLiteStatement.bindString(3, password);
                        sqLiteStatement.bindLong(4, mode);
                        sqLiteStatement.bindLong(5, connectionAction);
                        sqLiteStatement.executeInsert();
                    }
                }
            }
        }
    }


    @Override
    public ObjectsModel getUser() {
        LinkedList<ObjectsModel> objectsModelLinkedList = this.getObjectsModels("select * from user");
        if (objectsModelLinkedList.size() == 1) {
            return objectsModelLinkedList.get(0);
        } else {
            ObjectsModel objectsModel = new ObjectsModel();
            objectsModel.addCouple("", "");
            objectsModel.addCouple("", "");
            objectsModel.addCouple("", "");
            objectsModel.addCouple("", AbstractUserCache.NO_MODE);
            objectsModel.addCouple("", AbstractUserCache.DISCONNECTED);
            return objectsModel;
        }
    }

    @Override
    public void deleteUser(String userId) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from user where user_id = '" + userId + "'");
    }

    @Override
    protected String getTablename(String query) {
        return "user";
    }

    @Override
    protected ObjectsModel buildObjectsModel(String tablename) {
        if (tablename.compareTo("user") == 0) {
            ObjectsModel objectsModel =  new ObjectsModel();
            objectsModel.setTablename(tablename);
            return objectsModel;
        }
        return null;
    }

}
