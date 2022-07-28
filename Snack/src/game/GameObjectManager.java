package game;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameObjectManager extends EventUnit {
	private static GameObjectManager instance = new GameObjectManager();
	public final static int BLOCK_WIDTH = 48;
	public final static int BLOCK_HEIGHT = 48;
	public final static int DEFAULT_LEN = 2;
	public final static String BACKGROUND_IMAGE_PATH = "ice.png";// !!!!!!!
	public Snake playerA;
	public Snake playerB;
	public Candy candy;
	public Background bg;
	private CopyOnWriteArrayList<EventUnit> gameObjectArray;
	//Images of buttons
	public Image start_press;
	public Image back_press;
	public Image start;
	public Image back;
	public Image switch_press;
	public Image switch_;
	
	private Image ice;
	private Image sand;
	private Image book;
	private Image particles;
	private Image bookshelf;
	private Image wool_colored_black;
	private Image wool_colored_blue;
	private Image wool_colored_brown;
	private Image wool_colored_cyan;
	private Image wool_colored_gray;
	private Image wool_colored_green;
	private Image wool_colored_light_blue;
	private Image wool_colored_lime;
	private Image wool_colored_magenta;
	private Image wool_colored_orange;
	private Image wool_colored_pink;
	private Image wool_colored_purple;
	private Image wool_colored_red;
	private Image wool_colored_silver;
	private Image wool_colored_white;
	private Image wool_colored_yellow;
	private Image sand_stone;
	private Image glass;
	private Image log_oak_top;
	private Image log_oak;
	private Image stone_brick;
	private Image planks;
	private Image gunpowder;
	private Image hay_block;
	private Image enchanting_table;
	private Image crafting_table;
	private Image quart_block;
	private Image endframe;
	private Image diamond_block;
	private Image snow;
	
	public static final int NULL_PICTURE_ID = -100;
	public static final int ICE = 1;
	public static final int SAND = 2;
	public static final int BOOK = 3;
	public static final int HEART = 4;
	public static final int FIRE = 5;
	public static final int FIRE_BALL = 6;
	public static final int WEB = 7;
	public static final int REBORN = 8;
	public static final int BOOKSHELF = 9;
	public static final int WOOL_COLORED_BLACK = 10;
	public static final int WOOL_COLORED_BLUE = 11;
	public static final int WOOL_COLORED_BROWN = 12;
	public static final int WOOL_COLORED_CYAN = 13;
	public static final int WOOL_COLORED_GRAY = 14;
	public static final int WOOL_COLORED_GREEN = 15;
	public static final int WOOL_COLORED_LIGHT_BLUE = 16;
	public static final int WOOL_COLORED_LIME = 17;
	public static final int WOOL_COLORED_MAGENTA = 18;
	public static final int WOOL_COLORED_ORANGE = 19;
	public static final int WOOL_COLORED_PINK = 20;
	public static final int WOOL_COLORED_PURPLE = 21;
	public static final int WOOL_COLORED_RED = 22;
	public static final int WOOL_COLORED_SILVER = 23;
	public static final int WOOL_COLORED_WHITE = 24;
	public static final int WOOL_COLORED_YELLOW = 25;
	public static final int SAND_STONE = 26;
	public static final int GLASS=27;
	public static final int LOG_OAK_TOP=28;
	public static final int LOG_OAK=29;
	public static final int STONR_BRICK=30;
	public static final int PLANKS=31;
	public static final int GUNPOWDER=32;
	public static final int HAY_BLOCK=33;
	public static final int ENCHANTING_TABLE=34;
	public static final int CRAFTING_TABLE=35;
	public static final int QUARTZ_BLOCK=36;
	public static final int ENDFRAME=37;
	public static final int DIAMOND_BLOCK=38;
	public static final int SNOW=39;

	public static final Rectangle RECT_BOOK = new Rectangle(0, 0, 180, 183);
	public static final Rectangle RECT_HEART = new Rectangle(0, 41, 8, 48);
	public static final Rectangle RECT_FIRE = new Rectangle(0, 24, 8, 31);
	public static final Rectangle RECT_FIREBALL = new Rectangle(8, 41, 15, 47);
	public static final Rectangle RECT_WEB = new Rectangle(8, 31, 15, 39);
	public static final Rectangle RECT_REBORN = new Rectangle(16, 40, 23, 47);

	public static final int CAMP_RED = 2;
	public static final int CAMP_BLUE = 1;
	public static final int DEFAULT_SNAKE_CAMP = 0;

	public void addGameObject(EventUnit eu) {
		this.gameObjectArray.add(eu);
		System.out.println("添加GameObject");
	}

	public List<EventUnit> getGameObjects() {
		return this.gameObjectArray;
	}

	public void clearGameObject() {
		this.gameObjectArray.clear();
		System.out.println("删除所有GameObject");
	}

	public EventUnit getGameObjectByIndex(int index) {
		for (EventUnit eu : this.gameObjectArray) {
			if (eu.getEventUnitIndex() == index)
				return eu;
		}
		System.out.println("无法通过序号找到该GameObject");
		return null;
	}

	public void removeGameObject(EventUnit e) {
		System.out.println("试图删除Index=" + e.getEventUnitIndex());
		for (int i = 0; i < this.gameObjectArray.size(); i++) {
			if (gameObjectArray.get(i).getEventUnitIndex() == e.getEventUnitIndex()) {
				this.gameObjectArray.remove(i);
				System.out.println("删除成功");
				return;
			}
		}
		System.out.println("删除Index=" + e.getEventUnitIndex() + "失败:找不到该GameObject");
	}

	private GameObjectManager() {
		System.out.println("GameObjects init");
		initialize();
	}

	@Override
	protected void receiveMessage(EventUnit sender, int eventType) {
		switch (eventType) {
		case Events.SNAKE_OUT_OF_RANGE:
			GameStatus.getInstance().setWinner(this.getEnemy((Snake) sender));
			GameStatus.getInstance().setStatus(GameStatus.End);
			;
			break;
		case Events.SNAKE_BUMP_ITSELF:
			
			break;
		case Events.SNAKE_BUMP_OTHER_SNAKE:

			break;
		case Events.CANDY_EATEN:
			((Snake) sender).score.plusScore(5);
			this.addGameObject(candy = Candy.GenerateRandomCandy(Candy.CANDYTYPE_BASE));
			;
			break;
		case Events.OBJECT_REMOVE_SENDER:
			this.removeGameObject(sender);
			break;
		case Events.ICEBULLET_BUMP_OTHER_SNAKE:
			this.getEnemy(((StopBullet) sender).target).frozen();
			break;

		default:
			System.out.println("GameObjectManager无法处理该事件");
		}
	}

	public Snake getEnemy(Snake snake) {
		if (snake.getEventUnitIndex() == this.playerA.getEventUnitIndex())
			return playerB;
		return playerA;
	}

	private void initialize() {
		this.readImages();

		this.gameObjectArray = new CopyOnWriteArrayList<EventUnit>();

		/*this.addGameObject(
				bg = new Background(GameObjectManager.BOOKSHELF, 0, 0, Main.FRAME_WIDTH, Main.FRAME_HEIGHT, 64, 64));*/
		this.addGameObject(
				bg = new Background(Background.MAP_AUTHOR,13,24,64,64,0.8f));
		this.addGameObject(playerA = new Snake(DEFAULT_LEN, GameObjectManager.CAMP_BLUE,
				new Block(3 * BLOCK_WIDTH, 3 * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT)));
		this.addGameObject(playerB = new Snake(DEFAULT_LEN, GameObjectManager.CAMP_RED,
				new Block(24 * BLOCK_WIDTH, 14 * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT)));
		this.addGameObject(candy = Candy.GenerateRandomCandy(Candy.CANDYTYPE_BASE));

		this.addGameObject(GameStatus.getInstance());
		
		this.playerA.setPicture(GameObjectManager.WOOL_COLORED_BLUE);
		this.playerB.setPicture(GameObjectManager.WOOL_COLORED_RED);
		this.playerB.setDirection(Snake.LEFT);
	}

	private void readImages() {
		this.ice = this.getImage("ice.png");
		this.particles = this.getImage("particles.png");
		this.sand = this.getImage("sand.png");
		this.book = this.getImage("book.png");
		this.bookshelf = this.getImage("bookshelf.png");
		this.wool_colored_black=this.getImage("wool_colored_black.png");
		this.wool_colored_blue=this.getImage("wool_colored_blue.png");
		this.wool_colored_brown=this.getImage("wool_colored_brown.png");
		this.wool_colored_cyan=this.getImage("wool_colored_cyan.png");
		this.wool_colored_gray=this.getImage("wool_colored_gray.png");
		this.wool_colored_green=this.getImage("wool_colored_green.png");
		this.wool_colored_light_blue=this.getImage("wool_colored_light_blue.png");
		this.wool_colored_lime=this.getImage("wool_colored_lime.png");
		this.wool_colored_magenta=this.getImage("wool_colored_magenta.png");
		this.wool_colored_orange=this.getImage("wool_colored_orange.png");
		this.wool_colored_pink=this.getImage("wool_colored_pink.png");
		this.wool_colored_purple=this.getImage("wool_colored_purple.png");
		this.wool_colored_red=this.getImage("wool_colored_red.png");
		this.wool_colored_silver=this.getImage("wool_colored_silver.png");
		this.wool_colored_white=this.getImage("wool_colored_white.png");
		this.wool_colored_yellow=this.getImage("wool_colored_yellow.png");
		this.sand_stone=this.getImage("sandstone_top.png");
		this.glass=this.getImage("glass.png");
		this.log_oak_top=this.getImage("log_oak_top.png");
		this.log_oak=this.getImage("log_oak.png");
		this.stone_brick=this.getImage("stonebrick.png");
		this.planks=this.getImage("planks_oak.png");
		this.gunpowder=this.getImage("gunpowder.png");
		this.hay_block=this.getImage("hay_block_side.png");
		this.enchanting_table=this.getImage("enchanting_table_top.png");
		this.crafting_table=this.getImage("crafting_table_side.png");
		this.quart_block=this.getImage("quartz_block_chiseled.png");
		this.endframe=this.getImage("endframe_top.png");
		this.diamond_block=this.getImage("diamond_block.png");
		this.snow=this.getImage("snow.png");
		this.start=this.getImage("start.png");
		this.start_press=this.getImage("start_press.png");
		this.back=this.getImage("back.png");
		this.back_press=this.getImage("back_press.png");
		this.switch_=this.getImage("switch.png");
		this.switch_press=this.getImage("switch_press.png");
	}

	public void drawImage(int pictureID, int x, int y, int width, int height, float opaccity,Graphics g) {
		Graphics2D g2d=(Graphics2D)g;
		if(opaccity!=1f) {
		AlphaComposite newComposite=AlphaComposite.getInstance(AlphaComposite.SRC_OVER,opaccity);
		g2d.setComposite(newComposite);}
		switch (pictureID) {
		case GameObjectManager.BOOK:
			g.drawImage(this.book, x, y,  width+x, height+y, RECT_BOOK.x, RECT_BOOK.y, RECT_BOOK.width, RECT_BOOK.height,
					null);
			break;
		case GameObjectManager.FIRE:
			g.drawImage(this.particles, x, y,  width+x, height+y, RECT_FIRE.x, RECT_FIRE.y, RECT_FIRE.width,
					RECT_FIRE.height, null);
			break;
		case GameObjectManager.FIRE_BALL:
			g.drawImage(this.particles, x, y,  width+x, height+y, RECT_FIREBALL.x, RECT_FIREBALL.y, RECT_FIREBALL.width,
					RECT_FIREBALL.height, null);
			break;
		case GameObjectManager.HEART:
			g.drawImage(this.particles, x, y,  width+x, height+y, RECT_HEART.x, RECT_HEART.y, RECT_HEART.width,
					RECT_HEART.height, null);
			break;
		case GameObjectManager.ICE:
			g.drawImage(this.ice, x, y, width, height, null);
			break;
		case GameObjectManager.REBORN:
			g.drawImage(this.particles, x, y, width+x, height+y, RECT_REBORN.x, RECT_REBORN.y, RECT_REBORN.width,
					RECT_REBORN.height, null);
			break;
		case GameObjectManager.WEB:
			g.drawImage(this.particles, x, y, width+x, height+y, RECT_WEB.x, RECT_WEB.y, RECT_WEB.width, 
					RECT_WEB.height,null);
			break;
		case GameObjectManager.SAND:
			g.drawImage(this.sand, x, y, width, height, null);
			break;
		case GameObjectManager.BOOKSHELF:
			g.drawImage(this.bookshelf, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_BLACK:
			g.drawImage(this.wool_colored_black, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_BLUE:
			g.drawImage(this.wool_colored_blue, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_BROWN:
			g.drawImage(this.wool_colored_brown, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_CYAN:
			g.drawImage(this.wool_colored_cyan, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_GRAY:
			g.drawImage(this.wool_colored_gray, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_GREEN:
			g.drawImage(this.wool_colored_green, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_LIGHT_BLUE:
			g.drawImage(this.wool_colored_light_blue, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_LIME:
			g.drawImage(this.wool_colored_lime, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_MAGENTA:
			g.drawImage(this.wool_colored_magenta, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_ORANGE:
			g.drawImage(this.wool_colored_orange, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_PINK:
			g.drawImage(this.wool_colored_pink, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_PURPLE:
			g.drawImage(this.wool_colored_purple, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_RED:
			g.drawImage(this.wool_colored_red, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_SILVER:
			g.drawImage(this.wool_colored_silver, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_WHITE:
			g.drawImage(this.wool_colored_white, x, y, width, height, null);
			break;
		case GameObjectManager.WOOL_COLORED_YELLOW:
			g.drawImage(this.wool_colored_yellow, x, y, width, height, null);
			break;
		case GameObjectManager.SAND_STONE:
			g.drawImage(this.sand_stone, x, y, width, height, null);
			break;
		case GameObjectManager.GLASS:
			g.drawImage(this.glass, x, y, width, height, null);
			break;
		case GameObjectManager.LOG_OAK:
			g.drawImage(this.log_oak, x, y, width, height, null);
			break;
		case GameObjectManager.LOG_OAK_TOP:
			g.drawImage(this.log_oak_top, x, y, width, height, null);
			break;
		case GameObjectManager.STONR_BRICK:
			g.drawImage(this.stone_brick, x, y, width, height, null);
			break;
		case GameObjectManager.PLANKS:
			g.drawImage(this.planks, x, y, width, height, null);
			break;
		case GameObjectManager.GUNPOWDER:
			g.drawImage(this.gunpowder, x, y, width, height, null);
			break;
		case GameObjectManager.HAY_BLOCK:
			g.drawImage(this.hay_block, x, y, width, height, null);
			break;
		case GameObjectManager.ENCHANTING_TABLE:
			g.drawImage(this.enchanting_table, x, y, width, height, null);
			break;
		case GameObjectManager.CRAFTING_TABLE:
			g.drawImage(this.crafting_table, x, y, width, height, null);
			break;
		case GameObjectManager.QUARTZ_BLOCK:
			g.drawImage(this.quart_block, x, y, width, height, null);
			break;
		case GameObjectManager.DIAMOND_BLOCK:
			g.drawImage(this.diamond_block, x, y, width, height, null);
			break;
		case GameObjectManager.ENDFRAME:
			g.drawImage(this.endframe, x, y, width, height, null);
			break;
		case GameObjectManager.SNOW:
			g.drawImage(this.snow, x, y, width, height, null);
			break;
		
			
		}
		if(opaccity!=1f) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));}
	}

	private Image getImage(String s) {
		URL url = this.getClass().getResource(s);
		Image I = Toolkit.getDefaultToolkit().getImage(url);
		return I;
	}

	public static GameObjectManager getInstance() {
		return instance;
	}

	public static void reset() {
		instance.initialize();
		System.out.println("reset");
	}

}
