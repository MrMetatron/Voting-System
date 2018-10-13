package base;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import server.Add;

public class Result extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3213251195481723120L;
	private Font infoFont = new Font("����ϸ��", Font.ROMAN_BASELINE, 16);
	private ArrayList<Candidate> candidates;
	private int pollSum = 0;

	private RectPane panel;
	
	public Result(ArrayList<Candidate> candidates) {
		//����
		Toolkit tools = Toolkit.getDefaultToolkit();
		int screenWidth = (int) tools.getScreenSize().getWidth();
		int screenheight = (int) tools.getScreenSize().getHeight();
		this.setLocation(screenWidth/2 - 250, screenheight/2 - 300);
		this.candidates = candidates;
		
		for(Candidate candidate : candidates) {
			pollSum += candidate.getPoll();
		}
		
		panel = new RectPane(candidates);
		
		this.getContentPane().add(panel);
		this.setSize(600, 450);
		this.setTitle("ͶƱ���");
		this.setVisible(true);
	}
	
//	public static void main(String[] args) {
//		JFrame frame = new JFrame("Test");
//		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
//		
//		RectPane pane = new RectPane();
//		pane.setPreferredSize(new Dimension(600, 450));
//		
//		frame.getContentPane().add(pane);
//		frame.pack();
//		frame.setVisible(true);
//	}
}

class RectPane extends JPanel{
	private int x = 30;
	private int y = 350;
	private final int gap = 40;
	private final int width = 20;
	private ArrayList<Candidate> candidates;
	
	public RectPane(ArrayList<Candidate> candidates) {
		this.candidates = candidates;
	}
	
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		graphics.setFont(new Font("����ϸ��", Font.ROMAN_BASELINE, 16));
		graphics.setColor(Color.DARK_GRAY);
		graphics.drawLine(50, 20, 50, 380);
		graphics.drawLine(20, 350, 550, 350);
		
		//histogram:ֱ��ͼ
		if (Add.candidateCount != 0) {
			for (int i = 1; i <= Add.candidateCount; i++) {
				int h = candidates.get(i-1).getPoll();
				graphics.fillRect(x+gap*i, y-h, width, h);
				graphics.drawString(String.valueOf(i) + "��", x+gap*i, y+20);
				graphics.drawString(String.valueOf(candidates.get(i-1).getPoll()) + "Ʊ", x+gap*i, y-h-10);
			}
		}
	}
}
