package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TextViewTextSetter implements OnClickListener {

    private TextView textView;
    private String text;

    public TextViewTextSetter(TextView textView, String text) {
        this.textView = textView;
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public TextView getTextView() {
        return this.textView;
    }

    @Override
    public void onClick(View view) {
        if (this.textView != null) {
            this.textView.setText(this.text);
        }
    }

}