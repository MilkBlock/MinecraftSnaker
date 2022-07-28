package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Candy extends EventUnit{
	public static final Color DEFAULT_CANDY_COLOR=Color.red;
	public static final int CANDYTYPE_BASE=0;
	private Block candyBlock;
	public static Candy GenerateRandomCandy(int candyType) {
		Random r=new Random(System.currentTimeMillis());
		Candy candy = null;
		switch(candyType) {
		case Candy.CANDYTYPE_BASE:candy=new Candy(
				r.nextInt(Main.FRAME_WIDTH/GameObjectManager.BLOCK_WIDTH-6)*GameObjectManager.BLOCK_WIDTH,
				r.nextInt(Main.FRAME_HEIGHT/GameObjectManager.BLOCK_HEIGHT-6)*GameObjectManager.BLOCK_HEIGHT,
				GameObjectManager.BLOCK_WIDTH,GameObjectManager.BLOCK_HEIGHT,Candy.DEFAULT_CANDY_COLOR
				);break;
		//Wait
		default:System.out.println("找不到该类型的candy");
		}
		return candy;
	}
	public void drawObject(Graphics g) {
		candyBlock.drawBlock(g);
	}
	public Block getBlock() {
		return candyBlock;
	}
	public Candy(int x,int y,int width,int height,Color color) {
		this.candyBlock=new Block(x,y,width,height,color);
	}
	@Override
	protected void receiveMessage(EventUnit sender,int eventType) {
		switch(eventType) {
		case Events.CANDY_EATEN:this.sendMessage(GameObjectManager.getInstance(), Events.OBJECT_REMOVE_SENDER);break;
		}
	}
}
