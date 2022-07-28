package game;

import java.awt.Graphics;

public class PictureBlock extends Block{
	private int pictureID;
	private boolean isCompleted=false;
	public PictureBlock(Block b,int pictureID) {
		this.setColor(b.color);
		this.setPictureID(pictureID);
		this.setLocation(b.getX(), b.getY());
		this.setSize(b.getWidth(), b.getWidth());
	}
	public PictureBlock(int x,int y,int width,int height,int pictureID) {
		this.setPictureID(pictureID);
		this.setLocation(x, y);
		this.setSize(width, height);
	}
	public static PictureBlock createNullPictureBlock(int x,int y,int width,int height) {
		return new PictureBlock(x,y,width,height,GameObjectManager.NULL_PICTURE_ID);
	}
	public void setPictureID(int pictureID) {
		this.pictureID=pictureID;
	}

	public int getPictureID() {
		return this.pictureID;
	}
	public void setIsComplete(boolean b) {
		this.isCompleted=b;
	}
	
	@Override 
	public void drawBlock(Graphics g) {
		if(this.pictureID==GameObjectManager.NULL_PICTURE_ID) {super.drawBlock(g);}
		else if(this.isCompleted) GameObjectManager.getInstance().drawImage(this.pictureID, this.x, this.y, this.width,this.height,this.opaccity, g);
		else GameObjectManager.getInstance().drawImage(this.pictureID, x+2, y+2, width-4, height-4,this.opaccity, g);
	}
}
