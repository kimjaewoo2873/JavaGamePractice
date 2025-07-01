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

public class GameGround5 extends JPanel {
	private static final int LOOP_CONTINUOUSLY = Clip.LOOP_CONTINUOUSLY;
	private JLabel[] label = new JLabel[30];
	private JTextField textInput = new JTextField(20);
	private TextSource textSource = null;
	private Timer labelTimer;
	private JLabel time = new JLabel();
	private int turn = 0; // 2초동안 생성되는 녀석들의 개수
	private MyThread5[] th = new MyThread5[30];
	private TimerThread5 timerThread;
	private ImageIcon icon = new ImageIcon(getClass().getResource("카이도패기.gif")); // 카이도우 전쟁
	private Image img = icon.getImage();
	private ImageIcon icon2 = new ImageIcon(getClass().getResource("카이도우공격.png"));
	private Image img2 = icon2.getImage();
	private ImageIcon icon3 = new ImageIcon(getClass().getResource("카이도우공격1.png"));
	private Image img3 = icon3.getImage();
	private ImageIcon icon5 = new ImageIcon(getClass().getResource("멋쟁이루피.png"));
	private Image img5 = icon5.getImage();
	private ImageIcon icon6 = new ImageIcon(getClass().getResource("스네이크맨1.png")); // 스네이크맨 변신
	private Image img6 = icon6.getImage();
	private ImageIcon icon7 = new ImageIcon(getClass().getResource("포스게틀링건.png")); // 콩 게틀링건
	private Image img7 = icon7.getImage();
	private ImageIcon icon8 = new ImageIcon(getClass().getResource("포스맘바.png")); // 블랙맘바
	private Image img8 = icon8.getImage();
	private ImageIcon icon9 = new ImageIcon(getClass().getResource("스네이크맨2.png")); // 히드라
	private Image img9 = icon9.getImage();
	private ImageIcon icon10 = new ImageIcon(getClass().getResource("기어피프스1.png")); // 피프스 변신
	private Image img10 = icon10.getImage();
	private ImageIcon icon11 = new ImageIcon(getClass().getResource("기어피프스2.png")); // 바즈랑 건
	private Image img11 = icon11.getImage();
	private ImageIcon icon12 = new ImageIcon(getClass().getResource("바즈랑건2.png")); // 바즈랑 건
	private Image img12 = icon12.getImage();
	private ImageIcon gifIm = new ImageIcon(getClass().getResource("기어피프스.gif"));
	private Image gif1 = gifIm.getImage();
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
	private boolean skill2 = false;
	private boolean isColor = false;
	private String id;
	private Ranking ranking = new Ranking(null);
	private boolean sk1 = false; // 콩게틀링
	private boolean sk2 = false; // 블랙맘바
	private boolean sk3 = false; // 히드라
	private boolean sk4 = false; // 바즈랑 건
	private boolean backGround = false;

