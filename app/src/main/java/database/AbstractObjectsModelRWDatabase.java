package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import java.util.AbstractList;
import java.util.Iterator;


public abstract class AbstractObjectsModelRWDatabase extends AbstractObjectsModelReadableDatabase {

    private final ContentValues contentValues = new ContentValues();

    public AbstractObjectsModelRWDatabase(Context context, String name, CursorFactory cursorFactory, int version) {
        super(context, name, cursorFactory, version);
    }

    public ContentValues getContentValues() {
        return this.contentValues;
    }

    public boolean insertIntoTableValues(String tableName, AbstractList<String> attributes, AbstractList<Object> values) {
        if (tableName.length() > 0 && attributes.size() == values.size()) {
            Iterator<String> stringIterator = attributes.iterator();
            Iterator<Object> objectIterator = values.iterator();
            while (stringIterator.hasNext()) {
                String attribute = stringIterator.next();
                if (attribute == null) {
                    this.contentValues.clear();
                    return false;
                }
                if (attribute.length() == 0) {
                    this.contentValues.clear();
                    return false;
                }
                Object object = objectIterator.next();
                if (object == null) {
                    this.contentValues.clear();
                    return false;
                }
                if (object instanceof String) {
                    this.contentValues.put(attribute, (String) object);
                } else {
                    if (object instanceof Integer) {
                        this.contentValues.put(attribute, (Integer) object);
                    } else {
                        if (object instanceof Float) {
                            this.contentValues.put(attribute, (Float) object);
                        } else {
                            if (object instanceof Double) {
                                this.contentValues.put(attribute, (Double) object);
                            } else {
                                if (object instanceof Boolean) {
                                    this.contentValues.put(attribute, (Boolean) object);
                                } else {
                                    if (object instanceof Short) {
                                        this.contentValues.put(attribute, (Short) object);
                                    } else {
                                        if (object instanceof Long) {
                                            this.contentValues.put(attribute, (Long) object);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            try {
                SQLiteDatabase writableDatabase = this.getWritableDatabase();
                writableDatabase.insertOrThrow(tableName, null, this.contentValues);
                this.contentValues.clear();
                return true;
            } catch (SQLException exception) {
                this.contentValues.clear();
                return false;
            }
        }
        return false;
    }

}