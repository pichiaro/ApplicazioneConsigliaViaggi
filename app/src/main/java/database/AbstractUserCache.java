package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import models.ObjectsModel;

public abstract class AbstractUserCache extends AbstractObjectsModelReadableDatabase  {

    public final static int ONLY_PASSWORD_MODE = 0;
    public final static int CREDENTIALS_MODE = 1;
    public final static int NO_MODE = 2;
    public final static int AUTO = 3;
    public final static int USER_ID = 0;
    public final static int USERNAME = 1;
    public final static int PASSWORD = 2;
    public final static int MODE = 3;
    public final static int CONN = 4;
    public final static int CONNECTED = 0;
    public final static int DISCONNECTED = 1;

    public AbstractUserCache(Context context, String name, CursorFactory cursorFactory, int version) {
        super(context, name, cursorFactory, version);
    }

    public abstract ObjectsModel getUser();

    public abstract void insertUser(String userId, String username, String password, int mode, int connectionAction);

    public abstract void deleteUser(String userId);
}
