package suzp1984.github.io.rxjavademo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import suzp1984.github.io.blockingapi.BlockingApi;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    private Executor mExecutor = Executors.newFixedThreadPool(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);

        super.onDestroy();
    }

    @OnClick(R.id.blocking_api)
    public void onClickedBlockingApi() {
        // do not running in main threader
        new Thread(new Runnable() {
            @Override
            public void run() {
                BlockingApi blockingApi = new BlockingApi();
                Log.e(TAG, blockingApi.run());
            }
        }).start();
    }

    @OnClick(R.id.future_api)
    public void onClickedFutureApi() {
        FutureApi futureApi = FutureApi.getInstance();

        Future<String> future = futureApi.run();

        // blocking the main thread;
        /*try {
            Log.e(TAG, future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/

        // un-blocking main thread version
        new FutureAsyncTask().execute(future);
    }

    @OnClick(R.id.rxjava_api)
    public void onClickedRxjavaApi() {
        RxApi rxApi = new RxApi();
        Observable<String> observable = rxApi.run();

        observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, Thread.currentThread().getName() + ": onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, Thread.currentThread().getName() + ": " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "----------");
                Log.e(TAG, Thread.currentThread().getName() + ": " + s);
            }
        });
    }

    @OnClick(R.id.rxandroid)
    public void onClickedRxAndroid() {
        Observable.just("a", "b").subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, Thread.currentThread().getName() + ": onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, Thread.currentThread().getName() + ": " + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "----------");
                        Log.e(TAG, Thread.currentThread().getName() + ": " + s);
                    }
                });
    }
}
