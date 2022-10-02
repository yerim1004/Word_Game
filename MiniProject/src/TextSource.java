import java.util.Scanner;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class TextSource {
	private Vector<String> v = new Vector<String>();
	private JLabel[] gameScore = new JLabel[10];
	private JLabel[] playerName = new JLabel[10];
	
	public TextSource() { //파일에서 읽기
		try {
		Scanner scanner = new Scanner(new FileReader("words.txt"));
		while(scanner.hasNext()) {
			String word = scanner.nextLine(); //순수 문자열만
			v.add(word);
		}
		scanner.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public String get() {
		int index = (int)(Math.random()*v.size());
		
		return v.get(index);
	}
	//파일 쓰기
	public void textWrite(String text) {
		
		try( FileWriter fs = new FileWriter("점수_저장.txt" ,true);
			 BufferedWriter bs = new BufferedWriter( fs );
			)
        {
			String wText = text;
        	bs.write(text); //데이터 입력
        	bs.newLine();
        	bs.flush(); //파일에 쓰기
        }
		catch ( IOException e ) {
        	System.out.println(e);
        }
    }
	public void wordWrite(String text) {
		try ( FileWriter fw = new FileWriter("words.txt" ,true);
			  BufferedWriter bw = new BufferedWriter( fw );
		)
		{
			String wText = text;
			bw.write(text); //데이터 입력
        	bw.newLine();
        	bw.flush(); //파일에 쓰기
		}
		catch ( IOException e) {
			System.out.println(e);
		}
	}

	public void bringScore(JPanel window) {
		int i=0;
		int max;
		for(i=0; i<10; i++) {
			gameScore[i] = new JLabel();
			playerName[i] = new JLabel();
		}
		String text;
		i=0;
		try {
			window.setLayout(null);
			Scanner scanner = new Scanner(new FileReader("점수_저장.txt"));
			while(scanner.hasNext()) {
				text = scanner.next(); //순수 문자열만
				playerName[i].setText(text);
				System.out.println(text);
				text = scanner.next();
				System.out.println(text);
				gameScore[i].setText(text);
			
				playerName[i].setLocation(10, 10+(i*20));
				playerName[i].setSize(100, 20);
				gameScore[i].setLocation(120, 10+(i*20));
				gameScore[i].setSize(100, 20);
				window.add(playerName[i]);
				window.add(gameScore[i]);
				i++;
				if(i>9) i=0;
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}
	
}
