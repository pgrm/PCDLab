package session2.after.two_request_types;

public class TicketMachine {
    private static TicketMachine instance;
    private int currentTicketNumber = 0;

    private TicketMachine() {
    }

    public synchronized static TicketMachine getInstance() {
        if (instance == null) {
            instance = new TicketMachine();
        }

        return instance;
    }

    public synchronized int getNextTicket(Integer serviceId) {
        currentTicketNumber++;
        Integer num = Integer.valueOf(currentTicketNumber);

        TicketPanel.getInstance().addNewTicket(serviceId, num);
        return num;
    }
}
