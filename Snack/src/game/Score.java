package game;

public class Score extends EventUnit{
	private int score;
	public static int DEFAULT_SCORE;
	public Snake targetSnake;
	public Score(Snake snake) {
		score=0;
	}
	public void plusScore(int i) {
		score+=i;
	}
	public int getScore() {
		return score; 
	}
	protected void receiveMessage(EventUnit sender,int mes) {
		switch(mes) {
		case Events.SCORE:
			this.plusScore(Score.DEFAULT_SCORE);
			System.out.println("Snake Score");
		}
	}
	
}
