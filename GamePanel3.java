import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class GamePanel3 extends JPanel {
	private static final int LOOP_CONTINUOUSLY = Clip.LOOP_CONTINUOUSLY;
	private Clip clip;
	private JFrame jFrame;
	private int heartNum;
	private String song = "/하늘섬.wav";
	private InfoPanel3 infoPanel3 = new InfoPanel3();
	private String id;

	public GamePanel3(JFrame jFrame, int heartNum, String id) {
		this.jFrame = jFrame;
		this.heartNum = heartNum;
		this.id = id;
		setLayout(new BorderLayout());
		loadAudio(song);
		splitPanel();
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


	private void splitPanel() {
		JSplitPane hPane = new JSplitPane();
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		hPane.setDividerLocation(780);

		add(hPane);

		JSplitPane vPane = new JSplitPane();
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		vPane.setDividerLocation(300);
		vPane.setTopComponent(new MapPanel3());
		vPane.setBottomComponent(infoPanel3);
		vPane.setUI(new BasicSplitPaneUI() {
			public BasicSplitPaneDivider createDefaultDivider() {
				return new BasicSplitPaneDivider(this) {
					@Override
					public void paint(Graphics g) {
						g.setColor(Color.WHITE); // 테두리 색상 변경
						g.fillRect(0, 0, getWidth(), getHeight());
						super.paint(g);
					}
				};
			}
		});
		hPane.setRightComponent(vPane);
		hPane.setLeftComponent(new GameGround3(jFrame, clip, heartNum, id));
		hPane.setUI(new BasicSplitPaneUI() {
			public BasicSplitPaneDivider createDefaultDivider() {
				return new BasicSplitPaneDivider(this) {
					@Override
					public void paint(Graphics g) {
						g.setColor(Color.WHITE); // 테두리 색상 변경
						g.fillRect(0, 0, getWidth(), getHeight());
						super.paint(g);
					}
				};
			}
		});
	}
}
