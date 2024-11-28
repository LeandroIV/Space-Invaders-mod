package laserGame;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GamePanel extends JPanel {

    private int x, y, limitX, limitY, playerX, playerY, bgSlide, energyContainerNum, heatContainerNum, enemyContainerNum, enemyContainerNumTotal, heat, energy, hp, score;
    private boolean isShielded, isVenting, win, lose;
    private boolean showPulsatingU; 
    private Image background, ship, gDot, bDot, rDot, shield, venting;
    private MediaTracker tracker;
    private Level lvl;
    private LinkedList<Dot> dotList;
    private ArrayList<Enemy> enemies;
    private boolean winSoundPlayed = false;
    private boolean loseSoundPlayed = false;

    /**
     * Default Constructor
     */
    public GamePanel(Level ll) {
        super();
        win = false;
        lose = false;
        lvl = ll;
        background = lvl.getBackground();

        energyContainerNum = 0; // Initially set to 0
        heatContainerNum = 0;
        energy = 0; // Initialize energy to 0

        // Make below dynamic in future
        ship = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("images/blueShip.png"));

        gDot = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("images/greenDot.png"));

        bDot = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("images/blueDot.png"));

        rDot = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("images/redDot.png"));

        shield = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("images/roundShield.png"));

        venting = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("images/venting.png"));

        dotList = new LinkedList<Dot>();

        heatContainerNum = 0;

        tracker = new MediaTracker(this);
        tracker.addImage(background, 0);
        tracker.addImage(ship, 1);
        tracker.addImage(shield, 2);
        tracker.addImage(venting, 3);
        tracker.addImage(gDot, 4);
        tracker.addImage(bDot, 5);
        tracker.addImage(rDot, 6);

        try {
            tracker.waitForAll();
        } catch (InterruptedException ex) {
        }
        // End of content to be dynamic

        this.setSize(800, 750);

        showPulsatingU = false; // Initialize the pulsating U flag
    }
    
    /**
     * Tells the program how to paint the background
     * @param g
     * takes a graphics object
     */
    @Override
