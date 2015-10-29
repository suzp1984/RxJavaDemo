package suzp1984.github.io.rxjavademo;

import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by moses on 10/29/15.
 */
public class FutureAsyncTask extends AsyncTask<Future, Integer, Long> {

    private static final String TAG = "FutureAsyncTask";

    @Override
    protected Long doInBackground(Future... params) {
        int count = params.length;

        for (int i = 0; i < count; i++) {
            try {
                String r = (String) params[i].get();
                Log.e(TAG, r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
