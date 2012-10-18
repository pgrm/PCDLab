package session1.after.two;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Peter Date: 04.10.12 Time: 17:17 To change
 * this template use File | Settings | File Templates.
 */
public class Main {
	private final int maxElements = 100;
	private final double[] consumerSleeps = new double[] { 0.5, 0.3, 0.3 };
	private final double[] producerSleeps = new double[] { 0.2, 0.4, 0.6 };

	private List<VisualizedStack<String>> consumerStacks = new ArrayList<VisualizedStack<String>>();
	private List<VisualizedStack<String>> producerStacks = new ArrayList<VisualizedStack<String>>();

	public static void main(String[] args) throws IOException {
		Main mainProgram = new Main();

		mainProgram.run();
	}

	public Main() {
		VisualizedStack<String> stackX = new ConsoleVisualizedStack<String>("X");
		VisualizedStack<String> stackY = new ConsoleVisualizedStack<String>("Y");

		consumerStacks.add(stackX);
		consumerStacks.add(stackX);
		consumerStacks.add(stackY);

		producerStacks.add(stackX);
		producerStacks.add(stackY);
		producerStacks.add(stackY);
	}

	public void run() throws IOException {
		Thread[] threads = new Thread[consumerSleeps.length
				+ producerSleeps.length];
		Stopable[] threadsContent = new Stopable[threads.length];

		InitializeProducers(threads, threadsContent, 0);
		InitializeConsumers(threads, threadsContent, producerSleeps.length);
		StartThreads(threads);

		waitForLineInput();
		StopThreads(threadsContent);
	}

	private void InitializeProducers(Thread[] threads,
			Stopable[] threadsContet, int startIndex) {
		for (int i = startIndex; i < producerSleeps.length; i++) {
			Producer p = new Producer(producerStacks.get(i), producerSleeps[i],
					maxElements);

			threadsContet[i + startIndex] = p;
			threads[i + startIndex] = new Thread(p);
		}
	}

	private void InitializeConsumers(Thread[] threads,
			Stopable[] threadsContent, int startIndex) {
		for (int i = 0; i < consumerSleeps.length; i++) {
			Consumer c = new Consumer(consumerStacks.get(i), consumerSleeps[i]);

			threadsContent[i + startIndex] = c;
			threads[i + startIndex] = new Thread(c);
		}
	}

	private static void StartThreads(Thread[] threads) {
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
	}

	private static void StopThreads(Stopable[] threads) {
		for (int i = 0; i < threads.length; i++) {
			threads[i].stop();
		}
	}

	private void waitForLineInput() throws IOException {
		(new BufferedReader(new InputStreamReader(System.in))).readLine();
	}
}
