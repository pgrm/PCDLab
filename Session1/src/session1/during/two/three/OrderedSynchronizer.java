package session1.during.two.three;

import java.util.HashMap;
import java.util.Map;

import session1.during.two.CountSynchronizer;
import session1.during.two.IdentifiedRunnable;

/**
 * Created with IntelliJ IDEA. User: Peter Date: 04.10.12 Time: 18:22 To change
 * this template use File | Settings | File Templates.
 */
public class OrderedSynchronizer extends CountSynchronizer<IdentifiedRunnable> {
	private Map<String, Integer> pairs = new HashMap<String, Integer>();
	private String[] lastThreads;

	public OrderedSynchronizer(int maxCount, String[][] pairs) {
		super(maxCount);

		lastThreads = new String[pairs.length];
		for (int i = 0; i < pairs.length; i++) {
			for (String str : pairs[i]) {
				this.pairs.put(str, i);
			}
		}
	}

	@Override
	public synchronized boolean isItMyTurn(IdentifiedRunnable thread) {
		if (isThreadAllowed(thread.getId())) {
			synchronized (this) {
				this.notifyAll();
			}
			lastThreads[getPairSeries(thread.getId())] = thread.getId();
			return super.isItMyTurn(thread);
		}

		synchronized (this) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	private boolean isThreadAllowed(String threadId) {
		int pairIndex = getPairSeries(threadId);

		if (lastThreads[pairIndex] == null)
			return true;
		else
			return !lastThreads[pairIndex].equals(threadId);
	}

	private int getPairSeries(String threadId) {
		return pairs.get(threadId).intValue();
	}
}
