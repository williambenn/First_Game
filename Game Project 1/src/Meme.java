import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Meme{
	private static final int DOTSIZE = 50;
	private static final int GUNSIZE = 40;
	private static final int WALL_LENGTH = 70;
	private int pWidth, pHeight,permWidth, permHeight;
	private double angle;
	private static final int BULLET_CAP = 100;
	private ArrayList<Projectiles> projectiles;

	public Meme(int pW, int pH){
		pWidth = pW/2; pHeight = pH/2;
		permWidth = pW; permHeight = pH;
		projectiles = new ArrayList<Projectiles>();
	}

	public void moveLeft(){
		if(pWidth != WALL_LENGTH)
			pWidth -= 50;
	}

	public void moveRight(){
		if(pWidth + DOTSIZE*2 <= permWidth - WALL_LENGTH)
			pWidth += 50;
	}

	public void moveUp(){
		if(pHeight - DOTSIZE >= WALL_LENGTH)
			pHeight -= 50;
	}

	public void moveDown(){
		if(pHeight + DOTSIZE*2 <= permHeight - WALL_LENGTH)
			pHeight += 50;
	}

	public void angleCalc(int x, int y){
		angle = 89.6 + Math.atan2(pHeight - y, pWidth - x);
	}

	public int getCoordX(){
		return pWidth;
	}

	public int getCoordY(){
		return pHeight;
	}

	public void shoot(int x, int y){
		if(projectiles.size() <= BULLET_CAP){
			projectiles.add(new Projectiles(x, y, pWidth, pHeight));
		}
		else{
			projectiles.clear();
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(pWidth, pHeight, DOTSIZE, DOTSIZE);
	}

	public ArrayList<Projectiles> getArray(){
		return projectiles;
	}

	public void draw(Graphics2D g)
	{
		for(int i = 0; i < projectiles.size(); i++){
			if(projectiles.get(i) != null)
				projectiles.get(i).draw(g);
		}
		
		g.setColor(Color.black);
		g.fillOval(pWidth-5, pHeight-5, DOTSIZE+10, DOTSIZE+10);
		g.setColor(Color.red);
		g.fillOval(pWidth, pHeight, DOTSIZE, DOTSIZE);

		g.rotate(angle, pWidth+25, pHeight+25);
		g.setColor(Color.black);
		g.fillRect(pWidth+12, pHeight+27, GUNSIZE/2+6, GUNSIZE);
	}
}

