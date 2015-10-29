package suzp1984.github.io.rxjavademo;

import android.util.Log;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import suzp1984.github.io.blockingapi.BlockingApi;

/**
 * Created by moses on 10/28/15.
 */
public class RxApi {

    private static final String TAG = "RxApi";

    Observable<String> run() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {

                Log.e(TAG, Thread.currentThread().getName() + ": ---" );
                return Observable.just(new BlockingApi().run()).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread());
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());

    }

}
