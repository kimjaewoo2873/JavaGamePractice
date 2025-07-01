import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel5 extends JPanel {
	private Image img;
	private String pic;

	public InfoPanel5(String pic) {
		this.pic = pic;
		ImageIcon icon = new ImageIcon(getClass().getResource(pic));
		img = icon.getImage();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
