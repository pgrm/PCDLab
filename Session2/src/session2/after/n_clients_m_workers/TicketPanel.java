package session2.after.n_clients_m_workers;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class TicketPanel {

    private static TicketPanel instance;
    private Queue<Integer> tickets = new LinkedList<>();
    private Map<Integer, OfficeWorker> currentTurns = new Hashtable<>();

    private TicketPanel() {
    }

    public synchronized static TicketPanel getInstance() {
        if (instance == null) {
            instance = new TicketPanel();
        }

        return instance;
    }

    public Map<Integer, OfficeWorker> getCurrentTurns() {
        return currentTurns;
    }

    public synchronized Integer nextNumber(OfficeWorker freeWorker, Integer oldTurn) {
        waitForTicketNumber();

        Integer nextNum = defineNewTurnForWorker(freeWorker, oldTurn);
        notifyAll();

        return nextNum;
    }

    public synchronized void addNewTicket(Integer ticket) {
        tickets.add(ticket);
        notifyAll();
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

    private Integer defineNewTurnForWorker(OfficeWorker freeWorker, Integer oldTurn) {
        if (oldTurn != null) {
            currentTurns.remove(oldTurn);
        }

        Integer newTurn = tickets.poll();
        currentTurns.put(newTurn, freeWorker);
        return newTurn;
    }
}
