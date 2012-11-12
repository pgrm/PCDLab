package session2.after.n_clients_m_workers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import session2.Stopable;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    public void run() throws IOException {
        Client[] clients = new Client[100];
        OfficeWorker[] workers = new OfficeWorker[30];

        initializeAndStartClients(clients);
        initializeAndStartWorkers(workers);

        waitForLineInput();

        stopAll(clients);
        stopAll(workers);
    }

    private void initializeAndStartClients(Client[] clients) {
        for (int i = 0; i < clients.length; i++) {
            clients[i] = new Client("Client " + i, 500 + i * 200);
            new Thread(clients[i]).start();
        }
    }

    private void initializeAndStartWorkers(OfficeWorker[] workers) {
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new OfficeWorker("OfficeWorker " + i, 1000 + i * 150);
            new Thread(workers[i]).start();
        }
    }

    private void stopAll(Stopable[] stopables) {
        for (Stopable s : stopables) {
            s.stop();
        }
    }

    private void waitForLineInput() throws IOException {
        (new BufferedReader(new InputStreamReader(System.in))).readLine();
    }
}
