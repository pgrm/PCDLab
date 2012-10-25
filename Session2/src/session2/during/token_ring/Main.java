package session2.during.token_ring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	private static final int nodesInRing = 10;
	
	private Node[] tokenRing;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		new Main().run();
	}

	public void run() throws IOException {
		createTokenRing();
		startTokenRing();
		waitForLineInput();
		stopTokenRing();
	}
	
	private void createTokenRing() {
		tokenRing = new Node[nodesInRing];
		
		for (int i = 0; i < tokenRing.length; i++) {
			tokenRing[i] = new Node();
		}
		
		setTokenRingSuccessors();
	}

	private void setTokenRingSuccessors() {
		for (int i = 0; i < tokenRing.length - 1; i++) {
			tokenRing[i].setSuccessor(tokenRing[i + 1]);
		}
		
		tokenRing[tokenRing.length - 1].setSuccessor(tokenRing[0]);
	}

	private void startTokenRing() {
		for(Node n : tokenRing) {
			new Thread(n).start();
		}
		
		tokenRing[0].receiveToken();
	}

	private void stopTokenRing() {
		for (Node n : tokenRing) {
			n.stop();
		}
	}

	private void waitForLineInput() throws IOException {
		(new BufferedReader(new InputStreamReader(System.in))).readLine();
	}
}
