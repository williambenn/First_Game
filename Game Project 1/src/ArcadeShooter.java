import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class ArcadeShooter extends GameFrame{
	private static final long serialVersionUID = -2450477630768116721L;

	private static int DEFAULT_FPS = 100;

	private Meme hero; //the player
	private ArrayList<Dank> danks; //the enemies
	private Obstacles obs;
	private int waves;
	private int danksSpawned = 1;

	private int score;
	private Font font;
	private FontMetrics metrics;

	// used by quit 'button'
	private volatile boolean isOverQuitButton = false;
	private Rectangle quitArea;

	// used by the pause 'button'
	private volatile boolean isOverPauseButton = false;
	private Rectangle pauseArea;

	private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp

	public ArcadeShooter(long period){
		super(period);
	}

	protected void simpleInitialize(){
		hero = new Meme(pWidth,pHeight);
		danks = new ArrayList<Dank>();
		obs = new Obstacles(pWidth, pHeight);

		// set up message font
		font = new Font("SansSerif", Font.BOLD, 24);
		metrics = this.getFontMetrics(font);

		// specify screen areas for the buttons
		pauseArea = new Rectangle(pWidth - 100, pHeight - 45, 70, 15);
		quitArea = new Rectangle(pWidth - 100, pHeight - 20, 70, 15);
	}
	
	@Override
	protected void simpleRender(Graphics2D gScr) {
		for(int i = 0; i < danks.size(); i++){
			danks.get(i).draw(gScr);
		}

		obs.drawWalls(gScr);

		if(gameOver)
			gameOverMessage(gScr);

		gScr.setColor(Color.white);
		gScr.setFont(font);
		gScr.setColor(Color.white);
		gScr.setFont(font);

		// report frame count & average FPS and UPS at top left
		gScr.drawString("Average FPS/UPS: " + df.format(averageFPS) + ", " +
				df.format(averageUPS), 20, 25);  // was (10,55)

		// report time used and boxes used at bottom left
		gScr.drawString("Time Spent: " + timeSpentInGame + " secs", 10,
				pHeight - 15);

		gScr.drawString("Wave: " + waves , pWidth/3,
				pHeight - 15);
		
		gScr.drawString("Score: " + score , pWidth/2,
				pHeight - 15);
		
		// draw the pause and quit 'buttons'
		drawButtons(gScr);

		gScr.setColor(Color.black);
		// draw game elements: the obstacles and the worm
		hero.draw(gScr);

	}

	@Override
	protected void gameOverMessage(Graphics2D g) {
		String msg = "Game Over.";
		int x = (pWidth - metrics.stringWidth(msg)) / 2;
		int y = (pHeight - metrics.getHeight()) / 2;
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString(msg, x, y);
	}

	protected void newDanks(){

		for(int i = 0; i < danksSpawned; i++){
			danks.add(new Dank(pWidth, pHeight, hero));
		}
		danksSpawned = danksSpawned + 2;
		waves++;
	}

	@Override
	protected void simpleUpdate() {//add method to send projectiles from hero to outside to compare
		if(danks.isEmpty()){
			danks.clear();
			newDanks();
		}

		ArrayList<Projectiles> projs = hero.getArray();
		Iterator<Dank> enemIt = danks.iterator();

		while(enemIt.hasNext()){
			Dank e = enemIt.next();
			if(hero.getBounds().intersects(e.getBounds())){
				if(!isPaused)
					isPaused = !isPaused;
				gameOver = true;
				projs.clear();
			}
			
			for(int j = 0; j < projs.size(); j++){
				if(e.getBounds().intersects(projs.get(j).getBounds())){
					score += e.getSpeed();
					enemIt.remove();
					projs.remove(j);
				}
			}

			e.move();
		}
	}


	@Override
	protected void mousePress(int x, int y) {
		if (isOverPauseButton)
			isPaused = !isPaused; // toggle pausing
		else if (isOverQuitButton)
			running = false;
		if(!isPaused){
			hero.shoot(x, y);

		}
	}

	@Override
	protected void mouseMove(int x, int y) {
		if (running) { // stops problems with a rapid move after pressing 'quit'
			isOverPauseButton = pauseArea.contains(x, y) ? true : false;
			isOverQuitButton = quitArea.contains(x, y) ? true : false;
		}

		if(!isPaused) {
			hero.angleCalc(x, y);
		}
	}

	protected void keyPress(int k) {
		
		if(k == KeyEvent.VK_Q)
			isPaused = !isPaused;
		 
		if(!isPaused){
			if(k == KeyEvent.VK_A){
				hero.moveLeft();
			}
			if(k == KeyEvent.VK_D){
				hero.moveRight();
			}
			if(k == KeyEvent.VK_W){
				hero.moveUp();
			}
			if(k == KeyEvent.VK_S){
				hero.moveDown();
			}
		}
	}

	private void drawButtons(Graphics2D g) {
		g.setColor(Color.white);

		// draw the pause 'button'
		if (isOverPauseButton)
			g.setColor(Color.green);

		g.drawOval(pauseArea.x, pauseArea.y, pauseArea.width, pauseArea.height);
		if (isPaused)
			g.drawString("Paused", pauseArea.x, pauseArea.y + 10);
		else
			g.drawString("Pause", pauseArea.x + 5, pauseArea.y + 10);

		if (isOverPauseButton)
			g.setColor(Color.white);

		// draw the quit 'button'
		if (isOverQuitButton)
			g.setColor(Color.green);

		g.drawOval(quitArea.x, quitArea.y, quitArea.width, quitArea.height);
		g.drawString("Quit", quitArea.x + 15, quitArea.y + 10);

		if (isOverQuitButton)
			g.setColor(Color.white);
	} // drawButtons()

	public static void main(String args[]) {
		int fps = DEFAULT_FPS;
		if (args.length != 0)
			fps = Integer.parseInt(args[0]);

		long period = (long) 1000.0 / fps;
		System.out.println("fps: " + fps + "; period: " + period + " ms");
		new ArcadeShooter(period * 1000000L); // ms --> nanosecs


	} // end of main()
}
