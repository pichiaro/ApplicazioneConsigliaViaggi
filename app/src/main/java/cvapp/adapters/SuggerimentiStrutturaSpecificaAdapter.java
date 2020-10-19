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
import cvapp.models.AbstractAlbergoModel;
import cvapp.models.AbstractAttrazioneModel;
import cvapp.models.AbstractFNDModel;
import cvapp.models.AbstractRistoranteModel;

public class SuggerimentiStrutturaSpecificaAdapter extends AbstractAutoSuggestArrayAdapter {

    private Activity activity;

    public SuggerimentiStrutturaSpecificaAdapter(Activity activity, int resource, AbstractList<String> items, int numberOfSuggestions, String limitString) {
        super(activity, resource, items, numberOfSuggestions, limitString);
        this.activity = activity;
        ContainsFilter containsFilter = this.new ContainsFilter() {
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                String string = (String) super.convertResultToString(resultValue);
                String[] splitted = string.split("\n");
                String nome = splitted[1];
                String address = splitted[2];
                return nome + " " + address;
            }
        };
        this.setContainsFilter(containsFilter);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        AbstractList<String> items = this.getItems();
        Resources resources = this.activity.getResources();
        String string = items.get(position);
        String[] splitted = string.split("\n");
        String className = splitted[0];
        String nome = splitted[1];
        String address = splitted[2];


        Drawable drawable = null;
        if (className.compareTo(AbstractAlbergoModel.class.getName()) == 0) {
            drawable = resources.getDrawable(R.drawable.hotel, null);
        } else {
            if (className.compareTo(AbstractRistoranteModel.class.getName()) == 0) {
                drawable = resources.getDrawable(R.drawable.restaurant, null);
            } else {
                if (className.compareTo(AbstractAttrazioneModel.class.getName()) == 0) {
                    drawable = resources.getDrawable(R.drawable.museum, null);
                } else {
                    if (className.compareTo(AbstractFNDModel.class.getName()) == 0) {
                        drawable = resources.getDrawable(R.drawable.fnd, null);
                    }
                }
            }
        }
        drawable.setBounds(0, -5, 80, 80);

        TextView textView = new TextView(this.activity);
        textView.setCompoundDrawables(drawable, null, null, null);

        textView.setText("   " + nome);
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(null, Typeface.BOLD);
        linearLayout.addView(textView);

        textView = new TextView(this.activity);
        textView.setText("   " + address);
        textView.setTextSize(16);
        textView.setTextSize(Color.DKGRAY);
        drawable = resources.getDrawable(R.drawable.location, null);
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
