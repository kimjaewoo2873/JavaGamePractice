import java.awt.BorderLayout;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MakeSlider extends JDialog { // 음량 조절 슬라이드바 생성
	FloatControl control;
	JSlider slider;

	public MakeSlider(Clip clip) {
		control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		slider = new JSlider(JSlider.HORIZONTAL, -40, 0, (int) control.getValue());
		setLayout(new BorderLayout());
		JLabel label = new JLabel("볼륨 조절");

		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setPaintTrack(true);
		label.setHorizontalAlignment(JLabel.CENTER);
		add(label, BorderLayout.NORTH);
		add(slider);

		slider.addChangeListener(new ChangeListener() { // 슬라이드바 값으로 소리 조절
			public void stateChanged(ChangeEvent e) {
				float value = (float) slider.getValue();
				FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				float minValue = volume.getMinimum();
				float maxValue = volume.getMaximum();
				if (value < minValue) {
					value = minValue;
				} else if (value > maxValue) {
					value = maxValue;
				}
				volume.setValue(value);
				slider.setValue((int) value);
			}
		});
		setSize(280, 100);
	}
}