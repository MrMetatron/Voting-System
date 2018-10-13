package client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import base.Candidate;

public class ThreadIn extends Thread {
	private Socket cs;
	private ObjectInputStream in;
	private ArrayList<Candidate> candidates = new ArrayList<>();
	private ClientPanel clientPanel;

	public ThreadIn(Socket cs, ObjectInputStream in, ClientPanel clientPanel) {
		this.cs = cs;
		this.in = in;
		this.clientPanel = clientPanel;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			while (true) {
				try {
					in = new ObjectInputStream(new BufferedInputStream(cs.getInputStream()));
					candidates = (ArrayList<Candidate>)in.readObject();
					System.out.println("From Other:" + candidates.size());
					clientPanel.setList(candidates);
					clientPanel.setCandidates(candidates);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
