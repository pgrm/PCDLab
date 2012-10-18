package session1.after.three;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA. User: Peter Date: 04.10.12 Time: 17:17 To change
 * this template use File | Settings | File Templates.
 */
public class Main {
	private final int maxElements = 100;
	private final double[] consumerSleeps = new double[] { 0.5, 0.7, 0.3 };
	private final double[] hybridSleeps = new double[] { 0.1, 0.6, 1.1 };
	private final double[] producerSleeps = new double[] { 0.2, 1, 1.5 };

	private final int[] hybridsMaxElementsProduce = new int[] { 2, 10, 20 };

	private VisualizedStack<String> stack;

	public static void main(String[] args) throws IOException {
		VisualizedStack<String> stack = new ConsoleVisualizedStack<String>();

		Main mainProgram = new Main(stack);

		mainProgram.run();
	}

	public Main(VisualizedStack<String> stack) {
		this.stack = stack;
	}

	public void run() throws IOException {
		Thread[] threads = new Thread[consumerSleeps.length
				+ producerSleeps.length + hybridSleeps.length];
		Stopable[] threadsContent = new Stopable[threads.length];

		InitializeProducers(threads, threadsContent, 0);
		InitializeConsumers(threads, threadsContent, producerSleeps.length);
		InitializeHybrids(threads, threadsContent, producerSleeps.length
				+ consumerSleeps.length);
		StartThreads(threads);

		waitForLineInput();
		StopThreads(threadsContent);	
	}

	private void InitializeProducers(Thread[] threads,
			Stopable[] threadsContet, int startIndex) {
		for (int i = startIndex; i < producerSleeps.length; i++) {
			Producer p = new Producer(stack, producerSleeps[i], maxElements);

			threadsContet[i + startIndex] = p;
			threads[i + startIndex] = new Thread(p);
		}
	}

	private void InitializeConsumers(Thread[] threads,
			Stopable[] threadsContent, int startIndex) {
		for (int i = 0; i < consumerSleeps.length; i++) {
			Consumer c = new Consumer(stack, consumerSleeps[i]);

			threadsContent[i + startIndex] = c;
			threads[i + startIndex] = new Thread(c);
		}
	}

	private void InitializeHybrids(Thread[] threads, Stopable[] threadsContent,
			int startIndex) {
		for (int i = 0; i < hybridSleeps.length; i++) {
			Hybrid h = new Hybrid(stack, consumerSleeps[i],
					hybridsMaxElementsProduce[i], maxElements);

			threadsContent[i + startIndex] = h;
			threads[i + startIndex] = new Thread(h);
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
