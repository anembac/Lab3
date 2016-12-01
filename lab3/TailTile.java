package lab3;

import java.awt.*;


/**
 * A tail tile manages painting of a
 * filled rectangle (with a triangle pointing in the direction the head
 * was heading) in a specified area of the screen.
 * <p>
 * Whenever the object should paint itself,
 * it is told what size and position that
 * should be used to paint it.
 */
public class TailTile extends GameTile {

    /**
     * The color of the tail
     */
    private final Color color;
    private SnakeModel.Directions dir;

    /**
     * Creates a tail game tile.
     *
     * @param color the color of the tail.
     */
    public TailTile(final Color color) {
        this.color = color;
        this.dir = SnakeModel.Directions.NONE;
    }

    /**
     *
     * @param color The color of the tail
     * @param dir   A direction as defined in the SnakeModel class
     */
    public TailTile(final Color color, SnakeModel.Directions dir) {
        this.color = color;
        this.dir = dir;
    }


    /**
     * Draws itself in a given graphics context, position and size.
     *
     * @param g graphics context to draw on.
     * @param x pixel x coordinate of the tile to be drawn.
     * @param y pixel y coordinate of the tile to be drawn.
     * @param d size of this object in pixels.
     */
    @Override
    public void draw(final Graphics g, final int x, final int y,
                     final Dimension d) {
        g.setColor(this.color);
        g.fillRect(x, y, d.width, d.height);
        g.setColor(new Color(100, 100, 0));
        switch (dir) {
            case NORTH:
                g.fillPolygon(new Polygon(
                        new int[]{x + d.width / 2, x + 2 * d.width / 3, x + d.width / 3},
                        new int[]{y + d.height / 3, y + 3 * d.height / 4, y + 3 * d.height / 4},
                        3));
                break;
            case SOUTH:
                g.fillPolygon(new Polygon(
                        new int[]{x + d.width / 2, x + 2 * d.width / 3, x + d.width / 3},
                        new int[]{y + 2 * d.width / 3, y + d.height / 3, y + d.height / 3},
                        3));
                break;
            case WEST:
                g.fillPolygon(new Polygon(
                        new int[]{x + d.width / 3, x + 2 * d.width / 3, x + 2 * d.width / 3},
                        new int[]{y + d.height / 2, y + d.height / 3, y + 2 * d.height / 3},
                        3));
                break;
            case EAST:
                g.fillPolygon(new Polygon(
                        new int[]{x + d.width / 3, x + d.width / 3, x + 2 * d.width / 3},
                        new int[]{y + 2 * d.height / 3, y + d.height / 3, y + d.height / 2},
                        3));
                break;
            default: //same as north, possible to suggest that in code?
                g.fillPolygon(new Polygon(
                        new int[]{d.width / 2, 2 * d.width / 3, d.width / 3},
                        new int[]{d.height / 3, 3 * d.height / 4, 3 * d.height / 4},
                        3));
                break;
        }

    }
}