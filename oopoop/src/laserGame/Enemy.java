package laserGame;

import java.awt.*;
import java.util.*;

public class Enemy extends Character{

    private String moveFile;
    private Image image;
    private int hp, hpTotal, x, y, xSize, ySize, dotX, dotY, moveNum;
    private EnemyParser enPar;
    private LinkedList<Move> moveList;
    private Move currentMove;
    private boolean isFiring, isInitial;

    /**
     * The default enemy constructor
     * @param mv
     *  The string location of the enemy's move file
     * @param xx
     *  The initial x-coordinate spawn point
     * @param yy
     *  The initial y-coordinate spawn point
     */
    public Enemy(String mv, int xx, int yy){
        x=xx;
        y=yy;
        moveFile = mv;
        enPar = new EnemyParser(moveFile);
        enPar.parse();
        image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(enPar.getImageName()));
        moveNum = 0;
        moveList = enPar.getLoop();
        xSize = enPar.getXSize();
        ySize = enPar.getYSize();
        hp = enPar.getHP();
        hpTotal = hp;
        currentMove = moveList.get(moveNum);
        isFiring = false;
        isInitial = true;
    }



    /**
     * Controls the enemies movement/actions every turn
     */
    public void step(){
        isFiring=false;
        if(currentMove.getTurn()==currentMove.getNumTurns()){
            if(isInitial){
                moveList.remove(0);
                isInitial = false;
            }
            if(moveNum>moveList.size()-2)
                moveNum=0;
            else
                moveNum++;
               
            currentMove = moveList.get(moveNum);
        }

        if(currentMove.getDirection().equalsIgnoreCase("right"))
            x+=currentMove.getDistance();
        else if(currentMove.getDirection().equalsIgnoreCase("left"))
            x-=currentMove.getDistance();
        else if(currentMove.getDirection().equalsIgnoreCase("down"))
            y+=currentMove.getDistance();
        else if(currentMove.getDirection().equalsIgnoreCase("up"))
            y-=currentMove.getDistance();
        else if (currentMove.getDirection().equalsIgnoreCase("shoot")){
            dotY = currentMove.getDotYDist();
            dotX = currentMove.getDotXDist();
            isFiring=true;
        }
        else{
            //Needed to ensure the enemies sit in place and do nothing
        }
            

        currentMove.incrementTurn();
    }

    /**
     * Tells if the enemy is currently firing
     * @return
     *  A boolean saying if the enemy is firing or not
     */
    public boolean isFiring(){
        return isFiring;
    }

    /**
     * Tells if the enemy is in it's initial movement stage
     * @return
     *  A boolean saying if the enemy is in the initial stage or not
     */
    public boolean isInitial(){
        return isInitial;
    }

    /**
     * Sets Character's X coordinate
     * @param xx
     *  What the X Coordinate of the Character should be.
     */
    @Override
    public void setX(int xx){
        x=xx;
    }

    /**
     * Sets Character's Y coordinate
     * @param yy
     *  What the Y Coordinate of the Character should be.
     */
    @Override
    public void setY(int yy){
        y=yy;
    }

    /**
     * Returns the Character's current X coordinate.
     * @return
     *  The Character's current X coordinate
     */
    @Override
    public int getX(){
        return x;
    }

    /**
     * Returns the Character's current Y coordinate.
     * @return
     *  The Character's current Y coordinate
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
     * Gets the enemy's total hp
     * @return
     *  The int value of the enemy's total hp
     */
    public int getTotalHP(){
        return hpTotal;
    }


    /**
     * Gets the enemy's hp
     * @return
     *  The int value of the enemy's hp
     */
    @Override
    public int getHP(){
        return hp;
    }


    /**
     * Sets the enemy's hp
     * @param i
     *  The int value to set the enemy's hp to
     */
    @Override
    public void setHP(int i){
        hp=i;
    }


    /**
     * Increments the enemy's hp
     * @param i
     *  The int value to increment hp by
     */
    public void incrementHP(int i){
        hp+=i;
    }

    /**
     * Gets a dot associated with the character
     * @return
     *  Dot with traits belonging to the current character
     */
    public Dot getDot(){
        return new Dot(true, x+(xSize/2), y+ySize, dotX, dotY, "red");
    }

    /**
     * Gets the Image of the enemy
     * @return
     *  The enemy's current image
     */
    @Override
    public Image getImage(){
        return image;
    }
}
