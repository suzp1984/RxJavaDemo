package suzp1984.github.io.rxjavademo;

import rx.Observable;
import rx.functions.Func0;
import suzp1984.github.io.blockingapi.BlockingApi;

/**
 * Created by moses on 10/28/15.
 */
public class RxApi {

    Observable<String> run() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {

                return Observable.just(new BlockingApi().run());
            }
        });


    }

}
