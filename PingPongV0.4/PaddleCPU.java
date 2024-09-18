import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PaddleCPU here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PaddleCPU extends Paddle
{
    private int dx; // retning
    public PaddleCPU(int width, int height, boolean isPlayer, int direction){
        super(width, height, isPlayer);
        dx = direction;
        createImage();
    }
    /**
     * Act - do whatever the PaddleCPU wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        tryChangeDirection();
        if (getWorld() != null){
            setLocation(getX() + dx, getY());
        }
    }
    
    /**
     * Will rotate the paddle 180 degrees if the paddle is at worlds edge.
     */
    private void tryChangeDirection()
    {
        //Check to see if we are touching the outer boundaries of the world:
        // IF we are touching the right boundary OR we are touching the left boundary:
        //getWidthHeight()[0]/2, - getWidthHeight()[0]/2
        if(getX() >= getWorld().getWidth() - 5 || getX() <= 0)
        {
            //Change our 'x' direction to the inverted direction:
            int newX = (dx > 0) ? 1 : 499 - 5;
            setLocation(newX, getY());
            
            
            
            if (Greenfoot.getRandomNumber(4) == 2){
                dx = dx * -1;
                PingWorld kurtJensen = (PingWorld) getWorld();
                kurtJensen.terminatePaddle(this, dx);
            }
        }
    }
}
