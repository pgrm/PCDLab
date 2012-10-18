package session1.during.two.three;

import session1.during.two.GlobalMain;
import session1.during.two.IdentifiedRunnable;
import session1.during.two.ThreadSynchronizer;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA. User: Peter Date: 04.10.12 Time: 17:17 To change
 * this template use File | Settings | File Templates.
 */
public class Main implements GlobalMain.RunnableProvider<IdentifiedRunnable> {
	private ThreadSynchronizer<IdentifiedRunnable> synchronizer = new OrderedSynchronizer(
			100, new String[][] { { "a", "d" }, { "b", "c" } });

	public static void main(String[] args) throws IOException {
		Main configuration = new Main();
		GlobalMain<IdentifiedRunnable> mainProgram = new GlobalMain<IdentifiedRunnable>(
				configuration, 4);

		mainProgram.run();
	}

	@Override
	public IdentifiedRunnable generateThreadInstance(int sequence) {
		return new IdentifiedRunnable('a', sequence, synchronizer);
	}
}
