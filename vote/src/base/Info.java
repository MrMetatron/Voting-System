package base;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class Info extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4663165837857243924L;
	private Font font = new Font("华文细黑", Font.BOLD, 20);
	private Font infoFont = new Font("华文细黑", Font.ROMAN_BASELINE, 16);
	private Border emptyBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);

	private JLabel name;
	private JLabel sex;
	private JLabel special;
	private JLabel infoArea;

	public Info() {
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(layout.LEFT);
		setLayout(null);
		
		//信息栏
		JPanel namePane = new JPanel();		//Name
		
		namePane.setBounds(30, 10, 380, 45);
//		namePane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		namePane.setLayout(layout);
		
		JLabel nameLb = new JLabel("姓名:");
		nameLb.setFont(infoFont);
		name = new JLabel("");			
		name.setFont(infoFont);
		name.setBorder(emptyBorder);
		namePane.add(nameLb);
		namePane.add(name);
		
		JPanel sexPane = new JPanel();		//Sex
		
		sexPane.setBounds(30, 55, 380, 45);
//		sexPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		sexPane.setLayout(layout);
		
		JLabel sexLb = new JLabel("性别:");
		sexLb.setFont(infoFont);
		sex = new JLabel();
		sex.setFont(infoFont);
		sex.setBorder(emptyBorder);
		sexPane.add(sexLb);
		sexPane.add(sex);
		JPanel specPane = new JPanel();		//Specialty
		
		specPane.setBounds(30, 100, 380, 100);
//		specPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		specPane.setLayout(layout);
		
		JLabel speciaLb = new JLabel("特长:");
		speciaLb.setFont(infoFont);
		special = new JLabel();
		special.setFont(infoFont);
		special.setBorder(emptyBorder);
		specPane.add(speciaLb);
		specPane.add(special);
		JPanel infoPane = new JPanel();		//Information
		
		infoPane.setBounds(30, 200, 380, 200);
//		infoPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		infoPane.setLayout(layout);
		
		JLabel thisLb = new JLabel("简介:");
		thisLb.setFont(infoFont);
		infoArea = new JLabel();
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
		infoPane.add(thisLb);
		infoPane.add(scrollPane);
		
		this.add(namePane);
		this.add(sexPane);
		this.add(specPane);
		this.add(infoPane);
	}
	
	public void setName(String name_) {
		name.setText(name_);
	}
	
	public void setSex(String sex_) {
		sex.setText(sex_);
	}
	
	public void setSpeciality(String speciality) {
		special.setText(speciality);
	}
	
	public void setInfo(String info_) {
		infoArea.setText(info_);
	}
}
