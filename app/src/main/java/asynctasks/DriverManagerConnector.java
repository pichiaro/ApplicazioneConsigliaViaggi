package asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DriverManagerConnector extends AsyncTask<Object, Object, Void> {

    private Connection connection;

    public Connection getConnection() {
        return this.connection;
    }

    @Override
    protected Void doInBackground(Object...params) {
        try {
            if (params.length == 2) {
                if (params[0] instanceof String) {
                    if (params[1] instanceof Properties) {
                        String url = (String) params[0];
                        Properties properties = (Properties) params[1];
                        this.connection = DriverManager.getConnection(url, properties);
                    }
                }
            } else {
                if (params.length == 1) {
                    if (params[0] instanceof String) {
                        String url = (String) params[0];
                        this.connection = DriverManager.getConnection(url);
                    }
                } else {
                    if (params.length == 3) {
                        if (params[0] instanceof String) {
                            if (params[1] instanceof String) {
                                if (params[2] instanceof String) {
                                    String url = (String) params[0];
                                    String user = (String) params[1];
                                    String password = (String) params[2];
                                    this.connection = DriverManager.getConnection(url, user, password);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception exception) {
            Log.d("Message: ", exception.getClass().getName() + "\n" + exception.getMessage());
        }
        return null;
    }

}
