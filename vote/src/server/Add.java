package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import base.Candidate;
import base.MyBasicScrollBarUI;
import base.MyScrollPaneLayout;
import base.Result;

public class Add extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3213251195481723120L;
	private Font font = new Font("华文细黑", Font.BOLD, 20);
	private Font infoFont = new Font("华文细黑", Font.ROMAN_BASELINE, 16);
	
	private Border emptyBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);
	
	private JTextField name;
	private JTextField sex;
	private JTextField special;
	private JTextArea infoArea;
	
	public static int candidateCount = 0;
	
	public Add(ArrayList<Candidate> candidates, DefaultListModel<String> listModel, JLabel candi) {
		//居中
		Toolkit tools = Toolkit.getDefaultToolkit();
		int screenWidth = (int) tools.getScreenSize().getWidth();
		int screenheight = (int) tools.getScreenSize().getHeight();
		this.setLocation(screenWidth/2 - 250, screenheight/2 - 300);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		//标题
		JLabel title = new JLabel("填写候选人信息：");
		title.setFont(font);
		panel.add(title, BorderLayout.NORTH);
		
		//信息栏
		JPanel info = new JPanel();
		BoxLayout layout = new BoxLayout(info, BoxLayout.Y_AXIS);
		info.setLayout(layout);
		panel.add(info);
		JPanel namePane = new JPanel();		//Name
		JLabel nameLb = new JLabel("姓名:");
		nameLb.setFont(infoFont);
		name = new JTextField(15);			
		name.setFont(infoFont);
		name.setBorder(emptyBorder);
		namePane.add(nameLb);
		namePane.add(name);
		JPanel sexPane = new JPanel();		//Sex
		JLabel sexLb = new JLabel("性别:");
		sexLb.setFont(infoFont);
		sex = new JTextField(15);
		sex.setFont(infoFont);
		sex.setBorder(emptyBorder);
		sexPane.add(sexLb);
		sexPane.add(sex);
		JPanel specPane = new JPanel();		//Specialty
		JLabel speciaLb = new JLabel("特长:");
		speciaLb.setFont(infoFont);
		special = new JTextField(15);
		special.setFont(infoFont);
		special.setBorder(emptyBorder);
		specPane.add(speciaLb);
		specPane.add(special);
		JPanel infoPane = new JPanel();		//Information
		JLabel infoLb = new JLabel("简介:");
		infoLb.setFont(infoFont);
		infoArea = new JTextArea(5,15);
		infoArea.setFont(infoFont);
		infoArea.setBorder(emptyBorder);
		JScrollPane scrollPane = new JScrollPane();		// BEGIN
		scrollPane.setComponentZOrder(scrollPane.getVerticalScrollBar(), 0);
		scrollPane.setComponentZOrder(scrollPane.getViewport(), 1);
		scrollPane.getVerticalScrollBar().setOpaque(false);
		scrollPane.getVerticalScrollBar().setUnitIncrement(15);
		scrollPane.setLayout(new MyScrollPaneLayout());
		scrollPane.getVerticalScrollBar().setUI(new MyBasicScrollBarUI());
		scrollPane.setBorder(null);
		scrollPane.setViewportView(infoArea);			// END
		infoPane.add(infoLb);
		infoPane.add(scrollPane);
		
		info.add(namePane);
		info.add(sexPane);
		info.add(specPane);
		info.add(infoPane);
		
		//按钮组
		JPanel btnPane = new JPanel();
		btnPane.setPreferredSize(new Dimension(666, 120));
		panel.add(btnPane, BorderLayout.SOUTH);
		JButton submit = new JButton("提交");		//Submit
		btnPane.add(submit);
		submit.setFont(new Font("华文细黑", Font.ROMAN_BASELINE, 20));
		submit.setBackground(Color.GRAY);
		submit.setForeground(Color.WHITE);
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String names = name.getText();
				String sexs = sex.getText();
				String spec = special.getText();
				String info = infoArea.getText();
				
				Candidate candidate = new Candidate();
				candidate.setName(names);
				candidate.setSex(sexs);
				candidate.setSpeciality(spec);
				candidate.setInfo(info);
				candidates.add(candidate);
				if (listModel.getElementAt(0).equals("尚未添加候选人") || listModel.getElementAt(0).equals("")) {
					listModel.clear();
				}
				listModel.addElement((candidateCount+1) + "号: " + candidate.getName());
				candidateCount++;
				
				// 刷新计数
				candi.setText("候选人名单(" + String.valueOf(Add.candidateCount) + "):");
			}
		});
		
		this.getContentPane().add(panel);
		this.setSize(500, 600);
		this.setTitle("添加候选人");
		this.setVisible(true);
	}
}
