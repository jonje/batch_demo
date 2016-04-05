package gov.utah.dts;

/**
 * Created by jjensen on 3/30/16.
 */
public interface InteruptableRunnable extends Runnable {
    void stopJob() throws InterruptedException;
}
