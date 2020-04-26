import org.lwjgl.input.Mouse;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverState extends BasicGameState {
    Image backgroundImage;
    Image replayButtonImage;
    Image historyButtonImage;
    
    float imageX;
    float imageY;
    float imageHeight;
    
    float historyButtonX;
    float historyButtonY;
    float historyButtonWidth;
    float historyButtonHeight;


    @Override
    public int getID() {
        return GameStateManager.gameOverStateId;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.backgroundImage = new Image("images/gameOverWallpaper.jpg");
        this.replayButtonImage = new Image("images/replayButton.png");
        this.historyButtonImage = new Image("images/historyScore.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        float imageScale =  this.getFullWindowImageScale(
                gameContainer.getWidth(),
                gameContainer.getHeight(),
                this.backgroundImage);

        imageX = (gameContainer.getWidth() - imageScale * this.backgroundImage.getWidth()) / 2;
        imageY = (gameContainer.getHeight() - imageScale * this.backgroundImage.getHeight()) / 2;
        imageHeight = imageScale * this.backgroundImage.getHeight();

        this.backgroundImage.draw(imageX, imageY, imageScale);

        float buttonWidth = (float) (gameContainer.getWidth() * 0.2);
        float buttonScale = buttonWidth / this.replayButtonImage.getWidth();
        float buttonHeight = buttonScale * this.replayButtonImage.getHeight();
        float buttonX = gameContainer.getWidth() / 2 - buttonWidth / 2;
        float buttonY = (float) (gameContainer.getHeight() * 0.67);
        this.replayButtonImage.draw(buttonX, buttonY, buttonWidth, buttonHeight);
        
        this.historyButtonWidth = (float) (gameContainer.getWidth() * 0.2);
        float historyButtonScale = historyButtonWidth / this.replayButtonImage.getWidth();
        this.historyButtonHeight = historyButtonScale * this.replayButtonImage.getHeight();
        this.historyButtonX = gameContainer.getWidth() / 2 - historyButtonWidth / 2;
        this.historyButtonY = buttonY + 60;
        
        this.historyButtonImage.draw(historyButtonX, historyButtonY, historyButtonWidth, historyButtonHeight);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    	float posX = Mouse.getX();
    	float posY = imageHeight + 2 * imageY - Mouse.getY() - 16;
    	if ((posX > historyButtonX && posX < historyButtonX + historyButtonWidth)
    			&& (posY > historyButtonY && posY < historyButtonY + historyButtonHeight)) {
    		if (Mouse.isButtonDown(0)) {
    			stateBasedGame.enterState(GameStateManager.historyHighScoreStateId);
    		}
    	}
    }

    private float getFullWindowImageScale(float windowW, float windowH, Image image) {
        float imageW = image.getWidth();
        float imageH = image.getHeight();

        float widthRatio = windowW / imageW;
        float heightRatio = windowH / imageH;

        return widthRatio < heightRatio ? widthRatio : heightRatio;
    }
}
