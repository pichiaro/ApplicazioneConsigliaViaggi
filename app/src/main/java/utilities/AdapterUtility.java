package utilities;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsSpinner;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AdapterUtility {

    public static void adapt(AbsSpinner spinner, Context context, String...strings) {
        if (strings != null) {
            if (context != null) {
                if (spinner != null) {
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, strings);
                    spinner.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public static void adapt(ListView listView, Context context, View...views) {
        if (views != null) {
            if (context != null) {
                if (listView != null) {
                    for (int index = 0; index < views.length; index++) {
                        if (views[index] != null) {
                            listView.addHeaderView(views[index]);
                        }
                    }
                    View[] adapterViews = {};
                    ArrayAdapter<View> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, adapterViews);
                    listView.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public static void adapt(AbsListView listView, Context context) {
        if (context != null) {
            if (listView != null) {
                View[] views = {};
                ArrayAdapter<View> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, views);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

}
