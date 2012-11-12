/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session2.during.town_council_office;

import java.util.logging.Level;
import java.util.logging.Logger;
import session2.SleepyClass;

/**
 *
 * @author Peter
 */
public class OfficeWorker extends SleepyClass {

    private int currentClientNumber;

    public OfficeWorker(long maxMilliSecondsSleep) {
        super(maxMilliSecondsSleep);
    }

    public void serviceOfferedToClient(int number) {
        checkClientNumber(number);
        sleepForRandomTime();

        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    protected void runImplemented() {
        currentClientNumber = TicketPanel.getInstance().nextNumber(this);
        waitUntilServiceForClientCompleted();
    }

    private void waitUntilServiceForClientCompleted() {
        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(OfficeWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkClientNumber(int number) {
        if (number != currentClientNumber) {
            throw new IllegalStateException("Client [" + number + "] tried to access the service "
                    + "method, but it was [" + currentClientNumber + "]'s turn.");
        }
    }
}
