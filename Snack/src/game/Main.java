package game;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLayeredPane;

public class Main {
	public static final int FRAME_WIDTH=1480;
	public static final int FRAME_HEIGHT=800;
	public static final int FRAME_X=200;
	public static final int FRAME_Y=100;
	public static CalThread calThread=new CalThread();
	// Author: MilkBlock at 杭州十四中 
	public static TheCanvas theCanvas;
	public static TheKeyListener theKeyListener;
	public static Frame mainFrame;
	public static JLayeredPane thePanel;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		mainFrame =new Frame();
		mainFrame.addKeyListener(theKeyListener=new TheKeyListener());
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		mainFrame.setBounds(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);
		mainFrame.setTitle("Minecraft-Snake");
		theCanvas=new TheCanvas(FRAME_WIDTH,FRAME_HEIGHT);
		
		thePanel=new JLayeredPane();
		thePanel.add(theCanvas, new Integer(100));
		
		mainFrame.add(thePanel);
		
		Thread t;
		t=new Thread(calThread);
		t.start();
		calThread.setRunning(true);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		
		System.out.println(mainFrame.getBounds().toString());
	}
	
}
