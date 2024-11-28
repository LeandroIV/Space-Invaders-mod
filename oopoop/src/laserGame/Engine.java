package laserGame;
import java.awt.event.*;
import java.util.*;

public class Engine implements KeyListener{

    private GameFrame frame;
    private GamePanel panel;
    private LinkedList<Dot> dotList;
    private ArrayList<Enemy> enemies;
    private Player pl;
    private LinkedList<Level> levels;
    private Level level;
    private LinkedList<Event> events;
    private Event currentEvent;
    private long time;
    private boolean isComplete, isLast;
    private int deflectedDots; // Field to track the number of deflected dots
    private boolean showPulsatingU;
    

    /**
     * Constructs the default game environment --only usable constructor
     * @param f
     *  takes in a GameFrame
     */
    public Engine(GameFrame f) {
        frame = f;
        levels = new LinkedList<Level>();
        this.createLevel("dynamics/singleTest.lvl");
        this.createLevel("dynamics/testLevel.lvl");
        this.createLevel("dynamics/boss.lvl");
        level = levels.get(0);
        panel = new GamePanel(level);
        frame.add(panel);
        frame.addKeyListener(this);
        dotList = new LinkedList<Dot>();
        pl = new Player();
        time = 0;
        events = level.getEventList();
        currentEvent = events.remove(0);
        enemies = new ArrayList<Enemy>();
        isLast = false;
        isComplete = false;
        deflectedDots = 0; // Initialize the deflected dots counter
        showPulsatingU = false; // Initialize the pulsating U flag
        
        
    }

