import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

public class GameFrame extends JFrame {
	//이미지 로딩해서 아이콘 제작
	private ImageIcon startIcon = new ImageIcon("./start.png");
	private ImageIcon startIcon2 = new ImageIcon("./start_2.png");
	private ImageIcon stopIcon = new ImageIcon("./stop.png");
	private ImageIcon playIcon = new ImageIcon("./play.png");

	private JButton startBtn = new JButton(startIcon);
	private JButton stopBtn = new JButton(stopIcon);
	private JButton playBtn = new JButton(playIcon);
	private JButton scoreBtn = new JButton("score");
	
	private ScorePanel scorePanel = new ScorePanel();
	private EditPanel editPanel = new EditPanel();
	private Gamepanel gamePanel = new Gamepanel(scorePanel, editPanel);
	private TextSource textSource = new TextSource();
	
	public GameFrame() {
		setTitle("타이핑게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);

		splitPane(); //JSplitPane을 컨텐트팬의 CENTER에
		makeToolBar();
		setResizable(false);
		setVisible(true);
	}
	private void splitPane() {
		JSplitPane hPane = new JSplitPane();
		getContentPane().add(hPane, BorderLayout.CENTER);
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(600);
		hPane.setEnabled(false);
		hPane.setLeftComponent(gamePanel);
		
		JSplitPane pPane = new JSplitPane();
		pPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		pPane.setDividerLocation(400);
		pPane.setTopComponent(scorePanel);
		pPane.setBottomComponent(editPanel);
		//pPane.setVisible(false);
		hPane.setRightComponent(pPane);
	}
	class scoreWindow extends JFrame { //사용자 정보 출력창
		public scoreWindow() {
			setTitle("Top 10");
		JPanel NewWindowContainer = new JPanel();
        setContentPane(NewWindowContainer);
        
        
        textSource.bringScore(NewWindowContainer);
        
        setSize(200,400);
        setResizable(false);
        setVisible(true);
		}
	}

	private void makeToolBar() { //메뉴
		JToolBar tBar = new JToolBar();
		tBar.add(startBtn);
		tBar.add(stopBtn);
		tBar.add(playBtn);
		tBar.add(scoreBtn);
		getContentPane().add(tBar, BorderLayout.NORTH);
		
		startBtn.setRolloverIcon(startIcon2); //<- 아이콘에 ㅅ커서 올릴때
		startBtn.addActionListener(new StartAction());
		stopBtn.addActionListener(new StopAction());
		playBtn.addActionListener(new PlayAction());
		scoreBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new scoreWindow();
			}
		});		
	}
	
	private class StartAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gamePanel.startGame();
		}
	}
	private class StopAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gamePanel.stopGame();
		}
	}
	private class PlayAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gamePanel.reStart();
		}
	}
}
