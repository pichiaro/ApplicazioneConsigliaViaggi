package graphiccomponents;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class LinearLayouts {

    public final static double BUTTON_WIDTH = 0.335;
    public final static double BUTTON_HEIGHT = 0.125;
    public final static double LAYOUT_HEIGHT = 0.875;

    public static LinearLayout buildLayout(Context context, Button leftButton, Button centerButton, Button rightButton, int minus) {

        Resources system = Resources.getSystem();
        DisplayMetrics displayMetrics = system.getDisplayMetrics();
        int widthInPixel = displayMetrics.widthPixels;
        int heightInPixel = displayMetrics.heightPixels;

        heightInPixel = heightInPixel - minus;

        LinearLayout firstChild = new LinearLayout(context);
        firstChild.setOrientation(LinearLayout.VERTICAL);

        LayoutParams layoutParams = new LayoutParams(widthInPixel, (int)(heightInPixel * LinearLayouts.LAYOUT_HEIGHT));
        firstChild.setLayoutParams(layoutParams);

        layoutParams = new LayoutParams((int) (widthInPixel * LinearLayouts.BUTTON_WIDTH), (int) (heightInPixel * LinearLayouts.BUTTON_HEIGHT));
        centerButton.setLayoutParams(layoutParams);
        leftButton.setLayoutParams(layoutParams);
        rightButton.setLayoutParams(layoutParams);

        LinearLayout secondChild = new LinearLayout(context);
        secondChild.addView(leftButton);
        secondChild.addView(centerButton);
        secondChild.addView(rightButton);

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);

        container.addView(firstChild);
        container.addView(secondChild);

        return container;
    }

    public static LinearLayout buildScrollableLayout(Context context, Button leftButton, Button centerButton, Button rightButton, int minus) {

        Resources system = Resources.getSystem();
        DisplayMetrics displayMetrics = system.getDisplayMetrics();
        int widthInPixel = displayMetrics.widthPixels;
        int heightInPixel = displayMetrics.heightPixels;

        heightInPixel = heightInPixel - minus;

        ScrollView scrollView = new ScrollView(context);

        LayoutParams layoutParams = new LayoutParams(widthInPixel, (int) (heightInPixel * LinearLayouts.LAYOUT_HEIGHT));
        scrollView.setLayoutParams(layoutParams);

        layoutParams = new LayoutParams((int) (widthInPixel * LinearLayouts.BUTTON_WIDTH), (int) (heightInPixel * LinearLayouts.BUTTON_HEIGHT));
        centerButton.setLayoutParams(layoutParams);
        leftButton.setLayoutParams(layoutParams);
        rightButton.setLayoutParams(layoutParams);

        LinearLayout secondChild = new LinearLayout(context);
        secondChild.addView(leftButton);
        secondChild.addView(centerButton);
        secondChild.addView(rightButton);

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);

        container.addView(scrollView);
        container.addView(secondChild);

        return container;
    }

}
