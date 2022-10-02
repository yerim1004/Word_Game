import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditPanel extends JPanel {
	private TextSource textSource = new TextSource();
	private JTextField edit = new JTextField(20);
	private JButton addButton = new JButton("add");
	private JButton saveButton = new JButton("save");
	private String wText = null;
	
	public EditPanel() {
		this.setBackground(Color.CYAN);
		this.setLayout(new FlowLayout());
		add(edit);
		add(addButton);
		add(saveButton);
		JLabel ex = new JLabel("add : 단어 추가  save : 점수 저장");
		add(ex);
		scoreSave();
		addWord();
	}
	public void scoreSave() { //add를 눌러 단어를 추가
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wText = edit.getText();
				edit.setText("");
				textSource.wordWrite(wText);
			}
		});
	}
	public void addWord() { //save를 누르면 사용자 이름과 점수를 저장
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wText = edit.getText();
				edit.setText("");
				textSource.textWrite(wText);
			}
		});
	}
}
