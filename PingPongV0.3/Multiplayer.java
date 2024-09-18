import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Multiplayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Multiplayer extends Actor
{
    private GreenfootImage multiPlayer;
    private GreenfootImage multiYellow;
    
    public Multiplayer(String multiPlayerPath, String multiYellowPath) {
        multiPlayer = new GreenfootImage(multiPlayerPath);
        multiYellow = new GreenfootImage(multiYellowPath);
        setImage(multiPlayer);
    }
    /**
     * Act - do whatever the Multiplayer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (Greenfoot.mouseMoved(this)) {
            setImage(multiYellow);
        } else if (Greenfoot.mouseMoved(null)) {
            setImage(multiPlayer);
        }
        
        if (Greenfoot.mouseClicked(this)) {
            onClick();
        }
        }
    
    public void onClick() {
        getWorld().stopped();
        Greenfoot.setWorld(new PingWorld(true));
    }
}
