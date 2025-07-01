import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class GamePanel extends JPanel {
	private static final int LOOP_CONTINUOUSLY = Clip.LOOP_CONTINUOUSLY;
	private InfoPanel infoPanel = new InfoPanel();
	private JFrame jFrame;
	private Clip clip;
	private String id;
	public GamePanel(JFrame jFrame,Clip clip,String id) {
		this.jFrame = jFrame;
		this.id = id;
		this.clip = clip;
		setLayout(new BorderLayout());
		splitPanel();
		UIManager.put("SplitPane.border", BorderFactory.createEmptyBorder());

	}
	public void splitPanel() {
		JSplitPane hPane = new JSplitPane();
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		hPane.setDividerLocation(780);

		add(hPane);

		JSplitPane vPane = new JSplitPane();
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		vPane.setDividerLocation(300);
		vPane.setTopComponent(new MapPanel());
		vPane.setBottomComponent(infoPanel);
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
		hPane.setLeftComponent(new GameGround(jFrame, clip,id));
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