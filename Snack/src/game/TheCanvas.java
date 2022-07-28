package game;
import java.awt.*;
public class TheCanvas extends Canvas{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FrameRate frameRate;
	private Image BufferI;
	private Graphics g;
	private Block CanvasRange;//������Ϸ����
	public TheCanvas(int width,int height) {
		frameRate=new FrameRate();
		CanvasRange=new Block();
		CanvasRange.setLocation(this.getX(), this.getY());
		CanvasRange.setSize(width-GameObjectManager.BLOCK_WIDTH,height-GameObjectManager.BLOCK_WIDTH);
		CanvasRange.setColor(Color.gray);
		setSize(width,height);
	}
	public Block getCanvasRange() {
		return this.CanvasRange;
	}
	public void paint(Graphics g) {
		frameRate.calculate();
		g.setColor(Color.BLACK);
		g.drawString(frameRate.getFrameRate(), 30, 70);
		drawObjects(g);
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}
	public void drawObjects(Graphics g) {
		GameObjectManager GameObjs=GameObjectManager.getInstance();
		for(EventUnit eu:GameObjs.getGameObjects()) {
			eu.drawObject(g);
		}
	}
	public void update(Graphics scr) {
		if (BufferI == null) {
			 System.out.println("BufferImage��ʼ��");
			BufferI = createImage(this.getWidth(),this.getHeight());
			g = BufferI.getGraphics();
		}
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		paint(g);
		scr.drawImage(BufferI, 0, 0, this);
	}
}
