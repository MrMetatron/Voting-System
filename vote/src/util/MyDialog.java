package util;

import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 761357367645935474L;

	public MyDialog(String title, String msg) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("<html><h1><i>" + title + "</i></h1><hr><p>" + msg + "</p></html>");
		label.setFont(new Font("华文细黑", Font.ROMAN_BASELINE, 16));
		panel.add(label);
		
		this.setSize(300, 180);
		this.setTitle(title);
		this.add(panel);
		this.setVisible(true);
	}
	
	public MyDialog(String title, String msg, int width, int height) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("<html><h1><i>" + title + "</i></h1><hr><p>" + msg + "</p></html>");
		label.setFont(new Font("华文细黑", Font.ROMAN_BASELINE, 16));
		panel.add(label);
		
		this.setSize(width, height);
		this.setTitle(title);
		this.add(panel);
		this.setVisible(true);
	}
}
