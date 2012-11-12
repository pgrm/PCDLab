package session2.during.town_council_office;

import java.util.LinkedList;
import java.util.Queue;
import session2.Tuple;

public class TicketPanel {

    private static TicketPanel instance;
    private Queue<Integer> tickets = new LinkedList<>();
    private Tuple<Integer, OfficeWorker> currentTurn;

    private TicketPanel() {
    }

    public synchronized static TicketPanel getInstance() {
        if (instance == null) {
            instance = new TicketPanel();
        }

        return instance;
    }

    public Tuple<Integer, OfficeWorker> getCurrentNumber() {
        return currentTurn;
    }

    public synchronized Integer nextNumber(OfficeWorker freeWorker) {
        waitForTicketNumber();

        currentTurn = new Tuple<>(tickets.poll(), freeWorker);
        notifyAll();

        return currentTurn.first;
    }

    public void addNewTicket(Integer ticket) {
        tickets.add(ticket);

        synchronized (this) {
            notifyAll();
        }
    }

    private void waitForTicketNumber() {
        while (tickets.size() < 1) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
