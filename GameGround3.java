import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GameGround3 extends JPanel {
	private JLabel[] label = new JLabel[30];
	private JTextField textInput = new JTextField(20);
	private TextSource textSource = null;
	private Timer labelTimer;
	private JLabel time = new JLabel();
	private int turn = 0; // 2초동안 생성되는 녀석들의 개수
	private MyThread3[] th = new MyThread3[30];
	private TimerThread3 timerThread;
	private ImageIcon icon = new ImageIcon(getClass().getResource("하늘섬.png"));
	private Image img = icon.getImage();
	private ImageIcon icon2 = new ImageIcon(getClass().getResource("에넬.png"));
	private Image img2 = icon2.getImage();
	private ImageIcon icon3 = new ImageIcon(getClass().getResource("에넬공격.png"));
	private Image img3 = icon3.getImage();
	private ImageIcon icon4 = new ImageIcon(getClass().getResource("에넬공격1.png"));
	private Image img4 = icon4.getImage();
	private ImageIcon icon5 = new ImageIcon(getClass().getResource("루피.png"));
	private Image img5 = icon5.getImage();
	private ImageIcon icon6 = new ImageIcon(getClass().getResource("기어써드.png")); // 기어 써드
	private Image img6 = icon6.getImage();
	private ImageIcon icon7 = new ImageIcon(getClass().getResource("기어써드왕주먹.png")); // 기간트 게틀링건
	private Image img7 = icon7.getImage();
	private ImageIcon icon8 = new ImageIcon(getClass().getResource("펀치루피.png")); // 기간트 콩건
	private Image img8 = icon8.getImage();
	private ImageIcon icon9 = new ImageIcon(getClass().getResource("바주카루피.png")); // 기간트 바주카
	private Image img9 = icon9.getImage();
	private JFrame jFrame;
	private Clip clip;
	private JLabel killLog = new JLabel();
	private int killNum = 0;
	private int[] cnt = new int[100];
	private int[] kill = new int[100];
	private ImageIcon heartIcon1 = new ImageIcon(getClass().getResource("부활부활열매.png"));
	private ImageIcon heartIcon2 = new ImageIcon(getClass().getResource("부활부활열매.png"));
	private ImageIcon heartIcon3 = new ImageIcon(getClass().getResource("부활부활열매.png"));
	private Image[] heart = { heartIcon1.getImage(), heartIcon2.getImage(), heartIcon3.getImage() };
	private int heartNum;
	private boolean skill = false;
	private boolean isColor = false;
	private String id;
	private Ranking ranking = new Ranking(null);
	private boolean sk1 = false;
	private boolean sk2 = false;
	private boolean sk3 = false;

	public GameGround3(JFrame jFrame, Clip clip, int heartNum, String id) {
		this.jFrame = jFrame;
		this.clip = clip;
		this.heartNum = heartNum;
		this.id = id;
		setLayout(null);
		textInput.setSize(170, 30);
		textInput.setLocation(335, 600);
		time.setSize(45, 50);
		time.setForeground(Color.RED);
		time.setFont(new Font("Gothic", Font.BOLD, 40));
		time.setLocation(375, 550);
		killLog.setSize(50, 50);
		killLog.setLocation(30, 30);
		killLog.setFont(new Font("Gothic", Font.BOLD, 30));
		add(killLog);
		add(textInput);
		add(time);
		textInput.setFocusable(true);
		textInput.requestFocus();
		labelTimer = new Timer(1200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createLabel(turn);
				turn++;
				if (turn == 30) {
					labelTimer.stop();
				}
			}
		});
		labelTimer.start();
		timerThread = new TimerThread3(time, clip, jFrame, labelTimer, this, th);
		timerThread.start();
		for (int i = 0; i < cnt.length; i++) {
			cnt[i] = 0;
		}
		textInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField) e.getSource();
				String text = tf.getText();
				if (text.equals("기어 써드")) {
					skill = true;
				}
				for (int i = 0; i < turn; i++) {
					if (label[i] != null && text.equals(label[i].getText()) && kill[i] != 1) {
						if (label[i].getForeground() == Color.YELLOW) {
							cnt[i]++;
							if (cnt[i] == 2) {
								kill[i] = 1;
								label[i].setText("");
								label[i].setOpaque(false);
								label[i] = null;
								killNum++;
								th[i].interrupt();
							}
						} else {
							kill[i] = 1;
							label[i].setText("");
							label[i].setOpaque(false);
							label[i] = null;
							killNum++;
							th[i].interrupt();
						}
						if (killNum == 20) {
							clip.close();
							removeAllComponents(jFrame.getContentPane());
							GamePanel4 gamePanel4 = new GamePanel4(jFrame, getHeartNum(), id);
							jFrame.add(gamePanel4, BorderLayout.CENTER);
							jFrame.revalidate();
							jFrame.repaint();
							labelTimer.stop();
							timerThread.interrupt();
							for (int j = 0; j < turn; j++) {
								th[j].interrupt();
							}
						}
					}
				}
				if (skill) {
					if (text.equals("고무고무 기간트 게틀링건")) {
						sk1 = true;
						int i = 0;
						int deletedCount = 0;
						while (i < label.length - 1 && deletedCount < 4) { // 4명 삭제
							if (label[i] != null && label[i + 1] != null && !label[i].getText().equals("")
									&& !label[i + 1].getText().equals("")) {
								label[i].setText("");
								label[i].setOpaque(false);
								label[i] = null;
								killNum++;
								kill[i] = 1;
								if (th[i] != null && th[i].isAlive()) {
									th[i].interrupt();
								}
								i += 1;
								deletedCount++;

							} else {
								i++;
							}
							if (killNum == 20) {
								clip.close();
								removeAllComponents(jFrame.getContentPane());
								GamePanel4 gamePanel4 = new GamePanel4(jFrame, getHeartNum(), id);
								jFrame.add(gamePanel4, BorderLayout.CENTER);
								jFrame.revalidate();
								jFrame.repaint();
								labelTimer.stop();
								timerThread.interrupt();
								for (int j = 0; j < turn; j++) {
									th[j].interrupt();
								}
							}
						}
						repaint();
					}
					if (text.equals("고무고무 기간트 콩건")) { // 보스몹 2개 한방 컷
						sk2 = true;
						int i = 0;
						int deletedCount = 0;
						while (i < label.length - 1 && deletedCount < 2) {
							if (label[i] != null && !label[i].getText().equals("")
									&& label[i].getForeground() == Color.YELLOW) {
								label[i].setText("");
								label[i].setOpaque(false);
								label[i] = null;
								killNum++;
								if (th[i] != null && th[i].isAlive()) {
									th[i].interrupt();
								}
								i += 1;
								deletedCount++;

							} else {
								i++;
							}
							if (killNum == 20) {
								clip.close();
								removeAllComponents(jFrame.getContentPane());
								GamePanel4 gamePanel4 = new GamePanel4(jFrame, getHeartNum(), id);
								jFrame.add(gamePanel4, BorderLayout.CENTER);
								jFrame.revalidate();
								jFrame.repaint();
								labelTimer.stop();
								timerThread.interrupt();
								for (int j = 0; j < turn; j++) {
									th[j].interrupt();
								}
							}
						}
						repaint();
					}
					if (text.equals("고무고무 기간트 바주카")) { // 2명, 30만큼 밀침
						sk3 = true;
						int i = 0;
						int deletedCount = 0;
						while (i < label.length - 1 && deletedCount < 2) {
							if (label[i] != null && !label[i].getText().equals("")) {
								label[i].setLocation(label[i].getX() + 30, label[i].getY() - 30);
								i += 1;
								deletedCount++;
							} else {
								i++;
							}
						}
						repaint();
					}
				}
				tf.setText("");
				killLog.setText(Integer.toString(killNum));

			}
		});
		textSource = new TextSource(this);
	}

	public void createLabel(int n) {
		int r1;
		String word = textSource.next();
		label[n] = new JLabel(word);
		label[n].setOpaque(true);
		FontMetrics fontMetrics = label[n].getFontMetrics(label[n].getFont()); // 폰트 크기 조절 , 깨짐
		int labelWidth = fontMetrics.stringWidth(word) + 30;
		int labelHeight = fontMetrics.getHeight() + 2;
		label[n].setBackground(Color.WHITE);
		int persent = (int) (Math.random() * 10 + 1);
		if (persent > 0 && persent <= 3) {
			label[n].setBackground(Color.BLACK);
			label[n].setForeground(Color.YELLOW);
			isColor = true;
			repaint();
		} else {
			label[n].setForeground(Color.BLACK);
			isColor = false;
			repaint();
		}
		label[n].setFont(new Font("Gothic", Font.BOLD + Font.ITALIC, 18));
		label[n].setSize(labelWidth, labelHeight);
		r1 = (int) (Math.random() * 2);
		if (r1 == 0) {
			label[n].setLocation((int) (Math.random() * 201) + 520, 230);
		} else {
			label[n].setLocation(560, (int) (Math.random() * 211) + 20);
		}
		add(label[n]);

		th[n] = new MyThread3(this, label[n], heartNum);
		th[n].start();
	}

	public void removeAllComponents(Container container) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			container.remove(component);
		}
		container.revalidate();
		container.repaint();
	}

	public int getTurn() {
		return turn;
	}

	public int getHeartNum() {
		return heartNum;
	}

	public void updateHeart(int n) {
		heartNum += n;
		repaint();
		if (heartNum == 0) {
			System.out.println("하트 사망");
			HashMap<String, String> rankingMap = ranking.getPlayerInfo();
			ranking.modifyPlayerInfo(id, "2R-알라바스타");
			if (rankingMap.containsKey(id)) {
				System.out.println("랭킹 수정");
				String value = "2R-알라바스타";
				rankingMap.put(id, value);
			}
			clip.close();
			removeAllComponents(jFrame.getContentPane());
			FailPanel failPanel = new FailPanel(jFrame, id);
			jFrame.add(failPanel, BorderLayout.CENTER);
			jFrame.revalidate();
			jFrame.repaint();
			labelTimer.stop();
			timerThread.interrupt();
			for (int j = 0; j < getTurn(); j++) {
				if (th[j].isAlive()) {
					th[j].interrupt();
				}
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		if (skill && !sk1 && !sk2 && !sk3) { // 기어 써드 변신
			g.drawImage(img6, 10, 430, 200, 200, this);
		} else if (skill && sk1) { // 기간트 게틀링건
			g.drawImage(img7, 10, 430, 200, 200, this);
		} else if (skill && sk2) { // 기간트 콩건
			g.drawImage(img8, 10, 430, 200, 200, this);
		} else if (skill && sk3) { // 기간트 바주카
			g.drawImage(img9, 10, 430, 200, 200, this);
		} else {
			g.drawImage(img5, 10, 430, 200, 200, this);
		}
		sk1 = false;
		sk2 = false;
		sk3 = false;
		if (isColor) {
			int rand = (int) (Math.random() * 2);
			if (rand == 1)
				g.drawImage(img3, 560, 20, 200, 200, this);
			else
				g.drawImage(img4, 560, 20, 200, 200, this);
		} else {
			g.drawImage(img2, 560, 20, 200, 200, this);
		}
		int x = 570;
		for (int i = 0; i < heartNum; i++) {
			g.drawImage(heart[i], x, 570, 50, 60, this);
			x += 60;
		}
	}
}

class TimerThread3 extends Thread {
	private int i = 30;
	private JLabel timeLabel;
	private Clip clip;
	private JFrame jFrame;
	private GameGround3 gameGround3;
	private Timer labelTimer;
	private MyThread3 th[];

	public TimerThread3(JLabel timeLabel, Clip clip, JFrame jFrame, Timer labelTimer, GameGround3 gameGround3,
			MyThread3 th[]) {
		this.timeLabel = timeLabel;
		this.clip = clip;
		this.jFrame = jFrame;
		this.labelTimer = labelTimer;
		this.gameGround3 = gameGround3;
		this.th = th;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println(i);
				// timeLabel.setText(Integer.toString(i));
				sleep(1000);
				i--;
			} catch (InterruptedException e) {
				interrupt();
				return;
			}
		}
	}
}

