package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CopyOnWriteArrayList;

public class TheKeyListener implements KeyListener {
	private static boolean[] Keys;
	public void clear() {
		this.keyTrackers.clear();
	}
	private CopyOnWriteArrayList<KeyTracker> keyTrackers;
	public TheKeyListener() {
		Keys = new boolean[256];
		keyTrackers=new CopyOnWriteArrayList<KeyTracker>();
		for (int i = 0; i < 256; i++) {
			Keys[i] = false;
		}
	}
	public void addKeyTracker(KeyTracker kt) {
		this.keyTrackers.add(kt);
	}
	public void removeKeyTracker(KeyTracker KTtoRemove) {
		for(int i=0;i<this.keyTrackers.size();i++) {
			if(keyTrackers.get(i).getEventUnitIndex()==KTtoRemove.getEventUnitIndex()) {
				this.keyTrackers.remove(i);
				return;
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		Keys[e.getKeyCode()] = true;
		
		
		 //System.out.println("Press"+e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keys[e.getKeyCode()] = false;
		for(KeyTracker kt :this.keyTrackers) {
			kt.check(e.getKeyCode());
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// no needed
	}
	public static boolean getKeyDown(int i) {

		if (Keys[i] == true) {
			return true;
		} else
			return false;

	}


}
