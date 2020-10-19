package models;

import java.util.Iterator;
import java.util.LinkedList;

public class ObjectsModel {

    private LinkedList<String> attributes = new LinkedList<>();
    private LinkedList<Object> values = new LinkedList<>();
    private String tablename;

    public ObjectsModel() {

    }

    public ObjectsModel(ObjectsModel objectsModel) {
        if (objectsModel != null) {
            if (objectsModel.tablename != null) {
                if (objectsModel.attributes != null) {
                    if (objectsModel.values != null) {
                        this.tablename = objectsModel.tablename;
                        this.attributes = objectsModel.attributes;
                        this.values = objectsModel.values;
                    }
                }
            }
        }
    }

    public void addCouple(String attribute, Object value) {
        if (attribute != null) {
            this.attributes.add(attribute);
            this.values.add(value);
        }
    }

    public void addFirstCouple(String attribute, Object value) {
        this.attributes.addFirst(attribute);
        this.values.addFirst(value);
    }

    public void updateValue(String attribute, Object value) {
        Iterator<String> stringIterator = this.attributes.iterator();
        Iterator<Object> objectIterator = this.values.iterator();
        int index = 0;
        while (stringIterator.hasNext()) {
            String string = stringIterator.next();
            objectIterator.next();
            if (string.compareTo(attribute) == 0) {
                objectIterator.remove();
                this.values.add(index, value);
                break;
            }
            index++;
        }
    }


    public void removeCouple(int i) {
        if (i >= 0 && i < this.attributes.size()) {
            this.attributes.remove(i);
            this.values.remove(i);
        }
    }

    public String getAttribute(int i) {
        if (i >= 0 && i < this.attributes.size()) {
            String attribute = this.attributes.get(i);
            return attribute;
        }
        return null;
    }

    public Object getValue(int i) {
        if (i >= 0 && i < this.values.size()) {
            Object value = this.values.get(i);
            return value;
        }
        return null;
    }

    public Object getValue(String attribute) {
        Iterator<String> stringIterator = this.attributes.iterator();
        Iterator<Object> objectIterator = this.values.iterator();
        while (stringIterator.hasNext()) {
            String string = stringIterator.next();
            Object object = objectIterator.next();
            if (string.compareTo(attribute) == 0) {
                return object;
            }
        }
        return null;
    }

    public String getTablename() {
        return this.tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public int getFieldSize() {
        return this.attributes.size();
    }

}
