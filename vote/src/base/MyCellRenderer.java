package base;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

public class MyCellRenderer extends JLabel implements ListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3161593514356500051L;

	public MyCellRenderer() {
		// TODO Auto-generated constructor stub
		Border border = BorderFactory.createEmptyBorder(10, 10, 10, 20);
		Font font = new Font("»ªÎÄ·ÂËÎ",Font.HANGING_BASELINE , 20);
		this.setFont(font);
		this.setBorder(border);
		this.setOpaque(true);
		this.setPreferredSize(new Dimension(220, 60));
	}
	
	public MyCellRenderer(String fontName) {
		// TODO Auto-generated constructor stub
		Border border = BorderFactory.createEmptyBorder(10, 10, 10, 20);
		Font font = new Font(fontName,Font.HANGING_BASELINE , 20);
		this.setFont(font);
		this.setBorder(border);
		this.setOpaque(true);
		this.setPreferredSize(new Dimension(220, 60));
	}
	
	public MyCellRenderer(String fontName, int fontSize) {
		// TODO Auto-generated constructor stub
		Border border = BorderFactory.createEmptyBorder(10, 10, 10, 20);
		Font font = new Font(fontName,Font.HANGING_BASELINE , fontSize);
		this.setFont(font);
		this.setBorder(border);
		this.setOpaque(true);
		this.setPreferredSize(new Dimension(220, 60));
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		// TODO Auto-generated method stub 
		
		String name = value.toString();
		String imgUrl = name + ".jpg";
		
		setIcon(new ImageIcon(imgUrl));
        setText(name);
        setIconTextGap(10);

        Color background;
        Color foreground;

        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = Color.BLUE;
            foreground = Color.WHITE;
            System.out.println(dropLocation.getIndex());

        // check if this cell is selected
        } else if (isSelected) {
            background = new Color(220, 220, 220);	
            foreground = Color.WHITE;

        // unselected, and not the DnD drop location
        } else {
            background = new Color(238, 238, 238);
            foreground = Color.BLACK;
        };

        setBackground(background);
        setForeground(foreground);
		return this;
	}

}
