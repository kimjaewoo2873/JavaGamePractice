import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RankingPanel extends JPanel {
	private ImageIcon icon = new ImageIcon(getClass().getResource("메리호2.png"));
	private Image img = icon.getImage();
	private ImageIcon icon2 = new ImageIcon(getClass().getResource("랭킹패널.png"));
	private Image img2 = icon2.getImage();
	private ImageIcon icon3 = new ImageIcon(getClass().getResource("엑스버튼2.png"));
	private Image img3 = icon3.getImage();
	private Ranking ranking = new Ranking(null);
	private JFrame jFrame;
	private String id;
	private Clip clip;

	public RankingPanel(JFrame jFrame, Clip clip, String id) {
		this.jFrame = jFrame;
		this.id = id;
		this.clip = clip;
		repaint();
		System.out.println("랭킹패널이동");
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				if (mouseX >= 770 && mouseX <= 820 && mouseY >= 60 && mouseY <= 110) {
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

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(img2, 250, 20, 600, 600, this);
		g.drawImage(img3, 770, 60, 50, 50, this);
		drawRankings(g);
	}

	private void drawRankings(Graphics g) {
		int y = 200;
		Font font = new Font("맑은 고딕", Font.PLAIN + Font.BOLD, 25);
		g.setFont(font);

		HashMap<String, String> rankingMap = ranking.getPlayerInfo();

		Set<String> keys = rankingMap.keySet();
		Iterator<String> it = keys.iterator();

		int i = 0;
		while (i < 10 && it.hasNext()) {
			String name = it.next();
			String score = rankingMap.get(name);

			g.drawString(name + " : " + score, 300, y);
			y += 30;
			i++;
		}
	}

	private void removeAllComponents(Container container) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			container.remove(component);
		}
		container.revalidate();
		container.repaint();
	}
}
