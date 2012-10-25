package session2.during.token_ring;

import java.util.Random;

import session2.Stopable;

public class Node implements Runnable, Stopable {
	private static final int maxSleepTimeInMS = 2000;
	
	private Random random = new Random();
	private Node successor;
	
	private boolean canRun = true;
	private boolean hasToken;
	
	public Node getSuccessor() {
		return successor;
	}
	
	public void setSuccessor(Node successor) {
		this.successor = successor;
	}
	
	@Override
	public void run() {
		while (canRun) {
			performWork();
			
			if (hasToken) {
				performTokenWork();
				passOnToken();
			}
		}
	}
	
	@Override
	public void stop() {
		canRun = false;
	}

	public void receiveToken() {
		hasToken = true;
		
		synchronized (this) {
			notify();
		}
	}
	
	private void performWork() {
		System.out.print(".");
		sleep();
	}	
	
	private void performTokenWork() {
		System.out.println("T" + Thread.currentThread().getId());
		sleep();
	}

	private void passOnToken() {
		hasToken = false;
		successor.receiveToken();
	}

	private void sleep() {
		try {
			synchronized (this) {
				wait(getRandomSleepTime());				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int getRandomSleepTime() {
		return (int)(random.nextDouble() * ((double)maxSleepTimeInMS));
	}
}
