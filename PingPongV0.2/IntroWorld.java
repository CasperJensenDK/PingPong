import greenfoot.*;
import java.awt.Font;

/**
 * Write a description of class IntroWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IntroWorld extends World
{
    private GreenfootSound introMusic;
    private static final int WORLD_WIDTH = 500;
    private static final int WORLD_HEIGHT = 700;
    
    //Color swap and intro message definers
    private Color[] colors;
    private int colorIndex;
    private int delayCounter;
    private int delayStart;
    private String introMessage;
    private String introMessageTwo;
    private String introMessageThree;
    //private String introMessageFour; Redundant

    /**
     * Constructor for objects of class IntroWorld.
     */
    public IntroWorld()
    {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1); 
        colors = new Color[] {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA};
        colorIndex = 0;
        delayCounter = 0;
        delayStart = 240; //Sets the delay between color swaps
        //Messages
        introMessage = "Welcome to Ping!";
        introMessageTwo = "PING!!!";
        introMessageThree = "Control with Arrow Keys, or A and D!";
        //introMessageFour = "Hit <enter> to start game..."; Redundant
        addIntroMessage();
        addIntroMessages();
        //Clickable figures for multiplayer and singleplayer
        Multiplayer multiplayer = new Multiplayer("multiplayer.png", "multiyellow.png");
        addObject(multiplayer, getWidth() / 2 + 100, getHeight() / 2 - 120);
        Singleplayer singleplayer = new Singleplayer("singleplayer.png", "singleyellow.png");
        addObject(singleplayer, getWidth() / 2 - 120, getHeight() / 2 - 120);
        //Sound
        introMusic = new GreenfootSound("introscreenmusic.mp3");        
    }
    
    public void act()
    {
        delayCounter++;
        if (delayCounter>= delayStart) 
        {
            delayCounter = 0;
            colorIndex = (colorIndex + 1) % colors.length;
            setBackgroundColor(colors[colorIndex]);
            addIntroMessage();
            addIntroMessages();
        }
        
        /*String key = Greenfoot.getKey();
        if (key != null && key.equals("enter"))
        {
            Greenfoot.setWorld(new PingWorld(true));
        } Redundant*/
        
    }
    
    private void setBackgroundColor(Color color) 
    {
        GreenfootImage background = new GreenfootImage(getWidth(), getHeight());
        background.setColor(color);
        background.fill();
        setBackground(background);
    }
    
    private void addIntroMessage() { // Bigger intro messages
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        greenfoot.Font font = new greenfoot.Font("OCR A Extended", true, false, 48);
        background.setFont(font);
        background.drawString(introMessage, getWidth() / 2 - 240, getHeight() / 2);
        background.drawString(introMessageTwo, getWidth() / 2 - 105, getHeight() / 2 - 225);
    }
    
    private void addIntroMessages() { //The smaller intro message
    GreenfootImage background = getBackground();
    background.setColor(Color.BLACK);
    greenfoot.Font fonttwo = new greenfoot.Font("OCR A Extended", true, false, 22);
    background.setFont(fonttwo);
    background.drawString(introMessageThree, getWidth() / 2 - 245, getHeight() / 2 + 75);
    //background.drawString(introMessageFour, getWidth() / 2 - 165, getHeight() / 2 + 150);
    }
    
    @Override
    public void stopped() {
        introMusic.pause();
    }
    
    @Override
    public void started() {
        introMusic.playLoop();
    }
}


