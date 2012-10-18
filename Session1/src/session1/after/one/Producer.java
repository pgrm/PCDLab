package session1.after.one;

import java.util.Random;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA. User: Peter Date: 04.10.12 Time: 17:36 To change
 * this template use File | Settings | File Templates.
 */
public class Producer implements Runnable, Stopable {
	private Stack<String> stackToProduceTo;
	private Random randomTimer = new Random();
	private int maxMilliSecondsSleep;
	private int maxElementsToProduceAtOnce;
	private int maxValuesToProduce;
	private boolean notInterrupted = true;

	public Producer(Stack<String> stackToProduceTo, double maxSecondsSleep,
			int maxElementsToProduceAtOnce, int maxValuesToProduce) {
		this.stackToProduceTo = stackToProduceTo;
		this.maxMilliSecondsSleep = (int) (maxSecondsSleep * 1000);
		this.maxElementsToProduceAtOnce = maxElementsToProduceAtOnce;
		this.maxValuesToProduce = maxValuesToProduce;
	}

	@Override
	public void run() {
		System.out.println("Producer is starting...");

		while (notInterrupted) {
			produceAllElements();

			try {
				Thread.sleep(randomSleepTime());
			} catch (InterruptedException e) {
				notInterrupted = false;
			}
		}

		System.out.println("Producer is stopping ...");
	}

	public void stop() {
		notInterrupted = false;
	}

	private void produceAllElements() {
		synchronized (stackToProduceTo) {
			for (int i = 0; i < randomElementsToProduce(); i++) {
				try {
					produceElement(i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
			}
			stackToProduceTo.notifyAll();
		}
	}

	private void produceElement(int elementIndex) throws InterruptedException {
		// if more producers are running, a while is needed, 
		// since it can be that another producer has been woken up before this one, 
		// and the stack is full again.
		while (stackToProduceTo.size() >= maxValuesToProduce) {
			if (elementIndex > 0) {
				stackToProduceTo.notifyAll();
			}
			stackToProduceTo.wait();
		}
		stackToProduceTo.push("Item");
	}

	private long randomSleepTime() {
		return (long) (randomTimer.nextDouble() * maxMilliSecondsSleep);
	}

	private int randomElementsToProduce() {
		return (int) (randomTimer.nextDouble() * (double)maxElementsToProduceAtOnce);
	}
}
