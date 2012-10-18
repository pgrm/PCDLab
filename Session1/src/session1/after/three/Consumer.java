package session1.after.three;

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
    private boolean notInterrupted = true;

    public Consumer(Stack<String> stackToConsumeFrom, double maxSecondsSleep) {
        this.stackToConsumeFrom = stackToConsumeFrom;
        this.maxMilliSecondsSleep =  (int)(maxSecondsSleep * 1000);
    }

    @Override
    public void run() {
        System.out.println("Consumer is starting...");

        while (notInterrupted) {
            consume();

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

    private void consume() {
    	synchronized (stackToConsumeFrom) {
    		if (stackToConsumeFrom.isEmpty()) {
				try {
					stackToConsumeFrom.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		} else { 
    			stackToConsumeFrom.pop();
    			stackToConsumeFrom.notifyAll();
    		}
		}
    }

    private long randomSleepTime() {
        return (long)(randomTimer.nextDouble() * maxMilliSecondsSleep);
    }
}
