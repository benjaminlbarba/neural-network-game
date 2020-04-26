import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * GameStateManager contains the main method of this project and manages between different states of the game such as
 * game state, game over state, etc.
 */
public class GameStateManager extends StateBasedGame {
	public static final int gameWindowWidth = 800;
	public static final int gameWindowHeight = 600;

	public static final int mainGameStateId = 0;
	public static final int gameOverStateId = 1;
	public static final int historyHighScoreStateId = 2;

	public GameStateManager(String title) {
		super(title);
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		this.addState(new MainGameState(gameWindowWidth, gameWindowHeight, false));
		this.addState(new GameOverState());
		this.addState(new HistoryHighScoreState());
	}

	@Override
	public void keyPressed(int key, char c) {
		this.getState(mainGameStateId).keyPressed(key, c);
	}

	public static void main(String[] args) throws SlickException {

		AppGameContainer appGameContainer = new AppGameContainer(new GameStateManager("Pacman"));

		appGameContainer.setDisplayMode(gameWindowWidth, gameWindowHeight, false);
		appGameContainer.setShowFPS(false);
		appGameContainer.setAlwaysRender(true);
		appGameContainer.setMinimumLogicUpdateInterval(1000 / 60);
		appGameContainer.start();
	}
}
