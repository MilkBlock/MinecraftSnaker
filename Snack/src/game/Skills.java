package game;

import java.awt.event.KeyEvent;

public class Skills extends EventUnit{
	public static final int SKILL_ICE=0;
	public static final int SKILL_REVERSE=1;
	public static final int SKILL_DISAPPEAR=2;
	public static final int SKILL_ASSAULT=3;
	public static final int SKILL_DYING=4;
	
	public static final long ASSAULT_TIME=3000;
	public static final long DISAPPEAR_TIME=3000;
	public static final long DYING_TIME=300;
	
	public static final long ICE_TIME_COOLING=10000;
	public static final long REVERSE_TIME_COOLING=15000;
	public static final long ASSAULT_TIME_COOLING=10000;
	public static final long DISAPPEAR_TIME_COOLING=30000;
	
	public KeyTracker keyTracker_ASSAULT;  //E  - R  -  F  -  Q
	public KeyTracker keyTracker_REVERSE;  //1  - 2  -  3  -  4
	public KeyTracker keyTracker_ICE;
	public KeyTracker keyTracker_DIEAPPEAR;
	
	private Ticker ticker_ASSAULT;
	private Ticker ticker_DIEAPPEAR;
	private Ticker ticker_dying;
	
	public Ticker ticker_Assault_Cooling;
	public Ticker ticker_Dieappear_Cooling;
	public Ticker ticker_Ice_Cooling;
	public Ticker ticker_Reserve_Cooling;
	
	private boolean isAssaultUseable=true;
	private boolean isIceUseable=true;
	private boolean isDisappearUseable=true;
	private boolean isReserveUseable=true;
	
