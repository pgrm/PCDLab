/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session2.after.n_clients_1_worker;

import java.util.logging.Level;
import java.util.logging.Logger;
import session2.Tuple;
import session2.after.NamedSleepyClass;

/**
 *
 * @author Peter
 */
public class Client extends NamedSleepyClass {

    public Client(String name, long maxMilliSecondsSleep) {
        super(name, maxMilliSecondsSleep);
    }

    @Override
    protected void runImplemented() {
        reportProgress("Going to Town Council Office ...");
        goToTownCouncilOffice();
        reportProgress("Going to sleep...");
        sleepForRandomTime();
    }

    private void goToTownCouncilOffice() {
        int ticketNumber = TicketMachine.getInstance().getNextTicket();
        reportProgress("Waiting for my turn (" + ticketNumber + ") ...");
        OfficeWorker myWorker = waitForMyTurn(ticketNumber);
        reportProgress("Talking to my worker ...");
        myWorker.serviceOfferedToClient(ticketNumber);
    }

    private OfficeWorker waitForMyTurn(int ticketNumber) {
        Tuple<Integer, OfficeWorker> currentTurn;

        synchronized (TicketPanel.getInstance()) {
            do {
                currentTurn = TicketPanel.getInstance().getCurrentNumber();
                waitIfNotMyTurn(ticketNumber, currentTurn.first);
            } while (currentTurn.first != ticketNumber);
        }

        return currentTurn.second;
    }

    private void waitIfNotMyTurn(int myTicketNumber, int currentTicketNumber) {
        if (myTicketNumber != currentTicketNumber) {
            try {
                TicketPanel.getInstance().wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
