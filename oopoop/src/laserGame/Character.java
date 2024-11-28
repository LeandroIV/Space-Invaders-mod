    package laserGame;
    import java.awt.*;

    public abstract class Character {

        private int hp;
        private String name;
        private Image image;
        private int x,y,xSize,ySize;

        /**
         * Default Constructor
         */
        public Character() {
            x=0;
            y=0;
            xSize=40;
            ySize=40;
        }

        /**
         * Constructs Character with specific values.
         * @param xx
         *  The X position of the Character.
         * @param yy
         *  The Y position of the Character.
         * @param xs
         *  The X length of the Character.
         * @param ys
         *  The Y length of the Character.
         * @param s
         *  The name of the Image to be used.
         */
        public Character(int xx, int yy, int xs, int ys, String s){
            x=xx;
            y=yy;
            xSize=xs;
            ySize=ys;
            image = Toolkit.getDefaultToolkit().getImage(
                    this.getClass().getResource(s));
        }


        /**
         * Sets the players hp
         * @param i
         *  sets players hp to int i
         */
        public void setHP(int i){
            hp=i;
        }

        /**
         * Gets players hp
         * @return
         *  returns players hp
         */
        public int getHP(){
            return hp;
        }

        /**
         * Sets the players name
         * @param s
         *  sets players name to String s
         */
        public void setName(String s){
            name=s;
        }

        /**
         * Gets the Players current name
         * @return
         *  returns players name
         */
        public String getName(){
            return name;
        }

        /**
         * Sets the Character's Image.
         * @param s
         *  The name of the Image to use.
         */
        public void setImage(String s){
            try{
                image = Toolkit.getDefaultToolkit().getImage(
                    this.getClass().getResource(s));
            } catch(Exception ex){
                System.out.println("Unable to find character image");
            }
            
        }

        /**
         * Gets the current Image used.
         * @return
         *  Current Character Image.
         */
        public Image getImage(){
            return image;
        }

        /**
         * Sets Character's X coordinate
         * @param xx
         *  What the X Coordinate of the Character should be.
         */
        public abstract void setX(int xx);

        /**
         * Sets Character's Y coordinate
         * @param yy
         *  What the Y Coordinate of the Character should be.
         */
        public abstract void setY(int yy);

        /**
         * Returns the Character's current X coordinate.
         * @return
         *  The Character's current X coordinate
         */
        public abstract int getX();

        /**
         * Returns the Character's current Y coordinate.
         * @return
         *  The Character's current Y coordinate
         */
        public abstract int getY();

        /**
         * Sets the Character's width.
         * @param xx
         *  What the Character's width should be set to.
         */
        public abstract void setXSize(int xx);
        
        /**
         * Sets the Character's height.
         * @param yy
         *  What the Character's height should be set to.
         */
        public abstract void setYSize(int yy);

        /**
         * Returns the Character's current width.
         * @return
         *  The Character's current width.
         */
        public abstract int getXSize();
        
        /**
         * Returns the Character's current height.
         * @return
         *  The Character's current height.
         */
        public abstract int getYSize();
    }
