

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Projectiles {
	private double permWidth, permHeight;
	private final int PROJ_LENGTH = 20;
	private double xVel,yVel;

	public Projectiles(int wantX, int wantY, int originX, int originY){
		permWidth = originX;
		permHeight = originY;
		double xCoord = -(originX - wantX);
		double yCoord = -(originY - wantY);
		
		double hyp = Math.sqrt(xCoord*xCoord + yCoord*yCoord);
		hyp = Math.hypot(xCoord, yCoord);
		xVel = xCoord/hyp;
		yVel = yCoord/hyp;
	}
	
	private void shootProj(){
		permWidth+= xVel*30;
		permHeight+= yVel*30;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) permWidth, (int) permHeight, PROJ_LENGTH+50, PROJ_LENGTH+50);
	}
	
	synchronized public void draw(Graphics2D g){
		shootProj();
		g.setColor(Color.black);
		g.fillOval((int)permWidth+10,(int)permHeight+10, PROJ_LENGTH, PROJ_LENGTH);
	}
}