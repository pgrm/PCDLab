package session1.after.one;

import java.util.Random;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 04.10.12
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class Consumer implements Runnable, Stopable {
    private Stack<String> stackToConsumeFrom;
    private Random randomTimer = new Random();
    private int maxMilliSecondsSleep;
    private int maxElementsToConsumeAtOnce;
    private boolean notInterrupted = true;

    public Consumer(Stack<String> stackToConsumeFrom, double maxSecondsSleep, int maxElementsToConsumeAtOnce) {
        this.stackToConsumeFrom = stackToConsumeFrom;
        this.maxElementsToConsumeAtOnce = maxElementsToConsumeAtOnce;
        this.maxMilliSecondsSleep =  (int)(maxSecondsSleep * 1000);
    }

    @Override
    public void run() {
        System.out.println("Consumer is starting...");

        while (notInterrupted) {
            consumeAllElements();

            try {
                Thread.sleep(randomSleepTime());
            } catch (InterruptedException e) {
                notInterrupted = false;
            }
        }

        System.out.println("Consumer is stopping ...");
    }

    public void stop() {
        notInterrupted = false;
    }

    private void consumeAllElements() {
    	synchronized (stackToConsumeFrom) {
    		for (int i = 0; i < randomElementsToConsume(); i++) {
    			try {
					consumeElement(i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		stackToConsumeFrom.notifyAll();
		}
    }
    
    private void consumeElement(int elementIndex) throws InterruptedException {
		// if more consumers are running, a while is needed, 
		// since it can be that another consumer has been woken up before this one, 
		// and the stack is empty again.
    	while (stackToConsumeFrom.isEmpty()) {
    		if (elementIndex > 0) {
    			stackToConsumeFrom.notifyAll();
    		}
    		stackToConsumeFrom.wait();
    	}
    	stackToConsumeFrom.pop();
    }

    private long randomSleepTime() {
        return (long)(randomTimer.nextDouble() * maxMilliSecondsSleep);
    }
    
	private int randomElementsToConsume() {
		return (int) (randomTimer.nextDouble() * (double)maxElementsToConsumeAtOnce);
	}
}
