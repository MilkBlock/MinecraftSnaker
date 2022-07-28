package game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Selector extends EventUnit{
	public static final int MAP_SELECTOR=0;
	public static final int PLAYERA_SKILL_SELECTOR=1;
	public static final int PLAYERB_SKILL_SELECTOR=2;
	public int type;
	public int count=0;
	private boolean skillList[];
	private boolean enable=false;
	private ArrayList<KeyTracker> keyTrackers;
	public Selector(int type) {
		this.keyTrackers=new ArrayList<>();
		this.type=type;
		initKeyTrackers();
	}
	public void enable() {
		if(enable)return;
		System.out.println(this.getEventUnitIndex());
		GameObjectManager.getInstance().addGameObject(this);
		for(KeyTracker keyTracker:keyTrackers)
			keyTracker.enable();
	}
	public void unenable() {
		for(KeyTracker keyTracker:keyTrackers)
			keyTracker.unenable();
		GameObjectManager.getInstance().removeGameObject(this);
	}
	private void initKeyTrackers() {
		switch(type) {
		case Selector.MAP_SELECTOR:
			this.keyTrackers.add(new KeyTracker(this,KeyEvent.VK_M) {
				public void tick() {
					System.out.println("tick");
					if(GameStatus.getInstance().mapSelector.count<GameStatus.maps.length-1)
					GameStatus.getInstance().mapSelector.count++;
					else GameStatus.getInstance().mapSelector.count=0;
					GameObjectManager.getInstance().bg.setMap(Background.MAPS[GameStatus.getInstance().mapSelector.count]);
				}
			});
			break;
		case Selector.PLAYERA_SKILL_SELECTOR:break;
		case Selector.PLAYERB_SKILL_SELECTOR:break;
		}
	}
	public boolean[] generateSkillList() {
		return this.skillList;
	}
	@Override
	public void drawObject(Graphics g) {
	}
	
}
