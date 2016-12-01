package lab3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.*;
//TODO JAVADOC

/**
 * Initially 1 food is randomly placed in the matrix. The dark green
 * snake head aims to collect food which disappear after collection,
 * immediately generating a new piece of food.
 * For every food eaten the snake's length will increase by one tile.
 * The game is over when the snake hits a wall or itself.
 */
public class SnakeModel extends GameModel {
    public enum Directions {
        EAST(1, 0),
        WEST(-1, 0),
        NORTH(0, -1),
        SOUTH(0, 1),
        NONE(0, 0);

        private final int xDelta;
        private final int yDelta;

        Directions(final int xDelta, final int yDelta) {
            this.xDelta = xDelta;
            this.yDelta = yDelta;
        }

        public int getXDelta() {
            return this.xDelta;
        }

        public int getYDelta() {
            return this.yDelta;
        }
    }

	/*
     * The following GameTile objects are used only
	 * to describe how to paint the specified item.
	 * 
	 * This means that they should only be used in
	 * conjunction with the get/setGameboardState()
	 * methods.
	 */

    /**
     * Color constant for the head.
     */
    private static final Color hc = new Color(0, 100, 0);

    /**
     * Color constant for the tail
     */
    private static final Color tc = new Color(0, 200, 0);

    /**
     * Graphical representation of a piece of food.
     */
    private static final GameTile FOOD_TILE = new FoodTile(new Color(255, 0, 0), new Color(0, 255, 0));

    /**
     * Graphical representation of the snake's head.
     */
    private static HeadTile HEAD_TILE = new HeadTile(hc);

    /**
     * Graphical representation of the snake's tail.
     */
    private static GameTile TAIL_TILE = new TailTile(tc);

    /**
     * Graphical representation of a blank tile.
     */
    private static final GameTile BLANK_TILE = new GameTile();

    /**
     * A list containing the positions of the snake's tail.
     */
    private final Queue<Position> tail = new LinkedList<>();

    /**
     * The position of the single piece of food on the board.
     */
    private Position foodPos;
    /*
     * The declaration and object creation above uses the new language feature
	 * 'generic types'. It can be declared in the old way like this:
	 * private java.util.Queue tail = new LinkedList();
	 * This will however result in a warning at compilation
	 * "Position" in this case is the type of the objects that are going
	 * to be used in the List
	 */

    /**
     * The position of the head.
     */
    private Position headPos;

    /**
     * The direction of the snake.
     */
    private Directions direction = Directions.NORTH;

    /**
     * The number of food eaten.
     */
    private int score;

    /**
     * Create a new model for the snake game.
     */
    public SnakeModel() {
        Dimension size = getGameboardSize();

        // Blank out the whole gameboard
        for (int i = 0; i < size.width; i++) {
            for (int j = 0; j < size.height; j++) {
                setGameboardState(i, j, BLANK_TILE);
            }
        }

        // Insert the head in the middle of the gameboard.
        this.headPos = new Position(size.width / 2, size.height / 2);
        setGameboardState(this.headPos, HEAD_TILE);
        addFood();
    }

    /**
     * Method for inserting food into the gameboard.
     * Only one piece of food exists at any given moment.
     */
    private void addFood() {
        Position newFoodPos;
        Dimension size = getGameboardSize();
        // Loop until a blank position is found and ...
        do {
            newFoodPos = new Position((int) (Math.random() * size.width),
                    (int) (Math.random() * size.height));
        } while (!isPositionEmpty(newFoodPos) && containsBlankTile());
        if (containsBlankTile()) {
            // ... add a new piece of food to the empty tile.
            setGameboardState(newFoodPos, FOOD_TILE);
            foodPos = newFoodPos;
        }
    }

    /**
     * Return whether the specified position is empty.
     *
     * @param pos The position to test.
     * @return true if position is empty.
     */
    private boolean isPositionEmpty(final Position pos) {
        return (getGameboardState(pos) == BLANK_TILE);
    }

    /**
     * Update the direction of the snake
     * according to the user's keypress.
     * The snake cannot go backwards into itself.
     */
    private void updateDirection(final int key) {
        switch (key) {
            case KeyEvent.VK_LEFT:
                if (!(this.direction == Directions.EAST)) {
                    this.direction = Directions.WEST;

                }
                break;
            case KeyEvent.VK_UP:
                if (!(this.direction == Directions.SOUTH)) {
                    this.direction = Directions.NORTH;

                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!(this.direction == Directions.WEST)) {
                    this.direction = Directions.EAST;

                }
                break;
            case KeyEvent.VK_DOWN:
                if (!(this.direction == Directions.NORTH)) {
                    this.direction = Directions.SOUTH;

                }
                break;
            default:
                // Don't change direction if another key is pressed
                break;
        }
    }

    /**
     * Get next position of the snake head.
     */
    private Position getNextHeadPos() {
        return new Position(
                this.headPos.getX() + this.direction.getXDelta(),
                this.headPos.getY() + this.direction.getYDelta());
    }


    /**
     * This method is called repeatedly so that the
     * game can update its state.
     *
     * @param lastKey The most recent keystroke.
     */
    @Override
    public void gameUpdate(final int lastKey) throws GameOverException {
        updateDirection(lastKey);

        // Erase the previous position and adds that position to the tail.
        TAIL_TILE = new TailTile(tc, this.direction);
        setGameboardState(this.headPos, TAIL_TILE);
        tail.add(this.headPos);

        //Change snake head position.
        this.headPos = getNextHeadPos();


        //Checks for gameover conditions.
        if (isOutOfBounds(this.headPos) || hitTail(this.headPos)) {
            throw new GameOverException(this.score);
        } else if (!containsBlankTile()) {
            System.out.println("Wow, You win!");
            throw new GameOverException(this.score + 1);
        }
        // Draw snake head at new position.
        HEAD_TILE.setDir(this.direction);
        setGameboardState(this.headPos, HEAD_TILE);

        // Remove the food at the new snake head position (if any)
        if (this.foodPos.equals(this.headPos)) {
            addFood();
            this.score++;
        } else {
            //The average game tick will have the tail grow by 1 and then decrease by 1.
            //If food is eaten it will simply grow by 1.
            setGameboardState(tail.remove(), BLANK_TILE);
        }
    }

    /**
     * @param pos The position to test.
     * @return <code>true</code> if the position is outside the playing field, <code>false</code> otherwise.
     */
    private boolean isOutOfBounds(Position pos) {
        return pos.getX() < 0 || pos.getX() >= getGameboardSize().width
                || pos.getY() < 0 || pos.getY() >= getGameboardSize().height;
    }

    //checks if gameboard still has blank tiles
    private boolean containsBlankTile() {
        for (int x = 0; x < getGameboardSize().getWidth(); x++) {
            for (int y = 0; y < getGameboardSize().getHeight(); y++) {
                if (getGameboardState(x, y).equals(BLANK_TILE)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * @param pos The position to test.
     * @return <code>true</code> if the position is located on a tail_tile (or head tile,
     * but ideally there is only one head), <code>false</code> otherwise.
     */
    private boolean hitTail(Position pos) {
        return !(getGameboardState(pos).equals(BLANK_TILE)) &&
                !(getGameboardState(pos).equals(FOOD_TILE));
    }

}
