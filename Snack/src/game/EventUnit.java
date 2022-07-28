package game;

import java.awt.Graphics;

public class EventUnit {
	private int gameObjectIndex;
	private static int currentMaxIndex;
	public int getEventUnitIndex() {
		return this.gameObjectIndex;
	}
	public EventUnit() {
		this.gameObjectIndex=++currentMaxIndex;
		System.out.println("创建一个Game Object   当前currentMaxIndex= "+EventUnit.currentMaxIndex);
	}
	protected void receiveMessage(EventUnit sender,int event) {
		
	}
	protected void sendMessage(EventUnit eventUnit,int event) {
		eventUnit.receiveMessage(this,event);
	}
	public void drawObject(Graphics g) {
		
	}
}
