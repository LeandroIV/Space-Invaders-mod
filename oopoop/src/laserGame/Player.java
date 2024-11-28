package laserGame;
import java.util.Random;

public class Player extends Character{
    private boolean left,right,up,down,shielded,firing,randomFiring,venting,canMove;
    private int x,y,xSize,ySize,energy, dotSpeed,heat,hp;
    private Random rand;

    public Player() {
        rand = new Random();
        energy = 0; // Initialize energy to 0
        heat = 0;
        hp = 100;
        shielded = false;
        firing = false;
        randomFiring = false;
        x = 350;
        y = 450;
        xSize = 39;
        ySize = 33;
        dotSpeed = -150;
        canMove = true;
    }

    /**
     * Gets a dot associated with the character
     * @return
     *  Dot with traits belonging to the current character
     */
    public Dot getLeftDot(){
        return new Dot(false, x+23, y+16, 0, dotSpeed, "blue");
    }

    /**
     * Gets a dot associated with the character
     * @return
     *  Dot with traits belonging to the current character
     */
    public Dot getRightDot(){
        return new Dot(false, x+38, y+16, 0, dotSpeed, "blue");
    }
    
    /**
     * Gets a Random dot associated with the character
     * @return
     *  Dot with traits belonging to the current character
     */
    public Dot getRandomLeftDot(){
        
        return new Dot(false, x+23, y+16, 0+rand.nextInt(80)-40, dotSpeed+rand.nextInt(160)-40, "blue");
    }

    /**
     * Gets a Random dot associated with the character
     * @return
     *  Dot with traits belonging to the current character
     */
    public Dot getRandomRightDot(){
        return new Dot(false, x+38, y+16, 0+rand.nextInt(80)-40, dotSpeed+rand.nextInt(160)-40, "blue");
    }

    /**
     * Changes the players hp
     * @param i
     *  The int to increment the player's hp by
     */
    public void incrementHP(int i){
        hp+=i;
    }

    /**
     * Sets the players hp
     * @param i
     *  The int to set the player's hp to
     */
    @Override
    public void setHP(int i){
        hp=i;
    }

    /**
     * Gets the player's hp
     * @return
     *  The int value of the player's hp
     */
    @Override
    public int getHP(){
        return hp;
    }
    /**
     * Sets the Players X-Coordinate
     * @param xx
     *  The value to set x to
     */
    @Override
    public void setX(int xx){
        x=xx;
    }

    /**
     * Sets the Players Y-Coordinate
     * @param yy
     *  The value to set y to
     */
    @Override
    public void setY(int yy){
        y=yy;
    }

    /**
     * Sets if the player is moving left
     * @param b
     *  The boolean value to set left to
     */
    public void setLeft(boolean b){
        left=b;
    }

    /**
     * Sets if the player is moving right
     * @param b
     *  The boolean value to set right to
     */
    public void setRight(boolean b){
        right=b;
    }

    /**
     * Sets if the player is moving up
     * @param b
     *  The boolean value to set up to
     */
    public void setUp(boolean b){
        up=b;
    }

    /**
     * Sets if the player is moving down
     * @param b
     *  The boolean value to set down to
     */
    public void setDown(boolean b){
        down=b;
    }

    /**
     * Sets if the player is shielding
     * @param b
     *  The boolean value to set the shield on or off
     */
    public void setShielded(boolean b){
        shielded=b;
    }

    /**
     * Sets if the player is firing
     * @param b
     *  The boolean value to set if the player is firing
     */
    public void setFiring(boolean b){
        if(venting || randomFiring)
            firing = false;
        else
            firing = b;
    }

    /**
     * Sets the player's current energy
     * @param en
     *  The value to set the current player's energy to
     */
    public void setEnergy(int en){
        energy=en;
    }

    /**
     * Sets the player's current heat
     * @param d
     *  The value to set the current player's heat to
     */
    public void setHeat(int d){
        heat=d;
    }

    /**
     * Gets the player's X coordinate
     * @return
     *  The players X Coordinate
     */
    @Override
    public int getX(){
        return x;
    }

    /**
     * Gets the player's X coordinate
     * @return
     *  The players X Coordinate
     */
    @Override
    public int getY(){
        return y;
    }

        /**
     * Sets the Character's width.
     * @param xx
     *  What the Character's width should be set to.
     */
    @Override
    public void setXSize(int xx){
        xSize=xx;
    }

    /**
     * Sets the Character's height.
     * @param yy
     *  What the Character's height should be set to.
     */
    @Override
    public void setYSize(int yy){
        ySize=yy;
    }

