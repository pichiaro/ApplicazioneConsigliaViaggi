package onclicklisteners;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

public class DialogDismissExecuter implements OnClickListener {

    private Dialog dialog;

    public DialogDismissExecuter(Dialog dialog) {
        this.dialog = dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Dialog getDialog() {
        return this.dialog;
    }

    @Override
    public void onClick(View v) {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
    }

}
