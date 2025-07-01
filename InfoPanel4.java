import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InfoPanel4 extends JPanel {
	int score = 0;
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	private ImageIcon icon = new ImageIcon(getClass().getResource("루피베리3.png"));
	private Image img = icon.getImage();

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

	}
}
