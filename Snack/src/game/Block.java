package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected float opaccity=1;
	protected Color color;
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Block() {}
	public Block(int x,int y,int width,int height,Color color) {
		setSize(width,height);
		setColor(color);
		setLocation(x,y);
	}
	public Block(int x,int y,int width,int height) {
		setSize(width,height);
		setLocation(x,y);
	}
	public void setSize(int w,int h) {
		this.width=w;
		this.height=h;
	}
	public void setColor(Color c) {
		this.color=c;
	}
	public void setLocation(int x,int y) {
		this.x=x;
		this.y=y;
	}
	public void setOpaccity(float op) {
		this.opaccity=op;
	}
	public int getWidth() {
		return this.width;
	}
	public float getOpaccity() {
		return this.opaccity;
	}
	public int getHeight() {
		return this.height;
	}
	public Color getColor() {
		return this.color;
	}
	public void drawBlock(Graphics g) {
		g.setColor(color);
		g.fillRect(x+2, y+2, width-4, height-4); 
	}
	public Block(Block block) {
		this.x=block.x;
		this.y=block.y;
		this.width=block.width;
		this.height=block.height;
		this.color=block.color;
	}
	public boolean intersectWith(Block block) {
		return (new Rectangle(this.x,this.y,this.width,this.height)).intersects(new Rectangle(block.x,block.y,block.width,block.height));
	}
	@Override
	public String toString() {
		return String.format("Location(%d,%d) Color:%s ", this.x,this.y,this.color.toString());
		
	}
	public boolean sameLocation(Block block) {
		return this.x==block.x&&this.y==block.y;
	}
}
