package session1.after.one;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 04.10.12
 * Time: 17:30
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleVisualizedStack<E> extends VisualizedStack<E> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7510270602620906062L;

	@Override
    protected void updateVisualization() {
        System.out.print("[");

        for (int i = 0; i < size(); i++) {
            System.out.print("X");
        }

        System.out.println("]");
    }
}
