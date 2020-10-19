package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.AbstractList;
import java.util.Iterator;

public class TextViewsTextSetter implements OnClickListener {

    private AbstractList<TextView> textViews;
    private String text;

    public TextViewsTextSetter(AbstractList<TextView> textViews, String text) {
        this.textViews = textViews;
        this.text = text;
    }

    public void setTextViews(AbstractList<TextView> textViews) {
        this.textViews = textViews;
    }

    public AbstractList<TextView> getTextViews() {
        return this.textViews;
    }

    public void addTextViews(TextView...textViews) {
        if (this.textViews != null) {
            if (textViews != null) {
                for (int index = 0; index < textViews.length; index++) {
                    if (textViews[index] != null) {
                        this.textViews.add(textViews[index]);
                    }
                }
            }
        }
    }

    public TextView getTextView(int index) {
        TextView textView = null;
        if (this.textViews != null) {
            if (index >= 0) {
                if (index < this.textViews.size()) {
                    textView = this.textViews.get(index);
                }
            }
        }
        return textView;
    }

    public TextView removeTextView(int index) {
        TextView textView = null;
        if (this.textViews != null) {
            if (index >= 0) {
                if (index < this.textViews.size()) {
                    textView = this.textViews.remove(index);
                }
            }
        }
        return textView;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public void onClick(View view) {
        if (this.textViews != null) {
            Iterator<TextView> iterator = this.textViews.iterator();
            while (iterator.hasNext()) {
                TextView next = iterator.next();
                if (next != null) {
                    next.setText(this.text);
                }
            }
        }
    }

}
