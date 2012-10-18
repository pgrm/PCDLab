package session1.during.two;


/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 04.10.12
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public class CountSynchronizer<T extends Stopable> implements ThreadSynchronizer<T> {
    private int count = 1;
    private int maxCount;

    public CountSynchronizer(int maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    public synchronized boolean isItMyTurn(T thread) {
        if (count > maxCount) {
        	thread.stop();
        	return  false;
        }
        // else
        count++;
        return true;
    }
}
