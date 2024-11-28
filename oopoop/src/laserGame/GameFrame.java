package laserGame;
import javax.swing.*;
import java.awt.event.*;

public class GameFrame extends JFrame implements ActionListener{
    
    private Timer timer;
 
    /**
     * Default Constructor
     */
    @SuppressWarnings("static-access")
    public GameFrame() {
        super("Space Invaders + Innovated Twist");
        this.setSize(800,750);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        
        // Start background music
        SoundPlayer.playSound("/laserGame/sounds/MusicBG2.wav", 0.7f);

   
    }
    
   
    /**
     * Listens for button presses
     * @param e
     *  takes in action event
     */
    @Override
    public void actionPerformed(ActionEvent e){

    }
    
}
