package session1.during.two.two;

import session1.during.two.CountSynchronizer;
import session1.during.two.IdentifiedRunnable;

/**
 * Created with IntelliJ IDEA. User: Peter Date: 04.10.12 Time: 18:22 To change
 * this template use File | Settings | File Templates.
 */
public class OrderedSynchronizer extends CountSynchronizer<IdentifiedRunnable> {
	private final char startingCharId = 'a';
	
	private String currentId;
	private int threadCount;

	public OrderedSynchronizer(int maxCount, int threadCount) {
		super(maxCount);
		this.threadCount = threadCount;
		moveToNextId();
	}

	@Override
	public synchronized boolean isItMyTurn(IdentifiedRunnable thread) {
		if (thread.getId().equals(currentId)) {
			synchronized (this) {
				this.notifyAll();
			}
			moveToNextId();
			return super.isItMyTurn(thread);
		}
		
		synchronized(this) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}

	private String moveToNextId() {
		if (currentId == null) {
			currentId = Character.toString(startingCharId);
		} else {
			char currentCharId = Character.toChars(getNextSequence() + startingCharId)[0];
			currentId = Character.toString(currentCharId);
		}

		return currentId;
	}
	
	private int getNextSequence() {
		int nextSequence = getCurrentSequence() + 1;
		
		if (nextSequence < threadCount)
			return nextSequence;
		else
			return 0;
	}
	
	private int getCurrentSequence() {
		return getSequenceOf(currentId);
	}
	
	private int getSequenceOf(String threadId) {
		char currentCharId = threadId.charAt(0);
		return currentCharId - startingCharId;
	}
}
