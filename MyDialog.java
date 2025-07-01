import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyDialog extends JDialog {
    private JTextField tf = new JTextField(10);
    private JButton okBt = new JButton("확인");
    private JButton outBt = new JButton("취소");
    private TextSource textSource;

    public MyDialog(JFrame jFrame, String title) {
        super(jFrame, title);
        setLayout(new FlowLayout());
        add(tf);
        add(okBt);
        add(outBt);
        setSize(150, 100);
        setLocation(460, 300);
        okBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textSource != null) {
                    textSource.addWord(tf.getText());
                    setVisible(false);
                }
            }
        });

        outBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    public void setTextSource(TextSource textSource) {
        this.textSource = textSource;
    }
}
