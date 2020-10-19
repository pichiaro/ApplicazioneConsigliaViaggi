package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

import models.ObjectsModel;

public abstract class AbstractObjectsModelReadableDatabase extends SQLiteOpenHelper {

    public AbstractObjectsModelReadableDatabase(Context context, String name, CursorFactory cursorFactory, int version) {
        super(context, name, cursorFactory, version);
    }

    public Cursor getCursor(String query) {
        Cursor cursor = null;
        if (query.length() > 0) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();
                cursor = db.rawQuery(query, null);
            } catch (Exception exception) {
                return null;
            }
        }
        return cursor;
    }

    public boolean hasResult(String query) {
        query = query + " limit 1";
        Cursor cursor = this.getCursor(query);
        if (cursor == null) {
            return false;
        }
        if (cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    public LinkedList<ObjectsModel> getObjectsModels(String query) {
        LinkedList<ObjectsModel> objectsModels = new LinkedList<>();
        if (query.length() > 0) {
            try {
                Cursor cursor = this.getCursor(query);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int columnCount = cursor.getColumnCount();
                        String tableName = this.getTablename(query);
                        do {
                            ObjectsModel objectsModel = this.buildObjectsModel(tableName);
                            for (int i = 0; i < columnCount; i++) {
                                String attribute = cursor.getColumnName(i);
                                Object value = null;
                                int type = cursor.getType(i);
                                switch (type) {
                                    case Cursor.FIELD_TYPE_STRING:
                                        value = cursor.getString(i);
                                        break;
                                    case Cursor.FIELD_TYPE_FLOAT:
                                        value = cursor.getFloat(i);
                                        break;
                                    case Cursor.FIELD_TYPE_INTEGER:
                                        value = cursor.getInt(i);
                                        break;
                                    case Cursor.FIELD_TYPE_BLOB:
                                        value = cursor.getBlob(i);
                                        break;
                                }
                                objectsModel.addCouple(attribute, value);
                            }
                            objectsModels.addFirst(objectsModel);
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
            } catch (Exception exception) {
                Log.d("Message: ",exception.getMessage());
            }
        }
        return objectsModels;
    }

    protected abstract String getTablename(String query);

    protected abstract ObjectsModel buildObjectsModel(String tableName);

}