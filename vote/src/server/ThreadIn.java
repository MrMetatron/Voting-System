package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import base.Candidate;

public class ThreadIn extends Thread {
	private Socket cs;
	private DataInputStream in;
	private ServerPanel serverPanel;
	private ArrayList<Candidate> candidates;

	public ThreadIn(Socket cs, DataInputStream in2, ServerPanel serverPanel) {
		this.cs = cs;
		this.in = in2;
		this.serverPanel = serverPanel;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			in = new DataInputStream(new BufferedInputStream(cs.getInputStream()));
			while (true) {
				int votes = in.readInt();
				serverPanel.setSelectedIndex(votes);
				addPoll(votes);
				System.out.println("From Other:" + votes);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		serverPanel.setClientCounts(serverPanel.getClientCounts()-1);
		serverPanel.setCountLabel(serverPanel.getClientCounts());
	}
	
	private void addPoll(int poll) {
		candidates = serverPanel.getCandidates();
		int polls = candidates.get(poll).getPoll() + 1;
		candidates.get(poll).setPoll(polls);
	}
}
