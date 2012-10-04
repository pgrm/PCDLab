package session1.during.one;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 04.10.12
 * Time: 17:22
 * To change this template use File | Settings | File Templates.
 */
public abstract class VisualizedStack<E> extends Stack<E> {
    protected abstract void updateVisualization();

    @Override
    public synchronized E push(E element) {
        E ret = super.push(element);
        updateVisualization();
        return  ret;
    }

    @Override
    public synchronized E pop() {
        E ret = super.pop();
        updateVisualization();
        return ret;
    }
}
