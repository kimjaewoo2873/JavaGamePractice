import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GameGround extends JPanel {
	private JLabel[] label = new JLabel[30];
	private JTextField textInput = new JTextField(10);
	private TextSource textSource = null;
	private Timer labelTimer;
	private JLabel time = new JLabel();
	private int turn = 0; // 2초동안 생성되는 녀석들의 개수
	private MyThread1[] th = new MyThread1[30];
	private TimerThread timerThread;
	private ImageIcon icon = new ImageIcon(getClass().getResource("/아론파크.png"));
	private Image img = icon.getImage();
	private ImageIcon icon2 = new ImageIcon(getClass().getResource("/아론.png"));
	private Image img2 = icon2.getImage();
	private ImageIcon icon3 = new ImageIcon(getClass().getResource("/아론공격.png"));
	private Image img3 = icon3.getImage();
	private ImageIcon icon4 = new ImageIcon(getClass().getResource("/루피1.png")); // img4
	private Image img4 = icon4.getImage();
	private ImageIcon icon5 = new ImageIcon(getClass().getResource("/게틀링건루피.png")); // img5
	private Image img5 = icon5.getImage();
	private ImageIcon icon6 = new ImageIcon(getClass().getResource("/바주카루피.png")); // img6
	private Image img6 = icon6.getImage();
	private JFrame jFrame;
	private Clip clip;
	private int[] cnt = new int[100];
	private int[] kill = new int[100];
	private ImageIcon heartIcon1 = new ImageIcon(getClass().getResource("/부활부활열매.png"));
	private ImageIcon heartIcon2 = new ImageIcon(getClass().getResource("/부활부활열매.png"));
	private ImageIcon heartIcon3 = new ImageIcon(getClass().getResource("/부활부활열매.png"));
	private Image[] heart = { heartIcon1.getImage(), heartIcon2.getImage(), heartIcon3.getImage() };
	private int heartNum = 3;
	private boolean isColor = false;
	private boolean sk1 = false;
	private boolean sk2 = false;
	private String id;
	private Ranking ranking = new Ranking(null);

	public GameGround(JFrame jFrame, Clip clip, String id) {
		this.jFrame = jFrame;
		this.clip = clip;
		this.id = id;
		setLayout(null);
		textInput.setSize(170, 30);
		textInput.setLocation(335, 600);
		time.setSize(45, 50);
		add(textInput);
		add(time);
		textInput.setFocusable(true);
		textInput.requestFocus();
		labelTimer = new Timer(2000, new ActionListener() {
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

		for (int i = 0; i < cnt.length; i++) {
			cnt[i] = 0;
		}
		timerThread = new TimerThread(this, time, jFrame, clip, th, labelTimer, id);
		timerThread.start();
		textInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField) e.getSource();
				String text = tf.getText();
				for (int i = 0; i < turn; i++) {
					if (label[i] != null && text.equals(label[i].getText()) && kill[i] != 1) {
						if (label[i].getForeground() == Color.BLUE) {
							cnt[i]++;
							if (cnt[i] == 2) {
								kill[i] = 1;
								label[i].setText("");
								label[i].setOpaque(false);
								label[i] = null;
								th[i].interrupt();
							}
						} else {
							kill[i] = 1;
							label[i].setText("");
							label[i].setOpaque(false);
							label[i] = null;
							th[i].interrupt();
						}
					}
				}
				if (text.equals("고무고무 게틀링건")) { // 2명 죽임
					sk1 = true;
					int i = 0;
					int deletedCount = 0;
					while (i < label.length - 1 && deletedCount < 2) {
						if (label[i] != null && label[i + 1] != null && !label[i].getText().equals("")
								&& !label[i + 1].getText().equals("")) {
							label[i].setText("");
							label[i].setOpaque(false);
							label[i] = null;
							kill[i] = 1;
							cnt[i] = 2;
							if (th[i] != null && th[i].isAlive()) {
								th[i].interrupt();
							}
							i += 1;
							deletedCount++;
						} else {
							i++;
						}
					}
					repaint();
				}

				if (text.equals("고무고무 바주카")) { // 1명, 10만큼 밀쳐냄
					sk2 = true;
					int i = 0;
					int deletedCount = 0;
					while (i < label.length - 1 && deletedCount < 1) {
						if (label[i] != null && label[i + 1] != null && !label[i].getText().equals("")
								&& !label[i + 1].getText().equals("")) {
							label[i].setLocation(label[i].getX() + 10, label[i].getY() - 10);
							i += 1;
							deletedCount++;
						} else {
							i++;
						}
					}
				}
				tf.setText("");
			}
		});
		textSource = new TextSource(this);
	}

	public void createLabel(int n) {
		int r1;
		String word = textSource.next();
		label[n] = new JLabel(word);
		FontMetrics fontMetrics = label[n].getFontMetrics(label[n].getFont()); // 폰트 크기 조절 , 깨짐
		int labelWidth = fontMetrics.stringWidth(word) + 30;
		int labelHeight = fontMetrics.getHeight() + 2;
		int persent = (int) (Math.random() * 10 + 1);
		label[n].setOpaque(true);
		label[n].setBackground(Color.WHITE);
		if (persent > 0 && persent <= 3) {
			label[n].setForeground(Color.BLUE);
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
			label[n].setLocation((int) (Math.random() * 201) + 540, 230);
		} else {
			label[n].setLocation(560, (int) (Math.random() * 211) + 20);
		}
		add(label[n]);

		th[n] = new MyThread1(this, label[n]);
		th[n].start();
	}

	public int getTurn() {
		return turn;
	}

	public void removeThread() {
		for (int i = 0; i < th.length; i++) {
			if (th[i] != null)
				th[i].interrupt();
		}
	}

	public int getHeartNum() {
		return heartNum;
	}

	public void updateHeart(int n) {
		heartNum += n;
		repaint();
		if (heartNum == 0) {
			HashMap<String, String> rankingMap = ranking.getPlayerInfo();
			ranking.modifyPlayerInfo(id, "0R-메리호 침몰");
			if (rankingMap.containsKey(id)) {
				System.out.println("랭킹 수정");
				String value = "0R-메리호 침몰";
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
		if (!sk1 && !sk2) {
			g.drawImage(img4, 10, 430, 200, 200, this);
		}
		if (sk1) {
			g.drawImage(img5, 10, 430, 200, 200, this);
		}
		if (sk2) {
			g.drawImage(img6, 10, 430, 200, 200, this);
		}
		sk1 = false;
		sk2 = false;
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

	private void removeAllComponents(Container container) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			container.remove(component);
		}
		container.revalidate();
		container.repaint();
	}
}

