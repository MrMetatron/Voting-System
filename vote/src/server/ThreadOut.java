package server;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import base.Candidate;

public class ThreadOut extends Thread {
	private Socket cs;
	private ObjectOutputStream out;
	private ServerPanel serverPanel;
	
	public ThreadOut(Socket cs, ObjectOutputStream out2, ServerPanel serverPanel) {
		this.cs = cs;
		this.out = out2;
		this.serverPanel = serverPanel;
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			try {
				out = new ObjectOutputStream(new BufferedOutputStream(cs.getOutputStream()));
				if (!serverPanel.getSendSwitch()) {
					continue;
				}
				out.writeObject(serverPanel.getCandidates());
				out.flush();
//				out.reset();
				serverPanel.setSendSwitch(false);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void setSendSwitch(boolean sendswitch) {
//		this.sendswitch = sendswitch;
	}
}
