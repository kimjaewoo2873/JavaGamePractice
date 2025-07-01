import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LastPanel extends JPanel {
	private static final int LOOP_CONTINUOUSLY = Clip.LOOP_CONTINUOUSLY;
	private ImageIcon icon = new ImageIcon(getClass().getResource("라스트.png"));
	private Image img = icon.getImage();
	private ImageIcon icon1 = new ImageIcon(getClass().getResource("home.png"));
	private Image img1 = icon1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	private ImageIcon icon2 = new ImageIcon(getClass().getResource("exit.png"));
	private Image img2 = icon2.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	private ImageIcon icon3 = new ImageIcon(getClass().getResource("home2.png"));
	private Image img3 = icon3.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	private ImageIcon icon4 = new ImageIcon(getClass().getResource("exit2.png"));
	private Image img4 = icon4.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	private JButton btn1 = new JButton(new ImageIcon(img1));
	private JButton btn2 = new JButton(new ImageIcon(img2));
	private Ranking ranking = new Ranking(null);
	private String song = "/다음이야기.wav";
	private String id;
	private JFrame jFrame;
	private Clip clip;

	public LastPanel(JFrame jFrame, String id) {
		this.id = id;
		this.jFrame = jFrame;
		loadAudio(song);
		repaint();
		setLayout(null);

		btn1.setSize(200, 200);
		btn1.setLocation(150, 0);
		btn2.setSize(200, 200);
		btn2.setLocation(800, 0);

		btn1.setOpaque(false);
		btn1.setContentAreaFilled(false);
		btn1.setBorderPainted(false);
		btn2.setOpaque(false);
		btn2.setContentAreaFilled(false);
		btn2.setBorderPainted(false);

		add(btn1);
		add(btn2);
		repaint();

		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clip.close();
				removeAllComponents(jFrame.getContentPane());
				FirstFrame firstFrame = new FirstFrame(id);
				jFrame.revalidate();
				jFrame.repaint();
				jFrame.setVisible(false);
				System.out.println("홈버튼 클릭 id는 = " + id);
				jFrame.setVisible(false);
			}
		});

		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				System.out.println("퇴장버튼 클릭");
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

		HashMap<String, String> rankingMap = ranking.getPlayerInfo();
		ranking.modifyPlayerInfo(id, "Laftel-해적왕");
		if (rankingMap.containsKey(id)) {
			System.out.println("랭킹 수정");
			String value = "Laftel-해적왕";
			rankingMap.put(id, value);
		}
	}

	public void loadAudio(String pathName) { // 오디오 생성 메소드
		try {
			clip = AudioSystem.getClip();
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource(pathName));
			clip.open(audioStream);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		clip.start();
		clip.loop(LOOP_CONTINUOUSLY);
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
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(img2, 150, 0, 200, 200, this);
		g.drawImage(img3, 800, 0, 200, 200, this);

	}
}
