package session2.during.town_council_office;

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
        Client c = new Client(1000);
        OfficeWorker ow = new OfficeWorker(1000);

        new Thread(c).start();
        new Thread(ow).start();

        waitForLineInput();

        c.stop();
        ow.stop();
    }

    private void waitForLineInput() throws IOException {
        (new BufferedReader(new InputStreamReader(System.in))).readLine();
    }
}
