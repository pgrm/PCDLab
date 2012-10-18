package session1.during.two;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 04.10.12
 * Time: 18:26
 * To change this template use File | Settings | File Templates.
 */
public class GlobalMain<T extends Runnable> {
    private RunnableProvider<T> runnableProvider;
    private Thread[] threads;
    private List<T> runnables;

    public GlobalMain(RunnableProvider<T> runnableProvider, int maxThreadsCount) {
        this.runnableProvider = runnableProvider;
        this.threads = new Thread[maxThreadsCount];
        this.runnables = new ArrayList<T>();
    }

    public void run() {
        initializeThreads();
        startThreads();
    }

    /**
     * 
     */
    private void initializeThreads() {
        for (int i = 0; i < threads.length; i++) {
            T implementation = runnableProvider.generateThreadInstance(i);
            runnables.add(implementation);
            threads[i] = new Thread(implementation);
        }
    }

    private void startThreads() {
        for (Thread t : threads) {
            t.start();
        }
    }

    public interface RunnableProvider<T> {
        T generateThreadInstance(int sequence);
    }
}
