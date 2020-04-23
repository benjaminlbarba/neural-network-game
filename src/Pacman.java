import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Pacman is the class inherited from BasicCharacter class that contains all relevant
 * fields and methods related to the pacman moving on the map. The only specification that
 * needs to make in Pacman class against BasicCharacter class is to implement the abstract
 * method update();
 */
public class Pacman {

    public static final float stepSize = 1; // must be factor of blockSize (length of row and col)
    private boolean isAddScore;
    private Directions nextDir;

    private HashMap<Directions, Animation> pacmanAnimations = new HashMap<>();

    private float x;
    private float y;

    private float centerX;
    private float centerY;

    private float elementPixelUnit;
    private Directions dir;
    private Circle pacmanCircle;
    private float pacmanCircleRadius;

    private boolean isAtIntersection = false;

    private float closestNonCollisionX;
    private float closestNonCollisionY;

    private boolean isDebug;
    private Animation pacmanRightAnimation;
    private HashMap<Directions, Integer> dirMapX;
    private HashMap<Directions, Integer> dirMapY;
    private boolean isColliding = false;
    private ArrayList<Shape> wallShapesAroundPacman;


    public Pacman(float x, float y, float elementPixelUnit, boolean isDebug) {
        this.x = x;
        this.y = y;
        this.dir = Directions.STILL;
        this.isDebug = isDebug;
        this.elementPixelUnit = elementPixelUnit;
        nextDir = Directions.STILL;
        this.pacmanCircleRadius = (float) ((this.elementPixelUnit / 2) * 0.90);
        initDirMap();
    }

    public void init() {
        try {
            SpriteSheet pacmanLeftSpriteSheet = new SpriteSheet("images/pacman/pacman_left.jpg", 56, 56);
            Animation leftAnimation = new Animation(pacmanLeftSpriteSheet, 100);

            SpriteSheet pacmanRightSpriteSheet = new SpriteSheet("images/pacman/pacman_right.jpg", 56, 56);
            Animation rightAnimation = new Animation(pacmanRightSpriteSheet, 100);

            SpriteSheet pacmanUpSpriteSheet = new SpriteSheet("images/pacman/pacman_up.jpg", 56, 56);
            Animation upAnimation = new Animation(pacmanUpSpriteSheet, 100);

            SpriteSheet pacmanDownSpriteSheet = new SpriteSheet("images/pacman/pacman_down.jpg", 56, 56);
            Animation downAnimation = new Animation(pacmanDownSpriteSheet, 100);

            SpriteSheet pacmanStillSpriteSheet = new SpriteSheet("images/pacman/pacman_still.jpg", 56, 56);
            Animation stillAnimation = new Animation(pacmanStillSpriteSheet, 100);

            this.pacmanAnimations.put(Directions.UP, upAnimation);
            this.pacmanAnimations.put(Directions.DOWN, downAnimation);
            this.pacmanAnimations.put(Directions.LEFT, leftAnimation);
            this.pacmanAnimations.put(Directions.RIGHT, rightAnimation);
            this.pacmanAnimations.put(Directions.STILL, stillAnimation);


            // This is the conversion between animation coordinate and circle coordinate so that they fully overlap.
            this.centerX = this.x + this.elementPixelUnit / 2;
            this.centerY = this.y + this.elementPixelUnit / 2;

            this.pacmanCircle = new Circle(
                    this.centerX,
                    this.centerY,
                    this.pacmanCircleRadius);

        } catch (SlickException e) {
            System.out.println("Cannot load Pacman images.");
        }

    }

    protected void initDirMap() {
        dirMapX = new HashMap<Directions, Integer>();
        dirMapY = new HashMap<Directions, Integer>();
        Directions[] dindex = {
                Directions.STILL,
                Directions.LEFT,
                Directions.RIGHT,
                Directions.UP,
                Directions.DOWN
        };
        int[] dx = {0, -1, 1, 0, 0};
        int[] dy = {0, 0, 0, -1, 1};
        for (int i = 0; i < dindex.length; i++) {
            dirMapX.put(dindex[i], dx[i]);
            dirMapY.put(dindex[i], dy[i]);
        }
    }

