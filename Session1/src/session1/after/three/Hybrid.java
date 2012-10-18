package session1.after.three;

import java.util.Random;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA. User: Peter Date: 04.10.12 Time: 17:36 To change
 * this template use File | Settings | File Templates.
 */
public class Hybrid implements Runnable, Stopable {
	private Stack<String> elementsStack;
	private Random randomTimer = new Random();
	private int maxMilliSecondsSleep;
	private int maxElementsToProduceIfStackEmpty;
	private int maxValuesToProduce;
	private boolean notInterrupted = true;

	public Hybrid(Stack<String> elementsStack, double maxSecondsSleep,
			int maxElementsToProduceIfStackEmpty, int maxValuesToProduce) {
		this.elementsStack = elementsStack;
		this.maxMilliSecondsSleep = (int) (maxSecondsSleep * 1000);
		this.maxElementsToProduceIfStackEmpty = maxElementsToProduceIfStackEmpty;
		this.maxValuesToProduce = maxValuesToProduce;
	}

	@Override
	public void run() {
		System.out.println("Hybrid is starting...");

		while (notInterrupted) {
			if (!consume()) {
				startProducing(randomElementsToProduce());
			} else {
				sleep();
			}			
		}

		System.out.println("Hybrid is stopping ...");
	}

	public void stop() {
		notInterrupted = false;
	}

    private boolean consume() {
    	synchronized (elementsStack) {
    		if (elementsStack.isEmpty()) {
    			return false;
    		} else { 
    			elementsStack.pop();
    			elementsStack.notifyAll();
    			return true;
    		}
		}
    }

    private void startProducing(int itemsCount) {
    	System.out.println("Hybrid switched to producing " + itemsCount + " items ...");
    	for (int i = 0; i < itemsCount && notInterrupted; i++) {
    		produce();
    		sleep();
    	}
    	System.out.println("Hybrid switched back to consuming...");
    }
    
	private void produce() {
		synchronized (elementsStack) {
			if (elementsStack.size() >= maxValuesToProduce) {
				try {
					elementsStack.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			elementsStack.push("Item");
			elementsStack.notifyAll();
		}
	}

	private void sleep() {
		try {
			Thread.sleep(randomSleepTime());
		} catch (InterruptedException e) {
			notInterrupted = false;
		}				
	}
	
	private long randomSleepTime() {
		return (long) (randomTimer.nextDouble() * maxMilliSecondsSleep);
	}

	private int randomElementsToProduce() {
		return (int) (randomTimer.nextDouble() * (double)maxElementsToProduceIfStackEmpty);
	}
}
