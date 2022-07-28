package game;

import java.awt.Color;
import java.awt.Graphics;

public class SkillFlag extends EventUnit{
	public static final int ORANGE_SKILL_LOCATION_X=200;
	public static final int ORANGE_SKILL_LOCATION_Y=700;
	public static final int BLUE_SKILL_LOCATION_X=1100;
	public static final int BLUE_SKILL_LOCATION_Y=700;
	private int x;
	private int y;
	private Skills skills;
	public SkillFlag(Skills skills) {
		this.skills=skills;
		switch(skills.target.getCamp()) {
		case GameObjectManager.CAMP_RED:this.x=BLUE_SKILL_LOCATION_X;this.y=SkillFlag.BLUE_SKILL_LOCATION_Y;break;
		case GameObjectManager.CAMP_BLUE:this.x=ORANGE_SKILL_LOCATION_X;this.y=SkillFlag.ORANGE_SKILL_LOCATION_Y;break;
		}
	}
	@Override
	public void drawObject(Graphics g) {
		g.setColor(Color.ORANGE);
		g.drawString("技能", x-100, y);
		g.drawString("生命值", x-120, y-60);
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, 40,40);
		g.fillRect(x+40+20, y, 40,40);
		g.fillRect(x+2*(40+20), y, 40,40);
		g.fillRect(x+3*(40+20), y, 40,40);
		GameObjectManager.getInstance().drawImage(GameObjectManager.GUNPOWDER, x+2, y+2, 40-4, 40-4, 1f, g);
		GameObjectManager.getInstance().drawImage(GameObjectManager.ENDFRAME, x+1*(40+20)+2, y+2, 40-4, 40-4, 1f, g);
		GameObjectManager.getInstance().drawImage(GameObjectManager.WEB, x+2+2*(40+20), y+2, 40-4, 40-4, 1f, g);
		GameObjectManager.getInstance().drawImage(GameObjectManager.REBORN, x+2+3*(40+20), y+2, 40-4, 40-4, 1f, g);
		
		for(int i=0;i<this.skills.target.getHearts();i++)
				GameObjectManager.getInstance().drawImage(GameObjectManager.HEART, x+i*(40+20), y-60, 40-4, 40-4, 1f, g);

		g.setColor(Color.gray);
		g.fillRect(x, y, 40,(int) (40*skills.ticker_Assault_Cooling.getPercentageComplete()));
		g.fillRect(x+40+20, y,40, (int) (40*skills.ticker_Dieappear_Cooling.getPercentageComplete()));
		g.fillRect(x+2*(40+20), y,40, (int) (40*skills.ticker_Ice_Cooling.getPercentageComplete()));
		g.fillRect(x+3*(40+20), y, 40,(int) (40*skills.ticker_Reserve_Cooling.getPercentageComplete()));
	}
}
