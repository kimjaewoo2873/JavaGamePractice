package Game;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MonsterFrame extends JFrame{
	public MonsterFrame() {
		setContentPane(new GamePanel());
		setSize(500,500);
		setVisible(true);
		
		getContentPane().setFocusable(true); // 현재 컨텐트팬 키 이벤트는 포커스 줘야한다.
		getContentPane().requestFocus(); // ''	
	}
	class GamePanel extends JPanel{
		private JLabel avataLabel = new JLabel("@");
		private JLabel monsterLabel [] = new JLabel[30];
		MonsterThread th [] = new MonsterThread [30];
		private Timer monsterTimer;
		public GamePanel() {
			setLayout(null);
			avataLabel.setSize(20,20); // 아바타 크기
			avataLabel.setLocation(10,10); // 아바타 위치
			avataLabel.setForeground(Color.BLUE); // 아바타 색상
			add(avataLabel); //패널에 아바타 레이블 추가
			monsterTimer = new Timer(2000, new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					createMonster();
					}
				});
	        monsterTimer.start();
			this.addKeyListener(new MyKey());
		}
		private void createMonster() {
            for (int i = 0; i < monsterLabel.length; i++) {
                if (monsterLabel[i] == null) { //la Check if the monster label is null (not in use)
                    monsterLabel[i] = new JLabel("M");
                    monsterLabel[i].setSize(20, 20);
                    monsterLabel[i].setLocation((int) (Math.random() * 400), (int) (Math.random() * 400));
                    monsterLabel[i].setForeground(Color.RED);
                    add(monsterLabel[i]);

                    th[i] = new MonsterThread(monsterLabel[i]);
                    th[i].start();
                    break; // Exit the loop once a monster is created
                }
            }
        }
		class MonsterThread extends Thread{ // 몬스터 움직이는 스레드
			private JLabel monsterLabel;  // 몬스터 레이블 받기위한(접근) 레이블 하나 생성
			public MonsterThread(JLabel label) { 
				this.monsterLabel = label; // 몬스터 레이블 받기
			}
			@Override
			public void run() { // 스레드의 시작 메소드
				while(true) {		
					if(avataLabel.getX() < monsterLabel.getX())
						monsterLabel.setLocation(monsterLabel.getX() - 5 , monsterLabel.getY());
					else 		
						monsterLabel.setLocation(monsterLabel.getX() + 5 , monsterLabel.getY());
					if(avataLabel.getX() < monsterLabel.getY())
						monsterLabel.setLocation(monsterLabel.getX() , monsterLabel.getY()-5);
					else 
						monsterLabel.setLocation(monsterLabel.getX() , monsterLabel.getY()+5);
					try {
						Thread.sleep(200);
					}catch (InterruptedException e) {
						Container c = monsterLabel.getParent();
						c.remove(monsterLabel);
						c.repaint();
						return;
					}	
				}
			}
		}
		class MyKey extends KeyAdapter{
			@Override
			public void keyPressed(KeyEvent e) { // 키가 눌리면
				int key = e.getKeyCode(); // 키 값 알아내기위한 getKeyCode는 int형 리턴
				switch(key) {
				case KeyEvent.VK_LEFT: // 비교할때 KeyEvent.VK_XXX
					avataLabel.setLocation(avataLabel.getX() - 10,avataLabel.getY());
					break;
				case KeyEvent.VK_RIGHT: 
					avataLabel.setLocation(avataLabel.getX() + 10,avataLabel.getY());
					break;
				case KeyEvent.VK_UP: 
					avataLabel.setLocation(avataLabel.getX(),avataLabel.getY() - 10);
					break;
				case KeyEvent.VK_DOWN: 
					avataLabel.setLocation(avataLabel.getX(),avataLabel.getY() + 10);
					break;
				case KeyEvent.VK_K:
					for(int i=0;i<th.length;i++) {
						th[i].interrupt(); // 죽이기
					}
					break;
				case KeyEvent.VK_X:
					for(int i=0;i<monsterLabel.length;i++) {
						monsterLabel[i].setLocation((int)(Math.random()*400),(int)(Math.random()*400));
					}
					break;
				}
			}
		}
	}
	public static void main(String[] args) {
		new MonsterFrame();
	}
}