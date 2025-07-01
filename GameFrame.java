import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

public class GameFrame extends JFrame {
	private static final int LOOP_CONTINUOUSLY = Clip.LOOP_CONTINUOUSLY;
	private GamePanel gamePanel = null;
	private TextSource textSource = null;
	private String song1 = "/아론파크.wav";
	private ImageIcon icon = new ImageIcon(getClass().getResource("메리호2.png"));
	private Image img = icon.getImage();
	private String id;
	private MyDialog dialog;
	private GameGround gameGround = null;
	private Clip clip;

	public GameFrame(String id) {
		this.id = id;
		setTitle("원 피 스");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		makeMenu();
		dialog = new MyDialog(this, "단어 추가");
		dialog.setTextSource(new TextSource(this));
		loadAudio(song1);
		gamePanel = new GamePanel(this, clip, id);
		add(gamePanel, BorderLayout.CENTER);
		setSize(1100, 700);
		setVisible(true);

	}

	private void makeMenu() { // 메뉴바 생성 메소드
		JMenuBar mb = new JMenuBar();
		mb.setBackground(Color.WHITE);
		setJMenuBar(mb); // add는 여러개 붙히기 가능, set은 한개만 붙히는 것

		JMenu fileMenu = new JMenu("Option");
		JMenuItem Word = new JMenuItem("단어 추가");
		fileMenu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit");
		Word.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(true);
			}
		});
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(Word);
		fileMenu.add(exitItem);
		mb.add(fileMenu);

		JMenu musicMenu = new JMenu("Sound");
		JMenuItem[] musicItem = new JMenuItem[3];
		String[] itemTitle = { "켜기", "끄기", "볼륨 조절" };

		MenuActionListener mal = new MenuActionListener();
		for (int i = 0; i < musicItem.length; i++) {
			musicItem[i] = new JMenuItem(itemTitle[i]);
			musicItem[i].addActionListener(mal);
			musicMenu.add(musicItem[i]);
		}
		mb.add(musicMenu);
	}

	public static void removeAllComponents(Container container) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			if (component instanceof Container) {
				removeAllComponents((Container) component); // 재귀적으로 하위 컨테이너의 컴포넌트 제거
			}
			container.remove(component); // 컴포넌트 제거
		}
		container.revalidate(); // 레이아웃을 재구성하고
		container.repaint(); // 다시 그리기
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

	class MenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String pick = e.getActionCommand();
			if (pick.equals("켜기")) {
				clip.start();
			} else if (pick.equals("끄기")) {
				clip.stop();
			} else { // 볼륨 조절
				MakeSlider ms = new MakeSlider(clip);
				ms.setVisible(true);
			}
		}
	}
}
