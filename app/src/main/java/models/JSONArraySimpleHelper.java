package models;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JSONArraySimpleHelper {

    private Context context;

    public JSONArraySimpleHelper(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    public String readJSONFileFromAssetsDir(String fileName) {
        String json = null;
        try {
            AssetManager assetManager = this.context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception exception) {

        }
        return json;
    }

    public LinkedList<ObjectsModel> toObjectsModels(String fileName, String...attribute) {
        LinkedList<ObjectsModel> objectsModelLinkedList = new LinkedList<>();
        try {
            String string = readJSONFileFromAssetsDir(fileName);
            JSONArray jsonArray = new JSONArray(string);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ObjectsModel objectsModel = new ObjectsModel();
                for (int j = 0; j < attribute.length; j++) {
                    String value = jsonObject.get(attribute[j]).toString();
                    objectsModel.addCouple(attribute[j], value);
                }
                objectsModelLinkedList.addFirst(objectsModel);
            }
        } catch (Exception exception) {

        }
        return objectsModelLinkedList;
    }

    public List<String> toStrings(String fileName, String separator, String...attributes) {
        LinkedList<ObjectsModel> objectsModelLinkedList = this.toObjectsModels(fileName, attributes);
        Iterator<ObjectsModel> iterator = objectsModelLinkedList.iterator();
        LinkedList<String> strings = new LinkedList<>();
        while (iterator.hasNext()) {
            ObjectsModel objectsModel = iterator.next();
            String concatenation = "";
            while (objectsModel.getFieldSize() > 0) {
                concatenation = concatenation + objectsModel.getValue(0) + separator;
                objectsModel.removeCouple(0);
            }
            concatenation = concatenation.replaceAll(separator + "$","");
            strings.addFirst(concatenation);
        }
        Stream<String> stream = strings.stream();
        stream = stream.distinct();
        List<String> list = stream.collect(Collectors.toList());
        return list;
    }

}
