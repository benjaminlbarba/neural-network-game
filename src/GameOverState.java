import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverState extends BasicGameState {
    Image backgroundImage;
    Image replayButtonImage;


    @Override
    public int getID() {
        return GameStateManager.gameOverStateId;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.backgroundImage = new Image("images/gameOverWallpaper.jpg");
        this.replayButtonImage = new Image("images/replayButton.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        float imageScale =  this.getFullWindowImageScale(
                gameContainer.getWidth(),
                gameContainer.getHeight(),
                this.backgroundImage);

        float imageX = (gameContainer.getWidth() - imageScale * this.backgroundImage.getWidth()) / 2;
        float imageY = (gameContainer.getHeight() - imageScale * this.backgroundImage.getHeight()) / 2;

        this.backgroundImage.draw(imageX, imageY, imageScale);

        float buttonWidth = (float) (gameContainer.getWidth() * 0.2);
        float buttonScale = buttonWidth / this.replayButtonImage.getWidth();
        float buttonHeight = buttonScale * this.replayButtonImage.getHeight();
        float buttonX = gameContainer.getWidth() / 2 - buttonWidth / 2;
        float buttonY = (float) (gameContainer.getHeight() * 0.7);
        this.replayButtonImage.draw(buttonX, buttonY, buttonWidth, buttonHeight);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    private float getFullWindowImageScale(float windowW, float windowH, Image image) {
        float imageW = image.getWidth();
        float imageH = image.getHeight();

        float widthRatio = windowW / imageW;
        float heightRatio = windowH / imageH;

        return widthRatio < heightRatio ? widthRatio : heightRatio;
    }
}
