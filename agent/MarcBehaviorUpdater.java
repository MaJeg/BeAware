package agent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public abstract class MarcBehaviorUpdater implements BehaviorUpdater {
	private DatagramSocket _ds;
	private int _port;
	
	protected MarcBehaviorUpdater(int port) {
		_port=port;
		try {
			_ds=new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
