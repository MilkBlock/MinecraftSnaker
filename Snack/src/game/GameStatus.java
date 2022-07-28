package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class GameStatus extends EventUnit{
	public static final int WaitForPlaying=0;
	public static final int Playing=1;
	public static final int End=2;
	public static final int BOOK_BORDER_DISTANCE=25;
	public static final int BOOK_OPACCITY=1;
	private JButton backButton;
	private JButton startButton;
	private JButton switchButton;
	private int status;
	private Snake winner;
	private KeyTracker keyTracker_SPACE;
	private KeyTracker keyTracker_BACK;
	public Selector mapSelector;
	public static final String[] maps= {"作者的大名","苦力怕","末地之门","一间小房子","附魔台",};
	private static GameStatus instance=new GameStatus();
	public int getStatus() {
		return status;
	}
	public void setWinner(Snake snake) {
		this.winner=snake;
	}
	public Snake getWinner() {
		return this.winner;
	}
	private GameStatus() {
		this.initialize();
		
	}
	public void setStatus(int i) {
		System.out.println("setStatus");
		if(i==GameStatus.Playing) {
			System.out.println("Playing");
			this.startButton.setVisible(false);
			this.switchButton.setVisible(false);
			this.backButton.setVisible(false);
			this.mapSelector.unenable();
			Main.calThread.setRunning(true);
			GameObjectManager.getInstance().playerA.setCreeping(true);
			GameObjectManager.getInstance().playerA.getSkills().enable();
			GameObjectManager.getInstance().playerB.setCreeping(true);
			GameObjectManager.getInstance().playerB.getSkills().enable();
		}
		if(i==GameStatus.End) {
			this.backButton.setVisible(true);
			Main.calThread.setRunning(false);
			this.keyTracker_BACK.enable();
		}
		this.status=i;
	}
	public JButton initButton(int x, int y, int Sx, int Sy,Image I, Image o) {
		JButton j = new JButton(new ImageIcon(I));
		j.setSize(Sx, Sy);
		j.setLocation(x, y);
		j.setBorder(null);
		j.setContentAreaFilled(false);
		j.setRolloverIcon(new ImageIcon(o));
		j.setVerticalAlignment(SwingConstants.CENTER);
		
		return j;
	}
	private void initialize() {
		this.isFirstDraw=true;
		System.out.println("GameStatus init");
		
		this.winner=null;
		keyTracker_SPACE=new KeyTracker(this,KeyEvent.VK_SPACE) {
			@Override 
			public void tick() {
				((GameStatus)this.getEventUnit()).setStatus(GameStatus.Playing);
				Main.theKeyListener.removeKeyTracker(this);
			}
		};
		keyTracker_BACK=new KeyTracker(this,KeyEvent.VK_B) {
			@Override 
			public void tick() {
				Main.calThread.clear();
				Main.theKeyListener.clear();
				Main.thePanel.remove(GameStatus.getInstance().startButton);
				Main.thePanel.remove(GameStatus.getInstance().backButton);;
				GameObjectManager.reset();
				GameStatus.reset();
			}
		};
		this.mapSelector=new Selector(Selector.MAP_SELECTOR);
		this.keyTracker_SPACE.enable();
		
		this.setStatus(GameStatus.WaitForPlaying);
	}
	public static GameStatus getInstance() {
		return instance;
	}
	boolean isFirstDraw=true;
	@Override
	public void drawObject(Graphics g) {
		if(this.isFirstDraw) {
			instance.mapSelector.enable();
			this.isFirstDraw=false;
			//init buttons   确保所有图片加载完成
			this.startButton=this.initButton(460,500, 211, 89, GameObjectManager.getInstance().start,GameObjectManager.getInstance().start_press );
			this.backButton=this.initButton(500, 500, 211, 89, GameObjectManager.getInstance().back,GameObjectManager.getInstance().back_press );
			this.switchButton=this.initButton(700, 500, 211, 89, GameObjectManager.getInstance().switch_,GameObjectManager.getInstance().switch_press );
			this.startButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					GameStatus.getInstance().setStatus(GameStatus.Playing);
				}
			});
			this.switchButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("tick");
					if(GameStatus.getInstance().mapSelector.count<GameStatus.maps.length-1)
					GameStatus.getInstance().mapSelector.count++;
					else GameStatus.getInstance().mapSelector.count=0;
					GameObjectManager.getInstance().bg.setMap(Background.MAPS[GameStatus.getInstance().mapSelector.count]);
				}
			});
			this.backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Main.calThread.clear();
					Main.theKeyListener.clear();
					Main.thePanel.remove(GameStatus.getInstance().startButton);
					Main.thePanel.remove(GameStatus.getInstance().backButton);;
					GameObjectManager.reset();
					GameStatus.reset();
				}
			});
			System.out.println("add");
			Main.thePanel.add(this.startButton, new Integer(300));
			Main.thePanel.add(this.switchButton, new Integer(300));
			this.backButton.setVisible(false);
			Main.thePanel.add(this.backButton, new Integer(300));
			Main.mainFrame.setFocusable(true);

		}
		if(this.status==GameStatus.End) {

			GameObjectManager.getInstance().drawImage(GameObjectManager.BOOK, GameStatus.BOOK_BORDER_DISTANCE,GameStatus.BOOK_BORDER_DISTANCE ,
					Main.FRAME_WIDTH-2*GameStatus.BOOK_BORDER_DISTANCE ,Main.FRAME_HEIGHT-2*GameStatus.BOOK_BORDER_DISTANCE, GameStatus.BOOK_OPACCITY,g);
			g.setColor(Color.GRAY);
			g.setFont(new Font("楷体", Font.BOLD, 24));
			g.drawString("Game   Over", Main.FRAME_WIDTH/2-100, Main.FRAME_HEIGHT/2-30);
			g.setColor(Color.RED);
			try{
			if(GameStatus.getInstance().getWinner().getCamp()!=GameObjectManager.CAMP_BLUE)g.drawString("红色阵营获胜", Main.FRAME_WIDTH/2-100, Main.FRAME_HEIGHT/2+40);
			else g.drawString("蓝色阵营获胜", Main.FRAME_WIDTH/2-100, Main.FRAME_HEIGHT/2+40);
			}catch(java.lang.NullPointerException e) {
				return;
			}
			g.setColor(Color.GRAY);
			g.drawString("PlayerA的分数: "+GameObjectManager.getInstance().playerA.score.getScore(), Main.FRAME_WIDTH/2-100, Main.FRAME_HEIGHT/2-80);
			g.drawString("PlayerB的分数: "+GameObjectManager.getInstance().playerB.score.getScore(), Main.FRAME_WIDTH/2-100, Main.FRAME_HEIGHT/2-160);
			g.setColor(Color.red);
		}
		if(this.status==GameStatus.WaitForPlaying) {
			GameObjectManager.getInstance().drawImage(GameObjectManager.BOOK, GameStatus.BOOK_BORDER_DISTANCE,GameStatus.BOOK_BORDER_DISTANCE ,
					Main.FRAME_WIDTH-2*GameStatus.BOOK_BORDER_DISTANCE ,Main.FRAME_HEIGHT-4*GameStatus.BOOK_BORDER_DISTANCE, GameStatus.BOOK_OPACCITY,g);
			g.setColor(Color.gray);
			g.setFont(new Font("楷体",Font.BOLD, 24));
			
			g.drawString("技能 PlayerA :Q隐匿  R反转  F发射冰弹  E突袭", Main.FRAME_WIDTH/2-300, Main.FRAME_HEIGHT/2-30);
			g.drawString("    PlayerB :1隐匿  2反转  3发射冰弹  4突袭", Main.FRAME_WIDTH/2-300, Main.FRAME_HEIGHT/2+10);
		}
		if(this.status==GameStatus.Playing) {
			
		}
	}
	public static void reset() {
		GameStatus.instance.initialize();
	}
}
