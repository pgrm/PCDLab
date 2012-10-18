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
public class Producer implements Runnable, Stopable {
    private Stack<String> stackToProduceTo;
    private Random randomTimer = new Random();
    private int maxMilliSecondsSleep;
    private int maxValuesToProduce;
    private boolean notInterrupted = true;

    public Producer(Stack<String> stackToProduceTo, double maxSecondsSleep, int maxValuesToProduce) {
        this.stackToProduceTo = stackToProduceTo;
        this.maxMilliSecondsSleep = (int)(maxSecondsSleep * 1000);
        this.maxValuesToProduce = maxValuesToProduce;
    }

    @Override
    public void run() {
        System.out.println("Producer is starting...");

        while (notInterrupted) {
            produce();

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

    private void produce() {
    	synchronized (stackToProduceTo) {
    		if (stackToProduceTo.size() >= maxValuesToProduce) {
				try {
					stackToProduceTo.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		stackToProduceTo.push("Item");
    		stackToProduceTo.notifyAll();
		}
    }

    private long randomSleepTime() {
        return (long)(randomTimer.nextDouble() * maxMilliSecondsSleep);
    }
}
