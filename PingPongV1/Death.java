import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Death here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Death extends World
{
    private String introMessage;
    private String introMessageTwo;
    private String introMessageThree;
    private String introMessageFour;
    private PingWorld sovs;
    private String[] insults = new String[]{
    "You're proof evolution can go wrong.",
    "If I had a dollar for each brain cell, I'd be broke.",
    "You're not stupid; just unlucky at thinking.",
    "You’re like a cloud when you disappear it's a beautiful day.",
    "I’d agree, but then we'd both be wrong.",
    "You bring joy by leaving the room.",
    "You’re not pretty enough to be this dumb.",
    "You're as useful as the 'g' in lasagna.",
    "I'd call you a tool, but tools are useful.",
    "Born on a highway? Because that's where accidents happen.",
    "You have the perfect face for radio.",
    "Your brain's a tourist: rarely in town.",
    "I'd explain it, but crayons are at home.",
    "You're a few fries short of a Happy Meal.",
    "If clueless was an Olympic sport, you'd win."
    };
    private String theInsult = insults[Greenfoot.getRandomNumber(insults.length)];
    /**
     * Constructor for objects of class Death.
     * 
     */
    public Death()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(500, 700, 1); 
        setBackground("pingbackground0.png");
        //Messages
        introMessage = "GAME!";
        introMessageTwo = "OVER!!!";
        introMessageThree = theInsult;
        introMessageFour = "Hit <Enter> to restart game...";
        addIntroMessage();
        addIntroMessages();
    }
    public Death(int playerNumber){
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(500, 700, 1); 
        setBackground("pingbackground0.png");
        //Messages
        introMessage = "GAME!";
        introMessageTwo = "OVER!!!";
        introMessageThree = theInsult;
        introMessageFour = "Hit <Enter> to restart game...";
        addMessageForPlayer("A message for Player " + playerNumber + ":");
        addIntroMessage();
        addIntroMessages();
    }
    public void act()
    {
        reset();
    }
    private void addMessageForPlayer(String str){
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        greenfoot.Font fonttwo = new greenfoot.Font("OCR A Extended", true, false, 12);
        background.setFont(fonttwo);
        background.drawString(str, getWidth()/2 - (int) (str.length() * 12 * 0.6f)/2, getHeight() / 2 + 55);
    }
    
    private void addIntroMessage() { // Bigger intro messages
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        greenfoot.Font font = new greenfoot.Font("OCR A Extended", true, false, 48);
        background.setFont(font);
        
        background.drawString(introMessage, getWidth() / 2 -75, getHeight() / 2 - 175);
        background.drawString(introMessageTwo, getWidth() / 2 - 75, getHeight() / 2 + 175);
    }
    
    private void addIntroMessages() { //The smaller intro message
    GreenfootImage background = getBackground();
    background.setColor(Color.BLACK);
    greenfoot.Font fonttwo = new greenfoot.Font("OCR A Extended", true, false, 22);
    background.setFont(fonttwo);
    
     background.drawString(introMessageFour,getWidth()/2 - (int) (introMessageFour.length() * 22 * 0.6f)/2, getHeight() / 2 + 200);
    greenfoot.Font fontthree = new greenfoot.Font("OCR A Extended", true, false, 12);
    background.setFont(fontthree);
    background.drawString(introMessageThree, getWidth()/2 - (int) (introMessageThree.length() * 12 * 0.6f)/2, getHeight() / 2 + 75);
    
   
    }
    private void reset()
    {
       if ( Greenfoot.isKeyDown("enter")){
           IntroWorld kurtJensen = new IntroWorld();
           kurtJensen.started();
           Greenfoot.setWorld(kurtJensen);}
    }
}