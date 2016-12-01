package lab3;

import java.awt.*;

public class FoodTile extends GameTile {

    /**
     * The color of the head
     */
    private final Color color1;
    private final Color color2;


    /**
     * Creates a vaguely head-shaped game tile.
     *
     * @param color1 the primary color.
     * @param color2 the secondary color.
     */
    public FoodTile(final Color color1, final Color color2) {
        this.color1 = color1;
        this.color2 = color2;
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

        final int foodwidth = 2*d.width/3;
        g.setColor(color2);
        g.fillArc(x+foodwidth-5,y-foodwidth/3,foodwidth/2,foodwidth/2,225,180);
        g.fillArc(x+foodwidth/2+5,y-foodwidth/3,foodwidth/2,foodwidth/2,135,180);
        g.setColor(color1);
        g.fillOval(x+foodwidth/2,y,foodwidth,foodwidth);




    }

}