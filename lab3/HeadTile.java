package lab3;

import java.awt.*;
import lab3.SnakeModel.Directions;

public class HeadTile extends GameTile {

    /**
     * The color of the head
     */
    private final Color color;
    public Directions dir;



    /**
     * Creates a vaguely head-shaped game tile.
     *
     * @param color the color of the Head.
     */
    @Deprecated
    public HeadTile(final Color color, Directions dir) {
        this.color = color;
        this.dir = dir;
    }

    public HeadTile(final Color color){
        this.color = color;
        this.dir = Directions.NONE;
    }

    public void setDir(Directions dir){
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
    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void draw(final Graphics g, final int x, final int y,
                     final Dimension d) {
        final int eyesize = d.width/4;
        g.setColor(this.color);
        switch(dir){
            case NORTH:
                //head
                g.fillArc(x, y, d.width, d.height, 0, 180);
                g.fillRect(x, y + (d.height / 2), d.width, d.height / 2);
                //eyes
                g.setColor(new Color(255,160,50));
                g.fillOval(x+2,y+d.height/2-eyesize,eyesize,eyesize);
                g.fillOval(x+d.width-eyesize-2,y+d.height/2-eyesize,eyesize,eyesize);
                break;

            case WEST:
                //head
                g.fillArc(x, y, d.height, d.width, 90, 180);
                g.fillRect(x+d.height/2,y,d.height/2,d.width);
                //eyes
                g.setColor(new Color(255,160,50));
                g.fillOval(x+d.width/2-eyesize,y+2,eyesize,eyesize);
                g.fillOval(x+d.width/2-eyesize,y+d.width-eyesize-2,eyesize,eyesize);

                break;
            case EAST:
                //head
                g.fillArc(x, y, d.height, d.width, 270, 180);
                g.fillRect(x,y,d.height/2,d.width);
                //eyes
                g.setColor(new Color(255,160,50));
                g.fillOval(x+d.width/2,y+2,eyesize,eyesize);
                g.fillOval(x+d.width/2,y+d.width-eyesize-2,eyesize,eyesize);

                break;
            case SOUTH:
                //head
                g.fillArc(x, y, d.width, d.height, 180, 180);
                g.fillRect(x, y, d.width, d.height / 2);
                //eyes
                g.setColor(new Color(255,160,50));
                g.fillOval(x+2,y+d.height/2,eyesize,eyesize);
                g.fillOval(x+d.width-eyesize-2,y+d.height/2,eyesize,eyesize);

                break;
            default: //same as north
                //head
                g.fillArc(x, y, d.width, d.height, 0, 180);
                g.fillRect(x, y + (d.height / 2), d.width, d.height / 2);
                //tongue
                g.setColor(new Color(255,0,0));
                g.drawLine(x+d.width/2,y,x+d.width/2,y-5);
                g.drawLine(x+d.width/2,y-5,x+d.width/2-4,y-9);
                g.drawLine(x+d.width/2,y-5,x+d.width/2+4,y-9);
                //eyes
                g.setColor(new Color(255,160,50));
                g.fillOval(x+2,y+15,eyesize,eyesize);
                g.fillOval(x+d.width-eyesize-2,y+15,eyesize,eyesize);
                break;
        }
    }

}