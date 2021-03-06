/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session2.after.two_request_types;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import session2.after.NamedSleepyClass;

/**
 *
 * @author Peter
 */
public class Client extends NamedSleepyClass {
    public final Integer lookingForServiceId;

    public Client(Integer lookingForServiceId, String name, long maxMilliSecondsSleep) {
        super(name, maxMilliSecondsSleep);
        this.lookingForServiceId = lookingForServiceId;
    }

    @Override
    protected void runImplemented() {
        reportProgress("Going to Town Council Office ...");
        goToTownCouncilOffice();
        reportProgress("Going to sleep...");
        sleepForRandomTime();
    }

    private void goToTownCouncilOffice() {
        int ticketNumber = TicketMachine.getInstance().getNextTicket(lookingForServiceId);
        reportProgress("Waiting for my turn (" + ticketNumber + ") for service (" + lookingForServiceId + ")...");
        OfficeWorker myWorker = waitForMyTurn(ticketNumber);
        reportProgress("Talking to my worker ...");
        myWorker.serviceOfferedToClient(ticketNumber);
    }

    private OfficeWorker waitForMyTurn(int ticketNumber) {
        OfficeWorker myWorker;
        Map<Integer, OfficeWorker> currentTurns;

        synchronized (TicketPanel.getInstance()) {
            do {
                currentTurns = TicketPanel.getInstance().getCurrentTurns();
                myWorker = waitIfNotMyTurn(ticketNumber, currentTurns);
            } while (myWorker == null);
        }

        return myWorker;
    }

    private OfficeWorker waitIfNotMyTurn(int myTicketNumber, Map<Integer, OfficeWorker> currentTurns) {
        Integer myTicket = Integer.valueOf(myTicketNumber);

        if (currentTurns.containsKey(myTicket)) {
            return currentTurns.get(myTicket);
        } else {
            try {
                TicketPanel.getInstance().wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }
}