public void paint(Graphics g) {
    // Turns on AntiAliasing
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g.setColor(new Color(0, 0, 0));
    g.fillRect(0, 0, 800, 750);
    // BACKGROUND, HP MONITOR, & SHIP
    g.drawImage(background, 0, 0 + bgSlide, this);
    g.drawImage(background, x, y + bgSlide - 1200, this);

    g.setColor(new Color(30, 62, 100));
    g.fillArc(32, 615, 70, 70, 0, (int) ((hp / 100.0) * 360));
    g.setFont(new Font("Monospace", 20, 20));
    g.setColor(new Color(255, 255, 255));
    g.drawString("" + hp, 50, 655);
    g.setFont(new Font("Monospace", 10, 10));

    g.drawImage(ship, playerX, playerY, this);

    // ENEMIES
    for (int i = 0; i < enemies.size(); i++) {
        Character c = enemies.get(i);
        g.drawImage(c.getImage(), c.getX(), c.getY(), this);
    }

    // ATTACKS
    try {
        Iterator<Dot> it = dotList.iterator();
        while (it.hasNext()) {
            Dot tmpDot = it.next();
            g.drawImage(this.getDotImage(tmpDot.getColor()), tmpDot.getX(), tmpDot.getY(), this);
        }
    } catch (ConcurrentModificationException ex) {
    }

    g.drawImage(ship, playerX, playerY, this);

    // VENTING
    if (isVenting) {
        g.drawImage(venting, playerX, playerY, this);
    }
    // SHIELD
    if (isShielded) {
        g.drawImage(shield, playerX, playerY, this);
    }

    // INTERFACE
    g.setColor(new Color(114, 0, 255));
    for (int i = 0; i < 20; i++) {
        g.setColor(new Color(114, 0, 255));
        if (energyContainerNum <= i)
            g.drawRect(10, 150 + (i * 15), 30, 10);
        else
            g.fillRect(10, 150 + (i * 15), 30, 10);
        g.setColor(new Color(255, 114, 0));
        if (heatContainerNum <= i)
            g.drawRect(50, 150 + (i * 15), 20, 10);
        else
            g.fillRect(50, 150 + (i * 15), 20, 10);
    }

    if (enemyContainerNumTotal > 0) {
        g.setColor(new Color(30, 225, 0));
        g.drawRect(767, 150, 25, ((enemyContainerNumTotal * 300) / enemyContainerNumTotal) + 2);
        if (enemyContainerNum > 0) {
            g.fillRect(770, 152, 20, (enemyContainerNum * 300) / enemyContainerNumTotal);
        }
    }

    g.setColor(new Color(100, 100, 100));
    g.drawRect(5, 5, 90, 75);
    g.setColor(new Color(0, 112, 255));
    g.drawString("X-Coord: " + playerX, 10, 20);
    g.drawString("Y-Coord: " + playerY, 10, 30);
    g.setColor(new Color(0, 200, 0));
    g.drawString("Dots: " + dotList.size(), 10, 40);
    g.setColor(new Color(194, 0, 255));
    g.drawString("Energy: " + energy, 10, 50);
    g.setColor(new Color(255, 114, 0));
    g.drawString("Heat: " + heat, 10, 60);
    g.setColor(new Color(255, 255, 255));
    g.drawString("HP:" + hp, 10, 70);
    g.setColor(new Color(255, 255, 0));
    g.drawString("Score: " + score, 10, 80);

    if (win) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, 800, 750);
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Monospace", 50, 50));
        g.drawString("You Win!", 300, 300);
        g.setFont(new Font("Monospace", 30, 30));
        g.drawString("Final Score: " + score, 295, 350);
    } else if (lose) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, 800, 750);
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Monospace", 50, 50));
        g.drawString("You Lose!", 290, 350);
        g.setFont(new Font("Monospace", 30, 30));
        g.drawString("Final Score: " + score, 295, 400);
    } else if (showPulsatingU) {
        // Draw the pulsating "U" if the flag is set and the game is not won or lost
        drawPulsatingU(g2);
    }

    this.repaint();
}
        
    

    
    

    /**
     * Sets the characters coordinates
     * @param newX
     *  takes in the X coordinate
     * @param newY
     *  takes in the X coordinate
     */
    public void setCoordinates(int newX, int newY){
        x=newX;
        y=newY;
    }

    /**
     * Sets the characters coordinates
     * @param newX
     *  takes in the X coordinate
     * @param newY
     *  takes in the X coordinate
     */
    public void setPlayerCoordinates(int newX, int newY){
        playerX=newX;
        playerY=newY;
    }

    /**
     * Sets if the shield should be drawn
     * @param b
     *  What the shield status should be set to
     */
    public void setShield(boolean b){
        isShielded=b;
    }

    /**
     * Sets if the vent should be drawn
     * @param b
     *  What the vent status should be set to
     */
    public void setVenting(boolean b){
        isVenting=b;
    }

    /**
     * Sets the list of Dots to be drawn.
     * @param d
     *  What the playerDotList should be set to.
     */
    public void setDots(LinkedList<Dot> d){
        dotList=d;
    }

    /**
     * Sets the player's energy.
     * @param i
     *  What the energy should be set to.
     */
    public void setEnergy(int i){
        energy=i;
    }
    
    /**
     * Sets the player's energy.
     * @param i
     *  What the energy should be set to.
     */
    public void setHeat(int i){
        heat=i+30;
    }

    /**
     * Sets the panel's hp monitor value
     * @param i
     *  The value to set the panel's hp monitor to
     */
    public void setHP(int i){
        hp=i;
    }
    /**
     * Sets the player's energy.
     * @param i
     *  What the energy should be set to.
     */
    public void setEnergyContainer(int i){
        energyContainerNum=i;
    }

    /**
     * Sets the player's energy.
     * @param i
     *  What the energy should be set to.
     */
    public void setHeatContainer(int i){
        heatContainerNum=i;
    }

    /**
     * Returns the boundaries of the picture
     * @return
     *  Returns X amt of pixels
     */
    public int getLimitX(){
        limitX = background.getWidth(this);
        return limitX;
    }

    /**
     * Returns the boundaries of the picture
     * @return
     *  Returns Y amt of pixels
     */
    public int getLimitY(){
        limitY = background.getHeight(this);
        return limitY;
    }

    /**
     * Manages the background's scrolling.
     */
    public void setSlide(){
    	if(bgSlide>=800)
    		bgSlide=0;
    	else
    		bgSlide++;
    }

    /**
     * Sets the enemies to be drawn.
     * @param a
     *  What the enemy list should hold.
     */
    public void setEnemies(ArrayList<Enemy> a){
        enemies = a;
    }

    // Method to set the pulsating U flag
    public void setShowPulsatingU(boolean show) {
        this.showPulsatingU = show;
    }

    // Method to draw the pulsating "U"
    private void drawPulsatingU(Graphics2D g) {
        long time = System.currentTimeMillis();
        float alpha = (float) ((Math.sin(time * 0.01) + 1) / 2 * 0.5 + 0.5); // Pulsating effect
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setFont(new Font("SansSerif", Font.BOLD, 40));
        g.setColor(Color.MAGENTA);
        g.drawString("PRESS & HOLD U", 250, 700); // Position of the "U" on the screen
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Reset alpha
    }

    /**
     * Method to return the dot image
     * @param color
     *  Dot color int
     * @return Image
     */
    public Image getDotImage(int color) {
        if (color == 1) // red
            return rDot;
        if (color == 2) // blue
            return bDot;
        if (color == 3) // green
            return gDot;
        return null;
    }

    /**
     * Takes in a string to determine the color of the dot
     * @param s
     *  The color to change it to
     * @return
     *  The image in the color you specified
     */
    public Image getDotImage(String s){

        if(s.equalsIgnoreCase("blue"))
            return bDot;
        else if(s.equalsIgnoreCase("red"))
            return rDot;
        else if(s.equalsIgnoreCase("green"))
            return gDot;
        else
            return gDot;
    }
    
    /**
     * Sets the background of the game
     * @param ll
     *  The Level to pass to the panel
     */
    public void setLevel(Level ll){
        lvl = ll;
        background = lvl.getBackground();
    }

    /**
     * Sets the value to display of the last enemy's hp meter
     * @param i
     *  The int value of the enemy's hp
     * @param tot
     *  The int value of the enemy's total hp
     */
    public void setEnemyHPMeter(int i, int tot){
        enemyContainerNum = i;
        enemyContainerNumTotal = tot;
    }

    /**
     * Sets the panel to the win status or not
     * @param b
     *  The boolean value saying if the player has won
     */
    public void setWin(boolean b) {
        
        SoundPlayer.playSound("/laserGame/sounds/win.wav", 0.8f);
        win = b;
    }

    /**
     * Sets the panel to the lose status or not
     * @param b
     *  The boolean value saying if the player has lost
     */
    public void setLose(boolean b) {
        
        SoundPlayer.playSound("/laserGame/sounds/lose.wav", 0.8f);
        lose = b;
    }
    
    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }
}



