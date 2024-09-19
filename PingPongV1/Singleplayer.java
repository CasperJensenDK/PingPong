import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Singleplayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Singleplayer extends Actor
{
    private GreenfootImage singlePlayer;
    private GreenfootImage singleYellow;
    
    public Singleplayer(String singlePlayerPath, String singleYellowPath) {
        singlePlayer = new GreenfootImage(singlePlayerPath);
        singleYellow = new GreenfootImage(singleYellowPath);
        setImage(singlePlayer);
    }
    /**
     * Act - do whatever the Multiplayer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (Greenfoot.mouseMoved(this)) {
            setImage(singleYellow);
        } else if (Greenfoot.mouseMoved(null)) {
            setImage(singlePlayer);
        }
        
        if (Greenfoot.mouseClicked(this)) {
            onClick();
        }
        }
    
    public void onClick() {
        getWorld().stopped();
        Greenfoot.setWorld(new PingWorld(true, false));
    }
}
