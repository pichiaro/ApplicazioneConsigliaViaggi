package activities;

import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public abstract class SlidingUpBaseActivity extends AppCompatActivity {

    private SlidingUpPanelLayout slidingUpPanelLayout;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.slidingUpPanelLayout = new SlidingUpPanelLayout(this);
        this.slidingUpPanelLayout.setGravity(Gravity.BOTTOM);
        this.setContentView(this.slidingUpPanelLayout);
    }

    public SlidingUpPanelLayout getSlidingUpPanelLayout() {
        return this.slidingUpPanelLayout;
    }

    public void setLayout(View background, View panel) {
        if (background != null && panel != null) {
            this.slidingUpPanelLayout.addView(background);
            this.slidingUpPanelLayout.addView(panel);
        }
    }

    public int getActionBarHeight() {
        int actionBarHeight = 0;
        Theme theme = this.getTheme();
        if (theme != null) {
            Resources resources = this.getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            TypedValue typedValue = new TypedValue();
            if (theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, displayMetrics);
            }
        }
        return actionBarHeight;
    }

    private boolean isOnBackPressedEnabled;

    public boolean isOnBackPressedEnabled() {
        return this.isOnBackPressedEnabled;
    }

    public void setOnBackPressedDisabled(boolean isOnBackPressedEnabled) {
        this.isOnBackPressedEnabled = isOnBackPressedEnabled;
    }

    @Override
    public void onBackPressed() {
        if (this.isOnBackPressedEnabled) {
            super.onBackPressed();
        }
    }

}


