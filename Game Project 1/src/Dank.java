import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Dank {
	private static final int ENEMY_LENGTH = 40;
	private int pWidth, pHeight;
	private double heroX, heroY, xVel, yVel;
	private Meme hero;
	private double speed;
	private double spawn, widthSpawn, heightSpawn;


	public Dank(int pW, int pH, Meme now){
		pWidth = pW; pHeight = pH;
		hero = now;
		speed = 4 + Math.random()*2;
		spawn = Math.random()*4;
		widthSpawn = Math.random()* pW;
		heightSpawn = Math.random()* pH;
		spawnPlace(pWidth, pHeight);
	}

	public int getSpeed(){
		return (int) speed;
	}

	private void spawnPlace(int width, int height){
		if(spawn < 1){
			pWidth = (int) (width- widthSpawn);
			pHeight = 0;
		}
		else if(spawn < 2){
			pWidth = 0;
			pHeight = (int) (height - heightSpawn);
		}
		else if(spawn < 3){
			pWidth = (int) (width- widthSpawn);
		}
		else{
			pHeight = (int) (height - heightSpawn);
		}
	}

	public void move(){

		heroX = hero.getCoordX() - pWidth;
		heroY = hero.getCoordY() - pHeight;

		double hyp = Math.sqrt((heroX*heroX) + (heroY*heroY));
		xVel = (heroX/hyp);
		yVel = (heroY/hyp);

		pWidth += xVel*speed;
		pHeight += yVel*speed;
	}

	public Rectangle getBounds() {
		return new Rectangle(pWidth, pHeight, ENEMY_LENGTH, ENEMY_LENGTH);
	}

	public void draw(Graphics2D g){
		g.setColor(Color.magenta);			
		g.fillRoundRect(pWidth, pHeight, ENEMY_LENGTH, ENEMY_LENGTH, 10, 10);
	}
}
