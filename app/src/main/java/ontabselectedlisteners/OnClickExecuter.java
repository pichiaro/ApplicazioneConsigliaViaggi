package ontabselectedlisteners;

import android.view.View.OnClickListener;

import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;

public class OnClickExecuter implements OnTabSelectedListener {

    private OnClickListener onClickListener;
    private String text;

    public OnClickExecuter(OnClickListener onClickListener, String text) {
        this.onClickListener = onClickListener;
        this.text = text;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OnClickListener getOnClickListener() {
        return this.onClickListener;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public void onTabSelected(Tab tab) {
        if (this.onClickListener != null) {
            CharSequence charSequence = tab.getText();
            if (charSequence != null) {
                String text = charSequence.toString();
                if (text.compareTo(this.text) == 0) {
                    this.onClickListener.onClick(null);
                }
            }
        }
    }

    @Override
    public void onTabUnselected(Tab tab) {

    }

    @Override
    public void onTabReselected(Tab tab) {
        if (this.onClickListener != null) {
            CharSequence charSequence = tab.getText();
            if (charSequence != null) {
                String text = charSequence.toString();
                if (text.compareTo(this.text) == 0) {
                    this.onClickListener.onClick(null);
                }
            }
        }
    }

}
