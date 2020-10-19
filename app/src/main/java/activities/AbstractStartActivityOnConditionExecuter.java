package activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public abstract class AbstractStartActivityOnConditionExecuter implements OnClickListener {

    public final static String BUNDLE_NAME = "BUNDLE";
    private Activity fromActivity;
    private Class<? extends Activity> toActivityClass;
    private Dialog dialog;
    private Bundle bundle;

    public AbstractStartActivityOnConditionExecuter(Dialog dialog, Activity fromActivity, Class<? extends Activity> toActivityClass, Bundle bundle) {
        this.dialog = dialog;
        this.fromActivity = fromActivity;
        this.toActivityClass = toActivityClass;
        this.bundle = bundle;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Dialog getDialog() {
        return this.dialog;
    }

    public void setFromActivity(Activity fromActivity) {
        this.fromActivity = fromActivity;
    }

    public Activity getFromActivity() {
        return this.fromActivity;
    }

    public void setToActivityClass(Class<? extends Activity> toActivityClass) {
        this.toActivityClass = toActivityClass;
    }

    public Class<? extends Activity> getToActivityClass() {
        return this.toActivityClass;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    protected abstract boolean checkCondition();

    @Override
    public void onClick(View view) {
        if (this.dialog != null) {
            this.dialog.show();
        }
        if (this.fromActivity != null && this.toActivityClass != null && this.checkCondition()) {
            Intent intent = new Intent(this.fromActivity, this.toActivityClass);
            intent.putExtra(AbstractStartActivityOnConditionExecuter.BUNDLE_NAME, this.bundle);
            this.fromActivity.startActivity(intent);
        }
        else {
            if (this.dialog != null) {
                this.dialog.dismiss();
            }
        }
    }

}
