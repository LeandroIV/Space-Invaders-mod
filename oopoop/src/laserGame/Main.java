package laserGame;


public class Main {

    /**
     * Main thread of program sets engine refresh rate
     *  @param args the command line arguments
     */
    @SuppressWarnings("unused")
    public static void main(String[] args) {
	
	//Sets the pmoffscreen to false to fix bug in openjdk rendering
	try{
		System.setProperty("sun.java2d.pmoffscreen", "false");
	}catch(Exception ex){
		ex.printStackTrace();
		System.out.println("Unable to change system properties!");
	}
        
        GameFrame frame = new GameFrame();
        
        Engine engine = new Engine(frame);

        while(true){
            try{
                engine.step();
                Thread.sleep(15);
            }catch(InterruptedException ex){}
        }
    }
}
