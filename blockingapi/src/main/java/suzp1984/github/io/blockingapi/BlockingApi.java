package suzp1984.github.io.blockingapi;

import java.util.Random;

/**
 * Created by moses on 10/28/15.
 */
public class BlockingApi {

    private Random mRandom;

    public BlockingApi() {
        mRandom = new Random();
    }

    public String run() {

        try {
            Thread.sleep(mRandom.nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Hello World!";
    }
}