	private boolean enable=false;
	public Snake target;
	private SkillFlag skillFlag;
	public Skills(Snake snake) {
		System.out.println("Éú³ÉSkills");
		this.target=snake;
		this.initKeyTrackers();
		this.initTickers();

	}
	private void initTickers() {
		this.ticker_dying=new Ticker(this,Skills.DYING_TIME,10) {
			public void tick() {
				if(((Skills)this.getEventUnit()).target.getOpaccity()==0.95f)
					((Skills)this.getEventUnit()).target.setOpaccity(0.4f);
				else ((Skills)this.getEventUnit()).target.setOpaccity(0.95f);
				if(this.currentLastTimes==0) {((Skills)this.getEventUnit()).target.setOpaccity(1f);
					System.out.println("end Dying");
				}
			}
		};
		this.ticker_Assault_Cooling=new Ticker(this,Skills.ASSAULT_TIME_COOLING,false) {
			public void tick() {
				((Skills)this.getEventUnit()).isAssaultUseable=true;
			}
		};
		this.ticker_Ice_Cooling=new Ticker(this,Skills.ICE_TIME_COOLING,false) {
			public void tick() {
				((Skills)this.getEventUnit()).isIceUseable=true;
			}
		};
		this.ticker_Dieappear_Cooling=new Ticker(this,Skills.DISAPPEAR_TIME_COOLING,false) {
			public void tick() {
				((Skills)this.getEventUnit()).isDisappearUseable=true;
				this.unenable();
			}
		};
		this.ticker_Reserve_Cooling=new Ticker(this,Skills.REVERSE_TIME_COOLING,false) {
			public void tick() {
				((Skills)this.getEventUnit()).isReserveUseable=true;
				this.unenable();
			}
		};
		// 		))))    )))       )))
		this.ticker_DIEAPPEAR=new Ticker(target,Skills.DISAPPEAR_TIME,false) {
			public void tick() {
				((Snake)this.getEventUnit()).show();
				this.unenable();
			}
		};
		this.ticker_ASSAULT=new Ticker(target,Skills.ASSAULT_TIME,false) {
			public void tick() {
				((Snake)this.getEventUnit()).ticker_Creep.setInterval(Snake.DEFAULT_STEP_TIME);
				this.unenable();
			}
		};
	}
	private void initKeyTrackers() {
		switch(target.getCamp()) {
		case GameObjectManager.CAMP_BLUE:
			this.keyTracker_ASSAULT=new KeyTracker(target,KeyEvent.VK_E) {
				public void tick() {
					((Snake)this.getEventUnit()).getSkills().releaseSkill(Skills.SKILL_ASSAULT);
				}
			};
			this.keyTracker_REVERSE=new KeyTracker(target,KeyEvent.VK_R) {
				public void tick() {
					((Snake)this.getEventUnit()).getSkills().releaseSkill(Skills.SKILL_REVERSE);
				}
			};
			this.keyTracker_ICE=new KeyTracker(target,KeyEvent.VK_F) {
				public void tick() {
					((Snake)this.getEventUnit()).getSkills().releaseSkill(Skills.SKILL_ICE);
				}
			};
			this.keyTracker_DIEAPPEAR=new KeyTracker(target,KeyEvent.VK_Q) {
				public void tick() {
					((Snake)this.getEventUnit()).getSkills().releaseSkill(Skills.SKILL_DISAPPEAR);
				}
			};
			break;
		case GameObjectManager.CAMP_RED:
			this.keyTracker_ASSAULT=new KeyTracker(target,KeyEvent.VK_NUMPAD4) {
				public void tick() {
					((Snake)this.getEventUnit()).getSkills().releaseSkill(Skills.SKILL_ASSAULT);
				}
			};
			this.keyTracker_REVERSE=new KeyTracker(target,KeyEvent.VK_NUMPAD2) {
				public void tick() {
					((Snake)this.getEventUnit()).getSkills().releaseSkill(Skills.SKILL_REVERSE);
				}
			};
			this.keyTracker_ICE=new KeyTracker(target,KeyEvent.VK_NUMPAD3) {
				public void tick() {
					((Snake)this.getEventUnit()).getSkills().releaseSkill(Skills.SKILL_ICE);
				}
			};
			this.keyTracker_DIEAPPEAR=new KeyTracker(target,KeyEvent.VK_NUMPAD1) {
				public void tick() {
					((Snake)this.getEventUnit()).getSkills().releaseSkill(Skills.SKILL_DISAPPEAR);
				}
			};
			break;
		}
	}
	public void releaseSkill(int skillType) {
		switch(skillType) {
		case Skills.SKILL_ICE:
			if(this.isIceUseable) {
				GameObjectManager.getInstance().addGameObject(new StopBullet(this.target));
				this.isIceUseable=false;
				this.ticker_Ice_Cooling.enable();
			}break;
		case Skills.SKILL_REVERSE:
			if(this.isReserveUseable) {
				target.reverse();
				this.isReserveUseable=false;
				this.ticker_Reserve_Cooling.enable();
			}
			break;
		case Skills.SKILL_ASSAULT:
			if(this.isAssaultUseable) {
				target.ticker_Creep.setInterval(Snake.FAST_STEP_TIME);
				this.ticker_ASSAULT.enable();
				this.ticker_Assault_Cooling.enable();
				this.isAssaultUseable=false;
			}
			break;
		case Skills.SKILL_DISAPPEAR:
			if(this.isDisappearUseable) {
				target.disappear();
				this.ticker_DIEAPPEAR.enable();
				this.ticker_Dieappear_Cooling.enable();
				this.isDisappearUseable=false;
			}
			break;
		case Skills.SKILL_DYING:
			System.out.println("dying");
			this.target.setOpaccity(0.95f);
			this.ticker_dying.enable();
			break;
		}
	}
	@Override 
	public void receiveMessage(EventUnit sender,int mes) {
		switch(mes) {
		
		}
	}
	public void enable() {
		System.out.println("Skills Enable");
		this.skillFlag=new SkillFlag(this);
		GameObjectManager.getInstance().addGameObject(skillFlag);
		this.enable=true;
		this.keyTracker_ASSAULT.enable();
		this.keyTracker_DIEAPPEAR.enable();
		this.keyTracker_ICE.enable();
		this.keyTracker_REVERSE.enable();
	}
	public void unenable() {
		System.out.println("Skills UnEnable");
		this.enable=false;
		this.keyTracker_ASSAULT.unenable();
		this.keyTracker_DIEAPPEAR.unenable();
		this.keyTracker_ICE.unenable();
		this.keyTracker_REVERSE.unenable();
	}
	public boolean getEnable() {
		return this.enable;
	}
}
