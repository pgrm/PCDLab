package session1.during.two;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 04.10.12
 * Time: 18:17
 * To change this template use File | Settings | File Templates.
 */
public interface ThreadSynchronizer<T> {
    boolean isItMyTurn(T thread);
}
