import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FirstPanel extends JPanel {
	private ImageIcon icon = new ImageIcon(getClass().getResource("/순항메리호.gif"));
	private Image img = icon.getImage();
	private Clip clip;
	private JFrame jFrame;
	private String id;
	private JLabel name = new JLabel();
	private ImageIcon icon1 = new ImageIcon(getClass().getResource("/start.png"));
	private Image img1 = icon1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	private ImageIcon icon2 = new ImageIcon(getClass().getResource("score.png"));
	private Image img2 = icon2.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
	private ImageIcon icon3 = new ImageIcon(getClass().getResource("start2.png"));
	private Image img3 = icon3.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	private ImageIcon icon4 = new ImageIcon(getClass().getResource("score2.png"));
	private Image img4 = icon4.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
	private ImageIcon icon5 = new ImageIcon(getClass().getResource("exit.png"));
	private Image img5 = icon5.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
	private ImageIcon icon6 = new ImageIcon(getClass().getResource("exit2.png"));
	private Image img6 = icon6.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
	private ImageIcon icon7 = new ImageIcon(getClass().getResource("스킬칸.png"));
	private Image img7 = icon7.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
	private ImageIcon icon8 = new ImageIcon(getClass().getResource("스킬칸2.png"));
	private Image img8 = icon8.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
	private JButton btn1 = new JButton(new ImageIcon(img1));
	private JButton btn2 = new JButton(new ImageIcon(img2));
	private JButton btn3 = new JButton(new ImageIcon(img5));
	private JButton btn4 = new JButton(new ImageIcon(img7));

	public FirstPanel(JFrame jFrame, Clip clip, String id) {
		this.clip = clip;
		this.id = id;
		this.jFrame = jFrame;
		setLayout(null);

		btn1.setSize(200, 200);
		btn1.setLocation(5, 250);
		btn2.setSize(200, 200);
		btn2.setLocation(2, 450);
		btn3.setSize(150, 150);
		btn3.setLocation(920, 0);

		btn4.setSize(150, 150);
		btn4.setLocation(300, 100);

		btn1.setOpaque(false);
		btn1.setContentAreaFilled(false);
		btn1.setBorderPainted(false);
		btn2.setOpaque(false);
		btn2.setContentAreaFilled(false);
		btn2.setBorderPainted(false);
		btn3.setOpaque(false);
		btn3.setContentAreaFilled(false);
		btn3.setBorderPainted(false);
		btn4.setOpaque(false);
		btn4.setContentAreaFilled(false);
		btn4.setBorderPainted(false);

		add(btn1);
		add(btn2);
		add(btn3);
		add(btn4);
		name.setText("<html>&lt;" + id + "><font color='red'> 해적단</font></html>");
		name.setFont(new Font("Malgun Gothic", Font.BOLD, 60));
		name.setSize(1000, 100);
		name.setLocation(60, 2);
		add(name);
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clip.close();
				removeAllComponents(jFrame.getContentPane());
				GameFrame gameFrame = new GameFrame(id);
				jFrame.revalidate();
				jFrame.repaint();
				System.out.println("홈버튼 클릭 id는 = " + id);
				jFrame.setVisible(false);
			}
		});

		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAllComponents(jFrame.getContentPane());
				RankingPanel rankingPanel = new RankingPanel(jFrame, clip, id);
				jFrame.add(rankingPanel, BorderLayout.CENTER);
				jFrame.revalidate();
				jFrame.repaint();
				System.out.println("랭킹 클릭 id는 = " + id);
			}
		});

		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btn4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAllComponents(jFrame.getContentPane());
				SkillPanel skillPanel = new SkillPanel(jFrame, clip, id);
				jFrame.add(skillPanel, BorderLayout.CENTER);
				jFrame.revalidate();
				jFrame.repaint();
				System.out.println("스킬인포 클릭 id는 = " + id);
			}
		});

		btn1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn1.setIcon(new ImageIcon(img3));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn1.setIcon(new ImageIcon(img1));
			}
		});
		btn2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn2.setIcon(new ImageIcon(img4));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn2.setIcon(new ImageIcon(img2));
			}
		});
		btn3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn3.setIcon(new ImageIcon(img6));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn3.setIcon(new ImageIcon(img5));
			}
		});
		btn4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn4.setIcon(new ImageIcon(img8));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn4.setIcon(new ImageIcon(img7));
			}
		});
		repaint();
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

	}
}
