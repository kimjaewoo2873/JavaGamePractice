package Game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameFrame extends JFrame{
	private static final int LOOP_CONTINUOUSLY = Clip.LOOP_CONTINUOUSLY;
	private Clip clip;
	private String song = "newjeans.wav";
	public GameFrame() {
		setTitle("원피스 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		createMenu();
		setSize(1100,700);
		setVisible(true);
		loadAudio(song);
	}
	public void createMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu musicMenu = new JMenu("음악");
		JMenuItem [] musicItem = new JMenuItem[3];
		String [] itemTitle = {"켜기","끄기","볼륨 조절"};
		
		MenuActionListener mal = new MenuActionListener();
		for(int i=0;i<musicItem.length;i++) {
			musicItem[i] = new JMenuItem(itemTitle[i]);
			musicItem[i].addActionListener(mal);
			musicMenu.add(musicItem[i]);
		}
		mb.add(musicMenu);
		setJMenuBar(mb);
	}
	public void loadAudio(String pathName) {
		try {
			clip = AudioSystem.getClip();
			File audioFile = new File(pathName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
		}
		catch(LineUnavailableException e) { e.printStackTrace();}
		catch(UnsupportedAudioFileException e) { e.printStackTrace();}
		catch(IOException e) { e.printStackTrace();}
		clip.start();
		clip.loop(LOOP_CONTINUOUSLY);
	}
	class MenuActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String pick = e.getActionCommand();
			if(pick.equals("켜기")) {
				clip.start();
			}
			else if(pick.equals("끄기")) {
				clip.stop();
			}
			else { // 볼륨 조절
				makeSlider ms = new makeSlider();
				ms.setVisible(true);
			}
		}
	}
	class makeSlider extends JDialog { // 음량 조절 슬라이드바 생성
		FloatControl control;
		JSlider slider;
		public makeSlider() {
			control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			slider = new JSlider(JSlider.HORIZONTAL,-40,0,(int)control.getValue());
			setLayout(new BorderLayout());
			JLabel label = new JLabel("볼륨 조절");
			
			slider.setPaintLabels(true);
			slider.setPaintTicks(true);
			slider.setPaintTrack(true);
			label.setHorizontalAlignment(JLabel.CENTER);
			add(label,BorderLayout.NORTH);
			add(slider);
			
			slider.addChangeListener(new ChangeListener() { // 슬라이드바 값으로 소리 조절
			    public void stateChanged(ChangeEvent e) {
			        float value = (float) slider.getValue();
			        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			        float minValue = volume.getMinimum();
			        float maxValue = volume.getMaximum();
			        if (value < minValue) {
			            value = minValue;
			        } 
			        else if (value > maxValue) {
			            value = maxValue;
			        }
			        volume.setValue(value);
			        slider.setValue((int)value); 
			    }
			});
			setSize(280,100);
		}
	}
}
