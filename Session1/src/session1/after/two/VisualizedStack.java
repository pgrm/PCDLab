package session1.after.two;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 04.10.12
 * Time: 17:22
 * To change this template use File | Settings | File Templates.
 */
public abstract class VisualizedStack<E> extends Stack<E> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6453428958929254564L;

	protected abstract void updateVisualization();

    @Override
    public E push(E element) {
        E ret = super.push(element);
        updateVisualization();
        return  ret;
    }

    @Override
    public E pop() {
        E ret = super.pop();
        updateVisualization();
        return ret;
    }
}
