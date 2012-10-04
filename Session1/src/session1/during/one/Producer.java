package session1.during.one;

import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 04.10.12
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class Producer implements Runnable {
    private Stack<String> stackToProduceTo;
    private Random randomTimer = new Random();
    private int maxMilliSecondsSleep;
    private boolean notInterrupted = true;

    public Producer(Stack<String> stackToConsumeFrom, double maxSecondsSleep) {
        this.stackToProduceTo = stackToConsumeFrom;
        this.maxMilliSecondsSleep = (int)(maxSecondsSleep * 1000);
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
        try {
            stackToProduceTo.push("Item");
        } catch (EmptyStackException ex) {
            // can be ignored
        }
    }

    private long randomSleepTime() {
        return (long)(randomTimer.nextDouble() * maxMilliSecondsSleep);
    }
}
