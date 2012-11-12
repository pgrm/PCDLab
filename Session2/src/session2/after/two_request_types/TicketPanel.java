package session2.after.two_request_types;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class TicketPanel {

    private static TicketPanel instance;
    private Map<Integer, Queue<Integer>> tickets = new Hashtable<>();
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
        waitForTicketNumberForWorker(freeWorker);

        Integer nextNum = defineNewTurnForWorker(freeWorker, oldTurn);
        notifyAll();

        return nextNum;
    }

    public synchronized void addNewTicket(int serviceId, Integer ticket) {
        addTicketToQueue(serviceId, ticket);
        notifyAll();
    }

    private void waitForTicketNumberForWorker(OfficeWorker freeWorker) {
        Integer serviceId = freeWorker.handlingServiceId;

        while (!tickets.containsKey(serviceId) || tickets.get(serviceId).size() < 1) {
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

        Integer newTurn = tickets.get(freeWorker.handlingServiceId).poll();
        currentTurns.put(newTurn, freeWorker);
        return newTurn;
    }

    private void addTicketToQueue(Integer serviceId, Integer ticket) {
        if (!tickets.containsKey(serviceId)) {
            tickets.put(serviceId, new LinkedList<Integer>());
        }
        tickets.get(serviceId).add(ticket);
    }
}
