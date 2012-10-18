package session1.during.two;

/**
 * Created with IntelliJ IDEA. User: Peter Date: 04.10.12 Time: 18:46 To change
 * this template use File | Settings | File Templates.
 */
public class IdentifiedRunnable implements Runnable, Stopable {
	private ThreadSynchronizer<IdentifiedRunnable> synchronizer;
	private String id;
	private boolean keepRunning = true;

	public IdentifiedRunnable(char startingId, int sequence,
			ThreadSynchronizer<IdentifiedRunnable> synchronizer) {
		this(getInstanceId(startingId, sequence), synchronizer);
	}

	public IdentifiedRunnable(String id,
			ThreadSynchronizer<IdentifiedRunnable> synchronizer) {
		this.id = id;
		this.synchronizer = synchronizer;
	}

	@Override
	public void run() {
		while (keepRunning) {
			synchronized (synchronizer) {
				if (synchronizer.isItMyTurn(this))
					System.out.print(id);
			}
		}
	}

	@Override
	public void stop() {
		keepRunning = false;
	}

	public String getId() {
		return id;
	}

	private static String getInstanceId(char startingId, int sequence) {
		char currentId = Character.toChars(startingId + sequence)[0];

		return Character.toString(currentId);
	}
}
