package handlers;

import android.os.AsyncTask;

public abstract class AbstractAsyncHandler {

    protected abstract boolean checkFunctionsStatus();

    protected abstract boolean executeBefore();

    protected abstract boolean executeInBackground();

    protected abstract boolean executeAfter();

    public class InnerAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            executeBefore();
        }

        @Override
        protected Void doInBackground(Void...nothings) {
            executeInBackground();
            return null;
        }

        @Override
        protected void onPostExecute(Void nothing) {
            executeAfter();
        }

    }

}
