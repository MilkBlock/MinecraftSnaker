package game;

import java.util.concurrent.CopyOnWriteArrayList;

public class CalThread implements Runnable{
	public static final int DETAL_SLEEP=20;
	private volatile boolean isRunning=false;
	private long lastTime;
	private long currentTime;
	private long delta;
	private CopyOnWriteArrayList<Ticker> tickers=new CopyOnWriteArrayList<Ticker>();
	public void removeTicker(Ticker t) {
		for(int i=0;i<tickers.size();i++) {
			if(tickers.get(i).getEventUnitIndex()==t.getEventUnitIndex()) {tickers.remove(i);
			return;}
		}	}
	public void clear() {
		this.tickers.clear();
	}
	public void addTicker(Ticker t) {
		this.tickers.add(t);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			if(isRunning) {
				currentTime=System.currentTimeMillis();
				delta=currentTime-lastTime;
			//��������GameObject
				for(Ticker ticker : this.tickers) {
					ticker.count(delta);
				}
				lastTime=currentTime;
			}
			
			try {
				Thread.sleep(DETAL_SLEEP);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	public void setRunning(boolean bool) {
		isRunning=bool;
		System.out.println("CalThread isRunning ="+this.isRunning);
	}
}
