package session2.after.n_clients_1_worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    public void run() throws IOException {
        Client[] c = new Client[10];
        OfficeWorker ow = new OfficeWorker("OfficeWorker", 1000);

        initializeAndStartClients(c);
        new Thread(ow).start();

        waitForLineInput();

        stopAllClients(c);
        ow.stop();
    }

    private void initializeAndStartClients(Client[] c) {
        for (int i = 0; i < c.length; i++) {
            c[i] = new Client("Client " + i, 500 + i * 200);
            new Thread(c[i]).start();
        }
    }

    private void stopAllClients(Client[] c) {
        for (Client client : c) {
            client.stop();
        }
    }

    private void waitForLineInput() throws IOException {
        (new BufferedReader(new InputStreamReader(System.in))).readLine();
    }
}
