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

    float replayButtonWidth;
    float replayButtonHeight;
    float replayButtonX;
    float replayButtonY;

    float historyButtonWidth;
    float historyButtonHeight;
    float historyButtonX;
    float historyButtonY;



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

        this.replayButtonWidth = (float) (gameContainer.getWidth() * 0.2);
        float buttonScale = replayButtonWidth / this.replayButtonImage.getWidth();
        this.replayButtonHeight = buttonScale * this.replayButtonImage.getHeight();
        this.replayButtonX = gameContainer.getWidth() / 2 - this.replayButtonWidth / 2;
        this.replayButtonY = (float) (gameContainer.getHeight() * 0.67);
        this.replayButtonImage.draw(this.replayButtonX, this.replayButtonY, this.replayButtonWidth, this.replayButtonHeight);
        
        this.historyButtonWidth = (float) (gameContainer.getWidth() * 0.2);
        float historyButtonScale = historyButtonWidth / this.replayButtonImage.getWidth();
        this.historyButtonHeight = historyButtonScale * this.replayButtonImage.getHeight();
        this.historyButtonX = gameContainer.getWidth() / 2 - this.historyButtonWidth / 2;
        this.historyButtonY = this.replayButtonY + 60;
        
        this.historyButtonImage.draw(this.historyButtonX, this.historyButtonY, this.historyButtonWidth, this.historyButtonHeight);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    	float posX = Mouse.getX();
    	float posY = this.imageHeight + 2 * this.imageY - Mouse.getY() - 16;
    	// Click history button
    	if ((posX > this.historyButtonX && posX < this.historyButtonX + this.historyButtonWidth)
    			&& (posY > this.historyButtonY && posY < this.historyButtonY + this.historyButtonHeight)
                && Mouse.isButtonDown(0)) {
            stateBasedGame.enterState(GameStateManager.historyHighScoreStateId);
    	}
    	// Click replay button
        if ((posX > this.replayButtonX && posX < this.replayButtonX + this.replayButtonWidth)
                && (posY > this.replayButtonY && posY < this.replayButtonY + this.replayButtonHeight)
                && Mouse.isButtonDown(0)) {
            stateBasedGame.enterState(GameStateManager.mainGameStateId);
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