    /**
     * Returns the Character's current width.
     * @return
     *  The Character's current width.
     */
    @Override
    public int getXSize(){
        return xSize;
    }

    /**
     * Returns the Character's current height.
     * @return
     *  The Character's current height.
     */
    @Override
    public int getYSize(){
        return ySize;
    }

    /**
     * Tells if the player is moving left
     * @return
     *  Boolean is moving left
     */
    public boolean isLeft(){
        return left;
    }

    /**
     * Tells if the player is moving right
     * @return
     *  Boolean is moving right
     */
    public boolean isRight(){
        return right;
    }

    /**
     * Tells if the player is moving up
     * @return
     *  Boolean is moving up
     */
    public boolean isUp(){
        return up;
    }

    /**
     * Tells if the player is moving Down
     * @return
     *  Boolean is moving Down
     */
    public boolean isDown(){
        return down;
    }

    /**
     * Returns the player's current energy
     * @return
     *  The player's current energy
     */
    public int getEnergy(){
        return energy;
    }

    /**
     * Returns the player's current heat
     * @return
     *  The player's current heat
     */
    public int getHeat(){
        return heat;
    }

    /**
     * Returns whether the shield is on
     * @return
     *  the boolean value of whether the shield is on or off
     */
    public boolean isShielded(){
        return shielded;
    }

    /**
     * Returns whether the player is firing
     * @return
     *  the boolean value of whether the player is firing or not
     */
    public boolean isFiring(){
        return firing;
    }

    /**
     * Gets the number of energy containers to display
     * @return
     *  The number of energy containers
     */
    public int getEnergyContainer(){
        return energy/1000;
    }

    /**
     * Gets the number of heat containers to display
     * @return
     *  The number of heat containers
     */
    public int getHeatContainer(){
        return heat/1000;
    }

    /**
     * Adds to the value of x
     * @param xx
     *  The value to change X by
     */
    public void incrementX(int xx){
        x=x+xx;
    }

    /**
     * Adds to the value of Y
     * @param yy
     *  The value to change Y by
     */
    public void incrementY(int yy){
        y=y+yy;
    }

    /**
     * Adds to the player's energy value
     * @param e
     *  The value to change energy by
     */
    public void incrementEnergy(int e){
        energy=energy+e;
    }

    /**
     * Adds to the player's heat value
     * @param h
     *  The value to change heat by
     */
    public void incrementHeat(int h){
        heat=heat+h;
    }
    
    /**
     * Sets the player in a mode where he fires random bullets
     * @param b
     *  The boolean value to determine if the player is firing random bullets
     */
    public void setRandomFiring(boolean b) {
        if(venting || firing)
            randomFiring = false;
        else
            randomFiring = b;
    }

    /**
     * Tells if the player is in random firing mode
     * @return
     *  The boolean value saying if the player is randomly firing
     */
    public boolean isRandomFiring(){
        return randomFiring;
    }

    /**
     * Sets the player in a mode where the ship vents
     * @param b
     *  The boolean value to determine if the player is venting
     */
    public void setVenting(boolean b) {
        venting = b;
    }

    /**
     * Tells if the player is in venting mode
     * @return
     *  The boolean value saying if the player is venting
     */
    public boolean isVenting(){
        return venting;
    }
    
    /**
     * Changes the characters attributes every turn
     */
    public void step(){
        if(energy<=19976)
            energy+=24;
        else
            energy=20000;
    
        if(heat<1)
            venting=false;
        if(heat>=6)
            heat-=6;
        else if(heat<6)
            heat=0;
    
        if(shielded){
            energy-=4;
            heat+=25;
        }
    
        if(heat<0)
            heat=0;
        if(energy<0)
            energy=0;
    
        // Player Movement
        if(heat>20000){
            canMove=false;
        }
        else if(heat<18000)
            canMove=true;
    
        if(canMove){
            if(right&&x<730)
               this.incrementX(5);
            if(left&&x>=5)
               this.incrementX(-5);
            if(down&&y<670)
               this.incrementY(5);
            if(up&&y>=5)
               this.incrementY(-5);
        }
    
        if(energy>=16){
            if(firing){
                this.incrementEnergy(-16);
                this.incrementHeat(8);
            }
        }
    
        // Handle random firing
        if(randomFiring){
            if(energy > 0){
                this.incrementEnergy(-100); // Change the depletion rate as necessary
                this.incrementHeat(150);
            } else {
                randomFiring = false;
            }
        }
    
        if(venting){
            if(heat>30)
                heat-=80;
            else
                heat=0;
        }
    
        if(energy<4)
            shielded=false;
        if(heat>21000)
            shielded=false;
    }


}
