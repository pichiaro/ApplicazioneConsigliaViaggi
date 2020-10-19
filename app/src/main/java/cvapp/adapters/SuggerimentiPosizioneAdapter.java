package cvapp.adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.AbstractList;

import adapters.AbstractAutoSuggestArrayAdapter;
import cvapp.R;

public class SuggerimentiPosizioneAdapter extends AbstractAutoSuggestArrayAdapter {

    private Activity activity;

    public SuggerimentiPosizioneAdapter(Activity activity, int resource, AbstractList<String> items, int numberOfSuggestions, String limitString) {
        super(activity, resource, items, numberOfSuggestions, limitString);
        this.activity = activity;
        AbstractAutoSuggestArrayAdapter.ContainsFilter containsFilter = this.new ContainsFilter() {};
        this.setContainsFilter(containsFilter);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AbstractList<String> items = this.getItems();
        Resources resources = this.activity.getResources();
        String string = items.get(position);
        String[] split = string.split(", ");
        Drawable drawable = resources.getDrawable(R.drawable.city, null);
        drawable.setBounds(0, -5, 80, 80);

        TextView textView = new TextView(this.activity);
        textView.setText("   " + split[0]);
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setCompoundDrawables(drawable, null, null, null);
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(textView);
        textView = new TextView(this.activity);
        textView.setText("   " + split[1] + ", " + split[2]);
        textView.setTextSize(16);
        textView.setTextSize(Color.DKGRAY);
        drawable = resources.getDrawable(R.drawable.flag, null);
        drawable.setBounds(0, -5, 80, 80);
        textView.setCompoundDrawables(drawable, null, null, null);

        linearLayout.addView(textView);
        textView = new TextView(this.activity);
        linearLayout.addView(textView);
        return linearLayout;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return this.activity;
    }
}
