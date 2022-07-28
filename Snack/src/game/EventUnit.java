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
		System.out.println("����һ��Game Object   ��ǰcurrentMaxIndex= "+EventUnit.currentMaxIndex);
	}
	protected void receiveMessage(EventUnit sender,int event) {
		
	}
	protected void sendMessage(EventUnit eventUnit,int event) {
		eventUnit.receiveMessage(this,event);
	}
	public void drawObject(Graphics g) {
		
	}
}
