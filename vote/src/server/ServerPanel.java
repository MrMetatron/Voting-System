package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import base.Candidate;
import base.Info;
import base.MyBasicScrollBarUI;
import base.MyScrollPaneLayout;
import base.Result;

public class ServerPanel extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6427539639279642807L;
	private Font font = new Font("华文细黑", Font.ROMAN_BASELINE, 20);
	private ArrayList<Candidate> candidates = new ArrayList<Candidate>();
	private Candidate candidate = null;
	private Result resultPane;
	private JLabel candi;
	private int selectedIndex = 0;
	private  boolean sendswitch = false;
	private JLabel online;
	private int clientCounts = 0;

	public ServerPanel(String host, String port) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		InitNetwor initNetwor = new InitNetwor(host, port, this);
		initNetwor.start();

		Toolkit tools = Toolkit.getDefaultToolkit();
		int screenWidth = (int) tools.getScreenSize().getWidth();
		int screenheight = (int) tools.getScreenSize().getHeight();
		this.setLocation(screenWidth/2 - 333, screenheight/2 - 250);	
		
		// 候选人信息面板
		Info info = new Info();
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(666, 500));
		panel.setLayout(new BorderLayout());
		
		JToolBar toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(666, 40));
		toolBar.setBackground(Color.GRAY);
		toolBar.setBorder(null);
		
		//chat list
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		if (candidates.isEmpty()) {
			listModel.addElement("尚未添加候选人");
		}
		
		JList list = new JList(listModel);
		list.setCellRenderer(new base.MyCellRenderer("华文细黑", 16));
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				 try {
					 int index = list.getSelectedIndex();
					 candidate = candidates.get(index);
					 info.setName(candidate.getName());
					 info.setSex(candidate.getSex());
					 info.setSpeciality(candidate.getSpeciality());
					 info.setInfo(candidate.getInfo());	
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		
		JPanel listPane = new JPanel();
		listPane.add(list);
		
		//List panel in the west-2-top
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setComponentZOrder(scrollPane.getVerticalScrollBar(), 0);
		scrollPane.setComponentZOrder(scrollPane.getViewport(), 1);
		scrollPane.getVerticalScrollBar().setOpaque(false);
		scrollPane.getVerticalScrollBar().setUnitIncrement(15);
		scrollPane.setLayout(new MyScrollPaneLayout());
		scrollPane.getVerticalScrollBar().setUI(new MyBasicScrollBarUI());
		scrollPane.setBorder(null);
		scrollPane.setViewportView(listPane);

		//ToolBar
		JLabel tNew = new JLabel("添加候选人");		// Add
		toolBar.add(tNew);
		tNew.setForeground(Color.WHITE);
		tNew.setFont(new Font("华文细黑", Font.ROMAN_BASELINE, 14));
		tNew.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		tNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				candidate = new Candidate();
				Add add = new Add(candidates, listModel, candi);
			}
		});		
		JLabel listName = new JLabel("推送名单");		// list of name
		toolBar.add(listName);
		listName.setForeground(Color.WHITE);
		listName.setFont(new Font("华文细黑", Font.ROMAN_BASELINE, 14));
		listName.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		listName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				listName.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				setSendSwitch(true);
			}
		});
		JLabel result = new JLabel("投票结果");		// result
		toolBar.add(result);
		result.setForeground(Color.WHITE);
		result.setFont(new Font("华文细黑", Font.ROMAN_BASELINE, 14));
		result.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		result.addMouseListener(new  MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				result.setCursor(new Cursor(Cursor.HAND_CURSOR));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				resultPane = new Result(candidates);
			}
		});
		online = new JLabel("在线人数(0)");		// Online
		toolBar.add(online);
		online.setForeground(Color.WHITE);
		online.setFont(new Font("华文细黑", Font.ROMAN_BASELINE, 14));
		online.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JLabel Test = new JLabel("测试");		// Test
		toolBar.add(Test);
		Test.setForeground(Color.WHITE);
		Test.setFont(new Font("华文细黑", Font.ROMAN_BASELINE, 14));
		Test.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Test.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Test.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("测试中...");
				for (Candidate candidate : candidates) {
					System.out.println(candidate.getName());
					System.out.println(Add.candidateCount);
					System.out.println("添加成功");
				}
			}
		});
		
		candi = new JLabel("候选人名单(" + String.valueOf(Add.candidateCount) + "):");
		candi.setFont(new Font("华文细黑", Font.BOLD, 20));
		candi.setPreferredSize(new Dimension(228, 60));
		JPanel canPane = new JPanel();
		canPane.add(candi);
		JPanel west = new JPanel();
		west.setLayout(new BorderLayout());
		west.add(canPane, BorderLayout.NORTH);
		west.add(scrollPane, BorderLayout.CENTER);
		west.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		west.setBackground(Color.WHITE);

		panel.add(info, BorderLayout.CENTER);
		panel.add(west, BorderLayout.WEST);
		panel.add(toolBar, BorderLayout.NORTH);
		
		this.setTitle("服务器端");
		this.getContentPane().add(panel);
		this.pack();
		this.setVisible(true);
	}
	
	public boolean getSendSwitch() {
		return sendswitch;
	}
	
	public void setSendSwitch(boolean sendswitchs) {
		sendswitch = sendswitchs;
	}
	
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}
	
	public int getClientCounts() {
		return clientCounts;
	}
	
	public void setClientCounts(int newCounts) {
		clientCounts = newCounts;
	}
	
	public void setCountLabel(int count) {
		online.setText("在线人数(" + count + ")");
		online.repaint();
	}
	
	public ArrayList<Candidate> getCandidates() {
		return candidates;
	}
}

class InitNetwor extends Thread{
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private InetSocketAddress address;
	private String host = null;
	private int port = 0;
	private ServerPanel sPane;
	private int count = 0;
	
	
	public InitNetwor(String host, String port, ServerPanel serverPanel) {
		this.sPane = serverPanel;
		this.host = host;
		try {
			this.port = Integer.parseInt(port);
		} catch (Exception e) {
			this.port = 3000;
			System.err.println("端口号转换失败，已使用默认端口号：3000");
		}
	}
	
	@Override
	public void run() {
		super.run();
		address = new InetSocketAddress(host, port);
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(address);
			while (true) {
				clientSocket = serverSocket.accept();
				count = sPane.getClientCounts();
				sPane.setClientCounts(++count);
				sPane.setCountLabel(count);
				ClientThread clientThread = new ClientThread(clientSocket, count, sPane);
				clientThread.start();
			}
		} catch (IOException e) {
			// 服务器默认网络状态
			try {
				serverSocket = new ServerSocket(port);
			while (true) {
				clientSocket = serverSocket.accept();
				count = sPane.getClientCounts();
				sPane.setClientCounts(++count);
				sPane.setCountLabel(count);
				ClientThread clientThread = new ClientThread(clientSocket, count, sPane);
				clientThread.start();
			}
			} catch (IOException e1) {
				System.out.println(e1.getStackTrace());
				System.err.println("网络服务启动失败");
			}
			System.err.println("已使用默认网络配置：localhost、3000");
		}
	}
}