    /**
     * Implemented from the abstract method in BasicCharacter class,
     * update method is called every frame of the game by the governing update method in Game class.
     * it updates the positions (x, y) and directions (dir) of the pacman with processed key inputs.
     * Update score based on whether location has dot.
     */
    public void update(
            int delta,
            ArrayList<Shape> closeByWallShapes,
            float closestNonCollisionX,
            float closestNonCollisionY
    ) {
        this.setWallShapesAroundPacman(closeByWallShapes);
        this.closestNonCollisionX = closestNonCollisionX;
        this.closestNonCollisionY = closestNonCollisionY;
        this.pacmanAnimations.values().forEach(animation -> animation.update(delta));
        this.updatePacmanCirclePosition();
        this.setIsAtIntersectionAndCollidingWithWall();
        updatePosition();

        if (dirMovable(nextDir)) {
            dir = nextDir;
        }
        else if (isAtIntersection && isColliding) {
            replaceGhostToPathCenter();
            nextDir = Directions.STILL;
            dir = nextDir;

            // dir remain unchanged if at intersection, nextDir unmovable and dir movable.
        }
    }


    public void render(Graphics g) {
        this.pacmanAnimations.get(this.dir).draw(this.x, this.y, elementPixelUnit, elementPixelUnit);
        if (isDebug) {
            g.draw(this.pacmanCircle);
        }
    }

    /**
     * Return whether next position is accessible given dir.
     *
     * @param d direction for next position
     * @return boolean whether next position is accessible given dir.
     * @see Directions
     */
    private boolean dirMovable(Directions d) {
        float nextX = x + elementPixelUnit * dirMapX.get(d);
        float nextY = y + elementPixelUnit * dirMapY.get(d);

        this.pacmanCircle.setCenterX(nextX + this.elementPixelUnit / 2);
        this.pacmanCircle.setCenterY(nextY + this.elementPixelUnit / 2);
        boolean isNextPositionPacmanCircleColliding = this.getIsCollidingWithCircle(this.pacmanCircle);
        updatePacmanCirclePosition();
        return !isNextPositionPacmanCircleColliding;
    }

/*    *//**
     * Return boolean whether current coordinate is at cell center.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return boolean is at cell center
     *//*
    public boolean isAtCellCenter(float x, float y) {
        boolean b = (Math.round(x) == Math.round(map.getClosestNonCollisionX(x))) &&
                (Math.round(y) == Math.round(map.getClosestNonCollisionY(y)));
        return b;
    }*/

    /**
     * This method updates the center x,y of the circle based on the x, y of the pacman animation.
     */
    private void updatePacmanCirclePosition() {
        this.centerX = this.x + this.elementPixelUnit / 2;
        this.centerY = this.y + this.elementPixelUnit / 2;

        this.pacmanCircle.setCenterX(centerX);
        this.pacmanCircle.setCenterY(centerY);
    }

    /**
     * Setter for nextDir
     *
     * @param nextDir nextDir to be set
     */
    public void setNextDir(Directions nextDir) {
        this.nextDir = nextDir;
    }

    private void setWallShapesAroundPacman(ArrayList<Shape> wallShapesAroundPacman) {
        this.wallShapesAroundPacman = wallShapesAroundPacman;
    }

    // When collision is detected, the pacman circle would already be slightly off the center of its path.
    // This method moves it back to the center, resets its position to be right before the collision so that the
    // collision state is clear and the pacman could change direction.
    private void replaceGhostToPathCenter() {
        this.x = this.closestNonCollisionX;
        this.y = this.closestNonCollisionY;
    }

    public boolean getIsCollidingWithCircle(Circle circle) {
        for (Shape wall : this.wallShapesAroundPacman) {
            if (circle.intersects(wall)) {
                return true;
            }
        }
        return false;
    }

