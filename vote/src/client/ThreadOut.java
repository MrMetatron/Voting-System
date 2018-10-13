package client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ThreadOut extends Thread {
	private Socket cs;
	private DataOutputStream out;
	private ClientPanel clientPanel;
	
	public ThreadOut(Socket cs, DataOutputStream out, ClientPanel clientPanel) {
		this.cs = cs;
		this.out = out;
		this.clientPanel = clientPanel;
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			try {
				out = new DataOutputStream(new BufferedOutputStream(cs.getOutputStream()));

				if (!clientPanel.getSendSwitch()) {
					continue;
				}
				int votes = clientPanel.getSelectedIndex();
				out.writeInt(votes);
				out.flush();
				clientPanel.setSendSwitch(false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
