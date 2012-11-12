/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session2.during.town_council_office;

import java.util.logging.Level;
import java.util.logging.Logger;
import session2.SleepyClass;
import session2.Tuple;

/**
 *
 * @author Peter
 */
public class Client extends SleepyClass {

    public Client(long maxMilliSecondsSleep) {
        super(maxMilliSecondsSleep);
    }

    @Override
    protected void runImplemented() {
        System.out.println("Going to Town Council Office ...");
        goToTownCouncilOffice();
        System.out.println("Going to sleep...");
        sleepForRandomTime();
    }

    private void goToTownCouncilOffice() {
        int ticketNumber = TicketMachine.getInstance().getNextTicket();
        System.out.println("Waiting for my turn (" + ticketNumber + ") ...");
        OfficeWorker myWorker = waitForMyTurn(ticketNumber);
        System.out.println("Talking to my worker ...");
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
