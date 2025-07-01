import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class StartFrame extends JFrame {
	private static final int LOOP_CONTINUOUSLY = Clip.LOOP_CONTINUOUSLY;
	private String song = "/수정 우리의꿈.wav";
	private Clip clip;
	private ImageIcon icon = new ImageIcon(getClass().getResource("/우리의꿈.gif")); // 리소스 스트림을 사용하여 ImageIcon 생성
	private Image img = icon.getImage();
	private JTextField login = new JTextField(30);
	private Ranking ranking = new Ranking(null);
	String id = null;

	public StartFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new MyPanel());
		loadAudio(song);
		setSize(1100, 700);
		setVisible(true);
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

	private class MyPanel extends JComponent {
		public MyPanel() {
			login.setSize(120, 20);
			login.setLocation(460, 465);
			add(login);
			login.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					JTextField g = (JTextField) e.getSource();
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						id = g.getText();
						clip.close();
						FirstFrame firstFrame = new FirstFrame(id);
						ranking.addPlayerInfo(id, "선착장");
						StartFrame.this.setVisible(false);
					}
				}
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
	}
}