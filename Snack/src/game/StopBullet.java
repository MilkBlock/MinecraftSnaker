package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public  class StopBullet extends EventUnit{

	int x;
	int y;
	Snake target;
	int direction;//Use Snake directions
	public static Color DEFAULT_ICEBULLET_COLOR;
	public static final int INTERVAL_GOAHEAD=100;
	public static final float DEFAULT_OPCCITY=0.5f;
	private Block blocks[];
	public Ticker ticker_GoAhead;
	int width;
	int height;
	public StopBullet(Snake snake) {
		this.target=snake;
		this.blocks=new Block[6];
		Block base=snake.getNextBlock();
		this.width=base.width;
		this.height=base.height;
		this.direction=snake.getDirection();
		switch(snake.getDirection()) {
		case Snake.DOWN:
			blocks[0]=new PictureBlock(base.getX()-base.getWidth(),base.getY(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			blocks[1]=new PictureBlock(base.getX()-base.getWidth(),base.getY()+base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			
			blocks[2]=new PictureBlock(base.getX(),base.getY()+base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			blocks[3]=new PictureBlock(base.getX(),base.getY()+2*base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			
			blocks[4]=new PictureBlock(base.getX()+base.getWidth(),base.getY(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			blocks[5]=new PictureBlock(base.getX()+base.getWidth(),base.getY()-base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);break;
		case Snake.UP:
			blocks[0]=new PictureBlock(base.getX()-base.getWidth(),base.getY(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			blocks[1]=new PictureBlock(base.getX()-base.getWidth(),base.getY()-base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			
			blocks[2]=new PictureBlock(base.getX(),base.getY()-base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			blocks[3]=new PictureBlock(base.getX(),base.getY()-2*base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			
			blocks[4]=new PictureBlock(base.getX()+base.getWidth(),base.getY(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			blocks[5]=new PictureBlock(base.getX()+base.getWidth(),base.getY()-base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);break;
			
		case Snake.LEFT:
			blocks[0]=new PictureBlock(base.getX(),base.getY()-base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			blocks[1]=new PictureBlock(base.getX()-base.getWidth(),base.getY()-base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			
			blocks[2]=new PictureBlock(base.getX()-base.getWidth(),base.getY(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			blocks[3]=new PictureBlock(base.getX()-2*base.getWidth(),base.getY(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			
			blocks[4]=new PictureBlock(base.getX(),base.getY()+base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			blocks[5]=new PictureBlock(base.getX()-base.getWidth(),base.getY()+base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);break;
		case Snake.RIGHT:
			blocks[0]=new PictureBlock(base.getX(),base.getY()-base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			blocks[1]=new PictureBlock(base.getX()+base.getWidth(),base.getY()-base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			
			blocks[2]=new PictureBlock(base.getX()+base.getWidth(),base.getY(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			blocks[3]=new PictureBlock(base.getX()+2*base.getWidth(),base.getY(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			
			blocks[4]=new PictureBlock(base.getX(),base.getY()+base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);
			blocks[5]=new PictureBlock(base.getX()+base.getWidth(),base.getY()+base.getHeight(),base.getWidth(),base.getHeight(),GameObjectManager.ICE);break;
			
		}		
		
		this.initTickers();
		this.ticker_GoAhead.enable();
	}
	private void initTickers() {
		this.ticker_GoAhead=new Ticker(this,StopBullet.INTERVAL_GOAHEAD,true) {
			public void tick() {
				((StopBullet)this.getEventUnit()).GoAheadForOneBlock();
			}
		};
	}
	private boolean isBumpOtherSnake() {
		boolean ans=false;
		if(GameObjectManager.getInstance().getEnemy(target).getOpaccity()!=1f)return false;
		for(int i=0;i<6;i++)if(GameObjectManager.getInstance().getEnemy(target).body.get(0).sameLocation(this.blocks[i]))ans=true;
		return ans;
	}
	private void GoAheadForOneBlock() {
		if(isOutOfRange()) {
			this.sendMessage(GameObjectManager.getInstance(), Events.OBJECT_REMOVE_SENDER);
			this.ticker_GoAhead.unenable();
		}
		if(isBumpOtherSnake()) {
			this.sendMessage(GameObjectManager.getInstance(), Events.ICEBULLET_BUMP_OTHER_SNAKE);
			this.sendMessage(GameObjectManager.getInstance(), Events.OBJECT_REMOVE_SENDER);
		}
		switch(this.direction) {
		case Snake.UP:for(int i=0;i<6;i++)this.blocks[i].y-=this.height;break;
		case Snake.DOWN:for(int i=0;i<6;i++)this.blocks[i].y+=this.height;break;
		case Snake.LEFT:for(int i=0;i<6;i++)this.blocks[i].x-=this.width;break;
		case Snake.RIGHT:for(int i=0;i<6;i++)this.blocks[i].x+=this.width;break;
		default:		System.out.println(this.direction);		

		}
	}
	private boolean isOutOfRange() {
		if(!this.blocks[4].intersectWith(Main.theCanvas.getCanvasRange())) {System.out.println(Main.theCanvas.getCanvasRange());return true;}
		else return false;
	}
	public void drawObject(Graphics g) {
		Graphics2D g2d=(Graphics2D)g;
		AlphaComposite newComposite=AlphaComposite.getInstance(AlphaComposite.SRC_OVER,StopBullet.DEFAULT_OPCCITY);
		g2d.setComposite(newComposite);
		for(Block block :this.blocks)
			block.drawBlock(g);
		newComposite=AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
	}
}
