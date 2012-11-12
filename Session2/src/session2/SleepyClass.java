/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session2;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import session2.during.town_council_office.Client;

/**
 *
 * @author Peter
 */
public abstract class SleepyClass implements Runnable, Stopable {
    protected boolean continueRunning = true;
    protected long maxMilliSecondsSleep;
    protected Random randomTimer = new Random();

    public SleepyClass(long maxMilliSecondsSleep) {
        this.maxMilliSecondsSleep = maxMilliSecondsSleep;
    }

    @Override
    public void run() {
        while (continueRunning) {
            runImplemented();
        }
    }

    @Override
    public void stop() {
        continueRunning = false;
    }

    protected abstract void runImplemented();

    protected void sleepForRandomTime() {
        try {
            Thread.sleep(randomSleepTime());
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private long randomSleepTime() {
        return (long) (randomTimer.nextDouble() * maxMilliSecondsSleep);
    }
}
