package util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadIn extends Thread {
	private Socket cs;
	private DataInputStream in;

	public ThreadIn(Socket cs, DataInputStream in) {
		this.cs = cs;
		this.in = in;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			in = new DataInputStream(new BufferedInputStream(cs.getInputStream()));
			while (true) {
				String msg = in.readUTF();
				System.out.println("From Other:" + msg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
