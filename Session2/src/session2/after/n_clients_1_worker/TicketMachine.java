package session2.after.n_clients_1_worker;

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

    public synchronized int getNextTicket() {
        currentTicketNumber++;
        Integer num = Integer.valueOf(currentTicketNumber);

        TicketPanel.getInstance().addNewTicket(num);
        return num;
    }
}
