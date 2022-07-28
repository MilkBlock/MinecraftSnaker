package game;
public class FrameRate {
   private String frameRate;
   private long lastTime;
   private long delta;
   private int frameCount;
   public void initialize() {
      lastTime = System.currentTimeMillis();
      frameRate = "FPS 0";
   }
   public FrameRate() {
	   this.initialize();
   }
   public void calculate() {
      long current = System.currentTimeMillis();
      delta += current - lastTime;
      lastTime = current;
      frameCount++;
      if( delta > 1000 ) {
    	   System.out.println(frameCount);
    	   delta -= 1000;
         frameRate = String.format( "FPS %d", frameCount );
         frameCount = 0; 
      }
   }
   public String getFrameRate() {
	   return frameRate;
   }
}