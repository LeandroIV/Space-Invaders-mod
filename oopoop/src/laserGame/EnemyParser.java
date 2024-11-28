package laserGame;

import java.io.*;
import java.util.*;

public class EnemyParser {
    private LinkedList<Move> moveOrder;
    private String imageName, name;
    private int enemyHP, enemyXSize, enemyYSize;
    private InputStream instream;
    private InputStreamReader infile;
    private BufferedReader inbuf;

    /**
     * Constructor of the move parser
     * @param s
     *  The String representation of the enemy's .mvmt file
     */
    public EnemyParser(String s){
        name = s;
    }

    /**
     * Gets the list of moves to loop through
     * @return
     *  The LinkedList of moves
     */
    public LinkedList<Move> getLoop(){
        return moveOrder;
    }

    /**
     * The name of the image to use
     * @return
     *  The String representation of the image's location
     */
    public String getImageName(){
        return imageName;
    }

    /**
     * Gets the enemy's starting hp from the file
     * @return
     *  The int value of the enemy's starting hp
     */
    public int getHP(){
        return enemyHP;
    }

    /**
     * Gets the enemy's X-Size from the file
     * @return
     *  The int value of the enemy's X-Size
     */
    public int getXSize(){
        return enemyXSize;
    }

    /**
     * Gets the enemy's Y-Size from the file
     * @return
     *  The int value of the enemy's Y-Size
     */
    public int getYSize(){
        return enemyYSize;
    }

    /**
     * Parses the enemy file
     */
    public void parse(){
            try{

            moveOrder = new LinkedList<Move>();
            String tmp = "";
            //Gets file
            instream = this.getClass().getResourceAsStream(name);
            infile = new InputStreamReader(instream);
            inbuf = new BufferedReader(infile);
            inbuf.readLine();
            imageName = inbuf.readLine();
            String[] stats = inbuf.readLine().split(" ");
            enemyHP = Integer.parseInt(stats[0]);
            enemyXSize = Integer.parseInt(stats[1]);
            enemyYSize = Integer.parseInt(stats[2]);
            //Parses file into move
            while((tmp = inbuf.readLine()) != null){
                    if(!tmp.equalsIgnoreCase("")){
                        String[] tmparr = tmp.split(":");
                        moveOrder.add(new Move(tmparr[0], Integer.parseInt(tmparr[1]), Integer.parseInt(tmparr[2]), Integer.parseInt(tmparr[3])));
                    }
                }
            inbuf.close();
            infile.close();
            instream.close();
            } catch (IOException ex){
                System.out.println("Unable to parse enemy file!");
                System.exit(1);
        }
    }

    /**
     * Sets the current enemy to be parsed
     * @param s
     *  The string representation of the enemy file's location
     */
    public void setEnemy(String s){
        name = s;
    }
}
