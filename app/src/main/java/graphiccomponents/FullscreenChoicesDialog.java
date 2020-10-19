package graphiccomponents;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.Vector;

public class FullscreenChoicesDialog extends InputReadableDialog {

    private LinearLayout contentView;

    public FullscreenChoicesDialog(Context context) {
        super(context, "", InputReadableDialog.FULLSCREEN_THEME);

        Resources system = Resources.getSystem();
        DisplayMetrics displayMetrics = system.getDisplayMetrics();
        int widthInPixel = displayMetrics.widthPixels;
        int heightInPixel = displayMetrics.heightPixels;

        this.contentView = new LinearLayout(context);
        this.contentView.setOrientation(LinearLayout.VERTICAL);

        Toolbar toolbar = new Toolbar(context);
        this.contentView.addView(toolbar);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2), (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;
        TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        this.contentView.addView(textView);

        layoutParams = new LinearLayout.LayoutParams((int) (widthInPixel * LinearLayouts.BUTTON_WIDTH), (int) (heightInPixel * LinearLayouts.BUTTON_HEIGHT));

        LinearLayout linearLayout = new LinearLayout(context);
        Button button = new Button(context);
        button.setLayoutParams(layoutParams);
        button.setClickable(false);
        button.setBackgroundColor(Color.TRANSPARENT);
        linearLayout.addView(button);

        button = new Button(context);
        button.setLayoutParams(layoutParams);
        linearLayout.addView(button);

        button = new Button(context);
        button.setLayoutParams(layoutParams);
        linearLayout.addView(button);

        this.contentView.addView(linearLayout);

        this.setContentView(this.contentView);
    }

    @Override
    public void dismiss() {
        Vector<String> selecteds = this.getSelecteds();
        if (selecteds.size() == 0) {
            this.getReadInputTextView().setText(this.getReadInputDefaultText());
        } else {
            String text = "";
            Iterator<String> stringIterator = selecteds.iterator();
            while (stringIterator.hasNext()) {
                String selected = stringIterator.next();
                text = text + selected + "\n";
            }
            text = text.replaceAll("(\n)$", "");
            this.getReadInputTextView().setText(text);
        }
        super.dismiss();
    }



    public Toolbar getToolbar() {
        Toolbar toolbar = (Toolbar) this.contentView.getChildAt(0);
        return toolbar;
    }


    public Button getResetButton() {
        int childCount = this.contentView.getChildCount();
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(childCount - 1);
        Button button = (Button) linearLayout.getChildAt(1);
        return button;
    }

    public Button getRightButton() {
        int childCount = this.contentView.getChildCount();
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(childCount - 1);
        Button button = (Button) linearLayout.getChildAt(2);
        return button;
    }

    public LinearLayout getContentView() {
        return this.contentView;
    }

    public void setCheckBoxes(AbstractList<CheckBox> checkBoxes) {
        if (checkBoxes != null) {
            Iterator<CheckBox> iterator = checkBoxes.iterator();
            int index = 2;
            while (iterator.hasNext()) {
                CheckBox next = iterator.next();
                if (next != null) {
                    this.contentView.addView(next, index);
                    index ++;
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        int childCount = this.contentView.getChildCount();
        if (childCount > 3) {
            for (int index = 2; index < childCount - 1; index++) {
                CheckBox checkBox = (CheckBox) this.contentView.getChildAt(index);
                checkBox.setChecked(false);
            }
        }
    }

    public Vector<String> getSelecteds() {
        Vector<String> selecteds = new Vector<>();
        int childCount = this.contentView.getChildCount();
        if (childCount > 3) {
            for (int index = 2; index < childCount - 1; index++) {
                CheckBox checkBox = (CheckBox) this.contentView.getChildAt(index);
                if (checkBox.isChecked()) {
                    CharSequence charSequence = checkBox.getText();
                    String text = charSequence.toString();
                    selecteds.add(text);
                }
            }
        }
        return selecteds;
    }

    public String[] getSelectedsAsArray() {
        Vector<String> selecteds = this.getSelecteds();
        String[] array = new String[selecteds.size()];
        array = selecteds.toArray(array);
        return array;
    }

}