    /**
     * Sets the movement boundaries and how fast the player moves
     */
    public void step() {
        panel.setPlayerCoordinates(pl.getX(), pl.getY());

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).step();
            if (enemies.get(i).isFiring())
                this.newEnemyDot(enemies.get(i));
        }

        this.manageDots();
        this.managePanel();

        // Player Bullet Fire
        if (pl.isFiring())
            this.newPlayerDot();
        if (pl.isRandomFiring())
            this.newRandomPlayerDot();

        Iterator<Dot> it = dotList.iterator();
        while (it.hasNext()) {
            Dot tmpDot = it.next();
            if (tmpDot.isDead())
                it.remove();
        }

        // Removing off screen enemies
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (!e.isInitial()) {
                if (e.getX() < -100 || e.getX() > 900 || e.getY() < -100 || e.getY() > 850) {
                    enemies.remove(e);
                    i--;
                }
            }
        }

        // Events being called and used
        if (events.size() == 0 && enemies.size() < 1 && !isComplete && isLast) {
            isComplete = true;
            this.nextLevel();
        }
        if (currentEvent != null) {
            if (currentEvent.getTime() == time) {
                for (Enemy e : currentEvent.getEnemies())
                    enemies.add(e);
                // Determines if level is out of events
                if (enemies.size() > 0 && events.size() == 0)
                    isLast = true;
                if (events.size() > 0) {
                    currentEvent = events.remove(0);
                }
            }
            time++;
        }

        // Player step method called
        pl.step();

        // Check if the energy level is 19000 or more
        if (pl.getEnergy() >= 19000) {
            showPulsatingU = true;
        } else {
            showPulsatingU = false;
        }

        // Update the panel with the pulsating U status
        panel.setShowPulsatingU(showPulsatingU);
    }

    /**
     * Recognizes keypresses and sets boolean to true
     * @param e
     *  takes in a key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            pl.setRight(true);
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            pl.setLeft(true);
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            pl.setUp(true);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            pl.setDown(true);
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            pl.setVenting(false);
            pl.setFiring(true);
        } else if (e.getKeyCode() == KeyEvent.VK_K) {
            if (pl.getEnergy() >= 4 && !pl.isShielded()) {
                pl.setVenting(false);
                SoundPlayer.playSound("/laserGame/sounds/shield.wav", 0.6f);
                pl.setShielded(true);
            } else {
                pl.setShielded(false);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_U) {
            if (pl.getEnergy() >= 19000) {
                pl.setVenting(false);
                pl.setRandomFiring(true);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_L) {
            if (pl.isVenting())
                pl.setVenting(false);
            else {
                pl.setVenting(true);
                SoundPlayer.playSound("/laserGame/sounds/venting.wav", 0.6f);
                pl.setShielded(false);
                pl.setFiring(false);
                pl.setRandomFiring(false);
            }
        }
    }

    /**
     * Recognizes when a key is typed
     * @param e
     *  takes in a key event
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Recognizes when a key is released and sets boolean to false
     * @param e
     *  takes in a key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            pl.setRight(false);
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            pl.setLeft(false);
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            pl.setUp(false);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            pl.setDown(false);
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            pl.setFiring(false);
        } else if (e.getKeyCode() == KeyEvent.VK_U) {
            pl.setRandomFiring(false);
        }
    }

    /**
     * Manages dot removal and runs hit detection.
     */
    public void manageDots() {
        // Checks to see if dot is hitting enemy, and increments if not.
        Iterator<Dot> it = dotList.iterator();
        while (it.hasNext()) {
            Dot tmpDot = it.next();

            // Removes Out-Of-Bounds Dots
            if (tmpDot.getY() < -10)
                it.remove();
            else if (tmpDot.getY() > 760)
                it.remove();
            else if (tmpDot.getX() < -10)
                it.remove();
            else if (tmpDot.getX() > 810)
                it.remove();
            else {
                tmpDot.step();
                // Check if dot is good and hitting enemy or if it's deflected and hitting enemy
                if (!tmpDot.isBad() || tmpDot.isDeflected()) {
                    if (enemies.size() > 0) {
                        for (int c = 0; c < enemies.size(); c++) {
                            if (this.checkHit(tmpDot, enemies.get(c))) {
                                tmpDot.setDead(true);
                                Enemy tmpEnemy = enemies.get(c);
                                tmpEnemy.incrementHP(-5);
                                if (tmpEnemy.getHP() < 1) {
                                    panel.setEnemyHPMeter(0, 1);
                                    enemies.remove(c);
                                    c--;
                                    panel.incrementScore(); // Increment score when an enemy is removed
                                } else {
                                    panel.setEnemyHPMeter(tmpEnemy.getHP(), tmpEnemy.getTotalHP());
                                }
                            }
                        }
                    }
                }

                // Check to see if dot is bad and hitting player
                if (tmpDot.isBad() && !tmpDot.isDeflected()) { // Only check for bad dots that are not deflected
                    if (this.checkHit(tmpDot, pl)) {
                        if (pl.getHP() < 1)
                            panel.setLose(true);
                        if (!pl.isShielded()) {
                            pl.incrementHP(-1);
                            it.remove();
                        } else {
                            // Bounce the bullet off instead of removing it
                            bounceDotOff(tmpDot, pl);
                        }
                    }
                }
            }
        }
        panel.setDots(dotList);
    }
    

    /**
     * Adds a new Player dot to the list.
     */
    public void newPlayerDot() {
        dotList.add(pl.getLeftDot());
        dotList.add(pl.getRightDot());
    }

    /**
     * Adds a new Player dot to the list.
     */
    public void newRandomPlayerDot() {
        dotList.add(pl.getRandomLeftDot());
        SoundPlayer.playSound("/laserGame/sounds/Pewpew.wav", 0.5f);
        dotList.add(pl.getRandomRightDot());
        
    }

    /**
     * Adds a new Enemy dot to the list.
     */
    public void newEnemyDot(Enemy e) {
        dotList.add(e.getDot());
    }

    /**
     * Checks to see if a specific dot is hitting a specific character.
     * @param d
     *  The Dot to check.
     * @param c
     *  The Character to check.
     * @return
     *  True if Dot is in Character's bounds, False if not.
     */
    public boolean checkHit(Dot d, Character c) {
        boolean b = false;
        int dx = d.getX();
        int dy = d.getY();
        int cx = c.getX();
        int cy = c.getY();
        int cxs = c.getXSize();
        int cys = c.getYSize();

        if (((dx < cx + cxs) && (dx > cx)) && ((dy < cy + cys) && (dy > cy)))
            b = true;
        return b;
    }

    /**
     * Bounces a dot off the player's shield.
     * @param d
     *  The Dot to bounce.
     * @param pl
     *  The Player character with the shield.
     */
    private void bounceDotOff(Dot d, Player pl) {
        int dotX = d.getX();
        int dotY = d.getY();
        int playerX = pl.getX();
        int playerY = pl.getY();
    
        // Calculate the direction to bounce the dot
        int bounceX = dotX - playerX;
        int bounceY = playerY - dotY; // Invert the Y direction to make it bounce upwards
    
        // Set the new direction for the dot
        d.setDirection(bounceX, bounceY);
    
        // Optionally increase the speed of the dot after bouncing
        d.setSpeed(d.getSpeed() * 4.5);
    
        // Mark the dot as deflected
        d.setDeflected(true);
        
        // Play the shield bounce sound effect
        SoundPlayer.playSound("/laserGame/sounds/shieldbounce.wav", 0.6f);

        // Increment the deflected dots counter
        deflectedDots++;
        panel.incrementScore(); // Increment score when a dot is deflected
    }

    // Method to get the number of deflected dots
    public int getDeflectedDots() {
        return deflectedDots;
    }

    /**
     * Calls all of the methods required to update the panel
     */
    public void managePanel() {
        panel.setEnergyContainer(pl.getEnergyContainer());
        panel.setHeatContainer(pl.getHeatContainer());
        panel.setEnergy(pl.getEnergy());
        panel.setHeat(pl.getHeat());
        panel.setHP(pl.getHP());
        panel.setSlide();
        panel.repaint();
        panel.setVenting(pl.isVenting());
        panel.setShield(pl.isShielded());
        panel.setEnemies(enemies);
    }

    /**
     * Shifts the engine to the next level
     */
    public void nextLevel() {
        time = 0;
        isLast = false;
        isComplete = false;
        if (levels.size() > 1) {
            levels.remove(0);
            level = levels.get(0);
            level.parse();
            panel.setLevel(level);
            events = level.getEventList();
            currentEvent = events.remove(0);
        } else if (levels.size() == 1) {
            panel.setWin(true);
            levels.remove(0);
            currentEvent = null;
            SoundPlayer.stopSound();
        }
    }

    /**
     * Creates a new level and adds it to the level list
     * @param s
     *  The String representation of the level's location
     */
    private void createLevel(String s) {
        level = new Level(s);
        level.parse();
        levels.add(level);
    }
}
