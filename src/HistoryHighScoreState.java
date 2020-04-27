import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HistoryHighScoreState extends BasicGameState {
    Image highScoreImage;
    Image backButtonImage;
    private static int currentScore = 0;
    
    float backButtonWidth;
    float backButtonHeight;
    float backButtonX;
    float backButtonY;
    
    @Override
    public int getID() {
        return GameStateManager.historyHighScoreStateId;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    	this.highScoreImage = new Image("images/highScore.png");
    	this.backButtonImage = new Image("images/backButton.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        float iconWidth = (float) (gameContainer.getWidth() * 0.2);
        float iconScale = iconWidth / this.highScoreImage.getWidth();
        float iconHeight = iconScale * this.highScoreImage.getHeight();
        float iconX = gameContainer.getWidth() / 2 - iconWidth / 2;
        float iconY = (float) (gameContainer.getHeight() * 0.15);
        this.highScoreImage.draw(iconX, iconY, iconWidth, iconHeight);

        Path file = Paths.get("high-scores.txt");
		List<String> lines = Collections.emptyList();
		try {
			// Files.write(file, lines, StandardCharsets.UTF_8);
			lines = Files.readAllLines(file, StandardCharsets.UTF_8); 
		} catch (Exception e) {
			// do nothing...
		}
		float scoreY = (float) (iconY + (gameContainer.getHeight() * 0.4));
		boolean currentScorePrinted = false;
		for (String s : lines) {
			if (Integer.parseInt(s) == currentScore && !currentScorePrinted) {
				currentScorePrinted = true;
				graphics.drawString("Current Score: ", gameContainer.getWidth() / 2 - 140, scoreY);
			}
			graphics.drawString(s, gameContainer.getWidth() / 2 - 10, scoreY);
            scoreY += 20;
		}

        this.backButtonX = iconX;
        this.backButtonY = scoreY;
        this.backButtonWidth = iconWidth;
        this.backButtonHeight = iconScale * this.backButtonImage.getHeight();
        this.backButtonImage.draw(backButtonX, backButtonY, backButtonWidth, backButtonHeight);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    	float posX = Mouse.getX();
    	float posY = gameContainer.getHeight() - Mouse.getY() - 16;
    	// Click history button
    	if ((posX > this.backButtonX && posX < this.backButtonX + this.backButtonWidth)
    			&& (posY > this.backButtonY && posY < this.backButtonY + this.backButtonHeight)
                && Mouse.isButtonDown(0)) {
            stateBasedGame.enterState(GameStateManager.gameOverStateId);
    	}

    }
    
    public static void setCurrentScore(int num) {
    	currentScore = num;
    }
}
