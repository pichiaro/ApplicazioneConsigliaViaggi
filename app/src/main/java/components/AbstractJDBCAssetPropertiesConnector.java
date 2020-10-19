package components;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import asynctasks.DriverManagerConnector;
import models.ObjectsModel;

public abstract class AbstractJDBCAssetPropertiesConnector extends AbstractQueriesInterface {

    private DriverManagerConnector driverManagerConnector;
    private AlertDialog alertDialog;
    private String propertiesFilename;
    private String urlAttribute;
    private Activity activity;

    public AbstractJDBCAssetPropertiesConnector(Activity activity, String propertiesFilename) {
        this.activity = activity;
        this.propertiesFilename = propertiesFilename;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public AlertDialog getAlertDialog() {
        return this.alertDialog;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setPropertiesFilename(String propertiesFilename) {
        this.propertiesFilename = propertiesFilename;
    }

    public String getPropertiesFilename() {
        return this.propertiesFilename;
    }

    public void setUrlAttribute(String urlAttribute) {
        this.urlAttribute = urlAttribute;
    }

    public String getUrlAttribute() {
        return this.urlAttribute;
    }

    public void setDriverManagerConnector(DriverManagerConnector driverManagerConnector) {
        this.driverManagerConnector = driverManagerConnector;
    }

    public DriverManagerConnector getDriverManagerConnector() {
        return this.driverManagerConnector;
    }

    public void initialize() {
        if (this.activity != null) {
            Builder builder = new Builder(activity);
            builder.setTitle("Back-End Error");
            builder.setCancelable(true);
            builder.setPositiveButton("", null);
            this.alertDialog = builder.create();
            try {
                Resources resources = this.activity.getResources();
                AssetManager assetManager = resources.getAssets();
                InputStream inputStream = assetManager.open(this.propertiesFilename);
                Properties properties = new Properties();
                properties.load(inputStream);
                this.driverManagerConnector = new DriverManagerConnector();
                String url = (String) properties.get(this.urlAttribute);
                this.driverManagerConnector.execute(url, properties);
            } catch (Exception exception) {
                AlertDialog alertDialog = this.getAlertDialog();
                if (alertDialog != null) {
                    alertDialog.setMessage(exception.getClass().getName() + "\n" + exception.getMessage());
                    alertDialog.show();
                }
            }
        }
    }

    public boolean executeCommand(String update) {
        Connection connection = this.driverManagerConnector.getConnection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(update);
                preparedStatement.executeUpdate();
                connection.commit();
                preparedStatement.close();
                connection.close();
                return true;
            }
            catch(Exception exception) {
                if (this.alertDialog != null) {
                    this.alertDialog.setMessage(exception.getClass().getName() + "\n" + exception.getMessage());
                    this.alertDialog.show();
                }
            }
        }
        return false;
    }

    public boolean insert(String tableName, AbstractList<String> attributes, AbstractList<Object> values) {
        Connection connection = this.driverManagerConnector.getConnection();
        if (connection != null) {
            try {
                if (attributes.size() == values.size()) {
                    String attributeList = "(";
                    String valuesList = "(";
                    Iterator<String> stringIterator = attributes.iterator();
                    while (stringIterator.hasNext()) {
                        String attribute = stringIterator.next();
                        attributeList = attributeList + attribute + ",";
                        valuesList = valuesList + "?,";
                    }
                    attributeList = attributeList + ")";
                    attributeList = attributeList.replaceAll(",\\)$", ")");
                    valuesList = valuesList + ")";
                    valuesList = valuesList.replaceAll(",\\)$", ")");
                    PreparedStatement insertStatement = connection.prepareStatement("insert into " + tableName + " " + attributeList + " values " + valuesList + ";");
                    Iterator<Object> iterator = values.iterator();
                    int parameterIndex = 1;
                    while (iterator.hasNext()) {
                        Object object = iterator.next();
                        insertStatement.setObject(parameterIndex, object);
                    }
                    insertStatement.executeUpdate();
                    connection.commit();
                    insertStatement.close();
                    connection.close();
                    return true;
                }
            } catch (Exception exception) {
                if (this.alertDialog != null) {
                    this.alertDialog.setMessage(exception.getClass().getName() + "\n" + exception.getMessage());
                    this.alertDialog.show();
                }
            }
        }
        return false;
    }

    protected abstract ObjectsModel buildObjectsModel(String tableName);

    protected abstract String getTablename(String query);

    public AbstractList<ObjectsModel> select(String query) {
        Connection connection = this.driverManagerConnector.getConnection();
        LinkedList<ObjectsModel> objectsModels = new LinkedList<>();
        if (connection != null) {
            try {
                PreparedStatement queryStatement = connection.prepareStatement(query);
                ResultSet resultSet = queryStatement.executeQuery();
                String tableName = this.getTablename(query);
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();
                while (resultSet.next()) {
                    ObjectsModel objectsModel = this.buildObjectsModel(tableName);
                    for (int index = 1; index <= columnCount; index++) {
                        Object object = resultSet.getObject(index);
                        String attribute = resultSetMetaData.getColumnName(index);
                        objectsModel.addCouple(attribute, object);
                    }
                    objectsModels.addFirst(objectsModel);
                }
            } catch (Exception exception) {
                if (this.alertDialog != null) {
                    this.alertDialog.setMessage(exception.getClass().getName() + "\n" + exception.getMessage());
                    this.alertDialog.show();
                }
            }
        }
        return objectsModels;
    }

    public boolean isConnected() {
        if (this.driverManagerConnector != null) {
            Connection connection = this.driverManagerConnector.getConnection();
            if (connection != null) {
                return true;
            }
        }
        return false;
    }

}