class TimerThread extends Thread {
	private int i = 30;
	private JLabel timeLabel;
	private JFrame jFrame;
	private Clip clip;
	private MyThread1[] th;
	private GameGround gameGround;
	private Timer labelTimer;
	private String id;

	public TimerThread(GameGround gameGround, JLabel timeLabel, JFrame jFrame, Clip clip, MyThread1 th[],
			Timer labelTimer, String id) {
		this.gameGround = gameGround;
		this.timeLabel = timeLabel;
		this.jFrame = jFrame;
		this.clip = clip;
		this.th = th;
		this.labelTimer = labelTimer;
		this.id = id;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("id = " + id);
				System.out.println(i);
				if (i <= 5) {
					timeLabel.setOpaque(false);
					timeLabel.setForeground(Color.RED);
					timeLabel.setText(Integer.toString(i));
					timeLabel.setLocation(280, 590);
					timeLabel.setFont(new Font("Gothic", Font.BOLD, 40));
				} else {
					timeLabel.setOpaque(false);
					timeLabel.setForeground(Color.BLACK);
					timeLabel.setText(Integer.toString(i));
					timeLabel.setLocation(280, 590);
					timeLabel.setFont(new Font("Gothic", Font.BOLD, 40));
				}
				if (i <= 0) {
					clip.close();
					removeAllComponents(jFrame.getContentPane());
					System.out.println("heart:" + gameGround.getHeartNum());
					GamePanel2 gamePanel2 = new GamePanel2(jFrame, gameGround.getHeartNum(), id);
					jFrame.add(gamePanel2, BorderLayout.CENTER);
					jFrame.revalidate();
					jFrame.repaint();
					labelTimer.stop();
					for (int j = 0; j < gameGround.getTurn(); j++) {
						if (th[j].isAlive()) {
							th[j].interrupt();
						}
					}
					interrupt();
				}
				sleep(500);
				i--;
			} catch (InterruptedException e) {
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

class MyThread1 extends Thread {
	private JLabel label;
	private GameGround gameGround;
	private boolean actionTaken = false;

	public MyThread1(GameGround gameGround, JLabel label) {
		this.label = label;
		this.gameGround = gameGround;
	}

	@Override
	public void run() { // 스레드 배열중에서 한놈한놈마다 있음, 전체종료해야함
		while (true) {
			int r = (int) (Math.random() * 2);
			int r1 = (int) (Math.random() * (180 - 10 + 1)) + 10;
			int r2 = (int) (Math.random() * (600 - 430 + 1)) + 430;
			if (r == 0) {
				if (r1 <= label.getX()) {
					label.setLocation(label.getX() - 5, label.getY());
				}
			} else {
				if (r2 >= label.getY()) {
					label.setLocation(label.getX(), label.getY() + 5);
				}
			}
			if ((10 <= label.getX() && label.getX() <= 180) && (label.getY() >= 430 && label.getY() <= 600)
					&& !actionTaken) { // y좌표 500까지가면 종료
				label.setText("");
				label.setOpaque(false);
				actionTaken = true;
				this.interrupt();
				gameGround.updateHeart(-1);
			}
			try {
				sleep(300);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
