import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
	private ImageIcon icon = new ImageIcon(getClass().getResource("/시작루피.gif"));
	private Image img = icon.getImage();

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
