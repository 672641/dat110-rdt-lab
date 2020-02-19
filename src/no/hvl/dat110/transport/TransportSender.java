package no.hvl.dat110.transport;

import java.util.concurrent.LinkedBlockingQueue;

import no.hvl.dat110.network.Datagram;
import no.hvl.dat110.network.NetworkService;

public abstract class TransportSender extends Stopable implements ITransportProtocolEntity {

	protected LinkedBlockingQueue<byte[]> outdataqueue;
	private NetworkService ns;
	
	public TransportSender(String name) {
		super(name);
		outdataqueue = new LinkedBlockingQueue<byte[]>();
	}

	public void register(NetworkService ns) {
		this.ns = ns;
		ns.register(this);
	}
	
	public final void rdt_send(byte[] data) {

		try {
			
			outdataqueue.put(data);
			
		} catch (InterruptedException ex) {
			System.out.println("TransportSender thread " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public final void deliver_data(byte[] data) {

		// should never be called in the current setting
		throw new RuntimeException("deliver_data called in transport sender");
	}
	
	// udt_send should always just send the segment via the underlying network service
	public final void udt_send(Segment segment) {
		System.out.println("[Transport:Sender   ] udt_send: " + segment.toString());
		ns.udt_send(new Datagram(segment));
	}

}
