package graphiccomponents;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

public abstract class InputReadableDialog extends Dialog implements Resettable {

    public final static int FULLSCREEN_THEME = android.R.style.Theme_DeviceDefault_Light_NoActionBar;
    private TextView readInputTextView;
    private String readInputDefaultText;

    public InputReadableDialog(Context context, String readInputDefaultText) {
        super(context);
        this.readInputTextView = new TextView(context);
        this.setReadInputDefaultText(readInputDefaultText);
    }

    public InputReadableDialog(Context context, String readInputDefaultText, int theme) {
        super(context, theme);
        this.readInputTextView = new TextView(context);
        this.setReadInputDefaultText(readInputDefaultText);
    }

    public TextView getReadInputTextView() {
        return this.readInputTextView;
    }

    public String getReadInputDefaultText() {
        return this.readInputDefaultText;
    }

    public void setReadInputDefaultText(String readInputDefaultText) {
        this.readInputDefaultText = readInputDefaultText;
        this.readInputTextView.setText(this.readInputDefaultText);
    }

    @Override
    public void reset() {
        if (this.readInputDefaultText != null) {
            this.readInputTextView.setText(this.readInputDefaultText);
        }
        else {
            this.readInputTextView.setText("");
        }
    }

}
