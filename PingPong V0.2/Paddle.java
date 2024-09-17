import greenfoot.*;


/**
 * A paddle is an object that goes back and forth. Though it would be nice if balls would bounce of it.
 * 
 * @author The teachers 
 * @version 1
 */
public class Paddle extends Actor
{
    private int width;
    private int height;
    private int speed; // speed
    private boolean isPlayer = false;
    private int[] dir;

    /**
     * Constructs a new paddle with the given dimensions.
     */
    public Paddle(int width, int height, boolean isPlayer)
    {
        this.width = width;
        this.height = height;
        this.isPlayer = isPlayer;
        dir = new int[]{0, 0};
        speed = 2;
        createImage();
    }

    /**
     * Act - do whatever the Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // hvis vi vil kan vil tilføje acceleration
        if (getWorld() != null && isPlayer){
            if (Greenfoot.isKeyDown("A") || Greenfoot.isKeyDown("left")){
                dir[0] = -speed;
            }
            else if (Greenfoot.isKeyDown("D") || Greenfoot.isKeyDown("right")){
                dir[0] = speed;
            }
            else{
                dir = new int[]{0, 0};
            }
        }
        //getX() + dir[0]
        setLocation(472, getY() + dir[1]); 
        if (getX() + width/2 > getWorld().getWidth()){
            setLocation(getWorld().getWidth() - width/2, getY());
        }
        else if (getX() - width/2 < 0){
            setLocation(0 + width/2, getY());
        }
    }

    /**
     * Creates and sets an image for the paddle, the image will have the same dimensions as the paddles width and height.
     */
    public void createImage()
    {
        GreenfootImage image = new GreenfootImage(width, height);
        image.setColor(Color.BLACK);
        image.fill();
        setImage(image);
    }
    
    public float getDx(){
        return speed;
    }
    public int[] getWidthHeight(){
        int[] jens = {width, height};
        return jens;
    }
    
    public int[] getDir(){
        return dir;
    }

}
