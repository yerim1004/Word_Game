import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Gamepanel extends JPanel {
	private JTextField input = new JTextField(30);
	private JLabel[] text = new JLabel[10]; // ���� �ܾ��
	private JLabel start = new JLabel("Ÿ���� ������ �����մϴ�");
	JLabel bell = new JLabel();

	private ScorePanel scorePanel = null;
	private EditPanel editPanel = null;
	private TextSource textSource = new TextSource();
	
	private ImageIcon b = new ImageIcon("./��.png");
	private GameGroundPanel groundPanel = null;
	private String newWord = null; //�� �ܾ�
	private int life;
	private int level = 1;
	private boolean flag = false; //�����带 ���� �� ���
	
	public Gamepanel(ScorePanel scorePanel, EditPanel editPanel) {
		this.scorePanel = scorePanel;
		this.editPanel = editPanel;
		groundPanel = new GameGroundPanel(); 	
		setLayout(new BorderLayout());
		add(groundPanel, BorderLayout.CENTER);
		add(new InputPanel(), BorderLayout.SOUTH);
		
	}
	public void startGame() {
		groundPanel.start();
	}
	public void stopGame() {
		flag = true;
	}
	class GameGroundPanel extends JPanel {
		
		public GameGroundPanel() {
			this.setBackground(Color.WHITE);
			setLayout(null);
			start.setSize(100, 40);
			start.setLocation(100,  10);
			add(start);
			
			bell.setIcon(b);
			bell.setSize(150,  600);
			bell.setLocation(-10, -50);
			add(bell);
			
		}
		public void start() {
			start.setText("");
			flag = false;
			MakeThread mth = new MakeThread(this);
			
			mth.start();
		}
	}
	class InputPanel extends JPanel {
		public InputPanel() {
			setLayout(new FlowLayout());
			setBackground(Color.RED);
			this.setFocusable(true);
			this.requestFocus();
			add(input);
			
			AnswerThread ath = new AnswerThread(groundPanel, this);
			ath.start();
		}
	}
	public void makeWord(int i) {
		int x = (int)(Math.random()*20);
		newWord = textSource.get();
		if(newWord == null)
			newWord = textSource.get();

		text[i].setText(newWord); //�ܾ� ���� x��ǥ�� ����, y��ǥ�� ����
		text[i].setSize(90, 20);
		text[i].setBackground(Color.WHITE);
		
		if(x==5 || x==15) //Ư�� �ܾ� ����
			text[i].setBackground(Color.YELLOW);
		
		text[i].setOpaque(true);
		text[i].setLocation(600, (int)(Math.random()*400+20));
		text[i].setVisible(true);
		groundPanel.add(text[i]);
		
		new GameThread(text[i], groundPanel).start();
	}
	class AnswerThread extends Thread {
		private int i=0;
		private GameGroundPanel panel;
		private InputPanel inputPanel;
		public AnswerThread(GameGroundPanel panel, InputPanel inputPanel) {
			this.panel = panel;
			this.inputPanel = inputPanel;
		}
		public void run() {
				input.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//���͸� ������ ȣ��
						answer(e);
					}
				});
				try {
					Thread.sleep(500);	
				}
				catch (InterruptedException e) {return;}
		}
		public void answer(ActionEvent e) {
						
			JTextField t = (JTextField)(e.getSource());	
			String inWord = t.getText();

			for(i=0; i<10; i++) {
				if(text[i].getText().equals(inWord)) { //����		
					//Ư�� �ܾ� ���� Ȯ���ϱ�
					if(text[i].getBackground()==(Color.YELLOW))
						scorePanel.s_increase();
					else
						scorePanel.increase();
					//input ����
					t.setText("");
					text[i].setVisible(false);
					panel.repaint();
					new ScoreThread().start();
					break;
				}
				if(i==9) { //0-9���� ���� �� ã�� ���						
					scorePanel.decrease();
					t.setText("");
				}
			}
		}
	}
	
	 public synchronized void waitGame() {
	      try {
	    	 if(flag==true)
	          wait(); // �����带 �Ͻ� ���� ���·� ���� 
	      } catch (InterruptedException e) {
	          e.printStackTrace();
	     }
	  }
	  public synchronized void reStart() {
	  	flag = false;
	    notify(); // �Ͻ����� ���¿� �ִ� �����带 ���� �����·� ����
	  }
	
	class MakeThread extends Thread {
		private GameGroundPanel panel;
		private int i=0;
		public MakeThread() {}
		public MakeThread(GameGroundPanel panel) {
			this.panel = panel;
			for(int j=0; j<10; j++) {
				text[j] = new JLabel();
			}
		}
		public void run() {
			while(true) {
				if(i>9)
					i=0;
				makeWord(i);
				System.out.println(i);
				i++;
				try {
					Thread.sleep(4000);
					int life = scorePanel.returnLife();
					if(flag)
						waitGame();
					else if (!flag)
						reStart();
					if(life<=0)
						return;
				}
				catch(InterruptedException e) {
					System.out.println("������ ����");
					return;
				}
			}
		}
	}
	
	class GameThread extends Thread {
		private JLabel text =null;
		private GameGroundPanel panel = null;
		private int delay = 1500;
		
		public GameThread() {}
		public GameThread(JLabel text, GameGroundPanel panel) {
			this.text = text;
			this.panel = panel;
		}
		public void run() {
			while(true) { //�� ������ ���� �ð����� �����δ�
				if(text.isVisible())
					text.setLocation(text.getX()-20, text.getY());
				if(text.getX() < 135 && text.isVisible()) {
					String t = text.getText();
					System.out.println(t);
					text.setVisible(false);
					life = scorePanel.delife();
					if(life==0) {
						scorePanel.printOver();
						System.out.println("life=0");
						panel.removeAll();
						panel.repaint();
						return;
					}
				}
				try {
					if(flag)
						waitGame();
					else if(!flag)
						reStart();
					switch(level) {
					case 1: delay = 1300; break;
					case 2: delay = 1100; break;
					case 3: delay = 900; break;
					}
					Thread.sleep(delay);
					panel.repaint();
				}
				catch(InterruptedException e) {	
					System.out.println("������ ����");
					return;
				}
			}
		}
	}
	class ScoreThread extends Thread {
		private int score;
		public ScoreThread() {}
		public void run() {
			score = scorePanel.returnScore();
			if(score>=2000) {
				level=2;

			}
			else if(score>=4000) {
				level=3;
			}
			scorePanel.levelUp(level);
		}
	}
}
