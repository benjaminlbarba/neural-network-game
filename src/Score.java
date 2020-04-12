/**
 * Score is the class that initializes the score in the game, keeps track of it, and display it on the game window.
 */
public class Score {
	private int score;
	
	public Score() {
		// If we eventually decide to implement more complex initial score logic (such as different levels receives different initial scores),
		// we should create a initializeScore method to handle that specifically.
		this.score = 0;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

}
