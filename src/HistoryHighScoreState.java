import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HistoryHighScoreState extends BasicGameState {
    Image highScoreImage;
    private static int currentScore = 0;
    
    @Override
    public int getID() {
        return GameStateManager.historyHighScoreStateId;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    	this.highScoreImage = new Image("images/highScore.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        float iconWidth = (float) (gameContainer.getWidth() * 0.2);
        float iconScale = iconWidth / this.highScoreImage.getWidth();
        float iconHeight = iconScale * this.highScoreImage.getHeight();
        float iconX = gameContainer.getWidth() / 2 - iconWidth / 2;
        float iconY = (float) (gameContainer.getHeight() * 0.1);
        this.highScoreImage.draw(iconX, iconY, iconWidth, iconHeight); 
        
        
        Path file = Paths.get("high-scores.txt");
		List<String> lines = Collections.emptyList();
		try {
			// Files.write(file, lines, StandardCharsets.UTF_8);
			lines = Files.readAllLines(file, StandardCharsets.UTF_8); 
		} catch (Exception e) {
			// do nothing...
		}
		int height = 300;
		boolean currentScorePrinted = false;
		for (String s : lines) {
			if (Integer.parseInt(s) == currentScore && !currentScorePrinted) {
				currentScorePrinted = true;
				graphics.drawString("Current Score: ", gameContainer.getWidth() / 2 - 140, height);
			}
			graphics.drawString(s, gameContainer.getWidth() / 2 - 10, height);
			height += 20;
		}
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }
    
    public static void setCurrentScore(int num) {
    	currentScore = num;
    }
}
