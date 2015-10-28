package suzp1984.github.io.rxjavademo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import suzp1984.github.io.blockingapi.BlockingApi;

/**
 * Created by moses on 10/28/15.
 */
public class FutureApi {

    private ExecutorService mExecutorService;
    private static FutureApi sFutureApi;

    public static FutureApi getInstance() {
        if (sFutureApi == null) {
            sFutureApi = new FutureApi();
        }

        return sFutureApi;
    }

    private FutureApi() {
        mExecutorService = Executors.newFixedThreadPool(10);
    }

    public Future<String> run() {
        return mExecutorService.submit(new BlockingCallable());
    }

    // Bocking Callable class
    public class BlockingCallable implements Callable<String> {

        @Override
        public String call() throws Exception {

            return new BlockingApi().run();
        }
    }
}
