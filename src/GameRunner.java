import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * GameRunner contains the main method of this project to start the game
 */
public class GameRunner {
	public static void main(String[] args) throws SlickException {
		// TODO: there could be exceptions thrown from the Slick2D library methods, once we implement every method, we should
		// think about how to properly handle the exceptions.
		
		int gameWindowWidth = 800;
		int gameWindowHeight = 600;
		
		Game pacmanGame = new Game("Pacman", gameWindowWidth, gameWindowHeight);
		AppGameContainer appGameContainer = new AppGameContainer(pacmanGame);
		
		appGameContainer.setDisplayMode(gameWindowWidth, gameWindowHeight, false);
		// TODO: confirm the desired update interval.
		appGameContainer.setMinimumLogicUpdateInterval(1000 / 60);
		appGameContainer.start();
	}
}
