package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class Snake extends EventUnit{
	public CopyOnWriteArrayList<Block> body;
	private Skills skills;
	private int direction=-2;
	private int camp;
	private int hearts;
	public Score score;
	public static Color DEFAULT_SNAKE_COLOR=Color.ORANGE;
	public static final int LIFES=3;
	public static final float DEFAULT_DISAPPEAR_OPACCITY=0.3f;
	public static final int DEFAULT_STEP_TIME=300;
	public static final int FROZEN_INTERVAL=700;
	public static final int FAST_STEP_TIME=100;
	public static final int UP=1;
	public static final int DOWN=-1;
	public static final int LEFT=2;
	public static final int RIGHT=-2;
	public static final int HEARTS=3;
	private boolean leftBlock=false;
	private boolean isCreeping=false;
	private float opaccity=1;
	
	private KeyTracker keyTrakcer_UP;
	private KeyTracker keyTrakcer_DOWN;
	private KeyTracker keyTrakcer_LEFT;
	private KeyTracker keyTrakcer_RIGHT;
	
	public Ticker ticker_Creep;
	public Ticker ticker_Frozen;
	
	public Snake(int len,int camp,Block head ) {
		this.hearts=Snake.LIFES;
		body=new CopyOnWriteArrayList<Block>();
		this.camp=camp;
		switch(camp) {
		case GameObjectManager.DEFAULT_SNAKE_CAMP:head.setColor(Color.orange);break;
		case GameObjectManager.CAMP_RED:head.setColor(Color.BLUE);break;
		case GameObjectManager.CAMP_BLUE:head.setColor(Color.ORANGE);break;
		default:System.out.println("无法识别此阵营");
		}
		body.add(new PictureBlock(head,GameObjectManager.NULL_PICTURE_ID));
		while(len>0) {
			this.plusLength();
			len--;
		}
		this.score=new Score(this);
		this.initializeTickers();
		this.initializeKeyTrackers();
		this.skills=new Skills(this);
	}
	public int getHearts() {
		return this.hearts;
	}
	public int frozenCount=0;
	public void frozen() {
		this.setCreeping(false);
		this.ticker_Frozen.enable();
	}
	public void unfrozen() {
		this.setCreeping(true);
		for(Block block:this.body) {
			block=new Block(block);
		}
	}
	public void frozenOneBlock(Block block) {
		block=new PictureBlock(block,GameObjectManager.ICE);
	}
	public Skills getSkills() {
		return this.skills;
	}
	public int getCamp() {
		return this.camp;
	}
	public float getOpaccity() {
		return this.opaccity;
	}
	public void setOpaccity(float op) {
		this.opaccity=op;
		for(Block block:this.body)
			block.setOpaccity(op);
	}
	public void setPicture(int ID) {
		for(Block block:this.body) {
			((PictureBlock)block).setPictureID(ID);
		}
	}
	public void disappear() {
		this.setOpaccity(Snake.DEFAULT_DISAPPEAR_OPACCITY);
	}
	public void show() {
		this.setOpaccity(1f);
	}
	private void initializeKeyTrackers() {
		switch(camp) {
		case GameObjectManager.DEFAULT_SNAKE_CAMP:
		this.keyTrakcer_UP=new KeyTracker(this,KeyEvent.VK_W) {
			public void tick() {
				((Snake)this.getEventUnit()).setDirection(Snake.UP);
			}
		};
		this.keyTrakcer_DOWN=new KeyTracker(this,KeyEvent.VK_S) {
			public void tick() {
				((Snake)this.getEventUnit()).setDirection(Snake.DOWN);
			}
		};
		this.keyTrakcer_LEFT=new KeyTracker(this,KeyEvent.VK_A) {
			public void tick() {
				((Snake)this.getEventUnit()).setDirection(Snake.LEFT);
			}
		};
		this.keyTrakcer_RIGHT=new KeyTracker(this,KeyEvent.VK_D) {
			public void tick() {
				((Snake)this.getEventUnit()).setDirection(Snake.RIGHT);
			}
		};break;
		case GameObjectManager.CAMP_RED:
			this.keyTrakcer_UP=new KeyTracker(this,KeyEvent.VK_UP) {
				public void tick() {
					((Snake)this.getEventUnit()).setDirection(Snake.UP);
				}
			};
			this.keyTrakcer_DOWN=new KeyTracker(this,KeyEvent.VK_DOWN) {
				public void tick() {
					((Snake)this.getEventUnit()).setDirection(Snake.DOWN);
				}
			};
			this.keyTrakcer_LEFT=new KeyTracker(this,KeyEvent.VK_LEFT) {
				public void tick() {
					((Snake)this.getEventUnit()).setDirection(Snake.LEFT);
				}
			};
			this.keyTrakcer_RIGHT=new KeyTracker(this,KeyEvent.VK_RIGHT) {
				public void tick() {
					((Snake)this.getEventUnit()).setDirection(Snake.RIGHT);
				}
			};break;
		case GameObjectManager.CAMP_BLUE:
			this.keyTrakcer_UP=new KeyTracker(this,KeyEvent.VK_W) {
				public void tick() {
					((Snake)this.getEventUnit()).setDirection(Snake.UP);
				}
			};
			this.keyTrakcer_DOWN=new KeyTracker(this,KeyEvent.VK_S) {
				public void tick() {
					((Snake)this.getEventUnit()).setDirection(Snake.DOWN);
				}
			};
			this.keyTrakcer_LEFT=new KeyTracker(this,KeyEvent.VK_A) {
				public void tick() {
					((Snake)this.getEventUnit()).setDirection(Snake.LEFT);
				}
			};
			this.keyTrakcer_RIGHT=new KeyTracker(this,KeyEvent.VK_D) {
				public void tick() {
					((Snake)this.getEventUnit()).setDirection(Snake.RIGHT);
				}
			};break;
		}
		this.keyTrakcer_UP.enable();
		this.keyTrakcer_DOWN.enable();
		this.keyTrakcer_LEFT.enable();
		this.keyTrakcer_RIGHT.enable();
	}
	private void initializeTickers() {
		this.ticker_Creep=new Ticker(this,DEFAULT_STEP_TIME,true) {
			@Override
			public void tick() {
				((Snake)this.getEventUnit()).creep();
			}
		};
		this.ticker_Creep.unenable();
		this.ticker_Frozen=new Ticker(this,Snake.FROZEN_INTERVAL,true) {
			@Override
			public void tick() {
				if(((Snake)this.getEventUnit()).frozenCount<((Snake)this.getEventUnit()).body.size()) {
					((Snake)this.getEventUnit()).frozenOneBlock(((Snake)this.getEventUnit()).body.get(((Snake)this.getEventUnit()).frozenCount));
					((Snake)this.getEventUnit()).frozenCount++;
				}else {
					((Snake)this.getEventUnit()).unfrozen();
					((Snake)this.getEventUnit()).frozenCount=0;
					this.unenable();
				}
					
			}
		};
		this.ticker_Frozen.unenable();
	}
	public void setCreeping(boolean b) {
		if(b)this.ticker_Creep.enable();else this.ticker_Creep.unenable();
		this.isCreeping=true;
	}
	public boolean getCreeping() {
		return this.isCreeping;
	}
	public void setDirection(int dir) {
		if(!this.getNextBlock(dir).sameLocation(this.body.get(1)))this.direction=dir;
	}
	public int getDirection() {
		return this.direction;
	}
	public void plusLength() {
		Block tail=body.get(body.size()-1);
		Block newBlock=new PictureBlock(tail,((PictureBlock)tail).getPictureID());
		newBlock.setLocation(tail.getX()-GameObjectManager.BLOCK_WIDTH, tail.getY());
		body.add(tail);
	}
	public int getLength() {
		return body.size();
	}
	public void drawObject(Graphics g) {
		Graphics2D g2d=(Graphics2D)g;
		AlphaComposite newComposite=AlphaComposite.getInstance(AlphaComposite.SRC_OVER,this.opaccity);
		g2d.setComposite(newComposite);
		for(Block block : body) {
			block.drawBlock(g);
		}
	}
	public Block getNextBlock() {
		return this.getNextBlock(this.direction);
	}
	public Block getNextBlock(int dir) {
		Block head=body.get(0);
		Block res=new Block(head);
		switch(dir) {
		
		case DOWN:res.setLocation(res.getX(), res.getY()+res.getHeight());break;
		case UP:res.setLocation(res.getX(), res.getY()-res.getHeight());break;
		case LEFT:res.setLocation(res.getX()-res.getWidth(), res.getY());break;
		case RIGHT:res.setLocation(res.getX()+res.getWidth(), res.getY());break;
		}
		return res;
	}
	public void creep() {
		PictureBlock nextBlock=new PictureBlock(this.getNextBlock(),((PictureBlock)this.body.get(0)).getPictureID());
		if(isOutOfRange(nextBlock))this.sendMessage(GameObjectManager.getInstance(), Events.SNAKE_OUT_OF_RANGE);
		if(isEatCandy(nextBlock)) {
			this.leftBlock=true;
			this.sendMessage(GameObjectManager.getInstance().candy,Events.CANDY_EATEN );
			this.sendMessage(GameObjectManager.getInstance(),Events.CANDY_EATEN );
		}
		if(this.opaccity==1f) {
			if(isBumpItself(nextBlock)) {this.sendMessage(GameObjectManager.getInstance(), Events.SNAKE_BUMP_ITSELF);
				this.hearts--;
				this.skills.releaseSkill(Skills.SKILL_DYING);
				if(this.hearts==0)this.sendMessage(GameObjectManager.getInstance(), Events.SNAKE_OUT_OF_RANGE);
			}
			if(isBumpOtherSnake()) {
			this.sendMessage(GameObjectManager.getInstance(), Events.SNAKE_BUMP_OTHER_SNAKE);
			this.hearts--;
			this.skills.releaseSkill(Skills.SKILL_DYING);
			if(this.hearts==0)this.sendMessage(GameObjectManager.getInstance(), Events.SNAKE_OUT_OF_RANGE);
		}}
		if(this.leftBlock) {leftBlock=false;}else this.body.remove(body.get(body.size()-1));
		this.body.add(0,nextBlock);
	}
	public void reverse() {
		int x1=body.get(body.size()-1).getX();
		int x2=body.get(body.size()-2).getX();
		int y1=body.get(body.size()-1).getY();
		int y2=body.get(body.size()-2).getY();
		if(x1==x2) {
			if(y1<y2) this.direction=Snake.UP;else
			if(y1>y2)this.direction=Snake.DOWN;
		}else
		if(x1<x2) {
			this.direction=Snake.LEFT;
		}else
		if(x1>x2) {
			this.direction=Snake.RIGHT;
		}
		Collections.reverse(this.body);
	}
	private boolean isOutOfRange(Block nextBlock) {
		if(!nextBlock.intersectWith(Main.theCanvas.getCanvasRange())) {System.out.println(Main.theCanvas.getCanvasRange());return true;}
		else return false;
	}
	private boolean isBumpItself(Block nextBlock) {
		for(Block block:this.body)
			if(block.sameLocation(nextBlock))return true;
		return false;
	}
	private boolean isBumpOtherSnake() {
		boolean ans=false;
		if(GameObjectManager.getInstance().getEnemy(this).opaccity!=1f)return false;
		for(Block block:GameObjectManager.getInstance().getEnemy(this).body) { 
			if(block.sameLocation(this.getNextBlock()))ans=true;}
		return ans;
	}
	private boolean isEatCandy(Block nextBlock) {
		return GameObjectManager.getInstance().candy.getBlock().sameLocation(nextBlock);
	}
}
