package graphiccomponents;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

import androidx.appcompat.widget.Toolbar;

import onclicklisteners.TextViewTextSetter;

public class FullscreenInsertDialogBuilder {

    private Context context;

    private int toolbarBackgroundColor;
    private int editTextBackgroundColor;
    private int resetButtonBackgroundColor;
    private int rightButtonBackgroundColor;
    private String toolbarTitle;
    private int toolbarHeight;
    private int toolbarTitleTextColor;

    private String editTextText;
    private float editTextTextSize;
    private int editTextTextColor;
    private int editTextTextAlignment;
    private float resetButtonTextSize;
    private int resetButtonTextColor;
    private int resetButtonTextAlignment;
    private boolean resetButtonIsAllCaps;
    private String rightButtonText;
    private float rightButtonTextSize;
    private int rightButtonTextColor;
    private int rightButtonTextAlignment;
    private boolean rightButtonAllCaps;
    private ArrayAdapter arrayAdapter;
    private OnClickListener rightButtonOnClickListener;
    private int editTextBackgroundResources;
    private Drawable editTextLeftDrawable;
    private String editTextHint;
    private int toolBarNavigationIcon;

    public FullscreenInsertDialogBuilder(Context context) {
        this.context = context;
    }

    public void setToolbarBackgroundColor(int toolbarBackgroundColor) {
        this.toolbarBackgroundColor = toolbarBackgroundColor;
    }

    public void setEditTextBackgroundColor(int EditTextBackgroundColor) {
        this.editTextBackgroundColor = EditTextBackgroundColor;
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

    public void setEditTextText(String editTextText) {
        this.editTextText = editTextText;
    }

    public void setEditTextTextSize(float editTextTextSize) {
        this.editTextTextSize = editTextTextSize;
    }

    public void setEditTextTextColor(int editTextTextColor) {
        this.editTextTextColor = editTextTextColor;
    }

    public void setEditTextTextAlignment(int editTextTextAlignment) {
        this.editTextTextAlignment = editTextTextAlignment;
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

    public void setRightButtonOnClickListener(OnClickListener rightButtonOnClickListener) {
        this.rightButtonOnClickListener = rightButtonOnClickListener;
    }


    public void setEditTextBackgroundResources(int editTextBacgroundResources) {
        this.editTextBackgroundResources = editTextBacgroundResources;
    }

    public void setEditTextLeftDrawable(Drawable editTextLeftDrawable) {
        this.editTextLeftDrawable = editTextLeftDrawable;
    }

    public void setEditTextHint(String editTextHint) {
        this.editTextHint = editTextHint;
    }

    public void setArrayAdapter(ArrayAdapter arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
    }

    public void setToolBarNavigationIcon(int toolBarNavigationIcon) {
        this.toolBarNavigationIcon = toolBarNavigationIcon;
    }

    public FullscreenInsertDialog create() {
        FullscreenInsertDialog fullscreenInsertDialog = null;
        if (this.context != null) {

            fullscreenInsertDialog = new FullscreenInsertDialog(this.context);

            Toolbar toolbar = fullscreenInsertDialog.getToolbar();
            Resources system = Resources.getSystem();
            DisplayMetrics displayMetrics = system.getDisplayMetrics();
            int widthInPixel = displayMetrics.widthPixels;
            LayoutParams toolbarLayoutParams = new LayoutParams(widthInPixel, this.toolbarHeight);
            toolbar.setLayoutParams(toolbarLayoutParams);
            toolbar.setBackgroundColor(this.toolbarBackgroundColor);
            toolbar.setTitleTextColor(this.toolbarTitleTextColor);
            toolbar.setTitle(this.toolbarTitle);
            toolbar.setNavigationIcon(this.toolBarNavigationIcon);

            AutoCompleteTextView autoCompleteTextView = fullscreenInsertDialog.getAutoCompleteTextView();
            autoCompleteTextView.setBackgroundColor(this.editTextBackgroundColor);
            autoCompleteTextView.setText(this.editTextText);
            autoCompleteTextView.setTextSize(this.editTextTextSize);
            autoCompleteTextView.setTextColor(this.editTextTextColor);
            autoCompleteTextView.setTextAlignment(this.editTextTextAlignment);
            autoCompleteTextView.setHint(this.editTextHint);
            autoCompleteTextView.setBackgroundResource(this.editTextBackgroundResources);
            if (this.editTextLeftDrawable != null) {
                autoCompleteTextView.setCompoundDrawables(this.editTextLeftDrawable, null, null, null);
            }
            if (this.arrayAdapter != null) {
                autoCompleteTextView.setAdapter(this.arrayAdapter);
            }

            Button button = fullscreenInsertDialog.getResetButton();
            button.setBackgroundColor(this.resetButtonBackgroundColor);
            button.setText("Reset");
            button.setTextSize(this.resetButtonTextSize);
            button.setTextColor(this.resetButtonTextColor);
            button.setTextAlignment(this.resetButtonTextAlignment);
            button.setAllCaps(this.resetButtonIsAllCaps);
            TextViewTextSetter textViewTextSetter = new TextViewTextSetter(autoCompleteTextView, "");
            button.setOnClickListener(textViewTextSetter);

            button = fullscreenInsertDialog.getRightButton();
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
        return fullscreenInsertDialog;
    }

}
