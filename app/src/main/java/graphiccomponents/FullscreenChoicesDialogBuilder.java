package graphiccomponents;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.Vector;

import onclicklisteners.CompoundButtonsCheckSetter;

public class FullscreenChoicesDialogBuilder {

    private Context context;
    private int toolbarBackgroundColor;
    private int resetButtonBackgroundColor;
    private int rightButtonBackgroundColor;
    private String toolbarTitle;
    private int toolbarHeight;
    private int toolbarTitleTextColor;
    private float resetButtonTextSize;
    private int resetButtonTextColor;
    private int resetButtonTextAlignment;
    private boolean resetButtonIsAllCaps;
    private String rightButtonText;
    private float rightButtonTextSize;
    private int rightButtonTextColor;
    private int rightButtonTextAlignment;
    private boolean rightButtonAllCaps;
    private Vector<String> choices;
    private int checkBoxesTextColor;
    private float checkBoxesTextSize;
    private int checkBoxesBackgroundColor;
    private int toolbarNavigationIcon;
    private OnClickListener rightButtonOnClickListener;

    public FullscreenChoicesDialogBuilder(Context context) {
        this.context = context;
    }

    public void setToolbarBackgroundColor(int toolbarBackgroundColor) {
        this.toolbarBackgroundColor = toolbarBackgroundColor;
    }

    public void setResetButtonBackgroundColor(int resetButtonBackgroundColor) {
        this.resetButtonBackgroundColor = resetButtonBackgroundColor;
    }

    public void setRightButtonBackgroundColor(int rightButtonBackgroundColor) {
        this.rightButtonBackgroundColor = rightButtonBackgroundColor;
    }

    public void setToolbarTitle(String toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
    }

    public void setToolbarHeight(int toolbarHeight) {
        this.toolbarHeight = toolbarHeight;
    }

    public void setToolbarTitleTextColor(int toolbarTitleTextColor) {
        this.toolbarTitleTextColor = toolbarTitleTextColor;
    }

    public void setResetButtonTextSize(float resetButtonTextSize) {
        this.resetButtonTextSize = resetButtonTextSize;
    }

    public void setResetButtonTextColor(int resetButtonTextColor) {
        this.resetButtonTextColor = resetButtonTextColor;
    }

    public void setResetButtonTextAlignment(int resetButtonTextAlignment) {
        this.resetButtonTextAlignment = resetButtonTextAlignment;
    }

    public void setResetButtonIsAllCaps(boolean resetButtonIsAllCaps) {
        this.resetButtonIsAllCaps = resetButtonIsAllCaps;
    }

    public void setRightButtonText(String rightButtonText) {
        this.rightButtonText = rightButtonText;
    }

    public void setRightButtonTextSize(float rightButtonTextSize) {
        this.rightButtonTextSize = rightButtonTextSize;
    }

    public void setRightButtonTextColor(int rightButtonTextColor) {
        this.rightButtonTextColor = rightButtonTextColor;
    }

    public void setRightButtonTextAlignment(int rightButtonTextAlignment) {
        this.rightButtonTextAlignment = rightButtonTextAlignment;
    }

    public void setRightButtonAllCaps(boolean rightButtonAllCaps) {
        this.rightButtonAllCaps = rightButtonAllCaps;
    }

    public void setChoices(String...choices) {
        if (choices != null) {
            this.choices = new Vector<>();
            for (int index = 0; index < choices.length; index++) {
                if (choices[index] != null) {
                    this.choices.add(choices[index]);
                }
            }
        }
    }

    public void setCheckBoxesTextColor(int checkBoxesTextColor) {
        this.checkBoxesTextColor = checkBoxesTextColor;
    }

    public void setCheckBoxesTextSize(float checkBoxesTextSize) {
        this.checkBoxesTextSize = checkBoxesTextSize;
    }

    public void setCheckBoxesBackgroundColor(int checkBoxesBackgroundColor) {
        this.checkBoxesBackgroundColor = checkBoxesBackgroundColor;
    }

    public void setToolbarNavigationIcon(int toolbarNavigationIcon) {
        this.toolbarNavigationIcon = toolbarNavigationIcon;
    }

    public void setRightButtonOnClickListener(OnClickListener rightButtonOnClickListener) {
        this.rightButtonOnClickListener = rightButtonOnClickListener;
    }

    public FullscreenChoicesDialog create() {
        FullscreenChoicesDialog fullscreenChoicesDialog = null;
        if (this.context != null) {

            fullscreenChoicesDialog = new FullscreenChoicesDialog(this.context);

            Vector<CheckBox> checkBoxes = null;
            if (this.choices != null) {
                checkBoxes = new Vector<>();
                Iterator<String> iterator = this.choices.iterator();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    if (next != null) {
                        CheckBox checkBox = new CheckBox(this.context);
                        checkBox.setText(next);
                        checkBox.setTextSize(this.checkBoxesTextSize);
                        checkBox.setBackgroundColor(this.checkBoxesBackgroundColor);
                        checkBox.setTextColor(this.checkBoxesTextColor);
                        checkBoxes.add(checkBox);
                    }
                }
                fullscreenChoicesDialog.setCheckBoxes(checkBoxes);
            }

            Toolbar toolbar = fullscreenChoicesDialog.getToolbar();
            Resources system = Resources.getSystem();
            DisplayMetrics displayMetrics = system.getDisplayMetrics();
            int widthInPixel = displayMetrics.widthPixels;
            LinearLayout.LayoutParams toolbarLayoutParams = new LinearLayout.LayoutParams(widthInPixel, this.toolbarHeight);
            toolbar.setLayoutParams(toolbarLayoutParams);
            toolbar.setBackgroundColor(this.toolbarBackgroundColor);
            toolbar.setTitleTextColor(this.toolbarTitleTextColor);
            toolbar.setTitle(this.toolbarTitle);
            toolbar.setNavigationIcon(this.toolbarNavigationIcon);

            Button button = fullscreenChoicesDialog.getResetButton();
            button.setBackgroundColor(this.resetButtonBackgroundColor);
            button.setText("Reset");
            button.setTextSize(this.resetButtonTextSize);
            button.setTextColor(this.resetButtonTextColor);
            button.setTextAlignment(this.resetButtonTextAlignment);
            button.setAllCaps(this.resetButtonIsAllCaps);
            CompoundButtonsCheckSetter compoundButtonsCheckSetter = new CompoundButtonsCheckSetter((AbstractList) checkBoxes, false);
            button.setOnClickListener(compoundButtonsCheckSetter);

            button = fullscreenChoicesDialog.getRightButton();
            button.setBackgroundColor(this.rightButtonBackgroundColor);
            button.setText(this.rightButtonText);
            button.setTextSize(this.rightButtonTextSize);
            button.setTextColor(this.rightButtonTextColor);
            button.setTextAlignment(this.rightButtonTextAlignment);
            button.setAllCaps(this.rightButtonAllCaps);
            if (this.rightButtonOnClickListener != null) {
                button.setOnClickListener(this.rightButtonOnClickListener);
            }
        }
        return fullscreenChoicesDialog;
    }

}