package cn.edu.sise;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.ClientPanel;
import server.ServerPanel;

public class Client {
	private Font globalFont = new Font("华文细黑", Font.ROMAN_BASELINE, 14);
	private Socket clientSocket;
	
	public static void main(String args[]) {
		Connect start = new Connect();
	}
}

//Server start GUI
class Connect extends JFrame{
	private Font font = new Font("华文细黑", Font.ROMAN_BASELINE, 14);
	public Connect() {
		this.setTitle("客户端");

		Toolkit tools = Toolkit.getDefaultToolkit();
		int screenWidth = (int) tools.getScreenSize().getWidth();
		int screenheight = (int) tools.getScreenSize().getHeight();
		this.setLocation(screenWidth/2 - 200, screenheight/2 - 100);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 100));
		panel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
		
		JTextField host = new JTextField(14);
		host.setText("请输入服务器IP地址");
		host.setFont(font);
		host.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		host.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(host.getText().equals("")) {
					host.setText("请输入服务器IP地址");
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				host.setText("");
			}
		});
		
		JTextField port = new JTextField(5);
		port.setText("端口号");
		port.setFont(font);
		port.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		port.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if (port.getText().equals("")) {
					port.setText("端口号");
				}
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				port.setText("");
				
			}
		});
		
		JButton button = new JButton("Connect");
		button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		button.setFont(font);
		button.setBackground(Color.GRAY);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientPanel mainPanel = new ClientPanel(host.getText(), port.getText());
				setVisible(false);
			}
		});
		
		panel.add(host);
		panel.add(port);
		panel.add(button);
		
		this.getContentPane().add(panel);
		this.pack();
		this.setVisible(true);
	}
}