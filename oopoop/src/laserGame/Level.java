package laserGame;
import java.awt.*;
import java.util.LinkedList;
import java.io.*;

public class Level {
    private Image background;
    private LinkedList<Event> eventList;
    private String levelName, backgroundName;
    private InputStream instream;
    private InputStreamReader infile;
    private BufferedReader inbuf;

    /**
     * Constructs a new Level
     * @param ln
     *  Name of the level to import
     */
    public Level(String ln){
        levelName = ln;
    }

    /**
     * Gets the list of events to loop through
     * @return
     *  The LinkedList of events
     */
    public LinkedList<Event> getEventList(){
        return eventList;
    }

    /**
     * Sets background image
     * @param s
     *  takes in String s for image name;
     */
    public void setBackground(String s){
        background = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource(s));
    }

    /**
     * Gets the background image
     * @return
     *  returns background image
     */
    public Image getBackground(){
        return background;
    }

    public void parse(){
        try{
            eventList = new LinkedList<Event>();
            String tmp = "";
            //Gets file
            instream = this.getClass().getResourceAsStream(levelName);
            infile = new InputStreamReader(instream);
            inbuf = new BufferedReader(infile);
            inbuf.readLine();
            backgroundName = inbuf.readLine();
            
            //Parses file into event
            while((tmp = inbuf.readLine()) != null){
                String[] tmparr = tmp.split(":");
                eventList.add(new Event(tmparr[0], Integer.parseInt(tmparr[1]), Integer.parseInt(tmparr[2]), Integer.parseInt(tmparr[3]), Integer.parseInt(tmparr[4]), Integer.parseInt(tmparr[5]), Integer.parseInt(tmparr[6])));
            }
            inbuf.close();
            infile.close();
            instream.close();
            this.setBackground(backgroundName);
            
     
        } catch (IOException ex){
            System.out.println("Unable to parse Level file!");
            System.exit(1);
        }
    }
}
