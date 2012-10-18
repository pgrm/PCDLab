package session1.during.two.one;

import session1.during.one.ConsoleVisualizedStack;
import session1.during.one.Consumer;
import session1.during.one.Producer;
import session1.during.one.VisualizedStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 04.10.12
 * Time: 17:17
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private VisualizedStack<String> stack;

    public static void main(String [] args) throws IOException {
        VisualizedStack<String> stack = new ConsoleVisualizedStack<String>();

        Main mainProgram = new Main(stack);

        mainProgram.run();
    }

    public Main(VisualizedStack<String> stack) {
        this.stack = stack;
    }

    public void run() throws IOException {
        Producer producer = new Producer(stack, 1);
        Consumer consumer = new Consumer(stack, 1.1);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        waitForLineInput();
        consumer.stop();
        producer.stop();
    }

    private void waitForLineInput() throws IOException {
        (new BufferedReader(new InputStreamReader(System.in))).readLine();
    }
}