class MyThread3 extends Thread {
	private JLabel label;
	private GameGround3 gameGround3;
	private boolean actionTaken = false;

	public MyThread3(GameGround3 gameGround3, JLabel label, int heartNum) {
		this.label = label;
		this.gameGround3 = gameGround3;
	}

	@Override
	public void run() {
		while (true) {
			int r = (int) (Math.random() * 2);
			int r1 = (int) (Math.random() * (180 - 10 + 1)) + 10;
			int r2 = (int) (Math.random() * (600 - 430 + 1)) + 430;
			if (label != null) {
				if (r == 0) {
					if (r1 <= label.getX()) {
						label.setLocation(label.getX() - 5, label.getY());
					}
				} else {
					if (r2 >= label.getY()) {
						label.setLocation(label.getX(), label.getY() + 5);
					}
				}
			}

			if (label != null) {
				if ((10 <= label.getX() && label.getX() <= 180) && (label.getY() >= 430 && label.getY() <= 600)
						&& !actionTaken) { // y좌표 500까지가면 종료
					label.setText("");
					label.setOpaque(false);
					label = null;
					actionTaken = true;
					this.interrupt();
					gameGround3.updateHeart(-1);
				}
			}

			try {
				sleep(200);
			} catch (InterruptedException e) {
				return;
			}

		}
	}
}