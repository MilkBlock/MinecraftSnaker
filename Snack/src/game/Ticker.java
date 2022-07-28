package game;

public class Ticker extends EventUnit{
	private boolean isRepeat;
	public int repeatTimes;
	public int currentLastTimes;
	private EventUnit target;
	private long count;
	private long interval;
	private boolean enable;
	public Ticker(EventUnit eu,long Interval,boolean isRepeat) {
		this.repeatTimes=-1;
		this.isRepeat=isRepeat;
		this.target=eu;
		this.interval=Interval;
		this.count=Interval;
	}
	public Ticker(EventUnit eu,long Interval,int repeatTimes) {
		this.isRepeat=true;
		this.repeatTimes=repeatTimes;
		this.currentLastTimes=this.repeatTimes;
		this.target=eu;
		this.interval=Interval;
		this.count=Interval;
	}
	public void enable() {
		this.currentLastTimes=this.repeatTimes;
		this.count=this.interval;
		this.enable=true;
		Main.calThread.addTicker(this);
	}
	public void unenable() {
		this.enable=false;
		Main.calThread.removeTicker(this);
	}
	public EventUnit getEventUnit() {
		return this.target;
	}
	public void setInterval(long i) {
		this.interval=i;
	}
	public void setRepeatable(boolean b) {
		this.isRepeat=b;
	}
	public void count(long delta) {
		if(this.enable) {
			count-=delta;
			if(count<0) {
				tick();
				if(isRepeat&&this.currentLastTimes!=0) {
					this.count=interval;
					this.currentLastTimes--;
				}else Main.calThread.removeTicker(this);
		}}
	}
	public void tick() {
	}
	public float getPercentageComplete() {
		
		if(this.enable==false)return 0;
		
		return (float)(this.count)/(float)this.interval;
	}
}
