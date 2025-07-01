import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SkillPanel extends JPanel {
	private ImageIcon icon = new ImageIcon(getClass().getResource("스킬설명.png"));
	private Image img = icon.getImage();
	private ImageIcon icon2 = new ImageIcon(getClass().getResource("엑스버튼2.png"));
	private Image img2 = icon2.getImage();
	private Clip clip;
	private JFrame jFrame;
	private String id;

	public SkillPanel(JFrame jFrame, Clip clip, String id) {
		this.jFrame = jFrame;
		this.id = id;
		this.clip = clip;
		repaint();
		System.out.println("필독 이동");
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				if (mouseX >= 526 && mouseX <= 576 && mouseY >= 50 && mouseY <= 100) {
					System.out.println("엑스클릭");
					removeAllComponents(jFrame.getContentPane());
					FirstPanel firstPanel = new FirstPanel(jFrame, clip, id);
					jFrame.add(firstPanel, BorderLayout.CENTER);
					jFrame.revalidate();
					jFrame.repaint();
					System.out.println("퍼스트패널 새로 생성");
				}
			}
		});
	}

	private void removeAllComponents(Container container) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			container.remove(component);
		}
		container.revalidate();
		container.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(img2, 526, 50, 50, 50, this);
	}
}
