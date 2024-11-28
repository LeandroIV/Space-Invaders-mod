package laserGame;

public class Dot {
    private double x, y;
    private String color;
    private double xChange, yChange;
    private boolean isBad, isDead, isDeflected;

    /**
     * Constructs a Dot at (X,Y)
     * @param b
     *  The boolean value determining whether the dot is going evil or not.
     * @param xx
     *  The X Coordinate for the Dot
     * @param yy
     *  The Y Coordinate for the Dot
     * @param xc
     *  The amount the X Coordinate changes by each frame.
     * @param yc
     *  The amount the Y Coordinate changes by each frame.
     * @param s
     *  The color of the dot to fire (red, green blue).
     */
    public Dot(boolean b, int xx, int yy, int xc, int yc, String s){
        isBad = b;
        x = xx;
        y = yy;
        xChange = xc * 0.1;
        yChange = yc * 0.1;
        color = s;
        isDeflected = false; // Initialize isDeflected
    }

    /**
     * Moves the bullet along its path.
     */
    public void step(){
        if (isDeflected) {
            y += yChange;
        } else {
            y += yChange;
            x += xChange;
        }
    }

    /**
     * Sets Dot's X Coordinate
     * @param xx
     *  What X should be set to.
     */
    public void setX(int xx){
        x = xx;
    }

    /**
     * Sets Dot's Y Coordinate
     * @param yy
     *  What Y should be set to.
     */
    public void setY(int yy){
        y = yy;
    }

    /**
     * Returns the Dot's X coordinate.
     * @return
     *  The Dot's X coordinate.
     */
    public int getX(){
        return (int)x;
    }

    /**
     * Returns the Dot's Y coordinate.
     * @return
     *  The Dot's Y coordinate.
     */
    public int getY(){
        return (int)y;
    }

    /**
     * Returns the dot's current color.
     * @return
     *  The dot's color.
     */
    public String getColor(){
        return color;
    }

    /**
     * Tells if the dot is bad or not.
     * @return
     *  The boolean value saying if the dot is bad or not.
     */
    public boolean isBad(){
        return isBad;
    }

    /**
     * Tells if the dot is dead or not.
     * @return
     *  The boolean value saying if the dot is dead or not.
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Sets the dot to dead or not.
     * @param b
     *  The boolean value indicating if the dot is dead.
     */
    public void setDead(boolean b){
        isDead = b;
    }

    /**
     * Sets the direction of the dot.
     * @param xChange
     *  The amount the X coordinate changes by each frame.
     * @param yChange
     *  The amount the Y coordinate changes by each frame.
     */
    public void setDirection(double xChange, double yChange) {
        this.xChange = xChange * 0.3;
        this.yChange = yChange * 0.3;
    }

    /**
     * Sets the speed of the dot.
     * @param speed
     *  The new speed of the dot.
     */
    public void setSpeed(double speed) {
        double direction = Math.atan2(yChange, xChange);
        xChange = speed * Math.cos(direction) * 0.3;
        yChange = speed * Math.sin(direction) * 0.3;
    }

    /**
     * Gets the speed of the dot.
     * @return
     *  The current speed of the dot.
     */
    public double getSpeed() {
        return Math.sqrt(xChange * xChange + yChange * yChange) * 10;
    }

    /**
     * Sets the deflected status of the dot.
     * @param deflected
     *  The boolean value indicating if the dot is deflected.
     */
    public void setDeflected(boolean deflected) {
        isDeflected = deflected;
    }

    /**
     * Returns the deflected status of the dot.
     * @return
     *  The boolean value indicating if the dot is deflected.
     */
    public boolean isDeflected() {
        return isDeflected;
    }
}
