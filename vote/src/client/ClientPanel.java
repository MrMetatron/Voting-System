package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import util.MyDialog;

public class ClientPanel extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6427539639279642807L;
	private JPanel centerPanel;
	private Candidate candidate = new Candidate();
	private Info info;
	private Result resultPane;
	private JLabel candi;
	private JList list;
	private DefaultListModel<String> listModel;
	private ArrayList<Candidate> candidates = new ArrayList<>();
	private boolean recvswitch = false;
	private boolean sendSwitch = false;
	private int selectedIdex = 0;
	
	private Socket socket;
	private InetSocketAddress address;
	private ObjectInputStream in;
	private DataOutputStream out;
	private int port;
	private String host;

	public ClientPanel(String h, String p) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		host = h;
		try {
			this.port = Integer.parseInt(p);
		} catch (Exception e) {
			this.port = 3000;
			System.err.println("端口号转换失败，已使用默认端口号：3000");
			e.printStackTrace();
		}
		
		// 初始化连接
		try {
			socket = new Socket();
			address = new InetSocketAddress(host, port);
			socket.connect(address);
			System.out.println("Connected");
			
			ThreadIn threadIn = new ThreadIn(socket, in, this);
			ThreadOut threadOut = new ThreadOut(socket, out, this);
			threadIn.start();
			threadOut.start();
		} catch (Exception e) {
			MyDialog myDialog = new MyDialog("连接出错", "请检查服务器IP地址是否正确！<br>请查看服务器是否已开启！");
			myDialog.setLocationRelativeTo(null);
			e.printStackTrace();
		}

		Toolkit tools = Toolkit.getDefaultToolkit();
		int screenWidth = (int) tools.getScreenSize().getWidth();
		int screenheight = (int) tools.getScreenSize().getHeight();
		this.setLocation(screenWidth/2 - 333, screenheight/2 - 250);	
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		
		JButton voteBtn = new JButton("投他一票");
		voteBtn.setPreferredSize(new Dimension(145,45));
		voteBtn.setForeground(Color.WHITE);
		voteBtn.setFont(new Font("华文细黑", Font.ROMAN_BASELINE, 20));
		voteBtn.setBackground(Color.GRAY);
		voteBtn.setForeground(Color.WHITE);
		voteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setSendSwitch(true);
			}
		});
		centerPanel.add(voteBtn, BorderLayout.SOUTH);
		
		// 候选人信息面板
		info = new Info();
		centerPanel.add(info, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(666, 500));
		panel.setLayout(new BorderLayout());
		
		JToolBar toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(666, 40));
		toolBar.setBackground(Color.GRAY);
		toolBar.setBorder(null);
		
		//chat list
		listModel = new DefaultListModel<String>();
		listModel.addElement("尚未添加候选人");
		
		list = new JList(listModel);
		list.setCellRenderer(new base.MyCellRenderer("华文细黑", 16));
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				 try {
					 selectedIdex = list.getSelectedIndex();
					 System.out.println(list.getSelectedIndex());
					 candidate = candidates.get(selectedIdex);
					 info.setName(candidate.getName());
					 System.out.println(candidate.getName());
					 info.setSex(candidate.getSex());
					 info.setSpeciality(candidate.getSpeciality());
					 info.setInfo(candidate.getInfo());	
				} catch (Exception e2) {
					e2.printStackTrace();
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
		JLabel result = new JLabel("投票结果");		// result
		toolBar.add(result);
		result.setForeground(Color.WHITE);
		result.setFont(new Font("华文细黑", Font.ROMAN_BASELINE, 14));
		result.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		result.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				result.setCursor(new Cursor(Cursor.HAND_CURSOR));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				resultPane = new Result(candidates);
			}
		});
		JLabel connect = new JLabel("重新连接");		// Reconnect
		toolBar.add(connect);
		connect.setForeground(Color.WHITE);
		connect.setFont(new Font("华文细黑", Font.ROMAN_BASELINE, 14));
		connect.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		connect.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				connect.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				//待开发
			}
		});
		
		candi = new JLabel("候选人名单(0):");
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

		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(west, BorderLayout.WEST);
		panel.add(toolBar, BorderLayout.NORTH);
		
		this.setTitle("客户端");
		this.getContentPane().add(panel);
		this.pack();
		this.setVisible(true);
	}
	
	public boolean getRecvSwitch() {
		return recvswitch;
	}
	
	public void setRecvSwitch(boolean recvswitch) {
		this.recvswitch = recvswitch;
	}
	
	public boolean getSendSwitch() {
		return sendSwitch;
	}
	
	public void setSendSwitch(boolean sendswitch) {
		this.sendSwitch = sendswitch;
	}
	
	public int getSelectedIndex() {
		return selectedIdex;
	}
	
	public void setList(ArrayList<Candidate> candidatess) {
		listModel.clear();
		for (int i = 0; i < candidatess.size(); i++) {
			candidate = candidatess.get(i);
			listModel.addElement((i+1) + "号: " + candidate.getName());
		}
	}
	
	public void setCandidates(ArrayList<Candidate> candidates) {
		this.candidates = candidates;
		candi.setText("候选人名单(" + candidates.size() + "):");
	}
}