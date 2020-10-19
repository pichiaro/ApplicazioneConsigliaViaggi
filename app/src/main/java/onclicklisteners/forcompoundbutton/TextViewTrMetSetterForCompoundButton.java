package onclicklisteners.forcompoundbutton;

import android.text.method.TransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.TextView;

public class TextViewTrMetSetterForCompoundButton implements OnClickListener {

    private TextView textView;
    private TransformationMethod transformationMethodChecked;
    private TransformationMethod transformationMethodUnchecked;

    public TextViewTrMetSetterForCompoundButton(TextView textView, TransformationMethod transformationMethodChecked, TransformationMethod transformationMethodUnchecked) {
        this.textView = textView;
        this.transformationMethodChecked = transformationMethodChecked;
        this.transformationMethodUnchecked = transformationMethodUnchecked;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public TextView getTextView() {
        return this.textView;
    }

    public void setTransformationMethodChecked(TransformationMethod transformationMethodChecked) {
        this.transformationMethodChecked = transformationMethodChecked;
    }

    public TransformationMethod getTransformationMethodChecked() {
        return this.transformationMethodChecked;
    }

    public void setTransformationMethodUnchecked(TransformationMethod transformationMethodUnchecked) {
        this.transformationMethodUnchecked = transformationMethodUnchecked;
    }

    public TransformationMethod getTransformationMethodUnchecked() {
        return this.transformationMethodUnchecked;
    }

    @Override
    public void onClick(View view) {
        if (this.textView != null) {
            if (view instanceof CompoundButton) {
                CompoundButton cast = (CompoundButton) view;
                if (cast.isChecked()) {
                    this.textView.setTransformationMethod(this.transformationMethodChecked);
                }
                else {
                    this.textView.setTransformationMethod(this.transformationMethodUnchecked);
                }
            }
        }
    }

}
