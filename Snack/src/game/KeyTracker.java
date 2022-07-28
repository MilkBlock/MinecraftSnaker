package game;

public class KeyTracker extends EventUnit{
	private int keyToTrack;
	private boolean enable;
	private EventUnit target;
	public void tick() {
		System.out.println("KeyTracker Tick方法未被重载");
	}
	public void check(int key) {
		if(this.enable&&key==this.keyToTrack) {
			this.tick();
		}
	}
	public int getKeyToTrack() {
		return this.keyToTrack;
	}
	public EventUnit getEventUnit() {
		return this.target;
	}
	public KeyTracker(EventUnit eu,int key) {
		this.keyToTrack=key;
		this.target=eu;
		this.enable=false;
	}
	public KeyTracker(KeyTracker temp,EventUnit target) {
		this.keyToTrack=temp.getKeyToTrack();
		this.target=target;
		this.enable=false;
	}
	public void enable() {
		this.enable=true;
		Main.theKeyListener.addKeyTracker(this);
	}
	public void unenable() {
		this.enable=false;
		Main.theKeyListener.removeKeyTracker(this);
	}
}
