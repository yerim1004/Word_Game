import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
	private int score = 0;
	private JLabel textLabel = new JLabel("점수");
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	private JLabel levelLabel = new JLabel("Level 1");
	
	private ImageIcon lifeIcon = new ImageIcon("./새.png");  //라이프 이미지
	private ImageIcon lifeIcon2 = new ImageIcon("./틀린새.png"); //죽은 라이프 이미지
	private Image img = lifeIcon.getImage();
	private Image img2 = lifeIcon2.getImage();
	
	private JLabel lifeLabel[] = new JLabel[3];
	private int life = 3;
	
	
	public ScorePanel() {
		this.setBackground(Color.WHITE);
		setLayout(null);
		
		textLabel.setSize(40, 30);
		textLabel.setLocation(10, 20);
		textLabel.setFont(new Font("고딕", Font.PLAIN, 15));
		add(textLabel);
		
		scoreLabel.setSize(100, 30);
		scoreLabel.setForeground(Color.BLUE);
		scoreLabel.setFont(new Font("Consolas", Font.PLAIN, 18));
		scoreLabel.setLocation(80,  20);
		add(scoreLabel);
	
		levelLabel.setSize(200, 50);
		levelLabel.setFont(new Font("굴림", Font.BOLD, 25));
		levelLabel.setLocation(10, 80);
		add(levelLabel);
		
		new LifeThread(lifeIcon2).start();
	}
	public void increase() {
		score += 100;
		scoreLabel.setText(Integer.toString(score));
	}
	public void decrease() {
		score -= 50;
		scoreLabel.setText(Integer.toString(score));
	}
	public void s_increase() { //아이템 획득 시 증가하는 점수
		score += 300;
		scoreLabel.setText(Integer.toString(score));
	}
	public void levelUp(int level) {
		if(level==2)
			levelLabel.setText("Level 2");
		else if(level==3)
			levelLabel.setText("Level 3");
	}
	public int delife() {
		life--;
		System.out.println(life);
		new LifeThread(lifeIcon2).start();
		return life;
	}
	public int returnLife()
	{
		return life;
	}
	public int returnScore() {
		return score;
	}
	public void paintLife() {
		for(int i=0; i<life; i++) {
			lifeLabel[i] = new JLabel(lifeIcon);
			lifeLabel[i].setSize(80, 80);
			lifeLabel[i].setLocation((80*i)+10, 200);
			this.add(lifeLabel[i]);
		}
	}
	public void printOver() {
		levelLabel.setText("Game Over...");
	}
	class LifeThread extends Thread { //라이프 스레드 라이프가 깎일 때 동작한다.
		private ImageIcon delife = null;
		public LifeThread() {}
		public LifeThread (ImageIcon delife) {
			this.delife = delife;	
		}
		public void run() {
			switch(life) {
			case 3: //처음 
				paintLife(); break;
			case 2:
				lifeLabel[2].setIcon(delife); break;
			case 1:
				lifeLabel[1].setIcon(delife); break;
			case 0:
				lifeLabel[0].setIcon(delife);
				new LifeThread().interrupt();
			}
		}
	}
	
}
