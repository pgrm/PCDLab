/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session2.after.two_request_types;

import java.util.logging.Level;
import java.util.logging.Logger;
import session2.after.NamedSleepyClass;

/**
 *
 * @author Peter
 */
public class OfficeWorker extends NamedSleepyClass {
    public final Integer handlingServiceId;
    private Integer currentClientNumber = null;

    public OfficeWorker(int handlingServiceId, String name, long maxMilliSecondsSleep) {
        super(name, maxMilliSecondsSleep);
        this.handlingServiceId = handlingServiceId;
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
        reportProgress("Waiting for next number for service (" + handlingServiceId + ")...");
        currentClientNumber = TicketPanel.getInstance().nextNumber(this, currentClientNumber);
        reportProgress("Waiting to completed service for my client with number " + currentClientNumber);
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
