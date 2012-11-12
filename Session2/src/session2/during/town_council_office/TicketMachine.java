package session2.during.town_council_office;

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

    public int getNextTicket() {
        currentTicketNumber++;
        Integer num = Integer.valueOf(currentTicketNumber);

        TicketPanel.getInstance().addNewTicket(num);
        return num;
    }
}
