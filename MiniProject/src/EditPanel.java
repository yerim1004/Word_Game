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
		JLabel ex = new JLabel("add : �ܾ� �߰�  save : ���� ����");
		add(ex);
		scoreSave();
		addWord();
	}
	public void scoreSave() { //add�� ���� �ܾ �߰�
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wText = edit.getText();
				edit.setText("");
				textSource.wordWrite(wText);
			}
		});
	}
	public void addWord() { //save�� ������ ����� �̸��� ������ ����
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wText = edit.getText();
				edit.setText("");
				textSource.textWrite(wText);
			}
		});
	}
}
