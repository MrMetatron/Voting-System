package util;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ThreadOut extends Thread {
	private Socket cs;
	private DataOutputStream out;
	
	public ThreadOut(Socket cs, DataOutputStream out) {
		this.cs = cs;
		this.out = out;
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			try {
				out = new DataOutputStream(new BufferedOutputStream(cs.getOutputStream()));

				Scanner scanner = new Scanner(System.in);
				String msg = scanner.nextLine();
				
				out.writeUTF(msg);
				out.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
