package onclicklisteners;

import android.text.method.TransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TextViewTrMetSetter implements OnClickListener {

    private TextView textView;
    private TransformationMethod transformationMethod;

    public TextViewTrMetSetter(TextView textView, TransformationMethod transformationMethod) {
        this.textView = textView;
        this.transformationMethod = transformationMethod;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public TextView getTextView() {
        return this.textView;
    }

    public void setTransformationMethod(TransformationMethod transformationMethod) {
        this.transformationMethod = transformationMethod;
    }

    public TransformationMethod getTransformationMethod() {
        return this.transformationMethod;
    }

    @Override
    public void onClick(View view) {
        if (this.textView != null) {
            this.textView.setTransformationMethod(this.transformationMethod);
        }
    }

}
