import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class InfoPanel2 extends JPanel {
	private ImageIcon icon = new ImageIcon(getClass().getResource("루피베리.png"));
	private Image img = icon.getImage();

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

	}
}
