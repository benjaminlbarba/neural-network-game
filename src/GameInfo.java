import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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
	
	/**
	 * Update the high history scores
	 * @return
	 */
	public boolean updateHighScore() {
		// List<String> lines = Arrays.asList("0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
		Path file = Paths.get("high-scores.txt");
		List<String> lines = Collections.emptyList();
		try {
			// Files.write(file, lines, StandardCharsets.UTF_8);
			lines = Files.readAllLines(file, StandardCharsets.UTF_8); 
		} catch (Exception e) {
			// do nothing...
			return false;
		}
		if (lines.size() != 10) {
			return false;
		}
		List<Integer> intList = lines.stream()
				.map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());
		if (this.getScore() > intList.get(9)) {
			intList.set(9, this.getScore());
		}
		Collections.sort(intList, Collections.reverseOrder()); 
		List<String> newLines = intList.stream()
				.map(s -> Integer.toString(s))
				.collect(Collectors.toList());
		try {
			Files.write(file, newLines, StandardCharsets.UTF_8);
		} catch (Exception e) {
			// do nothing...
			return false;
		}
		return true;
	}

}
