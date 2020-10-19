package onclicklisteners;

import android.app.Dialog;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener ;
import android.view.Window;

public class DialogShowExecuter implements  OnClickListener {

    private Dialog dialog;
    private boolean isDialogShowedFullscreen;

    public DialogShowExecuter(Dialog dialog, boolean isDialogShowedFullscreen) {
        this.dialog = dialog;
        this.isDialogShowedFullscreen = isDialogShowedFullscreen;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Dialog getDialog() {
        return this.dialog;
    }

    public void setDialogShowedFullscreen(boolean dialogShowedFullscreen) {
        isDialogShowedFullscreen = dialogShowedFullscreen;
    }

    public boolean isDialogShowedFullscreen() {
        return this.isDialogShowedFullscreen;
    }

    @Override
    public void onClick(View view) {
        if (this.dialog != null) {
            this.dialog.show();
            if (this.isDialogShowedFullscreen) {
                Window window = this.dialog.getWindow();
                Resources system = Resources.getSystem();
                DisplayMetrics displayMetrics = system.getDisplayMetrics();
                int screenWidthInPixel = displayMetrics.widthPixels;
                int screenHeightInPixel = displayMetrics.heightPixels;
                window.setLayout(screenWidthInPixel, screenHeightInPixel);
            }
        }
    }
}
