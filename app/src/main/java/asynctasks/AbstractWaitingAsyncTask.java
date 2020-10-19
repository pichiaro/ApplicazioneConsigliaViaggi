package asynctasks;

import android.app.Dialog;
import android.os.AsyncTask;

public abstract class AbstractWaitingAsyncTask extends AsyncTask<Object, Object, Void> {

    private Dialog dialog;

    public AbstractWaitingAsyncTask(Dialog dialog) {
        this.dialog = dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Dialog getDialog() {
        return this.dialog;
    }

    @Override
    protected void onPreExecute() {
        if (this.dialog != null) {
            this.dialog.show();
        }
    }

    @Override
    protected void onPostExecute(Void nothing) {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
    }

}
