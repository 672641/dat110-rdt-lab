package no.hvl.dat110.transport;

public abstract class Stopable extends Thread {

	private boolean stop = false;
	protected String name;
	
	public Stopable(String name) {
		this.name = name;
	}
	
	public synchronized void doStop() {
		stop = true;
	}

	public synchronized boolean doCont() {
		return !stop;

	}

	public abstract void doProcess();
	
	public void run() {

		System.out.println(name + " running");
		
		while (doCont()) {

			doProcess();
			
		}

		System.out.println(name + " stopping");

	}
}
