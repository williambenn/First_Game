
import java.awt.Color;
import java.awt.Graphics2D;

public class Obstacles {
	private int pWidth, pHeight;
	private static final int WALL_LENGTH = 70;

	public Obstacles(int pW, int pH){
		pWidth = pW; pHeight = pH;
	}
	
	public void drawWalls(Graphics2D g){
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, pWidth, WALL_LENGTH);
		g.fillRect(0, 0, WALL_LENGTH, pHeight);
		g.fillRect(0, pHeight- WALL_LENGTH, pWidth, WALL_LENGTH);
		g.fillRect(pWidth - WALL_LENGTH, 0, WALL_LENGTH, pHeight);
	}
}
