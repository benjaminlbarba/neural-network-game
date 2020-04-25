import org.newdawn.slick.Graphics;

/**
 * GameInfo is the class that initializes the score in the game, keeps track of it, and display it on the game window.
 */
public class GameInfo {
	private int score;
	private int lives;
	private int level;

	public GameInfo() {
		// If we eventually decide to implement more complex initial score logic (such as different levels receives different initial scores),
		// we should create a initializeScore method to handle that specifically.
		this.score = 0;
		this.lives = 3;
		this.level = 1;
	}

	public void addScore(int scoreAdded) {
		this.score += scoreAdded;
	}
	
	public void render(Graphics g) {
		g.drawString("Level:" + this.level, 10, 50);
		g.drawString("Score: " + this.score, 10, 100);
		g.drawString("Remaining lives: " + this.lives, 10, 150);
	}

	public int getLives() {
		return this.lives;
	}

	public void setLives(int newLives) {
		this.lives = newLives;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return this.level;
	}

}