	public GameGround5(JFrame jFrame, Clip clip, int heartNum, String id) {
		this.jFrame = jFrame;
		this.clip = clip;
		this.heartNum = heartNum;
		this.id = id;
		setLayout(null);
		textInput.setSize(170, 30);
		textInput.setLocation(335, 600);
		killLog.setSize(50, 50);
		killLog.setLocation(30, 30);
		killLog.setFont(new Font("Gothic", Font.BOLD, 30));
		add(killLog);
		add(textInput);
		add(time);
		textInput.setFocusable(true);
		textInput.requestFocus();
		labelTimer = new Timer(800, new ActionListener() {
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
		timerThread = new TimerThread5(time, id, labelTimer, th, jFrame, clip, this);
		timerThread.start();
		for (int i = 0; i < cnt.length; i++) {
			cnt[i] = 0;
		}
		textInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField) e.getSource();
				String text = tf.getText();
				if (text.equals("기어 포스 스네이크맨")) {
					skill = true;
				}
				if (text.equals("기어 피프스")) {
					skill2 = true;
					backGround = true;
					System.out.println("피프스 변신");
				}
				for (int i = 0; i < turn; i++) {
					if (label[i] != null && text.equals(label[i].getText()) && kill[i] != 1) {
						if (label[i].getForeground() == Color.MAGENTA) {
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
							// increase
							kill[i] = 1;
							label[i].setText("");
							label[i].setOpaque(false);
							label[i] = null;
							killNum++;
							th[i].interrupt();
						}
						if (killNum == 30) {
							clip.close();
							removeAllComponents(jFrame.getContentPane());
							Laftel laftel = new Laftel(jFrame, id);
							jFrame.add(laftel, BorderLayout.CENTER);
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
					if (text.equals("고무고무 콩 게틀링건")) { // 무작위 5명 삭제
						sk1 = true;
						repaint();
						Timer skillTimer = new Timer(2000, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								sk1 = false;
								repaint();
							}
						});
						skillTimer.setRepeats(false);
						skillTimer.start();
						int i = 0;
						int deletedCount = 0;
						while (i < label.length - 1 && deletedCount < 5) { 
							if (label[i] != null && !label[i].getText().equals("")) {
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
							if (killNum == 30) {
								clip.close();
								removeAllComponents(jFrame.getContentPane());
								Laftel laftel = new Laftel(jFrame, id);
								jFrame.add(laftel, BorderLayout.CENTER);
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
					if (text.equals("고무고무 블랙맘바")) { // 보스몹 5명 원콤
						sk2 = true;
						Timer skillTimer = new Timer(2000, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								sk2 = false;
								repaint();
							}
						});
						skillTimer.setRepeats(false);
						skillTimer.start();
						int i = 0;
						int deletedCount = 0;
						while (i < label.length - 1 && deletedCount < 5) { // 보스몹 5명 삭제
							if (label[i] != null && !label[i].getText().equals("")
									&& label[i].getForeground() == Color.MAGENTA) {
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
							if (killNum == 30) {
								clip.close();
								removeAllComponents(jFrame.getContentPane());
								Laftel laftel = new Laftel(jFrame, id);
								jFrame.add(laftel, BorderLayout.CENTER);
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
					if (text.equals("고무고무 히드라")) { // 9개 원콤내기
						sk3 = true;
						Timer skillTimer = new Timer(2000, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								sk3 = false;
								repaint();
							}
						});
						skillTimer.setRepeats(false);
						skillTimer.start();
						int i = 0;
						int deletedCount = 0;
						while (i < label.length - 1 && deletedCount < 9) {
							if (label[i] != null && !label[i].getText().equals("")) {
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
							if (killNum == 30) {
								clip.close();
								removeAllComponents(jFrame.getContentPane());
								Laftel laftel = new Laftel(jFrame, id);
								jFrame.add(laftel, BorderLayout.CENTER);
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
					if (skill2) {
						if (text.equals("고무고무 바즈랑 건")) { // 현재 패널 속 글자 모두 삭제
							sk4 = true;
							Timer skillTimer = new Timer(2000, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									sk4 = false;
									repaint();
								}
							});
							skillTimer.setRepeats(false);
							skillTimer.start();
							System.out.println("바즈랑 건 사용");
							int i = 0;
							while (i < label.length - 1) {
								if (label[i] != null && !label[i].getText().equals("")) {
									label[i].setText("");
									label[i].setOpaque(false);
									label[i] = null;
									killNum++;
									if (th[i] != null && th[i].isAlive()) {
										th[i].interrupt();
									}
									i += 1;
								} else {
									i++;
								}
								if (killNum == 30) {
									clip.close();
									removeAllComponents(jFrame.getContentPane());
									Laftel laftel = new Laftel(jFrame, id);
									jFrame.add(laftel, BorderLayout.CENTER);
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
					}
				}
				tf.setText("");
				killLog.setOpaque(false);
				killLog.setForeground(Color.YELLOW);
				killLog.setText(Integer.toString(killNum));

			}
		});
		textSource = new TextSource(this);
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
			HashMap<String, String> rankingMap = ranking.getPlayerInfo();
			ranking.modifyPlayerInfo(id, "4R-드레스로자");
			if (rankingMap.containsKey(id)) {
				System.out.println("랭킹 수정");
				String value = "4R-드레스로자";
				rankingMap.put(id, value);
			}
			clip.close();
			labelTimer.stop();
			for (int j = 0; j < getTurn(); j++) {
				if (th[j].isAlive()) {
					th[j].interrupt();
				}
			}
			removeAllComponents(jFrame.getContentPane());
			FailPanel failPanel = new FailPanel(jFrame, id);
			jFrame.add(failPanel, BorderLayout.CENTER);
			jFrame.revalidate();
			jFrame.repaint();

		}
	}

	public void createLabel(int n) {
		int r1;
		String word = textSource.next();
		label[n] = new JLabel(word);
		label[n].setOpaque(true);
		label[n].setBackground(Color.WHITE);
		FontMetrics fontMetrics = label[n].getFontMetrics(label[n].getFont()); // 폰트 크기
		int labelWidth = fontMetrics.stringWidth(word) + 30;
		int labelHeight = fontMetrics.getHeight() + 2;
		int persent = (int) (Math.random() * 10 + 1);
		if (persent > 0 && persent <= 5) { // 보스 공격이 늘어남
			label[n].setForeground(Color.MAGENTA);
			isColor = true;
		} else {
			label[n].setForeground(Color.YELLOW);
			label[n].setBackground(Color.BLACK);
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

		th[n] = new MyThread5(this, label[n]);
		th[n].start();
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
		if (!backGround) {
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		} else {
			g.drawImage(gif1, 0, 0, getWidth(), getHeight(), this);
		}
		if (skill && !sk1 && !sk2 && !sk3) { // 스네이크맨
			if (skill2) {
				g.drawImage(img10, 10, 430, 200, 200, this); // 피프스
				if (sk4) { // 바즈랑건
					g.drawImage(img11, 10, 430, 200, 200, this);
					g.drawImage(img12, 200, 150, 330, 360, this);

				}
			} else {
				g.drawImage(img6, 10, 430, 200, 200, this);
			}
		} else if (skill && sk1) { // 콩 게틀링건
			g.drawImage(img7, 10, 430, 200, 200, this);
		} else if (skill && sk2) { // 블랙맘바
			g.drawImage(img8, 10, 430, 200, 200, this);
		} else if (skill && sk3) { // 히드라
			g.drawImage(img9, 10, 430, 200, 200, this);
		} else {
			g.drawImage(img5, 10, 430, 200, 200, this);
		}
		if (isColor) {
			g.drawImage(img3, 560, 20, 200, 200, this);
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

class TimerThread5 extends Thread {
	private int i = 90;
	private JLabel timeLabel;
	private Ranking ranking = new Ranking(null);
	private String id;
	private Timer labelTimer;
	private MyThread5 th[];
	private JFrame jFrame;
	private Clip clip;
	private GameGround5 gameGround5;

	public TimerThread5(JLabel timeLabel, String id, Timer labelTimer, MyThread5 th[], JFrame jFrame, Clip clip,
			GameGround5 gameGround5) {
		this.timeLabel = timeLabel;
		this.id = id;
		this.labelTimer = labelTimer;
		this.th = th;
		this.jFrame = jFrame;
		this.clip = clip;
		this.gameGround5 = gameGround5;
	}

	@Override
	public void run() {
		while (true) {

			try {
				System.out.println(i);

				timeLabel.setText(Integer.toString(i));
				if (i <= 5) {
					timeLabel.setOpaque(false);
					timeLabel.setForeground(Color.YELLOW);
					timeLabel.setText(Integer.toString(i));
					timeLabel.setLocation(280, 590);
					timeLabel.setFont(new Font("Gothic", Font.BOLD, 40));
				} else {
					timeLabel.setOpaque(false);
					timeLabel.setForeground(Color.WHITE);
					timeLabel.setText(Integer.toString(i));
					timeLabel.setLocation(280, 590);
					timeLabel.setFont(new Font("Gothic", Font.BOLD, 40));
				}
				if (i < 0) {
					HashMap<String, String> rankingMap = ranking.getPlayerInfo();
					ranking.modifyPlayerInfo(id, "4R-드레스로자");
					if (rankingMap.containsKey(id)) {
						System.out.println("랭킹 수정");
						String value = "4R-드레스로자";
						rankingMap.put(id, value);
					}
					clip.close();
					removeAllComponents(jFrame.getContentPane());
					FailPanel failPanel = new FailPanel(jFrame, id);
					jFrame.add(failPanel, BorderLayout.CENTER);
					jFrame.revalidate();
					jFrame.repaint();
					labelTimer.stop();
					for (int j = 0; j < th.length; j++) {
						if (th[j] != null && th[j].isAlive()) {
							th[j].interrupt();
						}
					}
					interrupt();
				}
				sleep(500);
				i--;

			} catch (InterruptedException e) {
				interrupt();
				return;
			}
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

class MyThread5 extends Thread {
	private JLabel label;
	private GameGround5 gameGround5;
	private boolean actionTaken = false;

	public MyThread5(GameGround5 gameGround5, JLabel label) {
		this.label = label;
		this.gameGround5 = gameGround5;
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
					gameGround5.updateHeart(-1);
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