    public void setIsAtIntersectionAndCollidingWithWall() {
        for (Shape wall : this.wallShapesAroundPacman) {
            if (this.pacmanCircle.intersects(wall)) {
                this.isAtIntersection = true;
                this.isColliding = true;
                return;
            }
        }
        // if the ghost is close enough to a nearest non collision location (path center) and the pacman temp circle
        // (placed at the nearest path center) has more than 2 available directions
        // (more than current direction and its reverse), it is also at intersection.
        if (Math.abs(this.closestNonCollisionX - this.x) < this.stepSize / 2 &&
                Math.abs(this.closestNonCollisionY - this.y) < this.stepSize / 2 &&
                this.getAvailableDirections(this.closestNonCollisionX, this.closestNonCollisionY).size() >= 2) {
            this.isAtIntersection = true;
            return;
        }

        this.isAtIntersection = false;
        this.isColliding = false;
    }


    private void updatePosition() {
        if (dir != Directions.STILL) {
            x += dirMapX.get(dir) * stepSize;
            y += dirMapY.get(dir) * stepSize;
        }
    }


    // This method creates a temporary pacman circle that is placed one radius distance more than the
    // current actual pacman circle for each of the four directions. Then it populates available directions for those
    // that do not cause a collision between the temporary pacman circle and the wallShapesAroundPacman.
    private ArrayList<Directions> getAvailableDirections(float x, float y) {
        ArrayList<Directions> availableDirections = new ArrayList<>();

        float currentCircleCenterX = x + this.elementPixelUnit / 2;
        float currentCircleCenterY = y + this.elementPixelUnit / 2;

        Circle tempPacmanCircle = new Circle(
                currentCircleCenterX,
                currentCircleCenterY,
                this.pacmanCircleRadius);

        // LEFT
        tempPacmanCircle.setCenterX(currentCircleCenterX - this.pacmanCircleRadius);
        tempPacmanCircle.setCenterY(currentCircleCenterY);
        if (!this.wallShapesAroundPacman.stream().anyMatch(w -> tempPacmanCircle.intersects(w))) {
            availableDirections.add(Directions.LEFT);
        }

        // RIGHT
        tempPacmanCircle.setCenterX(currentCircleCenterX + this.pacmanCircleRadius);
        tempPacmanCircle.setCenterY(currentCircleCenterY);
        if (!this.wallShapesAroundPacman.stream().anyMatch(w -> tempPacmanCircle.intersects(w))) {
            availableDirections.add(Directions.RIGHT);
        }
        // UP
        tempPacmanCircle.setCenterX(currentCircleCenterX);
        tempPacmanCircle.setCenterY(currentCircleCenterY - this.pacmanCircleRadius);
        if (!this.wallShapesAroundPacman.stream().anyMatch(w -> tempPacmanCircle.intersects(w))) {
            availableDirections.add(Directions.UP);
        }
        // DOWN
        tempPacmanCircle.setCenterX(currentCircleCenterX);
        tempPacmanCircle.setCenterY(currentCircleCenterY + this.pacmanCircleRadius);
        if (!this.wallShapesAroundPacman.stream().anyMatch(w -> tempPacmanCircle.intersects(w))) {
            availableDirections.add(Directions.DOWN);
        }

        return availableDirections;
    }

    /**
     * Getter for isAddScore
     *
     * @return boolean whether score should be incremented if dot is eaten.
     */
    public boolean isAddScore() {
        return isAddScore;
    }

    /**
     * Set isAddScore to true;
     */
    private void addScore() {
        isAddScore = true;
    }

    /**
     * Getter for x.
     *
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * Getter for y.
     *
     * @return y
     */
    public float getY() {
        return y;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    /**
     * Getter for direction.
     *
     * @return dir
     */
    public Directions getDir() {
        return dir;
    }

    /**
     * Setter for direction.
     *
     * @param d direction to be set.
     */
    public void setDir(Directions d) {
        dir = d;
    }

    /**
     * Getter for nextDir.
     *
     * @return nextDir
     */
    public Directions getNextDir() {
        return nextDir;
    }

    /**
     * Setter for next direction.
     *
     * @param dir direction to be set.
     */
    public void setNextDirection(Directions dir) {
        nextDir = dir;
    }

    public Circle getPacmanCircle() {
        return this.pacmanCircle;
    }
}
